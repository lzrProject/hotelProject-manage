package com.hotel.houses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = {"com.hotel.houses","exception"})
//开启eureka服务端
@EnableEurekaClient
@MapperScan(basePackages = {"com.hotel.houses.dao"})
public class HousesApplication {
    public static void main(String[] args) {
        SpringApplication.run(HousesApplication.class,args);
    }
}
