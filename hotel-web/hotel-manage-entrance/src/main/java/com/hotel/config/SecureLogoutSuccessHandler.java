package com.hotel.config;

import com.alibaba.fastjson.JSON;
import entity.CookieUtil;
import entity.Result;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import string.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static servlet.ServletUtil.getResponse;

/**
 * Describe: 自定义 Security 注销成功处理类
 * */
@Component
public class SecureLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String[] cookies = {"username","Authorization"} ;
        Map<String, String> cookie = CookieUtil.readCookie(httpServletRequest, cookies);
        String username = cookie.get("username");

        redisTemplate.delete(username +":access_token");
        redisTemplate.delete(username +":refresh_token");
        SecurityContextHolder.clearContext();
        if(StringUtil.isNotEmpty(cookie.get("Authorization"))){
            Cookie authorization = new Cookie("Authorization", "");
            authorization.setDomain("cfoj55pn.dongtaiyuming.net");
            httpServletResponse.addCookie(authorization);
        }
        Result result = new Result(true, 200, "注销成功");
        HttpServletResponse response = getResponse();
        response.setHeader("Content-type","application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(result));

    }
}
