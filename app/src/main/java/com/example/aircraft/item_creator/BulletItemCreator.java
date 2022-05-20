package com.example.aircraft.item_creator;

import com.example.aircraft.item.AbstractItem;
import com.example.aircraft.item.BulletItem;

public class BulletItemCreator implements ItemCreator {

    @Override
    public AbstractItem createItem(int LocationX, int LocationY) {
        AbstractItem item = new BulletItem(LocationX, LocationY, 0 ,2);
        return item;
    }
}
