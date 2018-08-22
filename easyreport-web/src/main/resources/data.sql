set mode MySQL;

-- ----------------------------
-- Records of ezrpt_member_module
-- ----------------------------
MERGE INTO `ezrpt_member_module` VALUES ('3', '40', '数据源', 'report.ds', 'icon-datasource', 'views/report/ds', '40,3', '0', '0', '', '', '2', '1', '数据源', '2014-10-30 06:41:24', '2015-12-17 18:13:28');
MERGE INTO `ezrpt_member_module` VALUES ('4', '40', '报表设计', 'report.designer', 'icon-chart', 'views/report/designer', '40,4', '0', '0', '', '', '1', '1', '报表设计', '2014-10-30 06:41:42', '2015-12-17 18:13:28');
MERGE INTO `ezrpt_member_module` VALUES ('7', '0', '用户权限', 'membership', 'icon-user', 'views/report/user', '7', '1', '0', '', '', '3', '1', '用户权限', '2014-10-30 06:45:47', '2015-12-17 18:13:28');
MERGE INTO `ezrpt_member_module` VALUES ('23', '7', '用户管理', 'membership.user', 'icon-member', 'views/member/user', '7,23', '0', '0', '', '', '1', '1', '用户管理', '2014-10-30 07:38:22', '2015-12-17 18:13:57');
MERGE INTO `ezrpt_member_module` VALUES ('24', '7', '角色管理', 'membership.role', 'icon-group', 'views/member/role', '7,24', '0', '0', '', '', '2', '1', '角色管理', '2014-10-30 07:38:44', '2015-12-17 18:13:57');
MERGE INTO `ezrpt_member_module` VALUES ('25', '7', '权限管理', 'membership.permission', 'icon-perm', 'views/member/permission', '7,25', '0', '0', '', '', '3', '1', '操作管理', '2014-10-30 07:39:03', '2015-12-17 18:13:57');
MERGE INTO `ezrpt_member_module` VALUES ('26', '32', '系统日志', 'membership.event', 'icon-event', 'views/member/event', '32,26', '0', '0', '', '', '3', '1', '系统日志', '2014-10-30 07:41:06', '2015-12-17 18:13:57');
MERGE INTO `ezrpt_member_module` VALUES ('31', '7', '模块管理', 'membership.module', 'icon-org', 'views/member/module', '7,31', '0', '0', '', '', '4', '1', '模块管理', '2014-10-31 02:21:46', '2015-12-17 18:13:57');
MERGE INTO `ezrpt_member_module` VALUES ('32', '0', '系统管理', 'sys', 'icon-settings4', 'views/sys/conf', '32', '1', '0', '', '', '5', '1', '系统管理', '2014-11-12 04:20:57', '2015-12-17 18:13:28');
MERGE INTO `ezrpt_member_module` VALUES ('39', '32', '系统配置', 'sys.conf', 'icon-settings2', 'views/sys/conf', '32,39', '0', '0', ' ', ' ', '2', '0', '系统配置', '2015-08-08 02:48:03', '2015-12-17 18:13:57');
MERGE INTO `ezrpt_member_module` VALUES ('40', '0', '报表管理', 'report', 'icon-designer1', 'views/report/designer', '40', '1', '0', '', '', '1', '1', '报表管理', '2015-12-14 03:45:36', '2015-12-17 18:13:28');
MERGE INTO `ezrpt_member_module` VALUES ('41', '40', '参数配置', 'report.conf', 'icon-settings', 'views/report/conf', '40,41', '0', '0', '', '', '3', '1', '参数配置', '2015-12-14 03:45:36', '2015-12-17 18:13:28');
MERGE INTO `ezrpt_member_module` VALUES ('42', '0', '计划任务', 'schedule', 'icon-scheduled1', 'views/schedule/task', '42', '1', '0', '', '', '2', '1', '计划任务', '2015-12-14 03:45:36', '2015-12-17 18:13:28');
MERGE INTO `ezrpt_member_module` VALUES ('54', '42', '任务管理', 'schedule.task', 'icon-task2', 'views/schedule/task', '42,54', '0', '0', ' ', ' ', '1', '1', '任备管理', '2016-08-29 09:43:08', '2016-08-29 09:43:12');


