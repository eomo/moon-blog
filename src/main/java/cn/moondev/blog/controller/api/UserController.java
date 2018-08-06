package cn.moondev.blog.controller.api;

import cn.moondev.blog.dto.UserDTO;
import cn.moondev.blog.model.Category;
import cn.moondev.blog.service.CategoryService;
import cn.moondev.blog.service.UserService;
import cn.moondev.framework.annotation.Permit;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Permit
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDTO<String> login(@RequestBody UserDTO user) {
        return ResponseDTO.success(service.login(user));
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public ResponseDTO<List<Category>> menu() {
        List<Category> categories = categoryService.getMenuCategory();
        categories.add(Category.travel());
        categories.add(Category.book());
        return ResponseDTO.success(categories);
    }
}
