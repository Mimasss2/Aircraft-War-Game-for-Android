package com.example.aircraft;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.aircraft.service.MusicConst;

import java.util.HashMap;

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();
    private SoundPool mSoundPool;
    private MediaPlayer bgmPlayer;
    private MediaPlayer bossBgmPlayer;
    public MusicService() {

    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "==== MusicService onCreate ===");
        AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
        mSoundPool = new SoundPool.Builder().setMaxStreams(10).setAudioAttributes(audioAttributes).build();
        soundID.put(MusicConst.MUSIC_BGM, mSoundPool.load(this, R.raw.bgm, 1));
        soundID.put(MusicConst.MUSIC_BOSS_BGM, mSoundPool.load(this, R.raw.bgm_boss, 1));
        soundID.put(MusicConst.MUSIC_BOMB, mSoundPool.load(this, R.raw.bomb_explosion, 1));
        soundID.put(MusicConst.MUSIC_BULLET_SUPPLY, mSoundPool.load(this, R.raw.bullet, 1));
        soundID.put(MusicConst.MUSIC_BULLET_HIT, mSoundPool.load(this, R.raw.bullet_hit, 1));
        soundID.put(MusicConst.MUSIC_GAME_OVER, mSoundPool.load(this, R.raw.game_over, 1));
        soundID.put(MusicConst.MUSIC_SUPPLY, mSoundPool.load(this, R.raw.get_supply, 1));
    }
    @NonNull
    @Override
    public IBinder onBind(Intent intent){
        playBGMMusic();
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public void playMusicOnce(int musicType) {
            mSoundPool.play(soundID.get(musicType),1,1,0,0,1);
        }
        public void playBossBgm() {
            playBossMusic();
        }
        public void stopBossBgm() {
            stopBossMusic();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    //播放音乐
    public void playBGMMusic(){
        if(bgmPlayer == null){
            bgmPlayer = MediaPlayer.create(this, R.raw.bgm);
            bgmPlayer.setLooping(true);
        }
//        Log.w("music","before bgm start");
        bgmPlayer.start();
//        Log.w("music","bgm start success");
    }
    public void playBossMusic(){
        if(bossBgmPlayer == null){
            bossBgmPlayer = MediaPlayer.create(this, R.raw.bgm_boss);
            bossBgmPlayer.setLooping(true);
        }
        bossBgmPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopBGMMusic();
    }
    /**
     * 停止播放
     */
    public void stopBGMMusic() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.reset();//重置
            bgmPlayer.release();//释放
            bgmPlayer = null;
        }
    }
    public void stopBossMusic() {
        if (bossBgmPlayer != null) {
            bossBgmPlayer.stop();
            bossBgmPlayer.reset();//重置
            bossBgmPlayer.release();//释放
            bossBgmPlayer = null;
        }
    }

}
