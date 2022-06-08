package com.example.aircraft;

import com.example.aircraft.conn.DAO.Prop;
import com.example.aircraft.conn.service.Impl.PropServiceImpl;
import com.example.aircraft.conn.service.PropService;

import org.junit.Test;

import java.util.List;

public class PropServiceTest {
    PropService propService = new PropServiceImpl();
    @Test
    public void showAllProps() {
        List<Prop> propList = propService.showAllProps();
        System.out.println(propList);
    }
    @Test
    public void insertProp() {
        boolean res;
        res = propService.insertProp(new Prop(1, "bomb1", 50));
        System.out.println(res);
        res = propService.insertProp(new Prop(2, "bomb2", 500));
        System.out.println(res);
        res = propService.insertProp(new Prop(4, "bomb4", 0));
        System.out.println(res);
        res = propService.insertProp(new Prop(0, "bomb0", 333));
        System.out.println(res);
        res = propService.insertProp(new Prop(7, "bomb7", 1));
        System.out.println(res);
    }
    @Test
    public void getPropById() {
        Prop prop = propService.getPropById(1);
        System.out.println(prop);
        Prop prop1 = propService.getPropById(100);
        System.out.println(prop1);
    }
/*    @Test
    public void insertPropInstance() {
        boolean b;
        b = propService.insertPropInstance(1, 2);
        System.out.println(b);
        b = propService.insertPropInstance(1, 1);
        System.out.println(b);
    }*/
    @Test
    public void showUserProp() {
        List<Prop> propList = propService.showUserProp(1);
        System.out.println(propList);
        List<Prop> propList2 = propService.showUserProp(2);
        System.out.println(propList2);
    }
}
