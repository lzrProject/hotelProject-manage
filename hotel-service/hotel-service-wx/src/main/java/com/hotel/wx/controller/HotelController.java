package com.hotel.wx.controller;

import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.HotelFile;
import com.hotel.wx.service.HotelService;
import entity.Result;
import entity.StatusCode;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hotel")
@CrossOrigin
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("list")
    public Result findAll(){
        List<Hotel> list = hotelService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @PostMapping("hotelList")
    public Result<List<Hotel>> data(@RequestBody Hotel hotel){
        List<Hotel> list = hotelService.findList(hotel);
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @PostMapping("add")
    public Integer addAll(@RequestBody Hotel hotel){
        hotelService.addAll(hotel);
        return hotel.getId();
    }

    @PostMapping("addHF")
    public Result addAll(@RequestBody HotelFile hotelFile){
        hotelService.addAll(hotelFile);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    @GetMapping("byId")
    public Result<Hotel> findById(@RequestParam Integer id){
        Hotel result = hotelService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",result);
    }

    @GetMapping("findByParentId")
    public Result findByParentId(@RequestParam int parentId){
        List<Hotel> byParentId = hotelService.findByParentId(parentId);
        return new Result(true,StatusCode.OK,"查询成功",byParentId);
    }

    @PutMapping("updated")
    public Result update(@RequestBody Hotel hotel) {
        hotelService.update(hotel);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping("delete/{id}")
    public Result remove(@PathVariable Integer id){
        hotelService.delById(id);
        hotelService.delByHotelId(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
