package cn.moondev.blog.controller.api;

import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.model.Movie;
import cn.moondev.blog.service.MovieService;
import cn.moondev.framework.annotation.Permit;
import cn.moondev.framework.model.PaginationDTO;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {

    @Autowired
    private MovieService service;

    @Permit
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseDTO<PaginationDTO<Movie>> page(@RequestBody QueryDTO query) {
        return ResponseDTO.success(service.page(query));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseDTO<Void> createMovie(@RequestBody Map<String,String> dto) {
        service.add(dto.get("doubanId"),dto.get("remark"));
        return ResponseDTO.success();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseDTO<Void> updateMovie(@RequestBody Map<String,String> dto) {
        service.update(dto.get("doubanId"),dto.get("remark"));
        return ResponseDTO.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseDTO<Void> deleteMovie(@PathVariable Long id) {
        service.delete(id);
        return ResponseDTO.success();
    }

}
