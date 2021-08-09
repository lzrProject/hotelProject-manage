package com.hotel.oauth.interceptor;

import com.hotel.oauth.util.AdminToken;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenRequestInterceptor implements RequestInterceptor {

    /***
     * 拦截器 Feign执行之前拦截，获取登录令牌
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //生成令牌 放入头文件
        String token = AdminToken.adminToken();
        requestTemplate.header("Authorization","bearer "+token);

    }
}
