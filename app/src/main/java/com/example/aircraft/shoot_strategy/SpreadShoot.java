package com.example.aircraft.shoot_strategy;

import com.example.aircraft.bullet.BaseBullet;
import com.example.aircraft.bullet.EnemyBullet;
import com.example.aircraft.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class SpreadShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> shoot(int LocationX, int LocationY, int speedY, int direction, int power, boolean is_hero) {
        List<BaseBullet> res = new LinkedList<>();
        BaseBullet baseBullet;
        int speedX = 0;
        if(is_hero) {
            for(int i=0; i<3; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new HeroBullet(LocationX + (i*2 - 3 + 1)*10, LocationY + direction*2, i*2 - 3, speedY + direction*5, power);
                res.add(baseBullet);
            }
        }
        else {
            for(int i=0; i<3; i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                baseBullet = new EnemyBullet(LocationX + (i*2 - 3 + 1)*10, LocationY + direction*2, i*2 - 3, speedY + direction*5, power);
                res.add(baseBullet);
            }
        }
        return res;
    }
}
