package com.example.aircraft.item_creator;


import com.example.aircraft.item.AbstractItem;
import com.example.aircraft.item.BloodItem;

public class BloodItemCreator implements ItemCreator {

    private int Hp = 20;
    @Override
    public AbstractItem createItem(int LocationX, int LocationY) {
        AbstractItem item = new BloodItem(LocationX, LocationY, 0 ,2, Hp);
        return item;
    }
}
