package cn.moondev.blog.controller.api;

import cn.moondev.blog.model.Category;
import cn.moondev.blog.service.CategoryService;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseDTO<List<Category>> categories() {
        return ResponseDTO.success(service.getAllCategory());
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseDTO<Void> createCategory(@RequestBody Category category) {
        service.create(category);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseDTO<Void> updateCategory(@RequestBody Category category) {
        service.update(category);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseDTO<Void> deleteCategory(@PathVariable long id) {
        service.delete(id);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "/stick/{id}", method = RequestMethod.POST)
    public ResponseDTO<Void> stick(@PathVariable long id) {
        service.stick(id);
        return ResponseDTO.success();
    }

}
