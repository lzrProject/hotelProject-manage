package com.hotel.interceptor;

import entity.CookieUtil;
import entity.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OauthInterceptor extends OAuth2AuthenticationEntryPoint {


    //??????????????????
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    //????????????(????????????)
    @Value(value = "${serverName}")
    private String serverName;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        try{
            String message = authException.getLocalizedMessage();
//            String serverName = request.getServerName();
            if(message.contains("Access token expired")){
//                System.out.println(request.getServerPort());
//                System.out.println(request.getServerName());
                String length = "Access token expired: ";
                String token = message.substring(length.length());
                String username = "";
                String id = "";
                String avatar = "";
                if(TokenDecode.dcodeToken(token).get("username") == null || TokenDecode.dcodeToken(token).get("username") == ""){

                    String[] cookies = {"username","avatar","id"} ;
                    Map<String, String> map = CookieUtil.readCookie(request, cookies);
                    username = map.get("username");
                    id = map.get("id");
                    avatar = map.get("avatar");
                }else {
                    username = TokenDecode.dcodeToken(token).get("username");
                    id = TokenDecode.dcodeToken(token).get("id");
                    avatar = TokenDecode.dcodeToken(token).get("avatar");
                }

                //????????????????????????????????????????????????????????????
//                redisTemplate.opsForValue().set("name", "111");
                if(!redisTemplate.hasKey(username+":refresh_token")){
//                    response.sendRedirect("http://"+request.getServerName()+":8001/oauth/login");
                    response.sendRedirect("http://"+serverName+"/oauth/login");
                }
                String refresh_token = redisTemplate.opsForValue().get(username+":refresh_token");
                MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
                bodyMap.add("grant_type", "refresh_token");//?????????????????? ???????????????????????????
                bodyMap.add("refresh_token", refresh_token);
                bodyMap.add("username", "admin");

                //???????????????
                String clientId = TokenDecode.dcodeToken(token).get("client_id");
                String Authorization = "Basic "+ new String(Base64.getEncoder().encode((clientId+":"+clientId).getBytes()),"UTF-8");
                MultiValueMap<String, String> headerMap = new LinkedMultiValueMap();
                headerMap.add("Authorization",Authorization);

                try {
                    //?????????????????????????????????
                    ServiceInstance choose = loadBalancerClient.choose("user-auth");
                    String url = choose.getUri() + "/oauth/token";

                    HttpEntity httpEntity = new HttpEntity(bodyMap,headerMap);

                    ResponseEntity<Map> map = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
                    //????????????????????????
                    Map<String, String> exMap = map.getBody();
                    redisTemplate.opsForValue().set(username+":access_token",(String) exMap.get("access_token"),60*60*2*1000, TimeUnit.MILLISECONDS);

//                    //???token??????????????????
//                    ServerHttpRequest.Builder header = serverWebExchange.getRequest().mutate().header("Authorization",exMap.get("access_token"));
//                    response.setHeader("Authorization", exMap.get("access_token"));
//
//                    (ServletRequestAttributes) RequestContextHolder.setRequestAttributes();
//                    response.setHeader("Authorization", exMap.get("access_token"));
//                    request.getRequestDispatcher(request.getRequestURI()).forward(request, response);
//                    response.sendRedirect("/index");



                    CookieUtil.addCookie(response,serverName,"/","username",username,7200,false);
                    CookieUtil.addCookie(response,serverName,"/","id",id,7200,false);
                    CookieUtil.addCookie(response,serverName,"/","avatar",avatar,7200,false);


                    CookieUtil.addCookie(response,serverName,"/","Authorization",exMap.get("access_token"),7200,false);

//                    response.sendRedirect("http://"+serverName+":8001/index");
                    response.sendRedirect("http://"+serverName+"/index");
                } catch (Exception e) {
//                    e.printStackTrace();
                    // ??????????????????????????????????????????????????????????????????????????????????????????
                    response.setHeader("Content-Type", "application/json;charset=utf-8");
                    response.getWriter().print("{\"code\":411,\"message\":\"??????????????????????????????????????????.\"}");
                    response.getWriter().flush();
                }

            }else{
                super.commence(request,response,authException);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}