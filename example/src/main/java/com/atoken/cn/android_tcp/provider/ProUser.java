package com.atoken.cn.android_tcp.provider;

/**
 * Created by Aatoken on 2018/6/12.
 */

public class ProUser {
    public int userId;
    public String userName;
    public boolean userSex;

    public ProUser(int userId, String userName, boolean userSex) {
        this.userId = userId;
        this.userName = userName;
        this.userSex = userSex;
    }

    public ProUser() {
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUserSex() {
        return userSex;
    }

    public void setUserSex(boolean userSex) {
        this.userSex = userSex;
    }

    @Override
    public String toString() {
        return "ProUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSex=" + userSex +
                '}';
    }
}
