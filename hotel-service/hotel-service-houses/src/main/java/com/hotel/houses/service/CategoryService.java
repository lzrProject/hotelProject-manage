package com.hotel.houses.service;

import com.hotel.houses.pojo.Category;

import java.util.List;

public interface CategoryService {

    //分类查询：查询所有子节点
    List<Category> findByParentId(Integer pid);

    //查询子节点下的分类
}
