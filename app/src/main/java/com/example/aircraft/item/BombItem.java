package com.example.aircraft.item;

import com.example.aircraft.GameActivity;
import com.example.aircraft.GameView;
import com.example.aircraft.MainActivity;
import com.example.aircraft.air.AbstractAircraft;
import com.example.aircraft.air.BossEnemy;
import com.example.aircraft.air.EliteEnemy;
import com.example.aircraft.air.HeroAircraft;
import com.example.aircraft.basic.AbstractFlyingObject;
import com.example.aircraft.bullet.BaseBullet;
import com.example.aircraft.service.MusicConst;

import java.util.ArrayList;
import java.util.List;

public class BombItem extends AbstractItem {

    private List<AbstractFlyingObject> flyingObjects;

    public void add_objects(AbstractFlyingObject o) {
        flyingObjects.add(o);
    }

    public void add_all(List<BaseBullet> o) {
        flyingObjects.addAll(o);
    }

    public void delete_objects(AbstractFlyingObject o) {
        flyingObjects.remove(o);
    }

    public BombItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        this.flyingObjects = new ArrayList<AbstractFlyingObject>();
    }

    @Override
    public void activate(HeroAircraft hero, GameActivity gameActivity) {
        if(MainActivity.music) {
            gameActivity.playMusicOnce(MusicConst.MUSIC_BOMB);
        }
        GameView g = GameActivity.gameView;
        this.notify_flyingObjects(g.getEnemyAircrafts(), g.getEnemyBullets());
    }

    public void notify_flyingObjects(List<AbstractAircraft> enemys, List<BaseBullet> bullets) {
        for(AbstractAircraft air : enemys) {
            if(air instanceof BossEnemy) {
                continue;
            }
            else {
                air.vanish();
                GameView g = GameActivity.gameView;
                g.increase_score(10);
                if(air instanceof EliteEnemy) {
                    ((EliteEnemy) air).dropItem();
                }
            }
        }

        for(BaseBullet b : bullets) {
            b.vanish();
        }
    }
}

