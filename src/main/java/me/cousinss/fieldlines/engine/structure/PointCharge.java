package me.cousinss.fieldlines.engine.structure;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import me.cousinss.fieldlines.InteractableStructure;
import me.cousinss.fieldlines.engine.ChargedEntity;
import me.cousinss.fieldlines.engine.Entity;
import me.cousinss.fieldlines.engine.structure.ChargeStructure;

public class PointCharge extends ChargeStructure implements InteractableStructure {
    public PointCharge(int charge, Entity<? extends Entity<?>> parent, double x, double y) {
        super(parent, x, y, 25, 25);
        this.addChild(new ChargedEntity(this, 0, 0, charge));
    }

    @Override
    public Node getNode() {
        Rectangle r = new Rectangle(5, 5);
        r.setTranslateX(this.getGlobalX());
        r.setTranslateY(this.getGlobalY());
        r.setFill(Color.WHITE);
        return r;
    }
}