/*
 Navicat Premium Data Transfer

 Source Server         : 10.10.6.63
 Source Server Type    : MySQL
 Source Server Version : 50617
 Source Host           : 10.10.6.63
 Source Database       : tsl_db

 Target Server Type    : MySQL
 Target Server Version : 50617
 File Encoding         : utf-8

 Date: 12/09/2015 10:19:49 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `bail_balance`
-- ----------------------------
DROP TABLE IF EXISTS `bail_balance`;
CREATE TABLE `bail_balance` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `category` varchar(20) NOT NULL COMMENT '保证金类别',
  `type` varchar(20) DEFAULT NULL COMMENT '保证金类型（暂时没有用到这个字段）',
  `category_number` varchar(64) NOT NULL COMMENT '选择保证金类别，对应的选择的id',
  `begin_balance` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '期初余额',
  `end_balance` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '期末余额',
  `recharge_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '充值金额',
  `debit_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '扣款金额(冻结金额)',
  `within_merchant_id` varchar(64) DEFAULT NULL COMMENT '境内商户ID',
  `collect_date` date DEFAULT NULL COMMENT '汇总日期',
  `is_real_time` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否是实时余额，0否，1是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `bail_recharge_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '保证金， 充值金额（作废）',
  `bail_debit_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '保证金， 扣款金额，（作废）',
  `debit_recharge_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '扣款， 充值金额（作废）',
  `debit_debit_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '扣款， 扣款金额（作废）',
  `xxpf_bail` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '先行赔付',
  `xysf_bail` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '行邮税',
  `tk_bail` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '退款',
  `shfk_bail` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '商户罚款',
  `fx_bail` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '分销',
  `cz_bail` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '充值',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bail_balance_ccn_uq` (`category`,`category_number`,`collect_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44931 DEFAULT CHARSET=utf8 COMMENT='保证金余额表';

-- ----------------------------
--  Table structure for `bail_balance_details`
-- ----------------------------
DROP TABLE IF EXISTS `bail_balance_details`;
CREATE TABLE `bail_balance_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `bail_balance_id` bigint(20) NOT NULL COMMENT '保证金余额表ID',
  `category` varchar(20) NOT NULL COMMENT '保证金类别',
  `category_number` varchar(64) NOT NULL COMMENT '选择保证金类别，对应的选择的id',
  `type` varchar(20) NOT NULL COMMENT '保证金类型',
  `recharge_amount` decimal(20,0) NOT NULL COMMENT '充值金额',
  `debit_amount` decimal(20,0) NOT NULL COMMENT '扣款金额',
  `end_balance` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '期末余额',
  `collect_date` date NOT NULL COMMENT '汇总日期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `bail_balance_details_category_index` (`category`,`type`,`category_number`,`collect_date`) USING BTREE,
  KEY `bail_balance_fk_details_index` (`bail_balance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=309561 DEFAULT CHARSET=utf8 COMMENT='保证金余额明细（台账）表';

-- ----------------------------
--  Table structure for `cf_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `cf_orderinfo`;
CREATE TABLE `cf_orderinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `orderst` varchar(10) NOT NULL COMMENT '订单状态',
  `currency` varchar(6) DEFAULT NULL COMMENT '收款币种',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `pay_channel` varchar(12) NOT NULL COMMENT '支付方式',
  `pay_status` varchar(12) DEFAULT NULL COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT 'ECC支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `activities_amt` int(20) unsigned zerofill DEFAULT NULL COMMENT '活动金额',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `custid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `merchant_date` varchar(8) DEFAULT NULL COMMENT '商户日期[格式为:YYYYMMDD]',
  `subamt` int(20) NOT NULL COMMENT '第三方支付金额',
  `billing_amt` int(20) DEFAULT NULL COMMENT '计费金额',
  `settle_amt` int(20) DEFAULT NULL COMMENT '结算金额',
  `cptime` datetime DEFAULT NULL COMMENT 'CP平台时间',
  `spid` varchar(64) DEFAULT NULL COMMENT '网关号',
  `acctp` varchar(20) DEFAULT NULL COMMENT '借贷标识',
  `paytype` varchar(20) NOT NULL COMMENT '交易类型',
  `currency_code` varchar(10) DEFAULT NULL COMMENT '货币代码',
  `cpdate` varchar(8) DEFAULT NULL COMMENT 'CP日期',
  `old_orderno` varchar(64) DEFAULT NULL COMMENT '原始订单号',
  `old_subamt` int(20) DEFAULT NULL COMMENT '原始支付金额',
  `channel` varchar(12) NOT NULL COMMENT '交易渠道',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `ba_date` varchar(12) DEFAULT NULL COMMENT '平账日期',
  `ba_user_id` varchar(64) DEFAULT NULL COMMENT '平账操作人',
  `exchange_status` char(1) NOT NULL DEFAULT '0' COMMENT '购汇返回状态[默认0:未购汇;]',
  `remark` varchar(300) DEFAULT NULL COMMENT '差分备注',
  `remark_user_id` varchar(128) DEFAULT NULL COMMENT '填写备注人的id',
  `remark_time` datetime DEFAULT NULL COMMENT '填写备注时间',
  `pz_status` varchar(10) NOT NULL DEFAULT '0' COMMENT '是否平帐状态， 0:未平账 1:已平账',
  `pz_time` datetime DEFAULT NULL COMMENT '平帐时间',
  `pz_user_id` varchar(50) DEFAULT NULL COMMENT '平帐人id',
  `ecc_uptime` datetime DEFAULT NULL COMMENT 'ECC订单记录更新时间',
  `ship_via` varchar(30) DEFAULT NULL COMMENT '快递公司编号',
  `tracking_number` varchar(64) DEFAULT NULL COMMENT '运单号',
  `weight` int(20) DEFAULT NULL COMMENT '订单重量',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `order_amt` int(20) DEFAULT NULL COMMENT '订单现金总额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣总额',
  `pf_currency` varchar(6) DEFAULT NULL COMMENT '购汇币种',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cf_orderinfo_orderno_uq` (`orderno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='平账差异订单金额信息表';

-- ----------------------------
--  Table structure for `cp_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `cp_orderinfo`;
CREATE TABLE `cp_orderinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderno` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `merchant_date` varchar(8) DEFAULT NULL COMMENT '商户日期[格式为:YYYYMMDD]',
  `subamt` int(20) NOT NULL DEFAULT '0' COMMENT '支付金额',
  `billing_amt` int(20) DEFAULT NULL COMMENT '计费金额',
  `settle_amt` int(20) DEFAULT NULL COMMENT '结算金额',
  `cptime` datetime DEFAULT NULL COMMENT 'CP平台时间',
  `spid` varchar(64) DEFAULT NULL COMMENT '网关号',
  `acctp` varchar(20) DEFAULT NULL COMMENT '借贷标识',
  `paytype` varchar(20) NOT NULL COMMENT '交易类型',
  `currency_code` varchar(10) DEFAULT NULL COMMENT '货币代码',
  `cpdate` varchar(8) DEFAULT NULL COMMENT 'CP日期',
  `cpsn` varchar(32) NOT NULL COMMENT 'CP流水号',
  `old_orderno` varchar(64) DEFAULT NULL COMMENT '原始订单号',
  `old_subamt` int(20) DEFAULT NULL COMMENT '原始支付金额',
  `channel` varchar(12) NOT NULL DEFAULT '117' COMMENT '交易渠道(默认为银联)',
  `ba_flag` char(1) NOT NULL DEFAULT '0' COMMENT '平账状态[0:未平账 1:已平账]',
  `ba_user_id` varchar(30) DEFAULT NULL COMMENT '平账人',
  `ba_date` varchar(30) DEFAULT NULL COMMENT '平账日期',
  `inserttime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `to_flag` varchar(20) DEFAULT '30' COMMENT '平帐，差异查询以后过滤标识 10进入了平帐  20进入差异 30都没有进入',
  `realorderno` varchar(64) DEFAULT NULL,
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `pay_channel_code` char(255) DEFAULT NULL,
  `data_source` varchar(20) DEFAULT 'system' COMMENT '数据来源，system=系统插入，manual=手动插入',
  `billing_amt_state` varchar(20) NOT NULL DEFAULT '0' COMMENT '计费金额状态，1=已经统计，0=未统计',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cpsn_uq` (`cpsn`,`orderno`,`paytype`) USING BTREE,
  KEY `idx_realorderno` (`realorderno`) USING BTREE,
  KEY `idx_ba_flag` (`ba_flag`) USING BTREE,
  KEY `idx_cptime` (`cptime`) USING BTREE,
  KEY `idx_order` (`orderno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1266953 DEFAULT CHARSET=utf8 COMMENT='银联订单信息表，通过银联接口获取的订单镜像，作为清算系统的银联数据源';

-- ----------------------------
--  Table structure for `deduction`
-- ----------------------------
DROP TABLE IF EXISTS `deduction`;
CREATE TABLE `deduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deduct_no` char(16) DEFAULT NULL COMMENT '扣款单编号',
  `merchant_no` char(16) DEFAULT NULL COMMENT '商户号',
  `margin_type` char(12) DEFAULT NULL COMMENT '保证金类别',
  `deduct_type` char(2) DEFAULT NULL COMMENT '扣款单类型',
  `adjust_type` char(2) DEFAULT NULL COMMENT '调整类型 01-调整 02-调减',
  `source_type` char(16) DEFAULT NULL COMMENT '扣款来源',
  `create_type` char(2) DEFAULT NULL COMMENT '创建类型 01-手工 02-系统',
  `status` char(6) DEFAULT NULL,
  `margin_amt` int(255) DEFAULT NULL COMMENT '保证金管理充值金额',
  `deduct_amt` int(255) unsigned zerofill DEFAULT NULL COMMENT '调整金额',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime(3) DEFAULT NULL,
  `insert_userId` char(12) DEFAULT NULL,
  `update_userId` char(12) DEFAULT NULL,
  `is_delete` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否删除， 1=是，0=否',
  `rsv1` char(20) DEFAULT NULL COMMENT '业务时间',
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) NOT NULL DEFAULT '0' COMMENT '是否已经验证过',
  `is_reverse_audit` char(2) NOT NULL DEFAULT '0' COMMENT '是否反审核， 1=是，0=否',
  `old_deduct_amt` varchar(255) DEFAULT NULL COMMENT '调整金额',
  `reverse_source` char(2) DEFAULT NULL COMMENT '反审核来源，U=更新，D=删除',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `submit_audit_time` datetime DEFAULT NULL COMMENT '提交审核时间',
  `submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '提交审核人id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `confirm_audit_time` datetime DEFAULT NULL COMMENT '审核确认时间',
  `confirm_audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核确认人id',
  `counter_submit_audit_time` datetime DEFAULT NULL COMMENT '反审核提交时间',
  `counter_submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '反审核提交人id',
  `counter_audit_time` datetime DEFAULT NULL COMMENT '反审核审核时间',
  `counter_audit_user_id` varchar(32) DEFAULT NULL COMMENT '反审核审核人id',
  `is_freeze` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否冻结，1=是，0=否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26133 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `deduction_1120bak`
-- ----------------------------
DROP TABLE IF EXISTS `deduction_1120bak`;
CREATE TABLE `deduction_1120bak` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `deduct_no` char(16) DEFAULT NULL COMMENT '扣款单编号',
  `merchant_no` char(16) DEFAULT NULL COMMENT '商户号',
  `margin_type` char(12) DEFAULT NULL COMMENT '保证金类别',
  `deduct_type` char(2) DEFAULT NULL COMMENT '扣款单类型',
  `adjust_type` char(2) DEFAULT NULL COMMENT '调整类型',
  `source_type` char(16) DEFAULT NULL,
  `create_type` char(2) DEFAULT NULL COMMENT '创建类型',
  `status` char(6) DEFAULT NULL,
  `margin_amt` int(255) DEFAULT NULL,
  `deduct_amt` int(255) unsigned zerofill DEFAULT NULL COMMENT '调整类型',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime(3) DEFAULT NULL,
  `insert_userId` char(12) DEFAULT NULL,
  `update_userId` char(12) DEFAULT NULL,
  `is_delete` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否删除， 1=是，0=否',
  `rsv1` char(20) DEFAULT NULL COMMENT '业务时间',
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) NOT NULL DEFAULT '0' COMMENT '是否已经验证过',
  `is_reverse_audit` char(2) NOT NULL DEFAULT '0' COMMENT '是否反审核， 1=是，0=否',
  `old_deduct_amt` varchar(255) DEFAULT NULL COMMENT '调整金额',
  `reverse_source` char(2) DEFAULT NULL COMMENT '反审核来源，U=更新，D=删除',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `submit_audit_time` datetime DEFAULT NULL COMMENT '提交审核时间',
  `submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '提交审核人id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `confirm_audit_time` datetime DEFAULT NULL COMMENT '审核确认时间',
  `confirm_audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核确认人id',
  `counter_submit_audit_time` datetime DEFAULT NULL COMMENT '反审核提交时间',
  `counter_submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '反审核提交人id',
  `counter_audit_time` datetime DEFAULT NULL COMMENT '反审核审核时间',
  `counter_audit_user_id` varchar(32) DEFAULT NULL COMMENT '反审核审核人id',
  `is_freeze` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否冻结，1=是，0=否'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `deduction_dd`
-- ----------------------------
DROP TABLE IF EXISTS `deduction_dd`;
CREATE TABLE `deduction_dd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deduct_no` char(16) DEFAULT NULL COMMENT '扣款单编号',
  `merchant_no` char(16) DEFAULT NULL COMMENT '商户号',
  `margin_type` char(12) DEFAULT NULL COMMENT '保证金类别',
  `deduct_type` char(2) DEFAULT NULL COMMENT '扣款单类型',
  `adjust_type` char(2) DEFAULT NULL COMMENT '调整类型',
  `source_type` char(16) DEFAULT NULL,
  `create_type` char(2) DEFAULT NULL COMMENT '创建类型',
  `status` char(6) DEFAULT NULL,
  `margin_amt` int(255) DEFAULT NULL,
  `deduct_amt` int(255) unsigned zerofill DEFAULT NULL COMMENT '调整类型',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime(3) DEFAULT NULL,
  `insert_userId` char(12) DEFAULT NULL,
  `update_userId` char(12) DEFAULT NULL,
  `is_delete` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否删除， 1=是，0=否',
  `rsv1` char(20) DEFAULT NULL COMMENT '业务时间',
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) NOT NULL DEFAULT '0' COMMENT '是否已经验证过',
  `is_reverse_audit` char(2) NOT NULL DEFAULT '0' COMMENT '是否反审核， 1=是，0=否',
  `old_deduct_amt` varchar(255) DEFAULT NULL COMMENT '调整金额',
  `reverse_source` char(2) DEFAULT NULL COMMENT '反审核来源，U=更新，D=删除',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `submit_audit_time` datetime DEFAULT NULL COMMENT '提交审核时间',
  `submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '提交审核人id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `confirm_audit_time` datetime DEFAULT NULL COMMENT '审核确认时间',
  `confirm_audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核确认人id',
  `counter_submit_audit_time` datetime DEFAULT NULL COMMENT '反审核提交时间',
  `counter_submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '反审核提交人id',
  `counter_audit_time` datetime DEFAULT NULL COMMENT '反审核审核时间',
  `counter_audit_user_id` varchar(32) DEFAULT NULL COMMENT '反审核审核人id',
  `is_freeze` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否冻结，1=是，0=否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14635 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `deduction_easipay`
-- ----------------------------
DROP TABLE IF EXISTS `deduction_easipay`;
CREATE TABLE `deduction_easipay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deduction_no` char(20) DEFAULT NULL COMMENT '扣款单编号',
  `deducton_amt` int(255) DEFAULT NULL COMMENT '划帐金额',
  `status` char(20) DEFAULT NULL COMMENT '逻辑删除字段  N/Y',
  `audit_status` char(20) DEFAULT NULL COMMENT '审核状态',
  `audit_no` char(20) DEFAULT NULL COMMENT '批次号 划帐批次号',
  `deduction_type` char(20) DEFAULT NULL,
  `merchant_id` int(11) DEFAULT NULL COMMENT '商户id',
  `comment` varchar(255) DEFAULT NULL,
  `updatetime` datetime(3) DEFAULT NULL,
  `inserttime` datetime(3) DEFAULT NULL,
  `insert_userid` char(20) DEFAULT NULL,
  `rsv1` varchar(255) DEFAULT NULL COMMENT '划帐时保证金余额',
  `rsv2` varchar(255) DEFAULT NULL COMMENT '境内节点代码',
  `rsv3` varchar(255) DEFAULT NULL,
  `territory_merchant_number` varchar(64) DEFAULT NULL COMMENT '境内商户号',
  `territory_org_number` varchar(64) DEFAULT NULL COMMENT '境内商户组织机构代码',
  `territory_merchant_code` varchar(64) DEFAULT NULL COMMENT '境内商户节点',
  `territory_merchant_name` varchar(128) DEFAULT NULL COMMENT '境内商户名称',
  `trx_serno` char(26) DEFAULT NULL COMMENT '交易流水号',
  `recharge_state` varchar(20) NOT NULL DEFAULT '01' COMMENT '充值状态(01待充值，02充值中，03充值成功，99充值失败)',
  `rdo_time` datetime DEFAULT NULL COMMENT '支付平台处理时间',
  `rtrx_serno` char(26) DEFAULT NULL COMMENT '支付平台交易流水号',
  `rtn_code` char(6) DEFAULT NULL COMMENT '返回代码',
  `ertn_code` varchar(12) DEFAULT NULL COMMENT '扩展返回代码',
  `ertn_info` varchar(128) DEFAULT NULL COMMENT '扩展返回信息',
  `rtn_pay_cur` varchar(6) DEFAULT NULL COMMENT '返回支付币种',
  `rtn_pay_tamt` varchar(20) DEFAULT NULL COMMENT '返回支付总金额',
  `rtn_pay_balance` varchar(20) DEFAULT NULL COMMENT '返回付款账户交易后余额',
  `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `submit_audit_time` datetime DEFAULT NULL COMMENT '提交审核时间',
  `submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '提交审核人id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `confirm_audit_time` datetime DEFAULT NULL COMMENT '审核确认时间',
  `confirm_audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核确认人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `deduction_propay_audit`
-- ----------------------------
DROP TABLE IF EXISTS `deduction_propay_audit`;
CREATE TABLE `deduction_propay_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `deduction_no` char(20) DEFAULT NULL,
  `audit_no` char(20) DEFAULT NULL COMMENT '批次号',
  `merchant_id` char(20) DEFAULT NULL COMMENT '境内商户号',
  `audit_state` varchar(20) DEFAULT NULL COMMENT '审核状态（10=未审核，30=审核通过,40=审核失败）',
  `deduction_amount` decimal(20,0) DEFAULT '0' COMMENT '保证金金额',
  `territory_merchant_number` varchar(64) DEFAULT NULL COMMENT '境内商户号',
  `territory_org_number` varchar(64) DEFAULT NULL COMMENT '境内商户组织机构代码',
  `territory_merchant_code` varchar(64) DEFAULT NULL COMMENT '境内商户节点',
  `territory_merchant_name` varchar(128) DEFAULT NULL COMMENT '境内商户名称',
  `trx_serno` char(26) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `recharge_state` varchar(20) NOT NULL DEFAULT '01' COMMENT '充值状态(01待充值，02充值中，03充值成功，99充值失败)',
  `create_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `rdo_time` datetime(3) DEFAULT NULL COMMENT '支付平台处理时间',
  `rtrx_serno` char(26) DEFAULT NULL COMMENT '支付平台交易流水号',
  `rtn_code` char(6) DEFAULT NULL COMMENT '返回代码',
  `ertn_code` varchar(12) DEFAULT NULL COMMENT '扩展返回代码',
  `ertn_info` varchar(128) DEFAULT NULL COMMENT '扩展返回信息',
  `rtn_pay_cur` varchar(6) DEFAULT NULL COMMENT '返回支付币种',
  `rtn_pay_tamt` varchar(20) DEFAULT NULL COMMENT '返回支付总金额',
  `rtn_pay_balance` varchar(20) DEFAULT NULL COMMENT '返回付款账户交易后余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `diff_report`
-- ----------------------------
DROP TABLE IF EXISTS `diff_report`;
CREATE TABLE `diff_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主建',
  `orderno` varchar(64) DEFAULT NULL COMMENT '订单号',
  `orderst` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '境外商户',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水号',
  `pay_channel` varchar(12) DEFAULT NULL COMMENT '支付渠道',
  `pay_status` varchar(12) DEFAULT NULL COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `freidght` int(11) DEFAULT NULL COMMENT '运费',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `refund_flag` varchar(12) DEFAULT NULL COMMENT '是否退过款',
  `realorderno` varchar(64) DEFAULT NULL COMMENT '第三方支付订单号',
  `subamt` int(20) DEFAULT NULL COMMENT '第三方支付金额',
  `billing_amt` int(20) DEFAULT NULL COMMENT '计费金额',
  `settle_amt` int(20) DEFAULT NULL COMMENT '结算金额',
  `channel` varchar(12) DEFAULT NULL COMMENT '交易渠道',
  `paytype` varchar(12) DEFAULT NULL COMMENT '交易类型',
  `cpsn` varchar(64) DEFAULT NULL COMMENT 'CP流水号',
  `to_flag` varchar(20) DEFAULT NULL COMMENT '平帐，差异查询以后过滤标识',
  `spid` varchar(64) DEFAULT NULL COMMENT '网关号',
  `ba_flag` varchar(64) DEFAULT NULL COMMENT '平账状态[0:未平账 1:已平账]',
  `ba_date` varchar(64) DEFAULT NULL COMMENT '平账日期',
  `cptime` datetime DEFAULT NULL COMMENT 'CP平台时间',
  `pay_channel_code` varchar(64) DEFAULT NULL COMMENT '支付方式(转义后)',
  `diff_type` varchar(20) DEFAULT NULL COMMENT '差分类型，xf=消费差分，tk=退款差分，zf=支付差分',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `ro_order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
  `ro_pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `ro_document_no` varchar(32) DEFAULT NULL COMMENT '单据号',
  `ro_document_type` varchar(20) DEFAULT NULL COMMENT '单据类型',
  `ro_refund_order_no` varchar(64) DEFAULT NULL COMMENT '退款单号',
  `ro_refund_state` varchar(20) DEFAULT NULL COMMENT '退款状态',
  `ro_refund_amount` decimal(20,0) DEFAULT NULL COMMENT '退款金额',
  `ro_refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `ro_original_order_amount` decimal(20,0) DEFAULT NULL COMMENT '原订单金额',
  `ro_refund_amount_difference` decimal(20,0) DEFAULT NULL COMMENT '退款差额',
  `ro_pay_channel` varchar(20) DEFAULT NULL COMMENT '支付渠道， code跟ecc支付渠道一样',
  `ro_pay_refund_state` varchar(20) DEFAULT NULL COMMENT '退款类型',
  `ro_create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人姓名',
  `ro_audit_status` varchar(20) DEFAULT NULL COMMENT '审核状态',
  `ro_audit_user_id` varchar(64) DEFAULT NULL COMMENT '审核用户id',
  `ro_audit_user_name` varchar(100) DEFAULT NULL COMMENT '审核用户名',
  `ro_audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refund_realorderno` varchar(255) DEFAULT NULL COMMENT '退款-订单号',
  `refund_cpsn` varchar(255) DEFAULT NULL COMMENT '退款-流水号',
  `refund_cptime` varchar(255) DEFAULT NULL COMMENT '退款-支付时间',
  `refund_channel` varchar(255) DEFAULT NULL COMMENT '退款-支付渠道',
  `refund_subamt` int(20) DEFAULT NULL COMMENT '退款-支付金额',
  `refund_billing_amt` int(20) DEFAULT NULL COMMENT '退款-计费金额',
  `refund_settle_amt` int(20) DEFAULT NULL COMMENT '退款-结算金额',
  `refund_paytype` varchar(255) DEFAULT NULL COMMENT '退款-交易类型',
  `refund_spid` varchar(64) DEFAULT NULL COMMENT '退款-网关号',
  PRIMARY KEY (`id`),
  KEY `idx_ordertime` (`ordertime`) USING BTREE,
  KEY `idx_orderno` (`orderno`) USING BTREE,
  KEY `idx_realorderno` (`realorderno`) USING BTREE,
  KEY `idx_pay_serialno` (`pay_serialno`) USING BTREE,
  KEY `idx_cpsn` (`cpsn`) USING BTREE,
  KEY `idx_pay_channel` (`pay_channel`) USING BTREE,
  KEY `idx_channel` (`channel`) USING BTREE,
  KEY `idx_ba_flag` (`ba_flag`) USING BTREE,
  KEY `idx_to_flag` (`to_flag`) USING BTREE,
  KEY `idx_pay_channel_code` (`pay_channel_code`) USING BTREE,
  KEY `idx_to_flag_paytype` (`paytype`,`to_flag`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=724419652 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `difference_log`
-- ----------------------------
DROP TABLE IF EXISTS `difference_log`;
CREATE TABLE `difference_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `description` varchar(1000) NOT NULL COMMENT '描述',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='差分执行时间记录';

-- ----------------------------
--  Table structure for `easipay_acc_qur`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_acc_qur`;
CREATE TABLE `easipay_acc_qur` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_code` varchar(10) NOT NULL COMMENT '组织机构号',
  `trx_serno` char(26) DEFAULT NULL COMMENT '交易流水号',
  `req_time` datetime DEFAULT NULL COMMENT '请求时间',
  `rtrx_serno` char(26) DEFAULT NULL COMMENT '应答交易流水号',
  `rdo_time` datetime DEFAULT NULL COMMENT '应答处理时间',
  `acc_cur` varchar(6) DEFAULT NULL COMMENT '币种',
  `acc_balance` varchar(20) DEFAULT NULL COMMENT '账户余额',
  `last_balance` varchar(20) DEFAULT NULL COMMENT '上日余额',
  `rtn_code` char(6) DEFAULT NULL COMMENT '返回代码',
  `ertn_code` varchar(12) DEFAULT NULL COMMENT '扩展返回代码',
  `ertn_info` varchar(128) DEFAULT NULL COMMENT '扩展返回信息',
  `spt1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `spt2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `spt3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `memo` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `org_code_uq` (`org_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=368 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `easipay_expurch_notice`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_expurch_notice`;
CREATE TABLE `easipay_expurch_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trx_code` char(4) NOT NULL COMMENT '交易代码',
  `trx_serno` char(26) NOT NULL COMMENT '交易流水号',
  `src_ncode` varchar(8) NOT NULL COMMENT '商户节点代码',
  `merchant_name` varchar(100) DEFAULT NULL COMMENT '商户名称',
  `ex_tcnt` varchar(6) NOT NULL COMMENT '总笔数',
  `pay_cur` char(3) NOT NULL COMMENT '付款币种',
  `pay_tamt` varchar(17) NOT NULL COMMENT '付款总额',
  `ex_cur` char(3) NOT NULL COMMENT '购汇币种',
  `ex_tamt` varchar(17) NOT NULL COMMENT '购汇总额',
  `res_serno` char(26) NOT NULL COMMENT '响应流水',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `spt1` varchar(100) DEFAULT NULL COMMENT '备用域1',
  `spt2` varchar(100) DEFAULT NULL COMMENT '备用域2',
  `spt3` varchar(100) DEFAULT NULL COMMENT '备用域3',
  `msg_createtime` datetime NOT NULL COMMENT '报文创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `epn_trx_serno_uq` (`trx_serno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=564 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `easipay_expurch_notice_detail`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_expurch_notice_detail`;
CREATE TABLE `easipay_expurch_notice_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trx_serno` char(26) NOT NULL COMMENT '交易流水号',
  `otrx_serno` char(26) NOT NULL COMMENT '原交易流水号',
  `etrx_serno` char(26) NOT NULL COMMENT '银行交易流水号',
  `dracc_name` varchar(100) DEFAULT NULL COMMENT '付款方户名',
  `pay_amt` varchar(17) NOT NULL COMMENT '付款金额',
  `ex_amt` varchar(17) NOT NULL COMMENT '购汇金额',
  `ex_rate` varchar(18) NOT NULL COMMENT '购汇汇率',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `sspt1` varchar(100) DEFAULT NULL COMMENT '备用域1',
  `sspt2` varchar(100) DEFAULT NULL COMMENT '备用域2',
  `sspt3` varchar(100) DEFAULT NULL COMMENT '备用域3',
  `pay_cur` char(3) DEFAULT NULL COMMENT '付款币种',
  `ex_cur` char(3) DEFAULT NULL COMMENT '购汇币种',
  `orderno` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_channel` varchar(12) DEFAULT NULL COMMENT '支付方式',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  PRIMARY KEY (`id`),
  UNIQUE KEY `epnd_otrx_serno_uq` (`otrx_serno`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `easipay_exrem_notice`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_exrem_notice`;
CREATE TABLE `easipay_exrem_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trx_code` char(4) NOT NULL COMMENT '交易代码',
  `trx_serno` char(26) NOT NULL COMMENT '交易流水号',
  `src_ncode` varchar(8) NOT NULL COMMENT '商户节点代码',
  `merchant_name` varchar(100) DEFAULT NULL COMMENT '商户名称',
  `dracc_name` varchar(100) DEFAULT NULL COMMENT '付款方户名',
  `rec_ncode` char(8) NOT NULL COMMENT '收款方节点代码',
  `cracc_name` varchar(128) DEFAULT NULL COMMENT '收款方名称',
  `cracc_n0` varchar(32) NOT NULL COMMENT '收款方账号',
  `rem_tcnt` varchar(6) NOT NULL COMMENT '总笔数',
  `pay_cur` char(3) NOT NULL COMMENT '付款币种',
  `pay_tamt` varchar(17) NOT NULL COMMENT '付款总额',
  `rem_cur` char(3) NOT NULL COMMENT '付汇币种',
  `rem_tamt` varchar(17) NOT NULL COMMENT '付汇总额',
  `res_serno` char(26) NOT NULL COMMENT '响应流水',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `spt1` varchar(100) DEFAULT NULL COMMENT '备用域1',
  `spt2` varchar(100) DEFAULT NULL COMMENT '备用域2',
  `spt3` varchar(100) DEFAULT NULL COMMENT '备用域3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ern_trx_serno_uq` (`trx_serno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `easipay_exrem_notice_detail`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_exrem_notice_detail`;
CREATE TABLE `easipay_exrem_notice_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trx_serno` char(26) NOT NULL COMMENT '交易流水号',
  `otrx_serno` char(26) NOT NULL COMMENT '原交易流水号',
  `etrx_serno` char(26) NOT NULL COMMENT '银行交易流水号',
  `rem_amt` varchar(17) NOT NULL COMMENT '付汇金额',
  `pay_amt` varchar(17) NOT NULL COMMENT '付款金额',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `sspt1` varchar(100) DEFAULT NULL COMMENT '备用域1',
  `sspt2` varchar(100) DEFAULT NULL COMMENT '备用域2',
  `sspt3` varchar(100) DEFAULT NULL COMMENT '备用域3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ernd_otrx_serno_uq` (`otrx_serno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=260357 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `easipay_exrem_process`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_exrem_process`;
CREATE TABLE `easipay_exrem_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trx_code` char(4) NOT NULL COMMENT '交易代码',
  `trx_serno` char(26) NOT NULL COMMENT '交易流水号',
  `req_time` datetime NOT NULL COMMENT '请求时间',
  `pay_biz` char(2) NOT NULL COMMENT '支付业务',
  `src_ncode` varchar(8) NOT NULL COMMENT '商户节点代码',
  `merchant_name` varchar(100) DEFAULT NULL COMMENT '商户名称',
  `rem_cur` char(3) NOT NULL COMMENT '付汇币种',
  `rem_fund_flag` char(1) NOT NULL COMMENT '付汇资金标识[S:一级商户 R:二级商户]',
  `rec_ncode` varchar(8) NOT NULL COMMENT '付汇商户节点',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `spt1` varchar(100) DEFAULT NULL COMMENT '备用域1',
  `spt2` varchar(100) DEFAULT NULL COMMENT '备用域2',
  `spt3` varchar(100) DEFAULT NULL COMMENT '备用域3',
  `res_rtrx_serno` char(26) DEFAULT NULL COMMENT '应答方交易流水号',
  `res_rdo_time` datetime NOT NULL COMMENT '应答方处理时间',
  `res_rtn_code` char(6) DEFAULT NULL COMMENT '[应答]返回代码',
  `res_rtn_info` varchar(100) DEFAULT NULL COMMENT '[应答]返回信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `erp_trx_serno_uq` (`trx_serno`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `easipay_exrem_process_detail`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_exrem_process_detail`;
CREATE TABLE `easipay_exrem_process_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `trx_serno` char(26) NOT NULL COMMENT '交易流水号',
  `otrx_serno` char(26) NOT NULL COMMENT '原交易流水号',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `sspt1` varchar(100) DEFAULT NULL COMMENT '备用域1',
  `sspt2` varchar(100) DEFAULT NULL COMMENT '备用域2',
  `sspt3` varchar(100) DEFAULT NULL COMMENT '备用域3',
  `res_err_code` varchar(6) DEFAULT NULL COMMENT '[应答]错误代码',
  `res_err_info` varchar(100) DEFAULT NULL COMMENT '[应答]错误描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `erpd_otrx_serno_uq` (`otrx_serno`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `easipay_trxlog`
-- ----------------------------
DROP TABLE IF EXISTS `easipay_trxlog`;
CREATE TABLE `easipay_trxlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `msg_code` char(6) NOT NULL COMMENT '报文代码',
  `trx_code` char(4) NOT NULL COMMENT '交易代码',
  `trx_serno` char(26) NOT NULL COMMENT '交易流水号',
  `data` longtext COMMENT '报文data(Base64)',
  `errMsg` text COMMENT '错误信息',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态[0:未处理 1:已解析保存 2:已回调通知]',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `msg_createtime` datetime NOT NULL COMMENT '报文创建时间',
  `checktime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '检视时间',
  `msg_type` char(2) NOT NULL COMMENT '报文类型[00:请求 01:响应 03:kjt请求]',
  PRIMARY KEY (`id`),
  UNIQUE KEY `elog_trx_serno_uq` (`trx_serno`,`msg_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=415033 DEFAULT CHARSET=utf8 COMMENT='东方支付报文日志表';

-- ----------------------------
--  Table structure for `eastpay`
-- ----------------------------
DROP TABLE IF EXISTS `eastpay`;
CREATE TABLE `eastpay` (
  `orderno` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ecc_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `ecc_orderinfo`;
CREATE TABLE `ecc_orderinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `orderst` varchar(10) NOT NULL COMMENT '订单状态',
  `currency` varchar(6) DEFAULT NULL COMMENT '收款币种',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `pay_channel` varchar(12) NOT NULL DEFAULT '117' COMMENT '支付方式',
  `pay_status` varchar(12) DEFAULT '-1' COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `activities_amt` int(20) unsigned zerofill DEFAULT NULL COMMENT '活动金额',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `ba_date` varchar(30) DEFAULT NULL COMMENT '平账日期',
  `ba_user_id` varchar(30) DEFAULT NULL COMMENT '平账人',
  `ba_flag` char(1) NOT NULL DEFAULT '0' COMMENT '平账状态[0:未平账 1:已平账 5:强制平账]',
  `custid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `to_flag` varchar(20) NOT NULL DEFAULT '30' COMMENT '平帐，差异查询以后过滤标识 10进入了平帐  20进入差异 30都没有进入',
  `ecc_uptime` datetime DEFAULT NULL COMMENT 'ECC订单记录更新时间',
  `ship_via` varchar(30) DEFAULT NULL COMMENT '快递公司编号',
  `tracking_number` varchar(64) DEFAULT NULL COMMENT '运单号',
  `weight` int(20) DEFAULT NULL COMMENT '订单重量',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `order_amt` int(20) DEFAULT NULL COMMENT '订单现金总额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣金额',
  `pf_currency` varchar(6) DEFAULT NULL COMMENT '购汇币种',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `ba_flg_b` char(1) NOT NULL DEFAULT '0',
  `coupon` int(20) DEFAULT NULL COMMENT '优惠券',
  `goods_original_price` int(20) DEFAULT NULL COMMENT '商品原价',
  `cash_pay` int(20) DEFAULT NULL COMMENT '对应SO_Master的CashPay',
  `pay_price` int(20) DEFAULT NULL COMMENT '对应SO_Master的PayPrice',
  `ship_price` int(20) DEFAULT NULL COMMENT '对应SO_Master的ShipPrice',
  `premium_amt` int(20) DEFAULT NULL COMMENT '对应SO_Master的PremiumAmt',
  `source` varchar(20) NOT NULL DEFAULT '0' COMMENT '订单来源None = 0,Phone = 1, Wechat = 2, IPhone = 3, Android = 4,API = 5,Msite = 6',
  `auth_customer_sysno` int(32) DEFAULT NULL COMMENT '实名信息_客户编号',
  `auth_name` varchar(50) DEFAULT NULL COMMENT '实名信息_客户姓名',
  `auth_idcard_type` int(6) DEFAULT NULL COMMENT '实名信息_证件类型',
  `auth_idcard_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实名信息_证件号码',
  `auth_birthday` date DEFAULT NULL COMMENT '实名信息_客户生日',
  `auth_phone_number` varchar(50) DEFAULT NULL COMMENT '实名信息_手机号码',
  `auth_email` varchar(200) DEFAULT NULL COMMENT '实名信息_电子邮箱',
  `auth_address` varchar(200) DEFAULT NULL COMMENT '实名信息_地址',
  `auth_gender` int(6) DEFAULT NULL COMMENT '实名信息_性别[0:女, 1:男]',
  `hold_mark` char(1) DEFAULT '0' COMMENT '冻结标志',
  `fep_status` int(6) DEFAULT NULL COMMENT '购汇状态(SO_CheckShipping表的ForeignExchangePurchasingStatus)',
  `sale_channel_sysno` int(32) DEFAULT NULL COMMENT '(对应SO_CheckShipping表的SaleChannelSysNo)',
  `fep_sale_bill_flag` char(1) DEFAULT NULL COMMENT 'ForeignExchangePurchasing_SaleBillMaster及ForeignExchangePurchasing_SaleBillItem是否有对应记录的标志[0:无,1:有]',
  `purchasing_amt` int(20) DEFAULT NULL COMMENT '购汇金额',
  `bank_trans_number` varchar(50) DEFAULT NULL COMMENT '对应Finance_NetPay的BankTransNumber',
  `pay_card_number` varchar(40) DEFAULT NULL COMMENT '对应Finance_NetPay的PayCardNumber',
  `receive_currency_code` varchar(20) DEFAULT NULL COMMENT '对应Vendor_Customs_Info表的ReceiveCurrencyCode',
  `ship_currency_code` varchar(20) DEFAULT 'CNY' COMMENT '对应Vendor_Customs_Info表的ShipCurrencyCode',
  `merchant_sysno` int(32) DEFAULT NULL COMMENT '对应SO_CheckShipping表的MerchantSysNo',
  `purchas_status` int(6) DEFAULT '0' COMMENT '购汇状态[0:未购汇,1:待购汇,2:购汇中,3:购汇完成,-3:购汇异常,-1:无需购汇,9:自行购汇]',
  `auth_syn_flag` char(1) DEFAULT '1' COMMENT '实名信息同步开关[''0'':不需要同步,''1'':需要同步]',
  `merchant_code` varchar(64) DEFAULT NULL COMMENT '二级商户东方支付备案号',
  `pay_channel_code` varchar(20) DEFAULT NULL COMMENT '支付方式(转义后)',
  `netting_amt` int(20) NOT NULL DEFAULT '0' COMMENT '轧差金额',
  `netting_source` varchar(20) DEFAULT NULL COMMENT '退款订单源',
  `netting_status` varchar(2) DEFAULT '00' COMMENT '是否轧差 01-已轧差 00和其它为未轧差',
  `netting_type` varchar(20) DEFAULT NULL COMMENT '退款操作类型: \r\n01轧差购汇退款\r\n02保证金退款\r\n03银行转账\r\n',
  `netting_time` timestamp NULL DEFAULT NULL COMMENT '轧差操作时间',
  `netting_notice_result` varchar(6) DEFAULT 'N' COMMENT '轧差通知结果\r\nN-未通知\r\nY-已通知\r\nR-已购汇',
  `tax_state` varchar(20) NOT NULL DEFAULT 'wjs' COMMENT '行邮税状态，（wjs=未缴税、clz=处理中、yjs=已交税、yts=已退税）',
  `tax_batch_number` varchar(64) DEFAULT NULL COMMENT '行邮税处理批次号',
  `status_time` datetime DEFAULT NULL COMMENT '出库时间',
  `out_status` varchar(2) DEFAULT '0' COMMENT '订单出库状态默认是0：未装车 1-已装车',
  `perinfo_trx_serno` char(26) DEFAULT NULL COMMENT '个人信息同步交易流水号',
  `perinfo_rtn_code` char(6) DEFAULT NULL COMMENT '个人信息同步返回代码',
  `perinfo_rtn_info` varchar(100) DEFAULT NULL COMMENT '个人信息同步返回信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ecc_orderinfo_orderno_uq` (`orderno`),
  KEY `idx_ba_flag` (`ba_flag`) USING BTREE,
  KEY `idx_merchant_lv2` (`merchant_lv2`) USING BTREE,
  KEY `idx_pay_channel_code` (`pay_channel_code`) USING BTREE,
  KEY `idx_orderst` (`orderst`) USING BTREE,
  KEY `idx_paytime` (`paytime`) USING BTREE,
  KEY `idx_ordertime` (`ordertime`) USING BTREE,
  KEY `idx_tax_batch_number` (`tax_batch_number`) USING BTREE,
  KEY `idx_tax_source` (`source`) USING BTREE,
  KEY `idx_tax_state` (`tax_state`),
  KEY `idx_tracking_number` (`tracking_number`) USING BTREE COMMENT '运单号索引',
  KEY `idx_perinfo_trx_serno` (`perinfo_trx_serno`) USING BTREE,
  KEY `idx_ status_time` (`status_time`) USING BTREE,
  KEY `idx_sale_channel_sysno` (`sale_channel_sysno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=423457512438 DEFAULT CHARSET=utf8 COMMENT='ECC订单信息表，从ECC导入的订单镜像，作为清算系统的ECC数据源';

-- ----------------------------
--  Table structure for `end_balance`
-- ----------------------------
DROP TABLE IF EXISTS `end_balance`;
CREATE TABLE `end_balance` (
  `deduct_amt1` decimal(65,0) DEFAULT NULL,
  `deduct_amt2` decimal(65,0) DEFAULT NULL,
  `margin_type1` char(12) DEFAULT NULL,
  `margin_type2` char(12) DEFAULT NULL,
  `merchant_no1` char(16) DEFAULT NULL,
  `merchant_no2` char(16) DEFAULT NULL,
  `result_balance` decimal(65,0) NOT NULL DEFAULT '0',
  `end_balance` decimal(20,0) DEFAULT NULL,
  `rb` decimal(65,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `eq_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `eq_orderinfo`;
CREATE TABLE `eq_orderinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderno` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `orderst` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `currency` varchar(6) DEFAULT NULL COMMENT '收款币种',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `pay_channel` varchar(12) DEFAULT NULL COMMENT '支付方式',
  `pay_status` varchar(12) DEFAULT NULL COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `activities_amt` int(20) unsigned zerofill DEFAULT NULL COMMENT '活动金额',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `custid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `merchant_date` varchar(8) DEFAULT NULL COMMENT '商户日期[格式为:YYYYMMDD]',
  `billing_amt` int(20) DEFAULT NULL COMMENT '计费金额',
  `settle_amt` int(20) DEFAULT NULL COMMENT '结算金额',
  `cptime` datetime DEFAULT NULL COMMENT 'CP平台时间',
  `spid` varchar(64) DEFAULT NULL COMMENT '网关号',
  `acctp` varchar(20) DEFAULT NULL COMMENT '借贷标识',
  `paytype` varchar(20) DEFAULT NULL COMMENT '交易类型',
  `currency_code` varchar(10) DEFAULT NULL COMMENT '货币代码',
  `cpdate` varchar(8) DEFAULT NULL COMMENT 'CP日期',
  `old_orderno` varchar(64) DEFAULT NULL COMMENT '原始订单号',
  `old_subamt` int(20) DEFAULT NULL COMMENT '原始支付金额',
  `channel` varchar(12) DEFAULT NULL COMMENT '交易渠道',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `ba_date` varchar(12) NOT NULL COMMENT '平账日期',
  `ba_user_id` varchar(64) DEFAULT NULL COMMENT '平账操作人',
  `exchange_status` char(1) NOT NULL DEFAULT '0' COMMENT '购汇返回状态[默认0:未购汇;]',
  `ecc_uptime` datetime DEFAULT NULL COMMENT 'ECC订单记录更新时间',
  `ship_via` varchar(30) DEFAULT NULL COMMENT '快递公司编号',
  `tracking_number` varchar(64) DEFAULT NULL COMMENT '运单号',
  `weight` int(20) DEFAULT NULL COMMENT '订单重量',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `order_amt` int(20) DEFAULT NULL COMMENT '订单现金总额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣总额',
  `pf_currency` varchar(6) DEFAULT NULL COMMENT '购汇币种',
  `status` varchar(10) NOT NULL DEFAULT '0' COMMENT '状态 0是没有退款 1是完全退款 2是部分退款',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `subamt` int(11) DEFAULT NULL COMMENT '第三方支付金额',
  `cpsn` varchar(64) DEFAULT NULL COMMENT 'CP流水号',
  `to_flag` varchar(10) DEFAULT '10' COMMENT '平帐，差异查询以后过滤标识',
  `ba_flag` varchar(10) DEFAULT '1' COMMENT '平账状态[0:未平账 1:已平账]',
  `realorderno` varchar(64) DEFAULT NULL COMMENT '第三方支付订单号',
  `pay_channel_code` varchar(20) DEFAULT NULL,
  `diff_type` varchar(20) DEFAULT NULL COMMENT '差分类型，xf=消费差分，tk=退款差分，zf=支付差分',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `ro_order_no` varchar(64) DEFAULT NULL COMMENT '订单号',
  `ro_pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `ro_document_no` varchar(32) DEFAULT NULL COMMENT '单据号',
  `ro_document_type` varchar(20) DEFAULT NULL COMMENT '单据类型',
  `ro_refund_order_no` varchar(64) DEFAULT NULL COMMENT '退款单号',
  `ro_refund_state` varchar(20) DEFAULT NULL COMMENT '退款状态',
  `ro_refund_amount` decimal(20,0) DEFAULT NULL COMMENT '退款金额',
  `ro_refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `ro_original_order_amount` decimal(20,0) DEFAULT NULL COMMENT '原订单金额',
  `ro_refund_amount_difference` decimal(20,0) DEFAULT NULL COMMENT '退款差额',
  `ro_pay_channel` varchar(20) DEFAULT NULL COMMENT '支付渠道， code跟ecc支付渠道一样',
  `ro_pay_refund_state` varchar(20) DEFAULT NULL COMMENT '退款类型',
  `ro_create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人姓名',
  `ro_audit_status` varchar(20) DEFAULT NULL COMMENT '审核状态',
  `ro_audit_user_id` varchar(64) DEFAULT NULL COMMENT '审核用户id',
  `ro_audit_user_name` varchar(100) DEFAULT NULL COMMENT '审核用户名',
  `ro_audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `refund_realorderno` varchar(255) DEFAULT NULL COMMENT '退款-订单号',
  `refund_cpsn` varchar(255) DEFAULT NULL COMMENT '退款-流水号',
  `refund_cptime` varchar(255) DEFAULT NULL COMMENT '退款-支付时间',
  `refund_channel` varchar(255) DEFAULT NULL COMMENT '退款-支付渠道',
  `refund_subamt` int(20) DEFAULT NULL COMMENT '退款-支付金额',
  `refund_billing_amt` int(20) DEFAULT NULL COMMENT '退款-计费金额',
  `refund_settle_amt` int(20) DEFAULT NULL COMMENT '退款-结算金额',
  `refund_paytype` varchar(255) DEFAULT NULL COMMENT '退款-交易类型',
  `refund_spid` varchar(64) DEFAULT NULL COMMENT '退款-网关号',
  PRIMARY KEY (`id`),
  KEY `eq_orderinfo_orderno_uq` (`orderno`) USING BTREE,
  KEY `eq_ordertime_index` (`ordertime`),
  KEY `eq_baflag_index` (`ba_flag`),
  KEY `eq_toflag_index` (`to_flag`),
  KEY `eq_pay_channel_code_index` (`pay_channel_code`) USING BTREE,
  KEY `eq_realorderno_index` (`realorderno`) USING BTREE,
  KEY `eq_paytype_index` (`paytype`) USING BTREE,
  KEY `idx_diff_type` (`diff_type`)
) ENGINE=InnoDB AUTO_INCREMENT=710419 DEFAULT CHARSET=utf8 COMMENT='平账订单信息表，已经对完帐的订单信息';

-- ----------------------------
--  Table structure for `exchange_rate_maintain`
-- ----------------------------
DROP TABLE IF EXISTS `exchange_rate_maintain`;
CREATE TABLE `exchange_rate_maintain` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `original_currency` varchar(10) NOT NULL COMMENT '原外币',
  `target_currency` varchar(10) NOT NULL COMMENT '目标外币',
  `exchange_rate` int(11) NOT NULL COMMENT '汇率',
  `exchange_rate_accuracy` int(11) NOT NULL COMMENT '汇率精度',
  `foreign_currency_quotes` varchar(30) NOT NULL COMMENT '外币牌价',
  `effective_time` date NOT NULL COMMENT '生效时间，一天只能够有一个记录',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` varchar(20) NOT NULL DEFAULT 'N' COMMENT '是否删除，Y=是，N=否',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `exchange_rate_maintain_effective_time_uq` (`effective_time`,`original_currency`,`target_currency`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='汇率维护表';

-- ----------------------------
--  Table structure for `express`
-- ----------------------------
DROP TABLE IF EXISTS `express`;
CREATE TABLE `express` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `no` varchar(20) NOT NULL COMMENT '快递编号',
  `name` varchar(50) NOT NULL COMMENT '快递名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否删除，1是，0否',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  KEY `express_no_uq` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COMMENT='快递公司表';

-- ----------------------------
--  Table structure for `foreign_exchange_purchasing_audit`
-- ----------------------------
DROP TABLE IF EXISTS `foreign_exchange_purchasing_audit`;
CREATE TABLE `foreign_exchange_purchasing_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pay_channel_code` varchar(20) DEFAULT NULL COMMENT '支付方式',
  `pay_start_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_end_time` datetime DEFAULT NULL COMMENT '支付时间',
  `merchant_codes` text COMMENT '境外商户号(多个)',
  `application_time` datetime DEFAULT NULL COMMENT '申请时间',
  `biz_audit_time` datetime DEFAULT NULL COMMENT '业务审核时间',
  `finance_audit_time` datetime DEFAULT NULL COMMENT '财务审核时间',
  `application_userid` varchar(32) DEFAULT NULL COMMENT '申请用户',
  `biz_audit_userid` varchar(32) DEFAULT NULL COMMENT '审核者id',
  `finance_audit_userid` varchar(32) DEFAULT NULL COMMENT '财务审核者id',
  `audit_status` char(6) NOT NULL COMMENT '审核状态\r\n[\r\n待审核:nn;\r\n业务审核不通过:bn;\r\n业务审核通过:by;\r\n财务审核不通过:fn;\r\n财务审核通过:fy;\r\n]',
  `status` char(6) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `purchasing_amt` int(11) DEFAULT NULL COMMENT '总购汇金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `foreign_exchange_purchasing_batch`
-- ----------------------------
DROP TABLE IF EXISTS `foreign_exchange_purchasing_batch`;
CREATE TABLE `foreign_exchange_purchasing_batch` (
  `SysNo` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `BatchNo` char(12) NOT NULL COMMENT '购汇批次号',
  `BatchStatus` int(2) NOT NULL DEFAULT '1' COMMENT '购汇批次状态',
  `PurchasingBatchAmt` int(26) DEFAULT '0' COMMENT '批次购汇总金额',
  `CreateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`SysNo`)
) ENGINE=InnoDB AUTO_INCREMENT=622 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `foreign_exchange_purchasing_batch_order`
-- ----------------------------
DROP TABLE IF EXISTS `foreign_exchange_purchasing_batch_order`;
CREATE TABLE `foreign_exchange_purchasing_batch_order` (
  `SysNo` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `BatchNo` char(12) NOT NULL COMMENT '购汇批次号',
  `SOSysNo` bigint(20) NOT NULL DEFAULT '1' COMMENT '关联订单ID',
  `PurchasingAmt` int(20) DEFAULT '0' COMMENT '购汇金额',
  `OrderException` text COMMENT '异常信息',
  `CreateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `TRX_SERNO` char(26) DEFAULT NULL COMMENT '报文流水',
  PRIMARY KEY (`SysNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `foreign_exchange_purchasing_refund`
-- ----------------------------
DROP TABLE IF EXISTS `foreign_exchange_purchasing_refund`;
CREATE TABLE `foreign_exchange_purchasing_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `check_day` date NOT NULL COMMENT '检查日期',
  `purchasingno` char(20) DEFAULT NULL COMMENT '购汇批次号',
  `orderno` char(64) DEFAULT NULL COMMENT '订单号',
  `purchasetime` timestamp(3) NULL DEFAULT NULL COMMENT '购汇时间',
  `purchasing_stauts` char(6) DEFAULT NULL COMMENT '订单购汇状态',
  `purchasing_amt` int(11) DEFAULT NULL COMMENT '订单购汇金额',
  `orderst` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `hold_mark` char(1) DEFAULT '0' COMMENT '冻结标志',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `checkday_purchasingno_orderno_uq` (`check_day`,`purchasingno`,`orderno`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `foreign_exchange_purchasing_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `foreign_exchange_purchasing_sequence`;
CREATE TABLE `foreign_exchange_purchasing_sequence` (
  `SysNo` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `Key` varchar(20) NOT NULL COMMENT 'Sequence键名',
  `Sequence` bigint(20) DEFAULT '1' COMMENT 'Sequence键值',
  PRIMARY KEY (`SysNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `fre_collect_settle_info`
-- ----------------------------
DROP TABLE IF EXISTS `fre_collect_settle_info`;
CREATE TABLE `fre_collect_settle_info` (
  `settle_batchno` bigint(20) NOT NULL COMMENT '结算批次号',
  `adjust_amount` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '订单调整金额汇总',
  `settle_amount` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '结算金额汇总',
  `collect_remark` varchar(500) DEFAULT NULL COMMENT '汇总金额备注信息',
  `all_adjust_amount` decimal(20,0) unsigned zerofill DEFAULT NULL COMMENT '整体调整金额',
  `coll_orderandall_sum` decimal(20,0) unsigned zerofill DEFAULT NULL COMMENT '订单金额调整和整体调整金额汇总',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人员',
  PRIMARY KEY (`settle_batchno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `fre_collect_statement_info`
-- ----------------------------
DROP TABLE IF EXISTS `fre_collect_statement_info`;
CREATE TABLE `fre_collect_statement_info` (
  `statement_batchno` bigint(20) NOT NULL,
  `company_subidy_freight_sum` decimal(20,0) DEFAULT NULL COMMENT ' 平台或公司补贴汇总',
  `merchant_subsidy_freight_sum` decimal(20,0) DEFAULT NULL COMMENT '商家补贴运费汇总',
  `freight_adjust_sum` decimal(20,0) DEFAULT NULL COMMENT '运费调整金汇总',
  `received_amount_sum` decimal(20,0) DEFAULT NULL COMMENT '3PL实收运费汇总',
  `real_settle_amount_sum` decimal(20,0) DEFAULT NULL COMMENT '实际结算金额汇总',
  `freight_sum` decimal(20,0) DEFAULT NULL COMMENT '运费汇总',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人员',
  `ship_via` varchar(20) DEFAULT NULL COMMENT '快递公司',
  `merchant_code` varchar(30) DEFAULT NULL COMMENT '商户编号',
  `settle_start_date` datetime DEFAULT NULL COMMENT '结算起始时间',
  `settle_end_date` datetime DEFAULT NULL COMMENT '结算结算时间',
  PRIMARY KEY (`statement_batchno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `fre_orderno_temp`
-- ----------------------------
DROP TABLE IF EXISTS `fre_orderno_temp`;
CREATE TABLE `fre_orderno_temp` (
  `order_no` varchar(100) DEFAULT NULL,
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`),
  KEY `inx_orderno` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fre_settle`
-- ----------------------------
DROP TABLE IF EXISTS `fre_settle`;
CREATE TABLE `fre_settle` (
  `settle_id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '结算单主键',
  `settle_batchno` int(20) DEFAULT NULL COMMENT '结算批次号',
  `ship_via` varchar(20) DEFAULT NULL COMMENT '快递公司代码',
  `ship_name` varchar(500) DEFAULT NULL COMMENT '快递公司名称',
  `delivery_startdate` varchar(20) DEFAULT NULL COMMENT '运费查询开始时间（出库开始时间）',
  `delivery_enddate` varchar(20) DEFAULT NULL COMMENT '运费结算时间',
  `payable_amount` decimal(20,0) DEFAULT NULL COMMENT '应付金额',
  `adjust_amount` decimal(20,0) DEFAULT NULL COMMENT '根据订单调整金额汇总',
  `settle_remark` varchar(500) DEFAULT NULL COMMENT '运单结算备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人员',
  `settle_order_id` bigint(30) DEFAULT NULL COMMENT '外键结算单的主键',
  `settle_start_date` datetime DEFAULT NULL COMMENT '结算起始时间',
  `settle_end_date` datetime DEFAULT NULL COMMENT '结算结算时间',
  `settle_amount` decimal(30,0) DEFAULT NULL COMMENT '结算金额',
  PRIMARY KEY (`settle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2015050486 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `fre_settle_audit_info`
-- ----------------------------
DROP TABLE IF EXISTS `fre_settle_audit_info`;
CREATE TABLE `fre_settle_audit_info` (
  `id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ship_via` varchar(20) DEFAULT NULL COMMENT '快递公司代码',
  `ship_name` varchar(500) DEFAULT NULL,
  `settle_start_date` datetime DEFAULT NULL COMMENT '结算起始时间',
  `settle_end_date` datetime DEFAULT NULL COMMENT '结算结算时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人员',
  `payable_amount` decimal(20,0) DEFAULT NULL COMMENT '应付金额',
  `adjust_order_amount` decimal(20,0) DEFAULT NULL COMMENT '根据订单汇总调整金额',
  `settle_amount` decimal(10,0) DEFAULT NULL COMMENT '结算金额汇总',
  `settle_batchno` int(20) DEFAULT NULL COMMENT '结算批次号',
  `audit_status` varchar(30) DEFAULT NULL COMMENT '审核状态：\r\n审核状态\r\n[\r\n待审核:nn;\r\n业务审核不通过:bn;\r\n业务审核通过:by;\r\n财务审核不通过:fn;\r\n财务审核通过:fy;\r\n]\r\n\r\n',
  `audit_by` varchar(30) DEFAULT NULL COMMENT '审核人',
  `audit_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `settle_remark` varchar(500) DEFAULT NULL COMMENT '运单结算备注',
  `clear_app_date` datetime DEFAULT NULL COMMENT '清分算申请时间',
  `settle_app_date` datetime DEFAULT NULL COMMENT '结算申请时间',
  PRIMARY KEY (`id`),
  KEY `ind_clear_app_date` (`clear_app_date`) USING BTREE,
  KEY `ind_settle_app_date` (`settle_app_date`) USING BTREE,
  KEY `ind_audit_status` (`audit_status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `fre_settle_order`
-- ----------------------------
DROP TABLE IF EXISTS `fre_settle_order`;
CREATE TABLE `fre_settle_order` (
  `settle_order_id` bigint(30) NOT NULL AUTO_INCREMENT COMMENT '结算单订单编号',
  `settle_no` int(11) DEFAULT NULL COMMENT '结算单编号',
  `ship_via` varchar(20) DEFAULT NULL COMMENT '快递公司编号',
  `ship_name` varchar(50) DEFAULT NULL,
  `settle_status` int(11) DEFAULT NULL COMMENT '结算状态',
  `settle_start_date` datetime DEFAULT NULL COMMENT '结算起始时间',
  `settle_end_date` datetime DEFAULT NULL COMMENT '结算结算时间',
  `collection_freight` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '代收运费金额',
  `company_subsidy_freight` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '平台补贴运费',
  `merchant_subsidy_freight` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '商户补贴运费',
  `freight_adjust_amount` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '运费调整金额',
  `received_amount` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '3PL实收金额',
  `real_settle_amount` decimal(10,0) unsigned zerofill DEFAULT NULL COMMENT '实收结算金额',
  `financial_vouchers` decimal(10,0) DEFAULT NULL COMMENT '财务凭证号',
  `pay_date` datetime DEFAULT NULL COMMENT '付款日期',
  `financial_remark` varchar(500) DEFAULT NULL COMMENT '财务备注信息',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人员',
  `flag` varchar(1) DEFAULT '0' COMMENT '有效标记 0-有效 1-无效',
  PRIMARY KEY (`settle_order_id`),
  KEY `comp_ind_start_end` (`settle_start_date`,`settle_end_date`) USING BTREE COMMENT '时间组合索引'
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `fre_statement`
-- ----------------------------
DROP TABLE IF EXISTS `fre_statement`;
CREATE TABLE `fre_statement` (
  `statement_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '结算对账单编号',
  `statement_batchno` bigint(20) DEFAULT NULL,
  `delivery_date` datetime DEFAULT NULL COMMENT '出库时间',
  `ship_via` varchar(20) DEFAULT NULL COMMENT '快递公司',
  `ship_name` varchar(200) DEFAULT NULL,
  `merchant_code` varchar(30) DEFAULT NULL COMMENT '商户编号',
  `order_no` varchar(30) DEFAULT NULL COMMENT '订单编号',
  `delivery_no` varchar(30) DEFAULT NULL COMMENT '运单号',
  `order_freight` decimal(10,0) DEFAULT NULL COMMENT '订单运费',
  `company_subsidy_freight` decimal(10,0) DEFAULT NULL COMMENT '平台补贴运费',
  `merchant_subsidy_freight` decimal(10,0) DEFAULT NULL COMMENT '商家补贴运费',
  `freight_adjust_amount` decimal(10,0) DEFAULT NULL COMMENT '运费调整金',
  `received_amount` decimal(10,0) DEFAULT NULL COMMENT '3PL实收运费',
  `real_settle_amount` decimal(10,0) DEFAULT NULL COMMENT '实际结算金额',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) DEFAULT NULL COMMENT '更新人员',
  `settle_order_id` bigint(30) DEFAULT NULL COMMENT '外键结算单的主键',
  `settle_start_date` datetime DEFAULT NULL COMMENT '结算起始时间',
  `settle_end_date` datetime DEFAULT NULL COMMENT '结算结算时间',
  `merchant_name` varchar(500) DEFAULT NULL COMMENT '二级商户名称',
  PRIMARY KEY (`statement_id`),
  KEY `index_statement_batchno` (`statement_batchno`) USING BTREE,
  KEY `index_settle_order_id` (`settle_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=44917 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `freight_fixes`
-- ----------------------------
DROP TABLE IF EXISTS `freight_fixes`;
CREATE TABLE `freight_fixes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fixes_type` char(1) NOT NULL COMMENT '运费调整类型[0:订单调整;1:整单调整]',
  `ship_via` varchar(64) DEFAULT NULL COMMENT '快递公司(整单调整才有)',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号（整单调整时为整单调整ID）',
  `fixes_starttime` datetime DEFAULT NULL COMMENT '整单调整周期开始时间',
  `fixes_endtime` datetime DEFAULT NULL COMMENT '整单调整周期结束时间',
  `fixes_amt` int(20) NOT NULL DEFAULT '0' COMMENT '调整金额（分）',
  `fixes_reason` varchar(300) DEFAULT NULL COMMENT '调整原因',
  `fixes_clause` varchar(300) DEFAULT NULL COMMENT '调整款项',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:作废 1:正常]',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_operator` varchar(64) NOT NULL COMMENT '创建人',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `update_operator` varchar(64) DEFAULT NULL COMMENT '最后更新操作人',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `pay_channel` varchar(12) DEFAULT NULL COMMENT '支付方式',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`),
  KEY `ind_orderno` (`orderno`) USING BTREE,
  KEY `ind_fixs_type` (`fixes_type`) USING BTREE,
  KEY `ind_status` (`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `fxwjs`
-- ----------------------------
DROP TABLE IF EXISTS `fxwjs`;
CREATE TABLE `fxwjs` (
  `﻿orderno` varchar(255) DEFAULT NULL,
  UNIQUE KEY `ind_orderno` (`﻿orderno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `limit_merchant`
-- ----------------------------
DROP TABLE IF EXISTS `limit_merchant`;
CREATE TABLE `limit_merchant` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `limit_type` char(6) DEFAULT NULL COMMENT '额度类型  xj先结算 xf先发货 jg 警告',
  `limit_amt` int(11) DEFAULT NULL COMMENT '额度金额',
  `status` char(12) DEFAULT NULL COMMENT '状态 备用',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `merchant_no` char(12) DEFAULT NULL COMMENT '商户号',
  `inserttime` datetime DEFAULT NULL,
  `insert_userId` char(12) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `update_userId` char(12) DEFAULT NULL,
  `start_day` date DEFAULT NULL COMMENT '信用额度有效期开始日期',
  `end_day` date DEFAULT NULL COMMENT '信用额度有效期结束日期',
  `rsv1` varchar(255) DEFAULT NULL,
  `rsv2` varchar(255) DEFAULT NULL,
  `rsv3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `margin`
-- ----------------------------
DROP TABLE IF EXISTS `margin`;
CREATE TABLE `margin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `margin_no` varchar(16) DEFAULT NULL COMMENT '编号',
  `margin_amt` int(255) DEFAULT NULL COMMENT '保证金金额',
  `type` varchar(12) DEFAULT NULL COMMENT '类型',
  `merchant_no` varchar(16) DEFAULT NULL COMMENT '商户号',
  `status` varchar(6) DEFAULT NULL COMMENT '状态',
  `comment` varchar(80) DEFAULT NULL COMMENT '注释',
  `inserttime` datetime(3) DEFAULT NULL,
  `insert_userId` varchar(12) DEFAULT NULL,
  `update_userId` varchar(12) DEFAULT NULL,
  `updatetime` datetime(3) DEFAULT NULL,
  `is_delete` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否删除， 1=是，0=否',
  `rsv1` varchar(20) DEFAULT NULL COMMENT '业务时间',
  `rsv2` varchar(255) DEFAULT NULL,
  `rsv3` varchar(255) DEFAULT NULL,
  `is_reverse_audit` varchar(2) NOT NULL DEFAULT '0' COMMENT '是否反审核， 1=是，0=否',
  `old_margin_amt` varchar(255) DEFAULT NULL COMMENT '保证金金额',
  `reverse_source` varchar(2) DEFAULT NULL COMMENT '反审核来源，U=更新，D=删除',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `margin_interface_log`
-- ----------------------------
DROP TABLE IF EXISTS `margin_interface_log`;
CREATE TABLE `margin_interface_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `requestId` varchar(50) DEFAULT NULL COMMENT '请求ID',
  `orderNo` varchar(30) DEFAULT NULL COMMENT '订单号',
  `orderAmt` decimal(10,0) DEFAULT NULL COMMENT '订单金额',
  `orderStatus` varchar(2) DEFAULT NULL COMMENT '订单状态',
  `serviceType` varchar(2) DEFAULT NULL COMMENT '订单类型',
  `deductionMode` varchar(20) DEFAULT NULL COMMENT '扣款模式:  xj-先结算后发货 jg-警告 xf-先发货后结算 yfk-预付款模式',
  `margin_operType` varchar(2) DEFAULT NULL COMMENT '保证金接口类型：11-保证金充值 12-保证金作废 13-保证金扣款 14-保证金查询',
  `merchantCode` varchar(30) DEFAULT NULL COMMENT '商户号',
  `subMargin` varchar(255) DEFAULT NULL COMMENT '保证金类型',
  `distrMerchant` varchar(30) DEFAULT NULL,
  `deal_status` varchar(2) DEFAULT NULL COMMENT '01-待处理 02-处理失败 03-处理成功 04-处理中',
  `message` varchar(2000) DEFAULT NULL COMMENT '处理结果',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `code` varchar(10) DEFAULT NULL COMMENT '处理返回码:000000-ok',
  `msg` varchar(2000) DEFAULT NULL,
  `deduct_no` varchar(20) DEFAULT NULL COMMENT '扣款单编号',
  `extend1` varchar(100) DEFAULT NULL COMMENT '扩展字段1',
  `extend2` varchar(100) DEFAULT NULL COMMENT '扩展字段2',
  `extend3` varchar(100) DEFAULT NULL COMMENT '扩展字段3',
  PRIMARY KEY (`id`),
  KEY `ind_requestId` (`requestId`),
  KEY `ind_deal_status` (`deal_status`)
) ENGINE=InnoDB AUTO_INCREMENT=26468 DEFAULT CHARSET=utf8 COMMENT='保证金接口调用相关日志';

-- ----------------------------
--  Table structure for `merchant_account`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_account`;
CREATE TABLE `merchant_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_code` varchar(64) NOT NULL COMMENT '二级商户节点号',
  `pay_channel` varchar(12) NOT NULL COMMENT '支付渠道',
  `check_day` date NOT NULL COMMENT '对账日',
  `status` char(6) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `subamt` int(20) DEFAULT NULL COMMENT '资金金额',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `freight` int(10) DEFAULT NULL COMMENT '运费',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `thirdparty_amt` int(20) DEFAULT NULL COMMENT '第三方/银行手续费',
  `deduction` int(20) DEFAULT NULL COMMENT '扣款',
  `refund` int(20) DEFAULT NULL COMMENT '退款',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣总额',
  `insert_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `merchant_account_by_day_uq` (`merchant_code`,`pay_channel`,`check_day`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10845 DEFAULT CHARSET=utf8 COMMENT='商户对账日统计表';

-- ----------------------------
--  Table structure for `merchant_account_detail`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_account_detail`;
CREATE TABLE `merchant_account_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_batch_id` bigint(20) NOT NULL COMMENT '商户按日对账批次id',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `orderst` varchar(10) NOT NULL COMMENT '订单状态',
  `currency` varchar(6) DEFAULT NULL COMMENT '收款币种',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `pay_channel` varchar(12) NOT NULL COMMENT '支付方式',
  `pay_status` varchar(12) DEFAULT '-1' COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `activities_amt` int(20) unsigned zerofill DEFAULT NULL COMMENT '活动金额',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `ba_date` varchar(30) DEFAULT NULL COMMENT '平账日期',
  `ba_user_id` varchar(30) DEFAULT NULL COMMENT '平账人',
  `ba_flag` char(1) NOT NULL DEFAULT '0' COMMENT '平账状态[0:未平账 1:已平账]',
  `custid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `to_flag` varchar(20) NOT NULL DEFAULT '30' COMMENT '平帐，差异查询以后过滤标识 10进入了平帐  20进入差异 30都没有进入',
  `ecc_uptime` datetime DEFAULT NULL COMMENT 'ECC订单记录更新时间',
  `ship_via` varchar(30) DEFAULT NULL COMMENT '快递公司编号',
  `tracking_number` varchar(64) DEFAULT NULL COMMENT '运单号',
  `weight` int(20) DEFAULT NULL COMMENT '订单重量',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `order_amt` int(20) DEFAULT NULL COMMENT '订单现金总额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣总额',
  `pf_currency` varchar(6) DEFAULT NULL COMMENT '购汇币种',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户对账审核订单详情表';

-- ----------------------------
--  Table structure for `merchant_audit_detail`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_audit_detail`;
CREATE TABLE `merchant_audit_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `audit_id` bigint(20) NOT NULL COMMENT '商户对账审核批次id',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `orderst` varchar(10) NOT NULL COMMENT '订单状态',
  `currency` varchar(6) DEFAULT NULL COMMENT '收款币种',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `pay_channel` varchar(12) NOT NULL COMMENT '支付方式',
  `pay_status` varchar(12) DEFAULT '-1' COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `activities_amt` int(20) unsigned zerofill DEFAULT NULL COMMENT '活动金额',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `ba_date` varchar(30) DEFAULT NULL COMMENT '平账日期',
  `ba_user_id` varchar(30) DEFAULT NULL COMMENT '平账人',
  `ba_flag` char(1) NOT NULL DEFAULT '0' COMMENT '平账状态[0:未平账 1:已平账]',
  `custid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `to_flag` varchar(20) NOT NULL DEFAULT '30' COMMENT '平帐，差异查询以后过滤标识 10进入了平帐  20进入差异 30都没有进入',
  `ecc_uptime` datetime DEFAULT NULL COMMENT 'ECC订单记录更新时间',
  `ship_via` varchar(30) DEFAULT NULL COMMENT '快递公司编号',
  `tracking_number` varchar(64) DEFAULT NULL COMMENT '运单号',
  `weight` int(20) DEFAULT NULL COMMENT '订单重量',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `order_amt` int(20) DEFAULT NULL COMMENT '订单现金总额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣总额',
  `pf_currency` varchar(6) DEFAULT NULL COMMENT '购汇币种',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1308 DEFAULT CHARSET=utf8 COMMENT='商户对账日统计订单详情表';

-- ----------------------------
--  Table structure for `merchant_audit_info`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_audit_info`;
CREATE TABLE `merchant_audit_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `application_time` datetime DEFAULT NULL COMMENT '申请时间',
  `biz_audit_time` datetime DEFAULT NULL COMMENT '业务审核时间',
  `finance_audit_time` datetime DEFAULT NULL COMMENT '财务审核时间',
  `application_userid` char(11) DEFAULT NULL COMMENT '申请人id',
  `biz_audit_userid` char(11) DEFAULT NULL COMMENT '业务审核人员id',
  `finance_audit_userid` char(11) DEFAULT NULL COMMENT '财务审核人员id',
  `audit_status` char(6) NOT NULL COMMENT '审核状态\r\n[\r\n待审核:nn;\r\n业务审核不通过:bn;\r\n业务审核通过:by;\r\n财务审核不通过:fn;\r\n财务审核通过:fy;\r\n]',
  `status` char(6) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `merchant_name` varchar(64) NOT NULL COMMENT '商户名称',
  `start_time` date NOT NULL COMMENT '开始时间',
  `end_time` date NOT NULL COMMENT '结束时间',
  `subamt` int(20) NOT NULL COMMENT '资金金额',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `freight` int(10) DEFAULT NULL COMMENT '运费',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `thirdparty_amt` int(20) DEFAULT NULL COMMENT '第三方/银行手续费',
  `deduction` int(20) DEFAULT NULL COMMENT '扣款',
  `refund` int(20) DEFAULT NULL COMMENT '退款',
  `pay_channel` varchar(12) DEFAULT NULL COMMENT '支付方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='商户对账审核表';

-- ----------------------------
--  Table structure for `merchant_cash_pool`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_cash_pool`;
CREATE TABLE `merchant_cash_pool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchantCode` varchar(50) DEFAULT NULL COMMENT '商户号',
  `amt` decimal(10,0) DEFAULT NULL COMMENT '商户剩余现金金额',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ind_merchantId` (`merchantCode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=92339 DEFAULT CHARSET=utf8 COMMENT='商户订单总金额维护表';

-- ----------------------------
--  Table structure for `merchant_deposit`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_deposit`;
CREATE TABLE `merchant_deposit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `status` char(1) NOT NULL COMMENT '商户状态 0:正常',
  `tax_deposit` int(20) unsigned zerofill NOT NULL COMMENT '行邮税保证金',
  `refund_deposit` int(20) unsigned zerofill NOT NULL COMMENT '退款保证金',
  `dispute_deposit` int(20) unsigned zerofill NOT NULL COMMENT '争议保证金',
  `compensation_deposit` int(20) unsigned zerofill NOT NULL COMMENT '先行赔付保证金',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `merchant_over`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_over`;
CREATE TABLE `merchant_over` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchant_lv2` varchar(50) DEFAULT NULL COMMENT '二级商户号',
  `merchant_lv3` varchar(50) NOT NULL COMMENT '三级商户号',
  `currency_type` varchar(20) NOT NULL DEFAULT '1' COMMENT '货币类型',
  `merchant_begin_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '三级商户期初余额',
  `merchant_end_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '三级商户期末余额',
  `curr_debit_pur_sum` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '借方购汇发生额',
  `curr_credit_pay_sum` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '贷方付汇发生额',
  `summary_date` date NOT NULL COMMENT '汇总日期',
  `settle_status` varchar(30) NOT NULL DEFAULT 'no_settle' COMMENT '结算状态:no_settle-未结算  exception_settle-结算异常 complete_settle-结算完成',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `merchant_over_summary_date_uq` (`summary_date`,`merchant_lv3`,`currency_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6773 DEFAULT CHARSET=utf8 COMMENT='商户余额表';

-- ----------------------------
--  Table structure for `merchant_over_adjust_list`
-- ----------------------------
DROP TABLE IF EXISTS `merchant_over_adjust_list`;
CREATE TABLE `merchant_over_adjust_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchant_lv3` varchar(64) NOT NULL COMMENT '三级商户节点号',
  `no` varchar(32) NOT NULL COMMENT '调整单编号',
  `forex_currency` varchar(10) NOT NULL COMMENT '调整外汇币种',
  `forex_amount` decimal(20,2) NOT NULL COMMENT '调整外汇金额',
  `adjust_date` date NOT NULL COMMENT '调整单日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `receipt_status` varchar(255) DEFAULT NULL COMMENT '单据状态',
  `check_status` varchar(10) NOT NULL DEFAULT '10' COMMENT '审核状态，10=未审核，20=审核中，30=审核通过，40=审核失败',
  `check_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_delete` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否删除，1=是，0=否',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='三级商户余额表调整单表';

-- ----------------------------
--  Table structure for `monitor_log`
-- ----------------------------
DROP TABLE IF EXISTS `monitor_log`;
CREATE TABLE `monitor_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pay_day` date NOT NULL COMMENT '支付日期',
  `table_source` varchar(50) NOT NULL COMMENT '数据表来源',
  `channel` varchar(20) DEFAULT NULL COMMENT '支付渠道\r\n[\r\n111:东方支付\r\n114:财付通\r\n117,8607:银联支付\r\n117:银联B2C\r\n117All:银联合计\r\n118:微信支付\r\n112:支付宝\r\nOther:非支付渠道统计\r\n]\r\n',
  `count_type` varchar(20) DEFAULT NULL COMMENT '统计类型\r\n[\r\nall:总数\r\nba:已平账\r\nnotba:未平账\r\nsale:分销订单\r\n]',
  `count_num` int(20) DEFAULT '0' COMMENT '统计数',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5537 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `more_netting_orders`
-- ----------------------------
DROP TABLE IF EXISTS `more_netting_orders`;
CREATE TABLE `more_netting_orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `requestId` varchar(50) DEFAULT NULL COMMENT '请求id',
  `netting_amt` decimal(20,0) DEFAULT NULL COMMENT '此订单轧差金额',
  `creat_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `orderNo` varchar(30) DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`),
  KEY `ind_requestId` (`requestId`) USING BTREE,
  KEY `ind_orderNo` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=1182 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `netting_log`
-- ----------------------------
DROP TABLE IF EXISTS `netting_log`;
CREATE TABLE `netting_log` (
  `id` int(11) NOT NULL,
  `netting_start` timestamp NULL DEFAULT NULL,
  `refund_cnt` int(11) DEFAULT NULL,
  `refound_amt` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `order_cash_notice`
-- ----------------------------
DROP TABLE IF EXISTS `order_cash_notice`;
CREATE TABLE `order_cash_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `requestId` varchar(50) DEFAULT NULL COMMENT '请求id 唯一标记一次请求，全网唯一构成(YYYYMMDDHHMMSS+平台(2位每个调用平台唯一))+8位序号',
  `orderNo` varchar(50) DEFAULT NULL COMMENT '订单号',
  `merchantCode` varchar(50) DEFAULT NULL COMMENT '商户号或快递公司编号',
  `beforeAmt` decimal(30,0) DEFAULT NULL COMMENT '操作前金额',
  `purchsAmt` decimal(30,0) DEFAULT NULL COMMENT '订单购汇金额',
  `orderType` varchar(1) DEFAULT NULL COMMENT '订单的类型:1-新建订单 2-订单购汇完成 3-取消退款 4-申请退款',
  `deal_status` varchar(2) DEFAULT NULL COMMENT '处理状态:01-待处理 02-处理失败 03-处理成功',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `message` varchar(2000) DEFAULT NULL COMMENT '处理结果',
  `code` varchar(20) DEFAULT NULL COMMENT '错误代码',
  `msg` varchar(2000) DEFAULT NULL COMMENT '信息',
  PRIMARY KEY (`id`),
  KEY `ind_orderNo` (`orderNo`),
  KEY `ind_requestId` (`requestId`),
  KEY `ind_orderType` (`orderType`),
  KEY `ind_merchantCode` (`merchantCode`) USING BTREE,
  KEY `ind_createDate` (`create_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=93958 DEFAULT CHARSET=utf8 COMMENT='订单新建和购物完成通知接口';

-- ----------------------------
--  Table structure for `order_fixes`
-- ----------------------------
DROP TABLE IF EXISTS `order_fixes`;
CREATE TABLE `order_fixes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `fixes_amt` int(20) NOT NULL DEFAULT '0' COMMENT '调整金额（分）',
  `fixes_reason` varchar(300) DEFAULT NULL COMMENT '调整原因',
  `fixes_clause` varchar(300) DEFAULT NULL COMMENT '调整款项',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:作废 1:正常]',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_operator` varchar(64) NOT NULL COMMENT '创建人',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `update_operator` varchar(64) DEFAULT NULL COMMENT '最后更新操作人',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `pay_channel` varchar(12) DEFAULT NULL COMMENT '支付方式',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单调整表';

-- ----------------------------
--  Table structure for `order_notice`
-- ----------------------------
DROP TABLE IF EXISTS `order_notice`;
CREATE TABLE `order_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `orderNo` varchar(30) DEFAULT NULL COMMENT '订单号',
  `orderStatus` varchar(8) DEFAULT NULL COMMENT '订单状态',
  `operType` varchar(2) DEFAULT NULL COMMENT '操作类型:01-创建订单 02-更新订单',
  `operContent` varchar(500) DEFAULT NULL COMMENT '操作内容',
  `applyNo` varchar(30) DEFAULT NULL COMMENT '申请单编号',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `deal_status` varchar(2) DEFAULT NULL COMMENT '处理状态: 01-待处理 02-处理失败 03-处理成功',
  `message` varchar(1000) DEFAULT NULL COMMENT '处理结果',
  `bill_type` varchar(2) DEFAULT NULL COMMENT '单据类型：11-AO单通知 12-RO单通知 13-SO单通知 ',
  `requestId` varchar(50) DEFAULT NULL COMMENT '请求id 唯一标记一次请求，全网唯一构成(YYYYMMDDHHMMSS+平台(2位每个调用平台唯一))+8位序号',
  `code` varchar(20) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ind_orderNo` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=8775 DEFAULT CHARSET=utf8 COMMENT='AO单、RO单、SO单通知接口数据保存';

-- ----------------------------
--  Table structure for `order_refund_log`
-- ----------------------------
DROP TABLE IF EXISTS `order_refund_log`;
CREATE TABLE `order_refund_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderNo` varchar(50) DEFAULT NULL COMMENT '对应Ecc平台订单号',
  `serviceType` varchar(255) DEFAULT NULL COMMENT '01-平台  02-分销 03-物流',
  `orderstatus` varchar(2) DEFAULT NULL COMMENT '-4-自动作废-1-人工作废 0-待审核 4-已出库待申报 5-订单完成 6-申报失败订单作废 7-订单拒收41-申报待通关 45-已通关发往顾客 65-通关失败订单作废',
  `orderAmt` decimal(10,0) DEFAULT NULL COMMENT '原订单的金额',
  `refundAmt` decimal(10,0) DEFAULT NULL COMMENT '购汇后要退款的金额',
  `orderPurchsStatus` varchar(2) DEFAULT NULL COMMENT '订单购汇状态: -3-购汇异常 0-未购汇 1-待购汇 2-购汇中 3-购汇完成',
  `merchantCode` varchar(50) DEFAULT NULL COMMENT '境外商户号',
  `deal_status` varchar(2) DEFAULT NULL COMMENT '01-待处理 \r\n02-处理失败 \r\n03-处理成功 \r\n04-取消退款 \r\n05-需要人工处理 \r\n06-保证金扣款结果不明确\r\n07-未购汇未轧差订单退款\r\n08-订单表无法找到退款订单，无法退款\r\n09-已轧差成功，发生购汇异常，需要下次重新购汇\r\n',
  `refundType` varchar(2) DEFAULT NULL COMMENT '退款类型:\r\n01轧差购汇退款\r\n02保证金退款\r\n03银行转账\r\n04-取消退款\r\n05-需要人工干预的退款\r\n07-未购汇未轧差订单退款\r\n08-订单表无法找到退款订单，无法退款\r\n09-未购汇未轧差订单退款(资金池不足，扣保证金退款时)\r\n',
  `applyNo` varchar(50) DEFAULT NULL COMMENT '申请单编号',
  `billType` varchar(10) DEFAULT NULL COMMENT '扣款单类型:AO-AO单退款 RO-RO单退款',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `requestId` varchar(50) DEFAULT NULL COMMENT '请求id 唯一标记一次请求，全网唯一构成(YYYYMMDDHHMMSS+平台(2位每个调用平台唯一))+8位序号',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `code` varchar(10) DEFAULT NULL COMMENT '处理返回码:000000-ok',
  `msg` varchar(2000) DEFAULT NULL,
  `netting_orders` varchar(4000) DEFAULT NULL COMMENT '轧差的订单号组',
  `deal_msg` varchar(2000) DEFAULT NULL,
  `operType` varchar(2) DEFAULT NULL COMMENT '2-申请退款3-撤销退款 4-取消轧差订单退款',
  `monitorTime` varchar(20) DEFAULT NULL COMMENT '监控时间',
  `check_result` varchar(2) DEFAULT '00' COMMENT '轧差平账结果:01-轧差结果和退款金额相等 02-轧差有差异 03-保证金和退款金额无差异',
  `old_requestId` varchar(50) DEFAULT NULL COMMENT '对应的原退款requestId',
  `startTime` datetime DEFAULT NULL COMMENT 'job处理开始时间',
  `dectionNo` varchar(50) DEFAULT NULL COMMENT '保证金扣款单编号',
  `change_status` varchar(2) DEFAULT '01' COMMENT '是否需要保证金转轧差: \r\n01-默认值不需要转换\r\n02-待转换(待转换)\r\n03-处理完成\r\n',
  `retry_count` int(11) DEFAULT '0' COMMENT '重试次数',
  `synstatus` int(255) DEFAULT '0' COMMENT '是否一同步到二次轧差表：默认0-未同步，1已同步',
  `extend1` varchar(255) DEFAULT NULL COMMENT '扩展字段1',
  `extend2` varchar(255) DEFAULT NULL COMMENT '扩展字段2',
  `extend3` varchar(255) DEFAULT NULL COMMENT '扩展字段3',
  PRIMARY KEY (`id`),
  KEY `ind_deal_status` (`deal_status`) USING BTREE,
  KEY `ind_create_date` (`create_date`) USING BTREE,
  KEY `ind_requestId` (`requestId`) USING BTREE,
  KEY `ind_dectionNo` (`dectionNo`),
  KEY `ind_orderNo` (`orderNo`),
  KEY `ind_old_requestId` (`old_requestId`)
) ENGINE=InnoDB AUTO_INCREMENT=4562 DEFAULT CHARSET=utf8 COMMENT='记录退款日志表 order_refund_log';

-- ----------------------------
--  Table structure for `order_refundtype_change`
-- ----------------------------
DROP TABLE IF EXISTS `order_refundtype_change`;
CREATE TABLE `order_refundtype_change` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `requestid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '请求ID 关联退款表的requestid',
  `merchantCode` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '境外商户号',
  `orderno` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '订单号',
  `refundAmt` decimal(10,0) DEFAULT NULL COMMENT '购汇后要退款的金额',
  `applyno` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '申请单编号',
  `deal_status` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '扣款类型',
  `refundtype` varchar(2) COLLATE utf8_bin DEFAULT NULL COMMENT '扣款类型',
  `message` varchar(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息描述',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '该订单原创建时间',
  `create_by` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人',
  `update_date` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `dectionNo` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '保证金扣款单编号',
  `inserttime` timestamp NULL DEFAULT NULL COMMENT '原创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '原更新时间',
  `retry_count` int(11) DEFAULT '0' COMMENT '重试次数',
  `res1` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注1',
  `res2` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注2',
  `res3` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '备注3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=436 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT COMMENT='保证金扣款成功的，进行二次转为未购汇订单轧差';

-- ----------------------------
--  Table structure for `pf_merchant`
-- ----------------------------
DROP TABLE IF EXISTS `pf_merchant`;
CREATE TABLE `pf_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_code` char(40) DEFAULT NULL COMMENT '二级商户号',
  `merchant_name` varchar(255) DEFAULT NULL COMMENT '商户名',
  `ReceiveCurrencyCode` char(20) DEFAULT NULL COMMENT '购汇币种',
  `ShipCurrencyCode` char(20) DEFAULT NULL COMMENT '购汇币种',
  `status` char(6) DEFAULT '0' COMMENT '状态',
  `hiddenStatus` char(6) DEFAULT NULL COMMENT '是否隐藏',
  `orderKey` varchar(20) DEFAULT NULL COMMENT '排序字段',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `rs1` varchar(128) DEFAULT NULL,
  `rs2` varchar(128) DEFAULT NULL,
  `rs3` varchar(128) DEFAULT NULL,
  `ecc_merchant_id` char(50) DEFAULT NULL COMMENT 'ecc的商户主键',
  `org_code` varchar(32) DEFAULT NULL COMMENT '组织机构号',
  `easipay_acc_no` varchar(50) DEFAULT NULL COMMENT '东方支付平台支付账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1480 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pf_merchant_domestic`
-- ----------------------------
DROP TABLE IF EXISTS `pf_merchant_domestic`;
CREATE TABLE `pf_merchant_domestic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `domesticMerchantId` int(32) DEFAULT NULL COMMENT '境内商户编号',
  `vendorName` varchar(255) DEFAULT NULL COMMENT '商户名',
  `englishName` varchar(255) DEFAULT NULL COMMENT '商户英文名',
  `briefName` varchar(255) DEFAULT NULL COMMENT '商户名简称',
  `merchantCode` char(40) DEFAULT NULL COMMENT '境内商户二级商户号',
  `currencyCode` char(20) DEFAULT NULL COMMENT '币种',
  `shipCurrencyCode` char(20) DEFAULT NULL COMMENT '运费币种',
  `orgCode` varchar(32) DEFAULT NULL COMMENT '组织机构号',
  `easipayAccNo` varchar(50) DEFAULT NULL COMMENT '东方支付平台支付账号',
  `status` char(6) DEFAULT '0' COMMENT '状态',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=932 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pf_puchaseforeign_detail`
-- ----------------------------
DROP TABLE IF EXISTS `pf_puchaseforeign_detail`;
CREATE TABLE `pf_puchaseforeign_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pf_info_id` varchar(64) NOT NULL COMMENT '购汇记录Id',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `orderst` varchar(10) NOT NULL COMMENT '订单状态',
  `currency` varchar(6) NOT NULL COMMENT '收款币种',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) NOT NULL COMMENT '支付流水',
  `pay_channel` varchar(12) NOT NULL COMMENT '支付方式',
  `pay_status` varchar(12) NOT NULL COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` bigint(20) NOT NULL COMMENT '支付金额',
  `goods_amt` bigint(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` bigint(10) DEFAULT NULL COMMENT '运费金额',
  `activities_amt` bigint(20) unsigned zerofill DEFAULT NULL COMMENT '活动金额',
  `commission` bigint(20) DEFAULT NULL COMMENT '佣金',
  `tax` bigint(20) DEFAULT NULL COMMENT '税费',
  `kjt_amt` bigint(20) DEFAULT NULL COMMENT 'kjt代扣金额',
  `custid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `inserttime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='购汇详情表，购汇记录的详情以及购汇报文生成数据的来源';

-- ----------------------------
--  Table structure for `pf_puchaseforeign_info`
-- ----------------------------
DROP TABLE IF EXISTS `pf_puchaseforeign_info`;
CREATE TABLE `pf_puchaseforeign_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `application_Time` datetime DEFAULT NULL COMMENT '申请时间',
  `bizAudit_Time` datetime DEFAULT NULL COMMENT '业务审核时间',
  `financeAudit_Time` datetime DEFAULT NULL COMMENT '财务审核时间',
  `application_UserId` char(11) DEFAULT NULL COMMENT '申请用户',
  `bizAudit_UserId` char(11) DEFAULT NULL COMMENT '审核者id',
  `financeAudit_UserId` char(11) DEFAULT NULL COMMENT '财务审核者id',
  `audit_Status` char(6) DEFAULT NULL COMMENT '审核状态',
  `status` char(6) DEFAULT NULL COMMENT '状态',
  `create_Time` datetime NOT NULL,
  `modify_Time` datetime NOT NULL,
  `pf_status` char(6) DEFAULT NULL COMMENT '购汇状态',
  `pf_amt` int(11) DEFAULT NULL COMMENT '购汇金额',
  `kjt_amt` int(11) DEFAULT NULL COMMENT 'kjt代扣金额',
  `goods_amt` int(11) DEFAULT NULL COMMENT '货款金额',
  `purchaseSn` varchar(255) DEFAULT NULL COMMENT '购汇批次',
  `purchaseToDate` char(20) DEFAULT NULL COMMENT '购汇报表起始时间',
  `purchaseFromDate` char(20) DEFAULT NULL COMMENT '购汇报表终止时间',
  `tariff_amt` int(11) DEFAULT NULL COMMENT '行邮税',
  `ship_amt` int(11) DEFAULT NULL COMMENT '运费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pf_puchaseforeignsetting`
-- ----------------------------
DROP TABLE IF EXISTS `pf_puchaseforeignsetting`;
CREATE TABLE `pf_puchaseforeignsetting` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` char(11) DEFAULT NULL,
  `merchant_name` varchar(255) DEFAULT NULL,
  `status` char(6) DEFAULT NULL,
  `pf_type` char(6) DEFAULT NULL,
  `pf_cycle` char(6) DEFAULT NULL,
  `pf_data` int(11) DEFAULT NULL,
  `paychannel_type` char(25) DEFAULT NULL,
  `paychannel_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pf_puchforeign_diff`
-- ----------------------------
DROP TABLE IF EXISTS `pf_puchforeign_diff`;
CREATE TABLE `pf_puchforeign_diff` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `merchant_lv3` varchar(64) DEFAULT NULL COMMENT '三级商户节点号',
  `pf_cur` varchar(20) DEFAULT '1' COMMENT '币种，默认为人民币',
  `pf_amount` int(11) DEFAULT NULL COMMENT '购汇金额',
  `pf_freight` int(11) DEFAULT NULL COMMENT '购汇运费',
  `goods_amt` int(11) DEFAULT NULL COMMENT '货款金额',
  `foreign_currency_freight` int(255) DEFAULT NULL COMMENT '运费',
  `diff_amount` int(255) DEFAULT NULL COMMENT '差异金额',
  `pf_date` date DEFAULT NULL COMMENT '购汇日期',
  `pf_batch_no` varchar(500) DEFAULT NULL COMMENT '购汇批次号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79758 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pf_purchase_cycle`
-- ----------------------------
DROP TABLE IF EXISTS `pf_purchase_cycle`;
CREATE TABLE `pf_purchase_cycle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `type` varchar(20) NOT NULL DEFAULT '10' COMMENT '类型，10=全局订单，20=支付方式，30=特定商户',
  `cycle` varchar(20) NOT NULL COMMENT '周期',
  `pay_channel` varchar(20) DEFAULT NULL COMMENT '支付渠道， code跟ecc支付渠道一样',
  `merchant_id` varchar(64) DEFAULT NULL COMMENT '商户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(64) DEFAULT '' COMMENT '创建用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `audit_status` varchar(20) DEFAULT NULL COMMENT '审核状态',
  `audit_user_id` varchar(64) DEFAULT NULL COMMENT '审核用户id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `is_delete` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否删除，0=否，1=是',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='购汇周期表';

-- ----------------------------
--  Table structure for `pf_purchase_detail`
-- ----------------------------
DROP TABLE IF EXISTS `pf_purchase_detail`;
CREATE TABLE `pf_purchase_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pf_info_id` bigint(20) DEFAULT NULL,
  `purchasingNo` char(20) DEFAULT NULL COMMENT '购汇批次号',
  `orderNo` char(64) DEFAULT NULL COMMENT '订单号',
  `purchasingStatus` char(6) DEFAULT NULL COMMENT '购汇状态',
  `orderPurchasingStauts` char(6) DEFAULT NULL COMMENT '订单购汇状态',
  `purchasingSn` char(64) DEFAULT NULL,
  `purchasingAmt` int(11) DEFAULT NULL COMMENT '购汇金额',
  `orderPurchasingAmt` int(11) DEFAULT NULL COMMENT '订单购汇金额',
  `errInfo` varchar(100) DEFAULT NULL COMMENT '错误信息',
  `receiveCurrencyCode` char(64) DEFAULT NULL COMMENT '购汇币种',
  `shipCurrencyCode` char(64) DEFAULT NULL COMMENT '运费币种',
  `merchantCodeI` char(64) DEFAULT NULL COMMENT '一级商户号',
  `merchantCodeII` char(64) DEFAULT NULL COMMENT '二级商户号',
  `paySn` char(64) DEFAULT NULL COMMENT '支付流水号',
  `bankCardNo` char(64) DEFAULT NULL COMMENT '银行卡号',
  `customName` varchar(30) DEFAULT NULL COMMENT '客户姓名',
  `idCard` char(64) DEFAULT NULL COMMENT '身份证',
  `phoneNo` char(30) DEFAULT NULL COMMENT '电话号码',
  `email` char(128) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(4) DEFAULT NULL COMMENT '性别',
  `orderAmt` int(11) DEFAULT NULL COMMENT '订单金额',
  `shipAmt` int(11) DEFAULT NULL COMMENT '运费金额',
  `tariffAmt` int(11) DEFAULT NULL COMMENT '税款金额',
  `purchaseTime` datetime DEFAULT NULL COMMENT '购汇时间',
  `payTime` datetime DEFAULT NULL COMMENT '支付时间',
  `payChannel` varchar(20) DEFAULT NULL COMMENT '支付渠道',
  `insterttime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `status` char(6) DEFAULT NULL,
  `rsv1` varchar(128) DEFAULT NULL,
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=139706 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pf_purchase_main`
-- ----------------------------
DROP TABLE IF EXISTS `pf_purchase_main`;
CREATE TABLE `pf_purchase_main` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchasingNo` char(20) DEFAULT NULL COMMENT '购汇批次号',
  `orderNo` char(64) DEFAULT NULL COMMENT '订单号',
  `purchaseTime` timestamp(3) NULL DEFAULT NULL COMMENT '购汇时间',
  `purchasingStatus` char(6) DEFAULT NULL COMMENT '批次购汇状态',
  `orderPurchasingStauts` char(6) DEFAULT NULL COMMENT '订单购汇状态',
  `purchasingSn` char(64) DEFAULT NULL,
  `purchasingAmt` int(11) DEFAULT NULL COMMENT '购汇金额',
  `orderPurchasingAmt` int(11) DEFAULT NULL COMMENT '订单购汇金额',
  `errInfo` varchar(100) DEFAULT NULL COMMENT '错误信息',
  `receiveCurrencyCode` char(64) DEFAULT NULL COMMENT '购汇币种',
  `shipCurrencyCode` char(64) DEFAULT NULL COMMENT '运费币种',
  `merchantCodeI` char(64) DEFAULT NULL COMMENT '一级商户号',
  `merchantCodeII` char(64) DEFAULT NULL COMMENT '二级商户号',
  `paySn` char(64) DEFAULT NULL COMMENT '支付流水号',
  `bankCardNo` char(64) DEFAULT NULL COMMENT '银行卡号',
  `customName` varchar(30) DEFAULT NULL COMMENT '客户姓名',
  `idCard` char(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '身份证',
  `phoneNo` char(30) DEFAULT NULL COMMENT '电话号码',
  `email` char(128) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(4) DEFAULT NULL COMMENT '性别',
  `orderAmt` int(11) DEFAULT NULL COMMENT '订单金额',
  `shipAmt` int(11) DEFAULT NULL COMMENT '运费金额',
  `tariffAmt` int(11) DEFAULT NULL COMMENT '税款金额',
  `payTime` datetime DEFAULT NULL COMMENT '支付时间',
  `payChannel` varchar(20) DEFAULT NULL COMMENT '支付渠道',
  `inserttime` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL,
  `status` char(6) DEFAULT '1',
  `rsv1` varchar(128) DEFAULT NULL,
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) DEFAULT NULL,
  `coupon` int(20) DEFAULT NULL COMMENT '优惠券',
  `goods_original_price` int(20) DEFAULT NULL COMMENT '商品原价',
  `trx_serno` char(26) DEFAULT NULL COMMENT '交易流水号',
  `otrx_serno` char(32) DEFAULT NULL COMMENT '原交易流水号',
  `etrx_serno` char(26) DEFAULT NULL COMMENT '银行交易流水号',
  `dracc_name` varchar(100) DEFAULT NULL COMMENT '付款方户名',
  `pay_amt` varchar(17) DEFAULT NULL COMMENT '付款金额',
  `ex_amt` varchar(17) DEFAULT NULL COMMENT '购汇金额',
  `ex_rate` varchar(18) DEFAULT NULL COMMENT '购汇汇率',
  `pay_cur` char(3) DEFAULT NULL COMMENT '付款币种',
  `ex_cur` char(3) DEFAULT NULL COMMENT '购汇币种',
  `ex_status` char(6) DEFAULT '00' COMMENT '购汇状态\r\n00-未处理\r\nN-未购汇\r\nP-处理中\r\nS-成功\r\nF-失败\r\nC-退款',
  `expurch_notice_time` timestamp NULL DEFAULT NULL COMMENT '购汇结果通知时间',
  `rf_no` char(20) DEFAULT NULL,
  `exrem_amt` varchar(17) DEFAULT NULL COMMENT '付汇金额',
  `exrem_status` char(6) DEFAULT '00' COMMENT '付汇状态\r\n00-未处理\r\nN-未付汇\r\nP-处理中\r\nS-成功\r\nF-失败\r\nC-退款\r\n',
  `exrem_errcode` char(6) DEFAULT NULL COMMENT '付汇错误代码',
  `exrem_errinfo` varchar(100) DEFAULT NULL COMMENT '付汇错误信息',
  `exrem_notice_time` timestamp NULL DEFAULT NULL COMMENT '付汇结果通知时间',
  `exrem_process_time` timestamp NULL DEFAULT NULL COMMENT '付汇指令请求时间',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `source` varchar(20) DEFAULT NULL COMMENT '订单来源None = 0,Phone = 1, Wechat = 2, IPhone = 3, Android = 4,API = 5,Msite = 6',
  `merchant_sysno` int(32) DEFAULT NULL COMMENT '对应SO_CheckShipping表的MerchantSysNo',
  `payChannelCode` varchar(20) DEFAULT NULL COMMENT '支付方式(转义后)',
  `perinfo_trx_serno` char(26) DEFAULT NULL COMMENT '个人信息同步交易流水号',
  `perinfo_rtn_code` char(6) DEFAULT NULL COMMENT '个人信息同步返回代码',
  `perinfo_rtn_info` varchar(100) DEFAULT NULL COMMENT '个人信息同步返回信息',
  `extrans_rtn_code` char(6) DEFAULT NULL COMMENT '购汇批次返回代码',
  `extrans_rtn_info` varchar(100) DEFAULT NULL COMMENT '购汇批次返回信息',
  `extrans_errcode` char(6) DEFAULT NULL COMMENT '购汇错误代码',
  `extrans_errinfo` varchar(100) DEFAULT NULL COMMENT '购汇错误信息',
  `netting_amt` int(20) NOT NULL DEFAULT '0' COMMENT '轧差金额',
  `netting_source` varchar(20) DEFAULT NULL COMMENT '退款订单源',
  `netting_type` varchar(20) DEFAULT NULL COMMENT '退款操作类型',
  `netting_time` timestamp NULL DEFAULT NULL COMMENT '轧差操作时间',
  `netting_status` varchar(20) DEFAULT '00' COMMENT '是否轧差 01-已轧差 其它为未轧差',
  `netting_batch_status` varchar(2) DEFAULT '0' COMMENT '批量更新订单状态为已轧差:0-默认 1-已轧差',
  `netting_notice_result` varchar(6) DEFAULT 'N' COMMENT '轧差通知结果',
  `audit_id` bigint(20) DEFAULT NULL COMMENT '手动购汇审核编号',
  `realPurchasingAmt` int(11) DEFAULT NULL COMMENT '实际购汇金额',
  `customer_sysno` int(32) DEFAULT NULL COMMENT '客户编号',
  `is_gouhui` varchar(2) NOT NULL DEFAULT '0' COMMENT '是否购汇，1=是，0=否',
  `is_fuhui` varchar(2) NOT NULL DEFAULT '0' COMMENT '是否购汇，1=是，0=否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idux_orderno` (`purchasingNo`,`orderNo`) USING BTREE,
  KEY `idx_orderno` (`orderNo`) USING BTREE,
  KEY `idx_ purchaseTime` (`purchaseTime`) USING BTREE,
  KEY `idx_paytime` (`payTime`),
  KEY `idx_ordertime` (`ordertime`) USING BTREE,
  KEY `idx_perinfo_trx_serno` (`perinfo_trx_serno`) USING BTREE,
  KEY `idx_otrx_serno` (`otrx_serno`) USING BTREE,
  KEY `idx_merchant_sysno` (`merchant_sysno`) USING BTREE,
  KEY `inx_merchantCodeII` (`merchantCodeII`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123457440812 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `pf_purchasedaily`
-- ----------------------------
DROP TABLE IF EXISTS `pf_purchasedaily`;
CREATE TABLE `pf_purchasedaily` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `currency` char(30) DEFAULT NULL COMMENT '购汇币种',
  `freight_cur` char(30) DEFAULT NULL COMMENT '运费币种',
  `merchant_name` varchar(150) DEFAULT NULL COMMENT '商户名称',
  `merchant_code` varchar(30) DEFAULT NULL COMMENT '商户编码',
  `comment` varchar(255) DEFAULT NULL,
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `rs1` varchar(128) DEFAULT NULL,
  `rs2` varchar(128) DEFAULT NULL,
  `rs3` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `poundage_info`
-- ----------------------------
DROP TABLE IF EXISTS `poundage_info`;
CREATE TABLE `poundage_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `paytype` varchar(20) NOT NULL COMMENT '交易类型',
  `collect_date` date NOT NULL COMMENT '汇总日期（支付日期）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `east_pay_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '东方支付汇总金额',
  `caifutong_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '财付通总金额',
  `cup_pay_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '银联支付总金额',
  `cup_b2c_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '银联B2C总金额',
  `we_chat_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '微信支付总金额',
  `alipay_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '支付宝总金额',
  `pingan_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '平安银行总金额',
  PRIMARY KEY (`id`),
  UNIQUE KEY `poundage_info_paytype_cd_uq` (`paytype`,`collect_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=504 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `reconcilefile_log`
-- ----------------------------
DROP TABLE IF EXISTS `reconcilefile_log`;
CREATE TABLE `reconcilefile_log` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `paychannel_name` varchar(60) DEFAULT NULL COMMENT '存储最后文件的md5',
  `paychannel_type` char(25) DEFAULT NULL,
  `filename` varchar(200) DEFAULT NULL COMMENT '导入文件名',
  `importinfo` varchar(255) DEFAULT NULL COMMENT '导入信息',
  `status` varchar(60) DEFAULT NULL COMMENT '状态标示：y正常  n非正常',
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1402 DEFAULT CHARSET=utf8 COMMENT='支付通道对帐文件导入记录表';

-- ----------------------------
--  Table structure for `reconcilefile_log_detail`
-- ----------------------------
DROP TABLE IF EXISTS `reconcilefile_log_detail`;
CREATE TABLE `reconcilefile_log_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `err_type` char(20) DEFAULT NULL,
  `err_info` varchar(255) DEFAULT NULL,
  `order_no` char(20) DEFAULT NULL,
  `channel` char(20) DEFAULT NULL,
  `err_time` datetime DEFAULT NULL,
  `rsv1` varchar(128) DEFAULT NULL,
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=311796 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `refund_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `refund_orderinfo`;
CREATE TABLE `refund_orderinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `document_no` varchar(32) DEFAULT NULL COMMENT '单据号',
  `document_type` varchar(20) DEFAULT NULL COMMENT '单据类型',
  `refund_order_no` varchar(64) DEFAULT NULL COMMENT '退款单号',
  `refund_state` varchar(20) DEFAULT NULL COMMENT '退款状态',
  `refund_amount` decimal(20,0) DEFAULT NULL COMMENT '退款金额',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `original_order_amount` decimal(20,0) DEFAULT NULL COMMENT '原订单金额',
  `refund_amount_difference` decimal(20,0) DEFAULT NULL COMMENT '退款差额',
  `pay_channel` varchar(20) DEFAULT NULL COMMENT '支付渠道， code跟ecc支付渠道一样',
  `pay_refund_state` varchar(20) DEFAULT NULL COMMENT '退款类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_msg` varchar(255) DEFAULT NULL COMMENT '创建信息',
  `create_user_id` varchar(64) DEFAULT '' COMMENT '创建用户ID',
  `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人姓名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `audit_status` varchar(20) DEFAULT NULL COMMENT '审核状态',
  `audit_user_id` varchar(64) DEFAULT NULL COMMENT '审核用户id',
  `audit_user_name` varchar(100) DEFAULT NULL COMMENT '审核用户名',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `is_delete` varchar(20) NOT NULL DEFAULT '0' COMMENT '是否删除，0=否，1=是',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `ba_flag` char(1) NOT NULL DEFAULT '0' COMMENT '平账状态[0:未平账 1:已平账]',
  `ba_user_id` varchar(30) DEFAULT NULL COMMENT '平账人',
  `ba_date` varchar(30) DEFAULT NULL COMMENT '平账日期',
  `to_flag` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '30' COMMENT '平帐，差异查询以后过滤标识 10进入了平帐  20进入差异 30都没有进入',
  `pay_channel_code` varchar(20) DEFAULT NULL COMMENT '支付方式(转义后)',
  PRIMARY KEY (`id`),
  KEY `idx_orderno` (`order_no`) USING BTREE,
  KEY `idx_refund_time` (`refund_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28713 DEFAULT CHARSET=utf8 COMMENT='退款单表';

-- ----------------------------
--  Table structure for `restful_interface_info`
-- ----------------------------
DROP TABLE IF EXISTS `restful_interface_info`;
CREATE TABLE `restful_interface_info` (
  `id` int(23) NOT NULL AUTO_INCREMENT,
  `appId` varchar(30) DEFAULT NULL COMMENT '第三方应用唯一标示接口',
  `requestId` varchar(50) DEFAULT '' COMMENT '请求id 唯一标记一次请求，全网唯一构成(YYYYMMDDHHMMSS+平台(2位每个调用平台唯一))+8位序号',
  `token` varchar(255) DEFAULT NULL COMMENT 'MD5校验串',
  `bussOperTime` varchar(255) DEFAULT NULL COMMENT '调用接口的时间',
  `create_date` timestamp NULL DEFAULT NULL,
  `statusCode` varchar(10) DEFAULT NULL COMMENT '操作返回码',
  `message` varchar(2000) DEFAULT NULL,
  `update_Time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ind_requestId` (`requestId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=496141 DEFAULT CHARSET=utf8 COMMENT='保证成功的接口调用参数';

-- ----------------------------
--  Table structure for `rf_pay_application`
-- ----------------------------
DROP TABLE IF EXISTS `rf_pay_application`;
CREATE TABLE `rf_pay_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rf_no` char(20) DEFAULT NULL,
  `detail_status` char(6) DEFAULT NULL,
  `merchant_id` int(11) NOT NULL,
  `application_Time` datetime DEFAULT NULL COMMENT '申请时间',
  `bizAudit_Time` datetime DEFAULT NULL COMMENT '业务审核时间',
  `financeAudit_Time` datetime DEFAULT NULL COMMENT '财务审核时间',
  `application_UserId` char(11) DEFAULT NULL COMMENT '申请用户',
  `bizAudit_UserId` char(11) DEFAULT NULL COMMENT '审核者id',
  `financeAudit_UserId` char(11) DEFAULT NULL COMMENT '财务审核者id',
  `audit_Status` char(6) DEFAULT NULL COMMENT '审核状态',
  `status` char(6) DEFAULT NULL COMMENT '状态',
  `create_Time` datetime DEFAULT NULL,
  `modify_Time` datetime DEFAULT NULL,
  `rf_status` char(6) DEFAULT NULL COMMENT '购汇状态',
  `rf_code` char(15) DEFAULT NULL COMMENT '购汇金额',
  `rf_amt` int(11) DEFAULT NULL COMMENT 'kjt代扣金额',
  `rf_order_amt` int(11) DEFAULT NULL COMMENT '货款金额',
  `rfSn` char(15) DEFAULT NULL COMMENT '购汇批次',
  `payToDate` char(20) DEFAULT NULL COMMENT '购汇报表起始时间',
  `payFromDate` char(20) DEFAULT NULL COMMENT '购汇报表终止时间',
  `order_count` int(11) DEFAULT NULL COMMENT '行邮税',
  `rsv1` varchar(128) DEFAULT NULL,
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `rf_pay_application_detail`
-- ----------------------------
DROP TABLE IF EXISTS `rf_pay_application_detail`;
CREATE TABLE `rf_pay_application_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rfap_id` bigint(20) DEFAULT NULL COMMENT '申请单外键',
  `orderNo` char(20) DEFAULT NULL COMMENT '订单号',
  `merchant_code` char(20) DEFAULT NULL COMMENT '商户号',
  `order_amt` int(11) DEFAULT NULL COMMENT '订单金额',
  `rf_amt` int(11) DEFAULT NULL COMMENT '付汇金额',
  `orderTime` datetime DEFAULT NULL COMMENT '订单时间',
  `rfSn` char(15) DEFAULT NULL COMMENT '付汇流水',
  `rf_code` char(15) DEFAULT NULL COMMENT '外币code',
  `rf_status` char(15) DEFAULT NULL COMMENT '付汇状态',
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `rsv1` varchar(255) DEFAULT NULL,
  `rsv2` varchar(255) DEFAULT NULL,
  `rsv3` varchar(255) DEFAULT NULL,
  `audit_status` char(6) DEFAULT NULL,
  `trx_serno` char(26) DEFAULT NULL COMMENT '付汇请求交易流水号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=775 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `rf_payrule`
-- ----------------------------
DROP TABLE IF EXISTS `rf_payrule`;
CREATE TABLE `rf_payrule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) DEFAULT NULL COMMENT '商户id',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `last_pay_time` datetime DEFAULT NULL COMMENT '最后结算日期',
  `pay_type` char(6) DEFAULT NULL COMMENT '周结ww；半月结hm；月结；mm半年结hy；年结yy',
  `basic_amt` int(255) unsigned zerofill DEFAULT NULL COMMENT '起付金额',
  `comment` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` char(6) DEFAULT NULL COMMENT '状态 y启用 n未启用  d删除',
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `rs1` varchar(128) DEFAULT NULL,
  `rs2` varchar(128) DEFAULT NULL,
  `rs3` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `rf_payrule_info`
-- ----------------------------
DROP TABLE IF EXISTS `rf_payrule_info`;
CREATE TABLE `rf_payrule_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rf_id` bigint(20) DEFAULT NULL COMMENT 'rf_payrule  外键',
  `auditTime` datetime DEFAULT NULL,
  `auditUser` char(20) DEFAULT NULL,
  `auditStatus` char(6) DEFAULT NULL COMMENT '审核不通过：bn 审核通过： by 未审核：nn',
  `rsv1` varchar(128) DEFAULT NULL,
  `rsv2` varchar(128) DEFAULT NULL,
  `rsv3` varchar(128) DEFAULT NULL,
  `inserttime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sale_bill_item`
-- ----------------------------
DROP TABLE IF EXISTS `sale_bill_item`;
CREATE TABLE `sale_bill_item` (
  `SysNo` int(11) NOT NULL,
  `SaleBillSysNo` int(11) DEFAULT NULL,
  `SOSysNo` int(11) DEFAULT NULL,
  `PurchasingAmt` varchar(25) DEFAULT NULL,
  `CompanyCode` varchar(50) DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateUser` varchar(100) DEFAULT NULL,
  `EditDate` datetime(3) DEFAULT NULL,
  `EditUser` varchar(100) DEFAULT NULL,
  `Status` varchar(255) DEFAULT NULL COMMENT '0：初始状态，1:审核通过，2:审核拒绝，－1:已经作废，3购汇完成',
  `ChannelSysNo` int(11) DEFAULT NULL COMMENT '分销渠道号',
  `SaleChannelMode` int(11) DEFAULT NULL COMMENT '分销渠道模式',
  PRIMARY KEY (`SysNo`),
  KEY `idx_orderno` (`SOSysNo`) USING BTREE COMMENT '订单号',
  KEY `idx_SaleBillSysNo` (`SaleBillSysNo`) USING BTREE COMMENT 'master号码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `sale_channel`
-- ----------------------------
DROP TABLE IF EXISTS `sale_channel`;
CREATE TABLE `sale_channel` (
  `SysNo` int(11) NOT NULL,
  `ChannelName` varchar(100) COLLATE utf8_bin NOT NULL,
  `Note` varchar(500) COLLATE utf8_bin NOT NULL,
  `Status` int(11) NOT NULL,
  `VendorSysNo` int(11) NOT NULL,
  `SaleChannelMode` int(11) DEFAULT NULL,
  `AuditDate` datetime DEFAULT NULL,
  `AuditUser` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `CreateDate` datetime DEFAULT NULL,
  `CreateUser` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `EditDate` datetime DEFAULT NULL,
  `EditUser` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SysNo`),
  KEY `idx_SysNo` (`SysNo`) USING BTREE,
  KEY `idx_SaleChannelMode` (`SaleChannelMode`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `seq_generate`
-- ----------------------------
DROP TABLE IF EXISTS `seq_generate`;
CREATE TABLE `seq_generate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seq_name` varchar(100) DEFAULT '0' COMMENT '序列名称',
  `seq_num` bigint(20) DEFAULT '0' COMMENT '序列号',
  `status_desc` varchar(255) DEFAULT NULL COMMENT '状态描述:是否被占用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='序列号维护表';

-- ----------------------------
--  Table structure for `so_item`
-- ----------------------------
DROP TABLE IF EXISTS `so_item`;
CREATE TABLE `so_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `SOSysNo` varchar(64) NOT NULL COMMENT '订单编号',
  `ProductSysNo` int(20) NOT NULL,
  `Quantity` int(20) NOT NULL,
  `Weight` int(20) NOT NULL,
  `Price` int(20) NOT NULL,
  `Cost` int(20) NOT NULL,
  `Point` int(20) NOT NULL,
  `PointType` int(20) NOT NULL,
  `DiscountAmt` int(20) NOT NULL,
  `Warranty` text,
  `ProductType` int(20) NOT NULL,
  `GiftSysNo` int(20) DEFAULT NULL,
  `BriefName` varchar(200) DEFAULT NULL,
  `OriginalPrice` int(20) DEFAULT NULL,
  `PromotionDiscount` int(20) DEFAULT NULL,
  `IsDuplicateOrder` int(20) DEFAULT NULL,
  `DuplicateSOSysNo` text,
  `MasterProductSysNo` varchar(50) DEFAULT NULL,
  `WarehouseNumber` varchar(3) DEFAULT NULL,
  `UnitCostWithoutTax` int(20) NOT NULL,
  `IsMemberPrice` int(20) NOT NULL,
  `CompanyCode` char(50) DEFAULT NULL,
  `LanguageCode` char(5) DEFAULT NULL,
  `CurrencySysNo` int(20) DEFAULT NULL,
  `StoreCompanyCode` varchar(50) DEFAULT NULL,
  `IsShippedOut` int(20) DEFAULT NULL,
  `ShippedOutTime` datetime DEFAULT NULL,
  `DiscountType` int(20) DEFAULT NULL,
  `LeaseSettled` char(1) DEFAULT NULL,
  `TariffAmt` int(20) DEFAULT NULL,
  `TariffCode` varchar(8) DEFAULT NULL,
  `TariffRate` decimal(6,2) DEFAULT NULL,
  `EntryRecord` varchar(20) DEFAULT NULL,
  `StoreType` int(20) DEFAULT NULL,
  `ExchangeRate` decimal(19,6) DEFAULT NULL,
  `PlatPromotionDiscount` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_SO_Item` (`SOSysNo`) USING BTREE,
  KEY `IX_SO_Item_ProductType` (`ProductType`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=113204221 DEFAULT CHARSET=utf8 COMMENT='SO_Item订单明细表镜像';

-- ----------------------------
--  Table structure for `sql_svr`
-- ----------------------------
DROP TABLE IF EXISTS `sql_svr`;
CREATE TABLE `sql_svr` (
  `sysno` varchar(30) DEFAULT NULL,
  `utime` timestamp(3) NULL DEFAULT NULL,
  `otime` timestamp(3) NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tax_audit`
-- ----------------------------
DROP TABLE IF EXISTS `tax_audit`;
CREATE TABLE `tax_audit` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `batch_number` varchar(64) NOT NULL COMMENT '批次号',
  `audit_state` varchar(20) NOT NULL DEFAULT '10' COMMENT '审核状态（10=未审核，30=审核通过,40=审核失败）',
  `tax_total_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '行邮税总金额',
  `tax_currency` varchar(20) NOT NULL DEFAULT 'CNY' COMMENT '行邮税币种',
  `is_delete` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否删除， 1=是，0=否',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人id',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `submit_audit_time` datetime DEFAULT NULL COMMENT '提交审核时间',
  `submit_audit_user_id` varchar(32) DEFAULT NULL COMMENT '提交审核人id',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核人id',
  `confirm_audit_time` datetime DEFAULT NULL COMMENT '审核确认时间',
  `confirm_audit_user_id` varchar(32) DEFAULT NULL COMMENT '审核确认人id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tax_audit_details`
-- ----------------------------
DROP TABLE IF EXISTS `tax_audit_details`;
CREATE TABLE `tax_audit_details` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `batch_number` varchar(64) NOT NULL COMMENT '批次号',
  `audit_state` varchar(20) NOT NULL DEFAULT '10' COMMENT '审核状态（10=未审核，30=审核通过,40=审核失败）',
  `tax_total_amount` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '行邮税总金额',
  `territory_merchant_number` varchar(64) DEFAULT NULL COMMENT '境内商户号',
  `territory_org_number` varchar(64) DEFAULT NULL COMMENT '境内商户组织机构代码',
  `territory_merchant_code` varchar(64) DEFAULT NULL COMMENT '境内商户节点',
  `territory_merchant_name` varchar(128) DEFAULT NULL COMMENT '境内商户名称',
  `abroad_merchant_number` varchar(64) NOT NULL COMMENT '境外商户号',
  `trx_serno` char(26) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '交易流水号',
  `recharge_state` varchar(20) NOT NULL DEFAULT '01' COMMENT '充值状态(01待充值，02充值中，03充值成功，99充值失败)',
  `is_delete` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否删除， 1=是，0=否',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `rdo_time` datetime DEFAULT NULL COMMENT '支付平台处理时间',
  `rtrx_serno` char(26) DEFAULT NULL COMMENT '支付平台交易流水号',
  `rtn_code` char(6) DEFAULT NULL COMMENT '返回代码',
  `ertn_code` varchar(12) DEFAULT NULL COMMENT '扩展返回代码',
  `ertn_info` varchar(128) DEFAULT NULL COMMENT '扩展返回信息',
  `rtn_pay_cur` varchar(6) DEFAULT NULL COMMENT '返回支付币种',
  `rtn_pay_tamt` varchar(20) DEFAULT NULL COMMENT '返回支付总金额',
  `rtn_pay_balance` varchar(20) DEFAULT NULL COMMENT '返回付款账户交易后余额',
  `is_repeat_send` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否是重复发送， 1=是，0=否',
  `old_trx_serno` char(26) DEFAULT NULL COMMENT '老的交易流水号，is_repeat_send=1时有值',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1091 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tax_order_record`
-- ----------------------------
DROP TABLE IF EXISTS `tax_order_record`;
CREATE TABLE `tax_order_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主健',
  `tax_batch_number` varchar(64) NOT NULL COMMENT '批次号',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `orderst` varchar(10) NOT NULL COMMENT '订单状态',
  `tax` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '行邮税金额',
  `ordertime` datetime NOT NULL COMMENT '订单时间',
  `domestic_vendor_sys_no` varchar(64) DEFAULT NULL COMMENT '境内商户号',
  `merchant_lv2` varchar(64) NOT NULL COMMENT '境外商户号',
  `purchas_status` int(6) NOT NULL COMMENT '购汇状态[0:未购汇,1:待购汇,2:购汇中,3:购汇完成,-3:购汇异常,-1:无需购汇]',
  `distribution_order_audit_state` varchar(20) DEFAULT NULL COMMENT '分销订单审核状态',
  `sale_channel_sysno` int(32) DEFAULT NULL COMMENT '分销渠道',
  `source` varchar(20) NOT NULL DEFAULT '0' COMMENT '订单来源None = 0,Phone = 1, Wechat = 2, IPhone = 3, Android = 4,API = 5,Msite = 6',
  `is_delete` varchar(3) NOT NULL DEFAULT '0' COMMENT '是否删除， 1=是，0=否',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  PRIMARY KEY (`id`),
  KEY `tax_order_record_tax_batch_number_index` (`tax_batch_number`)
) ENGINE=InnoDB AUTO_INCREMENT=287996 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `tb_sequence`;
CREATE TABLE `tb_sequence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL,
  `current_value` int(11) NOT NULL,
  `_increment` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_sequence_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `temp_ecc_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `temp_ecc_orderinfo`;
CREATE TABLE `temp_ecc_orderinfo` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主键',
  `orderno` varchar(64) NOT NULL COMMENT '订单编号',
  `orderst` varchar(10) NOT NULL COMMENT '订单状态',
  `currency` varchar(6) DEFAULT NULL COMMENT '收款币种',
  `freight_cur` varchar(6) DEFAULT NULL COMMENT '运费币种',
  `merchant_lv1` varchar(64) DEFAULT NULL COMMENT '一级商户节点号',
  `merchant_lv2` varchar(64) DEFAULT NULL COMMENT '二级商户节点号',
  `ordertime` datetime DEFAULT NULL COMMENT '订单时间',
  `pay_serialno` varchar(64) DEFAULT NULL COMMENT '支付流水',
  `pay_channel` varchar(12) NOT NULL DEFAULT '117' COMMENT '支付方式',
  `pay_status` varchar(12) DEFAULT '-1' COMMENT '支付状态',
  `paytime` datetime DEFAULT NULL COMMENT '支付时间',
  `payamt` int(20) DEFAULT NULL COMMENT '支付金额',
  `goods_amt` int(20) DEFAULT NULL COMMENT '货款金额',
  `freidght` int(10) DEFAULT NULL COMMENT '运费金额',
  `activities_amt` int(20) unsigned zerofill DEFAULT NULL COMMENT '活动金额',
  `commission` int(20) DEFAULT NULL COMMENT '佣金',
  `tax` int(20) DEFAULT NULL COMMENT '税费',
  `ba_date` varchar(30) DEFAULT NULL COMMENT '平账日期',
  `ba_user_id` varchar(30) DEFAULT NULL COMMENT '平账人',
  `ba_flag` char(1) NOT NULL DEFAULT '0' COMMENT '平账状态[0:未平账 1:已平账 5:强制平账]',
  `custid` varchar(32) DEFAULT NULL COMMENT '用户id',
  `inserttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `operator` varchar(64) DEFAULT NULL COMMENT '操作人',
  `refund_flag` char(1) DEFAULT '0' COMMENT '是否退过款[0:未退过款;1:退过款]',
  `refundtime` datetime DEFAULT NULL COMMENT '退款时间',
  `to_flag` varchar(20) NOT NULL DEFAULT '30' COMMENT '平帐，差异查询以后过滤标识 10进入了平帐  20进入差异 30都没有进入',
  `ecc_uptime` datetime DEFAULT NULL COMMENT 'ECC订单记录更新时间',
  `ship_via` varchar(30) DEFAULT NULL COMMENT '快递公司编号',
  `tracking_number` varchar(64) DEFAULT NULL COMMENT '运单号',
  `weight` int(20) DEFAULT NULL COMMENT '订单重量',
  `outtime` datetime DEFAULT NULL COMMENT '出库时间',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态[0:逻辑删除 1:正常]',
  `order_amt` int(20) DEFAULT NULL COMMENT '订单现金总额',
  `discount_amt` int(20) DEFAULT NULL COMMENT '折扣金额',
  `pf_currency` varchar(6) DEFAULT NULL COMMENT '购汇币种',
  `rsv1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
  `rsv2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
  `rsv3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
  `ba_flg_b` char(1) NOT NULL DEFAULT '0',
  `coupon` int(20) DEFAULT NULL COMMENT '优惠券',
  `goods_original_price` int(20) DEFAULT NULL COMMENT '商品原价',
  `cash_pay` int(20) DEFAULT NULL COMMENT '对应SO_Master的CashPay',
  `pay_price` int(20) DEFAULT NULL COMMENT '对应SO_Master的PayPrice',
  `ship_price` int(20) DEFAULT NULL COMMENT '对应SO_Master的ShipPrice',
  `premium_amt` int(20) DEFAULT NULL COMMENT '对应SO_Master的PremiumAmt',
  `source` varchar(20) NOT NULL DEFAULT '0' COMMENT '订单来源None = 0,Phone = 1, Wechat = 2, IPhone = 3, Android = 4,API = 5,Msite = 6',
  `auth_customer_sysno` int(32) DEFAULT NULL COMMENT '实名信息_客户编号',
  `auth_name` varchar(50) DEFAULT NULL COMMENT '实名信息_客户姓名',
  `auth_idcard_type` int(6) DEFAULT NULL COMMENT '实名信息_证件类型',
  `auth_idcard_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '实名信息_证件号码',
  `auth_birthday` date DEFAULT NULL COMMENT '实名信息_客户生日',
  `auth_phone_number` varchar(50) DEFAULT NULL COMMENT '实名信息_手机号码',
  `auth_email` varchar(200) DEFAULT NULL COMMENT '实名信息_电子邮箱',
  `auth_address` varchar(200) DEFAULT NULL COMMENT '实名信息_地址',
  `auth_gender` int(6) DEFAULT NULL COMMENT '实名信息_性别[0:女, 1:男]',
  `hold_mark` char(1) DEFAULT '0' COMMENT '冻结标志',
  `fep_status` int(6) DEFAULT NULL COMMENT '购汇状态(SO_CheckShipping表的ForeignExchangePurchasingStatus)',
  `sale_channel_sysno` int(32) DEFAULT NULL COMMENT '(对应SO_CheckShipping表的SaleChannelSysNo)',
  `fep_sale_bill_flag` char(1) DEFAULT NULL COMMENT 'ForeignExchangePurchasing_SaleBillMaster及ForeignExchangePurchasing_SaleBillItem是否有对应记录的标志[0:无,1:有]',
  `purchasing_amt` int(20) DEFAULT NULL COMMENT '购汇金额',
  `bank_trans_number` varchar(50) DEFAULT NULL COMMENT '对应Finance_NetPay的BankTransNumber',
  `pay_card_number` varchar(40) DEFAULT NULL COMMENT '对应Finance_NetPay的PayCardNumber',
  `receive_currency_code` varchar(20) DEFAULT NULL COMMENT '对应Vendor_Customs_Info表的ReceiveCurrencyCode',
  `ship_currency_code` varchar(20) DEFAULT 'CNY' COMMENT '对应Vendor_Customs_Info表的ShipCurrencyCode',
  `merchant_sysno` int(32) DEFAULT NULL COMMENT '对应SO_CheckShipping表的MerchantSysNo',
  `purchas_status` int(6) DEFAULT '0' COMMENT '购汇状态[0:未购汇,1:待购汇,2:购汇中,3:购汇完成,-3:购汇异常,-1:无需购汇]',
  `auth_syn_flag` char(1) DEFAULT '1' COMMENT '实名信息同步开关[''0'':不需要同步,''1'':需要同步]',
  `merchant_code` varchar(64) DEFAULT NULL COMMENT '二级商户东方支付备案号',
  `pay_channel_code` varchar(20) DEFAULT NULL COMMENT '支付方式(转义后)',
  `netting_amt` int(20) NOT NULL DEFAULT '0' COMMENT '轧差金额',
  `netting_source` varchar(20) DEFAULT NULL COMMENT '退款订单源',
  `netting_status` varchar(2) DEFAULT '00' COMMENT '是否轧差 01-已轧差 00和其它为未轧差',
  `netting_type` varchar(20) DEFAULT NULL COMMENT '退款操作类型: \r\n01轧差购汇退款\r\n02保证金退款\r\n03银行转账\r\n',
  `netting_time` timestamp NULL DEFAULT NULL COMMENT '轧差操作时间',
  `netting_notice_result` varchar(6) DEFAULT 'N' COMMENT '轧差通知结果',
  `tax_state` varchar(20) NOT NULL DEFAULT 'wjs' COMMENT '行邮税状态，（wjs=未缴税、clz=处理中、yjs=已交税、yts=已退税）',
  `tax_batch_number` varchar(64) DEFAULT NULL COMMENT '行邮税处理批次号',
  `status_time` datetime DEFAULT NULL COMMENT '出库时间',
  `out_status` varchar(2) DEFAULT '0' COMMENT '订单出库状态默认是0：未装车 1-已装车',
  `perinfo_trx_serno` char(26) DEFAULT NULL COMMENT '个人信息同步交易流水号',
  `perinfo_rtn_code` char(6) DEFAULT NULL COMMENT '个人信息同步返回代码',
  `perinfo_rtn_info` varchar(100) DEFAULT NULL COMMENT '个人信息同步返回信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `temp_payment`
-- ----------------------------
DROP TABLE IF EXISTS `temp_payment`;
CREATE TABLE `temp_payment` (
  `id` bigint(20) NOT NULL,
  `remname` longtext,
  `payname` longtext,
  `remcurr` varchar(30) DEFAULT NULL,
  `remAmt` longtext,
  `exchange` varchar(60) DEFAULT NULL,
  `exchangeAm` varchar(90) DEFAULT NULL,
  `orderno` varchar(90) DEFAULT NULL,
  `serialnum` varchar(240) DEFAULT NULL,
  `filename` longtext,
  `sheetname` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `test_margin`
-- ----------------------------
DROP TABLE IF EXISTS `test_margin`;
CREATE TABLE `test_margin` (
  `dectionNo` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`dectionNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `test_refund`
-- ----------------------------
DROP TABLE IF EXISTS `test_refund`;
CREATE TABLE `test_refund` (
  `dectionNo` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`dectionNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `tmp_customerorder`
-- ----------------------------
DROP TABLE IF EXISTS `tmp_customerorder`;
CREATE TABLE `tmp_customerorder` (
  `orderNo` int(11) NOT NULL,
  `status_time` datetime DEFAULT NULL,
  PRIMARY KEY (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tmp_order`
-- ----------------------------
DROP TABLE IF EXISTS `tmp_order`;
CREATE TABLE `tmp_order` (
  `orderno` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tsl_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `tsl_sequence`;
CREATE TABLE `tsl_sequence` (
  `id` bigint(20) NOT NULL,
  `sequence_type` char(20) NOT NULL,
  `sequence_no` int(11) NOT NULL,
  `sequence_date` datetime DEFAULT NULL,
  `rule_code` char(20) DEFAULT NULL,
  `rsv1` char(20) DEFAULT NULL,
  `rsv2` char(20) DEFAULT NULL,
  `rsv3` char(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
