package cn.moondev.blog.mapper;

import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {

    long upsert(@Param("item") Article article);

    Article findById(@Param("id") String id);

    Article findDetailById(@Param("id") String id);

    Article findContentById(@Param("id") String id);

    Long count(@Param("item") QueryDTO dto);

    List<Article> find(@Param("item") QueryDTO dto);

    void viewCountxx(@Param("id") String id);

    void commentCountxx(@Param("id") String id);

    List<Article> hot();

    Article statByCategory(@Param("categoryId") long categoryId);

    Article statByTopic(@Param("topicId") long topicId);

    List<Article> all();

    @Select("delete from t_article where id = #{id}")
    void delete(@Param("id") String id);

    Article findContentByBadge(@Param("badge") String badge);
}
