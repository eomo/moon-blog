package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Topic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TopicMapper {

    @Select("SELECT * FROM t_topic")
    List<Topic> getAllTopic();

    @Select("SELECT * FROM t_topic WHERE `name` = #{name} LIMIT 1")
    Topic getTopicByName(@Param("name") String name);

    @Insert("INSERT INTO t_topic(`name`,`desc`,`image`,`format`) VALUES (#{item.name},#{item.desc},#{item.image},#{item.format})")
    void create(@Param("item") Topic topic);

    @Update("UPDATE t_topic SET `name` = #{item.name}, `desc` = #{item.desc}, `image` = #{item.image}, `format` = #{item.format} WHERE `id` = #{item.id}")
    void update(@Param("item") Topic topic);

    @Delete("DELETE FROM t_topic where `id` = #{id}")
    void delete(@Param("id") String id);

    @Select("SELECT * FROM t_topic WHERE `id` = #{id} LIMIT 1")
    Topic getTopicById(@Param("id") String id);

}
