/*
Navicat MySQL Data Transfer

Source Server         : docker_mysql
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : CRYPTIC_DATA

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-04 13:50:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for T_USER
-- ----------------------------
DROP TABLE IF EXISTS `T_USER`;
CREATE TABLE `T_USER` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id自增主键',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `phone` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
