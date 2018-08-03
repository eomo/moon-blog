package cn.moondev.blog.controller.ui;

import cn.moondev.blog.model.Article;
import cn.moondev.blog.model.Category;
import cn.moondev.blog.service.ArticleService;
import cn.moondev.blog.service.CategoryService;
import cn.moondev.framework.annotation.Permit;
import cn.moondev.framework.model.ResponseDTO;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
public class BlogController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 首页
     */
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }


    /**
     * 文章详情
     */
    @Permit
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
