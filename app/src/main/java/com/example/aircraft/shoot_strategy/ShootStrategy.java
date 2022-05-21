package com.example.aircraft.shoot_strategy;

import com.example.aircraft.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    public List<BaseBullet> shoot(int LocationX, int LocationY, int speedY, int direction, int power, boolean is_hero);
}
