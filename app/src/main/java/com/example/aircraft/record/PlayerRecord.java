package com.example.aircraft.record;

import java.util.Date;

public class PlayerRecord {
    private String userName;
    private int score;
    private Date date;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "userName='" + userName + '\'' +
                ", score=" + score +
                ", date=" + date +
                '}';
    }

    public PlayerRecord(String userName, int score) {
        this.userName = userName;
        this.score = score;
        this.date = new Date();
    }
}

