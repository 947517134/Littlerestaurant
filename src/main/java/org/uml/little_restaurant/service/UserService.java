package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.uml.little_restaurant.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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


    //提交订单信息（目前只有外卖订单信息处理）
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
        else if(type==0){                                       //堂食订单待完善（已完善）
            Integer tid = Integer.parseInt(request.getParameter("tid"));
            Map<String, Object> info = getUserInfo(request);
            map.put("name",info.get("name"));
            map.put("tele",info.get("tele"));
            map.put("tid",tid);
        }
        request.getSession().setAttribute("map",map);
    }


    //获取订单信息
    public Map<String,Object> getPreOrderInfo(HttpServletRequest request){
        return (Map<String, Object>) request.getSession().getAttribute("map");
    }

    //提交生成订单
    public String makeOrder(Double money, String dids, HttpServletRequest request) {
        String oid = UUID.randomUUID().toString();
        User u = (User) request.getSession().getAttribute("user");
        Integer uid = u.getUid();
        String starttime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Map<String, Object> info = getPreOrderInfo(request);
        Integer type = (Integer) info.get("type");
        String name = (String) info.get("name");
        String tele = (String) info.get("tele");
        int a = 0;

        //实现分类处理生成订单（已完善）
        if(type==0){
            Integer tid = (Integer) info.get("tid");
            jdbcTemplate.update("insert into orders(oid,uid,starttime,money,state,type,tid,dids,name,tele) values(?,?,?,?,?,?,?,?,?,?)",
                    oid,uid,starttime,money,0,type,tid,dids,name,tele);
            jdbcTemplate.update("update tables set tstate=0 where tid=?",tid);
        }
        if(type==1){
            String address = (String) info.get("address");
            jdbcTemplate.update("insert into orders(oid,uid,starttime,money,state,type,address,dids,name,tele) values(?,?,?,?,?,?,?,?,?,?)",
                    oid,uid,starttime,money,0,type,address,dids,name,tele);
        }

        return oid;

    }

    public List<Map<String,Object>> getFreeTablesByCap(@RequestParam("cap")Integer cap) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select tid,tstate from tables where tcap=? or tcap=? order by tid asc", cap, cap + 1);
        return maps;
    }

    //获取订单信息并循环遍历输出
    public List<Map<String,Object>> getOrdersInfo(HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("user");
        Integer uid = u.getUid();
        List<Map<String, Object>> orders = jdbcTemplate.queryForList("select * from orders where uid=? order by starttime desc",uid);
        for(Map<String,Object> order:orders){
            String dids= (String) order.get("dids");
            order.put("dishes",dids2list(dids));
            Integer state = (Integer) order.get("state");
            //如果已被接单或已完结，应显示员工编号及姓名
            if(state!=0){
                Integer eid = (Integer) order.get("eid");
                order.putAll(jdbcTemplate.queryForMap("select ename from emp where eid=?",eid));
            }
        }
        return orders;
    }

    //输出订单内容
    public List<Map<String,Object>> dids2list(String dids_str){
        String dids[] = dids_str.split(",");
        Integer did;
        Integer number;
        List<Map<String,Object>> dishes = new ArrayList<>();
        for(String did_str:dids){
            did = Integer.parseInt(did_str.split("-")[0]);
            number = Integer.parseInt(did_str.split("-")[1]);
            Map<String, Object> dish = jdbcTemplate.queryForMap("select dname,dprice from dish where did=?", did);
            dish.put("number",number);
            dishes.add(dish);
        }
        return dishes;
    }

}
