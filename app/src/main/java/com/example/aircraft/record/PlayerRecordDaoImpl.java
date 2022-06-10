package com.example.aircraft.record;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.aircraft.GameHistoryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.*;
import java.util.*;

public class PlayerRecordDaoImpl implements PlayerRecordDao{
    private GameHistoryActivity activity;
    public PlayerRecordDaoImpl(GameHistoryActivity gameHistoryActivity) {
        activity = gameHistoryActivity;
    }
    private String[] tags = {"records_easy","records_medium","records_hard", "records_internet"};

    @Override
    public List<PlayerRecord> getAllRecords(int gameMode) {
        List<PlayerRecord> playerRecords = readFromFile(gameMode);
        if(playerRecords == null) {
            Log.w("playerRecord","read failure");
            return null;
        }
        playerRecords.sort(new Comparator<PlayerRecord>() {
            @Override
            public int compare(PlayerRecord o1, PlayerRecord o2) {
                return o2.getScore() - o1.getScore();
            }
        });

        return playerRecords;
    }

    @Override
    public void addRecord(PlayerRecord playerRecord, int gameMode) {
        List<PlayerRecord> playerRecords = readFromFile(gameMode);
        if(playerRecords == null) {
            playerRecords = new ArrayList<>();
        }
        if(playerRecord != null) {
            playerRecords.add(playerRecord);
        }
        writeToFile(playerRecords,gameMode);
    }

    @Override
    public void deleteRecord(int index, int gameMode) {
        List<PlayerRecord> allRecords = getAllRecords(gameMode);
        PlayerRecord playerRecord = allRecords.get(index);
        Log.i("record","removing record:"+playerRecord.toString());
        allRecords.remove(index);
        writeToFile(allRecords,gameMode);
    }

    @Override
    public void clearAll(int gameMode) {
        List<PlayerRecord> playerRecords = new ArrayList<>();
        writeToFile(playerRecords,gameMode);
    }


    public void writeToFile(List<PlayerRecord> playerRecordList,int gameMode) {
/*        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(paths[gameMode-1]));
            for (PlayerRecord playerRecord : playerRecordList) {
                objectOutputStream.writeObject(playerRecord);
            }
            objectOutputStream.writeObject(null);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        SharedPreferences userPreference = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreference.edit();
        Gson gson = new Gson();
        String strJson = gson.toJson(playerRecordList);
        editor.putString(tags[gameMode-1],strJson);
//        editor.putString("record",playerRecordList.toString());
        editor.commit();

    }

/*    public List<PlayerRecord> readFromFile(int gameMode) {
        List<PlayerRecord> playerRecordList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(paths[gameMode-1]))) {
            PlayerRecord playerRecord = (PlayerRecord) objectInputStream.readObject();
            while(playerRecord != null) {
                playerRecordList.add(playerRecord);
                playerRecord = (PlayerRecord) objectInputStream.readObject();
            }
        } catch (FileNotFoundException e) {
            playerRecordList = null;
        } catch (IOException e) {
            playerRecordList = null;
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            playerRecordList = null;
            e.printStackTrace();
        }
        return playerRecordList;
    }*/

    public List<PlayerRecord> readFromFile(int gameMode) {
        List<PlayerRecord> playerRecordList;
        SharedPreferences userPreference = activity.getPreferences(Context.MODE_PRIVATE);
        String jsonRecordList = userPreference.getString(tags[gameMode-1], "");
        if(jsonRecordList == null) {
            return null;
        }
        Gson gson = new Gson();
        playerRecordList = gson.fromJson(jsonRecordList,new TypeToken<List<PlayerRecord>>(){}.getType());
        return playerRecordList;
    }

/*
    public static void main(String[] args) {
        PlayerRecordDao playerRecordDao = new PlayerRecordDaoImpl();
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
        playerRecordDao.addRecord(new PlayerRecord("fake",100),1);
    }
*/

}
