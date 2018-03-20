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
  `disable_status` int(5) NOT NULL DEFAULT '1' COMMENT '禁用状态：1启用，2禁用，默认1',
  `state` char(1) NOT NULL DEFAULT 'A' COMMENT 'A-可用;X-不可用',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '13819493700', '3fcce7190bf72f848cbfc6a3b1b62616', 've8zPuU493fi23fQh36amg==', null, null, null, '1', 'A', null, '2018-02-20 22:57:02', '2018-02-20 22:57:02');
INSERT INTO `sys_user` VALUES ('2', '13819493701', '9238e845efe5040f6936e2773936b00d', 'HC4oLbK7cVhDnfApn0OcqA==', null, null, null, '1', 'A', null, '2018-02-20 22:57:02', '2018-02-20 22:57:02');
INSERT INTO `sys_user` VALUES ('3', '13819493702', 'd711f8557c452d084b7e5ac36dcfffd8', 'kW2kHUa/TBNJ7xcGDinDmg==', null, null, null, '1', 'A', null, '2018-02-20 22:57:02', '2018-02-20 22:57:02');

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
INSERT INTO `sys_user_role` VALUES ('1', '1', 'A', '2018-02-20 23:00:49', '2018-02-20 23:00:49');
INSERT INTO `sys_user_role` VALUES ('2', '2', 'A', '2018-02-20 23:00:49', '2018-02-20 23:00:49');
INSERT INTO `sys_user_role` VALUES ('3', '3', 'A', '2018-02-20 23:00:49', '2018-02-20 23:01:45');

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
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_priv
-- ----------------------------
INSERT INTO `sys_priv` VALUES ('1', '0', 'sys:priv:dir:manage', '权限管理', '0', '/priv', '/priv', '权限管理', 'A', '2018-03-01 10:42:56', '2018-03-01 10:42:56');
INSERT INTO `sys_priv` VALUES ('2', '1', 'sys:priv:url:list', '权限列表', '1', '/priv/list', '/priv/list', '权限列表', 'A', '2018-03-01 10:42:56', '2018-03-01 10:42:56');
INSERT INTO `sys_priv` VALUES ('3', '1', 'sys:priv:url:add', '新建权限', '1', '/priv/add', '/priv/add', '新建权限', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('4', '1', 'sys:priv:url:edit', '编辑权限', '1', '/priv/edit', '/priv/edit', '编辑权限', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('5', '1', 'sys:priv:url:view', '查看权限', '1', '/priv/view', '/priv/view', '查看权限', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('6', '1', 'sys:priv:add', '新建权限', '2', null, null, '新建权限', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('7', '1', 'sys:priv:delete', '删除权限', '2', null, null, '删除权限', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('8', '1', 'sys:priv:edit', '编辑权限', '2', null, null, '编辑权限', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('9', '1', 'sys:priv:view', '查看权限', '2', null, null, '查看权限', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('10', '0', 'sys:role:dir:manage', '角色管理', '0', '/role', '/role', '角色管理', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('11', '10', 'sys:role:url:list', '角色列表', '1', '/role/list', '/role/list', '角色列表', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('12', '10', 'sys:role:url:add', '新建角色', '1', '/role/add', '/role/add', '新建角色', 'A', '2018-03-01 10:42:57', '2018-03-01 10:42:57');
INSERT INTO `sys_priv` VALUES ('13', '10', 'sys:role:url:edit', '编辑角色', '1', '/role/edit', '/role/edit', '编辑角色', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('14', '10', 'sys:role:url:view', '查看角色', '1', '/role/view', '/role/view', '查看角色', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('15', '10', 'sys:role:add', '新建角色', '2', null, null, '新建角色', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('16', '10', 'sys:role:delete', '删除角色', '2', null, null, '删除角色', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('17', '10', 'sys:role:edit', '编辑角色', '2', null, null, '编辑角色', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('18', '10', 'sys:role:view', '查看角色', '2', null, null, '查看角色', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('19', '10', 'sys:role:allocate', '分配权限', '2', null, null, '分配权限', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('20', '0', 'sys:user:dir:manage', '管理员管理', '0', '/user', '/user', '管理员管理', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('21', '20', 'sys:user:url:list', '管理员列表', '1', '/user/list', '/user/list', '管理员列表', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('22', '20', 'sys:user:url:add', '新建管理员', '1', '/user/add', '/user/add', '新建管理员', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('23', '20', 'sys:user:url:edit', '编辑管理员', '1', '/user/edit', '/user/edit', '编辑管理员', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('24', '20', 'sys:user:url:view', '查看管理员', '1', '/user/view', '/user/view', '查看管理员', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('25', '20', 'sys:user:add', '新建管理员', '2', null, null, '新建管理员', 'A', '2018-03-01 10:42:58', '2018-03-01 10:42:58');
INSERT INTO `sys_priv` VALUES ('26', '20', 'sys:user:delete', '删除管理员', '2', null, null, '删除管理员', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('27', '20', 'sys:user:edit', '编辑管理员', '2', null, null, '编辑管理员', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('28', '20', 'sys:user:view', '查看管理员', '2', null, null, '查看管理员', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('29', '20', 'sys:user:allocate', '分配角色', '2', null, null, '分配角色', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('30', '20', 'sys:user:lock', '禁用管理员', '2', null, null, '禁用管理员', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('31', '20', 'sys:user:unlock', '启用管理员', '2', null, null, '启用管理员', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('32', '20', 'sys:user:resetPwd', '重置密码', '2', null, null, '重置密码', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('33', '0', 'sys:profile:manage', '个人信息', '1', '/profile/view', '/profile/view', '个人信息', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');
INSERT INTO `sys_priv` VALUES ('34', '33', 'sys:profile:changePwd', '修改密码', '2', null, null, '修改密码', 'A', '2018-03-01 10:42:59', '2018-03-01 10:42:59');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'SuperAdmin', '超级管理员', 'A', '超级管理员', '2018-02-20 22:49:53', '2018-02-20 22:49:53');
INSERT INTO `sys_role` VALUES ('2', 'Admin', '管理员', 'A', '管理员', '2018-02-20 22:49:53', '2018-02-20 22:49:53');
INSERT INTO `sys_role` VALUES ('3', 'User', '普通用户', 'A', '普通用户', '2018-02-20 22:49:53', '2018-02-20 22:49:53');

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
INSERT INTO `sys_role_priv` VALUES ('1', '1', 'A', '2018-03-01 10:59:16', '2018-03-01 10:59:16');
INSERT INTO `sys_role_priv` VALUES ('1', '2', 'A', '2018-03-01 10:59:16', '2018-03-01 10:59:16');
INSERT INTO `sys_role_priv` VALUES ('1', '3', 'A', '2018-03-01 10:59:16', '2018-03-01 10:59:16');
INSERT INTO `sys_role_priv` VALUES ('1', '4', 'A', '2018-03-01 10:59:16', '2018-03-01 10:59:16');
INSERT INTO `sys_role_priv` VALUES ('1', '5', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '6', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '7', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '8', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '9', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '10', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '11', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '12', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '13', 'A', '2018-03-01 10:59:17', '2018-03-01 10:59:17');
INSERT INTO `sys_role_priv` VALUES ('1', '14', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '15', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '16', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '17', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '18', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '19', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '20', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '21', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '22', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '23', 'A', '2018-03-01 10:59:18', '2018-03-01 10:59:18');
INSERT INTO `sys_role_priv` VALUES ('1', '24', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '25', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '26', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '27', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '28', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '29', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '30', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '31', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '32', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '33', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('1', '34', 'A', '2018-03-01 10:59:19', '2018-03-01 10:59:19');
INSERT INTO `sys_role_priv` VALUES ('2', '33', 'A', '2018-03-01 10:59:24', '2018-03-01 10:59:24');
INSERT INTO `sys_role_priv` VALUES ('2', '34', 'A', '2018-03-01 10:59:24', '2018-03-01 10:59:24');
INSERT INTO `sys_role_priv` VALUES ('3', '33', 'A', '2018-03-01 10:59:28', '2018-03-01 10:59:28');
INSERT INTO `sys_role_priv` VALUES ('3', '34', 'A', '2018-03-01 10:59:28', '2018-03-01 10:59:28');
