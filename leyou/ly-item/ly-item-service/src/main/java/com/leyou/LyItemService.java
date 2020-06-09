package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient //开启全部配置组件
@MapperScan("com.leyou.dao")
public class LyItemService {

    public static void main(String[] args) {
        SpringApplication.run(LyItemService.class,args);
    }
}
