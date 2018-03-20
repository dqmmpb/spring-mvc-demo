/*
Navicat MySQL Data Transfer

Source Server         : 本地测试环境
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : xiaoma_data

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-02-20 23:02:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) NOT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '密码盐值',
  `name` varchar(128) DEFAULT NULL COMMENT '姓名',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `department` varchar(255) DEFAULT NULL COMMENT '部门',
  `status` int(5) NOT NULL DEFAULT '1' COMMENT '状态：1启用，2禁用，默认1',
  `state` char(1) NOT NULL DEFAULT 'A' COMMENT 'A-可用;X-不可用',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xm_manager
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `state` char(1) NOT NULL DEFAULT 'A' COMMENT 'A-可用;X-不可用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_session
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_session`;
CREATE TABLE `sys_user_session` (
  `user_session_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL COMMENT '用户token',
  `user_id` bigint(9) NOT NULL COMMENT '用户id',
  `state` char(1) NOT NULL DEFAULT 'A' COMMENT 'A-可用;X-不可用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_update_time` datetime NOT NULL,
  `ua` varchar(255) NOT NULL COMMENT 'useragent',
  PRIMARY KEY (`user_session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_session
-- ----------------------------

-- ----------------------------
-- Table structure for sys_priv
-- ----------------------------
DROP TABLE IF EXISTS `sys_priv`;
CREATE TABLE `sys_priv` (
  `priv_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_priv_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父节点id',
  `priv_code` varchar(255) NOT NULL COMMENT '权限code',
  `priv_name` varchar(60) NOT NULL COMMENT '权限名称',
  `type` int(9) NOT NULL COMMENT '0-目录;1-菜单;2-数据',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单url路径',
  `path` varchar(255) DEFAULT NULL COMMENT '菜单path路径',
  `description` varchar(1000) NOT NULL COMMENT '权限描述',
  `state` char(1) NOT NULL DEFAULT 'A' COMMENT 'A-可用;X-不可用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`priv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_priv
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(60) NOT NULL COMMENT '角色code',
  `role_name` varchar(60) NOT NULL COMMENT '角色名称',
  `state` char(1) NOT NULL DEFAULT 'A' COMMENT 'A-可用;X-不可用',
  `description` varchar(255) NOT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_priv
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_priv`;
CREATE TABLE `sys_role_priv` (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `priv_id` bigint(20) NOT NULL COMMENT '权限id',
  `state` char(1) NOT NULL DEFAULT 'A' COMMENT 'A-可用;X-不可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`priv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_priv
-- ----------------------------
