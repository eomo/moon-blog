package cn.moondev.blog.model;

public class Topic {

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
    public String format;

    public long viewCount;
    public long commentCount;

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

    public long getViewCount() {
        return viewCount;
    }

    public String getFormat() {
        return format;
    }

    public long getCommentCount() {
        return commentCount;
    }
}
