package com.example.aircraft.conn.DAO;

public class User {
    private int userId;
    private String userName;
    private String userPwd;
    private int userCredit;

    public User(int userId, String userName, String userPwd, int userCredit) {
        this.userId = userId;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userCredit = userCredit;
    }

    public User(String userName, String userPwd) {
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public int getUserId() {
        return userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public int getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(int userCredit) {
        this.userCredit = userCredit;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", userCredit=" + userCredit +
                '}';
    }
}
