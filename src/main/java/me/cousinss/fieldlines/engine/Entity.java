package me.cousinss.fieldlines.engine;

import java.util.ArrayList;
import java.util.List;

public class Entity<T extends Entity> {
    private double x;
    private double y;
    private final Entity parent;
    private final List<T> children;

    public Entity(Entity parent, double x, double y) {
        this.parent = parent;
        this.children = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public Entity(Entity parent) {
        this(parent, 0, 0);
    }

    public Entity getParent() {
        return this.parent;
    }

    public List<T> getChildren() {
        return this.children;
    }

    public void setChildren(List<T> children) {
        this.children.clear();
        this.children.addAll(children);
    }

    public void addChild(T child) {
        this.children.add(child);
    }

    public boolean removeChild(T child) {
        return this.children.remove(child);
    }

    public boolean hasChild(T child) {
        return this.children.contains(child);
    }

    public double getLocalX() {
        return this.x;
    }

    public double getLocalY() {
        return this.y;
    }

    public void setLocalX(double x) {
        this.x = x;
    }

    public void setLocalY(double y) {
        this.y = y;
    }

    public double getGlobalX() {
        if(this.getParent() == null) {
            return this.getLocalX();
        }
        return this.getLocalX() + this.getParent().getGlobalX();
    }

    public double getGlobalY() {
        if(this.getParent() == null) {
            return this.getLocalY();
        }
        return this.getLocalY() + this.getParent().getGlobalY();
    }

    public double setGlobalX(double x) {
        if(this.getParent() == null) {
            this.setLocalX(x);
            return x;
        }
        double lx = x - this.getParent().getGlobalX();
        this.setLocalX(lx);
        return lx;
    }

    public double setGlobalY(double y) {
        if(this.getParent() == null) {
            this.setLocalY(y);
            return y;
        }
        double ly = y - this.getParent().getGlobalY();
        this.setLocalY(ly);
        return ly;
    }

    public double getGlobalDistance(double x, double y) {
        return Math.sqrt(getSquaredGlobalDistance(x, y));
    }

    public double getSquaredGlobalDistance(double x, double y) {
        return Math.pow(this.getGlobalX() - x, 2) + Math.pow(this.getGlobalY() - y, 2);
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }
}
