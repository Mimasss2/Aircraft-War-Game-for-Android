package com.example.aircraft.conn.service;

import com.example.aircraft.conn.DAO.User;

public interface UserService {
    boolean insertUser(User user);
    User selectUserById(int userId);
    int getUserIdByName(String userName);
    boolean updateUserCredit(int userId, int updatedCredit);
    boolean verifyUser(User user);
}
