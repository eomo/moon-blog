package cn.moondev.blog.service;

import cn.moondev.blog.mapper.CommentMapper;
import cn.moondev.blog.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentMapper mapper;

    public void addComment(Comment comment) {
        comment.emailHash = DigestUtils.md5DigestAsHex(comment.authorEmail.getBytes());
        mapper.addComment(comment);
    }

    public List<Comment> comments4Article(String articleId) {
        List<Comment> comments = mapper.comments4Article(articleId);
        if (CollectionUtils.isEmpty(comments)) {
            return comments;
        }
        Map<Long, Comment> commentMap = comments.stream().collect(Collectors.toMap(c -> c.id, c -> c));
        // 第一层级的评论，即直接评论文章的，非回复某个评论
        List<Comment> parents = comments.stream().filter(c -> c.parentId == 0).collect(Collectors.toList());
        // 找子节点
        for (Comment comment : parents) {
            comment.children = getReplyList(comment, commentMap);
            comment.createdTimeDesc = replyTimeDesc(comment.createdTime);
        }
        return parents;
    }


    /**
     * 找到该评论的所有回复
     */
    private List<Comment> getReplyList(Comment comment, Map<Long, Comment> commentMap) {
        List<Comment> children = commentMap.values().stream()
                .filter(c -> c.rootId == comment.id)
                .collect(Collectors.toList());
        children.stream().forEach(c -> {
            c.createdTimeDesc = replyTimeDesc(c.createdTime);
            c.atAuthor = commentMap.get(c.parentId).author;
        });
        return children;
    }

    /**
     * 回复时间距当前时间的描述，比如：5分钟前，23天前，1个月前，1年前
     */
    private String replyTimeDesc(LocalDateTime createdTime) {
        LocalDateTime now = LocalDateTime.now();
        long period = createdTime.until(now, ChronoUnit.YEARS);
        if (period > 0) {
            return String.format(" %s 年前", period);
        }
        period = createdTime.until(now, ChronoUnit.MONTHS);
        if (period > 0) {
            return String.format(" %s 月前", period);
        }
        period = createdTime.until(now, ChronoUnit.WEEKS);
        if (period > 0) {
            return String.format(" %s 周前", period);
        }
        period = createdTime.until(now, ChronoUnit.DAYS);
        if (period > 0) {
            return String.format(" %s 天前", period);
        }
        period = createdTime.until(now, ChronoUnit.HOURS);
        if (period > 0) {
            return String.format(" %s 小时前", period);
        }
        period = createdTime.until(now, ChronoUnit.MINUTES);
        if (period > 0) {
            return String.format(" %s 分钟前", period);
        }
        return String.format(" %s 秒前", period);
    }
}
