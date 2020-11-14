package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.uml.little_restaurant.pojo.User;

import javax.servlet.http.HttpServletRequest;
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

    //获取用户信息
    public Map<String,Object> getUserInfo(HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("user");
        String tele = u.getTele();
        return jdbcTemplate.queryForMap("select tele,name,address from user where tele=?", tele);
    }

    //编辑用户信息
    public void editUser(String tele, String name, String address){
        jdbcTemplate.update("update user set name=?,address=? where tele=?",name,address,tele);
    }



    public void setTid(@RequestParam("type")Integer type, HttpServletRequest request){
        Map<String ,Object> map = new HashMap<>();
        map.put("type",type);
        if(type==1){                                            //前端提交type为1时按照该方法set外卖订单信息
            String name = request.getParameter("name");
            String tele = request.getParameter("tele");
            String address = request.getParameter("address");
            map.put("name",name);
            map.put("tele",tele);
            map.put("address",address);
        }
        else if(type==0){                                       //堂食订单待完善

        }
        request.getSession().setAttribute("map",map);
    }


    public Map<String,Object> getPreOrderInfo(HttpServletRequest request){
        return (Map<String, Object>) request.getSession().getAttribute("map");
    }

}
