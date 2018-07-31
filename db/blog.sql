CREATE TABLE IF NOT EXISTS `t_category`(
    `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
    `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `image` varchar(512) NOT NULL DEFAULT '' COMMENT '描述图片',
    `url` varchar(128) NOT NULL DEFAULT '' COMMENT '跳转链接',
    `menu` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否在菜单显示此分类，1显示0不显示',
    `order_no` INT  NOT NULL DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_topic`(
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
    `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `image` varchar(512) NOT NULL DEFAULT '' COMMENT '描述图片',
    PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_book`(
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(256) NOT NULL DEFAULT '' COMMENT '书名',
    `subtitle` varchar(256) NOT NULL DEFAULT '' COMMENT '副标题',
    `author` varchar(64) NOT NULL DEFAULT '' COMMENT '作者',
    `rating` varchar(4) NOT NULL DEFAULT '' COMMENT '豆瓣评分',
    `tag` varchar(64) NOT NULL DEFAULT '' COMMENT '标签',
    `translator` varchar(64) NOT NULL DEFAULT '' COMMENT '翻译',
    `pages` varchar(4) NOT NULL DEFAULT '' COMMENT '页数',
    `douban_id` varchar(16) NOT NULL DEFAULT '' COMMENT '豆瓣编号',
    `publisher` varchar(64) NOT NULL DEFAULT '' COMMENT '出版社',
    `pubdate` varchar(16) NOT NULL DEFAULT '' COMMENT '出版日期',
    `isbn10` varchar(10) NOT NULL DEFAULT '' COMMENT 'isbn10',
    `isbn13` varchar(13) NOT NULL DEFAULT '' COMMENT 'isbn13',
    `douban_api` varchar(512) NOT NULL DEFAULT '' COMMENT '豆瓣API地址',
    `author_intro` varchar(512) NOT NULL DEFAULT '' COMMENT '作者介绍',
    `summary` varchar(1024) NOT NULL DEFAULT '' COMMENT '图书介绍',
    `douban_image1` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `douban_image2` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `douban_image3` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `backup_image1` varchar(512) NOT NULL DEFAULT '' COMMENT '备份图片',
    `backup_image2` varchar(512) NOT NULL DEFAULT '' COMMENT '备份图片',
    `backup_image3` varchar(512) NOT NULL DEFAULT '' COMMENT '备份图片',
    `douban_url` varchar(512) NOT NULL DEFAULT '' COMMENT '豆瓣页面',
    `jingdong_url` varchar(512) NOT NULL DEFAULT '' COMMENT '京东购买链接',
    `duokan_url` varchar(512) NOT NULL DEFAULT '' COMMENT '多看电子书链接',
    `year` varchar(4) NOT NULL DEFAULT '2018' COMMENT '哪一年看的此书',
    `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '你自己的评论',
    PRIMARY KEY (`id`),
    UNIQUE KEY unique_index_douban(`douban_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_movie`(
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `title` varchar(256) NOT NULL DEFAULT '' COMMENT '名称',
    `original_title` varchar(256) NOT NULL DEFAULT '' COMMENT '原名',
    `aka` varchar(256) NOT NULL DEFAULT '' COMMENT '又名',
    `rating` varchar(4) NOT NULL DEFAULT '' COMMENT '豆瓣评分',
    `subtype` varchar(64) NOT NULL DEFAULT '' COMMENT '条目分类',
    `directors` varchar(256) NOT NULL DEFAULT '' COMMENT '导演',
    `casts` varchar(512) NOT NULL DEFAULT '' COMMENT '主演',
    `douban_id` varchar(16) NOT NULL DEFAULT '' COMMENT '豆瓣编号',
    `writers` varchar(512) NOT NULL DEFAULT '' COMMENT '编剧',
    `pubdates` varchar(16) NOT NULL DEFAULT '' COMMENT '如果条目类型是电影则为上映日期，如果是电视剧则为首播日期',
    `website` varchar(256) NOT NULL DEFAULT '' COMMENT '官方网站',
    `mainland_pubdate` varchar(16) NOT NULL DEFAULT '' COMMENT '大陆上映日期，如果条目类型是电影则为上映日期，如果是电视剧则为首播日期',
    `douban_url` varchar(512) NOT NULL DEFAULT '' COMMENT '豆瓣URL地址',
    `languages` varchar(32) NOT NULL DEFAULT '' COMMENT '语言',
    `summary` varchar(1024) NOT NULL DEFAULT '' COMMENT '电影介绍',
    `douban_image1` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `douban_image2` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `douban_image3` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `backup_image1` varchar(512) NOT NULL DEFAULT '' COMMENT '备份图片',
    `backup_image2` varchar(512) NOT NULL DEFAULT '' COMMENT '备份图片',
    `backup_image3` varchar(512) NOT NULL DEFAULT '' COMMENT '备份图片',
    `durations` varchar(8) NOT NULL DEFAULT '' COMMENT '片长',
    `genres` varchar(64) NOT NULL DEFAULT '' COMMENT '影片类型',
    `countries` varchar(64) NOT NULL DEFAULT '' COMMENT '制片国家/地区',
    `year` varchar(4) NOT NULL DEFAULT '2018' COMMENT '哪一年看的此书',
    `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '你自己的评论',
    PRIMARY KEY (`id`),
    UNIQUE KEY unique_index_douban(`douban_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;