-- ----------------------------
-- Records of ezrpt_member_permission
-- ----------------------------
MERGE INTO `ezrpt_member_permission` VALUES ('2', '23', '删除用户', 'membership.user:delete', '2', '删除', '2014-10-30 08:25:52', '2014-10-30 08:24:54');
MERGE INTO `ezrpt_member_permission` VALUES ('4', '23', '编辑用户', 'membership.user:update', '3', '编辑', '2014-10-30 13:02:37', '2014-10-30 13:01:39');
MERGE INTO `ezrpt_member_permission` VALUES ('5', '23', '查看用户', 'membership.user:view', '4', '查看', '2014-10-30 13:08:23', '2014-10-30 13:07:25');
MERGE INTO `ezrpt_member_permission` VALUES ('6', '24', '添加角色', 'membership.role:add', '5', '添加', '2014-10-31 02:13:48', '2014-10-31 02:12:52');
MERGE INTO `ezrpt_member_permission` VALUES ('7', '24', '删除角色', 'membership.role:delete', '4', '删除', '2014-10-31 02:15:43', '2014-10-31 02:14:47');
MERGE INTO `ezrpt_member_permission` VALUES ('8', '24', '编辑角色', 'membership.role:update', '3', '编辑', '2014-10-31 02:16:07', '2014-10-31 02:15:10');
MERGE INTO `ezrpt_member_permission` VALUES ('9', '24', '查看角色', 'membership.role:view', '2', '查看', '2014-10-31 02:16:21', '2014-10-31 02:15:25');
MERGE INTO `ezrpt_member_permission` VALUES ('10', '25', '管理', 'membership.permission:*', '1', '管理所有权限', '2014-10-31 02:24:04', '2014-10-31 02:23:08');
MERGE INTO `ezrpt_member_permission` VALUES ('14', '31', '管理', 'membership.module:*', '1', '管理系统模块', '2014-10-31 02:28:43', '2014-10-31 02:27:46');
MERGE INTO `ezrpt_member_permission` VALUES ('15', '31', '编辑模块', 'membership.module.edit', '2', '编辑', '2014-10-31 02:29:17', '2014-10-31 02:28:20');
MERGE INTO `ezrpt_member_permission` VALUES ('16', '31', '删除模块', 'membership.module.remove', '3', '删除', '2014-10-31 02:29:38', '2014-10-31 02:28:42');
MERGE INTO `ezrpt_member_permission` VALUES ('18', '26', '查看日志', 'membership.event:view', '1', '查看', '2014-10-31 02:31:08', '2014-10-31 02:30:11');
MERGE INTO `ezrpt_member_permission` VALUES ('20', '23', '添加用户', 'membership.user:add', '1', '添加', '2014-10-31 10:29:22', '2014-10-31 10:28:26');
MERGE INTO `ezrpt_member_permission` VALUES ('21', '2', '查看', 'dashboard:view', '1', '查看仪表盘图表', '2014-11-12 03:01:11', '2014-11-12 03:01:11');
MERGE INTO `ezrpt_member_permission` VALUES ('37', '23', '管理', 'membership.user:*', '1', '用户管理', '2014-11-12 03:26:41', '2014-11-12 03:26:41');
MERGE INTO `ezrpt_member_permission` VALUES ('38', '24', '管理', 'membership.role:*', '1', '角色管理', '2014-11-12 03:27:49', '2014-11-12 03:27:49');
MERGE INTO `ezrpt_member_permission` VALUES ('58', '26', '日志管理', 'membership.event:*', '2', '日志管理', '2014-11-27 07:51:02', '2014-11-27 07:51:04');
MERGE INTO `ezrpt_member_permission` VALUES ('59', '3', '删除数据源', 'report.ds:delete', '1', '删除数据源', '2015-06-23 06:55:58', '2015-06-23 06:55:58');
MERGE INTO `ezrpt_member_permission` VALUES ('60', '3', '创建数据源', 'report.ds:add', '2', '创建数据源', '2015-06-23 06:56:18', '2015-06-23 06:56:18');
MERGE INTO `ezrpt_member_permission` VALUES ('61', '3', '修改数据源', 'report.ds:update', '3', '修改数据源', '2015-06-23 06:56:37', '2015-06-23 06:56:37');
MERGE INTO `ezrpt_member_permission` VALUES ('62', '3', '查看数据源', 'report.ds:view', '4', '查看数据源', '2015-06-23 06:57:36', '2015-06-23 06:57:36');
MERGE INTO `ezrpt_member_permission` VALUES ('63', '3', '管理数据源', 'report.ds:*', '5', '管理数据源', '2015-06-23 06:59:14', '2015-06-23 06:59:14');
MERGE INTO `ezrpt_member_permission` VALUES ('64', '41', '管理', 'report.conf:*', '1', '管理报表参数配置', '2015-06-23 07:01:44', '2015-06-23 07:01:44');
MERGE INTO `ezrpt_member_permission` VALUES ('65', '4', '管理', 'report.designer:*', '2', '报表设计管理', '2015-06-23 07:02:04', '2015-06-23 07:02:04');
MERGE INTO `ezrpt_member_permission` VALUES ('66', '4', '查看', 'report.designer:view', '10', '查看', '2015-12-17 13:24:08', '2015-12-17 13:24:08');
MERGE INTO `ezrpt_member_permission` VALUES ('67', '4', '增加', 'report.designer:add', '10', '添加', '2015-12-17 13:24:40', '2015-12-17 13:24:52');
MERGE INTO `ezrpt_member_permission` VALUES ('69', '4', '修改', 'report.designer:update', '10', '编辑', '2015-12-17 17:43:58', '2015-12-17 17:43:58');
MERGE INTO `ezrpt_member_permission` VALUES ('71', '39', '管理', 'sys.conf:*', '10', '管理', '2015-12-17 17:44:35', '2015-12-17 17:44:35');
MERGE INTO `ezrpt_member_permission` VALUES ('72', '42', '修改', 'schedule.task:update', '10', '修改任务', '2015-12-17 17:45:36', '2015-12-17 17:45:43');
MERGE INTO `ezrpt_member_permission` VALUES ('73', '42', '删除', 'schedule.task:delete', '10', '删除任务', '2015-12-17 17:46:00', '2015-12-17 17:46:00');
MERGE INTO `ezrpt_member_permission` VALUES ('74', '42', '查看', 'schedule.task:view', '10', '查看任务', '2015-12-17 17:46:19', '2015-12-17 17:46:19');
MERGE INTO `ezrpt_member_permission` VALUES ('75', '54', '管理', 'schedule.task:*', '10', '管理所有任务', '2015-12-17 17:46:50', '2015-12-17 17:46:50');
MERGE INTO `ezrpt_member_permission` VALUES ('77', '54', '增加', 'schedule.task:add', '10', 'add', '2016-09-18 23:57:09', '2016-09-18 23:57:09');
MERGE INTO `ezrpt_member_permission` VALUES ('78', '4', '预览', 'report.designer:preview', '10', '预览报表', '2016-09-27 11:09:14', '2016-09-27 11:09:14');
MERGE INTO `ezrpt_member_permission` VALUES ('79', '4', '导出', 'report.designer:export', '10', '导出excel,pdf等', '2016-09-27 11:09:55', '2016-09-27 11:09:55');

