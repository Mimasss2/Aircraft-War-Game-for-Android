package com.example.aircraft.air;

import com.example.aircraft.basic.AbstractFlyingObject;
import com.example.aircraft.bullet.BaseBullet;
import com.example.aircraft.shoot_strategy.ShootStrategy;

import java.util.List;

public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected ShootStrategy strategy;
    protected int shootNum = 1;

    public int getshootNum() {
        return shootNum;
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
        if(hp > maxHp) {
            hp = maxHp;
        }
    }

    public int getHp() {
        return hp;
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> execute_shoot();

    public void setStrategy(ShootStrategy s) {
        this.strategy = s;
    }

}
