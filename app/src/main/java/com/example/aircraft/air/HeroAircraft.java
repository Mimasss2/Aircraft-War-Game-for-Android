package com.example.aircraft.air;

import com.example.aircraft.GameActivity;
import com.example.aircraft.ImageManager;
import com.example.aircraft.MainActivity;
import com.example.aircraft.bullet.BaseBullet;
import com.example.aircraft.bullet.HeroBullet;
import com.example.aircraft.shoot_strategy.SingleShoot;

import java.util.LinkedList;
import java.util.List;

public class HeroAircraft extends AbstractAircraft {

    /** 攻击方式 */
    private int shootNum = 1;     //子弹一次发射数量
    private int power = 30;       //子弹伤害
    private int direction = -1;  //子弹射击方向 (向上发射：1，向下发射：-1)
    private static HeroAircraft instance = new HeroAircraft(MainActivity.height / 2,
            MainActivity.width - GameActivity.HERO_IMAGE.getHeight() ,
            0, 0, 100);
    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.setStrategy(new SingleShoot());
    }

    public static HeroAircraft getInstance() {
        return instance;
    }
    public void resetHp() {
        HeroAircraft.getInstance().hp = 100;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> execute_shoot() {
//        List<BaseBullet> res = new LinkedList<>();
//        BaseBullet baseBullet;
//        int speedX = 0;
//        baseBullet = new HeroBullet(locationX, locationY + direction*2, speedX, speedY + direction*5, power);
//        res.add(baseBullet);
//        return res;
        return strategy.shoot(locationX, locationY, speedY, direction, power, true);
    }

    public void increaseNum(int n) {
        shootNum += n;
    }

}
