package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    public List<Article> getAllArticle();

}
