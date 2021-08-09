package com.hotel.entrance.controller;

import entity.CookieUtil;
import entity.Result;
import entity.TokenDecode;
import org.bouncycastle.math.raw.Mod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/***
 * 主页跳转
 */
@Controller
@RequestMapping
@CrossOrigin //跨域
public class IndexController {

    @GetMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response,Model model){
        Map<String, String> userInfo = TokenDecode.getUserInfo();
        String username="";
        String avatar="";
        if(userInfo.get("username") == null || userInfo.get("username") == ""){
            String[] cookies = {"username","avatar"} ;
            Map<String, String> cookieNames = CookieUtil.readCookie(request, cookies);
            username = cookieNames.get("username");
            avatar = cookieNames.get("avatar");
        }else{
            username = userInfo.get("username");
            avatar = userInfo.get("avatar");
        }
        model.addAttribute("username",username);
        model.addAttribute("avatar",avatar);
        return "index";
    }

    /**
     * Describe: 获取主页视图
     * Param: ModelAndView
     * Return: 主页视图
     * */
    @GetMapping("/console")
    public ModelAndView home(Model model)
    {
        return new Result().jumpPage("console/console");
    }

    /**
     * 查询消息
     *
     * @return*/
    @ResponseBody
    @GetMapping("system/notice/notice")
    public boolean notice(){

        return false;
    }


}
