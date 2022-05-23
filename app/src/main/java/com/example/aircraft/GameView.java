package com.example.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Bitmap g;
    private Thread running;
    private int height;
    private int width;
    private int time = 0;
    private int score = 0;
    private int last_score = 0;

    private int cycleDuration = 600;
    private int cycleTime = 0;

    private int backGroundTop = 0;
    private int enemyMaxNumber = 5;
    private Paint paint;
    private boolean boss = false;


    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
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
    private PlayerRecordDao playerRecordDao;

    public List<AbstractAircraft> getEnemyAircrafts() {
        return enemyAircrafts;
    }

    public List<BaseBullet> getEnemyBullets() {
        return enemyBullets;
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
        Runnable task = () -> {
            time += timeInterval;

            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);

                if (enemyAircrafts.size() < enemyMaxNumber) {
                    double rand = Math.random();
                    if(rand < 0.5) {
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
                gameActivity.playMusicOnce(MusicConst.MUSIC_GAME_OVER);
            }
        };

        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    public void draw(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        Paint paint = new Paint();
        canvas.drawBitmap(GameActivity.BACKGROUND_IMAGE, 0, this.backGroundTop - MainActivity.height, paint);
        canvas.drawBitmap(GameActivity.BACKGROUND_IMAGE, 0, this.backGroundTop, paint);
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
            // 跨越到新的周期
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
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            } else if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
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
                // 英雄机 与 敌机 相撞，均损毁
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
    }
    public void increase_score(int n) {
        this.score += n;
    }
}
