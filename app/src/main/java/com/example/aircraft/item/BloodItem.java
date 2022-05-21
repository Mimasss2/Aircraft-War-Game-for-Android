package com.example.aircraft.item;

import com.example.aircraft.MainActivity;
import com.example.aircraft.air.HeroAircraft;

public class BloodItem extends AbstractItem {
    private int Hp;
    public BloodItem(int locationX, int locationY, int speedX, int speedY, int Hp) {
        super(locationX, locationY, speedX, speedY);
        this.Hp = Hp;
    }

    @Override
    public void activate(HeroAircraft hero) {
        if(MainActivity.music) {
            //TODO:音乐播放
        }
        hero.decreaseHp(- Hp);
    }
}

