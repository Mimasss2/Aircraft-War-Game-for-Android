package com.example.aircraft.item_creator;

import com.example.aircraft.item.AbstractItem;

public interface ItemCreator {
    public AbstractItem createItem(int LocationX, int LocationY);
}
