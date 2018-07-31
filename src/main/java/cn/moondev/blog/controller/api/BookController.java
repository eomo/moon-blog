package cn.moondev.blog.controller.api;

import cn.moondev.blog.dto.BookDTO;
import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.model.Book;
import cn.moondev.blog.service.BookService;
import cn.moondev.framework.model.PaginationDTO;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    @Autowired
    private BookService service;

    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public ResponseDTO<PaginationDTO<Book>> page(@RequestBody QueryDTO query) {
        return ResponseDTO.success(service.page(query));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseDTO<Void> createBook(@RequestBody BookDTO dto) {
        service.add(dto);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseDTO<Void> updateBook(@RequestBody BookDTO dto) {
        service.update(dto);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseDTO<Void> deleteBook(@PathVariable Long id) {
        service.delete(id);
        return ResponseDTO.success();
    }

}
