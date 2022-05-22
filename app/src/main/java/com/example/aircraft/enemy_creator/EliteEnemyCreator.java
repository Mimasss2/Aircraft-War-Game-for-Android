package com.example.aircraft.enemy_creator;

import com.example.aircraft.GameActivity;
import com.example.aircraft.MainActivity;
import com.example.aircraft.air.AbstractAircraft;
import com.example.aircraft.air.EliteEnemy;

import java.util.Random;

public class EliteEnemyCreator implements EnemyCreator {

    private static Random rand = new Random();
    private static int speedX = 2;
    private static int speedY = 30;
    private static int hp = 30;

    public static void setSpeedX(int speedX) {
        EliteEnemyCreator.speedX = speedX;
    }

    public static void setSpeedY(int speedY) {
        EliteEnemyCreator.speedY = speedY;
    }

    public static void setHp(int hp) {
        EliteEnemyCreator.hp = hp;
    }

    public static int getSpeedX() {
        return speedX;
    }

    public static int getSpeedY() {
        return speedY;
    }

    public static int getHp() {
        return hp;
    }

    @Override
    public AbstractAircraft createEnemy() {
        int n = rand.nextInt(2);
        int speedx = speedX;
        if(n == 1) {
            speedx *= -1;
        }
        AbstractAircraft enemy = new EliteEnemy(
                (int) (Math.random() * (MainActivity.width - GameActivity.ELITE_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * MainActivity.height * 0.2) * 1,
                speedx,
                speedY,
                hp
        );
        return enemy;
    }
}
