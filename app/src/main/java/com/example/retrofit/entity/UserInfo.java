package com.example.retrofit.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInfo {
    @org.greenrobot.greendao.annotation.Id
    long Id;
    String user_name;
    String nick_name;
    boolean sex;

    @Generated(hash = 1452807668)
    public UserInfo(long Id, String user_name, String nick_name, boolean sex) {
        this.Id = Id;
        this.user_name = user_name;
        this.nick_name = nick_name;
        this.sex = sex;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean getSex() {
        return this.sex;
    }
}
