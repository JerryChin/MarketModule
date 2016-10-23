package com.hc.library.pojo;

import java.util.Date;

public class User {
    private Integer id;

    private String nickname;

    private String phone;

    private String account;

    private String password;

    private Integer type;

    private String icon;

    private Integer attentionnumber;

    private Long registertime;

    private String qrcode;

    private String signature;

    private Integer dynamicstruts;

    private Integer score;

    private Integer pushOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getAttentionnumber() {
        return attentionnumber;
    }

    public void setAttentionnumber(Integer attentionnumber) {
        this.attentionnumber = attentionnumber;
    }

    public Long getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Long registertime) {
        this.registertime = registertime;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    public Integer getDynamicstruts() {
        return dynamicstruts;
    }

    public void setDynamicstruts(Integer dynamicstruts) {
        this.dynamicstruts = dynamicstruts;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getPushOn() {
        return pushOn;
    }

    public void setPushOn(Integer pushOn) {
        this.pushOn = pushOn;
    }
}