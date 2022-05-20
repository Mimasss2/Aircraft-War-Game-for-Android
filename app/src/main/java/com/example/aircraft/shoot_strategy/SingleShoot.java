package com.example.aircraft.shoot_strategy;

import com.example.aircraft.bullet.BaseBullet;
import com.example.aircraft.bullet.EnemyBullet;
import com.example.aircraft.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class SingleShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int LocationX, int LocationY, int speedY, int direction, int power, boolean is_hero){
        List<BaseBullet> res = new LinkedList<>();
        BaseBullet baseBullet;
        int speedX = 0;
        if(is_hero) {
            baseBullet = new HeroBullet(LocationX, LocationY + direction*2, speedX, speedY + direction*5, power);
        }
        else {
            baseBullet = new EnemyBullet(LocationX, LocationY + direction*2, speedX, speedY + direction*5, power);
        }
        res.add(baseBullet);
        return res;
    }
}
