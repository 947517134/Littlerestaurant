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


}
