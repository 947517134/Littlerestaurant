package org.uml.little_restaurant.pojo;

public class Table {

    private Integer tid;
    private Integer tcap;
    private Integer tstate;

    public Table() {
    }

    public Table(Integer tid, Integer tcap, Integer tstate) {
        this.tid = tid;
        this.tcap = tcap;
        this.tstate = tstate;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getTcap() {
        return tcap;
    }

    public void setTcap(Integer tcap) {
        this.tcap = tcap;
    }

    public Integer getTstate() {
        return tstate;
    }

    public void setTstate(Integer tstate) {
        this.tstate = tstate;
    }
}
