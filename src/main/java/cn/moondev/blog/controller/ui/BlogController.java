package cn.moondev.blog.controller.ui;

import cn.moondev.blog.model.Article;
import cn.moondev.blog.model.Category;
import cn.moondev.blog.model.Topic;
import cn.moondev.blog.service.ArticleService;
import cn.moondev.blog.service.CategoryService;
import cn.moondev.blog.service.TopicService;
import cn.moondev.framework.annotation.Permit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Objects;

@Permit
@Controller
@RequestMapping("")
public class BlogController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TopicService topicService;

    /**
     * 首页
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("topics", topicService.getAllTopic());
        model.addAttribute("hots", articleService.hotArticles());
        return "/app/app-index";
    }

    /**
     * 分类页面
     */
    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public String categoryIndex(Model model, @PathVariable String id) {
        Category category = categoryService.getCategoryById(Long.parseLong(id));
        Article article = articleService.statByCategory(id);
        category.viewCount = article.viewCount;
        category.commentCount = article.commentCount;
        model.addAttribute("category", category);
        return "/app/app-category";
    }

    /**
     * 专题页
     */
    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    public String topicIndex(Model model, @PathVariable String id) {
        Topic topic = topicService.getTopicById(Long.parseLong(id));
        Article article = articleService.statByTopic(id);
        topic.viewCount = article.viewCount;
        topic.commentCount = article.commentCount;
        model.addAttribute("topic", topic);
        return "/app/app-topic";
    }

    /**
     * 足迹页面
     */
    @RequestMapping(value = "/travel", method = RequestMethod.GET)
    public String travelIndex(Model model) {
        return "/app/app-travel";
    }

    /**
     * 图书
     */
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String bookIndex(Model model) {
        return "/app/app-book";
    }

    /**
     * 观影记录
     */
    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public String movieIndex(Model model) {
        return "/app/app-movie";
    }


    /**
     * 文章详情
     */
    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public String getArticleById(Model model, @PathVariable String id) throws UnsupportedEncodingException {
        articleService.viewCountxx(id);
        Article article = articleService.detail(id);
        if (Objects.isNull(article)) {
            article = new Article();
        }
        article.content = Base64.getEncoder().encodeToString(article.content.getBytes("utf-8"));
        model.addAttribute("article", article);
        return "/app/app-article";
    }
}
