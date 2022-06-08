package com.example.aircraft.conn.service.Impl;

import static com.example.aircraft.conn.MysqlConnection.closeConnection;
import static com.example.aircraft.conn.MysqlConnection.closeStatement;
import static com.example.aircraft.conn.MysqlConnection.getConnection;

import com.example.aircraft.conn.DAO.User;
import com.example.aircraft.conn.service.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserServiceImpl implements UserService {
    @Override
    public boolean insertUser(User user) {
        Connection conn = null;
        Statement statement = null;
        int rowsChanged = 0;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "INSERT INTO aircraftwar.user (user_id, user_name,user_pwd, user_credit) " +
                    "VALUES (%s,\'%s\',\'%s\',%s)";
            sql = String.format(sql,user.getUserId(),user.getUserName(),
                    user.getUserPwd(),user.getUserCredit());
            rowsChanged = statement.executeUpdate(sql);
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
    public User selectUserById(int userId) {
        Connection conn = null;
        Statement stmt = null;
        User user = null;
        try{
            conn = getConnection();
            // execute query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_id, user_name, user_pwd, user_credit FROM aircraftwar.user"+
                    " WHERE user_id = %s";
            ResultSet rs = stmt.executeQuery(String.format(sql,userId));
            if(rs.next()){
                String userName = rs.getString("user_name");
                String userPwd = rs.getString("user_pwd");
                int userCredit = rs.getInt("user_credit");
                user = new User(userId,userName,userPwd,userCredit);
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
        return user;
    }

    @Override
    public int getUserIdByName(String userName) {
        Connection conn = null;
        Statement stmt = null;
        int userId = 0;
        try{
            conn = getConnection();
            // execute query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_id FROM aircraftwar.user"+
                    " WHERE user_name = \'%s\'";
            ResultSet rs = stmt.executeQuery(String.format(sql,userName));
            if(rs.next()){
                userId = rs.getInt("user_id");
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
        return userId;
    }

    @Override
    public boolean updateUserCredit(int userId, int updatedCredit) {
        Connection conn = null;
        Statement stmt = null;
        int rowsChanged = 0;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "UPDATE aircraftwar.user t " +
                    "SET t.user_credit = %s " +
                    "WHERE t.user_id = %s";
            rowsChanged = stmt.executeUpdate(String.format(sql,updatedCredit,userId));
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
        return rowsChanged == 1;
    }

    @Override
    public boolean verifyUser(User user) {
        Connection conn = null;
        Statement stmt = null;
        int count = 0;
        try{
            conn = getConnection();
            // execute query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM aircraftwar.user"+
                    " WHERE user_name = \'%s\' AND user_pwd = \'%s\'";
            sql = String.format(sql,user.getUserName(),user.getUserPwd());
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                count++;
            }
            System.out.println("executed sql:"+sql);
            System.out.println(count);
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
        return count == 1;
    }
}
