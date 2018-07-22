package cn.moondev.blog.controller;

import cn.moondev.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("")
public class BlogController {

    @Autowired
    private ArticleService articleService;

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
    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public String getArticleById(@PathVariable String id) {
        return "article";
    }
}
