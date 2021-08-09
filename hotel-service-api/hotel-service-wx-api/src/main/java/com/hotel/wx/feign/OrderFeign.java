package com.hotel.wx.feign;

import com.github.pagehelper.PageInfo;
import com.hotel.wx.pojo.Order;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "wx")
@RequestMapping(value = "order")
public interface OrderFeign {

    @PostMapping("findAll")
    Result<PageInfo> findAll(@RequestBody Order order, @RequestParam int page, @RequestParam int limit);

    @PostMapping("add")
    Result addAll(@RequestBody Order order);

    @PostMapping("findByOpenId")
    Result<List<Order>> findByOpenId(@RequestBody Order order);

    @GetMapping("findList")
    Result<List<Order>> findList();

    @PostMapping("update")
    Result update(@RequestBody Order order);
}
