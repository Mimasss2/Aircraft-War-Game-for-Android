package com.example.aircraft.item;

import com.example.aircraft.GameActivity;
import com.example.aircraft.MainActivity;
import com.example.aircraft.air.HeroAircraft;
import com.example.aircraft.service.MusicConst;

public class BloodItem extends AbstractItem {
    private int Hp;
    public BloodItem(int locationX, int locationY, int speedX, int speedY, int Hp) {
        super(locationX, locationY, speedX, speedY);
        this.Hp = Hp;
    }

    @Override
    public void activate(HeroAircraft hero, GameActivity gameActivity) {
        if(MainActivity.music) {
            gameActivity.playMusicOnce(MusicConst.MUSIC_SUPPLY);
        }
        hero.decreaseHp(- Hp);
    }
}

