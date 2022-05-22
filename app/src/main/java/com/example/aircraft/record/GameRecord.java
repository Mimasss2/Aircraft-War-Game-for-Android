package com.example.aircraft.record;

import java.util.Date;

public class GameRecord {
    private String userName;
    private int score;

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

    private Date date;

    @Override
    public String toString() {
        return "GameRecord{" +
                "userName='" + userName + '\'' +
                ", score=" + score +
                ", date=" + date +
                '}';
    }

    public GameRecord(String userName, int score) {
        this.userName = userName;
        this.score = score;
        this.date = new Date();
    }
}

