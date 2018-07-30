package cn.moondev.blog.model;

public class Movie {

    public int id;

    /**
     * 中文名
     */
    public String title;

    /**
     * 原名
     */
    public String originalTitle;

    /**
     * 又名
     */
    public String aka;

    /**
     * 条目页URL
     */
    public String doubanUrl;

    /**
     * 评分
     */
    public String rating;

    /**
     * 电影海报图
     */
    public String doubanImage1;
    public String doubanImage2;
    public String doubanImage3;
    public String backupImage1;
    public String backupImage2;
    public String backupImage3;

    /**
     * 条目分类
     */
    public String subtype;

    /**
     * 导演
     */
    public String directors;

    /**
     * 主演
     */
    public String casts;

    /**
     * 编剧
     */
    public String writers;

    /**
     * 官方网站
     */
    public String website;

    /**
     * 如果条目类型是电影则为上映日期，如果是电视剧则为首播日期
     */
    public String pubdates;

    /**
     * 大陆上映日期，如果条目类型是电影则为上映日期，如果是电视剧则为首播日期
     */
    public String mainlandPubdate;

    /**
     * 年份
     */
    public String year;

    /**
     * 语言
     */
    public String languages;

    /**
     * 片长
     */
    public String durations;

    /**
     * 影片类型，最多提供3个
     */
    public String genres;

    /**
     * 制片国家/地区
     */
    public String countries;

    /**
     * 简介
     */
    public String summary;
}
