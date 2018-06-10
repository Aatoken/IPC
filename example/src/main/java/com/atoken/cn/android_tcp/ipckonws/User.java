package com.atoken.cn.android_tcp.ipckonws;

import java.io.Serializable;

/**
 * Created by Aatoken on 2018/6/10.
 */

public class User implements Serializable {

    public int userId;
    public int userAge;
    public String userName;

    public User() {
    }

    public User(int userId, int userAge, String userName) {
        this.userId = userId;
        this.userAge = userAge;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userAge=" + userAge +
                ", userName='" + userName + '\'' +
                '}';
    }
}
