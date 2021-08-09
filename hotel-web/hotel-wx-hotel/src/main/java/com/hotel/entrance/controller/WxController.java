package com.hotel.entrance.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.wx.feign.CustomerFeign;
import com.hotel.wx.pojo.Customer;
import entity.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxController {

    @Autowired
    private CustomerFeign customerFeign;

    @GetMapping("login")
    public Map<String, Object> wxLogin(@RequestParam("code") String code){
        System.out.println(code);
        Map map = new HashMap();
        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }


        String appId = "wx7d52c7778d30a6ca";
        String secret = "9dc8e349426399ee7d346cc24b2fccbb";

//        String appId = "wx9ea6effaba497687";
//        String secret = "2e59272e4f60f9b4943f9d7bded4ca76";

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        boolean errcode = StringUtils.contains(forObject, "errcode");

        JSONObject obj= JSON.parseObject(forObject);
        if(errcode){
            //校验出错
            map.put("status",500);
            map.put("msg","登录失败");
            return map;
        }

        Result<List<Customer>> openid = customerFeign.findByOpenId(obj.get("openid").toString());
        if(openid.getData().size() == 0){
            Customer customer = new Customer();
            customer.setOpenId(obj.get("openid").toString());
            customerFeign.addOpenId(customer);
        }

        map.put("openid",obj.get("openid"));
        map.put("session_key",obj.get("session_key"));

        return map;


    }
}
