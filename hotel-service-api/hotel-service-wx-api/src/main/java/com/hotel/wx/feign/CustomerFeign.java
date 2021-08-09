package com.hotel.wx.feign;

import com.hotel.wx.pojo.Customer;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "wx")
@RequestMapping(value = "customer")
public interface CustomerFeign {
    @PostMapping("addOpenId")
    Integer addOpenId(@RequestBody Customer customer);

    @GetMapping("findByOpenId")
    Result<List<Customer>> findByOpenId(@RequestParam String openId);

}
