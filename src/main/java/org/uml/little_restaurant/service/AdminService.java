package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhiyu
 * @date 2020/11/11 12:46
 */
@Service
public class AdminService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //管理员登录
    public Long adminLogin(String username, String password){
        return (Long) jdbcTemplate.queryForMap("select count(*) a from admin where username=? and password=?",username,password).get("a");
    }

    public Map<String,Object> getUsers(Integer page, Integer limit){
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> users = jdbcTemplate.queryForList("select uid,tele,name,address from user limit ?,?",(page-1)*limit,limit);
        Long count = (Long) jdbcTemplate.queryForMap("select count(*) count from user").get("count");
        map.put("data",users);
        map.put("count",count);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }
}
