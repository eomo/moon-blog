package cn.moondev.blog.controller.ui;

import cn.moondev.framework.annotation.Permit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 进入管理后台页面
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * 登录页面
     */
    @RequestMapping(value = "/ddl", method = RequestMethod.GET)
    public String login(Model model) {
        return "/admin/admin-login";
    }

    /**
     * 分类管理
     */
    @Permit
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String category(Model model) {
        return "/admin/admin-category";
    }

    /**
     * 专题管理
     */
    @Permit
    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public String topic(Model model) {
        return "/admin/admin-topic";
    }

    /**
     * 图书管理
     */
    @Permit
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public String book(Model model) {
        return "/admin/admin-book";
    }

    /**
     * 电影管理
     */
    @Permit
    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public String movie(Model model) {
        return "/admin/admin-movie";
    }

    /**
     * 文章管理
     */
    @Permit
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public String article(Model model) {
        return "/admin/admin-article";
    }

    /**
     * 写文章
     */
    @RequestMapping(value = "/write/article/{id}", method = RequestMethod.GET)
    public String writeArticle(Model model, @PathVariable String id) {
        model.addAttribute("article_id", id);
        return "/admin/write-article";
    }

    /**
     * 编辑[关于我]页面内容
     */
    @Permit
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about(Model model) {
        return "/admin/admin-about";
    }

    /**
     * 文件上传
     */
    @Permit
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String upload(Model model) {
        return "/admin/admin-upload";
    }
}
