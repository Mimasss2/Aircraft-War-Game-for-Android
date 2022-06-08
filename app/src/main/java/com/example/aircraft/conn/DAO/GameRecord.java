package com.example.aircraft.conn.DAO;

import java.util.Date;

public class GameRecord {
    private int userId;
    private int gameScore;
    private String userName;
    private String createTime;
    private int gameMode;
    private int recordId;

    public GameRecord(int userId, int gameScore, String userName, int gameMode) {
        this.userId = userId;
        this.gameScore = gameScore;
        this.userName = userName;
        this.gameMode = gameMode;
        this.createTime = String.valueOf(new Date());
    }

    public GameRecord(int userId, int gameScore, String userName, String createTime, int gameMode) {
        this.userId = userId;
        this.gameScore = gameScore;
        this.userName = userName;
        this.createTime = createTime;
        this.gameMode = gameMode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "userId=" + userId +
                ", gameScore=" + gameScore +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", gameMode=" + gameMode +
                ", recordId=" + recordId +
                '}';
    }
}
