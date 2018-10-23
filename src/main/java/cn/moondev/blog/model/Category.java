package cn.moondev.blog.model;

public class Category {

    public Long id;

    public String code;

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
        category.id = -1L;
        category.code = "travel";
        category.name = "足迹";
        category.image = "//static.hicsc.com/image/tc/travel.jpg?imageMogr2/thumbnail/!80x80r/format/webp/blur/1x0/quality/75|imageslim";
        category.desc = "世界那么大，我想去走走。";
        category.url = "/travel";
        return category;
    }

    public static Category book() {
        Category category = new Category();
        category.id = -2L;
        category.code = "book";
        category.name = "图书";
        category.image = "//static.hicsc.com/image/tc/book3.jpg?imageMogr2/thumbnail/!80x80r/format/webp/blur/1x0/quality/75|imageslim";
        category.desc = "夫读书将以何为哉？辨其大义，以修己治人之体也，察其微言，以善精义入神之用也。";
        category.url = "/book";
        return category;
    }

    public static Category why() {
        Category category = new Category();
        category.id = -3L;
        category.code = "why";
        category.name = "十万个为什么";
        category.image = "//static.hicsc.com/image/tc/why-7.jpg?imageMogr2/thumbnail/!80x80r/format/webp/blur/1x0/quality/75|imageslim";
        category.desc = "世界真奇妙";
        category.url = "/why";
        return category;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
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
