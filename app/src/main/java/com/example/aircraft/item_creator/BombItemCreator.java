package com.example.aircraft.item_creator;

import com.example.aircraft.item.AbstractItem;
import com.example.aircraft.item.BombItem;

public class BombItemCreator implements ItemCreator {
    @Override
    public AbstractItem createItem(int LocationX, int LocationY) {
        AbstractItem item = new BombItem(LocationX, LocationY, 0, 2);
        return item;
    }
}