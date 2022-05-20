package com.example.aircraft.item;

import com.example.aircraft.air.HeroAircraft;
import com.example.aircraft.basic.AbstractFlyingObject;

public abstract class AbstractItem extends AbstractFlyingObject {
    public AbstractItem(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void activate(HeroAircraft hero) {
        System.out.println(this.getClass() + "item activated");
    }
}