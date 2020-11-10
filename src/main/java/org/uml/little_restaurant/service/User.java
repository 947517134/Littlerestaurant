package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class User {

    @Autowired
    JdbcTemplate jdbcTemplate;


    private Integer uid;
    private String tele;
    private String pwd;
    private String name;
    private String address;

    public User() {
    }

    public User(Integer uid, String tele, String pwd, String name, String address) {
        this.uid = uid;
        this.tele = tele;
        this.pwd = pwd;
        this.name = name;
        this.address = address;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //用户注册
    public Map<String,Object> registe(){

        Map<String,Object> map = new HashMap<>();
        long check= (long) jdbcTemplate.queryForMap("select count(*) a from user where tele=?",tele).get("a");
        if(check==0) {
            jdbcTemplate.update("insert into user(tele,pwd,name,address) values(?,?,?,?)",tele,pwd,name,address);
            map.put("status",200);
        }
        else{
            map.put("msg","该手机号已被注册！");
        }
        return map;
    }

    //用户登录
    public Long login(){
        Integer uid = null;
        try{
            uid = (Integer) jdbcTemplate.queryForMap("select uid from user where tele=? and pwd=?",tele,pwd).get("uid");
            return uid.longValue();     //查到记录后封装到uid返回
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
