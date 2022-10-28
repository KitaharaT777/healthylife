create table users (
	`id` bigint not null primary key auto_increment comment 'ID',
	`user_id` bigint not null comment 'ユーザーID',
	`mail_address` varchar(100) comment 'メールアドレス',
	`password` varchar(100) comment 'パスワード',
	`birthday` date comment '生年月日',
	`sex` tinyint not null default 0 comment '性別',
	`country` varchar(3) not null default 'JP' comment '国籍',
	`is_ai_search` tinyint not null default 0 comment 'AIフラグ',
	`is_withdrawaled` tinyint not null default 0 comment '退会フラグ',	
	`is_deleted` tinyint not null default 0 comment '削除フラグ',
	`create_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
	`update_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ユーザー情報';

ALTER TABLE `users`
	ADD UNIQUE KEY `idx_users_mail_address` (`mail_address`);


create table user_search (
	`id` bigint primary key auto_increment comment 'ID',
	`user_id` bigint not null comment 'ユーザーID',
	`mark` tinyint not null default 0 comment 'お気に入りマーク',
	`search_date` date comment '検索日',
	`word` varchar(100) comment '検索ワード',
	`is_deleted` tinyint not null default 0 comment '削除フラグ',
	`create_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
	`update_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
	`result1_probability` tinyint comment '結果1確率',
	`result1_name_id` int comment '結果1病名ID',
	`result2_probability` tinyint comment '結果2確率',
	`result2_name_id` int comment '結果2病名ID',
	`result3_probability` tinyint comment '結果3確率',
	`result3_name_id` int comment '結果3病名ID'
)  ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '検索履歴';


create table name_of_disease (
	`id` int not null primary key auto_increment comment 'ID',
	`name_id` int comment '病名ID',
	`name_of_disease` varchar(255) comment '病名',
	`information` text comment '症状',
	`link` varchar(1000) comment '参考リンク',
	`is_deleted` tinyint not null default 0 comment '削除フラグ',
	`create_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
	`update_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='病名情報';


create table result (
	`id` int primary key auto_increment comment 'ID',
	`result_id` int comment '結果ID',
	`comment` text comment '総評',
	`is_deleted` tinyint not null default 0 comment '削除フラグ',
	`create_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録日時',
	`update_date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
	`result1_probability` tinyint comment '結果1確率',
	`result1_name_id` int comment '結果1病名ID',
	`result2_probability` tinyint comment '結果2確率',
	`result2_name_id` int comment '結果2病名ID',
	`result3_probability` tinyint comment '結果3確率',
	`result3_name_id` int comment '結果3病名ID',
	`result4_probability` tinyint comment '結果4確率',
	`result4_name_id` int comment '結果4病名ID',
	`result5_probability` tinyint comment '結果5確率',
	`result5_name_id` int comment '結果5病名ID',
	`result6_probability` tinyint comment '結果6確率',
	`result6_name_id` int comment '結果6病名ID',
	`result7_probability` tinyint comment '結果7確率',
	`result7_name_id` int comment '結果7病名ID',
	`result8_probability` tinyint comment '結果8確率',
	`result8_name_id` int comment '結果8病名ID',
	`result9_probability` tinyint comment '結果9確率',
	`result9_name_id` int comment '結果9病名ID',
	`result10_probability` tinyint comment '結果10確率',
	`result10_name_id` int comment '結果10病名ID'
)  ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '検索結果';