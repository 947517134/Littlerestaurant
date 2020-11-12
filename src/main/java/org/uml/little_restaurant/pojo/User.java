package org.uml.little_restaurant.pojo;

public class User {

    private Integer uid;
    private String tele;
    private String pwd;
    private String name;
    private String address;

    public User() {
    }

    public User(String tele, String pwd) {
        this.tele = tele;
        this.pwd = pwd;
    }

    public User(String tele, String pwd, String name, String address) {
        this.tele = tele;
        this.pwd = pwd;
        this.name = name;
        this.address = address;
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

}
