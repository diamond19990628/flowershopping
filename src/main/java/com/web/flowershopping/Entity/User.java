package com.web.flowershopping.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private int user_id;
    private String openid;
    private String nickName;
    private String tel;
    private LocalDateTime birthday;
    private String create_datetime;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", openid='" + openid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", tel='" + tel + '\'' +
                ", birthday=" + birthday +
                ", create_datetime='" + create_datetime + '\'' +
                '}';
    }
}