package com.example.aircraft;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.aircraft.air.AbstractAircraft;
import com.example.aircraft.air.BossEnemy;
import com.example.aircraft.air.EliteEnemy;
import com.example.aircraft.air.HeroAircraft;
import com.example.aircraft.basic.AbstractFlyingObject;
import com.example.aircraft.bullet.BaseBullet;
import com.example.aircraft.enemy_creator.BossEnemyCreator;
import com.example.aircraft.enemy_creator.EliteEnemyCreator;
import com.example.aircraft.enemy_creator.EnemyCreator;
import com.example.aircraft.enemy_creator.MobEnemyCreator;
import com.example.aircraft.item.AbstractItem;
import com.example.aircraft.item.BombItem;
import com.example.aircraft.record.PlayerRecordDao;
import com.example.aircraft.record.PlayerRecordDaoImpl;
import com.example.aircraft.service.MusicConst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Bitmap g;
    public Thread running;
    private int height;
    private int width;
    private int time = 0;
    private int score = 0;
    private int last_score = 0;
    private int opp_score = 0;

    private int cycleDuration = 600;
    private int cycleTime = 0;

    private int backGroundTop = 0;
    private int enemyMaxNumber = 5;
    private double elite_probability = 0.3;
    private Paint paint;
    private boolean boss = false;
    private int mode = 1;
    private Bitmap backgroundImage;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String message_from_server;

    /**
     * Scheduled ??????????????????????????????
     */
    private final ScheduledExecutorService executorService;

    /**
     * ????????????(ms)?????????????????????
     */
    private int timeInterval = 40;

    public HeroAircraft getHeroAircraft() {
        return heroAircraft;
    }

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractItem> items;
    private static EnemyCreator enemy_creator;
    private static int boss_score = 100;
    private GameActivity gameActivity;

    public List<AbstractAircraft> getEnemyAircrafts() {
        return enemyAircrafts;
    }

    public List<BaseBullet> getEnemyBullets() {
        return enemyBullets;
    }

    public int getScore() {
        return score;
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        height = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;
        Bitmap map = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        this.g = Bitmap.createScaledBitmap(map, width, height, true);
        SurfaceHolder s = getHolder();
        heroAircraft = HeroAircraft.getInstance();
        heroAircraft.resetHp();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        items = new LinkedList<>();
        paint = new Paint();
        executorService = new ScheduledThreadPoolExecutor(1);
        mode = GameActivity.getMode();
        backgroundImage = GameActivity.BACKGROUND_IMAGE_EASY;

        gameActivity = (GameActivity) context;
        s.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        Paint paint = new Paint();
        canvas.drawBitmap(g, 0, 0, paint);
        holder.unlockCanvasAndPost(canvas);
        running = new Thread(this);
        running.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        DisplayMetrics m = getResources().getDisplayMetrics();
        System.out.println(m.heightPixels);
        System.out.println(m.widthPixels);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        running.interrupt();
    }

    @Override
    public void run() {
        System.out.println("Game start");

        switch (mode) {
            case 1: {
                easy();
                break;
            }
            case 2: {
                normal();
                break;
            }
            case 3: {
                hard();
                break;
            }
            case 4: {
                new NetConn().start();
            }
        }
//        if(mode == 4) {
//            synchronized (reader) {
//                try {
//                    reader.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//           while (message_from_server != "start"){
//                new Thread(() -> {
//                    try {
//                        message_from_server = reader.readLine();
//                        System.out.println(message_from_server);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }).start();
//            }
//        }
        if(mode == 4) {
            synchronized (GameView.this) {
                try {
                    GameView.this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        Runnable task = () -> {
            time += timeInterval;

            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                if(mode == 4) {
                    new Thread(() -> {
                        writer.println(score);
                        try {
                            String message = reader.readLine();
                            if (message != null) {
                                opp_score = Integer.parseInt(message);
                            }
                            System.out.println(opp_score);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    double rand = Math.random();
                    if(rand < elite_probability) {
                        enemy_creator = new EliteEnemyCreator();
                    }
                    else {
                        enemy_creator = new MobEnemyCreator();
                    }
                    AbstractAircraft air = enemy_creator.createEnemy();
                    enemyAircrafts.add(air);
                }
                if(((score - last_score) >= boss_score) && !boss) {
                    enemy_creator = new BossEnemyCreator();
                    enemyAircrafts.add(enemy_creator.createEnemy());
                    if(MainActivity.music) {
                        gameActivity.playBossMusic();
                    }
                    boss = true;
                    last_score = score;
                }
                shootAction();
            }

            bulletsMoveAction();

            aircraftsMoveAction();

            itemsMoveAction();

            crashCheckAction();

            postProcessAction();

            this.draw(this.getHolder());

            if(heroAircraft.getHp() <= 0) {
                executorService.shutdown();
                System.out.println("Game over");
                if(mode == 4) {
                    new Thread(() -> {
                        writer.println("quit");
                    }).start();
                }
                gameActivity.stopBGM();
                gameActivity.playMusicOnce(MusicConst.MUSIC_GAME_OVER);
                gameActivity.show_records();
            }
        };

        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    public void draw(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        Paint paint = new Paint();
        canvas.drawBitmap(backgroundImage, 0, this.backGroundTop - MainActivity.height, paint);
        canvas.drawBitmap(backgroundImage, 0, this.backGroundTop, paint);
        this.backGroundTop += 1;
        if(this.backGroundTop == MainActivity.height) {
            this.backGroundTop = 0;
        }

        paintImageWithPositionRevised(canvas, enemyBullets);
        paintImageWithPositionRevised(canvas, heroBullets);
        paintImageWithPositionRevised(canvas, enemyAircrafts);
        paintImageWithPositionRevised(canvas, items);

        canvas.drawBitmap(GameActivity.HERO_IMAGE, heroAircraft.getLocationX() - GameActivity.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - GameActivity.HERO_IMAGE.getHeight() / 2, null);
        drawScoreAndLife(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    private void paintImageWithPositionRevised(Canvas canvas, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }
        for (int i = 0; i < objects.size(); i++) {
            AbstractFlyingObject object = objects.get(i);
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            Paint paint = new Paint();
            canvas.drawBitmap(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, paint);
        }
    }

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // ?????????????????????
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        for(AbstractAircraft enemy: enemyAircrafts) {
            List<BaseBullet> bullets = enemy.execute_shoot();
            for(AbstractItem item : items) {
                if(item instanceof BombItem) {
                    ((BombItem) item).add_all(bullets);
                }
            }
            enemyBullets.addAll(bullets);
        }
        heroBullets.addAll(heroAircraft.execute_shoot());
    }

    private void bulletsMoveAction() {
        for (int i = 0; i < heroBullets.size(); i++) {
            heroBullets.get(i).forward();
        }
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void itemsMoveAction() {
        for (AbstractItem item : items) {
            item.forward();
        }
    }

    private void crashCheckAction() {
        // TODO ????????????????????????
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            } else if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // ????????????????????????
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // ????????????????????????????????????????????????
                    // ???????????????????????????????????????????????????
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // ??????????????????????????????
                    // ???????????????????????????
                    if(MainActivity.music) {
                        gameActivity.playMusicOnce(MusicConst.MUSIC_BULLET_HIT);
                    }
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if(enemyAircraft instanceof EliteEnemy) {
                            EliteEnemy enmey = (EliteEnemy)enemyAircraft;
                            items.addAll(enmey.dropItem());
                        }
                        score += 10;
                        if(enemyAircraft instanceof BossEnemy) {
                            ((BossEnemy) enemyAircraft).dropItem();
                            boss = false;
                            if(MainActivity.music) {
                                gameActivity.stopBossMusic();
                            }
                            score += 50;
                        }
                    }
                }
                // ????????? ??? ?????? ??????????????????
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }
        for (AbstractItem item : items) {
            if (item.notValid()) {
                continue;
            }
            else if (heroAircraft.crash(item)) {
                item.activate(heroAircraft,gameActivity);
                item.vanish();
            }
        }
    }

    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        items.removeIf(AbstractFlyingObject::notValid);
    }
    private void drawScoreAndLife(Canvas canvas) {
        int x = 10;
        int y = 100;
        paint.setColor(Color.RED);
        Typeface sansSerif = Typeface.create("SansSerif", Typeface.BOLD);
        paint.setTypeface(sansSerif);
        paint.setTextSize(80);
        canvas.drawText("SCORE:" + this.score,x,y,paint);
        y = y + 80;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y,paint);
        y = y + 80;
        canvas.drawText("OPP_SCORE:" + this.opp_score, x, y, paint);
    }

    private void easy() {
        boss = true;
        enemyMaxNumber = 5;
        elite_probability = 0.3;
        backgroundImage = GameActivity.BACKGROUND_IMAGE_EASY;
    }

    private void normal() {
        enemyMaxNumber = 10;
        elite_probability = 0.4;
        boss_score = 300;
        backgroundImage = GameActivity.BACKGROUND_IMAGE_NORMAL;
    }

    private void hard() {
        enemyMaxNumber = 15;
        elite_probability = 0.5;
        boss_score = 150;
        backgroundImage = GameActivity.BACKGROUND_IMAGE_HARD;
    }

    public void increase_score(int n) {
        this.score += n;
    }

    protected class NetConn extends Thread{
        @Override
        public void run(){
            try{
                socket = new Socket();
                //??????????????????????????????IP
                socket.connect(new InetSocketAddress
                        ("192.168.75.1",9999),5000);
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),"UTF-8")),true);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (message_from_server == null || !message_from_server.equals("start")) {
                    message_from_server = reader.readLine();
                }
                synchronized (GameView.this) {
                    GameView.this.notifyAll();
                }
                Log.i("client","connect to server");
            }catch(UnknownHostException ex){
                ex.printStackTrace();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
