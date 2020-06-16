package com.leyou.service;

import com.leyou.dao.UserMapper;
import com.leyou.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    UserMapper userMapper;

    public Boolean check(String data, Integer type) {
        Boolean result = false;
        User user =new User();

        //1：判断校验 type 1：用户名 2：手机
        if(type ==1){
            //用户名
            user.setUsername(data);
        }else if(type ==2){
            //手机
            user.setPhone(data);
        }

        //2：根据校验内容去数据库查询
        User user1 = userMapper.selectOne(user);
        if(user1==null){
            return true;
        }

        //3:用户名存在  false  true  手机号存在false  true
        return result;
    }

    public void insertUser(User user) {

        //生成盐salt
        String salt = UUID.randomUUID().toString().substring(0,32);

        String pwd = this.getPwd(user.getPassword(),salt);
        user.setPassword(pwd);
        user.setCreated(new Date());
        user.setSalt(salt);
        userMapper.insert(user);
    }

    /**
     * 通过原生密码+盐值生成md5加密后的密码
     * @param password
     * @param salt
     * @return
     */
    public String getPwd(String password,String salt){

        //使用md5加密
        String md5Hex = DigestUtils.md5Hex(password+salt);

        return md5Hex;
    }

    public User findUser(String username) {
        User user=new User();
        user.setUsername(username);

       return userMapper.selectOne(user);
    }

    public User findUserByUsernameAndPassword(String username, String password) {
       User user=new User();
       user.setUsername(username);
       user.setPassword(password);
       return userMapper.selectOne(user);
    }
}
