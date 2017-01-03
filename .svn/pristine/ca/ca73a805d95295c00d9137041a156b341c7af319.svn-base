/*
Navicat MySQL Data Transfer

Source Server         : 172.17.168.181
Source Server Version : 50610
Source Host           : 172.17.168.181:3306
Source Database       : passform

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2014-11-04 11:27:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_audit
-- ----------------------------
DROP TABLE IF EXISTS `data_audit`;
CREATE TABLE `data_audit` (
  `IP` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名',
  `sendCount` tinyint(3) DEFAULT NULL COMMENT '发送短信的次数',
  `failCount` int(10) unsigned DEFAULT '0' COMMENT '失败次数',
  `operateTime` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `lockStatus` int(1) DEFAULT NULL COMMENT '是否锁定标识1锁定，0正常',
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='审计表';

-- ----------------------------
-- Table structure for data_shortmessage
-- ----------------------------
DROP TABLE IF EXISTS `data_shortmessage`;
CREATE TABLE `data_shortmessage` (
  `id` int(255) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL COMMENT '域用户名',
  `authCode` varchar(20) DEFAULT NULL COMMENT '验证码',
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `serialNumber` int(50) DEFAULT NULL COMMENT '序列号',
  `expiresStatus` tinyint(1) DEFAULT NULL COMMENT '是否过期标识',
  `emailAddress` varchar(200) DEFAULT NULL COMMENT '邮件地址',
  `phoneNum` varchar(11) DEFAULT NULL COMMENT '电话号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='短信表';

-- ----------------------------
-- Table structure for sys_config_server
-- ----------------------------
DROP TABLE IF EXISTS `sys_config_server`;
CREATE TABLE `sys_config_server` (
  `IP` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `port` int(10) DEFAULT NULL COMMENT '端口号',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名',
  `userPassWd` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `sysType` int(5) unsigned NOT NULL COMMENT '系统配置类型(1,ldap,2,sms,3,email)',
  PRIMARY KEY (`sysType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表(服务端)';

-- ----------------------------
-- Table structure for sys_lock_time_gap
-- ----------------------------
DROP TABLE IF EXISTS `sys_lock_time_gap`;
CREATE TABLE `sys_lock_time_gap` (
  `type` tinyint(5) unsigned NOT NULL COMMENT '审计类型，1ip+user，2，ip，3，user',
  `lockTime` tinyint(5) unsigned NOT NULL COMMENT '锁定时间，分钟为单位',
  `sendCount` tinyint(5) unsigned zerofill NOT NULL COMMENT '允许失败次数',
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_opt_logs
-- ----------------------------
DROP TABLE IF EXISTS `sys_opt_logs`;
CREATE TABLE `sys_opt_logs` (
  `moduleName` varchar(50) DEFAULT NULL COMMENT '模块名',
  `operator` varchar(50) DEFAULT NULL COMMENT '操作人',
  `operateTime` date DEFAULT NULL COMMENT '操作时间',
  `operateStatus` int(5) unsigned DEFAULT NULL COMMENT '操作是否成功标识0正常 1失败'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Table structure for sys_send_time_gap
-- ----------------------------
DROP TABLE IF EXISTS `sys_send_time_gap`;
CREATE TABLE `sys_send_time_gap` (
  `type` int(255) unsigned NOT NULL COMMENT '配置类型(1,email ,2,phone)',
  `sendCount` int(10) DEFAULT NULL COMMENT '发送次数',
  `timeGap` int(5) unsigned NOT NULL COMMENT '时间间隔,以分钟为单位',
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;