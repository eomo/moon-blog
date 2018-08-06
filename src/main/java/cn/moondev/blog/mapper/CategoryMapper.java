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

    @Select("SELECT * FROM t_category WHERE `menu` = 1 ORDER BY order_no asc")
    List<Category> getMenuCategory();

    @Select("SELECT * FROM t_category WHERE `name` = #{name} LIMIT 1")
    Category getCategoryByName(@Param("name") String name);

    @Insert("INSERT INTO t_category(`code`, `name`,`desc`,`image`,`url`,`menu`,`order_no`,`format1`,`format2`) VALUES (#{item.code}, #{item.name},#{item.desc},#{item.image},#{item.url},#{item.menu},#{item.orderNo},#{item.format1},#{item.format2})")
    void create(@Param("item") Category category);

    @Update("UPDATE t_category SET `name` = #{item.name}, `desc` = #{item.desc}, `image` = #{item.image}, `url` = #{item.url}, `menu` = #{item.menu}, `format1` = #{item.format1}, `format2` = #{item.format2}, `code` = #{item.code} WHERE `id` = #{item.id}")
    void update(@Param("item") Category category);

    @Delete("DELETE FROM t_category where `id` = #{id}")
    void delete(@Param("id") long id);

    @Update("UPDATE t_category SET `order_no` = #{orderNo} WHERE `id` = #{id}")
    void updateOrderNo(@Param("orderNo") Integer orderNo, @Param("id") long id);

    @Select("SELECT COUNT(*) FROM t_category")
    Long count();

    @Select("SELECT * FROM t_category WHERE `id` = #{id} LIMIT 1")
    Category getCategoryById(@Param("id") long id);

    @Select("SELECT * FROM t_category WHERE `code` = #{code} LIMIT 1")
    Category getCategoryByCode(@Param("code") String code);
}
