package cn.moondev.blog.model;

public class Category {

    public String id;

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
    public String format1;
    public String format2;

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

    public long viewCount;
    public long commentCount;

    public Category() {

    }

    public static Category travel() {
        Category category = new Category();
        category.id = "travel";
        category.name = "足迹";
        category.image = "http://apps.moondev.cn/image/road.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim";
        category.desc = "世界那么大，我想去走走。";
        return category;
    }

    public static Category book() {
        Category category = new Category();
        category.id = "book";
        category.name = "图书";
        category.image = "http://apps.moondev.cn/image/book3.jpg?imageMogr2/thumbnail/!194x194r/format/webp/blur/1x0/quality/75|imageslim";
        category.desc = "夫读书将以何为哉？辨其大义，以修己治人之体也，察其微言，以善精义入神之用也。";
        return category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public int getMenu() {
        return menu;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public long getViewCount() {
        return viewCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public String getFormat1() {
        return format1;
    }

    public String getFormat2() {
        return format2;
    }
}
