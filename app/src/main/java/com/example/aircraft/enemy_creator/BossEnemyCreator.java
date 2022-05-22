package com.example.aircraft.enemy_creator;

import com.example.aircraft.GameActivity;
import com.example.aircraft.MainActivity;
import com.example.aircraft.air.AbstractAircraft;
import com.example.aircraft.air.BossEnemy;

import java.util.Random;

public class BossEnemyCreator implements EnemyCreator {

    private static Random rand = new Random();
    private static int hp = 50;

    public static int getHp() {
        return hp;
    }

    public static void setHp(int n) {
        BossEnemyCreator.hp = n;
    }
    @Override
    public AbstractAircraft createEnemy() {
        int n = rand.nextInt(2);
        int speedX = 2;
        if(n == 1) {
            speedX *= -1;
        }
        AbstractAircraft enemy = new BossEnemy(
                (int) (Math.random() * (MainActivity.width - GameActivity.BOSS_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * MainActivity.height * 0.2) * 1,
                speedX,
                0,
                hp
        );
        return enemy;
    }
}
