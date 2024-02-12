package me.cousinss.fieldlines.engine;

public interface DirectionalEntity {

    public static final double ANG_UP = Math.PI/2;
    public static final double ANG_DOWN = -Math.PI/2;
    public static final double RAD_TO_DEG = 180 / Math.PI;

    double getAngle();
    void setAngle(double angle);
}
