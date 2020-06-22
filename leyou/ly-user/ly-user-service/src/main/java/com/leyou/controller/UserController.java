package com.leyou.controller;

import com.leyou.utils.CodeUtils;
import com.leyou.pojo.User;
import com.leyou.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 校验数据是否正确 用户名 手机
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public Boolean checkData(@PathVariable("data") String data,@PathVariable("type") Integer type){
        System.out.println("1111");
        System.out.println("要校验的数据"+data);

       return  userService.check(data,type);

    }


    /**
     * 生产短信验证码
     * @param phone
     */
    @PostMapping("send")
    public void createCode(@RequestParam("phone") String phone) {
        System.out.println("输入的验证码"+phone);

        String code = CodeUtils.messageCode(6);
        System.out.println("生成的验证码"+code);

        //2:使用rabbitmq发送短信 phone、code
        Map<String,String> map =new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);
        amqpTemplate.convertAndSend("sms.changes","sms.send",map);

        //3：发送短信后存放redis 放验证码 code
        stringRedisTemplate.opsForValue().set("lysms_"+phone, code,55, TimeUnit.MINUTES); //放入redis中的code有效期为5分钟
    }

    /**
     *
     * 用户注册
     *
     * @param user
     * @param code
     */
    @PostMapping("/register")
    public void register(@Valid User user, String code){
        System.out.println("用户注册："+user.getUsername()+"code="+code);

        if(user!=null){

            //从redis中获取code
            String redisCode = stringRedisTemplate.opsForValue().get("lysms_" + user.getPhone());
            //1:判断code验证码是否一致
            if(redisCode.equals(code)){
                userService.insertUser(user);
            }
        }
    }

    /*
     * 根据用户名和密码查询用户
     * */
    @GetMapping("/query")
    public User query(@RequestParam("username") String username,@RequestParam("password") String password){

        System.out.println("查询用户："+username+"======"+password);
        //1：根据用户名查询用户信息
        User user  = userService.findusername(username);
        if(user!=null){
            //2：比对密码
            String newPassword = DigestUtils.md5Hex(password + user.getSalt());
            System.out.println("newPassword:"+newPassword);
            System.out.println("password:"+user.getPassword());
            if(user.getPassword().equals(newPassword)){
                return user;
            }
        }
        return null;
    }

//    /*
//     * 根据用户名和密码登录
//     * */
//    @PostMapping("login")
//    public String login(@RequestParam String username,@RequestParam String password){
//        System.out.println("查询用户："+username+"======"+password);
//
//        String result = "1";
//        User user = userService.findUser(username);
//        if(user!=null){
//            String newPassword= DigestUtils.md5Hex(password+user.getSalt());
//            if(newPassword.equals(user.getPassword())){
//                result = "0";
//            }
//        }
//
//        return result;
//    }



}
