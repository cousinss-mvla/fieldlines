package me.cousinss.fieldlines.engine;

import java.util.ArrayList;
import java.util.List;

public class ChargedEntity extends Entity<ChargedEntity> {

    private double charge;

    public ChargedEntity(Entity<? extends Entity<?>> parent, double x, double y, double charge) {
        super(parent, x, y);
        this.charge = charge;
    }

    public double getSelfCharge() {
        return this.charge;
    }

    public double getTotalCharge() {
        double sum = this.getSelfCharge();
        for(ChargedEntity c : this.getChildren()) {
            sum+=c.getTotalCharge();
        }
        return sum;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public List<ChargedEntity> getLowestDescendants() {
        List<ChargedEntity> list = new ArrayList<>();
        if(!this.hasChildren()) {
            list.add(this);
        }
        for(ChargedEntity child : this.getChildren()) {
            list.addAll(child.getLowestDescendants());
        }
        return list;
    }

    public List<ChargedEntity> getDescendantsWithSelf() {
        List<ChargedEntity> list = new ArrayList<>();
        list.add(this);
        for(ChargedEntity child : this.getChildren()) {
            list.addAll(child.getDescendantsWithSelf());
        }
        return list;
    }
}
