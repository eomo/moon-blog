package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> comments4Article(@Param("articleId") String articleId);

    void addComment(@Param("item") Comment comment);
}
