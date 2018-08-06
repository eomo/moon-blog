package cn.moondev.blog.controller.api;

import cn.moondev.blog.model.Topic;
import cn.moondev.blog.service.TopicService;
import cn.moondev.framework.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/topic")
public class TopicController {

    @Autowired
    private TopicService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseDTO<List<Topic>> topics() {
        return ResponseDTO.success(service.getAllTopic());
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseDTO<Void> createTopic(@RequestBody Topic topic) {
        service.create(topic);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseDTO<Void> updateTopic(@RequestBody Topic topic) {
        service.update(topic);
        return ResponseDTO.success();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseDTO<Void> deleteTopic(@PathVariable long id) {
        service.delete(id);
        return ResponseDTO.success();
    }

}
