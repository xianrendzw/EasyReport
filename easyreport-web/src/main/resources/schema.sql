

set mode MySQL;



-- ----------------------------
-- Table structure for ezrpt_member_module
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_member_module` (
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
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '系统模块记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统模块记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_module_code` (`code`),
  UNIQUE KEY `uk_module_parent_id_name` (`parent_id`,`name`),
  KEY `ix_path` (`path`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_member_permission
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_member_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统操作标识',
  `module_id` int(11) NOT NULL COMMENT '系统模块标识',
  `name` varchar(50) NOT NULL COMMENT '系统操作名称',
  `code` varchar(50) NOT NULL COMMENT '系统操作唯一代号',
  `sequence` int(11) NOT NULL COMMENT '系统操作的排序顺序',
  `comment` varchar(50) NOT NULL COMMENT '系统操作备注',
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '系统操作记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统操作记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permisson_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_member_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_member_role` (
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
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '系统角色记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统角色记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`code`),
  UNIQUE KEY `uk_role_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_member_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_member_user` (
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
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '系统用户记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '系统用户记录更新时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_account` (`account`),
  UNIQUE KEY `uk_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_meta_category
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_meta_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父分类',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `path` varchar(500) NOT NULL COMMENT '树型结构路径从根id到当前id的路径',
  `has_child` tinyint(1) NOT NULL COMMENT '是否为子类别1为是，0为否',
  `status` int(11) NOT NULL COMMENT '状态（1表示启用，0表示禁用，默认为0)',
  `sequence` int(11) NOT NULL COMMENT '节点在其父节点中的顺序',
  `comment` varchar(500) NOT NULL COMMENT '说明备注',
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_parent_id_name_category` (`parent_id`,`name`),
  KEY `ix_path_category` (`path`)
) ENGINE=InnoDB AUTO_INCREMENT=717 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_meta_conf
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_meta_conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置字典ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(50) NOT NULL COMMENT '配置key',
  `value` varchar(1000) NOT NULL COMMENT '配置值',
  `sequence` int(11) NOT NULL COMMENT '显示顺序',
  `comment` varchar(50) NOT NULL COMMENT '配置说明',
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ezrpt_meta_conf_uk_pid_key` (`parent_id`,`key`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_meta_datasource
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_meta_datasource` (
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
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  KEY `AK_uk_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_meta_report
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_meta_report` (
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
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_id_name` (`category_id`,`name`),
  UNIQUE KEY `uk_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=721 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ezrpt_meta_report_history
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_meta_report_history` (
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
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ezrpt_meta_report_history
-- ----------------------------

-- ----------------------------
-- Table structure for ezrpt_schedule_task
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_schedule_task` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表任务ID',
  `report_ids` varchar(1024) NOT NULL COMMENT '报表ID列表',
  `cron_expr` varchar(100) NOT NULL COMMENT '报表任务调度crontab表达式',
  `type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '任务类型,1为邮件任务,2为手机短信任务,其他保留.默认为1',
  `options` text NOT NULL COMMENT '配置选项(JSON格式)',
  `template` text NOT NULL COMMENT '任务内容模板',
  `comment` varchar(100) NOT NULL COMMENT '报表任务说明',
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '报表任务记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报表任务记录修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ezrpt_schedule_task
-- ----------------------------

-- ----------------------------
-- Table structure for ezrpt_sys_conf
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_sys_conf` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '配置字典ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `key` varchar(50) NOT NULL COMMENT '配置key',
  `value` varchar(1000) NOT NULL COMMENT '配置值',
  `sequence` int(11) NOT NULL COMMENT '显示顺序',
  `comment` varchar(50) NOT NULL COMMENT '配置说明',
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ezrpt_sys_conf_uk_key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ezrpt_sys_conf
-- ----------------------------

-- ----------------------------
-- Table structure for ezrpt_sys_event
-- ----------------------------
CREATE TABLE IF NOT EXISTS `ezrpt_sys_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志标识',
  `source` varchar(100) NOT NULL COMMENT '日志来源',
  `user_id` int(11) NOT NULL COMMENT '操作用户id',
  `account` varchar(50) NOT NULL COMMENT '操作用户账号',
  `level` varchar(10) NOT NULL COMMENT '日志级别',
  `message` text NOT NULL COMMENT '日志信息',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '日志发生的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8;
