package me.cousinss.fieldlines.engine;

import java.util.ArrayList;
import java.util.List;

public class ChargedEntity extends Entity<ChargedEntity> {

    private int charge;

    public ChargedEntity(Entity<? extends Entity<?>> parent, double x, double y, int charge) {
        super(parent, x, y);
        this.charge = charge;
    }

    public int getSelfCharge() {
        return this.charge;
    }

    public int getTotalCharge() {
        int sum = this.getSelfCharge();
        for(ChargedEntity c : this.getChildren()) {
            sum+=c.getTotalCharge();
        }
        return sum;
    }

    public void setCharge(int charge) {
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
}
