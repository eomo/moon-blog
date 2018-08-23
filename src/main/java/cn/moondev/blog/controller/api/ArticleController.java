package cn.moondev.blog.controller.api;

import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.model.Article;
import cn.moondev.blog.service.ArticleService;
import cn.moondev.framework.annotation.Permit;
import cn.moondev.framework.model.PaginationDTO;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseDTO<Article> page(@PathVariable String id) {
        Article article = new Article();
        if (!"id_NuLlaRtIclE".equalsIgnoreCase(id)) {
            article = service.detail4Admin(id);
        }
        return ResponseDTO.success(article);
    }

    @Permit
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseDTO<PaginationDTO<Article>> page(@RequestBody QueryDTO query) {
        return ResponseDTO.success(service.page(query));
    }

    /**
     * 保存草稿
     */
    @RequestMapping(value = "/draft", method = RequestMethod.POST)
    public ResponseDTO<String> draft(@RequestBody Article article) {
        return ResponseDTO.success(service.draft(article));
    }

    /**
     * 发布文章
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public ResponseDTO<String> publish(@RequestBody Article article) {
        return ResponseDTO.success(service.publish(article));
    }

    /**
     * 删除文章
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseDTO<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseDTO.success();
    }

    /**
     * 保存特殊文章
     */
    @RequestMapping(value = "/badge", method = RequestMethod.POST)
    public ResponseDTO<String> publishBadgeArticle(@RequestBody Article article) {
        return ResponseDTO.success(service.publishBadgeArticle(article));
    }

    /**
     * 特殊文章
     */
    @RequestMapping(value = "/badge/{badge}", method = RequestMethod.GET)
    public ResponseDTO<Article> publishBadgeArticle(@PathVariable String badge) {
        return ResponseDTO.success(service.findByBadge(badge));
    }

}
