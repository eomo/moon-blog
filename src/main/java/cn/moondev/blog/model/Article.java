package cn.moondev.blog.model;

import cn.moondev.framework.provider.random.RandomStringUtils;

public class Article {

    public String id;

    /**
     * 文章标题
     */
    public String title;

    /**
     * 文章图片
     */
    public String image;

    /**
     * 文章内容
     */
    public String content;

    /**
     * 文章摘要
     */
    public String summary;

    /**
     * 文章作者，暂未使用
     */
    public String author;

    /**
     * 创建时间
     */
    public String createdTime;

    /**
     * 更新时间
     */
    public String updatedTime;

    /**
     * 发布时间
     */
    public String publishTime = "";

    /**
     * 文章类型：0草稿，1已发布
     */
    public int status;

    /**
     * 所属专题
     */
    public int topicId;
    public String topicName;

    /**
     * 所属分类
     */
    public int categoryId;
    public String categoryName;

    /**
     * 浏览量
     */
    public long viewCount;

    /**
     * 评论数
     */
    public long commentCount;

    /**
     * 置顶标志：1表示置顶，0表示未置顶
     */
    public int stick;

    public Article() {
        this.title = "";
        this.content = "";
    }

    public static String genId() {
        return "id_" + RandomStringUtils.randomAlphanumeric(7);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getSummary() {
        return summary;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public int getStatus() {
        return status;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getViewCount() {
        return viewCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public int getStick() {
        return stick;
    }
}
