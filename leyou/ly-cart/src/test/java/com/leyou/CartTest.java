package com.leyou;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){

        BoundHashOperations<String, Object, Object> cart = stringRedisTemplate.boundHashOps("ly_cart");
        cart.put("skuid_12","{\"title\":\"小米手机\"}");

        System.out.println("12121");
    }
}
