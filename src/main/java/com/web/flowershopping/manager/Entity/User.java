package com.web.flowershopping.manager.Entity;

public class User {

    private int user_id;
    private String openid;
    private String nickName;
    private String create_datetime;

    // getter 和 setter

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
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

    public String getCreateDatetime() {
        return create_datetime;
    }

    public void setCreateDatetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }
    @Override
    public String toString(){
        return "User [openid=" + openid + ", nickName=" + nickName + "]";
    }
}