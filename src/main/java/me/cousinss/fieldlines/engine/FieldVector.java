package me.cousinss.fieldlines.engine;

public class FieldVector extends Entity implements DirectionalEntity {
    private double magnitudeX, magnitudeY;
    private final int rowID, colID;

    public FieldVector(Entity<? extends Entity> parent, double x, double y, int rowID, int colID) {
        super(parent, x, y);
        this.magnitudeX = 0;
        this.magnitudeY = 0;
        this.rowID = rowID;
        this.colID = colID;
    }

    public double getMagnitudeX() {
        return magnitudeX;
    }

    public void setMagnitudeX(double magnitudeX) {
        this.magnitudeX = magnitudeX;
    }

    public void addMagnitudeX(double magnitudeX) {
        this.magnitudeX += magnitudeX;
    }

    public double getMagnitudeY() {
        return magnitudeY;
    }

    public void setMagnitudeY(double magnitudeY) {
        this.magnitudeY = magnitudeY;
    }

    public void addMagnitudeY(double magnitudeY) {
        this.magnitudeY += magnitudeY;
    }

    @Override
    public double getAngle() {
        if(Double.isNaN(this.magnitudeX)) {
            return Math.signum(this.magnitudeY) * DirectionalEntity.ANG_DOWN;
        }
        if(Double.isNaN(this.magnitudeY)) {
            return (Math.signum(this.magnitudeX) / 2 + 1d) * Math.PI;
        }
        double ang = (((-Math.signum(this.magnitudeX) / 2) + 0.5d) * Math.PI) + Math.atan(this.magnitudeY / this.magnitudeX);
        return ang;
    }

    @Override
    public void setAngle(double angle) {
        //TODO
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.magnitudeX, 2) + Math.pow(this.magnitudeY, 2));
    }

    public void setMagnitude(double magnitude) {
        //TODO
    }

    public void clearValues() {
        this.magnitudeX = 0;
        this.magnitudeY = 0;
    }

    public int getRowID() {
        return rowID;
    }

    public int getColID() {
        return colID;
    }
}
