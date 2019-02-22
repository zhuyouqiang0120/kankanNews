/*
 Navicat Premium Data Transfer

 Source Server         : localhost-swatapp
 Source Server Type    : MySQL
 Source Server Version : 50505
 Source Host           : localhost
 Source Database       : zens_kknews

 Target Server Type    : MySQL
 Target Server Version : 50505
 File Encoding         : utf-8

 Date: 12/25/2017 17:37:27 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tbl_column`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_column`;
CREATE TABLE `tbl_column` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FSiteGUID` varchar(64) DEFAULT NULL,
  `FColumnGUID` varchar(64) DEFAULT NULL,
  `FColumnName` varchar(50) DEFAULT NULL,
  `FRssUrl` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tbl_column`
-- ----------------------------
BEGIN;
INSERT INTO `tbl_column` VALUES ('1', '06c4dc42d89f44b98a8f445810ab4782', '8499c69fa2ec4abda69ffea118e064c8', '申事', 'http://api.kankanews.com/kkrss/ocn/kkapp/kkartical_511.xml'), ('2', '06c4dc42d89f44b98a8f445810ab4782', '94e488dec9574963bf742d16db9bdcc4', '松江', 'http://api.kankanews.com/kkrss/ocn/kkclass/kkvideo_8184.xml'), ('3', '06c4dc42d89f44b98a8f445810ab4782', '6f742452fccb4955bafb86327e7eaf66', '嘉定', 'http://api.kankanews.com/kkrss/ocn/kkclass/kkvideo_9317.xml'), ('4', '06c4dc42d89f44b98a8f445810ab4782', '0f86bdcab44c4ab3a0160b162f282bcc', '精选', 'http://api.kankanews.com/kkrss/ocn/kkapp/kkartical_488.xml');
COMMIT;

-- ----------------------------
--  Table structure for `tbl_topic`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_topic`;
CREATE TABLE `tbl_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicId` varchar(50) DEFAULT NULL,
  `tcolmunId` varchar(50) DEFAULT NULL,
  `tsiteId` varchar(50) DEFAULT NULL,
  `title` text DEFAULT NULL,
  `tclass` int(11) DEFAULT NULL,
  `tcontent` text DEFAULT NULL,
  `thumbnail` text DEFAULT NULL,
  `createtime` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_topicId` (`topicId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tbl_topic`
-- ----------------------------
BEGIN;
INSERT INTO `tbl_topic` VALUES ('1', '7920697', '8499c69fa2ec4abda69ffea118e064c8', '06c4dc42d89f44b98a8f445810ab4782', '突破沪籍限制提供人才公寓 上海招聘老师诚意大', '0', '[{\"name\":\"突破沪籍限制提供人才公寓 上海招聘老师诚意大\",\"videopath\":\"/Users/zyq/MyWork/kknews/7920697.png\"}]', '/Users/zyq/MyWork/kknews/3bef1d00e299aece74878de96820ad80.jpg', '2017-03-24 11:23:02'), ('2', '7920842', '8499c69fa2ec4abda69ffea118e064c8', '06c4dc42d89f44b98a8f445810ab4782', '是谁在不让我退货？', '0', '[{\"name\":\"是谁在不让我退货？\",\"videopath\":\"/Users/zyq/MyWork/kknews/7920842.png\"}]', '/Users/zyq/MyWork/kknews/038f94c5671a639f39b39466be7bd380.jpg', '2017-03-24 11:23:03'), ('3', '7920850', '8499c69fa2ec4abda69ffea118e064c8', '06c4dc42d89f44b98a8f445810ab4782', '对流天气影响上海前往广州航班', '0', '[{\"name\":\"对流天气影响上海前往广州航班\",\"videopath\":\"/Users/zyq/MyWork/kknews/7920850.png\"}]', '/Users/zyq/MyWork/kknews/99cc8264c7f0e6a07007960911e2c6c7.jpg', '2017-03-24 11:23:13'), ('4', '7920870', '8499c69fa2ec4abda69ffea118e064c8', '06c4dc42d89f44b98a8f445810ab4782', '音乐美术进中考 上海今年暂不施行', '0', '[{\"name\":\"音乐美术进中考 上海今年暂不施行\",\"videopath\":\"/Users/zyq/MyWork/kknews/7920870.png\"}]', '/Users/zyq/MyWork/kknews/1b5f63d520367a7b1f4c7f531f4bf7f5.jpg', '2017-03-24 11:23:14'), ('5', '7920888', '8499c69fa2ec4abda69ffea118e064c8', '06c4dc42d89f44b98a8f445810ab4782', '虹口区北外滩金融港建设启动', '0', '[{\"name\":\"虹口区北外滩金融港建设启动\",\"videopath\":\"/Users/zyq/MyWork/kknews/7920888.png\"}]', '/Users/zyq/MyWork/kknews/31b1ecd09245709c28f49c393be96c24.jpg', '2017-03-24 11:23:14'), ('6', '7920916', '8499c69fa2ec4abda69ffea118e064c8', '06c4dc42d89f44b98a8f445810ab4782', 'LED灯\"缺斤少两\" 质监部门查获问题产品600余件', '0', '[{\"name\":\"LED灯\\\"缺斤少两\\\" 质监部门查获问题产品600余件\",\"videopath\":\"/Users/zyq/MyWork/kknews/7920916.png\"}]', '/Users/zyq/MyWork/kknews/661546369ec17f49a49176ea4f2eb168.jpg', '2017-03-24 11:23:15');
COMMIT;

-- ----------------------------
--  Table structure for `tbl_utils`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_utils`;
CREATE TABLE `tbl_utils` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `video_path` varchar(200) DEFAULT NULL,
  `save_path` varchar(200) DEFAULT NULL,
  `inject_url` text DEFAULT NULL,
  `underFrame_url` text DEFAULT NULL,
  `host_ip` varchar(20) DEFAULT NULL,
  `site_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `tbl_utils`
-- ----------------------------
BEGIN;
INSERT INTO `tbl_utils` VALUES ('1', '/Users/zyq/MyWork/kknews/', '/Users/zyq/MyWork/kknews/', 'http://10.211.55.4:8080/UCGS/api/topic/inject', 'http://10.211.55.4:8080/UCGS/api/topic/underFrame', '10.211.55.4', '06c4dc42d89f44b98a8f445810ab4782');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
