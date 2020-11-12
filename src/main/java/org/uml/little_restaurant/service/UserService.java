package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.uml.little_restaurant.pojo.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;



    //用户注册
    public Map<String,Object> registe(User user){

        Map<String,Object> map = new HashMap<>();
        long check= (long) jdbcTemplate.queryForMap("select count(*) a from user where tele=?",user.getTele()).get("a");
        if(check==0) {
            jdbcTemplate.update("insert into user(tele,pwd,name,address) values(?,?,?,?)",user.getTele(),user.getPwd(),user.getName(),user.getAddress());
            map.put("status",200);
        }
        else{
            map.put("msg","该手机号已被注册！");
        }
        return map;
    }

    //用户登录
    public Long login(User user){
        Integer uid = null;
        try{
            uid = (Integer) jdbcTemplate.queryForMap("select uid from user where tele=? and pwd=?",user.getTele(),user.getPwd()).get("uid");
            return uid.longValue();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
