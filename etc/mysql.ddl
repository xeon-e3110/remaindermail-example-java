CREATE DATABASE IF NOT EXISTS `remaindermail`;

USE `remaindermail`;

CREATE TABLE IF NOT EXISTS `remainder_mail_address_t` (
	`id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '管理番号',
	`mailAddress` VARCHAR(256) NOT NULL COMMENT 'メールアドレス',
	`messageID` INT(10) NOT NULL DEFAULT '0' COMMENT '送信済みメッセージID',
	`updateDate` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新日',
	`createDate` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '作成日',
	`deleteFlg` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '論理削除フラグ',
	PRIMARY KEY (`id`),
	KEY(`mailAddress`(256), `deleteFlg`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT='リマインダーメール送信先';

CREATE TABLE IF NOT EXISTS `remainder_mail_message_t` (
	`id` INT(10) UNSIGNED AUTO_INCREMENT NOT NULL COMMENT '管理番号',
	`title` TEXT NOT NULL COMMENT 'メールタイトル',
	`message` TEXT NOT NULL COMMENT 'メールメッセージ',
	`send` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '送信済みフラグ(0:未送信 1:送信済み',
	`createDate` DATETIME NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '作成日',
	`deleteFlg` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '論理削除フラグ',
	PRIMARY KEY (`id`),
	KEY(`send`, `deleteFlg`)
)Engine=InnoDB DEFAULT CHARSET=utf8 COMMENT='送信メール';