/*
 Navicat Premium Data Transfer

 Source Server         : dev_db
 Source Server Type    : MySQL
 Source Server Version : 50628
 Source Host           : 192.168.1.101
 Source Database       : soafw_db

 Target Server Type    : MySQL
 Target Server Version : 50628
 File Encoding         : utf-8

 Date: 03/10/2016 11:17:09 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `soa_sp`
-- ----------------------------
DROP TABLE IF EXISTS `soa_sp`;
CREATE TABLE `soa_sp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_name` varchar(100) DEFAULT NULL,
  `start_port` int(11) DEFAULT NULL,
  `stop_port` int(11) DEFAULT NULL,
  `service_port` int(11) DEFAULT NULL,
  `sp_description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`sp_name`)
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `soa_sp`
-- ----------------------------
BEGIN;
INSERT INTO `soa_sp` VALUES ('1', 'hello', '6080', '7080', '20880', 'hello');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
