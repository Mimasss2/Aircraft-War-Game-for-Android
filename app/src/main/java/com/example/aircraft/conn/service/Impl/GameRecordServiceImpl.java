package com.example.aircraft.conn.service.Impl;

import static com.example.aircraft.conn.MysqlConnection.closeConnection;
import static com.example.aircraft.conn.MysqlConnection.closeStatement;
import static com.example.aircraft.conn.MysqlConnection.getConnection;

import com.example.aircraft.conn.DAO.GameRecord;
import com.example.aircraft.conn.service.GameRecordService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameRecordServiceImpl implements GameRecordService {
    Comparator<GameRecord> comparator = new Comparator<GameRecord> () {
        @Override
        public int compare(GameRecord o1, GameRecord o2) {
            return o2.getGameScore() - o1.getGameScore();
        }
    };
    @Override
    public boolean insertGameRecord(GameRecord gameRecord) {
        Connection conn = null;
        Statement statement = null;
        int rowsChanged = 0;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "INSERT INTO aircraftwar.game_record (user_id, game_score, user_name, create_time, game_mode) " +
                    "VALUES (%s,%s,\'%s\',\'%s\',%s)";
            rowsChanged = statement.executeUpdate(String.format(sql,gameRecord.getUserId(),gameRecord.getGameScore(),
                    gameRecord.getUserName(),gameRecord.getCreateTime(),gameRecord.getGameMode()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // close resources
            try {
                closeStatement(statement);
            } catch (SQLException se2) {
            }// do nothing
            try {
                closeConnection(conn);
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return rowsChanged == 1;
    }

    @Override
    public List<GameRecord> getPlayerGameRecordsByUserId(int userId, int gameMode) {
        Connection conn = null;
        Statement stmt = null;
        GameRecord gameRecord;
        List<GameRecord> gameRecordList = new ArrayList<>();
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT user_id, game_score, user_name, create_time, game_mode FROM aircraftwar.game_record"+
                    " WHERE user_id = %s AND game_mode = %s";
            ResultSet rs = stmt.executeQuery(String.format(sql,userId,gameMode));
            while(rs.next()){
                int gameScore = rs.getInt("game_score");
                String userName = rs.getString("user_name");
                String createTime = rs.getString("create_time");

                gameRecord = new GameRecord(userId,gameScore,userName,createTime,gameMode);
                gameRecordList.add(gameRecord);
            }
            rs.close();
        }catch(SQLException se){
            // deal with JDBC error
            se.printStackTrace();
        }catch(Exception e){
            // deal with Class.forName error
            e.printStackTrace();
        }finally{
            // close resources
            try{
                closeStatement(stmt);
            }catch(SQLException se2){
            }// do nothing
            try{
                closeConnection(conn);
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        gameRecordList.sort(comparator);
        return gameRecordList;
    }

    @Override
    public List<GameRecord> getAllGameRecords(int gameMode) {
        Connection conn = null;
        Statement stmt = null;
        GameRecord gameRecord;
        List<GameRecord> gameRecordList = new ArrayList<>();
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_id, game_score, user_name, create_time, game_mode FROM aircraftwar.game_record"+
                    " WHERE game_mode = %s";
            ResultSet rs = stmt.executeQuery(String.format(sql,gameMode));
            while(rs.next()){
                int gameScore = rs.getInt("game_score");
                String userName = rs.getString("user_name");
                String createTime = rs.getString("create_time");
                int userId = rs.getInt("user_id");
                gameRecord = new GameRecord(userId,gameScore,userName,createTime,gameMode);
                gameRecordList.add(gameRecord);
            }
            rs.close();
        }catch(SQLException se){
            // deal with JDBC error
            se.printStackTrace();
        }catch(Exception e){
            // deal with Class.forName error
            e.printStackTrace();
        }finally{
            // close resources
            try{
                closeStatement(stmt);
            }catch(SQLException se2){
            }// do nothing
            try{
                closeConnection(conn);
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        gameRecordList.sort(comparator);
        return gameRecordList;
    }
}
