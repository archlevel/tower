/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.6.63
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : 10.10.6.63
 Source Database       : soafw_db

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 12/09/2015 10:16:52 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `soa_cache_version`
-- ----------------------------
DROP TABLE IF EXISTS `soa_cache_version`;
CREATE TABLE `soa_cache_version` (
  `obj_name` varchar(32) DEFAULT NULL,
  `rec_version` bigint(20) NOT NULL DEFAULT '0',
  `tab_version` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`obj_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

SET FOREIGN_KEY_CHECKS = 1;
