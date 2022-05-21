package com.example.aircraft.air;

import com.example.aircraft.bullet.BaseBullet;
import com.example.aircraft.item.AbstractItem;
import com.example.aircraft.item_creator.BloodItemCreator;
import com.example.aircraft.item_creator.BombItemCreator;
import com.example.aircraft.item_creator.BulletItemCreator;
import com.example.aircraft.item_creator.ItemCreator;
import com.example.aircraft.shoot_strategy.ShootStrategy;
import com.example.aircraft.shoot_strategy.SpreadShoot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractAircraft {
    private int direction = 1;
    private int power = 50;
    private static Random rand = new Random();
    private ItemCreator itemCreator;
    private ShootStrategy strategy;

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        this.strategy = new SpreadShoot();
    }

    @Override
    public List<BaseBullet> execute_shoot() {
        return strategy.shoot(locationX, locationY, speedY, direction, power, false);
    }

    public List<AbstractItem> dropItem() {
        List<AbstractItem> items = new LinkedList<>();
        int n = rand.nextInt(4);
        int x = this.getLocationX();
        int y = this.getLocationY() + direction*2;
        AbstractItem item;
        switch (n) {
            case 0: {
                itemCreator = new BloodItemCreator();
                item = itemCreator.createItem(x, y);
                items.add(item);
                break;
            }
            case 1: {
                itemCreator = new BombItemCreator();
                item = itemCreator.createItem(x, y);
                items.add(item);
                break;
            }
            case 2: {
                itemCreator = new BulletItemCreator();
                item = itemCreator.createItem(x, y);
                items.add(item);
                break;
            }
            default: break;
        }
        return items;
    }
}
