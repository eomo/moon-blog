create database blog character set utf8mb4 collate utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `t_category`(
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(32) NOT NULL,
    `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
    `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `image` varchar(512) NOT NULL DEFAULT '' COMMENT '描述图片',
    `format1` varchar(512) DEFAULT '' COMMENT '图片格式化',
    `format2` varchar(512) DEFAULT '' COMMENT '图片格式化',
    `url` varchar(128) DEFAULT '' COMMENT '跳转链接',
    `menu` TINYINT  NOT NULL DEFAULT 0 COMMENT '是否在菜单显示此分类，1显示0不显示',
    `order_no` INT  NOT NULL DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`),
    UNIQUE KEY unique_index_code(`code`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_topic`(
    `id` int unsigned NOT NULL AUTO_INCREMENT,
    `code` varchar(32) NOT NULL,
    `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
    `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `image` varchar(512) NOT NULL DEFAULT '' COMMENT '描述图片',
    `format` varchar(512) DEFAULT '' COMMENT '图片格式化',
    PRIMARY KEY (`id`),
    UNIQUE KEY unique_index_code(`code`)
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
    `author_intro` text COMMENT '作者介绍',
    `summary` text COMMENT '图书介绍',
    `small_image` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `large_image` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `medium_image` varchar(512) NOT NULL DEFAULT '' COMMENT '图书图片',
    `douban_url` varchar(512) NOT NULL DEFAULT '' COMMENT '豆瓣页面',
    `jingdong_url` varchar(512) NOT NULL DEFAULT '' COMMENT '京东购买链接',
    `duokan_url` varchar(512) NOT NULL DEFAULT '' COMMENT '多看电子书链接',
    `year` varchar(4) NOT NULL DEFAULT '2018' COMMENT '哪一年看的此书',
    `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '你自己的评论',
    `remark_url` varchar(512) COMMENT '评论链接',
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
    `small_image` varchar(512) NOT NULL DEFAULT '' COMMENT '图片',
    `large_image` varchar(512) NOT NULL DEFAULT '' COMMENT '图片',
    `medium_image` varchar(512) NOT NULL DEFAULT '' COMMENT '图片',
    `durations` varchar(8) NOT NULL DEFAULT '' COMMENT '片长',
    `genres` varchar(64) NOT NULL DEFAULT '' COMMENT '影片类型',
    `countries` varchar(64) NOT NULL DEFAULT '' COMMENT '制片国家/地区',
    `year` varchar(4) NOT NULL DEFAULT '2018' COMMENT '哪一年看的此书',
    `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '你自己的评论',
    PRIMARY KEY (`id`),
    UNIQUE KEY unique_index_douban(`douban_id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `t_article`(
    `id` varchar(10) NOT NULL DEFAULT '' COMMENT '主键',
    `title` varchar(512) NOT NULL DEFAULT '' COMMENT '标题',
    `image` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '文章描述图片',
    `mformat` VARCHAR(512) DEFAULT '' COMMENT '图片格式',
    `aformat` VARCHAR(512) DEFAULT '' COMMENT '图片格式',
    `content` longtext COMMENT '内容',
    `summary` VARCHAR(512) NOT NULL DEFAULT '' COMMENT '摘要',
    `author` varchar(32) NOT NULL DEFAULT '' COMMENT '作者',
    `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '创建时间',
    `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '更新时间',
    `publish_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '发布时间',
    `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：草稿0，发布1',
    `year` varchar(4) NOT NULL DEFAULT '' COMMENT '年份',
    `topic_id` varchar(32) NOT NULL DEFAULT '' COMMENT '专题ID',
    `category_id` varchar(32) NOT NULL DEFAULT '' COMMENT '分类ID',
    `view_count` INT NOT NULL DEFAULT 0 COMMENT '浏览量',
    `comment_count` INT NOT NULL DEFAULT 0 COMMENT '评论数量',
    `badge` VARCHAR(16) COMMIT '特殊标记，用于标识某些特殊的页面',
    `stick` INT NOT NULL DEFAULT 0 COMMENT '置顶标识',
    `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '你自己的评论'
    `remark_url` varchar(512) COMMENT '评论链接'
    PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_comment`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `article_id` varchar(20) NOT NULL DEFAULT '' COMMENT '关联文章ID',
  `author` varchar(50) NOT NULL DEFAULT '' COMMENT '作者',
  `author_email` varchar(100) NOT NULL DEFAULT '' COMMENT '作者email',
  `email_hash` varchar(32) NOT NULL DEFAULT '' COMMENT 'email hash',
  `author_url` varchar(200) COMMENT '作者url',
  `author_ip` varchar(100) COMMENT '作者ip',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `content` text COMMENT '评论内容',
  `root_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父评论ID',
  `parent_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '回复Id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comment_post_ID`(`article_id`) USING BTREE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;
