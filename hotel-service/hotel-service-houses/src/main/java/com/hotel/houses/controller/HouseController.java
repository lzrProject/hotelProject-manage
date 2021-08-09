package com.hotel.houses.controller;

import com.github.pagehelper.PageInfo;
import com.hotel.houses.pojo.Houses;
import com.hotel.houses.service.HouseService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tb_houses")
@CrossOrigin //跨域
public class HouseController {

    @Autowired
    private HouseService houseService;



    //添加数据
    @PostMapping
    public Result addAll(@RequestBody Houses houses){
        houseService.addAll(houses);
        return new Result(true,StatusCode.OK,"查询成功");
    }

    //删除数据
    @DeleteMapping(value = "/{id}")
    public Result delById(@PathVariable(value = "id") Integer id){
        houseService.delById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    //修改数据
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id")Integer id,@RequestBody Houses houses){
        houses.setId(id);
        houseService.update(houses);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    //根据id查询
    @GetMapping(value = "/{id}")
    public Result<Houses> findById(@PathVariable(value = "id")Integer id){
        Houses result = houseService.findById(id);
        return new Result<Houses>(true,StatusCode.OK,"查询成功",result);
    }

    ///根据条件查询
    @PostMapping(value = "/search")
    public Result<List<Houses>> findList(@RequestBody Houses houses){
        List<Houses> result = houseService.findList(houses);
        return new Result<List<Houses>>(true, StatusCode.OK,"查询成功",result);
    }

    //查询所有
    @GetMapping
    public Result<Houses> findAll(){
        List<Houses> result = houseService.findAll();
        return new Result<Houses>(true, StatusCode.OK,"查询成功",result);
    }


    //分页查询

    /***
     * @PathVariable: 获取URL中占位符参数
     *
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page,@PathVariable int size){
        PageInfo<Houses> result = houseService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK,"查询成功",result);
    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody Houses houses,@PathVariable int page,@PathVariable int size){
        PageInfo<Houses> result = houseService.findPage(houses, page, size);
        return new Result<PageInfo>(true, StatusCode.OK,"查询成功",result);
    }
}
