package com.leyou.controller;

import com.leyou.common.JwtUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.Sku;
import com.leyou.pojo.SkuVo;
import com.leyou.utils.JsonUtils;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CartController {

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public String prefix="ly_carts";

    public String prefixSelected="ly_cartsSelected";

    /**
     * 添加到购物车
     * @param token
     * @param skuVo
     */
    @RequestMapping("add")
    public void add(@CookieValue("token")String token, @RequestBody SkuVo skuVo){

        //获取用户信息
        UserInfo userInfoToken = this.getUserInfoToken(token);


        if(userInfoToken!=null){
            //添加购物车
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix+userInfoToken.getId());

            //判断存在
            if(hashOps.hasKey(skuVo.getId()+"")){
                //从redis中查询的skuvo
                SkuVo redisSkuVo = JsonUtils.parse(hashOps.get(skuVo.getId()+"").toString(), SkuVo.class);

                //修改购物车的商品数量
                redisSkuVo.setNum(redisSkuVo.getNum()+skuVo.getNum());

                //重新添加到redis
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(redisSkuVo));

                stringRedisTemplate.boundValueOps(prefixSelected+ skuVo.getId()).set(JsonUtils.serialize(redisSkuVo));

            }else {
                //第一次添加到redis
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(skuVo));

                stringRedisTemplate.boundValueOps(prefixSelected+ skuVo.getId()).set(JsonUtils.serialize(skuVo));
            }
        }


    }

    @RequestMapping("selectedSkuVo")
    public SkuVo selectedSkuVo(@CookieValue("token")String token){

        //获取用户信息
        UserInfo userInfoToken = this.getUserInfoToken(token);

        BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix+userInfoToken.getId());

        String s= stringRedisTemplate.boundValueOps(prefixSelected+userInfoToken.getId()).get();


        SkuVo skuVo = JsonUtils.parse(s, SkuVo.class);

        return skuVo;

    }


    /**
     * 修改购物车
     * @param token
     * @param skuVo
     */
    @RequestMapping("update")
    public void update(@CookieValue("token")String token, @RequestBody SkuVo skuVo){
        //获取用户信息
        UserInfo userInfoToken = this.getUserInfoToken(token);


        if(userInfoToken!=null){
            //添加购物车
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix+userInfoToken.getId());

            //判断存在
            if(hashOps.hasKey(skuVo.getId()+"")){
                //从redis中查询的skuvo
                SkuVo redisSkuVo = JsonUtils.parse(hashOps.get(skuVo.getId()+"").toString(), SkuVo.class);

                //修改购物车的商品数量
                redisSkuVo.setNum(redisSkuVo.getNum()+skuVo.getNum());

                //重新添加到redis
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(redisSkuVo));

            }else {
                //第一次添加到redis
                hashOps.put(skuVo.getId()+"",JsonUtils.serialize(skuVo));
            }
        }

    }

    /**
     * 删除购物车
     * @param token
     * @param id
     */
    @RequestMapping("delete")
    public void delete(@CookieValue("token")String token, @RequestParam("id")Long id){
        //获取用户信息
        UserInfo userInfoToken = this.getUserInfoToken(token);


        if(userInfoToken!=null){

            //购物车
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix+userInfoToken.getId());

            hashOps.delete(id);
        }

    }

    @RequestMapping("query")
    public List<SkuVo> query(@CookieValue("token")String token){
        //获取用户信息
        UserInfo userInfoToken = this.getUserInfoToken(token);
        List<SkuVo> list = new ArrayList<>();

        if(userInfoToken!=null){
            //购物车
            BoundHashOperations<String, Object, Object> hashOps = stringRedisTemplate.boundHashOps(prefix+userInfoToken.getId());

            Map<Object, Object> map = hashOps.entries();


            map.keySet().forEach(key->{

                SkuVo skuVo = JsonUtils.parse(hashOps.get(key).toString(), SkuVo.class);
                list.add(skuVo);
            });
        }

        return list;


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
