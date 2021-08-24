package com.hotel.oauth.controller;

import com.hotel.oauth.service.UserLoginService;
import com.hotel.oauth.util.AuthToken;
import com.hotel.oauth.util.CookieUtil;
import com.hotel.user.feign.UserFeign;
import com.hotel.user.pojo.User;
import entity.BCrypt;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/oauth")
public class UserLoginController {
    //客户端ID
    @Value("${auth.clientId}")
    private String clientId;

    //客户端密钥
    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    //Cookie生命周期
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;

    @Value("${token.accessTokenTime}")
    private int accessTokenTime;

    @Value("${token.refreshTokenTime}")
    private int refreshTokenTime;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private StringRedisTemplate redisTemplate;
    /***
     * 登录方法：
     *  参数传递：1.账号 2.密码 3.授权方式 grant_type
     *
     */
    //登录
    @PostMapping(value = "/user")
    public Result login(@RequestParam String username,@RequestParam String password,@RequestParam String rememberPwd) throws Exception {
//        return loginMap.get("username");

//        System.out.println(redisTemplate.opsForValue().get("namee"));
//        redisTemplate.opsForValue().set("name","mm");

        List<User> result = userFeign.findByUsername(username);

        if(result.size() != 0) {


            String a = "0";
            a.equals(result.get(0).getEnable());
            if(result.get(0).getEnable().equals("0")){
                return new Result(false, StatusCode.LOGINERROR, "账号未激活");
            }

            if (BCrypt.checkpw(password, result.get(0).getPassword())) {
                if(onlyLogin(username)){
                    return new Result(false, StatusCode.LOGINERROR, "账号在其他设备登录");
                }
                String grant_type = "password";

                AuthToken authToken = userLoginService.login(username, password, clientId, clientSecret, grant_type);
                if (authToken != null) {
                    saveCookie(authToken.getAccessToken());
                    redisTemplate.opsForValue().set(username+":access_token",authToken.getAccessToken(),accessTokenTime,TimeUnit.MILLISECONDS);
                    redisTemplate.opsForValue().set(username+":refresh_token",authToken.getRefreshToken(),refreshTokenTime,TimeUnit.MILLISECONDS);
                    return new Result(true, StatusCode.OK, "登录成功", authToken);
                }
            }
        }

        return new Result(false, StatusCode.LOGINERROR,"用户名或密码错误，登录失败");
    }

    /***
     * 将令牌存储到cookie
     * @param token
     */
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String cookieDomain = serverWebExchange.getRequest().getURI();
        CookieUtil.addCookie(response,cookieDomain,"/","Authorization",token,cookieMaxAge,false);
    }

    /**
     * 账号只允许登录一台设备
     * @param username
     * @return
     */
    private Boolean onlyLogin(String username){
        Boolean aBoolean = redisTemplate.hasKey(username + ":access_token");
        return aBoolean;
    }

//    private Boolean refresh(String username,String rememberPwd){
//        Boolean aBoolean = redisTemplate.hasKey(username + ":refresh_token");
//        if(rememberPwd == "true" && aBoolean){
//
//        }
//    }

}
