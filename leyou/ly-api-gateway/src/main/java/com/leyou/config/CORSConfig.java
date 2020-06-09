package com.leyou.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CORSConfig {

    @Bean
    public CorsFilter corsFilter(){

        CorsConfiguration configuration=new CorsConfiguration();

        configuration.addAllowedOrigin("http://manage.leyou.com"); //指定的域名前缀
        configuration.addAllowedOrigin("http://www.leyou.com"); //指定的域名前缀
        configuration.addAllowedHeader("*");//允许所有的请求头
        configuration.addAllowedMethod("*");//运行所有的请求方法
        configuration.setAllowCredentials(true);//允许携带cookies


        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return new CorsFilter(source);
    }
}
