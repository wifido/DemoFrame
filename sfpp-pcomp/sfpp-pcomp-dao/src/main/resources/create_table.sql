/*
Navicat MySQL Data Transfer

Source Server         : SFPP_DB_1
Source Server Version : 50628
Source Host           : 10.202.7.82:3306
Source Database       : pcomp1

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2016-08-15 18:18:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pcomp_kind
-- ----------------------------
DROP TABLE IF EXISTS `pcomp_kind`;
CREATE TABLE `pcomp_kind` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `pcomp_title_id` varchar(64) DEFAULT NULL COMMENT '对应公共组件主题主键',
  `name` varchar(64) DEFAULT NULL COMMENT '分类名称',
  `banner_image` varchar(512) DEFAULT NULL COMMENT '分类栏目图片',
  `top_photo` varchar(512) DEFAULT NULL COMMENT '分类栏目头像',
  `introduction` varchar(32) DEFAULT NULL COMMENT '一句话简介',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` int(11) NOT NULL DEFAULT '-1' COMMENT '创建人,默认为-1,-1是虚拟超管',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modified_by` int(11) NOT NULL DEFAULT '-1' COMMENT '修改人,默认为-1,-1是虚拟超管',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pcomp_title_id` (`pcomp_title_id`,`name`),
  KEY `pcomp_title_id_2` (`pcomp_title_id`),
  KEY `name` (`name`),
  KEY `created_time` (`created_time`),
  KEY `modified_time` (`modified_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公共组件类别表';

-- ----------------------------
-- Records of pcomp_kind
-- ----------------------------
INSERT INTO `pcomp_kind` VALUES ('1', '1', '编程语言/开发环境', 'http://10.202.7.85:8080/image/01/000/000014afb165e-da97-40f7-8690-23a26a9fbd53.png', 'http://10.202.7.85:8080/image/01/000/0000182c1cf36-056c-488a-8352-344d168221eb.png', '基本编程语言版本和开发环境搭建', '0', '2016-08-15 08:58:49', '-1', '2016-08-15 08:58:49', '-1');

-- ----------------------------
-- Table structure for pcomp_software
-- ----------------------------
DROP TABLE IF EXISTS `pcomp_software`;
CREATE TABLE `pcomp_software` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `pcomp_kind_id` varchar(64) DEFAULT NULL COMMENT '对应公共组件类别主键',
  `name` varchar(64) DEFAULT NULL COMMENT '软件名称',
  `avatar` varchar(512) DEFAULT NULL COMMENT '软件头像',
  `introduction_short` text COMMENT '简介',
  `introduction` text COMMENT '介绍',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` int(11) NOT NULL DEFAULT '-1' COMMENT '创建人,默认为-1,-1是虚拟超管',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modified_by` int(11) NOT NULL DEFAULT '-1' COMMENT '修改人,默认为-1,-1是虚拟超管',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pcomp_kind_id` (`pcomp_kind_id`,`name`),
  KEY `pcomp_kind_id_2` (`pcomp_kind_id`),
  KEY `name` (`name`),
  KEY `created_time` (`created_time`),
  KEY `modified_time` (`modified_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公共组件软件表';

-- ----------------------------
-- Records of pcomp_software
-- ----------------------------
INSERT INTO `pcomp_software` VALUES ('1', '1', 'Java', 'http://10.202.7.85:8080/image/01/000/000019d25ecf6-2899-490b-9c6b-be2f16a0ccd5.png', 'Java/JDK是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java/JDK语言具有功能强大和简单易用两个特征。', 'Java/JDK是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java/JDK语言具有功能强大和简单易用两个特征。', '0', '2016-08-15 11:26:03', '-1', '2016-08-15 11:26:03', '-1');

-- ----------------------------
-- Table structure for pcomp_title
-- ----------------------------
DROP TABLE IF EXISTS `pcomp_title`;
CREATE TABLE `pcomp_title` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '主题名称',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` int(11) NOT NULL DEFAULT '-1' COMMENT '创建人,默认为-1,-1是虚拟超管',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modified_by` int(11) NOT NULL DEFAULT '-1' COMMENT '修改人,默认为-1,-1是虚拟超管',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `created_time` (`created_time`),
  KEY `modified_time` (`modified_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公共组件主题表';

-- ----------------------------
-- Records of pcomp_title
-- ----------------------------
INSERT INTO `pcomp_title` VALUES ('1', '基础类', '0', '2016-08-13 18:40:32', '-1', '2016-08-13 18:40:32', '-1');
INSERT INTO `pcomp_title` VALUES ('2', 'web应用前端', '0', '2016-08-13 18:40:57', '-1', '2016-08-13 18:40:57', '-1');
INSERT INTO `pcomp_title` VALUES ('3', 'web应用后端', '0', '2016-08-13 18:41:01', '-1', '2016-08-13 18:41:08', '-1');
INSERT INTO `pcomp_title` VALUES ('4', '数据库相关', '0', '2016-08-13 18:41:17', '-1', '2016-08-13 18:41:17', '-1');
INSERT INTO `pcomp_title` VALUES ('5', '服务器相关', '0', '2016-08-13 18:41:26', '-1', '2016-08-13 18:41:26', '-1');
INSERT INTO `pcomp_title` VALUES ('6', '管理监控工具', '0', '2016-08-13 18:41:43', '-1', '2016-08-15 10:22:35', '-1');
INSERT INTO `pcomp_title` VALUES ('7', '其它', '0', '2016-08-13 18:41:46', '-1', '2016-08-13 18:41:50', '-1');

-- ----------------------------
-- Table structure for pcomp_version
-- ----------------------------
DROP TABLE IF EXISTS `pcomp_version`;
CREATE TABLE `pcomp_version` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `pcomp_software_id` varchar(64) DEFAULT NULL COMMENT '对应公共组件软件主键',
  `version_number` varchar(32) DEFAULT NULL COMMENT '版本号',
  `introduction` text COMMENT '简介',
  `quick_start` text COMMENT '快速上手文档',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` int(11) NOT NULL DEFAULT '-1' COMMENT '创建人,默认为-1,-1是虚拟超管',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modified_by` int(11) NOT NULL DEFAULT '-1' COMMENT '修改人,默认为-1,-1是虚拟超管',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pcomp_software_id` (`pcomp_software_id`,`version_number`),
  KEY `pcomp_software_id_2` (`pcomp_software_id`),
  KEY `version_number` (`version_number`),
  KEY `created_time` (`created_time`),
  KEY `modified_time` (`modified_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公共组件版本表';

-- ----------------------------
-- Records of pcomp_version
-- ----------------------------

-- ----------------------------
-- Table structure for pcomp_version_document_download
-- ----------------------------
DROP TABLE IF EXISTS `pcomp_version_document_download`;
CREATE TABLE `pcomp_version_document_download` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `pcomp_version_id` varchar(64) DEFAULT NULL COMMENT '对应公共组件软件版本主键',
  `description` varchar(64) DEFAULT NULL COMMENT '文档描述',
  `download` varchar(512) DEFAULT NULL COMMENT '文档下载地址',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  PRIMARY KEY (`id`),
  KEY `pcomp_version_id` (`pcomp_version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公共组件文档下载与描述对应关系表';

-- ----------------------------
-- Records of pcomp_version_document_download
-- ----------------------------

-- ----------------------------
-- Table structure for pcomp_version_platform_download
-- ----------------------------
DROP TABLE IF EXISTS `pcomp_version_platform_download`;
CREATE TABLE `pcomp_version_platform_download` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `pcomp_version_id` varchar(64) DEFAULT NULL COMMENT '对应公共组件软件版本主键',
  `platform` varchar(64) DEFAULT NULL COMMENT '下载版本使用对应平台',
  `download` varchar(512) DEFAULT NULL COMMENT '版本下载地址',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除,0否1是',
  PRIMARY KEY (`id`),
  KEY `pcomp_version_id` (`pcomp_version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公共组件版本下载与平台对应关系表';

-- ----------------------------
-- Records of pcomp_version_platform_download
-- ----------------------------
