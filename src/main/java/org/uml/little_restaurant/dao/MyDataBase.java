package org.uml.little_restaurant.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyDataBase {
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
