
CREATE DATABASE `geektime_eshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
-- ----------------------------
--  Table structure for `tb_dict`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict`;
CREATE TABLE `tb_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典id',
  `DictType` varchar(30) NOT NULL COMMENT '类型',
  `DictName` varchar(30) NOT NULL COMMENT '名称',
  `ParamName` varchar(30) DEFAULT NULL COMMENT '参数名称',
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tb_discount_id`
-- ----------------------------
DROP TABLE IF EXISTS `tb_discount_id`;
CREATE TABLE `tb_discount_id` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) DEFAULT '' COMMENT '折扣名称',
  `goods_id` varchar(20) DEFAULT '' COMMENT '商品id',
  `discount` decimal(10,0) DEFAULT NULL COMMENT '折扣百分比',
  `valid_start_time` timestamp NULL DEFAULT NULL COMMENT '有效期开始时间',
  `valid_end_time` timestamp NULL DEFAULT NULL COMMENT '有效期结束时间',
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='折扣表';

-- ----------------------------
--  Table structure for `tb_goods`
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods`;
CREATE TABLE `tb_goods` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) DEFAULT '' COMMENT '商品编码',
  `name` varchar(20) DEFAULT '' COMMENT '商品名称',
  `type_id` bigint(20) DEFAULT NULL COMMENT '商品',
  `weight` decimal(10,0) DEFAULT NULL COMMENT '重量',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商id',
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
--  Table structure for `tb_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_type`;
CREATE TABLE `tb_goods_type` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) DEFAULT '' COMMENT '类型编码',
  `name` varchar(20) DEFAULT '' COMMENT '类型名称',
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品类型表';

-- ----------------------------
--  Table structure for `tb_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT '0' COMMENT '用户id',
  `amount` decimal(10,0) DEFAULT '0',
  `goods_id` bigint(20) DEFAULT '0' COMMENT '商品id',
  `discount_id` bigint(20) DEFAULT '0' COMMENT '折扣id',
  `status` bigint(20) DEFAULT '0' COMMENT '此处的状态是字典表中订单的状态',
  `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(255) DEFAULT '',
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by` varchar(255) DEFAULT '',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
--  Table structure for `tb_shopping_cart`
-- ----------------------------
DROP TABLE IF EXISTS `tb_shopping_cart`;
CREATE TABLE `tb_shopping_cart` (
  `id` bigint(20) NOT NULL,
  `user_id` varchar(20) DEFAULT '' COMMENT '用户id',
  `goods_id` varchar(20) DEFAULT '' COMMENT '商品id',
  `amount` decimal(10,0) DEFAULT NULL COMMENT '总价',
  `discount_id` bigint(20) DEFAULT NULL COMMENT '折扣id',
  `add_discount_id` bigint(20) DEFAULT NULL COMMENT '加入时的折扣id',
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- ----------------------------
--  Table structure for `tb_supplier`
-- ----------------------------
DROP TABLE IF EXISTS `tb_supplier`;
CREATE TABLE `tb_supplier` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) DEFAULT '' COMMENT '名称',
  `register_account` varchar(20) DEFAULT '' COMMENT '注册号',
  `contact_address` varchar(20) DEFAULT '' COMMENT '联系地址',
  `contact_user_id` bigint(20) DEFAULT NULL COMMENT '联系人id',
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商表';

-- ----------------------------
--  Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `account` varchar(255) DEFAULT NULL COMMENT '登陆名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `id_card` varchar(255) DEFAULT NULL,
  `update_at` timestamp NULL DEFAULT NULL,
  `update_by` timestamp NULL DEFAULT NULL,
  `create_at` timestamp NULL DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '0-正常；1-删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
