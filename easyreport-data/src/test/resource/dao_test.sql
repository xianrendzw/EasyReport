/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50532
Source Host           : localhost:3306
Source Database       : dao_test

Target Server Type    : MYSQL
Target Server Version : 50532
File Encoding         : 65001

Date: 2014-07-04 15:34:32
*/

CREATE DATABASE `dao_test` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci; 

USE dao_test;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `parent_name` varchar(50) DEFAULT NULL,
  `comment` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
