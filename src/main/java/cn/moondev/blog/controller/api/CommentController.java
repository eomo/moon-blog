package cn.moondev.blog.controller.api;

import cn.moondev.blog.model.Comment;
import cn.moondev.blog.service.CommentService;
import cn.moondev.framework.annotation.Permit;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Permit
@RestController
@RequestMapping("/v1/comment")
public class CommentController {

    @Autowired
    private CommentService service;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseDTO<Void> addComment(@RequestBody Comment comment) {
        service.addComment(comment);
        return ResponseDTO.success();
    }
}
