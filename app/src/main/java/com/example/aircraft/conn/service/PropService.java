package com.example.aircraft.conn.service;

import com.example.aircraft.conn.DAO.Prop;

import java.util.List;

public interface PropService {
    List<Prop> showAllProps();
    boolean insertProp(Prop prop);
    Prop getPropById(int propId);
    boolean buyProp(int userId, int propId);
    List<Prop> showUserProp(int userId);
}
