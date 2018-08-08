package cn.moondev.blog.service;

import cn.moondev.blog.configuration.MessageCode;
import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.mapper.ArticleMapper;
import cn.moondev.blog.model.Article;
import cn.moondev.framework.model.PaginationDTO;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 公司简介
 */
@Service
public class ArticleService extends BaseService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleMapper mapper;

    public PaginationDTO<Article> page(QueryDTO dto) {
        PaginationDTO<Article> pagination = PaginationDTO.create(dto.pager, dto.size);
        pagination.setTotal(mapper.count(dto));
        if (pagination.getTotal() > 0) {
            dto.offset = pagination.getOffset();
            List<Article> articles = mapper.find(dto);
            articles.parallelStream().forEach(article -> {
                article.publishTimeDesc = replyTimeDesc(article.publishTime);
                article.summary = article.summary.length() > 80 ?
                        article.summary.substring(0, 80) + "..." : article.summary;
            });
            pagination.setList(articles);
        }
        return pagination;
    }

    /**
     * 热门文章
     */
    public List<Article> hotArticles() {
        return mapper.hot();
    }

    /**
     * 保存草稿
     */
    public String draft(Article article) {
        if (Strings.isNullOrEmpty(article.id)) {
            article.id = genArticleId();
            article.updatedTime = LocalDateTime.now();
            article.createdTime = LocalDateTime.now();
            article.status = 0;
            mapper.upsert(article);
            return article.id;
        }
        Article tmp = mapper.findById(article.id);
        if (Objects.isNull(tmp)) {
            throw MessageCode.ex(MessageCode.ARTICLE_NOT_EXISTS);
        }
        tmp.title = article.title;
        tmp.content = article.content;
        tmp.topicId = article.topicId;
        tmp.categoryId = article.categoryId;
        tmp.updatedTime = LocalDateTime.now();
        tmp.status = 0;
        mapper.upsert(tmp);
        return article.id;
    }

    /**
     * 发布文章
     */
    public String publish(Article article) {
        if (Strings.isNullOrEmpty(article.id)) {
            article.id = genArticleId();
            article.updatedTime = LocalDateTime.now();
            article.createdTime = LocalDateTime.now();
            article.publishTime = LocalDateTime.now();
            article.status = 1;
            mapper.upsert(article);
            return article.id;
        }
        Article tmp = mapper.findById(article.id);
        if (Objects.isNull(tmp)) {
            throw MessageCode.ex(MessageCode.ARTICLE_NOT_EXISTS);
        }
        tmp.title = article.title;
        tmp.content = article.content;
        tmp.topicId = article.topicId;
        tmp.categoryId = article.categoryId;
        tmp.updatedTime = LocalDateTime.now();
        tmp.publishTime = LocalDateTime.now();
        tmp.status = 1;
        mapper.upsert(tmp);
        return article.id;
    }

    public Article detail(String id) {
        return mapper.findDetailById(id);
    }

    /**
     * 浏览量++
     */
    public void viewCountxx(String id) {
        mapper.viewCountxx(id);
    }

    /**
     * 评论数++
     */
    public void commentCountxx(String id) {
        mapper.commentCountxx(id);
    }

    /**
     * 生成文章ID
     */
    private String genArticleId() {
        String id = Article.genId();
        return Objects.isNull(mapper.findById(id)) ? id : genArticleId();
    }

    public Article statByCategory(long categoryId) {
        return mapper.statByCategory(categoryId);
    }

    public Article statByTopic(long topicId) {
        return mapper.statByTopic(topicId);
    }

    public Map<String, List<Article>> archive() {
        List<Article> articles = mapper.all();
        return articles.stream().collect(Collectors.groupingBy(Article::year));
    }
}
