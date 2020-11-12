package org.uml.little_restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
//import org.uml.little_restaurant.service.Restaurant;
import org.uml.little_restaurant.pojo.User;
import org.uml.little_restaurant.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    //用户注册
    @PostMapping("/userRegiste")
    public Map<String, Object> userRegiste(@RequestParam("tele") String tele,
                                           @RequestParam("pwd") String pwd,
                                           @RequestParam("name") String name,
                                           @RequestParam("address") String address) {

        return userService.registe(new User(tele,pwd,name,address));
    }

    //用户登录
    @PostMapping("/userLogin")
    public Object userLogin(@RequestParam("tele") String tele,
                            @RequestParam("pwd") String pwd,
                            HttpServletRequest request) {
        Long uid = userService.login(new User(tele,pwd));        //传到service层的user，这里没有传参，相当于把前端获取端数据封装成全局变量供方法调用
        System.out.println(uid);        //把service里封装的数据打印
        if(uid!=null){
            User user = new User();
            user.setUid(uid.intValue());
            user.setTele(tele);
            request.getSession().setAttribute("user",user);
            return 200;     //如果查到结果不为空，通过session返回数据并返回200
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
