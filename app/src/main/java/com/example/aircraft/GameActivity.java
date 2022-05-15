package com.example.aircraft;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraft.air.HeroAircraft;
import com.example.aircraft.air.MobEnemy;
import com.example.aircraft.bullet.HeroBullet;

import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();
    public static Bitmap BACKGROUND_IMAGE;
    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_ENEMY_IMAGE;
    public static Bitmap BOSS_ENEMY_IMAGE;
    public static Bitmap BLOOD_PROP_IMAG;
    public static Bitmap BOMB_PROP_IMAG;
    public static Bitmap BULLET_PROP_IMAG;


    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = this.getResources();
        BACKGROUND_IMAGE = BitmapFactory.decodeResource(res, R.drawable.bg);
        BACKGROUND_IMAGE = Bitmap.createScaledBitmap(BACKGROUND_IMAGE, MainActivity.width, MainActivity.height, true);
        HERO_IMAGE = BitmapFactory.decodeResource(res, R.drawable.hero);
        HERO_BULLET_IMAGE = BitmapFactory.decodeResource(res, R.drawable.bullet_hero);
        ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(res, R.drawable.bullet_enemy);
        MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(res, R.drawable.mob);
        ELITE_ENEMY_IMAGE = BitmapFactory.decodeResource(res, R.drawable.elite);
        BOSS_ENEMY_IMAGE = BitmapFactory.decodeResource(res, R.drawable.boss);
        BLOOD_PROP_IMAG = BitmapFactory.decodeResource(res, R.drawable.prop_blood);
        BOMB_PROP_IMAG = BitmapFactory.decodeResource(res, R.drawable.prop_bomb);
        BULLET_PROP_IMAG = BitmapFactory.decodeResource(res, R.drawable.prop_bullet);

        CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
        CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
//        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
//        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
//        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
//
//        CLASSNAME_IMAGE_MAP.put(HpItem.class.getName(), BLOOD_ITEM_IMAG);
//        CLASSNAME_IMAGE_MAP.put(BombItem.class.getName(), BOMB_ITEM_IMAG);
//        CLASSNAME_IMAGE_MAP.put(BulletItem.class.getName(), BULLET_ITEM_IMAG);

        setContentView(R.layout.activity_game);
    }

    public Resources getRes() {
        return super.getResources();
    }
}