-- ----------------------------
-- Records of ezrpt_member_role
-- ----------------------------
MERGE INTO `ezrpt_member_role` VALUES ('4', '39,38,42,41,40,23,24,25,26,3,7,32,31,4', '76,75,67,66,77,64,65,74,73,72,59,60,61,62,63,69,37,20,2,4,5,38,9,8,7,6,10,14,15,16,71,70,18,58', '超级管理员', 'superAdmin', '1', '1', '1', '管理员', 'admin', '2014-10-31 14:38:09', '2015-12-18 01:48:00');
MERGE INTO `ezrpt_member_role` VALUES ('22', '39,38,42,41,40,23,24,25,26,3,7,32,31,4', '76,75,67,66,77,64,65,74,73,72,59,60,61,62,63,69,37,20,2,4,5,38,9,8,7,6,10,14,15,16,71,70,18,58', '开发人员', 'developer', '1', '1', '2', '开发人员', 'admin', '2014-11-15 12:56:56', '2015-12-18 01:47:45');
MERGE INTO `ezrpt_member_role` VALUES ('23', '3,4,40,41,42,54', '59,60,61,62,63,65,66,67,69,64,75,77,72,73,74', '测试人员', 'test', '1', '1', '10', '测试人员', 'admin', '2015-12-17 21:35:50', '2015-12-20 18:05:27');


