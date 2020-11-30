package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public Map<String,Object> getOrdersInfo(Integer page, Integer limit){
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> orders = jdbcTemplate.queryForList("select * from orders order by starttime desc limit ?,?",(page-1)*limit,limit);
        for(Map<String,Object> order:orders){
            Integer state = (Integer) order.get("state");
            if(state!=0){
                Integer eid = (Integer) order.get("eid");
                order.putAll(jdbcTemplate.queryForMap("select ename from emp where eid=?",eid));
            }
        }
        Long count = (Long) jdbcTemplate.queryForMap("select count(*) count from orders").get("count");
        map.put("data",orders);
        map.put("count",count);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }

}
