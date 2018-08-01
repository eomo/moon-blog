package cn.moondev.blog.controller.api;

import cn.moondev.blog.dto.UserDTO;
import cn.moondev.blog.service.UserService;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseDTO<String> login(@RequestBody UserDTO user) {
        return ResponseDTO.success(service.login(user));
    }
}
