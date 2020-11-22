package org.uml.little_restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.uml.little_restaurant.service.RestaurantService;
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

    @Autowired
    RestaurantService restaurantService;

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
        Long uid = userService.login(new User(tele,pwd));        //最初版本利用全局变量没有传参，更改业务结构后需要传参
        System.out.println(uid);
        if(uid!=null){
            User user = new User();
            user.setUid(uid.intValue());
            user.setTele(tele);
            request.getSession().setAttribute("user",user);
            return 200;                                         //如果查到结果不为空，把数据通过session返回前端，并返回200
        }
        return null;
    }


    //用户注销
    @GetMapping("/userLogout")
    public void userLogout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
    }


    //获取用户信息
    @GetMapping("/getUserInfo")
    public Map<String,Object> getUserInfo(HttpServletRequest request){
        return userService.getUserInfo(request);
    }


    //编辑用户信息
    @PostMapping("/editUser")
    public void editUser(@RequestParam("tele")String tele,
                         @RequestParam("name")String name,
                         @RequestParam("address")String address){
        userService.editUser(tele, name, address);
    }


    //设置订单用餐信息
    @PostMapping("/setPreOrderInfo")
    public void setTid(@RequestParam("type")Integer type, HttpServletRequest request){
        userService.setTid(type, request);
    }

    //获取订单用餐信息
    @GetMapping("/getPreOrderInfo")
    public Map<String,Object> getPreOrderInfo(HttpServletRequest request){
        return userService.getPreOrderInfo(request);
    }

    //获取所有菜品
    @GetMapping("/getDishes")
    public List<Map<String,Object>> getDishes(){
        return restaurantService.getDishes();
    }

    //提交生成订单 -- 需实现订单分类以便于后期接单分配（已完善）
    @PostMapping("/makeOrder")
    public String makeOrder(@RequestParam("money")Double money, @RequestParam("dids")String dids, HttpServletRequest request){
        return userService.makeOrder(money, dids, request);
    }

    //通过用餐人数 获取空闲桌号
    @GetMapping("/getFreeTablesByCap")
    public List<Map<String,Object>> getFreeTablesByCap(@RequestParam("cap")Integer cap) {
        return userService.getFreeTablesByCap(cap);
    }

    //获取用户订单历史信息
    @GetMapping("/getOrdersInfo")
    public List<Map<String,Object>> getOrdersInfo(HttpServletRequest request){
        return userService.getOrdersInfo(request);
    }


}
