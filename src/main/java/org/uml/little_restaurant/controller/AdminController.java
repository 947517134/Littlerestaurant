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
import java.util.List;
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
    //(分页)获取所有餐桌
    @GetMapping("/admin/getTableByPage")
    public Map<String,Object> getTableByPage(@RequestParam("page")Integer page,
                                             @RequestParam("limit")Integer limit){
        return restaurantService.getTableByPage(page,limit);
    }

    //删除餐桌
    @PostMapping("/admin/deleteTable")
    public void deleteTable(@RequestParam("tid")Integer tid){
        restaurantService.deleteTable(tid);
    }

    //编辑餐桌
    @PostMapping("/admin/editTable")
    public void editTable(@RequestParam("tid")Integer tid,
                          @RequestParam("tcap")Integer tcap){
        restaurantService.editTable(tid,tcap);
    }

    //添加餐桌
    @PostMapping("/admin/addTable")
    public void addTable(@RequestParam("tcap")Integer tcap){
        restaurantService.addTable(tcap);
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
    //(分页)查看所有用户 -- 管理员只能查看用户 不能增删改
    @GetMapping("/admin/getUsers")
    public Map<String,Object> getUsers(@RequestParam("page")Integer page,
                                       @RequestParam("limit")Integer limit){
        return adminService.getUsers(page, limit);
    }
    //(分页)获取所有订单 - 管理员只能查看订单信息 不能增删改
    @GetMapping("/admin/scanOrders")
    public Map<String,Object> getOrdersInfo(@RequestParam("page")Integer page,
                                            @RequestParam("limit")Integer limit){
        return adminService.getOrdersInfo(page,limit);
    }

    //查看某一订单所有的菜品
    @GetMapping("/admin/scanOrder")
    public List<Map<String,Object>> scanOrder(@RequestParam("oid")String oid){
        return adminService.scanOrder(oid);
    }


}
