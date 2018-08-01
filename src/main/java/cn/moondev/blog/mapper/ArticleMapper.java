package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    long upsert(@Param("item") Article article);

    Article findById(@Param("id") String id);

    Article findDetailById(@Param("id") String id);

    Long count(@Param("year") String year, @Param("categoryId") String categoryId);

    List<Article> find(@Param("year") String year, @Param("categoryId") String categoryId,
                       @Param("size") int size, @Param("offset") int offset);
}
