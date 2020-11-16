package org.uml.little_restaurant.dao;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyDataBase {

    //得到所有菜品 -- 给用户点餐时用
    @Select("select * from dish")
    List<Map<String, Object>> getDishes();
}
