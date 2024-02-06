package me.cousinss.fieldlines.engine;

public interface DirectionalEntity {

    public static final double ANG_UP = Math.PI/2;
    public static final double ANG_DOWN = -Math.PI/2;

    double getAngle();
    void setAngle(double angle);
}
