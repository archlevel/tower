/*
 Navicat Premium Data Transfer

 Source Server         : tsl-dev
 Source Server Type    : MySQL
 Source Server Version : 50622
 Source Host           : db.corp.tower.com
 Source Database       : soafw_db

 Target Server Type    : MySQL
 Target Server Version : 50622
 File Encoding         : utf-8

 Date: 11/01/2015 15:39:51 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `soa_monitor`
-- ----------------------------
DROP TABLE IF EXISTS `soa_monitor`;
CREATE TABLE `soa_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '纪录id',
  `ip` varchar(30) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `applications` varchar(30) DEFAULT NULL,
  `protocols` varchar(30) DEFAULT NULL,
  `interfaces` varchar(30) DEFAULT NULL,
  `methods` varchar(255) DEFAULT NULL,
  `params` varchar(255) DEFAULT NULL,
  `heartbeats` int(11) DEFAULT NULL COMMENT '心跳数量',
  `requests` int(11) DEFAULT NULL COMMENT '请求数量',
  `performances` int(11) DEFAULT NULL COMMENT '性能每分钟90%的毫秒数量',
  `updatetime` int(11) DEFAULT NULL COMMENT 'yyyyMMddhhmm',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
