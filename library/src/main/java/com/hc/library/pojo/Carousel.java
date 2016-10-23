package com.hc.library.pojo;

public class Carousel {
    private Integer id;

    private String imageaddress;

    private Integer productid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageaddress() {
        return imageaddress;
    }

    public void setImageaddress(String imageaddress) {
        this.imageaddress = imageaddress == null ? null : imageaddress.trim();
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }
}