package com.leyou.client;

import com.leyou.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserClientServer {
    /*
     * 根据用户名和密码查询用户
     * */
    @GetMapping("/query")
    public User query(@RequestParam("username") String username,@RequestParam("password") String password);
}
