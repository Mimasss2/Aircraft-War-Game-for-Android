package com.example.aircraft.air;

import com.example.aircraft.MainActivity;
import com.example.aircraft.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

public class MobEnemy extends AbstractAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.height) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> execute_shoot() {
        return new LinkedList<>();
    }

}
