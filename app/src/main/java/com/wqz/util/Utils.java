package com.wqz.util;

/**
 * Created by WangQiZhi on 2016/10/22.
 */
public class Utils
{
    public static String ROOT_URL = "http://120.27.110.76:8090/marketbg";

    //-----------------------------Product---------------------------------
    public static String PRODUCT_URL = ROOT_URL + "/product";

    public static String PRODUCT_GET_CAROUSEL = PRODUCT_URL + "/productGetCarousel";//轮播图片

    public static String PRODUCT_GET_SERACH = PRODUCT_URL + "/productGetSerach";//搜索

    public static String PRODUCT_CREATE = PRODUCT_URL + "/productCreate";//创建
    //======================================================================

    //-----------------------------ShoppingCart-----------------------------

    public static String SHOPPING_CART_URL = ROOT_URL + "/shoppingCart";

    public static String SHOPPING_CART_GET = SHOPPING_CART_URL + "/shoppingCartGet";//得到购物车数据

    public static String SHOPPING_CART_CREATE = SHOPPING_CART_URL + "/shoppingCartCreate";//创建购物车数据

    public static String SHOPPING_CART_DELETE = SHOPPING_CART_URL + "/shoppingCartDelete";//删除购物车数据

    //======================================================================

    //--------------------------------User----------------------------------

    public static String USER_URL = ROOT_URL + "/user";

    public static String USER_REGISTER = USER_URL + "/register";//注册

    public static String USER_LOGIN = USER_URL + "/login";//登录

    public static String USER_UPDATE = USER_URL + "/update";//更新

    public static String USER_UCHECK_ACCOUNT = USER_URL + "/checkAccount";//检测账号
    //======================================================================
}
