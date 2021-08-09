package com.hotel.houses.controller;

import com.hotel.houses.pojo.Category;
import com.hotel.houses.service.CategoryService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tb_category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/list/{pid}")
    public Result<List<Category>> findByParentId(@PathVariable Integer pid){
        List<Category> result = categoryService.findByParentId(pid);
        return new Result<List<Category>>(true, StatusCode.OK,"查询成功",result);
    }
}
