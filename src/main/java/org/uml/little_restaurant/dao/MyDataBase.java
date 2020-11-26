package org.uml.little_restaurant.dao;

import org.apache.ibatis.annotations.*;
import org.uml.little_restaurant.pojo.Emp;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyDataBase {
    //分页获取员工
    @Select("select eid,ename,egender,(case etype when 0 then '服务员' else '外卖员' end) etype,edate from emp limit ${(page-1)*limit},#{limit}")
    List<Map<String, Object>> getEmpByPage(Integer page, Integer limit);

    //获取员工总数
    @Select("select count(*) from emp")
    Long getEmpCount();

    //删除员工
    @Delete("delete from emp where eid=#{eid}")
    void deleteEmp(Integer eid);

    //编辑员工
    @Update("update emp set ename=#{ename},egender=#{egender},etype=#{etype},edate=#{edate} where eid=#{eid}")
    void editEmp(Emp emp);

    //增加员工
    @Insert("insert into emp(ename,egender,etype,edate) values(#{ename},#{egender},#{etype},#{edate})")
    void addEmp(Emp emp);

    //分页获取餐桌
    @Select("select tid,tcap,(case tstate when 0 then '不可用' else '可用' end) tstate from tables limit ${(page-1)*limit},#{limit}")
    List<Map<String, Object>> getTableByPage(Integer page, Integer limit);

    //获取餐桌总数
    @Select("select count(*) from tables")
    Long getTableCount();

    //添加餐桌
    @Insert("insert into tables(tcap,tstate) values(#{tcap},1)")
    void addTable(Integer tcap);

    //删除餐桌
    @Delete("delete from tables where tid=#{tid}")
    void deleteTable(Integer tid);

    //编辑餐桌
    @Update("update tables set tcap=#{tcap} where tid=#{tid}")
    void editTable(Integer tid, Integer tcap);
    //分页获取菜品
    @Select("select * from dish limit ${(page-1)*limit},#{limit}")
    List<Map<String, Object>> getDishByPage(Integer page, Integer limit);

    //获取菜品总数
    @Select("select count(*) from dish")
    Long getDishCount();

    //删除菜品
    @Delete("delete from dish where did=#{did}")
    void deleteDish(Integer did);

    //编辑菜品
    @Update("update dish set dname=#{dname},dprice=#{dprice} where did=#{did}")
    void editDish(Integer did, String dname, Double dprice);

    //添加菜品
    @Insert("insert into dish(dname,dprice) values(#{dname},#{dprice})")
    void addDish(String dname, Double dprice);

    //得到所有菜品 -- 给用户点餐时用
    @Select("select * from dish")
    List<Map<String, Object>> getDishes();
}
