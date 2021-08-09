package com.hotel.index.controller;

import com.hotel.index.pojo.Menu;
import com.hotel.index.service.IndexService;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/index")
@CrossOrigin //跨域
public class IndexController {
    @Autowired
    private IndexService indexService;

    /**
     *查询菜单数据
     */
    @GetMapping("menu")
    public List<Menu> getUserMenu(){
//        Menu menu = new Menu();
//        menu.setParentId(0);
        List<Menu> menus = indexService.findAll();
        List<Menu> menus1 = indexService.toUserMenu(menus, 0);
        for (int i = 0;i < menus1.size();i++){
            if(menus1.get(i).getEnable().equals(0)){
                menus1.remove(i);
            }else {
                for(int j=0;j < menus1.get(i).getChildren().size();j++){
                    if(menus1.get(i).getChildren().get(j).getEnable().equals(0)) {
                        menus1.get(i).getChildren().remove(j);
                    }
                }
            }

        }
        return menus1;
    }
}
