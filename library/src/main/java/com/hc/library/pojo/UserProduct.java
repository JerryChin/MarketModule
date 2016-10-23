package com.hc.library.pojo;

import java.util.Date;

public class UserProduct {
    private Integer id;

    private Integer userid;

    private Integer productid;

    private Date buytime;

    private Date expirationtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Date getBuytime() {
        return buytime;
    }

    public void setBuytime(Date buytime) {
        this.buytime = buytime;
    }

    public Date getExpirationtime() {
        return expirationtime;
    }

    public void setExpirationtime(Date expirationtime) {
        this.expirationtime = expirationtime;
    }
}