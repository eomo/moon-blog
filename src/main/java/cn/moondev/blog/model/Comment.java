package cn.moondev.blog.model;

import java.time.LocalDateTime;
import java.util.List;

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
    public String emailHash;
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
    public long rootId;
    public LocalDateTime createdTime;

    /**
     * 前端展示需要的数据
     */
    // 发布时间描述
    public String createdTimeDesc;
    // 这条评论@作者
    public String atAuthor;
    // 评论的评论列表
    public List<Comment> children;
}
