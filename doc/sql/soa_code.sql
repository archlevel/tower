/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.6.63
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : 10.10.6.63
 Source Database       : code_db

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 12/09/2015 10:16:14 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `code`
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `no` varchar(30) DEFAULT NULL COMMENT '编号',
  `name` varchar(30) DEFAULT NULL COMMENT '名称',
  `item_no` varchar(30) DEFAULT NULL COMMENT '节点编号',
  `item_key` varchar(30) DEFAULT NULL COMMENT '节点键',
  `item_value` varchar(30) DEFAULT NULL COMMENT '节点值',
  `sequence` int(11) DEFAULT NULL COMMENT '排序号',
  `is_use` varchar(10) NOT NULL DEFAULT '1' COMMENT '是否使用',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=379 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
