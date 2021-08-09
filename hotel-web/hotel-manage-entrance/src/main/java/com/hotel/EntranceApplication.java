package com.hotel;

import com.hotel.interceptor.FeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.hotel.index","com.hotel.user","com.hotel.wx"})
public class EntranceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EntranceApplication.class,args);
    }

    /***
     * 创建拦截器Bean对象
     * @return
     */
    @Bean
    public FeignInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
