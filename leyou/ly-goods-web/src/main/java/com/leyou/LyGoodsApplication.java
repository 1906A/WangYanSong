package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient  //开启注册中心组件
@EnableFeignClients  //开启远程调用服务
public class LyGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyGoodsApplication.class,args);

    }
}
