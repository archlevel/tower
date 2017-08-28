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

 Date: 12/09/2015 10:17:17 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `soa_exception`
-- ----------------------------
DROP TABLE IF EXISTS `soa_exception`;
CREATE TABLE `soa_exception` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` int(11) DEFAULT '0',
  `type` tinyint(4) DEFAULT '0',
  `message` varchar(500) DEFAULT NULL,
  `spid` tinyint(4) DEFAULT '0',
  `level` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`code`,`type`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
