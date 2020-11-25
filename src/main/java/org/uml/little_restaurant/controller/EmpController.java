package org.uml.little_restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uml.little_restaurant.pojo.Emp;
import org.uml.little_restaurant.service.EmpService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class EmpController {

    @Autowired
    EmpService empService;
    //员工登录
    @PostMapping("/empLogin")
    public int empLogin(@RequestParam("eid")Integer eid, HttpServletRequest request){
        Emp emp = empService.empLogin(eid);
        if(emp!=null){
            request.getSession().setAttribute("emp",emp);
            return 200;
        }
        return 404;
    }

    //员工注销
    @GetMapping("/emp/logout")
    public void empLogout(HttpServletRequest request){
        request.getSession().removeAttribute("emp");
        request.getSession().invalidate();
    }

    //获取员工信息
    @GetMapping("/emp/getEmpInfo")
    public Emp getEmpInfo(HttpServletRequest request){
        return (Emp) request.getSession().getAttribute("emp");
    }

    //前端方法，根据员工类型，决定渲染表格的哪些字段
    @GetMapping("/emp/howToRenderTable")
    public List<Map<String,Object>> howToRenderTable(HttpServletRequest request){
        return empService.howToRenderTable(request);
    }

    //(分页)获取所有可接的订单
    @GetMapping("/emp/scanOrders")
    public Map<String,Object> scanOrders(@RequestParam("page")Integer page,
                                               @RequestParam("limit")Integer limit,
                                               HttpServletRequest request){
        return empService.scanOrders(page, limit, request);
    }

    //获取某个订单的所有菜品
    @GetMapping("/emp/scanOrder")
    public List<Map<String,Object>> scanOrder(@RequestParam("oid")String oid){
        return empService.scanOrder(oid);
    }


    //接单 -- 订单状态变为已分类已分配
    @PostMapping("/emp/acceptOrder")
    public Map<String,Object> acceptOrder(@RequestParam("oid")String oid,
                                          HttpServletRequest request){
        return empService.acceptOrder(oid, request);
    }

    //前端方法 （根据员工类型分别渲染员工服务的订单界面的表格)
    @GetMapping("/emp/howToRenderTableMy")
    public List<Map<String,Object>> howToRenderTableMy(HttpServletRequest request){
        return empService.howToRenderTableMy(request);
    }

    //(分页)获取员工服务的订单
    @GetMapping("/emp/scanMyOrders")
    public Map<String,Object> scanMyOrders(@RequestParam("page")Integer page,
                                         @RequestParam("limit")Integer limit,
                                         HttpServletRequest request){
        return empService.scanMyOrders(page, limit, request);
    }

    //完结订单 -- 订单状态变为已完结
    @PostMapping("/emp/endOrder")
    public Map<String,Object> endOrder(@RequestParam("oid")String oid,
                                       HttpServletRequest request) {
        return empService.endOrder(oid, request);
    }

}
