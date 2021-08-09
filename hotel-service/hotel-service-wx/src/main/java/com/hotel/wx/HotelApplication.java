package com.hotel.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.hotel.wx","exception"})
//开启eureka服务端
@EnableEurekaClient
@MapperScan(basePackages = {"com.hotel.wx.dao"})
public class HotelApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class,args);
    }
}
