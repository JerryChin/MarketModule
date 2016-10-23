package com.hc.library.pojo;

public class ShoppingCartEx 
{
	private Integer number;

    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }

	private Integer shoppingcartid;

    public Integer getShoppingcartid()
    {
        return shoppingcartid;
    }

    public void setShoppingcartid(Integer shoppingcartid)
    {
        this.shoppingcartid = shoppingcartid;
    }

	//----------------------
	private Integer id;

    private String name;

    private Float price;

    private Integer type;

    private String imageaddress;

    private String introduce;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImageaddress() {
        return imageaddress;
    }

    public void setImageaddress(String imageaddress) {
        this.imageaddress = imageaddress == null ? null : imageaddress.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }
}
