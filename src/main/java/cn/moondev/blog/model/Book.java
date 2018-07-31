package cn.moondev.blog.model;

import cn.moondev.framework.utils.JsonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

public class Book {

    public int id;

    /**
     * 豆瓣评分
     */
    public String rating;

    /**
     * 书名
     */
    public String title;

    /**
     * 副标题
     */
    public String subtitle;

    /**
     * 作者
     */
    public String author;

    /**
     * 标签
     */
    public String tag;

    /**
     * 翻译
     */
    public String translator;

    /**
     * 页数
     */
    public String pages;

    /**
     * 豆瓣编号
     */
    public String doubanId;

    /**
     * 出版社
     */
    public String publisher;

    /**
     * 出版日期
     */
    public String pubdate;

    /**
     * 图书isbn
     */
    public String isbn10;
    public String isbn13;

    /**
     * 豆瓣API地址
     */
    public String doubanApi;

    /**
     * 作者介绍
     */
    public String authorIntro;

    /**
     * 图书介绍
     */
    public String summary;

    /**
     * 图书图片
     */
    public String doubanImage1;
    public String doubanImage2;
    public String doubanImage3;
    public String backupImage1;
    public String backupImage2;
    public String backupImage3;

    /**
     * 豆瓣页面
     */
    public String doubanUrl;

    /**
     * 京东购买链接
     */
    public String jingdongUrl;

    /**
     * 多看电子书链接
     */
    public String duokanUrl;

    /**
     * 哪一年看的
     */
    public String year;

    public String remark;

    public Book() {

    }

    public Book(JSONObject json) {
        this.rating = json.getJSONObject("rating").getString("average");
        this.title = json.getString("title");
        this.subtitle = json.getString("subtitle");
        this.author = JsonUtils.parseJsonStringArray(json.getJSONArray("author"));
        this.tag = parseTag(json.getJSONArray("tags"));
        this.translator = JsonUtils.parseJsonStringArray(json.getJSONArray("translator"));
        this.pages = json.getString("pages");
        this.doubanId = json.getString("id");
        this.publisher = json.getString("publisher");
        this.pubdate = json.getString("pubdate");
        this.isbn10 = json.getString("isbn10");
        this.isbn13 = json.getString("isbn13");
        this.doubanApi = json.getString("url");
        this.authorIntro = json.getString("author_intro");
        this.summary = json.getString("summary");
        JSONObject images = json.getJSONObject("images");
        this.doubanImage1 = images.getString("small");
        this.doubanImage2 = images.getString("large");
        this.doubanImage3 = images.getString("medium");
        this.backupImage1 = "";
        this.backupImage2 = "";
        this.backupImage3 = "";
        this.jingdongUrl = "";
        this.duokanUrl = "";
        this.doubanUrl = json.getString("alt");
    }

    /**
     * 解析图片tag
     */
    public String parseTag(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        int index = 1;
        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            String tagName = json.getString("name");
            if (title.contains(tagName) || author.contains(tagName)) {
                continue;
            }
            if (tagName.contains("+") || tagName.contains("*") || tagName.contains("=")) {
                break;
            }
            list.add(tagName);
            index++;
            if (index == 4) {
                break;
            }
        }
        return Joiner.on('/').join(list);
    }

}
