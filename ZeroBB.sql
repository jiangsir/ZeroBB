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
