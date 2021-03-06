package cn.moondev.blog.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public String smallImage;
    public String largeImage;
    public String mediumImage;

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
    /**
     * 书评链接
     */
    public String remarkUrl;

    public Book() {

    }

    public Book(JSONObject json) {
        this.rating = json.getJSONObject("rating").getString("average");
        this.title = json.getString("title");
        this.subtitle = json.getString("subtitle");
        this.author = parseJsonStringArray(json.getJSONArray("author"));
        this.tag = parseTag(json.getJSONArray("tags"));
        this.translator = parseJsonStringArray(json.getJSONArray("translator"));
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
        this.smallImage = images.getString("small");
        this.largeImage = images.getString("large");
        this.mediumImage = images.getString("medium");
        this.jingdongUrl = "";
        this.duokanUrl = "";
        this.doubanUrl = json.getString("alt");
    }

    /**
     * 解析图片tag
     */
    private String parseTag(JSONArray jsonArray) {
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

    private String parseJsonStringArray(JSONArray stringArray) {
        if (CollectionUtils.isEmpty(stringArray)) {
            return "";
        }
        List<String> list = stringArray.stream().map(item -> item.toString()).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            int index = str.indexOf('（');
            if (index > 0) {
                sb.append(str.substring(0,index)).append(",");
            } else {
                sb.append(str).append(",");
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
