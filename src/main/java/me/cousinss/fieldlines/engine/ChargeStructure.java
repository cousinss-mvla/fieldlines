package me.cousinss.fieldlines.engine;

import java.util.ArrayList;
import java.util.List;

public abstract class ChargeStructure extends ChargedEntity {
    private double width;
    private double height;

    public ChargeStructure(Entity<? extends Entity<?>> parent, double x, double y, double width, double height) {
        super(parent, x, y, 0);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setWidth(double width) {
        double p = width/this.getWidth();
        for(Entity<ChargedEntity> e : this.getChildren()) {
            e.setLocalX(e.getLocalX() * p);
        }
        this.width = width;
    }

    public void setHeight(double height) {
        double p = height/this.getHeight();
        for(Entity<ChargedEntity> e : this.getChildren()) {
            e.setLocalY(e.getLocalY() * p);
        }
        this.height = height;
    }
}