-- ----------------------------
-- Records of ezrpt_member_user
-- ----------------------------
MERGE INTO `ezrpt_member_user` VALUES ('1', '4', 'admin', '436a5530ff7436c546dc2047d24fff46', 'c1d69267a3fd2e207408b68f8662cf27', '管理员', '14068728@qq.com', '123456789', '1', 'sa', '2015-01-05 17:38:50', '2015-01-05 17:38:50');
MERGE INTO `ezrpt_member_user` VALUES ('5', '23', 'test', 'c2b57a2b72ec4f289c8daa68aa7fb3a6', '283facc4dc9896ddb303a736be9530ea', 'tester', 'tester@qq.com', '12343423423', '1', '1', '2015-12-20 18:06:59', '2015-08-14 10:04:00');

-- ----------------------------
-- Records of ezrpt_meta_category
-- ----------------------------
MERGE INTO `ezrpt_meta_category` VALUES ('711', '0', '中国空气(MySQL)', '711', '0', '0', '100', '', '2015-01-29 17:54:49', '2016-09-22 23:06:48');
MERGE INTO `ezrpt_meta_category` VALUES ('716', '0', '中国天气(MySQL)', '716', '0', '0', '100', '', '2015-02-10 15:20:31', '2016-09-22 23:06:50');

