package org.uml.little_restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
//import org.uml.little_restaurant.service.Restaurant;
import org.uml.little_restaurant.service.User;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    User user;

    //用户注册
    @PostMapping("/userRegiste")
    public Map<String, Object> userRegiste(@RequestParam("tele") String tele,
                                           @RequestParam("pwd") String pwd,
                                           @RequestParam("name") String name,
                                           @RequestParam("address") String address) {
        user.setTele(tele);
        user.setPwd(pwd);
        user.setName(name);
        user.setAddress(address);
        return user.registe();
    }

    //用户登录
    @PostMapping("/userLogin")
    public Object userLogin(@RequestParam("tele") String tele,
                            @RequestParam("pwd") String pwd,
                            HttpServletRequest request) {
        user.setTele(tele);
        user.setPwd(pwd);
        System.out.println(tele);
        System.out.println(pwd);
        Long uid = user.login();        //传到service层的user，这里没有传参，相当于把前端获取端数据封装成全局变量供方法调用
        System.out.println(uid);        //把service里封装的数据打印
        if (uid != null) {
            User user2 = new User();
            user2.setUid(uid.intValue());
            user2.setTele(tele);
            request.getSession().setAttribute("user", user2);
            user.setTele(null);
            user.setPwd(null);
            user.setName(null);
            user.setAddress(null);
            user.setUid(null);
            return 200;             //如果查到结果不为空，通过session返回数据并返回200
        }
        return null;
    }



    //用户注销
    @GetMapping("/userLogout")
    public void userLogout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
    }






}
