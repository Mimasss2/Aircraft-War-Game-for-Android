package com.example.aircraft;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraft.air.BossEnemy;
import com.example.aircraft.air.EliteEnemy;
import com.example.aircraft.air.HeroAircraft;
import com.example.aircraft.air.MobEnemy;
import com.example.aircraft.bullet.EnemyBullet;
import com.example.aircraft.bullet.HeroBullet;
import com.example.aircraft.item.BloodItem;
import com.example.aircraft.item.BombItem;
import com.example.aircraft.item.BulletItem;

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
    public static GameView gameView;
    public static int score;
    private static String name = "localUser";
    private static int mode = 1;

    public static int getMode() {
        return mode;
    }

    private boolean isControllingHero = false;
    public MusicService.MyBinder myBinder;
//    public static MusicService.MyBinder myBinder;
    private Connect conn;
    private Intent intent;

    public static void setMode(int mode) {
        GameActivity.mode = mode;
    }

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
        CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELITE_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(BossEnemy.class.getName(), BOSS_ENEMY_IMAGE);
        CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
        CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
//
        CLASSNAME_IMAGE_MAP.put(BloodItem.class.getName(), BLOOD_PROP_IMAG);
        CLASSNAME_IMAGE_MAP.put(BombItem.class.getName(), BOMB_PROP_IMAG);
        CLASSNAME_IMAGE_MAP.put(BulletItem.class.getName(), BULLET_PROP_IMAG);

        //music
        Log.i("music demo","bind service");
        conn = new Connect();
        intent = new Intent(this,MusicService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);

        setContentView(R.layout.activity_game);
        gameView = findViewById(R.id.GameView);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        System.out.println("touch event triggered!");
        int x = (int) event.getX();
        int y = (int) event.getY();
        HeroAircraft hero = gameView.getHeroAircraft();
        int leftBoundary = hero.getLocationX() - hero.getImage().getWidth();
        int rightBoundary = hero.getLocationX() + hero.getImage().getWidth();
        int topBoundary = hero.getLocationY() - hero.getImage().getHeight();
        int bottomBoundary = hero.getLocationY() + hero.getImage().getHeight();
//        System.out.println("clickX:"+x);
//        System.out.println("clickY:"+y);
//        System.out.println("leftBoudary:"+leftBoundary);
//        System.out.println("rightBoudary:"+rightBoundary);
//        System.out.println("topBoudary:"+topBoundary);
//        System.out.println("bottomBoudary:"+bottomBoundary);
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if ( x<0 || x>MainActivity.width || y<0 || y>MainActivity.height){
                // 防止超出边界
                return true;
            }
            if( x > leftBoundary && x < rightBoundary && y > topBoundary && y < bottomBoundary) {
                isControllingHero = true;
//                System.out.println("controlling");
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if ( x<0 || x>MainActivity.width || y<0 || y>MainActivity.height){
                // 防止超出边界
                return true;
            }
            if(isControllingHero) {
                gameView.getHeroAircraft().setLocation(x,y);
//                System.out.println("moving");
            }

        }
        else if(event.getAction() == MotionEvent.ACTION_UP) {
//            System.out.println("control end");
            isControllingHero = false;
        }
        else if(event.getAction() == MotionEvent.ACTION_CANCEL){
//            System.out.println("control canceled.");
            isControllingHero = false;
        }
        return true;
    }

    public Resources getRes() {
        return super.getResources();
    }
    class Connect implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            Log.i("music demo","Service Connnected");
            myBinder = (MusicService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    public void show_records() {
        Intent i = new Intent();
        i.putExtra("name", name);
        i.putExtra("score", gameView.getScore());
        i.putExtra("mode", mode);
        i.setComponent(new ComponentName("com.example.aircraft", "com.example.aircraft.GameHistoryActivity"));

        startActivity(i);
    }

    public void playMusicOnce(int musicType) {
        myBinder.playMusicOnce(musicType);
    }
    public void playBossMusic() {
        myBinder.playBossBgm();
    }
    public void stopBossMusic() {
        myBinder.stopBossBgm();
    }

}