-- ----------------------------
-- Records of ezrpt_meta_conf
-- ----------------------------
MERGE INTO `ezrpt_meta_conf` VALUES ('1', '33', '常见统计列', 'statColumn', 'statColumn', '0', '统计列', '2015-02-10 10:23:56', '2015-02-10 10:23:56');
MERGE INTO `ezrpt_meta_conf` VALUES ('5', '1', 'AQI指数', 'aqi', '空气指数', '100', '空气指数', '2015-02-10 11:01:45', '2015-02-10 11:01:45');
MERGE INTO `ezrpt_meta_conf` VALUES ('6', '1', 'AQI指数范围', 'aqi_range', 'AQI指数范围', '100', 'AQI指数范围', '2015-02-10 11:02:34', '2015-02-10 11:02:34');
MERGE INTO `ezrpt_meta_conf` VALUES ('7', '1', '空气等级', 'quality', '空气等级', '100', '空气等级', '2015-02-10 11:03:41', '2015-02-10 11:03:41');
MERGE INTO `ezrpt_meta_conf` VALUES ('15', '33', '常见日期列', 'dateColumn', 'dateColumn', '100', '', '2015-02-10 10:23:56', '2015-02-10 10:23:56');
MERGE INTO `ezrpt_meta_conf` VALUES ('16', '15', 'dt日期', 'dt', '日期', '100', '', '2015-02-10 11:17:18', '2015-02-10 11:17:18');
MERGE INTO `ezrpt_meta_conf` VALUES ('17', '15', 'date日期', 'date', '日期', '100', '日期列', '2015-02-10 11:17:22', '2015-02-10 11:17:22');
MERGE INTO `ezrpt_meta_conf` VALUES ('18', '1', 'PM2.5细颗粒物', 'pm25', 'PM2.5细颗粒物', '100', 'PM2.5细颗粒物', '2015-02-10 11:04:31', '2015-02-10 11:04:31');
MERGE INTO `ezrpt_meta_conf` VALUES ('19', '1', 'PM10可吸入颗粒物', 'pm10', 'PM10可吸入颗粒物', '100', 'PM10可吸入颗粒物', '2015-02-10 11:05:05', '2015-02-10 11:05:05');
MERGE INTO `ezrpt_meta_conf` VALUES ('32', '33', '常见维度列', 'dimColumn', 'dimColumn', '0', '常见统计列', '2015-02-10 10:23:56', '2015-02-10 10:23:56');
MERGE INTO `ezrpt_meta_conf` VALUES ('33', '0', '报表常用元数据列', 'reportCommonMetaColum', 'reportCommonMetaColum', '0', '报表常用元数据列', '2015-02-10 10:23:56', '2015-02-10 10:23:56');
MERGE INTO `ezrpt_meta_conf` VALUES ('36', '1', '二氧化硫', 'so2', '二氧化硫(So2)', '100', '二氧化硫', '2015-02-10 11:05:57', '2015-02-10 11:05:57');
MERGE INTO `ezrpt_meta_conf` VALUES ('37', '32', '城市', 'area', '城市', '100', '', '2015-02-10 11:10:47', '2015-02-10 11:10:47');
MERGE INTO `ezrpt_meta_conf` VALUES ('38', '32', '地区', 'region', '地区', '100', '华北，华南', '2015-02-10 11:11:06', '2015-02-10 11:11:06');
MERGE INTO `ezrpt_meta_conf` VALUES ('39', '32', '省(直辖市)', 'province', '省(直辖市)', '100', '', '2015-02-10 11:11:41', '2015-02-10 11:11:41');
MERGE INTO `ezrpt_meta_conf` VALUES ('40', '32', '城市', 'city', '城市', '100', '北京，上海', '2015-02-10 11:12:01', '2015-02-10 11:12:01');
MERGE INTO `ezrpt_meta_conf` VALUES ('41', '32', '城区', 'district', '城区', '100', '海淀区、朝阳区', '2015-02-10 11:12:43', '2015-02-10 11:12:43');
MERGE INTO `ezrpt_meta_conf` VALUES ('42', '32', '标题', 'title', '标题', '100', '', '2015-02-10 11:13:30', '2015-02-10 11:13:30');
MERGE INTO `ezrpt_meta_conf` VALUES ('48', '32', '标识', 'id', '标识', '100', '', '2015-02-10 11:13:44', '2015-02-10 11:13:44');
MERGE INTO `ezrpt_meta_conf` VALUES ('49', '32', '文本', 'text', '文本', '100', '', '2015-02-10 11:14:22', '2015-02-10 11:14:22');
MERGE INTO `ezrpt_meta_conf` VALUES ('50', '32', '首字母', 'capital', '首字母', '100', '首字母', '2015-02-10 11:15:27', '2015-02-10 11:15:27');
MERGE INTO `ezrpt_meta_conf` VALUES ('52', '33', '常见可选列', 'optionalColumn', 'optionalColumn', '0', '', '2015-02-10 10:23:56', '2015-02-10 10:23:56');
MERGE INTO `ezrpt_meta_conf` VALUES ('53', '52', '二氧化氮', 'no2', '二氧化氮(No2)', '100', '', '2015-02-10 11:20:21', '2015-02-10 11:20:21');
MERGE INTO `ezrpt_meta_conf` VALUES ('54', '52', '臭氧', 'o3', '臭氧(O3)', '100', '', '2015-02-10 11:20:55', '2015-02-10 11:20:55');
MERGE INTO `ezrpt_meta_conf` VALUES ('55', '32', '代号', 'code', '代号', '100', '', '2015-02-10 11:15:09', '2015-02-10 11:15:09');
MERGE INTO `ezrpt_meta_conf` VALUES ('56', '1', '一氧化碳', 'co', '一氧化碳(Co)', '100', '一氧化碳', '2015-02-10 11:06:26', '2015-02-10 11:06:26');
MERGE INTO `ezrpt_meta_conf` VALUES ('57', '1', '二氧化氮', 'no2', '二氧化氮(No2)', '100', '二氧化氮', '2015-02-10 11:06:48', '2015-02-10 11:06:48');
MERGE INTO `ezrpt_meta_conf` VALUES ('58', '1', '臭氧', 'o3', '臭氧(O3)', '100', '臭氧', '2015-02-10 11:07:21', '2015-02-10 11:07:21');
MERGE INTO `ezrpt_meta_conf` VALUES ('59', '15', '天', 'day', '天', '100', '', '2015-02-10 11:17:10', '2015-02-10 11:17:10');
MERGE INTO `ezrpt_meta_conf` VALUES ('60', '15', '小时', 'hour', '小时', '100', '', '2015-02-10 11:17:38', '2015-02-10 11:17:38');
MERGE INTO `ezrpt_meta_conf` VALUES ('61', '15', '月度', 'month', '月度', '100', '', '2015-02-10 11:17:57', '2015-02-10 11:17:57');
MERGE INTO `ezrpt_meta_conf` VALUES ('69', '0', '数据源类型', 'dbType', 'dbType', '10', '数据源类型', '2016-09-20 13:57:36', '2016-09-20 13:57:36');
MERGE INTO `ezrpt_meta_conf` VALUES ('70', '69', 'MySQL', 'mysql', '{\r\n	\"driverClass\":\"com.mysql.jdbc.Driver\",\r\n	\"queryerClass\":\"com.easytoolsoft.easyreport.engine.query.MySqlQueryer\",\r\n	\"jdbcUrl\":\"jdbc:mysql://${host}:${port}/${database}?characterEncoding=${encoding}\"\r\n}', '10', '', '2016-09-20 14:06:02', '2016-09-20 14:06:02');
MERGE INTO `ezrpt_meta_conf` VALUES ('71', '69', 'SQLServer', 'sqlserver', '{\r\n	\"driverClass\":\"com.microsoft.sqlserver.jdbc.SQLServerDriver\",\r\n	\"queryerClass\":\"com.easytoolsoft.easyreport.engine.query.SqlServerQueryer\",\r\n	\"jdbcUrl\":\"jdbc:sqlserver://${host};databaseName=${database}\"\r\n}', '10', '', '2016-09-20 14:08:09', '2016-09-20 14:08:09');
MERGE INTO `ezrpt_meta_conf` VALUES ('72', '0', '数据源连接池类型', 'dbPoolType', 'dbPoolType', '10', '', '2016-09-20 14:09:21', '2016-09-20 14:09:21');
MERGE INTO `ezrpt_meta_conf` VALUES ('73', '72', 'C3P0', 'c3p0', '{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.C3p0DataSourcePool\",\r\n	\"options\":{\r\n		\"initialPoolSize\":3,\r\n		\"minPoolSize\":1,\r\n		\"maxPoolSize\":10,\r\n		\"maxStatements\":50,\r\n		\"maxIdleTime\":1000,\r\n		\"acquireIncrement\":3,\r\n		\"acquireRetryAttempts\":30,\r\n		\"idleConnectionTestPeriod\":60,\r\n		\"breakAfterAcquireFailure\":false,\r\n		\"testConnectionOnCheckout\":false\r\n	}\r\n}', '10', '', '2016-09-20 14:10:46', '2016-09-20 14:10:46');
MERGE INTO `ezrpt_meta_conf` VALUES ('74', '72', 'DBCP2', 'dbcp2', '{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.DBCP2DataSourcePool\",\r\n	\"options\":{\r\n		\"initialSize\":3,\r\n		\"maxIdle\":20,\r\n		\"minIdle\":1,\r\n		\"logAbandoned\":true,\r\n		\"removeAbandoned\":true,\r\n		\"removeAbandonedTimeout\":180,\r\n		\"maxWait\":1000\r\n	}\r\n}', '10', '', '2016-09-20 14:11:03', '2016-09-20 14:11:03');
MERGE INTO `ezrpt_meta_conf` VALUES ('75', '72', 'Druid', 'druid', '{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.DruidDataSourcePool\",\r\n	\"options\":{\r\n		\"initialSize\": 3,\r\n		\"maxActive\": 20,\r\n		\"minIdle\": 1,\r\n		\"maxWait\": 60000,\r\n		\"timeBetweenEvictionRunsMillis\": 60000,\r\n		\"minEvictableIdleTimeMillis\": 300000,\r\n		\"testWhileIdle\": true,\r\n		\"testOnBorrow\": false,\r\n		\"testOnReturn\": false,\r\n		\"maxOpenPreparedStatements\": 20,\r\n		\"removeAbandoned\": true,\r\n		\"removeAbandonedTimeout\": 1800,\r\n		\"logAbandoned\": true\r\n	}\r\n}', '10', '', '2016-09-20 14:11:18', '2016-09-20 14:11:18');
MERGE INTO `ezrpt_meta_conf` VALUES ('76', '72', '无连接池', 'noDbPool', '{\r\n	\"poolClass\":\"com.easytoolsoft.easyreport.engine.dbpool.NoDataSourcePool\",\r\n	\"options\":{}\r\n}', '10', '', '2016-09-20 14:14:32', '2016-09-20 14:14:32');

