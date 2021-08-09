package com.hotel.oauth.controller.common;

import com.wf.captcha.utils.CaptchaUtil;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Describe: 验证码控制器
 * */
@RestController
@RequestMapping("system/captcha")
public class CaptchaController {
    static{
        System.setProperty("java.awt.headless", "true");
    }

    /**
     * 验证码生成
     * @param request 请求报文
     * @param response 响应报文
     * */
    @RequestMapping("generate")
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    /**
     * 异步验证
     * @param request 请求报文
     * @param captcha 验证码
     * @return 验证结果
     * */
    @RequestMapping("verify")
    public Result verify(HttpServletRequest request, String captcha){
        if(CaptchaUtil.ver(captcha,request)){
            return new Result(true, StatusCode.OK,"验证成功");
        }
        return new Result(false,StatusCode.ERROR,"验证失败");
    }

}
