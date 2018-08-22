package cn.moondev.blog.controller.ui;

import cn.moondev.blog.model.Article;
import cn.moondev.blog.model.Category;
import cn.moondev.blog.model.Topic;
import cn.moondev.blog.service.ArticleService;
import cn.moondev.blog.service.CategoryService;
import cn.moondev.blog.service.TopicService;
import cn.moondev.framework.annotation.Permit;
import cn.moondev.framework.provider.markdown.MarkdownOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
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
    @Autowired
    private MarkdownOperations markdownOperations;

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
    @RequestMapping(value = "/category/{code}", method = RequestMethod.GET)
    public String categoryIndex(Model model, @PathVariable String code) {
        Category category = categoryService.getCategoryByCode(code);
        if (Objects.isNull(category)) {
            return "/common/404";
        }
        Article article = articleService.statByCategory(category.id);
        category.viewCount = Objects.isNull(article) ? 0 : article.viewCount;
        category.commentCount = Objects.isNull(article) ? 0 : article.commentCount;
        model.addAttribute("category", category);
        return "/app/app-category";
    }

    /**
     * 所有分类
     */
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String categoriesIndex(Model model) {
        List<Category> categories = categoryService.getAllCategory();
        categories.add(Category.travel());
        categories.add(Category.book());
        model.addAttribute("categories", categories);
        return "/app/app-categories";
    }

    /**
     * 专题页
     */
    @RequestMapping(value = "/topic/{code}", method = RequestMethod.GET)
    public String topicIndex(Model model, @PathVariable String code) {
        Topic topic = topicService.getTopicByCode(code);
        if (Objects.isNull(topic)) {
            return "/common/404";
        }
        Article article = articleService.statByTopic(topic.id);
        topic.viewCount = Objects.isNull(article) ? 0 : article.viewCount;
        topic.commentCount = Objects.isNull(article) ? 0 : article.commentCount;
        model.addAttribute("topic", topic);
        return "/app/app-topic";
    }

    /**
     * 足迹
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
     * 归档
     */
    @RequestMapping(value = "/archive", method = RequestMethod.GET)
    public String archiveIndex(Model model) {
        model.addAttribute("amap", articleService.archive());
        return "/app/archive";
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
        article.content = markdownOperations.markdown2Html(article.content);
        model.addAttribute("article", article);
        return "/app/app-article";
    }
}
