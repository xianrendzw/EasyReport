/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : easy_report

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2014-08-04 11:59:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for config_dict
-- ----------------------------
DROP TABLE IF EXISTS `config_dict`;
CREATE TABLE `config_dict` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置字典ID',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(50) NOT NULL,
  `value` varchar(1000) NOT NULL,
  `comment` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_key` (`key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='配置字典表';

-- ----------------------------
-- Table structure for datasource
-- ----------------------------
DROP TABLE IF EXISTS `datasource`;
CREATE TABLE `datasource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据源ID',
  `uid` varchar(128) DEFAULT NULL COMMENT '数据源唯一ID,由调接口方传入',
  `name` varchar(50) NOT NULL COMMENT '数据源名称',
  `jdbc_url` varchar(100) NOT NULL COMMENT '数据源连接字符串(JDBC)',
  `user` varchar(50) NOT NULL COMMENT '数据源登录用户名',
  `password` varchar(100) NOT NULL COMMENT '数据源登录密码',
  `comment` varchar(100) NOT NULL COMMENT '说明备注',
  `create_user` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  KEY `AK_uk_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='数据源配置信息表';

-- ----------------------------
-- Table structure for reporting
-- ----------------------------
DROP TABLE IF EXISTS `reporting`;
CREATE TABLE `reporting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `pid` int(11) NOT NULL DEFAULT '0',
  `ds_id` int(11) NOT NULL COMMENT '数据源ID',
  `uid` varchar(128) DEFAULT NULL COMMENT '报表唯一ID,由接口调用方传入',
  `name` varchar(50) NOT NULL COMMENT '报表名称',
  `path` varchar(500) NOT NULL COMMENT '报表树型结构路径',
  `flag` int(11) NOT NULL COMMENT '报表树型节点标志,0为分类节点，1为报表结点',
  `has_child` tinyint(1) NOT NULL,
  `status` int(11) NOT NULL COMMENT '报表状态（1表示锁定，0表示编辑)',
  `sequence` int(11) NOT NULL COMMENT '报表节点在其父节点中的顺序',
  `data_range` int(11) NOT NULL DEFAULT '7' COMMENT '报表默认展示多少天的数据',
  `layout` int(2) NOT NULL COMMENT '布局形式.1横向;2纵向',
  `stat_column_layout` int(11) DEFAULT '1' COMMENT '统计列布局形式.1横向;2纵向',
  `sql_text` text NOT NULL COMMENT '报表SQL语句',
  `meta_columns` text NOT NULL COMMENT '配置项对象JSON序列化字符串',
  `query_params` text NOT NULL COMMENT '查询条件列属性集合json字符串',
  `comment` varchar(500) DEFAULT NULL COMMENT '说明备注',
  `create_user` varchar(50) NOT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  KEY `ukuid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=704 DEFAULT CHARSET=utf8 COMMENT='报表信息表';

-- ----------------------------
-- Table structure for reporting_sql_history
-- ----------------------------
DROP TABLE IF EXISTS `reporting_sql_history`;
CREATE TABLE `reporting_sql_history` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表SQL语句版本历史id',
  `report_id` int(11) NOT NULL COMMENT '报表id',
  `sql_text` text NOT NULL COMMENT '报表SQL语句',
  `author` varchar(64) NOT NULL COMMENT '报表SQL语句当前版本创建者',
  `comment` varchar(100) NOT NULL COMMENT '报表SQL语句版本历史说明',
  `create_time` datetime NOT NULL COMMENT '报表SQL语句版本历史创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '报表SQL语句版本历史修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1189 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reporting_task
-- ----------------------------
DROP TABLE IF EXISTS `reporting_task`;
CREATE TABLE `reporting_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表任务ID',
  `report_ids` varchar(1024) NOT NULL COMMENT '报表ID列表',
  `peroid` varchar(2) NOT NULL COMMENT '报表任务执行频率(取值：y每年|M每月|w每周|d每天|h每小时|m每分|s每秒)',
  `interval` int(11) NOT NULL COMMENT '报表任务执行间隔',
  `time` time NOT NULL COMMENT '报表任务执行时间,指定任务什么时间执行',
  `comment` varchar(100) NOT NULL COMMENT '报表任务说明',
  `create_time` datetime NOT NULL COMMENT '报表任务记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '报表任务记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='报表任务信息表';

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `pos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
