package com.example.aircraft.bullet;

public class EnemyBullet extends BaseBullet {

    public static int pow = 10;
    public static void setPow(int n) {
        EnemyBullet.pow = n;
    }

    public static int getPow() {
        return pow;
    }

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, pow);
    }
}