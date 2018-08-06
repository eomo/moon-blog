package cn.moondev.blog.service;

import cn.moondev.blog.configuration.MessageCode;
import cn.moondev.blog.mapper.CategoryMapper;
import cn.moondev.blog.model.Category;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    public List<Category> getMenuCategory() {
        List<Category> categories = categoryMapper.getMenuCategory();
        categories.parallelStream().forEach(c -> {
            if (Strings.isNullOrEmpty(c.url)) {
                c.url = "/category/" + c.id;
            }
        });
        return categories;
    }

    public void create(Category category) {
        validator(category);
        Category tmp = categoryMapper.getCategoryByName(category.name);
        if (Objects.nonNull(tmp)) {
            throw MessageCode.ex(MessageCode.NAME_REPEAT);
        }
        tmp = categoryMapper.getCategoryByCode(category.code);
        if (Objects.nonNull(tmp)) {
            throw MessageCode.ex(MessageCode.CODE_REPEAT);
        }
        Long count = categoryMapper.count();
        category.orderNo = (int) (count + 1);
        categoryMapper.create(category);
    }

    public void update(Category category) {
        validator(category);
        Category tmp = categoryMapper.getCategoryByName(category.name);
        if (Objects.nonNull(tmp) && tmp.id != category.id) {
            throw MessageCode.ex(MessageCode.NAME_REPEAT);
        }
        tmp = categoryMapper.getCategoryByCode(category.code);
        if (Objects.nonNull(tmp) && tmp.id != category.id) {
            throw MessageCode.ex(MessageCode.CODE_REPEAT);
        }
        categoryMapper.update(category);
    }

    public void delete(long id) {
        categoryMapper.delete(id);
    }

    /**
     * 置顶
     */
    public void stick(long id) {
        List<Category> categories = categoryMapper.getAllCategory();
        int index = 2;
        for (Category category : categories) {
            if (category.id == id) {
                categoryMapper.updateOrderNo(1, category.id);
            } else {
                categoryMapper.updateOrderNo(index, category.id);
                index++;
            }
        }
    }

    public Category getCategoryByCode(String code) {
        if ("readming".equalsIgnoreCase(code)) {
            return Category.book();
        }
        if ("travel".equalsIgnoreCase(code)) {
            return Category.travel();
        }
        return categoryMapper.getCategoryByCode(code);
    }

    private void validator(Category category) {
        if (Strings.isNullOrEmpty(category.code) || category.code.length() > 32) {
            throw MessageCode.ex(MessageCode.CODE_ERROR);
        }
        if (Strings.isNullOrEmpty(category.name) || category.name.length() > 32) {
            throw MessageCode.ex(MessageCode.NAME_ERROR);
        }
        if (Strings.isNullOrEmpty(category.desc) || category.desc.length() > 512) {
            throw MessageCode.ex(MessageCode.DESC_ERROR);
        }
        if (Strings.isNullOrEmpty(category.image) || category.desc.length() > 512) {
            throw MessageCode.ex(MessageCode.IMAGE_URL_ERROR);
        }
    }

}
