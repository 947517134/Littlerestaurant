/*
 Navicat Premium Data Transfer

 Source Server         : database
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : littlerest

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 22/11/2020 13:49:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of admin
-- ----------------------------
BEGIN;
INSERT INTO `admin` VALUES ('admin', '123456');
COMMIT;

-- ----------------------------
-- Table structure for dish
-- ----------------------------
DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
  `did` int NOT NULL AUTO_INCREMENT,
  `dname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dprice` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`did`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of dish
-- ----------------------------
BEGIN;
INSERT INTO `dish` VALUES (1, '龙井问茶龙井问茶', 1026.24);
INSERT INTO `dish` VALUES (2, '炭火烤鸡', 22.00);
INSERT INTO `dish` VALUES (3, '麻婆豆腐', 33.70);
INSERT INTO `dish` VALUES (4, '地锅鸡', 449.00);
INSERT INTO `dish` VALUES (5, '红烧娃娃鱼', 278.00);
INSERT INTO `dish` VALUES (6, '酸辣土豆丝', 12.00);
INSERT INTO `dish` VALUES (7, '咸菜', 7.00);
INSERT INTO `dish` VALUES (8, '口水鸡', 22.20);
INSERT INTO `dish` VALUES (9, '红烧茄子', 16.00);
INSERT INTO `dish` VALUES (10, '82年拉菲', 1699.00);
COMMIT;

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp` (
  `eid` int NOT NULL AUTO_INCREMENT,
  `ename` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `egender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `etype` int NOT NULL DEFAULT '0' COMMENT '0表示服务员,1表示配送员',
  `edate` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`eid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of emp
-- ----------------------------
BEGIN;
INSERT INTO `emp` VALUES (1, '员工1号', '女', 0, '2020-11-10');
INSERT INTO `emp` VALUES (2, '员工2号', '女', 1, '2020-11-10');
INSERT INTO `emp` VALUES (3, '员工3号', '男', 0, '2020-11-10');
COMMIT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `oid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `uid` int NOT NULL,
  `starttime` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `endtime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `eid` int DEFAULT NULL,
  `money` decimal(10,2) NOT NULL,
  `state` int NOT NULL COMMENT '0已下单1已接单2已完结',
  `type` int NOT NULL COMMENT '0堂食订单/1外卖',
  `tid` int DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `dids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tele` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`oid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of orders
-- ----------------------------
BEGIN;
INSERT INTO `orders` VALUES ('03aec6fe-a1ef-4837-90a7-ee78e40bb2f7', 1, '2020-11-21 13:18:44', NULL, NULL, 1081.94, 0, 0, 2, '王君帅', NULL, '1-1,2-1,3-1', '18766657057');
INSERT INTO `orders` VALUES ('05d49d9a-a0ed-4dc2-ab1a-1585ad90a568', 1, '2020-11-20 10:33:01', NULL, NULL, 36.00, 0, 1, NULL, '王君帅', '青岛大学', '6-3', '18766657057');
INSERT INTO `orders` VALUES ('28e28518-face-4983-835a-82ead9707fd7', 1, '2020-11-20 10:25:10', NULL, NULL, 1026.24, 0, 1, NULL, '王君帅', '青岛大学', '1-1', '18766657057');
INSERT INTO `orders` VALUES ('bfe4cd3f-defb-4e2f-962d-5f4f0fb5058b', 1, '2020-11-20 22:20:42', NULL, NULL, 782.70, 0, 1, NULL, '王君帅', '青岛大学', '2-1,3-1,4-1,5-1', '18766657057');
COMMIT;

-- ----------------------------
-- Table structure for tables
-- ----------------------------
DROP TABLE IF EXISTS `tables`;
CREATE TABLE `tables` (
  `tid` int NOT NULL AUTO_INCREMENT,
  `tcap` int NOT NULL,
  `tstate` int NOT NULL DEFAULT '1' COMMENT '1可用/0不可用',
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tables
-- ----------------------------
BEGIN;
INSERT INTO `tables` VALUES (1, 3, 1);
INSERT INTO `tables` VALUES (2, 6, 0);
INSERT INTO `tables` VALUES (3, 5, 1);
INSERT INTO `tables` VALUES (4, 6, 1);
INSERT INTO `tables` VALUES (5, 1, 0);
INSERT INTO `tables` VALUES (6, 1, 1);
INSERT INTO `tables` VALUES (7, 6, 1);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `tele` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '18766657057', '123456', '王君帅', '青岛大学');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
