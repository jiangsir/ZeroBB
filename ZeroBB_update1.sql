CREATE TABLE IF NOT EXISTS `appconfigs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `header` varchar(255) NOT NULL,
  `pagesize` int(11) NOT NULL,
  `defaultlogin` varchar(100) NOT NULL,
  `authdomains` varchar(255) NOT NULL,
  `client_id` varchar(255) NOT NULL,
  `client_secret` varchar(255) NOT NULL,
  `redirect_uri` varchar(255) NOT NULL,
  `signinip` varchar(255) NOT NULL,
  `announcement` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

UPDATE articles SET info='STANDARD' WHERE info ='一般';
UPDATE articles SET info='IMPORTANT' WHERE info ='重要';
UPDATE articles SET info='HEADLINE' WHERE info ='頭條';
