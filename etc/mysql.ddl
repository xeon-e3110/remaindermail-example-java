CREATE DATABASE IF NOT EXISTS `remaindermail`;

USE `remaindermail`;

CREATE TABLE IF NOT EXISTS `remainder_mail_address_t` (
	`id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '管理番号',
	`mailAddress` TEXT NOT NULL COMMENT 'メールアドレス',
	`updateDate` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
	`createDate` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
	`deleteFlg` TINYINT(3) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	KEY(`mailAddress`(255), `deleteFlg`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT='リマインダーメール送信先';

CREATE TABLE IF NOT EXISTS `remainder_mail_message_t` (
	`id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '管理番号',
	`title` TEXT NOT NULL COMMENT 'メールタイトル',
	`message` TEXT NOT NULL COMMENT 'メールメッセージ',
	`send` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '送信済みフラグ(0:未送信 1:送信済み',
	`createDate` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00',
	`deleteFlg` TINYINT(3) NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`),
	KEY(`send`, `deleteFlg`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT='送信メール';