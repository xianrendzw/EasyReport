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
  `uid` varchar(128) NOT NULL COMMENT '数据源唯一ID,由调接口方传入',
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
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='数据源配置信息表';

-- ----------------------------
-- Table structure for reporting
-- ----------------------------
DROP TABLE IF EXISTS `reporting`;
CREATE TABLE `reporting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `pid` int(11) NOT NULL DEFAULT '0',
  `uid` varchar(128) NOT NULL COMMENT '报表唯一ID,由接口调用方传入',
  `path` varchar(500) NOT NULL COMMENT '报表树型结构路径',
  `flag` int(11) NOT NULL COMMENT '报表树型节点标志,0为分类节点，1为报表结点',
  `has_child` tinyint(1) NOT NULL,
  `status` int(11) NOT NULL COMMENT '报表状态（1表示锁定，0表示编辑)',
  `sequence` int(11) NOT NULL COMMENT '报表节点在其父节点中的顺序',
  `ds_id` int(11) NOT NULL COMMENT '数据源ID',
  `name` varchar(50) NOT NULL COMMENT '报表名称',
  `data_range` int(11) NOT NULL DEFAULT '7' COMMENT '报表默认展示多少天的数据',
  `layout` int(2) NOT NULL COMMENT '布局形式(如:日期横、纵向升、降序4中）.1横向正序;2横向降序;3纵向正序;纵向降序;',
  `stat_column_layout` int(11) NOT NULL COMMENT '统计列布局形式.1横向;2纵向',
  `chart_type` varchar(10) NOT NULL DEFAULT 'line' COMMENT '图表类型(line,bar,pie,map)',
  `sql_text` text NOT NULL COMMENT '报表SQL语句',
  `meta_columns` text NOT NULL COMMENT '配置项对象JSON序列化字符串',
  `query_params` text NOT NULL COMMENT '查询条件列属性集合json字符串',
  `comment` varchar(500) NOT NULL COMMENT '说明备注',
  `create_user` varchar(50) NOT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  KEY `ukuid` (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='报表信息表';

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
  UNIQUE KEY `uk_key` (`key`,`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='配置字典表';

