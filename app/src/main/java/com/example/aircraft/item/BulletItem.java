package com.example.aircraft.item;

import com.example.aircraft.GameActivity;
import com.example.aircraft.MainActivity;
import com.example.aircraft.air.HeroAircraft;
import com.example.aircraft.service.MusicConst;
import com.example.aircraft.shoot_strategy.SingleShoot;
import com.example.aircraft.shoot_strategy.SpreadShoot;

public class BulletItem extends AbstractItem {

    public BulletItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft hero, GameActivity gameActivity) {
        hero.setStrategy(new SpreadShoot());
        if(MainActivity.music) {
            gameActivity.playMusicOnce(MusicConst.MUSIC_BULLET_SUPPLY);
        }
        Runnable timer = () -> {
            try {
                Thread.sleep(10000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            hero.setStrategy(new SingleShoot());
        };
        new Thread(timer).start();
    }
}
