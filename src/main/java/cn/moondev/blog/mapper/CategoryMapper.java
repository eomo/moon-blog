package cn.moondev.blog.mapper;

import cn.moondev.blog.model.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM t_category ORDER BY order_no asc")
    List<Category> getAllCategory();

    @Select("SELECT * FROM t_category WHERE `name` = #{name} LIMIT 1")
    Category getCategoryByName(@Param("name") String name);

    @Insert("INSERT INTO t_category(`name`,`desc`,`image`,`url`,`menu`,`order_no`) VALUES (#{item.name},#{item.desc},#{item.image},#{item.url},#{item.menu},#{item.orderNo})")
    void create(@Param("item") Category category);

    @Update("UPDATE t_category SET `name` = #{item.name}, `desc` = #{item.desc}, `image` = #{item.image}, `url` = #{item.url}, `menu` = #{item.menu} WHERE `id` = #{item.id}")
    void update(@Param("item") Category category);

    @Delete("DELETE FROM t_category where `id` = #{id}")
    void delete(@Param("id") Integer id);

    @Update("UPDATE t_category SET `order_no` = #{orderNo} WHERE `id` = #{id}")
    void updateOrderNo(@Param("orderNo") Integer orderNo, @Param("id") Integer id);

    @Select("SELECT COUNT(*) FROM t_category")
    Long count();
}
