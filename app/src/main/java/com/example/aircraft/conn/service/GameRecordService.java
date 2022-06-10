package com.example.aircraft.conn.service;

import com.example.aircraft.conn.DAO.GameRecord;

import java.util.List;

public interface GameRecordService {
    boolean insertGameRecord(GameRecord gameRecord,int gameMode);
    List<GameRecord> getPlayerGameRecordsByUserId(int userId, int gameMode);
    List<GameRecord> getAllGameRecords(int gameMode);
    void deleteLocalRecord(int index, int gameMode);
}
