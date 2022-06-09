package com.example.aircraft.conn.service.Impl;

import static com.example.aircraft.conn.MysqlConnection.closeConnection;
import static com.example.aircraft.conn.MysqlConnection.closeStatement;
import static com.example.aircraft.conn.MysqlConnection.getConnection;

import com.example.aircraft.conn.DAO.Prop;
import com.example.aircraft.conn.DAO.User;
import com.example.aircraft.conn.service.PropService;
import com.example.aircraft.conn.service.UserService;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PropServiceImpl implements PropService {
    @Override
    public List<Prop> showAllProps() {
        Connection conn = null;
        Statement stmt = null;
        Prop prop;
        List<Prop> propList = new ArrayList<>();
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT prop_id, prop_name, prop_credit, prop_description, resource_id FROM aircraftwar.props";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int propId = rs.getInt("prop_id");
                String propName = rs.getString("prop_name");
                int propCredit = rs.getInt("prop_credit");
                String propDescription = rs.getString("prop_description");
                int resourceId = rs.getInt("resource_id");
                prop = new Prop(propId,propName,propCredit,propDescription,resourceId);
                propList.add(prop);
            }
            rs.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
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
        return propList;
    }

    @Override
    public boolean insertProp(Prop prop) {
        Connection conn = null;
        Statement statement = null;
        int rowsChanged = 0;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "INSERT INTO aircraftwar.props (prop_id,prop_name,prop_credit,prop_description,resource_id) " +
                    "VALUES (%s,\'%s\',%s,\'%s\',%s)";
            rowsChanged = statement.executeUpdate(String.format(sql,prop.getPropId(),prop.getPropName(),
                    prop.getPropCredit(),prop.getDescription(),prop.getResourceId()));
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
    public Prop getPropById(int propId) {
        Connection conn = null;
        Statement stmt = null;
        Prop prop = null;
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT prop_id, prop_name, prop_credit, prop_description, resource_id FROM aircraftwar.props WHERE prop_id = %s";
            ResultSet rs = stmt.executeQuery(String.format(sql,propId));
            if(rs.next()){
                String propName = rs.getString("prop_name");
                int propCredit = rs.getInt("prop_credit");
                String propDescription = rs.getString("prop_description");
                int resourceId = rs.getInt("resource_id");
                prop = new Prop(propId,propName,propCredit,propDescription,resourceId);
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
        return prop;
    }

    @Override
    public boolean buyProp(int userId, int propId) {
        UserService userService = new UserServiceImpl();
        User user = userService.selectUserById(userId);
        Prop prop = getPropById(propId);
        if(user == null) {
            return false;
        }
        if(prop == null) {
            return false;
        }
        if(user.getUserCredit() < prop.getPropCredit()) {
            return false;
        }
        boolean insertSuccess = insertPropInstance(userId, propId);
        if(insertSuccess) {
            userService.updateUserCredit(userId, user.getUserCredit()- prop.getPropCredit());
        }
        return insertSuccess;
    }


    public boolean insertPropInstance(int userId, int propId) {
        Connection conn = null;
        Statement statement = null;
        int rowsChanged = 0;
        try {
            conn = getConnection();
            statement = conn.createStatement();
            String sql;
            sql = "INSERT INTO aircraftwar.prop_instance (user_id, prop_id) " +
                    "VALUES (%s,%s)";
            rowsChanged = statement.executeUpdate(String.format(sql,userId,propId));
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
    public List<Prop> showUserProp(int userId) {
        Connection conn = null;
        Statement stmt = null;
        Prop prop;
        List<Prop> propList = new ArrayList<>();
        try{
            conn = getConnection();
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT p.prop_id, p.prop_name, p.prop_credit,p.prop_description, p.resource_id FROM aircraftwar.props p INNER JOIN prop_instance i ON i" +
                    ".prop_id = p" +
                    ".prop_id WHERE i.user_id = %s";
            sql = String.format(sql,userId);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int propId = rs.getInt("p.prop_id");
                String propName = rs.getString("p.prop_name");
                int propCredit = rs.getInt("p.prop_credit");
                String propDescription = rs.getString("p.prop_description");
                int resourceId = rs.getInt("p.resource_id");
                prop = new Prop(propId,propName,propCredit,propDescription,resourceId);
                propList.add(prop);
            }
            rs.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
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
        return propList;
    }

}
