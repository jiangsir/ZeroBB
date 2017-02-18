-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2
-- http://www.phpmyadmin.net
--
-- 主機: localhost
-- 產生日期: 2016 年 02 月 03 日 23:06
-- 伺服器版本: 5.5.31
-- PHP 版本: 5.4.4-14+deb7u4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- 資料庫: `zerobb`
--

-- --------------------------------------------------------

--
-- 表的結構 `articles`
--

CREATE TABLE IF NOT EXISTS `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(50) NOT NULL,
  `title` varchar(254) NOT NULL DEFAULT '',
  `info` varchar(20) NOT NULL DEFAULT '一般',
  `type` varchar(12) NOT NULL DEFAULT 'default',
  `hyperlink` varchar(254) DEFAULT 'http://',
  `content` text NOT NULL,
  `hitnum` int(6) DEFAULT '0',
  `postdate` datetime DEFAULT '2000-01-01 00:00:00',
  `outdate` datetime NOT NULL DEFAULT '9999-12-31 23:59:59',
  `sortable` bigint(20) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `article_tags`
--

CREATE TABLE IF NOT EXISTS `article_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleid` int(11) NOT NULL,
  `tagname` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `articleid` (`articleid`,`tagname`),
  UNIQUE KEY `articleid_2` (`articleid`,`tagname`),
  KEY `tagname` (`tagname`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `exceptions`
--

CREATE TABLE IF NOT EXISTS `exceptions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uri` varchar(100) NOT NULL DEFAULT '',
  `account` varchar(20) NOT NULL DEFAULT '',
  `ipaddr` varchar(20) NOT NULL DEFAULT '',
  `exceptiontype` varchar(255) NOT NULL,
  `exception` text NOT NULL,
  `exceptiontime` datetime NOT NULL DEFAULT '2006-06-08 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `logs`
--

CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uri` varchar(100) NOT NULL DEFAULT '',
  `account` varchar(20) NOT NULL DEFAULT '',
  `ipaddr` varchar(20) NOT NULL DEFAULT '',
  `exceptiontype` varchar(255) NOT NULL,
  `exception` text NOT NULL,
  `exceptiontime` datetime NOT NULL DEFAULT '2006-06-08 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `tags`
--

CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagname` varchar(100) NOT NULL,
  `tagtitle` varchar(100) NOT NULL,
  `descript` varchar(255) NOT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tagname` (`tagname`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `upfiles`
--

CREATE TABLE IF NOT EXISTS `upfiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleid` int(11) NOT NULL DEFAULT '0',
  `filepath` varchar(100) NOT NULL,
  `filename` varchar(100) NOT NULL,
  `filetmpname` varchar(100) NOT NULL,
  `filesize` bigint(20) DEFAULT '0',
  `filetype` varchar(255) DEFAULT NULL,
  `binary` longblob NOT NULL,
  `hitnum` int(11) NOT NULL DEFAULT '0',
  `visible` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `NAME` (`articleid`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的結構 `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) NOT NULL DEFAULT '',
  `name` varchar(30) NOT NULL DEFAULT '',
  `division` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL,
  `passwd` varchar(12) NOT NULL,
  `email` varchar(50) NOT NULL,
  `homepage` varchar(128) NOT NULL,
  `description` varchar(128) NOT NULL,
  `headline` tinyint(1) NOT NULL,
  `visible` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

INSERT INTO `users` VALUES(1, 'admin', '管理者', 'admin', 'ADMIN', '!1ashsashs', 'Jiangzero@gmail.com', 'homepage', 'describe', 1, 1);
INSERT INTO `users` VALUES(2, 'all', '全部', 'all', 'USER', '', '', '', '', 0, 0);
INSERT INTO `users` VALUES(3, 'principal', '校長室', 'principal', 'USER', 'prin', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(4, 'jiaowu', '教務處', 'jiaowu', 'USER', 'off1', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(5, 'xuewu', '學務處', 'xuewu', 'USER', 'off2', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(6, 'zongwu', '總務處', 'zongwu', 'USER', 'off3', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(7, 'fudao', '輔導室', 'fudao', 'USER', 'off4', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(8, 'lib', '圖資中心', 'lib', 'USER', 'lib', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(9, 'kuaiji', '主計室', 'kuaiji', 'USER', '7717735', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(10, 'renshi', '人事室', 'renshi', 'USER', '309340', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(21, 'weisheng', '學務處衛生保健組', 'weisheng', 'USER', '528', '', '', '', 0, 1);
INSERT INTO `users` VALUES(12, 'zixun', '資訊組', 'zixun', 'USER', 'j04xu3', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(13, 'schoolnews', '校園新聞', 'schoolnews', 'USER', 'cjh', '', 'homepage', 'describe', 0, 1);
INSERT INTO `users` VALUES(14, 'teachers', '段考答案區', 'teachers', 'USER', '1234', '', '', '', 0, 1);
INSERT INTO `users` VALUES(15, 'documentation', '公文上傳區', 'documentation', 'USER', 'doc', '', '', '', 0, 1);
INSERT INTO `users` VALUES(16, 'zhucezu', '教務處註冊組', 'zhucezu', 'USER', 'tosca', '', '', '', 0, 1);
INSERT INTO `users` VALUES(17, 'jiaoxue', '教務處教學組', 'jiaoxue', 'USER', 'jiaoxue', '', '', '', 0, 1);
INSERT INTO `users` VALUES(18, 'honoredlist', '榮譽榜', 'honoredlist', 'USER', 'off2', '', '', '', 0, 1);
INSERT INTO `users` VALUES(20, 'jiankang', '健康中心', 'jiankang', 'USER', '525525', '', '', '健康中心', 0, 1);
INSERT INTO `users` VALUES(22, 'xuewuhonor', '學務處榮譽榜', 'xuewuhonor', 'USER', 'off2', '', '', '', 0, 1);
INSERT INTO `users` VALUES(24, 'shebei', '教務處設備組', 'shebei', 'USER', 'tosca', '', '', '', 0, 1);
INSERT INTO `users` VALUES(27, 'jiaowuzhuren', '教務主任', 'jiaowu', 'USER', '5100', '', '', '', 1, 1);
INSERT INTO `users` VALUES(28, 'xuewuwuzhuren', '學務主任', 'xuewu', 'USER', '5200', '', '', '', 1, 1);
INSERT INTO `users` VALUES(29, 'zongwuzhuren', '總務主任', 'zongwu', 'USER', '5300', '', '', '', 1, 1);
INSERT INTO `users` VALUES(30, 'fudaozhuren', '輔導主任', 'fudao', 'USER', '5600', '', '', '', 1, 1);
INSERT INTO `users` VALUES(31, 'renshizhuren', '人事主任', 'renshi', 'USER', '5700', '', '', '', 1, 1);
INSERT INTO `users` VALUES(32, 'kuaijizhuren', '主計主任', 'kuaiji', 'USER', '5800', '', '', '', 1, 1);
INSERT INTO `users` VALUES(33, 'libzhuren', '圖書主任', 'lib', 'USER', '5500', '', '', '', 1, 1);
INSERT INTO `users` VALUES(34, '501', '秘書', 'principal', 'USER', '5010', '', '', '', 1, 1);
