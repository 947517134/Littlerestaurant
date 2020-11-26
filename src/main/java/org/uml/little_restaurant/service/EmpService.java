package org.uml.little_restaurant.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.uml.little_restaurant.pojo.Emp;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhiyu
 * @date 2020/11/11 12:47
 */
@Service
public class EmpService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //员工登录
    public Emp empLogin(Integer eid){
        Emp emp = null;
        try{
            emp = jdbcTemplate.query("select * from emp where eid=?",new BeanPropertyRowMapper<>(Emp.class),eid).get(0);
            return emp;
        }catch (Exception e){
            return null;
        }
    }

    public List<Map<String,Object>> howToRenderTable(HttpServletRequest request){
        Emp emp = (Emp) request.getSession().getAttribute("emp");
        Integer etype = emp.getEtype();
        List<Map<String,Object>> lists = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("field","oid");
        map.put("title","订单编号");
        map.put("fixed","left");
        lists.add(map);
        map = new HashMap<>();
        map.put("field","uid");
        map.put("title","用户编号");
        lists.add(map);
        map = new HashMap<>();
        map.put("field","starttime");
        map.put("title","开始时间");
        map.put("width",160);
        map.put("sort",true);
        lists.add(map);
        map = new HashMap<>();
        map.put("field","money");
        map.put("title","金额");
        lists.add(map);
        map = new HashMap<>();
        if(etype==0) {
            map.put("field", "tid");
            map.put("title", "餐桌号");
        }
        else if(etype==1){
            map.put("field", "address");
            map.put("title", "地址");
        }
        lists.add(map);
        map = new HashMap<>();
        map.put("field","name");
        map.put("title","用餐人");
        lists.add(map);
        map = new HashMap<>();
        map.put("field","tele");
        map.put("title","手机号");
        lists.add(map);
        map = new HashMap<>();
        map.put("title","操作");
        map.put("width",200);
        map.put("fixed","right");
        map.put("align","center");
        map.put("toolbar","#barDemo");
        lists.add(map);
        return lists;
    }

    public Map<String,Object> scanOrders(Integer page, Integer limit, HttpServletRequest request){
        Emp emp = (Emp) request.getSession().getAttribute("emp");
        Integer etype = emp.getEtype();
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> orders = new ArrayList<>();
        if(etype==0){
            orders = jdbcTemplate
                    .queryForList("select oid,uid,starttime,money,tid,name,tele " +
                            "from orders " +
                            "where type=0 and state=0 " +
                            "order by starttime desc " +
                            "limit ?,?",(page-1)*limit,limit);
        }
        else if(etype==1){
            orders = jdbcTemplate
                    .queryForList("select oid,uid,starttime,money,address,name,tele " +
                            "from orders " +
                            "where type=1 and state=0 " +
                            "order by starttime desc " +
                            "limit ?,?",(page-1)*limit,limit);
        }
        Long count = (Long) jdbcTemplate.
                queryForMap("select count(*) count " +
                        "from orders " +
                        "where type=? and state=0",etype).get("count");
        map.put("data",orders);
        map.put("count",count);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }

    //将菜品(字符串) 转换为数组
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

    public List<Map<String,Object>> scanOrder(String oid){
        Map<String,Object> map = jdbcTemplate.queryForMap("select dids from orders where oid=?",oid);
        String dids = (String) map.get("dids");
        return dids2list(dids);
    }

    public Map<String,Object> acceptOrder(String oid, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Emp emp = (Emp) request.getSession().getAttribute("emp");
        Integer eid = emp.getEid();
        if((Integer) jdbcTemplate.queryForMap("select state from orders where oid =?",oid).get("state")!=0){
            map.put("status",404);
            map.put("msg","对不起，订单已经被别人抢走啦!");
        }
        else if(jdbcTemplate.update("update orders set eid=?,state=1 where oid=?",eid,oid)==1){
            map.put("status",200);
            map.put("msg","接单成功!");
        }
        return map;
    }

    public List<Map<String,Object>> howToRenderTableMy(HttpServletRequest request){
        Emp emp = (Emp) request.getSession().getAttribute("emp");
        Integer etype = emp.getEtype();
        List<Map<String,Object>> lists = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("field","oid");
        map.put("title","订单编号");
        map.put("fixed","left");
        lists.add(map);
        map = new HashMap<>();
        map.put("field","uid");
        map.put("title","用户编号");
        lists.add(map);
        map = new HashMap<>();
        map.put("field","state");
        map.put("title","订单状态");
        lists.add(map);
        map = new HashMap<>();
        map.put("field","starttime");
        map.put("title","开始时间");
        map.put("width",160);
        map.put("sort",true);
        lists.add(map);
        map = new HashMap<>();
        map.put("field","endtime");
        map.put("title","结束时间");
        map.put("width",160);
        map.put("sort",true);
        lists.add(map);
        map = new HashMap<>();
        map.put("field","money");
        map.put("title","金额");
        lists.add(map);
        map = new HashMap<>();
        if(etype==0) {
            map.put("field", "tid");
            map.put("title", "餐桌号");
        }
        else if(etype==1){
            map.put("field", "address");
            map.put("title", "地址");
        }
        lists.add(map);
        map = new HashMap<>();
        map.put("field","name");
        map.put("title","用餐人");
        lists.add(map);
        map = new HashMap<>();
        map.put("field","tele");
        map.put("title","手机号");
        lists.add(map);
        map = new HashMap<>();
        map.put("title","操作");
        map.put("width",200);
        map.put("fixed","right");
        map.put("align","center");
        map.put("toolbar","#barDemo");
        lists.add(map);
        return lists;
    }

    public Map<String,Object> scanMyOrders(Integer page, Integer limit, HttpServletRequest request){
        Emp emp = (Emp) request.getSession().getAttribute("emp");
        Integer etype = emp.getEtype();
        Integer eid = emp.getEid();
        Map<String,Object> map = new HashMap<>();
        List<Map<String, Object>> orders = new ArrayList<>();
        if(etype==0){
            orders = jdbcTemplate
                    .queryForList("select oid,uid,starttime,endtime,money,tid,state,name,tele " +
                            "from orders " +
                            "where eid=? " +
                            "order by starttime desc limit ?,?",eid,(page-1)*limit,limit);
        }
        else if(etype==1){
            orders = jdbcTemplate
                    .queryForList("select oid,uid,starttime,endtime,money,address,state,name,tele " +
                            "from orders " +
                            "where eid=? " +
                            "order by starttime desc limit ?,?",eid,(page-1)*limit,limit);
        }
        Long count = (Long) jdbcTemplate.queryForMap("select count(*) count from orders where eid=?",eid).get("count");
        map.put("data",orders);
        map.put("count",count);
        map.put("code",0);
        map.put("msg","获取数据成功");
        return map;
    }

    public Map<String,Object> endOrder(@RequestParam("oid")String oid,
                                       HttpServletRequest request) {
        String endtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Map<String,Object> map = new HashMap<>();
        map = jdbcTemplate.queryForMap("select tid from orders where oid=?",oid);
        if(map.get("tid")!=null){
            Integer tid = (Integer) map.get("tid");
            jdbcTemplate.update("update tables set tstate=1 where tid=?",tid);
        }
        if (jdbcTemplate.update("update orders set state=2,endtime=? where oid=?",endtime,oid) == 1) {
            map.put("status", 200);
            map.put("msg", "完结成功!");
        }
        return map;
    }
}
