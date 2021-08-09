package com.hotel.index.feign;

import com.hotel.index.pojo.Menu;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(value = "index")
@RequestMapping(value = "/index")
public interface IndexFeign {

    @GetMapping("/menu")
    @ApiOperation(value = "获取用户菜单数据")
    List<Menu> getUserMenu();
}
