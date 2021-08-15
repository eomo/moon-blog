package cn.moondev.blog.service;

import cn.moondev.blog.configuration.MessageCode;
import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.mapper.ArticleMapper;
import cn.moondev.blog.model.Article;
import cn.moondev.framework.model.PaginationDTO;
import cn.moondev.framework.provider.markdown.MarkdownOperations;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 公司简介
 */
@Service
public class ArticleService extends BaseService {

    private static final Logger LOG = LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    private ArticleMapper mapper;
    @Autowired
    private Cache<String, String> articleCache;
    @Autowired
    private MarkdownOperations markdownOperations;

    public PaginationDTO<Article> page(QueryDTO dto) {
        PaginationDTO<Article> pagination = PaginationDTO.create(dto.pager, dto.size);
        pagination.setTotal(mapper.count(dto));
        if (pagination.getTotal() > 0) {
            dto.offset = pagination.getOffset();
            List<Article> articles = mapper.find(dto);
            articles.parallelStream().forEach(article -> {
                article.publishTimeDesc = replyTimeDesc(article.publishTime);
                article.summary = article.summary.length() > 60 ?
                        article.summary.substring(0, 60) + "..." : article.summary;
            });
            pagination.setList(articles);
        }
        return pagination;
    }

    /**
     * 热门文章
     */
    public List<Article> hotArticles() {
        List<Article> articles = mapper.hot();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
        articles.stream().forEach(a -> a.publishTimeDesc = a.publishTime.format(dateTimeFormatter));
        return articles;
    }

    /**
     * 保存草稿
     */
    public String draft(Article article) {
        if (Strings.isNullOrEmpty(article.id)) {
            article.id = genArticleId();
            article.updatedTime = LocalDateTime.now();
            article.createdTime = LocalDateTime.now();
            article.publishTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0, 0);
            article.status = 0;
            mapper.upsert(article);
            return article.id;
        }
        Article tmp = mapper.findById(article.id);
        if (Objects.isNull(tmp)) {
            throw MessageCode.ex(MessageCode.ARTICLE_NOT_EXISTS);
        }
        tmp.title = article.title;
        tmp.summary = article.summary;
        tmp.content = article.content;
        tmp.topicId = article.topicId;
        tmp.categoryId = article.categoryId;
        tmp.image = article.image;
        tmp.aformat = article.aformat;
        tmp.mformat = article.mformat;
        tmp.updatedTime = LocalDateTime.now();
        tmp.status = 0;
        mapper.upsert(tmp);
        articleCache.invalidate(article.id);
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
        tmp.summary = article.summary;
        tmp.content = article.content;
        tmp.topicId = article.topicId;
        tmp.categoryId = article.categoryId;
        tmp.image = article.image;
        tmp.aformat = article.aformat;
        tmp.mformat = article.mformat;
        tmp.updatedTime = LocalDateTime.now();
        tmp.publishTime = Objects.isNull(tmp.publishTime) || tmp.publishTime.getYear() < 2017 ? LocalDateTime.now() : tmp.publishTime;
        tmp.status = 1;
        mapper.upsert(tmp);
        // 如果重新发布文章，清理缓存中的内容
        articleCache.invalidate(article.id);
        return article.id;
    }

    /**
     * 发布文章
     */
    public String publishBadgeArticle(Article article) {
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
        tmp.summary = article.summary;
        tmp.content = article.content;
        tmp.topicId = article.topicId;
        tmp.categoryId = article.categoryId;
        tmp.image = article.image;
        tmp.aformat = article.aformat;
        tmp.mformat = article.mformat;
        tmp.updatedTime = LocalDateTime.now();
        tmp.publishTime = Objects.isNull(tmp.publishTime) ? LocalDateTime.now() : tmp.publishTime;
        tmp.status = 1;
        mapper.upsert(tmp);
        // 如果重新发布文章，清理缓存中的内容
        articleCache.invalidate(article.id);
        return article.id;
    }

    public Article detail(String id) {
        viewCountxx(id);
        Article article = mapper.findDetailById(id);
        if (Objects.isNull(article)) {
            article = new Article();
        }
        article.publishTimeDesc = replyTimeDesc(article.publishTime);
        article.content = getArticleContent(id);
        return article;
    }

    public Article detail4Admin(String id) {
        return mapper.findById(id);
    }

    public Article findByBadge(String badge) {
        List<Article> articles = mapper.findContentByBadge(badge);
        return articles.isEmpty() ? null : articles.get(0);
    }

    public Article aboutme() {
        Article article = findByBadge("ABOUTME");
        viewCountxx(article.id);
        return article;
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
     * 删除指定文章
     */
    public void delete(String id) {
        mapper.delete(id);
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
        Map<String, List<Article>> map = Maps.newTreeMap((o1, o2) -> o2.compareTo(o1));
        for (Article article : articles) {
            List<Article> itemList = map.get(article.getYear());
            if (Objects.isNull(itemList)) {
                itemList = Lists.newLinkedList();
                map.put(article.getYear(), itemList);
            }
            itemList.add(article);
        }
        return map;
    }

    public void updateShowFlag(String id, int flag) {
        mapper.updateShowFlag(id, flag);
    }

    private String getArticleContent(String id) {
        try {
            String content = articleCache.get(id, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Article article = mapper.findContentById(id);
                    return markdownOperations.markdown2Html(article.content);
                }
            });
            return content;
        } catch (ExecutionException e) {
            LOG.error("获取文章内容出现异常", e);
            return "您浏览的文章已删除，请阅读其他文章或联系作者";
        }
    }
}
