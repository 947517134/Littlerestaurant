package org.uml.little_restaurant.pojo;

public class Emp {

    private Integer eid;
    private String ename;
    private String egender;
    private Integer etype;
    private String edate;

    public Emp() {
    }

    public Emp(Integer eid, String ename, String egender, Integer etype, String edate) {
        this.eid = eid;
        this.ename = ename;
        this.egender = egender;
        this.etype = etype;
        this.edate = edate;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEgender() {
        return egender;
    }

    public void setEgender(String egender) {
        this.egender = egender;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

}
