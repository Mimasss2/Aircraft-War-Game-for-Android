package com.example.aircraft;

import com.example.aircraft.conn.DAO.User;
import com.example.aircraft.conn.service.Impl.UserServiceImpl;
import com.example.aircraft.conn.service.UserService;

import org.junit.Test;

public class UserServiceTest {
    UserService userService = new UserServiceImpl();

    @Test
    public void testInsertUser() {
        User test1 = new User(0, "test1", "123456", 50);
        boolean result = userService.insertUser(test1);
        System.out.println(result);
    }
    @Test
    public void selectUser() {
        User user = userService.selectUserById(1);
        System.out.println(user);
        User user1 = userService.selectUserById(5);
        System.out.println(user1);
    }
    @Test
    public void verifyUser() {
        User user = new User("test1","12345");
        boolean result = userService.verifyUser(user);
        System.out.println(result);
    }
    @Test
    public void updateUserCredit() {
        boolean b = userService.updateUserCredit(3, 1000);
        System.out.println(b);
        b = userService.updateUserCredit(6, 1000);
        System.out.println(b);
    }
}
