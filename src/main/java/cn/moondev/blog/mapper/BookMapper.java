package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    void upsert(@Param("item") Book book);

    void delete(@Param("id") Long id);

    Long count(@Param("title") String title, @Param("author") String author);

    List<Book> find(@Param("title") String title, @Param("author") String author,
                    @Param("size") int size, @Param("offset") int offset);

    List<Book> year(@Param("year") String year);

    Book findByDoubanId(@Param("doubanId") String doubanId);
}
