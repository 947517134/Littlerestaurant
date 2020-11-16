package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uml.little_restaurant.dao.MyDataBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RestaurantService {

    @Autowired
    MyDataBase myDataBase;

    //获取所有菜品
    public List<Map<String, Object>> getDishes() {
        return myDataBase.getDishes();
    }


}