-- ----------------------------
-- Records of ezrpt_meta_datasource
-- ----------------------------
MERGE INTO `ezrpt_meta_datasource` VALUES ('49', '8b2d3b62-0c08-4d62-a666-8bb97fc9c222', '中国天气(SQLServer)', 'com.microsoft.sqlserver.jdbc.SQLServerDriver', 'jdbc:sqlserver://localhost;databaseName=ChinaWeatherAir', 'sa', 'ddd', 'com.easytoolsoft.easyreport.engine.query.SqlServerQueryer', 'com.easytoolsoft.easyreport.engine.dbpool.DBCP2DataSourcePool', '{\"initialSize\":3,\"maxIdle\":20,\"minIdle\":1,\"logAbandoned\":true,\"removeAbandoned\":true,\"removeAbandonedTimeout\":180,\"maxWait\":1000}', '', '2015-01-27 14:32:32', '2015-01-27 14:32:32');
MERGE INTO `ezrpt_meta_datasource` VALUES ('50', '6423b076-ce78-47fc-8c25-c005be2b85af', '中国天气(MySQL)', 'com.mysql.jdbc.Driver', 'jdbc:mysql://localhost:3306/china_weather_air?characterEncoding=UTF-8', 'root', 'ddd', 'com.easytoolsoft.easyreport.engine.query.MySqlQueryer', 'com.easytoolsoft.easyreport.engine.dbpool.DruidDataSourcePool', '{\"initialSize\":3,\"maxActive\":20,\"minIdle\":1,\"maxWait\":60000,\"timeBetweenEvictionRunsMillis\":60000,\"minEvictableIdleTimeMillis\":300000,\"testWhileIdle\":true,\"testOnBorrow\":false,\"testOnReturn\":false,\"maxOpenPreparedStatements\":20,\"removeAbandoned\":true,\"removeAbandonedTimeout\":1800,\"logAbandoned\":true}', '', '2015-01-29 17:54:32', '2015-01-29 17:54:32');

