package com.example.aircraft.record;

import java.util.List;

public interface PlayerRecordDao {
    List<PlayerRecord> getAllRecords(int gameMode);
    void addRecord(PlayerRecord playerRecord, int gameMode);
    void deleteRecord(int index,int gameMode);
    void clearAll(int gameMode);
}
