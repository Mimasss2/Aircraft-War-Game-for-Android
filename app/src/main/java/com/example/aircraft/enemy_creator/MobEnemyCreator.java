package com.example.aircraft.enemy_creator;

import com.example.aircraft.GameActivity;
import com.example.aircraft.MainActivity;
import com.example.aircraft.air.AbstractAircraft;
import com.example.aircraft.air.MobEnemy;

public class MobEnemyCreator implements EnemyCreator {

    private static int speedX = 0;
    private static int speedY = 15;

    public static int getSpeedX() {
        return speedX;
    }

    public static int getSpeedY() {
        return speedY;
    }

    public static int getHp() {
        return hp;
    }

    public static void setSpeedX(int speedX) {
        MobEnemyCreator.speedX = speedX;
    }

    public static void setSpeedY(int sppedY) {
        MobEnemyCreator.speedY = speedY;
    }

    public static void setHp(int hp) {
        MobEnemyCreator.hp = hp;
    }

    private static int hp = 10;
    @Override
    public AbstractAircraft createEnemy() {
        AbstractAircraft enemy = new MobEnemy(
                (int) ( Math.random() * (MainActivity.width
                        - GameActivity.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * MainActivity.height * 0.2)*1,
                speedX,
                speedY,
                hp
        );
        return enemy;
    }
}
