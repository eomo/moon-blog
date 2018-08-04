package cn.moondev.blog.model;

public class Topic {

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

    public long viewCount;
    public long commentCount;

    public int getId() {
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

    public long getViewCount() {
        return viewCount;
    }

    public long getCommentCount() {
        return commentCount;
    }
}
