package com.hotel.houses.service.impl;

import com.hotel.houses.dao.CategoryMapper;
import com.hotel.houses.pojo.Category;
import com.hotel.houses.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByParentId(Integer pid) {      //分类查询：查询所有子节点
        Category category = new Category();
        category.setParentId(pid);
        /***
         * select()非空字段作为条件参数
         */
        return categoryMapper.select(category);
    }
}
