package cn.moondev.blog.service;

import cn.moondev.blog.configuration.MessageCode;
import cn.moondev.blog.dto.QueryDTO;
import cn.moondev.blog.mapper.MovieMapper;
import cn.moondev.blog.model.Movie;
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
public class MovieService {

    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);
    @Autowired
    private MovieMapper mapper;
    @Autowired
    private OkHttpOperations okHttpOperations;
    @Autowired
    private QiniuOperations qiniuOperations;

    public void add(String doubanId, String remark) {
        Movie movie = getMovieInfoFromDouban(doubanId, remark);
        mapper.upsert(movie);
    }

    public void update(String doubanId, String remark) {
        Movie movie = mapper.findByDoubanId(doubanId);
        Movie newMovie = getMovieInfoFromDouban(doubanId, remark);
        movie.remark = newMovie.remark;
        mapper.upsert(newMovie);
    }

    public void delete(Long id) {
        mapper.delete(id);
    }

    public PaginationDTO<Movie> page(QueryDTO dto) {
        PaginationDTO<Movie> pagination = PaginationDTO.create(dto.pager, dto.size);
        pagination.setTotal(mapper.count(dto.title, dto.author));
        if (pagination.getTotal() > 0) {
            List<Movie> movies = mapper.find(dto.title, dto.author, pagination.getSize(), pagination.getOffset());
            pagination.setList(movies);
        }
        return pagination;
    }

    private Movie getMovieInfoFromDouban(String doubanId, String remark) {
        OkHttpRequest request = new OkHttpRequest();
        request.domain = "https://api.douban.com/v2/movie/subject/" + doubanId;
        String content = okHttpOperations.directSyncRequest(request);
        LOG.info("获取电影信息:{}", content);
        if (Strings.isNullOrEmpty(content)) {
            throw MessageCode.ex(MessageCode.API_ERROR);
        }
        JSONObject jsonObject = JSONObject.parseObject(content);
        Movie movie = new Movie(jsonObject);
        movie.smallImage = fetchImage(movie.smallImage);
        movie.largeImage = fetchImage(movie.largeImage);
        movie.mediumImage = fetchImage(movie.mediumImage);
        movie.remark = remark;
        return movie;
    }

    private String fetchImage(String doubanImage) {
        String qiniuImage = qiniuOperations.fetch(doubanImage, "sources", "movie/image", "http://apps.moondev.cn/");
        return Strings.isNullOrEmpty(qiniuImage) ? doubanImage : qiniuImage;
    }
}
