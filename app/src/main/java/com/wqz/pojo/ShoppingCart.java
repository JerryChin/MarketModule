package com.wqz.pojo;

public class ShoppingCart 
{
    private Integer id;

    private Integer userid;

    private Integer productid;

    private Float price;

    private Integer number;

    public ShoppingCart()
    {
    	
    }
    
    public ShoppingCart(Integer userid,Integer productid,
    		Float price,Integer number)
    {
    	this.userid = userid;
    	this.productid = productid;
    	this.price = price;
    	this.number = number;
    }
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}