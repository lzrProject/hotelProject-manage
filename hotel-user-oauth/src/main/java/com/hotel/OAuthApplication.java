package com.hotel;

import com.hotel.oauth.interceptor.TokenRequestInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

@ServletComponentScan
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.hotel.user","com.hotel.index"})
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class,args);
    }

    /***
     * 创建拦截器Bean对象
     * @return
     */
    @Bean
    public TokenRequestInterceptor feignInterceptor(){
        return new TokenRequestInterceptor();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
