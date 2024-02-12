package me.cousinss.fieldlines.engine.structure;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import me.cousinss.fieldlines.InteractableStructure;
import me.cousinss.fieldlines.engine.ChargedEntity;
import me.cousinss.fieldlines.engine.DirectionalEntity;
import me.cousinss.fieldlines.engine.Entity;

public class LineCharge extends ChargeStructure implements InteractableStructure, DirectionalEntity {

    private double angleRad;

    public LineCharge(int charge, Entity<? extends Entity<?>> parent, double x, double y, double length, double angleRad) {
        super(parent, x, y, length, 10);
        this.setCharge(charge);
        this.setAngle(angleRad); //calculates child positions
    }

    @Override
    public Node getNode() {
        Rectangle r = new Rectangle(this.getWidth(), this.getHeight());
        r.setRotate(this.angleRad * DirectionalEntity.RAD_TO_DEG);
        r.setTranslateX(this.getGlobalX());
        r.setTranslateY(this.getGlobalY());
        r.setFill(Color.WHITE);
        return r;
    }

    @Override
    public double getAngle() {
        return this.angleRad;
    }

    @Override
    public void setAngle(double angle) {
        this.angleRad = angle;
        this.clearChildren();
        double dNumCharges = this.getWidth() * ChargeStructure.STRUCTURE_RESOLUTION;
        double pad = (dNumCharges - ((int) dNumCharges)) * ChargeStructure.CHARGE_GAP / 2d;
        double cMag = this.getSelfCharge() / dNumCharges;
        for(int i = 0; i < dNumCharges; i++) {//TODO account for angle
            this.addChild(new ChargedEntity(this, -this.getWidth() / 2d + pad + i * ChargeStructure.CHARGE_GAP, 0, cMag));
        }
    }
}
