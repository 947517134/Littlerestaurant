package org.uml.little_restaurant.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uml.little_restaurant.pojo.Admin;
import org.uml.little_restaurant.service.AdminService;
import org.uml.little_restaurant.service.RestaurantService;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员专用 后台管理系统 各项功能均由调用Restaurant类完成
 *
 */
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    //管理员登录
    @PostMapping("/adminLogin")
    public int adminLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpServletRequest request){
        if(adminService.adminLogin(username,password)!=0) {
            Admin admin = new Admin(username,password);
            request.getSession().setAttribute("admin",admin);
            return 200;
        }
        return 404;
    }
}
