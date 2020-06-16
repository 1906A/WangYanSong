package com.leyou.controller;

import com.leyou.cilent.UserClient;
import com.leyou.common.CodeUtils;
import com.leyou.common.CookieUtils;
import com.leyou.common.JwtUtils;
import com.leyou.common.UserInfo;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.User;
import com.sun.deploy.net.HttpRequest;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;

@RestController
public class AuthController {

    @Autowired
    UserClient userClient;

    @Autowired
    JwtProperties jwtProperties;



    /*
     * 根据用户名和密码登录
     * */
    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password,HttpServletRequest request,HttpServletResponse response){
        System.out.println("查询用户："+username+"======"+password);

        String result = "1";

        //获取用户信息
        User queryUser = userClient.query(username, password);

            try {
                if(queryUser!=null){
                    //把用户信息根据公钥和私钥生成token
                String token = JwtUtils.generateToken(new UserInfo(queryUser.getId(), queryUser.getUsername())
                        , jwtProperties.getPrivateKey(),jwtProperties.getExpire() * 60);

                //存入cookies
                    CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire()*60);
                }

                result="0";

            } catch (Exception e) {
                e.printStackTrace();
            }

        return result;
    }
}
