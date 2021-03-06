package cn.moondev.blog.service;

import cn.moondev.blog.configuration.MessageCode;
import cn.moondev.blog.dto.BookDTO;
import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.mapper.BookMapper;
import cn.moondev.blog.model.Book;
import cn.moondev.blog.provider.QiniuOperations;
import cn.moondev.framework.model.PaginationDTO;
import cn.moondev.framework.provider.okhttp3.OkHttpOperations;
import cn.moondev.framework.provider.okhttp3.OkHttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private BookMapper mapper;
    @Autowired
    private OkHttpOperations okHttpOperations;
    @Autowired
    private QiniuOperations qiniuOperations;

    public void add(BookDTO dto) {
        Book book = getBookInfoFromDouban(dto);
        mapper.upsert(book);
    }

    public void update(BookDTO dto) {
        Book book = mapper.findByDoubanId(dto.doubanId);
        Book newBook = getBookInfoFromDouban(dto);
        book.rating = newBook.rating;
        book.author = newBook.author;
        book.translator = newBook.translator;
        book.jingdongUrl = newBook.jingdongUrl;
        book.duokanUrl = newBook.duokanUrl;
        book.year = newBook.year;
        book.remark = newBook.remark;
        book.remarkUrl = newBook.remarkUrl;
        book.smallImage = newBook.smallImage;
        book.largeImage = newBook.largeImage;
        book.mediumImage = newBook.mediumImage;
        mapper.upsert(book);
    }

    public void delete(Long id) {
        mapper.delete(id);
    }

    public List<Book> year(String year) {
        return mapper.year(year);
    }

    public PaginationDTO<Book> page(QueryDTO dto) {
        PaginationDTO<Book> pagination = PaginationDTO.create(dto.pager, dto.size);
        pagination.setTotal(mapper.count(dto.title, dto.author));
        if (pagination.getTotal() > 0) {
            List<Book> books = mapper.find(dto.title, dto.author, pagination.getSize(), pagination.getOffset());
            pagination.setList(books);
        }
        return pagination;
    }

    private Book getBookInfoFromDouban(BookDTO dto) {
        OkHttpRequest request = new OkHttpRequest();
        request.domain = "https://api.douban.com/v2/book/" + dto.doubanId + "?apikey=0df993c66c0c636e29ecbb5344252a4a";
        String content = okHttpOperations.directSyncRequest(request);
        LOG.info("douban api return:{}", content);
        if (Strings.isNullOrEmpty(content)) {
            throw MessageCode.ex(MessageCode.API_ERROR);
        }
        JSONObject jsonObject = JSONObject.parseObject(content);
        Book book = new Book(jsonObject);
        book.smallImage = fetchImage(book.smallImage);
        book.largeImage = fetchImage(book.largeImage);
        book.mediumImage = fetchImage(book.mediumImage);
        book.jingdongUrl = Strings.isNullOrEmpty(dto.jingdongUrl) ? "" : dto.jingdongUrl;
        book.duokanUrl = Strings.isNullOrEmpty(dto.duokanUrl) ? "" : dto.duokanUrl;
        book.year = Strings.isNullOrEmpty(dto.year) ? "" : dto.year;
        book.remark = Strings.isNullOrEmpty(dto.remark) ? "" : dto.remark;
        book.remarkUrl = dto.remarkUrl;
        return book;
    }

    private String fetchImage(String doubanImage) {
        String qiniuImage = qiniuOperations.fetch(doubanImage, "resources-hicsc", "image/book/", "https://resources.hicsc.com/");
        return Strings.isNullOrEmpty(qiniuImage) ? doubanImage : qiniuImage;
    }
}
