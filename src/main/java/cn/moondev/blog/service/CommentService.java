package cn.moondev.blog.service;

import cn.moondev.blog.mapper.CommentMapper;
import cn.moondev.blog.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.HtmlUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService extends BaseService{

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
}
