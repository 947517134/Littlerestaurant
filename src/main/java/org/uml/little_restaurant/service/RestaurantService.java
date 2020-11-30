package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uml.little_restaurant.dao.MyDataBase;
import org.uml.little_restaurant.pojo.Emp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantService {

    @Autowired
    MyDataBase myDataBase;
    //(分页)获取菜品列表
    public Map<String, Object> getDishByPage(Integer page, Integer limit) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> dishes = myDataBase.getDishByPage(page,limit);
        Long count = myDataBase.getDishCount();
        map.put("data",dishes);
        map.put("count",count);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }
    //(分页)获取餐桌列表
    public Map<String, Object> getTableByPage(Integer page, Integer limit) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> tables = myDataBase.getTableByPage(page,limit);
        Long count = myDataBase.getTableCount();
        map.put("data",tables);
        map.put("count",count);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }

    //添加餐桌
    public void addTable(Integer tcap) {
        myDataBase.addTable(tcap);
    }

    //删除餐桌
    public void deleteTable(Integer tid) {
        myDataBase.deleteTable(tid);
    }

    //编辑餐桌
    public void editTable(Integer tid, Integer tcap) {
        myDataBase.editTable(tid,tcap);
    }

    //删除菜品
    public void deleteDish(Integer did) {
        myDataBase.deleteDish(did);
    }

    //编辑菜品
    public void editDish(Integer did, String dname, Double dprice) {
        myDataBase.editDish(did,dname,dprice);
    }

    //添加菜品
    public void addDish(String dname, Double dprice) {
        myDataBase.addDish(dname,dprice);
    }
    //(分页)获取员工列表
    public Map<String, Object> getEmpByPage(Integer page, Integer limit) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> emps = myDataBase.getEmpByPage(page,limit);
        Long count = myDataBase.getEmpCount();
        map.put("data",emps);
        map.put("count",count);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }

    //删除员工
    public void deleteEmp(Integer eid) {
        myDataBase.deleteEmp(eid);
    }

    //编辑员工
    public void editEmp(Emp emp) {
        myDataBase.editEmp(emp);
    }

    //添加员工
    public void addEmp(Emp emp) {
        myDataBase.addEmp(emp);
    }



    //得到所有菜品
    public List<Map<String, Object>> getDishes() {
        return myDataBase.getDishes();
    }
}