-- ----------------------------
-- Records of ezrpt_meta_report
-- ----------------------------
MERGE INTO `ezrpt_meta_report` VALUES ('712', 'bb7be838-31ae-41fe-ae63-4ae8efe5c138', '711', '50', '2014年北京每天空气指数', 'select * from fact_air_cn where area="北京市" and year(dt)="2014"', '[{\"ordinal\":0,\"name\":\"dt\",\"text\":\"日期\",\"dataType\":\"DATE\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"aqi\",\"text\":\"空气指数\",\"dataType\":\"INT\",\"expression\":\"\",\"width\":11,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"aqi_range\",\"text\":\"AQI指数范围\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气等级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"pm25\",\"text\":\"PM2.5细颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"pm10\",\"text\":\"PM10可吸入颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"so2\",\"text\":\"二氧化硫(So2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"co\",\"text\":\"一氧化碳(Co)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"no2\",\"text\":\"二氧化氮(No2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false},{\"ordinal\":0,\"name\":\"o3\",\"text\":\"臭氧(O3)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"footings\":false,\"displayInMail\":false,\"optional\":false}]', '', '{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":7}', '1', '100', '', 'admin', '2015-01-29 17:59:14', '2016-09-26 18:40:37');
MERGE INTO `ezrpt_meta_report` VALUES ('713', '3180eafe-e4a2-4b5c-828e-e054043f6e87', '711', '50', '30天内城市空气指数', 'select * from fact_air_cn where area in ("${area}") and dt>="${startTime}" and dt <"${endTime}"', '[{\"ordinal\":0,\"name\":\"dt\",\"text\":\"日期\",\"dataType\":\"DATE\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"area\",\"text\":\"城市\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"format\":\"\",\"comment\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"1\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"aqi\",\"text\":\"空气指数\",\"dataType\":\"INT\",\"expression\":\"\",\"width\":11,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"aqi_range\",\"text\":\"AQI指数范围\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":20,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"quality\",\"text\":\"空气等级\",\"dataType\":\"VARCHAR\",\"expression\":\"\",\"width\":10,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"pm25\",\"text\":\"PM2.5细颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"pm10\",\"text\":\"PM10可吸入颗粒物\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"so2\",\"text\":\"二氧化硫(So2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"co\",\"text\":\"一氧化碳(Co)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":false,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"no2\",\"text\":\"二氧化氮(No2)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":true,\"footings\":false,\"displayInMail\":false},{\"ordinal\":0,\"name\":\"o3\",\"text\":\"臭氧(O3)\",\"dataType\":\"FLOAT\",\"expression\":\"\",\"width\":12,\"decimals\":\"0\",\"type\":\"3\",\"sortType\":\"0\",\"extensions\":false,\"hidden\":false,\"percent\":false,\"optional\":true,\"footings\":false,\"displayInMail\":false}]', '[{\"name\":\"area\",\"text\":\"城市\",\"defaultValue\":\"北京市\",\"defaultText\":\"北京市\",\"dataType\":\"string\",\"width\":\"100\",\"required\":true,\"formElement\":\"selectMul\",\"dataSource\":\"sql\",\"content\":\"select distinct area as name,area as text from fact_air_cn\",\"autoComplete\":false}]', '{\"layout\":2,\"statColumnLayout\":1,\"dataRange\":30}', '1', '100', '', 'admin', '2015-01-29 18:04:46', '2016-09-26 18:40:37');