-- ----------------------------
-- Records of config_dict
-- ----------------------------
INSERT INTO `config_dict` VALUES ('1', '33', '常见统计列', 'statColumn', 'statColumn', '统计列', '2013-12-29 16:42:31', '2013-12-29 16:42:31');
INSERT INTO `config_dict` VALUES ('5', '1', 'PV列名', 'pv', 'pv', 'PV列名', '2013-12-29 21:26:17', '2013-12-29 21:26:17');
INSERT INTO `config_dict` VALUES ('6', '1', 'UV列名', 'uv', 'uv', 'UV列名', '2013-12-29 21:27:03', '2013-12-29 21:27:03');
INSERT INTO `config_dict` VALUES ('7', '1', 'IP列名', 'ip', 'ip', 'IP列名', '2013-12-29 21:27:47', '2013-12-29 21:27:47');
INSERT INTO `config_dict` VALUES ('15', '33', '常见日期列', 'dateColumn', 'dateColumn', '', '2014-01-10 15:32:49', '2014-01-10 15:32:49');
INSERT INTO `config_dict` VALUES ('16', '15', 'dt日期列', 'dt', '日期', '', '2014-01-10 15:33:07', '2014-01-10 15:33:07');
INSERT INTO `config_dict` VALUES ('17', '15', 'date日期列', 'date', '日期', '日期列', '2014-01-13 11:27:09', '2014-01-13 11:27:09');
INSERT INTO `config_dict` VALUES ('18', '1', 'UUID列名', 'uuid', 'uuid', '', '2014-01-22 22:11:48', '2014-01-22 22:11:48');
INSERT INTO `config_dict` VALUES ('19', '1', 'LBID列名', 'lbid', 'lbid', '', '2014-01-22 22:12:17', '2014-01-22 22:12:17');
INSERT INTO `config_dict` VALUES ('32', '33', '常见维度列', 'dimColumn', 'dimColumn', '常见统计列', '2014-02-28 18:04:14', '2014-02-28 19:26:22');
INSERT INTO `config_dict` VALUES ('33', '0', '报表常用元数据列', 'reportCommonMetaColum', 'reportCommonMetaColum', '报表常用元数据列', '2014-02-28 18:04:14', '2014-02-28 20:31:26');
INSERT INTO `config_dict` VALUES ('36', '1', '搜索词列名', 'pub_md5', 'pub_md5', '', '2014-03-19 18:01:50', '2014-03-19 18:01:50');
INSERT INTO `config_dict` VALUES ('37', '32', '排序列名', 'ord', 'ord', '', '2014-03-19 18:02:48', '2014-03-19 18:02:48');
INSERT INTO `config_dict` VALUES ('38', '32', '唯一id', 'cid', 'cid', '', '2014-03-20 18:25:44', '2014-03-20 18:25:44');
INSERT INTO `config_dict` VALUES ('39', '32', 'TITLE', 'title', 'title', '', '2014-03-20 18:26:21', '2014-03-20 18:34:54');
INSERT INTO `config_dict` VALUES ('40', '32', 'NAME', 'name', 'name', '', '2014-03-20 18:26:45', '2014-03-20 18:34:44');
INSERT INTO `config_dict` VALUES ('41', '32', '区域', 'w', 'w', '', '2014-03-20 18:27:53', '2014-03-20 18:27:53');
INSERT INTO `config_dict` VALUES ('42', '32', '排序', 'orderid', 'orderid', '', '2014-03-21 16:52:34', '2014-03-21 16:52:34');
INSERT INTO `config_dict` VALUES ('48', '32', 'id', 'id', 'id', '', '2014-04-25 19:29:55', '2014-04-25 19:29:55');
INSERT INTO `config_dict` VALUES ('49', '32', 'classname', 'classname', 'classname', '', '2014-04-25 19:30:06', '2014-04-25 19:30:06');
INSERT INTO `config_dict` VALUES ('50', '32', '位置', 'position', 'position', '位置', '2014-04-30 10:37:09', '2014-04-30 10:37:09');
INSERT INTO `config_dict` VALUES ('52', '33', '常见可选列', 'optionalColumn', 'optionalColumn', '', '2014-06-04 17:33:19', '2014-06-04 17:33:19');
INSERT INTO `config_dict` VALUES ('53', '52', 'uuid', 'uuid', 'uuid', '', '2014-06-04 17:33:42', '2014-06-04 17:33:42');
INSERT INTO `config_dict` VALUES ('54', '52', 'lbid', 'lbid', 'lbid', '', '2014-06-04 17:33:55', '2014-06-04 17:33:55');
INSERT INTO `config_dict` VALUES ('55', '32', 'lvlname', 'lvlname', 'lvlname', '', '2014-06-04 18:55:52', '2014-06-04 18:55:52');

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('0');
INSERT INTO `sequence` VALUES ('1');
INSERT INTO `sequence` VALUES ('2');
INSERT INTO `sequence` VALUES ('3');
INSERT INTO `sequence` VALUES ('4');
INSERT INTO `sequence` VALUES ('5');
INSERT INTO `sequence` VALUES ('6');
INSERT INTO `sequence` VALUES ('7');
INSERT INTO `sequence` VALUES ('8');
INSERT INTO `sequence` VALUES ('9');
INSERT INTO `sequence` VALUES ('10');
INSERT INTO `sequence` VALUES ('11');
INSERT INTO `sequence` VALUES ('12');
INSERT INTO `sequence` VALUES ('13');
INSERT INTO `sequence` VALUES ('14');
INSERT INTO `sequence` VALUES ('15');
INSERT INTO `sequence` VALUES ('16');
INSERT INTO `sequence` VALUES ('17');
INSERT INTO `sequence` VALUES ('18');
INSERT INTO `sequence` VALUES ('19');
INSERT INTO `sequence` VALUES ('20');
INSERT INTO `sequence` VALUES ('21');
INSERT INTO `sequence` VALUES ('22');
INSERT INTO `sequence` VALUES ('23');
INSERT INTO `sequence` VALUES ('24');
INSERT INTO `sequence` VALUES ('25');
INSERT INTO `sequence` VALUES ('26');
INSERT INTO `sequence` VALUES ('27');
INSERT INTO `sequence` VALUES ('28');
INSERT INTO `sequence` VALUES ('29');
INSERT INTO `sequence` VALUES ('30');
INSERT INTO `sequence` VALUES ('31');
INSERT INTO `sequence` VALUES ('32');
INSERT INTO `sequence` VALUES ('33');
INSERT INTO `sequence` VALUES ('34');
INSERT INTO `sequence` VALUES ('35');
INSERT INTO `sequence` VALUES ('36');
INSERT INTO `sequence` VALUES ('37');
INSERT INTO `sequence` VALUES ('38');
INSERT INTO `sequence` VALUES ('39');
INSERT INTO `sequence` VALUES ('40');
INSERT INTO `sequence` VALUES ('41');
INSERT INTO `sequence` VALUES ('42');
INSERT INTO `sequence` VALUES ('43');
INSERT INTO `sequence` VALUES ('44');
INSERT INTO `sequence` VALUES ('45');
INSERT INTO `sequence` VALUES ('46');
INSERT INTO `sequence` VALUES ('47');
INSERT INTO `sequence` VALUES ('48');
INSERT INTO `sequence` VALUES ('49');
INSERT INTO `sequence` VALUES ('50');
INSERT INTO `sequence` VALUES ('51');
INSERT INTO `sequence` VALUES ('52');
INSERT INTO `sequence` VALUES ('53');
INSERT INTO `sequence` VALUES ('54');
INSERT INTO `sequence` VALUES ('55');
INSERT INTO `sequence` VALUES ('56');
INSERT INTO `sequence` VALUES ('57');
INSERT INTO `sequence` VALUES ('58');
INSERT INTO `sequence` VALUES ('59');
INSERT INTO `sequence` VALUES ('60');
INSERT INTO `sequence` VALUES ('61');
INSERT INTO `sequence` VALUES ('62');
INSERT INTO `sequence` VALUES ('63');
INSERT INTO `sequence` VALUES ('64');
INSERT INTO `sequence` VALUES ('65');
INSERT INTO `sequence` VALUES ('66');
INSERT INTO `sequence` VALUES ('67');
INSERT INTO `sequence` VALUES ('68');
INSERT INTO `sequence` VALUES ('69');
INSERT INTO `sequence` VALUES ('70');
INSERT INTO `sequence` VALUES ('71');
INSERT INTO `sequence` VALUES ('72');
INSERT INTO `sequence` VALUES ('73');
INSERT INTO `sequence` VALUES ('74');
INSERT INTO `sequence` VALUES ('75');
INSERT INTO `sequence` VALUES ('76');
INSERT INTO `sequence` VALUES ('77');
INSERT INTO `sequence` VALUES ('78');
INSERT INTO `sequence` VALUES ('79');
INSERT INTO `sequence` VALUES ('80');
INSERT INTO `sequence` VALUES ('81');
INSERT INTO `sequence` VALUES ('82');
INSERT INTO `sequence` VALUES ('83');
INSERT INTO `sequence` VALUES ('84');
INSERT INTO `sequence` VALUES ('85');
INSERT INTO `sequence` VALUES ('86');
INSERT INTO `sequence` VALUES ('87');
INSERT INTO `sequence` VALUES ('88');
INSERT INTO `sequence` VALUES ('89');
INSERT INTO `sequence` VALUES ('90');
INSERT INTO `sequence` VALUES ('91');
INSERT INTO `sequence` VALUES ('92');
INSERT INTO `sequence` VALUES ('93');
INSERT INTO `sequence` VALUES ('94');
INSERT INTO `sequence` VALUES ('95');
INSERT INTO `sequence` VALUES ('96');
INSERT INTO `sequence` VALUES ('97');
INSERT INTO `sequence` VALUES ('98');
INSERT INTO `sequence` VALUES ('99');
INSERT INTO `sequence` VALUES ('100');
INSERT INTO `sequence` VALUES ('101');
INSERT INTO `sequence` VALUES ('102');
INSERT INTO `sequence` VALUES ('103');
INSERT INTO `sequence` VALUES ('104');
INSERT INTO `sequence` VALUES ('105');
INSERT INTO `sequence` VALUES ('106');
INSERT INTO `sequence` VALUES ('107');
INSERT INTO `sequence` VALUES ('108');
INSERT INTO `sequence` VALUES ('109');
INSERT INTO `sequence` VALUES ('110');
INSERT INTO `sequence` VALUES ('111');
INSERT INTO `sequence` VALUES ('112');
INSERT INTO `sequence` VALUES ('113');
INSERT INTO `sequence` VALUES ('114');
INSERT INTO `sequence` VALUES ('115');
INSERT INTO `sequence` VALUES ('116');
INSERT INTO `sequence` VALUES ('117');
INSERT INTO `sequence` VALUES ('118');
INSERT INTO `sequence` VALUES ('119');
INSERT INTO `sequence` VALUES ('120');
INSERT INTO `sequence` VALUES ('121');
INSERT INTO `sequence` VALUES ('122');
INSERT INTO `sequence` VALUES ('123');
INSERT INTO `sequence` VALUES ('124');
INSERT INTO `sequence` VALUES ('125');
INSERT INTO `sequence` VALUES ('126');
INSERT INTO `sequence` VALUES ('127');
INSERT INTO `sequence` VALUES ('128');
INSERT INTO `sequence` VALUES ('129');
INSERT INTO `sequence` VALUES ('130');
INSERT INTO `sequence` VALUES ('131');
INSERT INTO `sequence` VALUES ('132');
INSERT INTO `sequence` VALUES ('133');
INSERT INTO `sequence` VALUES ('134');
INSERT INTO `sequence` VALUES ('135');
INSERT INTO `sequence` VALUES ('136');
INSERT INTO `sequence` VALUES ('137');
INSERT INTO `sequence` VALUES ('138');
INSERT INTO `sequence` VALUES ('139');
INSERT INTO `sequence` VALUES ('140');
INSERT INTO `sequence` VALUES ('141');
INSERT INTO `sequence` VALUES ('142');
INSERT INTO `sequence` VALUES ('143');
INSERT INTO `sequence` VALUES ('144');
INSERT INTO `sequence` VALUES ('145');
INSERT INTO `sequence` VALUES ('146');
INSERT INTO `sequence` VALUES ('147');
INSERT INTO `sequence` VALUES ('148');
INSERT INTO `sequence` VALUES ('149');
INSERT INTO `sequence` VALUES ('150');
INSERT INTO `sequence` VALUES ('151');
INSERT INTO `sequence` VALUES ('152');
INSERT INTO `sequence` VALUES ('153');
INSERT INTO `sequence` VALUES ('154');
INSERT INTO `sequence` VALUES ('155');
INSERT INTO `sequence` VALUES ('156');
INSERT INTO `sequence` VALUES ('157');
INSERT INTO `sequence` VALUES ('158');
INSERT INTO `sequence` VALUES ('159');
INSERT INTO `sequence` VALUES ('160');
INSERT INTO `sequence` VALUES ('161');
INSERT INTO `sequence` VALUES ('162');
INSERT INTO `sequence` VALUES ('163');
INSERT INTO `sequence` VALUES ('164');
INSERT INTO `sequence` VALUES ('165');
INSERT INTO `sequence` VALUES ('166');
INSERT INTO `sequence` VALUES ('167');
INSERT INTO `sequence` VALUES ('168');
INSERT INTO `sequence` VALUES ('169');
INSERT INTO `sequence` VALUES ('170');
INSERT INTO `sequence` VALUES ('171');
INSERT INTO `sequence` VALUES ('172');
INSERT INTO `sequence` VALUES ('173');
INSERT INTO `sequence` VALUES ('174');
INSERT INTO `sequence` VALUES ('175');
INSERT INTO `sequence` VALUES ('176');
INSERT INTO `sequence` VALUES ('177');
INSERT INTO `sequence` VALUES ('178');
INSERT INTO `sequence` VALUES ('179');
INSERT INTO `sequence` VALUES ('180');
INSERT INTO `sequence` VALUES ('181');
INSERT INTO `sequence` VALUES ('182');
INSERT INTO `sequence` VALUES ('183');
INSERT INTO `sequence` VALUES ('184');
INSERT INTO `sequence` VALUES ('185');
INSERT INTO `sequence` VALUES ('186');
INSERT INTO `sequence` VALUES ('187');
INSERT INTO `sequence` VALUES ('188');
INSERT INTO `sequence` VALUES ('189');
INSERT INTO `sequence` VALUES ('190');
INSERT INTO `sequence` VALUES ('191');
INSERT INTO `sequence` VALUES ('192');
INSERT INTO `sequence` VALUES ('193');
INSERT INTO `sequence` VALUES ('194');
INSERT INTO `sequence` VALUES ('195');
INSERT INTO `sequence` VALUES ('196');
INSERT INTO `sequence` VALUES ('197');
INSERT INTO `sequence` VALUES ('198');
INSERT INTO `sequence` VALUES ('199');
INSERT INTO `sequence` VALUES ('200');
INSERT INTO `sequence` VALUES ('201');
INSERT INTO `sequence` VALUES ('202');
INSERT INTO `sequence` VALUES ('203');
INSERT INTO `sequence` VALUES ('204');
INSERT INTO `sequence` VALUES ('205');
INSERT INTO `sequence` VALUES ('206');
INSERT INTO `sequence` VALUES ('207');
INSERT INTO `sequence` VALUES ('208');
INSERT INTO `sequence` VALUES ('209');
INSERT INTO `sequence` VALUES ('210');
INSERT INTO `sequence` VALUES ('211');
INSERT INTO `sequence` VALUES ('212');
INSERT INTO `sequence` VALUES ('213');
INSERT INTO `sequence` VALUES ('214');
INSERT INTO `sequence` VALUES ('215');
INSERT INTO `sequence` VALUES ('216');
INSERT INTO `sequence` VALUES ('217');
INSERT INTO `sequence` VALUES ('218');
INSERT INTO `sequence` VALUES ('219');
INSERT INTO `sequence` VALUES ('220');
INSERT INTO `sequence` VALUES ('221');
INSERT INTO `sequence` VALUES ('222');
INSERT INTO `sequence` VALUES ('223');
INSERT INTO `sequence` VALUES ('224');
INSERT INTO `sequence` VALUES ('225');
INSERT INTO `sequence` VALUES ('226');
INSERT INTO `sequence` VALUES ('227');
INSERT INTO `sequence` VALUES ('228');
INSERT INTO `sequence` VALUES ('229');
INSERT INTO `sequence` VALUES ('230');
INSERT INTO `sequence` VALUES ('231');
INSERT INTO `sequence` VALUES ('232');
INSERT INTO `sequence` VALUES ('233');
INSERT INTO `sequence` VALUES ('234');
INSERT INTO `sequence` VALUES ('235');
INSERT INTO `sequence` VALUES ('236');
INSERT INTO `sequence` VALUES ('237');
INSERT INTO `sequence` VALUES ('238');
INSERT INTO `sequence` VALUES ('239');
INSERT INTO `sequence` VALUES ('240');
INSERT INTO `sequence` VALUES ('241');
INSERT INTO `sequence` VALUES ('242');
INSERT INTO `sequence` VALUES ('243');
INSERT INTO `sequence` VALUES ('244');
INSERT INTO `sequence` VALUES ('245');
INSERT INTO `sequence` VALUES ('246');
INSERT INTO `sequence` VALUES ('247');
INSERT INTO `sequence` VALUES ('248');
INSERT INTO `sequence` VALUES ('249');
INSERT INTO `sequence` VALUES ('250');
INSERT INTO `sequence` VALUES ('251');
INSERT INTO `sequence` VALUES ('252');
INSERT INTO `sequence` VALUES ('253');
INSERT INTO `sequence` VALUES ('254');
INSERT INTO `sequence` VALUES ('255');
INSERT INTO `sequence` VALUES ('256');
INSERT INTO `sequence` VALUES ('257');
INSERT INTO `sequence` VALUES ('258');
INSERT INTO `sequence` VALUES ('259');
INSERT INTO `sequence` VALUES ('260');
INSERT INTO `sequence` VALUES ('261');
INSERT INTO `sequence` VALUES ('262');
INSERT INTO `sequence` VALUES ('263');
INSERT INTO `sequence` VALUES ('264');
INSERT INTO `sequence` VALUES ('265');
INSERT INTO `sequence` VALUES ('266');
INSERT INTO `sequence` VALUES ('267');
INSERT INTO `sequence` VALUES ('268');
INSERT INTO `sequence` VALUES ('269');
INSERT INTO `sequence` VALUES ('270');
INSERT INTO `sequence` VALUES ('271');
INSERT INTO `sequence` VALUES ('272');
INSERT INTO `sequence` VALUES ('273');
INSERT INTO `sequence` VALUES ('274');
INSERT INTO `sequence` VALUES ('275');
INSERT INTO `sequence` VALUES ('276');
INSERT INTO `sequence` VALUES ('277');
INSERT INTO `sequence` VALUES ('278');
INSERT INTO `sequence` VALUES ('279');
INSERT INTO `sequence` VALUES ('280');
INSERT INTO `sequence` VALUES ('281');
INSERT INTO `sequence` VALUES ('282');
INSERT INTO `sequence` VALUES ('283');
INSERT INTO `sequence` VALUES ('284');
INSERT INTO `sequence` VALUES ('285');
INSERT INTO `sequence` VALUES ('286');
INSERT INTO `sequence` VALUES ('287');
INSERT INTO `sequence` VALUES ('288');
INSERT INTO `sequence` VALUES ('289');
INSERT INTO `sequence` VALUES ('290');
INSERT INTO `sequence` VALUES ('291');
INSERT INTO `sequence` VALUES ('292');
INSERT INTO `sequence` VALUES ('293');
INSERT INTO `sequence` VALUES ('294');
INSERT INTO `sequence` VALUES ('295');
INSERT INTO `sequence` VALUES ('296');
INSERT INTO `sequence` VALUES ('297');
INSERT INTO `sequence` VALUES ('298');
INSERT INTO `sequence` VALUES ('299');
INSERT INTO `sequence` VALUES ('300');
INSERT INTO `sequence` VALUES ('301');
INSERT INTO `sequence` VALUES ('302');
INSERT INTO `sequence` VALUES ('303');
INSERT INTO `sequence` VALUES ('304');
INSERT INTO `sequence` VALUES ('305');
INSERT INTO `sequence` VALUES ('306');
INSERT INTO `sequence` VALUES ('307');
INSERT INTO `sequence` VALUES ('308');
INSERT INTO `sequence` VALUES ('309');
INSERT INTO `sequence` VALUES ('310');
INSERT INTO `sequence` VALUES ('311');
INSERT INTO `sequence` VALUES ('312');
INSERT INTO `sequence` VALUES ('313');
INSERT INTO `sequence` VALUES ('314');
INSERT INTO `sequence` VALUES ('315');
INSERT INTO `sequence` VALUES ('316');
INSERT INTO `sequence` VALUES ('317');
INSERT INTO `sequence` VALUES ('318');
INSERT INTO `sequence` VALUES ('319');
INSERT INTO `sequence` VALUES ('320');
INSERT INTO `sequence` VALUES ('321');
INSERT INTO `sequence` VALUES ('322');
INSERT INTO `sequence` VALUES ('323');
INSERT INTO `sequence` VALUES ('324');
INSERT INTO `sequence` VALUES ('325');
INSERT INTO `sequence` VALUES ('326');
INSERT INTO `sequence` VALUES ('327');
INSERT INTO `sequence` VALUES ('328');
INSERT INTO `sequence` VALUES ('329');
INSERT INTO `sequence` VALUES ('330');
INSERT INTO `sequence` VALUES ('331');
INSERT INTO `sequence` VALUES ('332');
INSERT INTO `sequence` VALUES ('333');
INSERT INTO `sequence` VALUES ('334');
INSERT INTO `sequence` VALUES ('335');
INSERT INTO `sequence` VALUES ('336');
INSERT INTO `sequence` VALUES ('337');
INSERT INTO `sequence` VALUES ('338');
INSERT INTO `sequence` VALUES ('339');
INSERT INTO `sequence` VALUES ('340');
INSERT INTO `sequence` VALUES ('341');
INSERT INTO `sequence` VALUES ('342');
INSERT INTO `sequence` VALUES ('343');
INSERT INTO `sequence` VALUES ('344');
INSERT INTO `sequence` VALUES ('345');
INSERT INTO `sequence` VALUES ('346');
INSERT INTO `sequence` VALUES ('347');
INSERT INTO `sequence` VALUES ('348');
INSERT INTO `sequence` VALUES ('349');
INSERT INTO `sequence` VALUES ('350');
INSERT INTO `sequence` VALUES ('351');
INSERT INTO `sequence` VALUES ('352');
INSERT INTO `sequence` VALUES ('353');
INSERT INTO `sequence` VALUES ('354');
INSERT INTO `sequence` VALUES ('355');
INSERT INTO `sequence` VALUES ('356');
INSERT INTO `sequence` VALUES ('357');
INSERT INTO `sequence` VALUES ('358');
INSERT INTO `sequence` VALUES ('359');
INSERT INTO `sequence` VALUES ('360');
INSERT INTO `sequence` VALUES ('361');
INSERT INTO `sequence` VALUES ('362');
INSERT INTO `sequence` VALUES ('363');
INSERT INTO `sequence` VALUES ('364');
INSERT INTO `sequence` VALUES ('365');
INSERT INTO `sequence` VALUES ('366');
INSERT INTO `sequence` VALUES ('367');
INSERT INTO `sequence` VALUES ('368');
INSERT INTO `sequence` VALUES ('369');
INSERT INTO `sequence` VALUES ('370');
INSERT INTO `sequence` VALUES ('371');
INSERT INTO `sequence` VALUES ('372');
INSERT INTO `sequence` VALUES ('373');
INSERT INTO `sequence` VALUES ('374');
INSERT INTO `sequence` VALUES ('375');
INSERT INTO `sequence` VALUES ('376');
INSERT INTO `sequence` VALUES ('377');
INSERT INTO `sequence` VALUES ('378');
INSERT INTO `sequence` VALUES ('379');
INSERT INTO `sequence` VALUES ('380');
INSERT INTO `sequence` VALUES ('381');
INSERT INTO `sequence` VALUES ('382');
INSERT INTO `sequence` VALUES ('383');
INSERT INTO `sequence` VALUES ('384');
INSERT INTO `sequence` VALUES ('385');
INSERT INTO `sequence` VALUES ('386');
INSERT INTO `sequence` VALUES ('387');
INSERT INTO `sequence` VALUES ('388');
INSERT INTO `sequence` VALUES ('389');
INSERT INTO `sequence` VALUES ('390');
INSERT INTO `sequence` VALUES ('391');
INSERT INTO `sequence` VALUES ('392');
INSERT INTO `sequence` VALUES ('393');
INSERT INTO `sequence` VALUES ('394');
INSERT INTO `sequence` VALUES ('395');
INSERT INTO `sequence` VALUES ('396');
INSERT INTO `sequence` VALUES ('397');
INSERT INTO `sequence` VALUES ('398');
INSERT INTO `sequence` VALUES ('399');
INSERT INTO `sequence` VALUES ('400');
INSERT INTO `sequence` VALUES ('401');
INSERT INTO `sequence` VALUES ('402');
INSERT INTO `sequence` VALUES ('403');
INSERT INTO `sequence` VALUES ('404');
INSERT INTO `sequence` VALUES ('405');
INSERT INTO `sequence` VALUES ('406');
INSERT INTO `sequence` VALUES ('407');
INSERT INTO `sequence` VALUES ('408');
INSERT INTO `sequence` VALUES ('409');
INSERT INTO `sequence` VALUES ('410');
INSERT INTO `sequence` VALUES ('411');
INSERT INTO `sequence` VALUES ('412');
INSERT INTO `sequence` VALUES ('413');
INSERT INTO `sequence` VALUES ('414');
INSERT INTO `sequence` VALUES ('415');
INSERT INTO `sequence` VALUES ('416');
INSERT INTO `sequence` VALUES ('417');
INSERT INTO `sequence` VALUES ('418');
INSERT INTO `sequence` VALUES ('419');
INSERT INTO `sequence` VALUES ('420');
INSERT INTO `sequence` VALUES ('421');
INSERT INTO `sequence` VALUES ('422');
INSERT INTO `sequence` VALUES ('423');
INSERT INTO `sequence` VALUES ('424');
INSERT INTO `sequence` VALUES ('425');
INSERT INTO `sequence` VALUES ('426');
INSERT INTO `sequence` VALUES ('427');
INSERT INTO `sequence` VALUES ('428');
INSERT INTO `sequence` VALUES ('429');
INSERT INTO `sequence` VALUES ('430');
INSERT INTO `sequence` VALUES ('431');
INSERT INTO `sequence` VALUES ('432');
INSERT INTO `sequence` VALUES ('433');
INSERT INTO `sequence` VALUES ('434');
INSERT INTO `sequence` VALUES ('435');
INSERT INTO `sequence` VALUES ('436');
INSERT INTO `sequence` VALUES ('437');
INSERT INTO `sequence` VALUES ('438');
INSERT INTO `sequence` VALUES ('439');
INSERT INTO `sequence` VALUES ('440');
INSERT INTO `sequence` VALUES ('441');
INSERT INTO `sequence` VALUES ('442');
INSERT INTO `sequence` VALUES ('443');
INSERT INTO `sequence` VALUES ('444');
INSERT INTO `sequence` VALUES ('445');
INSERT INTO `sequence` VALUES ('446');
INSERT INTO `sequence` VALUES ('447');
INSERT INTO `sequence` VALUES ('448');
INSERT INTO `sequence` VALUES ('449');
INSERT INTO `sequence` VALUES ('450');
INSERT INTO `sequence` VALUES ('451');
INSERT INTO `sequence` VALUES ('452');
INSERT INTO `sequence` VALUES ('453');
INSERT INTO `sequence` VALUES ('454');
INSERT INTO `sequence` VALUES ('455');
INSERT INTO `sequence` VALUES ('456');
INSERT INTO `sequence` VALUES ('457');
INSERT INTO `sequence` VALUES ('458');
INSERT INTO `sequence` VALUES ('459');
INSERT INTO `sequence` VALUES ('460');
INSERT INTO `sequence` VALUES ('461');
INSERT INTO `sequence` VALUES ('462');
INSERT INTO `sequence` VALUES ('463');
INSERT INTO `sequence` VALUES ('464');
INSERT INTO `sequence` VALUES ('465');
INSERT INTO `sequence` VALUES ('466');
INSERT INTO `sequence` VALUES ('467');
INSERT INTO `sequence` VALUES ('468');
INSERT INTO `sequence` VALUES ('469');
INSERT INTO `sequence` VALUES ('470');
INSERT INTO `sequence` VALUES ('471');
INSERT INTO `sequence` VALUES ('472');
INSERT INTO `sequence` VALUES ('473');
INSERT INTO `sequence` VALUES ('474');
INSERT INTO `sequence` VALUES ('475');
INSERT INTO `sequence` VALUES ('476');
INSERT INTO `sequence` VALUES ('477');
INSERT INTO `sequence` VALUES ('478');
INSERT INTO `sequence` VALUES ('479');
INSERT INTO `sequence` VALUES ('480');
INSERT INTO `sequence` VALUES ('481');
INSERT INTO `sequence` VALUES ('482');
INSERT INTO `sequence` VALUES ('483');
INSERT INTO `sequence` VALUES ('484');
INSERT INTO `sequence` VALUES ('485');
INSERT INTO `sequence` VALUES ('486');
INSERT INTO `sequence` VALUES ('487');
INSERT INTO `sequence` VALUES ('488');
INSERT INTO `sequence` VALUES ('489');
INSERT INTO `sequence` VALUES ('490');
INSERT INTO `sequence` VALUES ('491');
INSERT INTO `sequence` VALUES ('492');
INSERT INTO `sequence` VALUES ('493');
INSERT INTO `sequence` VALUES ('494');
INSERT INTO `sequence` VALUES ('495');
INSERT INTO `sequence` VALUES ('496');
INSERT INTO `sequence` VALUES ('497');
INSERT INTO `sequence` VALUES ('498');
INSERT INTO `sequence` VALUES ('499');
INSERT INTO `sequence` VALUES ('500');
INSERT INTO `sequence` VALUES ('501');
INSERT INTO `sequence` VALUES ('502');
INSERT INTO `sequence` VALUES ('503');
INSERT INTO `sequence` VALUES ('504');
INSERT INTO `sequence` VALUES ('505');
INSERT INTO `sequence` VALUES ('506');
INSERT INTO `sequence` VALUES ('507');
INSERT INTO `sequence` VALUES ('508');
