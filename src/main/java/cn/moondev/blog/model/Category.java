package cn.moondev.blog.model;

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

}
