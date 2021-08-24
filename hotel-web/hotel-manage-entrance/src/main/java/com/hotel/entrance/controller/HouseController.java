package com.hotel.entrance.controller;

import com.alibaba.fastjson.JSONObject;
import com.hotel.index.feign.FileFeign;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.Power;
import com.hotel.wx.feign.HotelFeign;
import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.HotelFile;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("house")
@RestController
public class HouseController {

    @Autowired
    private HotelFeign hotelFeign;

    @Autowired
    private FileFeign fileFeign;

    /**
     * 房源界面
     * */
    private String MODULE_PATH = "system/house/";

    /**
     * 订单信息
     * @return
     */
    @RequestMapping("main")
    public ModelAndView index(){
        return new Result().jumpPage(MODULE_PATH+"main");
    }

    /**
     * Describe: 获取房源列表数据
     * Param ModelAndView
     * Return 房源列表数据
     * */
    @GetMapping("data")
    public Result data(Hotel hotel){
        return hotelFeign.data(hotel);
    }

    @GetMapping("add")
    public ModelAndView add(){
        return  new Result().jumpPage(MODULE_PATH+"add");
    }


    @GetMapping("selectParent")
    public JSONObject selectParent(Hotel hotel){
        Result<List<Hotel>> data = hotelFeign.data(hotel);
        Hotel basePower = new Hotel();
        basePower.setHotelName("顶级权限");
        basePower.setId(0);
        basePower.setParentId(-1);
        data.getData().add(basePower);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();

        jsonObject1.put("code",200);
        jsonObject1.put("message","默认");

        jsonObject.put("status",jsonObject1);
        jsonObject.put("data",data.getData());
        return jsonObject;
    }

    @PostMapping("save")
    public Result save(@RequestBody Hotel hotel){

        if(hotel.getParentId() == null){
            return new Result(false, StatusCode.ERROR,"请选择上级菜单");
        }


        File file = new File();
        file.setFileName(hotel.getImageUrl());
        List<File> download = fileFeign.findFile(file);

        if(download.get(0).getId() == null){
            return new Result(false,StatusCode.ERROR,"无此图片");
        }
        String newUrl = "http://118.25.242.174:8080/group1/"+hotel.getImageUrl()+"."+download.get(0).getFileType();

        hotel.setScore("4.5");
        hotel.setDistance(2.0);
        hotel.setImageUrl(newUrl);
        Integer hotelId = hotelFeign.addAll(hotel);

        HotelFile hotelFile = new HotelFile();
        hotelFile.setFileId(download.get(0).getId());
        hotelFile.setHotelId(hotelId);

        Result result = hotelFeign.addAll(hotelFile);

        return result;
    }

    @GetMapping("edit")
    public ModelAndView edit(@RequestParam Integer id, Model model){
//        System.out.println(id);
        model.addAttribute("sysPower",hotelFeign.findById(id).getData());
        return new Result().jumpPage(MODULE_PATH + "edit");
    }

    @PutMapping("update")
    public Result update(@RequestBody Hotel hotel){
        if(hotel.getParentId() == null){
            return new Result(false,StatusCode.ERROR,"请选择上级菜单");
        }

        File file = new File();
        file.setFileName(hotel.getImageUrl());
        List<File> download = fileFeign.findFile(file);

        if(download.size() == 0){
            return new Result(false,StatusCode.ERROR,"无此图片");
        }
        String newUrl = "http://118.25.242.174:8080/group1/"+hotel.getImageUrl()+"."+download.get(0).getFileType();

        hotel.setImageUrl(newUrl);
        Result result = hotelFeign.update(hotel);
        return result;
    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id){
        Hotel hotel = new Hotel();
        hotel.setParentId(id);
        Result<List<Hotel>> result = hotelFeign.data(hotel);
        if(result.getData().size() != 0){
            return new Result(false,StatusCode.ERROR,"请先删除子集");
        }

        Result result1 = hotelFeign.remove(id);
        return result1;
    }
}
