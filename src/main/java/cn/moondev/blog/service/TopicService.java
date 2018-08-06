package cn.moondev.blog.service;

import cn.moondev.blog.configuration.MessageCode;
import cn.moondev.blog.mapper.TopicMapper;
import cn.moondev.blog.model.Topic;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TopicService {

    private static final Logger LOG = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    private TopicMapper mapper;

    public List<Topic> getAllTopic() {
        return mapper.getAllTopic();
    }

    public void create(Topic topic) {
        validator(topic);
        Topic tmp = mapper.getTopicByName(topic.name);
        if (Objects.nonNull(tmp)) {
            throw MessageCode.ex(MessageCode.NAME_REPEAT);
        }
        tmp = mapper.getTopicByCode(topic.code);
        if (Objects.nonNull(tmp)) {
            throw MessageCode.ex(MessageCode.CODE_REPEAT);
        }
        mapper.create(topic);
    }

    public void update(Topic topic) {
        validator(topic);
        Topic tmp = mapper.getTopicByName(topic.name);
        if (Objects.nonNull(tmp) && tmp.id != topic.id) {
            throw MessageCode.ex(MessageCode.NAME_REPEAT);
        }
        tmp = mapper.getTopicByCode(topic.code);
        if (Objects.nonNull(tmp) && tmp.id != topic.id) {
            throw MessageCode.ex(MessageCode.CODE_REPEAT);
        }
        mapper.update(topic);
    }

    public void delete(long id) {
        mapper.delete(id);
    }

    public Topic getTopicByCode(String code) {
        return mapper.getTopicByCode(code);
    }

    private void validator(Topic topic) {
        if (Strings.isNullOrEmpty(topic.code) || topic.code.length() > 32) {
            throw MessageCode.ex(MessageCode.CODE_ERROR);
        }
        if (Strings.isNullOrEmpty(topic.name) || topic.name.length() > 32) {
            throw MessageCode.ex(MessageCode.NAME_ERROR);
        }
        if (Strings.isNullOrEmpty(topic.desc) || topic.desc.length() > 512) {
            throw MessageCode.ex(MessageCode.DESC_ERROR);
        }
        if (Strings.isNullOrEmpty(topic.image) || topic.desc.length() > 512) {
            throw MessageCode.ex(MessageCode.IMAGE_URL_ERROR);
        }
    }

}
