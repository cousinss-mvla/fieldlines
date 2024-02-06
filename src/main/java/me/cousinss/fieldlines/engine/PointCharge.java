package me.cousinss.fieldlines.engine;

public class PointCharge extends ChargeStructure {
    public PointCharge(int charge, Entity<? extends Entity<?>> parent, double x, double y, double width, double height) {
        super(parent, x, y, width, height);
        this.addChild(new ChargedEntity(this, 0, 0, charge));
    }
}