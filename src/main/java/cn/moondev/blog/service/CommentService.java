package cn.moondev.blog.service;

import cn.moondev.blog.mapper.CommentMapper;
import cn.moondev.blog.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentMapper mapper;

    public void addComment(Comment comment) {
        mapper.addComment(comment);
    }

    public List<Comment> comments4Article(String articleId) {
        return mapper.comments4Article(articleId);
    }
}
