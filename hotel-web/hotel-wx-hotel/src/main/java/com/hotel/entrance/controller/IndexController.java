package com.hotel.entrance.controller;

import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("list")
public class IndexController {



    @PostMapping("data")
    @ResponseBody
    public List data(){
//


        return null;
    }
}
