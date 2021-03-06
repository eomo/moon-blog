package cn.moondev.blog.controller.api;

import cn.moondev.blog.model.Comment;
import cn.moondev.blog.service.ArticleService;
import cn.moondev.blog.service.CommentService;
import cn.moondev.framework.annotation.Permit;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Permit
@RestController
@RequestMapping("/v1/comment")
public class CommentController {

    @Autowired
    private CommentService service;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseDTO<Void> addComment(@RequestBody Comment comment) {
        service.addComment(comment);
        articleService.commentCountxx(comment.articleId);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
    public ResponseDTO<List<Comment>> comments(@PathVariable String articleId) {
        return ResponseDTO.success(service.comments4Article(articleId));
    }
}
