CREATE DATABASE  IF NOT EXISTS `easyreport2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `easyreport2`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: easyreport2
-- ------------------------------------------------------
-- Server version	5.5.5-10.0.13-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ezrpt_member_module`
--

DROP TABLE IF EXISTS `ezrpt_member_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_member_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统模块标识',
  `parent_id` int(11) NOT NULL COMMENT '系统模块父标识',
  `name` varchar(50) NOT NULL COMMENT '系统模块父标识',
  `code` varchar(50) NOT NULL COMMENT '系统模块代号',
  `icon` varchar(100) NOT NULL COMMENT '系统模块显示图标',
  `url` varchar(100) NOT NULL COMMENT '系统模块对应的页面地址',
  `path` varchar(200) NOT NULL COMMENT '从根模块到当前子模块的id路径，id之间用逗号分隔',
  `has_child` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否存在子模块,0否,1 是',
  `link_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'URL链接类型(0表示系统内部，1表示外部链接，默认 0)',
  `target` varchar(20) NOT NULL COMMENT 'URL链接的target(_blank,_top等)',
  `params` varchar(200) NOT NULL COMMENT 'URL链接参数',
  `sequence` int(11) NOT NULL COMMENT '系统模块在当前父模块下的排序顺序',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '系统模块的状态,1表示启用,0表示禁用,默认为1,其他保留',
  `comment` varchar(50) NOT NULL COMMENT '系统模块备注',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '系统模块记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统模块记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_module_code` (`code`),
  UNIQUE KEY `uk_module_parent_id_name` (`parent_id`,`name`),
  KEY `ix_path` (`path`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_member_module`
--

LOCK TABLES `ezrpt_member_module` WRITE;
/*!40000 ALTER TABLE `ezrpt_member_module` DISABLE KEYS */;
INSERT INTO `ezrpt_member_module` VALUES (3,40,'数据源','report.ds','icon-datasource','views/metadata/ds','40,3',0,0,'','',2,1,'数据源','2014-10-29 22:41:24','2015-12-17 10:13:28'),(4,40,'报表设计','report.designer','icon-chart','views/metadata/designer','40,4',0,0,'','',1,1,'报表设计','2014-10-29 22:41:42','2015-12-17 10:13:28'),(7,0,'用户权限','membership','icon-user','views/membership/user','7',1,0,'','',3,1,'用户权限','2014-10-29 22:45:47','2015-12-17 10:13:28'),(23,7,'用户管理','membership.user','icon-member','views/membership/user','7,23',0,0,'','',1,1,'用户管理','2014-10-29 23:38:22','2015-12-17 10:13:57'),(24,7,'角色管理','membership.role','icon-group','views/membership/role','7,24',0,0,'','',2,1,'角色管理','2014-10-29 23:38:44','2015-12-17 10:13:57'),(25,7,'权限管理','membership.permission','icon-perm','views/membership/permission','7,25',0,0,'','',3,1,'操作管理','2014-10-29 23:39:03','2015-12-17 10:13:57'),(26,32,'系统日志','sys.event','icon-event','views/sys/event','32,26',0,0,'','',3,1,'系统日志','2014-10-29 23:41:06','2015-12-17 10:13:57'),(31,7,'模块管理','membership.module','icon-org','views/membership/module','7,31',0,0,'','',4,1,'模块管理','2014-10-30 18:21:46','2015-12-17 10:13:57'),(32,0,'系统管理','sys','icon-settings4','views/sys/conf','32',1,0,'','',5,1,'系统管理','2014-11-11 20:20:57','2015-12-17 10:13:28'),(39,32,'系统配置','sys.conf','icon-settings2','views/sys/conf','32,39',0,0,' ',' ',2,1,'系统配置','2015-08-07 18:48:03','2015-12-17 10:13:57'),(40,0,'报表管理','report','icon-designer1','views/metadata/designer','40',1,0,'','',1,1,'报表管理','2015-12-13 19:45:36','2015-12-17 10:13:28'),(41,40,'参数配置','report.conf','icon-settings','views/metadata/conf','40,41',0,0,'','',3,1,'参数配置','2015-12-13 19:45:36','2015-12-17 10:13:28'),(42,0,'计划任务','schedule','icon-scheduled1','views/schedule/task','42',1,0,'','',2,1,'计划任务','2015-12-13 19:45:36','2015-12-17 10:13:28'),(54,42,'任务管理','schedule.task','icon-task2','views/schedule/task','42,54',0,0,' ',' ',1,1,'任备管理','2016-08-29 01:43:08','2016-08-29 01:43:12');
/*!40000 ALTER TABLE `ezrpt_member_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_member_permission`
--

DROP TABLE IF EXISTS `ezrpt_member_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_member_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统操作标识',
  `module_id` int(11) NOT NULL COMMENT '系统模块标识',
  `name` varchar(50) NOT NULL COMMENT '系统操作名称',
  `code` varchar(50) NOT NULL COMMENT '系统操作唯一代号',
  `sequence` int(11) NOT NULL COMMENT '系统操作的排序顺序',
  `comment` varchar(50) NOT NULL COMMENT '系统操作备注',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '系统操作记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统操作记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permisson_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_member_permission`
--

LOCK TABLES `ezrpt_member_permission` WRITE;
/*!40000 ALTER TABLE `ezrpt_member_permission` DISABLE KEYS */;
INSERT INTO `ezrpt_member_permission` VALUES (2,23,'删除用户','membership.user:delete',2,'删除','2014-10-30 00:25:52','2014-10-30 00:24:54'),(4,23,'编辑用户','membership.user:update',3,'编辑','2014-10-30 05:02:37','2014-10-30 05:01:39'),(5,23,'查看用户','membership.user:view',4,'查看','2014-10-30 05:08:23','2014-10-30 05:07:25'),(6,24,'添加角色','membership.role:add',5,'添加','2014-10-30 18:13:48','2014-10-30 18:12:52'),(7,24,'删除角色','membership.role:delete',4,'删除','2014-10-30 18:15:43','2014-10-30 18:14:47'),(8,24,'编辑角色','membership.role:update',3,'编辑','2014-10-30 18:16:07','2014-10-30 18:15:10'),(9,24,'查看角色','membership.role:view',2,'查看','2014-10-30 18:16:21','2014-10-30 18:15:25'),(10,25,'管理','membership.permission:*',1,'管理所有权限','2014-10-30 18:24:04','2014-10-30 18:23:08'),(14,31,'管理','membership.module:*',1,'管理系统模块','2014-10-30 18:28:43','2014-10-30 18:27:46'),(15,31,'编辑模块','membership.module.edit',2,'编辑','2014-10-30 18:29:17','2014-10-30 18:28:20'),(16,31,'删除模块','membership.module.remove',3,'删除','2014-10-30 18:29:38','2014-10-30 18:28:42'),(18,26,'查看日志','sys.event:view',1,'查看','2014-10-30 18:31:08','2014-10-30 18:30:11'),(20,23,'添加用户','membership.user:add',1,'添加','2014-10-31 02:29:22','2014-10-31 02:28:26'),(21,2,'查看','dashboard:view',1,'查看仪表盘图表','2014-11-11 19:01:11','2014-11-11 19:01:11'),(37,23,'管理','membership.user:*',1,'用户管理','2014-11-11 19:26:41','2014-11-11 19:26:41'),(38,24,'管理','membership.role:*',1,'角色管理','2014-11-11 19:27:49','2014-11-11 19:27:49'),(58,26,'日志管理','sys.event:*',2,'日志管理','2014-11-26 23:51:02','2014-11-26 23:51:04'),(59,3,'删除数据源','report.ds:delete',1,'删除数据源','2015-06-22 22:55:58','2015-06-22 22:55:58'),(60,3,'创建数据源','report.ds:add',2,'创建数据源','2015-06-22 22:56:18','2015-06-22 22:56:18'),(61,3,'修改数据源','report.ds:update',3,'修改数据源','2015-06-22 22:56:37','2015-06-22 22:56:37'),(62,3,'查看数据源','report.ds:view',4,'查看数据源','2015-06-22 22:57:36','2015-06-22 22:57:36'),(63,3,'管理数据源','report.ds:*',5,'管理数据源','2015-06-22 22:59:14','2015-06-22 22:59:14'),(64,41,'管理','report.conf:*',1,'管理报表参数配置','2015-06-22 23:01:44','2015-06-22 23:01:44'),(65,4,'管理','report.designer:*',2,'报表设计管理','2015-06-22 23:02:04','2015-06-22 23:02:04'),(66,4,'查看','report.designer:view',10,'查看','2015-12-17 05:24:08','2015-12-17 05:24:08'),(67,4,'增加','report.designer:add',10,'添加','2015-12-17 05:24:40','2015-12-17 05:24:52'),(69,4,'修改','report.designer:update',10,'编辑','2015-12-17 09:43:58','2015-12-17 09:43:58'),(71,39,'管理','sys.conf:*',10,'管理','2015-12-17 09:44:35','2015-12-17 09:44:35'),(72,42,'修改','schedule.task:update',10,'修改任务','2015-12-17 09:45:36','2015-12-17 09:45:43'),(73,42,'删除','schedule.task:delete',10,'删除任务','2015-12-17 09:46:00','2015-12-17 09:46:00'),(74,42,'查看','schedule.task:view',10,'查看任务','2015-12-17 09:46:19','2015-12-17 09:46:19'),(75,54,'管理','schedule.task:*',10,'管理所有任务','2015-12-17 09:46:50','2015-12-17 09:46:50'),(77,54,'增加','schedule.task:add',10,'add','2016-09-18 15:57:09','2016-09-18 15:57:09'),(78,4,'预览','report.designer:preview',10,'预览报表','2016-09-27 03:09:14','2016-09-27 03:09:14'),(79,4,'导出','report.designer:export',10,'导出excel,pdf等','2016-09-27 03:09:55','2016-09-27 03:09:55');
/*!40000 ALTER TABLE `ezrpt_member_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_member_role`
--

DROP TABLE IF EXISTS `ezrpt_member_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_member_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色标识',
  `modules` varchar(1000) NOT NULL COMMENT '系统角色所拥有的模块集合(module_id以英文逗号分隔)',
  `permissions` varchar(1000) NOT NULL COMMENT '系统角色所拥有的操作集合(permission_id以英文逗号分隔)',
  `name` varchar(50) NOT NULL COMMENT '系统角色名称',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '系统角色英语名',
  `is_system` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否为系统角色,1表示是，0表示否,默认为0',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '系统角色的状态,1表示启用,0表示禁用,默认为1,其他保留',
  `sequence` int(11) NOT NULL COMMENT '系统角色的排序顺序',
  `comment` varchar(50) NOT NULL COMMENT '系统角色备注',
  `create_user` varchar(64) NOT NULL COMMENT '创建用户',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '系统角色记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统角色记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`code`),
  UNIQUE KEY `uk_role_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_member_role`
--

LOCK TABLES `ezrpt_member_role` WRITE;
/*!40000 ALTER TABLE `ezrpt_member_role` DISABLE KEYS */;
INSERT INTO `ezrpt_member_role` VALUES (4,'39,38,42,41,40,23,24,25,26,3,7,32,31,4','76,75,67,66,77,64,65,74,73,72,59,60,61,62,63,69,37,20,2,4,5,38,9,8,7,6,10,14,15,16,71,70,18,58','超级管理员','superAdmin',1,1,1,'管理员','admin','2014-10-31 06:38:09','2015-12-17 17:48:00'),(22,'39,38,42,41,40,23,24,25,26,3,7,32,31,4','76,75,67,66,77,64,65,74,73,72,59,60,61,62,63,69,37,20,2,4,5,38,9,8,7,6,10,14,15,16,71,70,18,58','开发人员','developer',1,1,2,'开发人员','admin','2014-11-15 04:56:56','2015-12-17 17:47:45'),(23,'3,4,40,41,42,54','59,60,61,62,63,65,66,67,69,64,75,77,72,73,74','测试人员','test',1,1,10,'测试人员','admin','2015-12-17 13:35:50','2015-12-20 10:05:27'),(24,'40,4','66,78','support','support',0,1,10,'support','admin','2016-12-22 04:08:39','2016-12-22 04:08:39');
/*!40000 ALTER TABLE `ezrpt_member_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_member_user`
--

DROP TABLE IF EXISTS `ezrpt_member_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_member_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统用户标识',
  `roles` varchar(500) NOT NULL COMMENT '系统用户所属角色集合(role_id以英文逗号分隔)',
  `account` varchar(64) NOT NULL COMMENT '系统用户账号',
  `password` varchar(64) NOT NULL COMMENT '系统用户密码',
  `salt` varchar(50) NOT NULL COMMENT '加盐',
  `name` varchar(50) NOT NULL COMMENT '系统用户姓名',
  `email` varchar(64) NOT NULL COMMENT '系统用户电子邮箱',
  `telephone` varchar(36) NOT NULL COMMENT '系统用户用户电话号码,多个用英文逗号分开',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '系统用户的状态,1表示启用,0表示禁用,默认为1,其他保留',
  `comment` varchar(50) NOT NULL COMMENT '系统用户备注',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '系统用户记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统用户记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_account` (`account`),
  UNIQUE KEY `uk_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_member_user`
--

LOCK TABLES `ezrpt_member_user` WRITE;
/*!40000 ALTER TABLE `ezrpt_member_user` DISABLE KEYS */;
INSERT INTO `ezrpt_member_user` VALUES (1,'4','admin','436a5530ff7436c546dc2047d24fff46','c1d69267a3fd2e207408b68f8662cf27','管理员','14068728@qq.com','123456789',1,'sa','2015-01-05 09:38:50','2015-01-05 09:38:50'),(5,'23','test','c2b57a2b72ec4f289c8daa68aa7fb3a6','283facc4dc9896ddb303a736be9530ea','tester','tester@qq.com','12343423423',1,'1','2015-12-20 10:06:59','2015-08-14 02:04:00'),(6,'24','support','af7c1c236ce1bb9e1d3a186e02f2da71','e161f51a4b857845bf3cfb0822961ff3','test','1245322@163.com','12312412',1,'support','2016-12-22 04:10:11','2016-12-22 04:10:11');
/*!40000 ALTER TABLE `ezrpt_member_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_meta_category`
--

DROP TABLE IF EXISTS `ezrpt_meta_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_meta_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父分类',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `path` varchar(500) NOT NULL COMMENT '树型结构路径从根id到当前id的路径',
  `has_child` tinyint(1) NOT NULL COMMENT '是否为子类别1为是，0为否',
  `status` int(11) NOT NULL COMMENT '状态（1表示启用，0表示禁用，默认为0)',
  `sequence` int(11) NOT NULL COMMENT '节点在其父节点中的顺序',
  `comment` varchar(500) NOT NULL COMMENT '说明备注',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_parent_id_name` (`parent_id`,`name`),
  KEY `ix_path` (`path`(255))
) ENGINE=InnoDB AUTO_INCREMENT=744 DEFAULT CHARSET=utf8 COMMENT='报表类别表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_meta_category`
--

LOCK TABLES `ezrpt_meta_category` WRITE;
/*!40000 ALTER TABLE `ezrpt_meta_category` DISABLE KEYS */;
INSERT INTO `ezrpt_meta_category` VALUES (711,0,'中国空气(MySQL)','711',0,0,100,'','2015-01-29 09:54:49','2016-09-22 15:06:48'),(716,0,'中国天气(MySQL)','716',0,0,100,'','2015-02-10 07:20:31','2016-09-22 15:06:50');
/*!40000 ALTER TABLE `ezrpt_meta_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_meta_conf`
--

DROP TABLE IF EXISTS `ezrpt_meta_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_meta_conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置字典ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(50) NOT NULL COMMENT '配置key',
  `value` varchar(1000) NOT NULL COMMENT '配置值',
  `sequence` int(11) NOT NULL COMMENT '显示顺序',
  `comment` varchar(50) NOT NULL COMMENT '配置说明',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ezrpt_meta_conf_uk_pid_key` (`parent_id`,`key`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='报表元数据配置字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_meta_conf`
--

LOCK TABLES `ezrpt_meta_conf` WRITE;
/*!40000 ALTER TABLE `ezrpt_meta_conf` DISABLE KEYS */;
INSERT INTO `ezrpt_meta_conf` VALUES (1,33,'常见统计列','statColumn','statColumn',0,'统计列','2015-02-10 02:23:56','2015-02-10 02:23:56'),(5,1,'AQI指数','aqi','空气指数',100,'空气指数','2015-02-10 03:01:45','2015-02-10 03:01:45'),(6,1,'AQI指数范围','aqi_range','AQI指数范围',100,'AQI指数范围','2015-02-10 03:02:34','2015-02-10 03:02:34'),(7,1,'空气等级','quality','空气等级',100,'空气等级','2015-02-10 03:03:41','2015-02-10 03:03:41'),(15,33,'常见日期列','dateColumn','dateColumn',100,'','2015-02-10 02:23:56','2015-02-10 02:23:56'),(16,15,'dt日期','dt','日期',100,'','2015-02-10 03:17:18','2015-02-10 03:17:18'),(17,15,'date日期','date','日期',100,'日期列','2015-02-10 03:17:22','2015-02-10 03:17:22'),(18,1,'PM2.5细颗粒物','pm25','PM2.5细颗粒物',100,'PM2.5细颗粒物','2015-02-10 03:04:31','2015-02-10 03:04:31'),(19,1,'PM10可吸入颗粒物','pm10','PM10可吸入颗粒物',100,'PM10可吸入颗粒物','2015-02-10 03:05:05','2015-02-10 03:05:05'),(32,33,'常见维度列','dimColumn','dimColumn',0,'常见统计列','2015-02-10 02:23:56','2015-02-10 02:23:56'),(33,0,'报表常用元数据列','reportCommonMetaColum','reportCommonMetaColum',0,'报表常用元数据列','2015-02-10 02:23:56','2015-02-10 02:23:56'),(36,1,'二氧化硫','so2','二氧化硫(So2)',100,'二氧化硫','2015-02-10 03:05:57','2015-02-10 03:05:57'),(37,32,'城市','area','城市',100,'','2015-02-10 03:10:47','2015-02-10 03:10:47'),(38,32,'地区','region','地区',100,'华北，华南','2015-02-10 03:11:06','2015-02-10 03:11:06'),(39,32,'省(直辖市)','province','省(直辖市)',100,'','2015-02-10 03:11:41','2015-02-10 03:11:41'),(40,32,'城市','city','城市',100,'北京，上海','2015-02-10 03:12:01','2015-02-10 03:12:01'),(41,32,'城区','district','城区',100,'海淀区、朝阳区','2015-02-10 03:12:43','2015-02-10 03:12:43'),(42,32,'标题','title','标题',100,'','2015-02-10 03:13:30','2015-02-10 03:13:30'),(48,32,'标识','id','标识',100,'','2015-02-10 03:13:44','2015-02-10 03:13:44'),(49,32,'文本','text','文本',100,'','2015-02-10 03:14:22','2015-02-10 03:14:22'),(50,32,'首字母','capital','首字母',100,'首字母','2015-02-10 03:15:27','2015-02-10 03:15:27'),(52,33,'常见可选列','optionalColumn','optionalColumn',0,'','2015-02-10 02:23:56','2015-02-10 02:23:56'),(53,52,'二氧化氮','no2','二氧化氮(No2)',100,'','2015-02-10 03:20:21','2015-02-10 03:20:21'),(54,52,'臭氧','o3','臭氧(O3)',100,'','2015-02-10 03:20:55','2015-02-10 03:20:55'),(55,32,'代号','code','代号',100,'','2015-02-10 03:15:09','2015-02-10 03:15:09'),(56,1,'一氧化碳','co','一氧化碳(Co)',100,'一氧化碳','2015-02-10 03:06:26','2015-02-10 03:06:26'),(57,1,'二氧化氮','no2','二氧化氮(No2)',100,'二氧化氮','2015-02-10 03:06:48','2015-02-10 03:06:48'),(58,1,'臭氧','o3','臭氧(O3)',100,'臭氧','2015-02-10 03:07:21','2015-02-10 03:07:21'),(59,15,'天','day','天',100,'','2015-02-10 03:17:10','2015-02-10 03:17:10'),(60,15,'小时','hour','小时',100,'','2015-02-10 03:17:38','2015-02-10 03:17:38'),(61,15,'月度','month','月度',100,'','2015-02-10 03:17:57','2015-02-10 03:17:57'),(69,0,'数据源类型','dbType','dbType',10,'数据源类型','2016-09-20 05:57:36','2016-09-20 05:57:36'),(70,69,'MySQL','mysql','{\r\n	\"driverClass\":\"com.mysql.jdbc.Driver\",\r\n	\"queryerClass\":\"com.easytoolsoft.easyreport.engine.query.MySqlQueryer\",\r\n	\"jdbcUrl\":\"jdbc:mysql://${host}:${port}/${database}?characterEncoding=${encoding}\"\r\n}',10,'','2016-09-20 06:06:02','2016-09-20 06:06:02'),(71,69,'SQLServer','sqlserver','{\r\n	\"driverClass\":\"com.microsoft.sqlserver.jdbc.SQLServerDriver\",\r\n	\"queryerClass\":\"com.easytoolsoft.easyreport.engine.query.SqlServerQueryer\",\r\n	\"jdbcUrl\":\"jdbc:sqlserver://${host};databaseName=${database}\"\r\n}',10,'','2016-09-20 06:08:09','2016-09-20 06:08:09'),(72,0,'数据源连接池类型','dbPoolType','dbPoolType',10,'','2016-09-20 06:09:21','2016-09-20 06:09:21'),(73,72,'C3P0','c3p0','{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.C3p0DataSourcePool\",\r\n	\"options\":{\r\n		\"initialPoolSize\":3,\r\n		\"minPoolSize\":1,\r\n		\"maxPoolSize\":10,\r\n		\"maxStatements\":50,\r\n		\"maxIdleTime\":1000,\r\n		\"acquireIncrement\":3,\r\n		\"acquireRetryAttempts\":30,\r\n		\"idleConnectionTestPeriod\":60,\r\n		\"breakAfterAcquireFailure\":false,\r\n		\"testConnectionOnCheckout\":false\r\n	}\r\n}',10,'','2016-09-20 06:10:46','2016-09-20 06:10:46'),(74,72,'DBCP2','dbcp2','{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.DBCP2DataSourcePool\",\r\n	\"options\":{\r\n		\"initialSize\":3,\r\n		\"maxIdle\":20,\r\n		\"minIdle\":1,\r\n		\"logAbandoned\":true,\r\n		\"removeAbandoned\":true,\r\n		\"removeAbandonedTimeout\":180,\r\n		\"maxWait\":1000\r\n	}\r\n}',10,'','2016-09-20 06:11:03','2016-09-20 06:11:03'),(75,72,'Druid','druid','{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.DruidDataSourcePool\",\r\n	\"options\":{\r\n		\"initialSize\": 3,\r\n		\"maxActive\": 20,\r\n		\"minIdle\": 1,\r\n		\"maxWait\": 60000,\r\n		\"timeBetweenEvictionRunsMillis\": 60000,\r\n		\"minEvictableIdleTimeMillis\": 300000,\r\n		\"testWhileIdle\": true,\r\n		\"testOnBorrow\": false,\r\n		\"testOnReturn\": false,\r\n		\"maxOpenPreparedStatements\": 20,\r\n		\"removeAbandoned\": true,\r\n		\"removeAbandonedTimeout\": 1800,\r\n		\"logAbandoned\": true\r\n	}\r\n}',10,'','2016-09-20 06:11:18','2016-09-20 06:11:18'),(76,72,'无连接池','noDbPool','{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.NoDataSourcePool\",\r\n	\"options\":{}\r\n}',10,'','2016-09-20 06:14:32','2016-09-20 06:14:32');
/*!40000 ALTER TABLE `ezrpt_meta_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_meta_datasource`
--

DROP TABLE IF EXISTS `ezrpt_meta_datasource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_meta_datasource` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据源ID',
  `uid` varchar(128) NOT NULL COMMENT '数据源唯一ID,由调接口方传入',
  `name` varchar(50) NOT NULL COMMENT '数据源名称',
  `driver_class` varchar(100) NOT NULL COMMENT '数据源驱动类',
  `jdbc_url` varchar(500) NOT NULL COMMENT '数据源连接字符串(JDBC)',
  `user` varchar(50) NOT NULL COMMENT '数据源登录用户名',
  `password` varchar(100) NOT NULL COMMENT '数据源登录密码',
  `queryer_class` varchar(100) NOT NULL COMMENT '获取报表引擎查询器类名',
  `pool_class` varchar(100) NOT NULL COMMENT '报表引擎查询器使用的数据源连接池类名',
  `options` varchar(1000) NOT NULL COMMENT '数据源配置选项(JSON格式）',
  `comment` varchar(100) NOT NULL COMMENT '说明备注',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  KEY `AK_uk_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='数据源配置信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_meta_datasource`
--

LOCK TABLES `ezrpt_meta_datasource` WRITE;
/*!40000 ALTER TABLE `ezrpt_meta_datasource` DISABLE KEYS */;
INSERT INTO `ezrpt_meta_datasource` VALUES (49,'8b2d3b62-0c08-4d62-a666-8bb97fc9c222','中国天气(SQLServer)','com.microsoft.sqlserver.jdbc.SQLServerDriver','4A55B7F38EC68C1B8B36DE5271E92145734DAB64FAFD7EA6E0EE3F220B86C2A2B8626242F723F4902861FBBEB7CAE4E6D0DC8942383D054D3FA31922290FBBBE','1A59AA9D4C767DEA2CCE31CDB22C45C0','1A59AA9D4C767DEA2CCE31CDB22C45C0','com.easytoolsoft.easyreport.engine.query.SqlServerQueryer','com.easytoolsoft.easyreport.engine.dbpool.DBCP2DataSourcePool','{\"initialSize\":3,\"maxIdle\":20,\"minIdle\":1,\"logAbandoned\":true,\"removeAbandoned\":true,\"removeAbandonedTimeout\":180,\"maxWait\":1000}','','2015-01-27 06:32:32','2015-01-27 06:32:32'),(50,'6423b076-ce78-47fc-8c25-c005be2b85af','中国天气(MySQL)','com.mysql.jdbc.Driver','421C478BFCA5CFA368FC20F2727D56D5AAD15101FE2604D3559988BA389C83B1A16BD22864A216A986658E3E059FFF5C3467DA31237E8F3C8DC8B1ED60E257969C345C6A0815F682F7D27BFB79C0BDBF','72BE428C1D3F75E5B824804C1CD39B5D','BEBAC49CA6A51200182A84AA40ACEFA0','com.easytoolsoft.easyreport.engine.query.MySqlQueryer','com.easytoolsoft.easyreport.engine.dbpool.DruidDataSourcePool','{\"initialSize\":3,\"maxActive\":20,\"minIdle\":1,\"maxWait\":60000,\"timeBetweenEvictionRunsMillis\":60000,\"minEvictableIdleTimeMillis\":300000,\"testWhileIdle\":true,\"testOnBorrow\":false,\"testOnReturn\":false,\"maxOpenPreparedStatements\":20,\"removeAbandoned\":true,\"removeAbandonedTimeout\":1800,\"logAbandoned\":true}','','2015-01-29 09:54:32','2015-01-29 09:54:32');
/*!40000 ALTER TABLE `ezrpt_meta_datasource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_meta_report`
--

DROP TABLE IF EXISTS `ezrpt_meta_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_meta_report` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `uid` varchar(128) DEFAULT NULL COMMENT '报表唯一ID,由接口调用方传入',
  `category_id` int(11) NOT NULL COMMENT '报表分类id',
  `ds_id` int(11) NOT NULL COMMENT '数据源ID',
  `name` varchar(50) NOT NULL COMMENT '报表名称',
  `sql_text` text NOT NULL COMMENT '报表SQL语句',
  `meta_columns` text NOT NULL COMMENT '报表列集合元数据(JSON格式)',
  `query_params` text NOT NULL COMMENT '查询条件列属性集合(JSON格式)',
  `options` text NOT NULL COMMENT '报表配置选项(JSON格式)',
  `status` int(11) NOT NULL COMMENT '报表状态（1表示锁定，0表示编辑)',
  `sequence` int(11) NOT NULL COMMENT '报表节点在其父节点中的顺序',
  `comment` varchar(500) NOT NULL COMMENT '说明备注',
  `create_user` varchar(50) NOT NULL COMMENT '创建用户',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_id_name` (`category_id`,`name`),
  UNIQUE KEY `uk_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=739 DEFAULT CHARSET=utf8 COMMENT='报表信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_meta_report`
--

LOCK TABLES `ezrpt_meta_report` WRITE;
/*!40000 ALTER TABLE `ezrpt_meta_report` DISABLE KEYS */;
INSERT INTO `ezrpt_meta_report` VALUES (712,'bb7be838-31ae-41fe-ae63-4ae8efe5c138',711,50,'2014年北京每天空气指数','select * from fact_air_cn where area=\'北京市\' and year(dt)=\'2014\'','[{\"ordinal\":0,\"name\":\"dt\",\"text\":\"日期\",\"dataType\":\"DATE\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"aqi\",\"text\":\"空气指数\",\"dataType\":\"INT\",\"expression\":\"\",\"width\":11,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"aqi_range\",\"text\":\"AQI指数范围\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气等级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"pm25\",\"text\":\"PM2.5细颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"pm10\",\"text\":\"PM10可吸入颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"so2\",\"text\":\"二氧化硫(So2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"co\",\"text\":\"一氧化碳(Co)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"no2\",\"text\":\"二氧化氮(No2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"o3\",\"text\":\"臭氧(O3)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false}]','','{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":7}',1,100,'','admin','2015-01-29 09:59:14','2016-09-26 10:40:37'),(713,'3180eafe-e4a2-4b5c-828e-e054043f6e87',711,50,'30天内城市空气指数','select * from fact_air_cn where area in (\'${area}\') and dt>=\'${startTime}\' and dt <\'${endTime}\'','[{\"ordinal\":0,\"name\":\"dt\",\"text\":\"日期\",\"dataType\":\"DATE\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"area\",\"text\":\"城市\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"aqi\",\"text\":\"空气指数\",\"dataType\":\"INT\",\"expression\":\"\",\"width\":11,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"aqi_range\",\"text\":\"AQI指数范围\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气等级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"pm25\",\"text\":\"PM2.5细颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"pm10\",\"text\":\"PM10可吸入颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"so2\",\"text\":\"二氧化硫(So2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"co\",\"text\":\"一氧化碳(Co)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"no2\",\"text\":\"二氧化氮(No2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":true,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"o3\",\"text\":\"臭氧(O3)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":true,\"footings\":false,\"displayInMail\":false}]','[{\"name\":\"area\",\"text\":\"城市\",\"defaultValue\":\"北京市\",\"defaultText\":\"北京市\",\"dataType\":\"string\",\"width\":\"100\",\"required\":true,\"formElement\":\"selectMul\",\"dataSource\":\"sql\",\"content\":\"select distinct area as name,area as text from fact_air_cn\",\"autoComplete\":false}]','{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":30}',1,100,'','admin','2015-01-29 10:04:46','2016-09-26 10:40:37'),(714,'7608bb39-fa1b-4aff-bd15-b31f318e7025',711,50,'2014年主要城市空气质量','SELECT\n	area,\n	quality,\n	count(*) total,\n    count(*) / 365 as rating\nFROM\n	fact_air_cn\nWHERE\n	YEAR (dt) = \'2014\'\nGROUP BY\n	area,\n	quality','[{\"ordinal\":0,\"name\":\"area\",\"text\":\"城市/地区\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气质量\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"2\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"total\",\"text\":\"天数\",\"dataType\":\"BIGINT\",\"expression\":\"\",\"width\":21,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"rating\",\"text\":\"占比\",\"dataType\":\"DECIMAL\",\"expression\":\"\",\"width\":26,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":true,\"optional\":false,\"footings\":false,\"displayInMail\":false}]','','{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":7}',1,100,'','admin','2015-01-29 10:15:19','2016-09-26 10:40:38'),(715,'c8258587-eb6f-4a2a-8caf-0ae236b77807',711,50,'2014年主要城市空气质量好坏表','SELECT\n	area,\n	quality,\n	sum(total) total,\n    sum(total) / 365 as rating\nFROM\n	(\n		SELECT\n			area,\n			CASE quality\n		WHEN \'优\' THEN\n			\'好\'\n		WHEN \'良\' THEN\n			\'好\'\n		ELSE\n			\'差\'\n		END AS quality,\n		count(*) total\n	FROM\n		fact_air_cn\n	WHERE\n		YEAR (dt) = \'2014\'\n	GROUP BY\n		area,\n		quality\n	) t\nGROUP BY\n	t.area,\n	t.quality','[{\"ordinal\":0,\"name\":\"area\",\"text\":\"城市/地区\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"质量\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":1,\"decimals\":\"0\",\"type\":\"2\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"total\",\"text\":\"天数\",\"dataType\":\"DECIMAL\",\"expression\":\"\",\"width\":43,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"rating\",\"text\":\"占比\",\"dataType\":\"DECIMAL\",\"expression\":\"\",\"width\":48,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":true,\"optional\":false,\"footings\":false,\"displayInMail\":false}]','','{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":7}',1,100,'','admin','2015-01-29 10:31:43','2016-09-26 10:40:39'),(717,'5beca118-e15e-4c3b-91f4-5330dc9a62dd',716,50,'2014年北京每天天气','select * from fact_weather_cn where year(dt)=2014 and area=\'北京\'','[{\"ordinal\":0,\"name\":\"dt\",\"text\":\"日期\",\"dataType\":\"DATE\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"weather\",\"text\":\"天气\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":50,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"min_temp\",\"text\":\"最低温度\",\"dataType\":\"INT\",\"expression\":\"\",\"width\":11,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"max_temp\",\"text\":\"最高温度\",\"dataType\":\"INT\",\"expression\":\"\",\"width\":11,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"wind_level\",\"text\":\"风级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":255,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"wind_direct\",\"text\":\"风向\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":255,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false}]','','{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":7}',1,1,'','admin','2015-02-10 07:37:11','2016-09-26 10:40:40'),(718,'b6e04111-75b7-4c6b-9a30-c3d7d0b846c4',711,50,'2014年主要城市(可选)空气质量','SELECT\n	area,\n	CASE quality\n		WHEN \'优\' THEN \'1-优\'\n		WHEN \'良\' THEN \'2-良\'\n		WHEN \'轻度污染\' THEN \'3-轻度污染\'\n		WHEN \'中度污染\' THEN \'4-中度污染\'\n		WHEN \'重度污染\' THEN \'5-重度污染\'\n		WHEN \'严重污染\' THEN \'6-严重污染\'	\n	END AS quality,\n	count(*) total,\n    count(*) / 365 as rating,\n    avg(aqi) avg_aqi\nFROM\n	fact_air_cn\nWHERE\n	YEAR (dt) = \'2014\' \n    AND area in (\'${area}\')\nGROUP BY\n	area,\n	quality\norder by total desc','[{\"ordinal\":0,\"name\":\"area\",\"text\":\"城市\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"2\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气等级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":6,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"3\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"total\",\"text\":\"天数\",\"dataType\":\"BIGINT\",\"expression\":\"\",\"width\":21,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"footings\":false,\"optional\":false,\"percent\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"rating\",\"text\":\"占比\",\"dataType\":\"DECIMAL\",\"expression\":\"\",\"width\":26,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"footings\":false,\"optional\":false,\"percent\":true,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"avg_aqi\",\"text\":\"AQI均值\",\"dataType\":\"DECIMAL\",\"expression\":\"\",\"width\":16,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"displayInMail\":false,\"footings\":false}]','[{\"name\":\"area\",\"text\":\"城市\",\"defaultValue\":\"北京市\",\"defaultText\":\"北京市\",\"dataType\":\"string\",\"width\":\"100\",\"formElement\":\"selectMul\",\"dataSource\":\"sql\",\"content\":\"select distinct area as name,area as text from fact_air_cn\",\"required\":false,\"autoComplete\":false}]','{\"layout\":1,\"statColumnLayout\":1,\"dataRange\":7}',1,100,'','admin','2015-02-11 10:58:46','2016-09-26 10:40:40'),(719,'a6233a37-18b2-4ec9-8ca1-90e8fe3ffed1',711,50,'2014年分地区城市空气质量','SELECT\n	t1.region,\n	t1.province,\n	t2.area,\n	CASE t2.quality\n		WHEN \'优\' THEN \'1-优\'\n		WHEN \'良\' THEN \'2-良\'\n		WHEN \'轻度污染\' THEN \'3-轻度污染\'\n		WHEN \'中度污染\' THEN \'4-中度污染\'\n		WHEN \'重度污染\' THEN \'5-重度污染\'\n		WHEN \'严重污染\' THEN \'6-严重污染\'	\n	END AS quality,\n	count(*) total,\n	count(*) / 365 AS rating\nFROM\n	(\n		SELECT DISTINCT\n			t1.region,\n			t1.province,\n			t1.city\n		FROM\n			dim_area_cn t1\n	) t1\nINNER JOIN fact_air_cn t2 ON t1.city = t2.area\nWHERE\n	YEAR (t2.dt) = \'2014\'\n    AND t1.region like \'%${region}%\'\n	AND t1.province  like \'%${province}%\'\n	AND t2.area  like \'%${area}%\'\nGROUP BY\n	t1.region,\n	t1.province,\n	t2.area,\n	t2.quality','[{\"ordinal\":0,\"name\":\"region\",\"text\":\"地区\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"province\",\"text\":\"省(直辖市)\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":50,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"area\",\"text\":\"城市\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气等级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"2\",\"sortType\":\"1\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"total\",\"text\":\"天数\",\"dataType\":\"BIGINT\",\"expression\":\"\",\"width\":21,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"rating\",\"text\":\"占比\",\"dataType\":\"DECIMAL\",\"expression\":\"\",\"width\":26,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":true,\"optional\":false,\"footings\":false,\"displayInMail\":false}]','[{\"name\":\"region\",\"text\":\"地区\",\"defaultValue\":\"%\",\"defaultText\":\"全部\",\"dataType\":\"string\",\"width\":\"100\",\"formElement\":\"select\",\"dataSource\":\"sql\",\"content\":\"select distinct region as name,region as text from dim_area_cn\",\"required\":false,\"autoComplete\":false},{\"name\":\"province\",\"text\":\"省(市)\",\"defaultValue\":\"%\",\"defaultText\":\"全部\",\"dataType\":\"string\",\"width\":\"100\",\"formElement\":\"select\",\"dataSource\":\"sql\",\"content\":\"select distinct province as name,province as text from dim_area_cn\",\"required\":false,\"autoComplete\":false},{\"name\":\"area\",\"text\":\"城市\",\"defaultValue\":\"%\",\"defaultText\":\"全部\",\"dataType\":\"string\",\"width\":\"100\",\"formElement\":\"selectMul\",\"dataSource\":\"sql\",\"content\":\"select distinct area as name,area as text from fact_air_cn\",\"required\":false,\"autoComplete\":false}]','{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":7}',1,100,'','admin','2015-02-13 04:15:23','2016-09-26 10:40:41'),(720,'3b6d84ef-e6d7-4644-a145-e99b578426bf',711,50,'2014主要城市(可选)每月空气质量','SELECT\n    MONTH(dt) month,\n	area,\n	CASE quality\n		WHEN \'优\' THEN \'1-优\'\n		WHEN \'良\' THEN \'2-良\'\n		WHEN \'轻度污染\' THEN \'3-轻度污染\'\n		WHEN \'中度污染\' THEN \'4-中度污染\'\n		WHEN \'重度污染\' THEN \'5-重度污染\'\n		WHEN \'严重污染\' THEN \'6-严重污染\'	\n	END AS quality,\n	count(*) total,\n    count(*) / 365 as rating\nFROM\n	fact_air_cn\nWHERE\n	YEAR (dt) = \'2014\' \n    AND area in (\'${area}\')\nGROUP BY\n    MONTH(dt),\n	area,\n	quality\norder by total desc','[{\"ordinal\":0,\"name\":\"month\",\"text\":\"月度\",\"dataType\":\"INT\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":2,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"1\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"displayInMail\":false,\"footings\":false},{\"ordinal\":0,\"name\":\"area\",\"text\":\"城市\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"displayInMail\":false,\"footings\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气等级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":6,\"decimals\":\"0\",\"type\":\"2\",\"sortType\":\"1\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"displayInMail\":false,\"footings\":false},{\"ordinal\":0,\"name\":\"total\",\"text\":\"天数\",\"dataType\":\"BIGINT\",\"expression\":\"\",\"width\":21,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"displayInMail\":false,\"footings\":false},{\"ordinal\":0,\"name\":\"rating\",\"text\":\"占比\",\"dataType\":\"DECIMAL\",\"expression\":\"\",\"width\":26,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":true,\"optional\":false,\"displayInMail\":false,\"footings\":false}]','[{\"name\":\"area\",\"text\":\"城市\",\"defaultValue\":\"北京市\",\"defaultText\":\"北京市\",\"dataType\":\"string\",\"width\":\"100\",\"formElement\":\"selectMul\",\"dataSource\":\"sql\",\"content\":\"select distinct area as name,area as text from fact_air_cn\",\"required\":false,\"autoComplete\":false}]','{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":7}',1,100,'','admin','2015-02-13 10:26:33','2016-09-26 10:40:42');
/*!40000 ALTER TABLE `ezrpt_meta_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_meta_report_history`
--

DROP TABLE IF EXISTS `ezrpt_meta_report_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_meta_report_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表历史记录id',
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `uid` varchar(128) NOT NULL,
  `category_id` int(11) NOT NULL COMMENT '报表分类id',
  `ds_id` int(11) NOT NULL COMMENT '数据源ID',
  `name` varchar(50) NOT NULL COMMENT '报表名称',
  `sql_text` text NOT NULL COMMENT '报表SQL语句',
  `meta_columns` text NOT NULL COMMENT '报表列集合元数据(JSON格式)',
  `query_params` text NOT NULL COMMENT '查询条件列属性集合(JSON格式)',
  `options` text NOT NULL COMMENT '报表配置选项(JSON格式)',
  `status` int(11) NOT NULL COMMENT '报表状态（1表示锁定，0表示编辑)',
  `sequence` int(11) NOT NULL COMMENT '报表节点在其父节点中的顺序',
  `comment` varchar(500) NOT NULL COMMENT '说明备注',
  `author` varchar(50) NOT NULL COMMENT '创建用户',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_meta_report_history`
--

LOCK TABLES `ezrpt_meta_report_history` WRITE;
/*!40000 ALTER TABLE `ezrpt_meta_report_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `ezrpt_meta_report_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_schedule_task`
--

DROP TABLE IF EXISTS `ezrpt_schedule_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_schedule_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表任务ID',
  `report_ids` varchar(1024) NOT NULL COMMENT '报表ID列表',
  `cron_expr` varchar(100) NOT NULL COMMENT '报表任务调度crontab表达式',
  `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '任务类型,1为邮件任务,2为手机短信任务,其他保留.默认为1',
  `options` text NOT NULL COMMENT '配置选项(JSON格式)',
  `template` text NOT NULL COMMENT '任务内容模板',
  `comment` varchar(100) NOT NULL COMMENT '报表任务说明',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '报表任务记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '报表任务记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表任务信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_schedule_task`
--

LOCK TABLES `ezrpt_schedule_task` WRITE;
/*!40000 ALTER TABLE `ezrpt_schedule_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `ezrpt_schedule_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_sys_conf`
--

DROP TABLE IF EXISTS `ezrpt_sys_conf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_sys_conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置字典ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(50) NOT NULL COMMENT '配置key',
  `value` varchar(1000) NOT NULL COMMENT '配置值',
  `sequence` int(11) NOT NULL COMMENT '显示顺序',
  `comment` varchar(50) NOT NULL COMMENT '配置说明',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ezrpt_sys_conf_uk_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_sys_conf`
--

LOCK TABLES `ezrpt_sys_conf` WRITE;
/*!40000 ALTER TABLE `ezrpt_sys_conf` DISABLE KEYS */;
/*!40000 ALTER TABLE `ezrpt_sys_conf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ezrpt_sys_event`
--

DROP TABLE IF EXISTS `ezrpt_sys_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ezrpt_sys_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志标识',
  `source` varchar(100) NOT NULL COMMENT '日志来源',
  `user_id` int(11) NOT NULL COMMENT '操作用户id',
  `account` varchar(50) NOT NULL COMMENT '操作用户账号',
  `level` varchar(10) NOT NULL COMMENT '日志级别',
  `message` text NOT NULL COMMENT '日志信息',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `gmt_created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '日志发生的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ezrpt_sys_event`
--

LOCK TABLES `ezrpt_sys_event` WRITE;
/*!40000 ALTER TABLE `ezrpt_sys_event` DISABLE KEYS */;
INSERT INTO `ezrpt_sys_event` VALUES (1,'authenticate',-1,'admin','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 03:41:14'),(2,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 03:41:17'),(3,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 03:41:18'),(4,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:41:18'),(5,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),716;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:41:21'),(6,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:41:23'),(7,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 03:41:32'),(8,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 03:41:32'),(9,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbPoolType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 03:41:32'),(10,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:testConnection',1,'admin','INFO','name:测试数据源;params:jdbc:mysql://localhost:3307/easyreport2?characterEncoding=UTF-8,data123,data;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/testConnection','2016-12-22 03:43:08'),(11,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:edit',1,'admin','INFO','name:编辑数据源;params:DataSource(id=50, uid=null, name=中国天气(MySQL), driverClass=com.mysql.jdbc.Driver, jdbcUrl=421C478BFCA5CFA368FC20F2727D56D54F1CD42F6901C41D5C10F0E82F3C72D601E5A699D7F4EF471BE762EE564DB5789A23890986D85BF85BF065FA18F01EF9, user=72BE428C1D3F75E5B824804C1CD39B5D, password=BEBAC49CA6A51200182A84AA40ACEFA0, queryerClass=com.easytoolsoft.easyreport.engine.query.MySqlQueryer, poolClass=com.easytoolsoft.easyreport.engine.dbpool.DruidDataSourcePool, options={\"initialSize\":3,\"maxActive\":20,\"minIdle\":1,\"maxWait\":60000,\"timeBetweenEvictionRunsMillis\":60000,\"minEvictableIdleTimeMillis\":300000,\"testWhileIdle\":true,\"testOnBorrow\":false,\"testOnReturn\":false,\"maxOpenPreparedStatements\":20,\"removeAbandoned\":true,\"removeAbandonedTimeout\":1800,\"logAbandoned\":true}, comment=, gmtCreated=null, gmtModified=null);desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/edit','2016-12-22 03:43:14'),(12,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 03:43:14'),(13,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:3b6d84ef-e6d7-4644-a145-e99b578426bf;desc:;detail:','http://127.0.0.1:8080/report/uid/3b6d84ef-e6d7-4644-a145-e99b578426bf','2016-12-22 03:43:24'),(14,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:chart,3b6d84ef-e6d7-4644-a145-e99b578426bf,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@101a0ef;desc:;detail:','http://127.0.0.1:8080/report/chart/uid/3b6d84ef-e6d7-4644-a145-e99b578426bf','2016-12-22 03:43:26'),(15,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:table,3b6d84ef-e6d7-4644-a145-e99b578426bf,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@59eef2;desc:;detail:','http://127.0.0.1:8080/report/table/uid/3b6d84ef-e6d7-4644-a145-e99b578426bf','2016-12-22 03:43:26'),(16,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 03:44:13'),(17,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbPoolType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 03:44:13'),(18,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 03:44:13'),(19,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getTableData',1,'admin','INFO','name:获取表格报表JSON格式数据;params:3b6d84ef-e6d7-4644-a145-e99b578426bf,org.apache.shiro.web.servlet.ShiroHttpServletRequest@16be2cd;desc:;detail:','http://127.0.0.1:8080/report/table/getData.json','2016-12-22 03:45:26'),(20,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getChartData',1,'admin','INFO','name:获取图表报表JSON格式数据;params:3b6d84ef-e6d7-4644-a145-e99b578426bf,org.apache.shiro.web.servlet.ShiroHttpServletRequest@17c3938;desc:;detail:','http://127.0.0.1:8080/report/chart/getData.json','2016-12-22 03:45:26'),(21,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:testConnection',1,'admin','INFO','name:测试数据源;params:jdbc:mysql://localhost:3307/easyreport2?characterEncoding=UTF-8,data123,data;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/testConnection','2016-12-22 03:45:44'),(22,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:edit',1,'admin','INFO','name:编辑数据源;params:DataSource(id=50, uid=null, name=中国天气(MySQL), driverClass=com.mysql.jdbc.Driver, jdbcUrl=421C478BFCA5CFA368FC20F2727D56D54F1CD42F6901C41D5C10F0E82F3C72D601E5A699D7F4EF471BE762EE564DB5789A23890986D85BF85BF065FA18F01EF9, user=72BE428C1D3F75E5B824804C1CD39B5D, password=BEBAC49CA6A51200182A84AA40ACEFA0, queryerClass=com.easytoolsoft.easyreport.engine.query.MySqlQueryer, poolClass=com.easytoolsoft.easyreport.engine.dbpool.DruidDataSourcePool, options={\"initialSize\":3,\"maxActive\":20,\"minIdle\":1,\"maxWait\":60000,\"timeBetweenEvictionRunsMillis\":60000,\"minEvictableIdleTimeMillis\":300000,\"testWhileIdle\":true,\"testOnBorrow\":false,\"testOnReturn\":false,\"maxOpenPreparedStatements\":20,\"removeAbandoned\":true,\"removeAbandonedTimeout\":1800,\"logAbandoned\":true}, comment=, gmtCreated=null, gmtModified=null);desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/edit','2016-12-22 03:45:48'),(23,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 03:45:48'),(24,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:edit',1,'admin','INFO','name:编辑数据源;params:DataSource(id=49, uid=null, name=中国天气(SQLServer), driverClass=com.microsoft.sqlserver.jdbc.SQLServerDriver, jdbcUrl=4A55B7F38EC68C1B8B36DE5271E92145734DAB64FAFD7EA6E0EE3F220B86C2A2B8626242F723F4902861FBBEB7CAE4E6D0DC8942383D054D3FA31922290FBBBE, user=1A59AA9D4C767DEA2CCE31CDB22C45C0, password=1A59AA9D4C767DEA2CCE31CDB22C45C0, queryerClass=com.easytoolsoft.easyreport.engine.query.SqlServerQueryer, poolClass=com.easytoolsoft.easyreport.engine.dbpool.DBCP2DataSourcePool, options={\"initialSize\":3,\"maxIdle\":20,\"minIdle\":1,\"logAbandoned\":true,\"removeAbandoned\":true,\"removeAbandonedTimeout\":180,\"maxWait\":1000}, comment=, gmtCreated=null, gmtModified=null);desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/edit','2016-12-22 03:46:34'),(25,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 03:46:34'),(26,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138;desc:;detail:','http://127.0.0.1:8080/report/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 03:46:43'),(27,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:table,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@4773c6;desc:;detail:','http://127.0.0.1:8080/report/table/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 03:46:44'),(28,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:chart,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@16a05e5;desc:;detail:','http://127.0.0.1:8080/report/chart/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 03:46:44'),(29,'authenticate',-1,'admin','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 03:48:29'),(30,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 03:48:30'),(31,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 03:48:31'),(32,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:48:31'),(33,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getTableData',1,'admin','INFO','name:获取表格报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@fa38ae;desc:;detail:','http://127.0.0.1:8080/report/table/getData.json','2016-12-22 03:48:44'),(34,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getChartData',1,'admin','INFO','name:获取图表报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@abe98f;desc:;detail:','http://127.0.0.1:8080/report/chart/getData.json','2016-12-22 03:48:44'),(35,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 03:49:18'),(36,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 03:49:18'),(37,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbPoolType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 03:49:18'),(38,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:testConnection',1,'admin','INFO','name:测试数据源;params:jdbc:mysql://localhost:3307/easyreport2?characterEncoding=UTF-8,,data;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/testConnection','2016-12-22 03:49:27'),(39,'authenticate',-1,'admin','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 03:52:41'),(40,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 03:52:43'),(41,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 03:52:43'),(42,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:52:44'),(43,'authenticate',-1,'admin','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 03:55:21'),(44,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 03:55:22'),(45,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 03:55:23'),(46,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:55:23'),(47,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),716;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:55:28'),(48,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 03:55:31'),(49,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138;desc:;detail:','http://127.0.0.1:8080/report/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 03:55:46'),(50,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:chart,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@1ec20f1;desc:;detail:','http://127.0.0.1:8080/report/chart/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 03:55:47'),(51,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:table,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@5ea9fc;desc:;detail:','http://127.0.0.1:8080/report/table/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 03:55:47'),(52,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getChartData',1,'admin','INFO','name:获取图表报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@3e2665;desc:;detail:','http://127.0.0.1:8080/report/chart/getData.json','2016-12-22 03:55:48'),(53,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getTableData',1,'admin','INFO','name:获取表格报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@1089da1;desc:;detail:','http://127.0.0.1:8080/report/table/getData.json','2016-12-22 03:55:48'),(54,'authenticate',-1,'admin','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 04:00:19'),(55,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 04:00:20'),(56,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 04:00:21'),(57,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 04:00:21'),(58,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 04:01:20'),(59,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 04:01:21'),(60,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 04:01:21'),(61,'authenticate',-1,'admin','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 04:06:05'),(62,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 04:06:08'),(63,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 04:06:08'),(64,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 04:06:09'),(65,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138;desc:;detail:','http://127.0.0.1:8080/report/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:06:25'),(66,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:table,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@5f93dd;desc:;detail:','http://127.0.0.1:8080/report/table/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:06:26'),(67,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:chart,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@91aa84;desc:;detail:','http://127.0.0.1:8080/report/chart/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:06:26'),(68,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getChartData',1,'admin','INFO','name:获取图表报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@e1c2d8;desc:;detail:','http://127.0.0.1:8080/report/chart/getData.json','2016-12-22 04:06:26'),(69,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getTableData',1,'admin','INFO','name:获取表格报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@166fd10;desc:;detail:','http://127.0.0.1:8080/report/table/getData.json','2016-12-22 04:06:26'),(70,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 04:07:06'),(71,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 04:07:06'),(72,'com.easytoolsoft.easyreport.web.controller.metadata.ConfController:getConfItems',1,'admin','INFO','name:获取指定父key下的所有配置项;params:dbPoolType;desc:;detail:','http://127.0.0.1:8080/rest/metadata/conf/getConfItems','2016-12-22 04:07:06'),(73,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:testConnection',1,'admin','INFO','name:测试数据源;params:jdbc:mysql://localhost:3307/china_weather_air?characterEncoding=UTF-8,data123,data;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/testConnection','2016-12-22 04:07:39'),(74,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:list',1,'admin','INFO','name:分页获取数据源列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/list','2016-12-22 04:07:42'),(75,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138;desc:;detail:','http://127.0.0.1:8080/report/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:07:47'),(76,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:table,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@18cf1b3;desc:;detail:','http://127.0.0.1:8080/report/table/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:07:48'),(77,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',1,'admin','INFO','name:预览报表;params:chart,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@cb51c9;desc:;detail:','http://127.0.0.1:8080/report/chart/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:07:48'),(78,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getChartData',1,'admin','INFO','name:获取图表报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@1a96721;desc:;detail:','http://127.0.0.1:8080/report/chart/getData.json','2016-12-22 04:07:48'),(79,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getTableData',1,'admin','INFO','name:获取表格报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@c000f2;desc:;detail:','http://127.0.0.1:8080/report/table/getData.json','2016-12-22 04:07:48'),(80,'com.easytoolsoft.easyreport.web.controller.membership.UserController:list',1,'admin','INFO','name:分页获取用户列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/membership/user/list','2016-12-22 04:08:10'),(81,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:getRoleList',1,'admin','INFO','name:获取当前的角色列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015);desc:;detail:','http://127.0.0.1:8080/rest/membership/role/getRoleList','2016-12-22 04:08:10'),(82,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:list',1,'admin','INFO','name:分页获取角色列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/membership/role/list','2016-12-22 04:08:16'),(83,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:add',1,'admin','INFO','name:增加角色;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),Role(id=24, modules=, permissions=, name=support, code=support, isSystem=0, status=1, sequence=10, comment=support, createUser=admin, gmtCreated=Thu Dec 22 12:08:39 CST 2016, gmtModified=Thu Dec 22 12:08:39 CST 2016);desc:;detail:','http://127.0.0.1:8080/rest/membership/role/add','2016-12-22 04:08:39'),(84,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:list',1,'admin','INFO','name:分页获取角色列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/membership/role/list','2016-12-22 04:08:40'),(85,'authenticate',-1,'admin','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 04:09:29'),(86,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',1,'admin','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 04:09:30'),(87,'com.easytoolsoft.easyreport.web.controller.metadata.DataSourceController:listAll',1,'admin','INFO','name:获取所有数据源;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/ds/listAll','2016-12-22 04:09:31'),(88,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',1,'admin','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),711;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 04:09:31'),(89,'com.easytoolsoft.easyreport.web.controller.membership.UserController:list',1,'admin','INFO','name:分页获取用户列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/membership/user/list','2016-12-22 04:09:33'),(90,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:getRoleList',1,'admin','INFO','name:获取当前的角色列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015);desc:;detail:','http://127.0.0.1:8080/rest/membership/role/getRoleList','2016-12-22 04:09:33'),(91,'com.easytoolsoft.easyreport.web.controller.membership.UserController:add',1,'admin','INFO','name:新增用户;params:User(id=6, roles=24, account=support, password=af7c1c236ce1bb9e1d3a186e02f2da71, salt=e161f51a4b857845bf3cfb0822961ff3, name=test, email=1245322@163.com, telephone=12312412, status=1, comment=support, gmtCreated=Thu Dec 22 12:10:11 CST 2016, gmtModified=Thu Dec 22 12:10:11 CST 2016);desc:;detail:','http://127.0.0.1:8080/rest/membership/user/add','2016-12-22 04:10:11'),(92,'com.easytoolsoft.easyreport.web.controller.membership.UserController:list',1,'admin','INFO','name:分页获取用户列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/membership/user/list','2016-12-22 04:10:11'),(93,'com.easytoolsoft.easyreport.web.controller.membership.ModuleController:getChildModules',1,'admin','INFO','name:获取子模块树型列表;params:;desc:;detail:','http://127.0.0.1:8080/rest/membership/module/getChildModules','2016-12-22 04:10:14'),(94,'com.easytoolsoft.easyreport.web.controller.membership.PermissionController:list',1,'admin','INFO','name:获取权限列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),;desc:;detail:','http://127.0.0.1:8080/rest/membership/permission/list','2016-12-22 04:10:14'),(95,'com.easytoolsoft.easyreport.web.controller.membership.ModuleController:getChildModules',1,'admin','INFO','name:获取子模块树型列表;params:7;desc:;detail:','http://127.0.0.1:8080/rest/membership/module/getChildModules','2016-12-22 04:10:16'),(96,'com.easytoolsoft.easyreport.web.controller.membership.ModuleController:getChildModules',1,'admin','INFO','name:获取子模块树型列表;params:25;desc:;detail:','http://127.0.0.1:8080/rest/membership/module/getChildModules','2016-12-22 04:10:19'),(97,'com.easytoolsoft.easyreport.web.controller.membership.PermissionController:list',1,'admin','INFO','name:获取权限列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),25;desc:;detail:','http://127.0.0.1:8080/rest/membership/permission/list','2016-12-22 04:10:19'),(98,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:list',1,'admin','INFO','name:分页获取角色列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),DataGridPager(page=1, rows=50, sort=id, order=desc),,;desc:;detail:','http://127.0.0.1:8080/rest/membership/role/list','2016-12-22 04:10:29'),(99,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:listPermissionTree',1,'admin','INFO','name:获取当前用户所拥有的权限列表;params:User(id=1, roles=4, account=admin, password=436a5530ff7436c546dc2047d24fff46, salt=c1d69267a3fd2e207408b68f8662cf27, name=管理员, email=14068728@qq.com, telephone=123456789, status=1, comment=sa, gmtCreated=Mon Jan 05 17:38:50 CST 2015, gmtModified=Mon Jan 05 17:38:50 CST 2015),24;desc:;detail:','http://127.0.0.1:8080/rest/membership/role/listPermissionTree','2016-12-22 04:10:33'),(100,'com.easytoolsoft.easyreport.web.controller.membership.RoleController:authorize',1,'admin','INFO','name:给角色授权;params:Role(id=24, modules=40,4, permissions=66,78, name=null, code=null, isSystem=null, status=null, sequence=null, comment=null, createUser=null, gmtCreated=null, gmtModified=null);desc:;detail:','http://127.0.0.1:8080/rest/membership/role/authorize','2016-12-22 04:10:50'),(101,'authenticate',-1,'support','INFO','登录成功!','http://127.0.0.1:8080/membership/authenticate','2016-12-22 04:11:05'),(102,'com.easytoolsoft.easyreport.web.controller.metadata.CategoryController:getCategoryTree',6,'support','INFO','name:获取报表分类树;params:;desc:;detail:','http://127.0.0.1:8080/rest/metadata/category/getCategoryTree','2016-12-22 04:11:07'),(103,'com.easytoolsoft.easyreport.web.controller.metadata.ReportController:list',6,'support','INFO','name:分页获取报表列表;params:DataGridPager(page=1, rows=50, sort=id, order=desc),-1;desc:;detail:','http://127.0.0.1:8080/rest/metadata/report/list','2016-12-22 04:11:08'),(104,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',6,'support','INFO','name:预览报表;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138;desc:;detail:','http://127.0.0.1:8080/report/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:11:19'),(105,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',6,'support','INFO','name:预览报表;params:table,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@1696fad;desc:;detail:','http://127.0.0.1:8080/report/table/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:11:20'),(106,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:preview',6,'support','INFO','name:预览报表;params:chart,bb7be838-31ae-41fe-ae63-4ae8efe5c138,content,,,org.apache.shiro.web.servlet.ShiroHttpServletRequest@21f590;desc:;detail:','http://127.0.0.1:8080/report/chart/uid/bb7be838-31ae-41fe-ae63-4ae8efe5c138','2016-12-22 04:11:20'),(107,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getTableData',6,'support','INFO','name:获取表格报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@1a4b235;desc:;detail:','http://127.0.0.1:8080/report/table/getData.json','2016-12-22 04:11:20'),(108,'com.easytoolsoft.easyreport.web.controller.report.PreviewController:getChartData',6,'support','INFO','name:获取图表报表JSON格式数据;params:bb7be838-31ae-41fe-ae63-4ae8efe5c138,org.apache.shiro.web.servlet.ShiroHttpServletRequest@1ce3a59;desc:;detail:','http://127.0.0.1:8080/report/chart/getData.json','2016-12-22 04:11:20');
/*!40000 ALTER TABLE `ezrpt_sys_event` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-22 12:15:06
