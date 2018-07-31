package cn.moondev.blog.model;

import cn.moondev.framework.utils.JsonUtils;
import cn.moondev.framework.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    public String doubanId;

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

    /**
     * 点评
     */
    public String remark;

    public Movie() {

    }

    public Movie(JSONObject json) {
        this.rating = json.getJSONObject("rating").getString("average");
        this.title = json.getString("title");
        this.originalTitle = json.getString("original_title");
        this.aka = JsonUtils.parseJsonStringArray(json.getJSONArray("aka"));
        this.subtype = StringUtils.null2Empty(json.getString("subtype"));
        this.directors = parsePerson(json.getJSONArray("directors"));
        this.casts = parsePerson(json.getJSONArray("casts"));
        this.doubanId = json.getString("id");
        this.writers = parsePerson(json.getJSONArray("writers"));
        this.website = StringUtils.null2Empty(json.getString("website"));
        this.pubdates = StringUtils.null2Empty(json.getString("pubdates"));
        this.mainlandPubdate = StringUtils.null2Empty(json.getString("mainland_pubdate"));
        this.languages = StringUtils.null2Empty(json.getString("languages"));
        this.durations = StringUtils.null2Empty(json.getString("durations"));
        this.summary = json.getString("summary");
        JSONObject images = json.getJSONObject("images");
        this.doubanImage1 = images.getString("small");
        this.doubanImage2 = images.getString("large");
        this.doubanImage3 = images.getString("medium");
        this.backupImage1 = "";
        this.backupImage2 = "";
        this.backupImage3 = "";
        this.doubanUrl = json.getString("alt");
        this.year = StringUtils.null2Empty(json.getString("year"));
        this.genres = JsonUtils.parseJsonStringArray(json.getJSONArray("genres"));
        this.countries = JsonUtils.parseJsonStringArray(json.getJSONArray("countries"));
    }

    private String parsePerson(JSONArray array) {
        if (CollectionUtils.isEmpty(array)) {
            return "";
        }
        List<String> list = Lists.newArrayList();
        for (Object object : array) {
            JSONObject json = (JSONObject) object;
            list.add(json.getString("name"));
        }
        return Joiner.on(",").join(list);
    }
}
