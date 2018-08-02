package cn.moondev.blog.controller.ui;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 错误页面
 */
@Controller
@RequestMapping("/error")
public class ErrorPageController implements ErrorController {

    public static final String ERROR = "/error";

    public String getErrorPath() {
        return ERROR;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String error(HttpServletResponse response) {
        switch (response.getStatus()) {
            case 404:
                return "/common/404";
            case 403:
                return "/common/403";
            default:
                return "/common/500";
        }
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public String error403(@PathVariable String code) {
        switch (code) {
            case "404":
                return "/common/404";
            case "403":
                return "/common/403";
            default:
                return "/common/500";
        }
    }
}
