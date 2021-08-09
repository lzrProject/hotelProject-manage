package com.hotel.filter;

import com.hotel.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

/***
 * 全局过滤器
 * 实现用户权限鉴别（校验)
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    //令牌头名字
    private static final String AUTHORIZE_TOKEN = "Authorization";

    //用户登录
    private static final String USER_LOGIN_URL = "/oauth/login";

    @Autowired
    private RedisTemplate redisTemplate;

    //全局拦截
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();


        //用户登录或是一些不需要权限的请求，直接放行
        String uri = request.getURI().getPath();
        if(URLFilter.hasAuthorize(uri)){
            return chain.filter(exchange);
        }


        //获取用户信息
        //1)头文件
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        boolean hasToken = true;

        //2)参数获取令牌
        if(StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasToken = false;
        }

        //3)Cookie中
        if(StringUtils.isEmpty(token)){
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
//            HttpCookie httpCookie = request.getCookies().getFirst("JSESSIONID");
            if(httpCookie != null){
                token = httpCookie.getValue();
            }
        }

        //如果没有令牌，则拦截
//        if(StringUtils.isEmpty(token)){
//            //设置没有权限状态码 401
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            //响应空数据
//            return response.setComplete();
//        }

        /*//如果有令牌，则校验令牌是否有效
        try {
            JwtUtil.parseJWT(token);
            if(hasToken){
                //将token存入头文件中
                request.mutate().header(AUTHORIZE_TOKEN,token);
            }
        } catch (Exception e) {
            //无效则拦截
            //设置没有权限状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return response.setComplete();
        }*/

        //如果令牌为空，不允许访问，直接拦截(令牌前面加bearer)
        if(StringUtils.isEmpty(token)){
            //无效则拦截
            //设置没有权限状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
//            return response.setComplete();
            return needAuthorization("http://"+request.getURI().getAuthority()+USER_LOGIN_URL,exchange);
        }



        if(!hasToken){
            if(!token.startsWith("bearer ") && !token.startsWith("Bearer ")){
                token = "bearer "+token;
            }

            //将token存入头文件中
            request.mutate().header(AUTHORIZE_TOKEN,token);
        }


        return chain.filter(exchange);
    }

    /**
     * 响应设置
     * @param url
     * @param exchange
     * @return
     */
    public Mono<Void> needAuthorization(String url, ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        response.getHeaders().set("Location",url);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        //值越小越先执行
        return 0;
    }
}
