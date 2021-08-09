package com.hotel.wx.feign;


import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.HotelFile;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "wx")
@RequestMapping(value = "hotel")
public interface HotelFeign {

    @PostMapping("hotelList")
    Result<List<Hotel>> data(@RequestBody Hotel hotel);

    @PostMapping("add")
    Integer addAll(@RequestBody Hotel hotel);

    @PostMapping("addHF")
    Result addAll(@RequestBody HotelFile hotelFile);

    @GetMapping("byId")
    Result<Hotel> findById(@RequestParam Integer id);

    @GetMapping("findByParentId")
    Result findByParentId(@RequestParam int parentId);

    @PutMapping("updated")
    Result update(@RequestBody Hotel hotel);

    @DeleteMapping("delete/{id}")
    Result remove(@PathVariable Integer id);
}
