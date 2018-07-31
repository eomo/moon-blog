package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {

    void upsert(@Param("item") Movie movie);

    void delete(@Param("id") Long id);

    Long count(@Param("title") String title, @Param("person") String person);

    List<Movie> find(@Param("title") String title, @Param("person") String person,
                     @Param("size") int size, @Param("offset") int offset);

    Movie findByDoubanId(@Param("doubanId") String doubanId);
}
