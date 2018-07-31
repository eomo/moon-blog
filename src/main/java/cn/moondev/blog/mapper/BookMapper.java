package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Article;
import cn.moondev.blog.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    void insert(@Param("item") Book book);

}
