package cn.moondev.blog.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

    public int id;

    /**
     * 名称
     */
    public String name;

    /**
     * 描述
     */
    public String desc;

    /**
     * 描述图片地址
     */
    public String image;

    /**
     * 分类跳转链接
     */
    public String url;

    /**
     * 是否在首页菜单中显示，如果不显示将显示在所有分类页面中
     */
    public int menu;

    /**
     * 排序，主要是在首页菜单中的显示顺序
     */
    public int orderNo;

    public Category(){

    }

    public Category(int id, String name, String image, String url) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.url = url;
    }

    public static List<Category> def() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(-1,"足迹","http://apps.moondev.cn/image/road.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim",""));
        categories.add(new Category(-2,"图书","http://apps.moondev.cn/image/book3.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim",""));
        categories.add(new Category(-3,"电影","http://apps.moondev.cn/image/movie.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim",""));
        return categories;
    }

}
