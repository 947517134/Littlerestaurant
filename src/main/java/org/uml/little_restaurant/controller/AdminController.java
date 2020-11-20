package org.uml.little_restaurant.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uml.little_restaurant.pojo.Admin;
import org.uml.little_restaurant.service.AdminService;
import org.uml.little_restaurant.service.RestaurantService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 管理员专用 后台管理系统 各项功能均由调用Restaurant类完成
 *
 */
@RestController
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    RestaurantService restaurantService;

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
    //(分页)获取菜品信息
    @GetMapping("/admin/getDishByPage")
    public Map<String,Object> getDishByPage(@RequestParam("page")Integer page,
                                            @RequestParam("limit")Integer limit){
        return restaurantService.getDishByPage(page,limit);
    }

    //删除菜品
    @PostMapping("/admin/deleteDish")
    public void deleteDish(@RequestParam("did")Integer did){
        restaurantService.deleteDish(did);
    }

    //编辑菜品
    @PostMapping("/admin/editDish")
    public void editDish(@RequestParam("did")Integer did,
                         @RequestParam("dname")String dname,
                         @RequestParam("dprice")Double dprice){
        restaurantService.editDish(did,dname,dprice);
    }

    //添加菜品
    @PostMapping("/admin/addDish")
    public void addDish(@RequestParam("dname")String dname,
                        @RequestParam("dprice")Double dprice){
        restaurantService.addDish(dname,dprice);
    }


}
