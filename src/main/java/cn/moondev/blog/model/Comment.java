package cn.moondev.blog.model;

/**
 * 评论
 */
public class Comment {

    public long id;

    /**
     * 关联的文章ID
     */
    public String articleId;

    /**
     * 作者
     */
    public String author;
    public String authorEmail;
    public String authorUrl;
    public String authorIp;

    /**
     * 内容
     */
    public String content;

    /**
     * 评论的评论ID
     */
    public long parentId;
}
