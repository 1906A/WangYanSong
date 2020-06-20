package com.leyou.controller;

import com.leyou.common.JwtUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public String prefix="ly_carts";

    public void add(@CookieValue("token")String token, @RequestBody Sku sku){

        //获取用户信息
        UserInfo userInfoToken = this.getUserInfoToken(token);


        if(userInfoToken!=null){
            //添加购物车
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix+userInfoToken.getId());

            //判断存在
            if(hashOps!=null){
                Sku redisSku = (Sku) hashOps.get(sku.getId().toString());
            }else {
                hashOps.put(sku.getId(),sku);
            }
        }

    }


    /**
     * 登录后根据token解析用户信息
     * @param token
     * @return
     */
    public UserInfo getUserInfoToken(String token){

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());

            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
