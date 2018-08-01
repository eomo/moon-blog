package cn.moondev.blog.controller.api;

import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.model.Article;
import cn.moondev.blog.service.ArticleService;
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
            article = service.detail(id);
        }
        return ResponseDTO.success(article);
    }

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

}
