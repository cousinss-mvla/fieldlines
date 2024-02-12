package me.cousinss.fieldlines.engine;

import me.cousinss.fieldlines.InteractableStructure;
import me.cousinss.fieldlines.engine.structure.LineCharge;
import me.cousinss.fieldlines.engine.structure.PointCharge;

import java.util.List;
import java.util.stream.Collectors;

public class FLSimulation {
    private final ChargedEntity chargeRoot;
    private final Entity<FieldVector> vectorRoot;
    private final int width, height;
    private final double[][] voltage;

    public FLSimulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.chargeRoot = new ChargedEntity(null, 0, 0, 0);
        this.vectorRoot = new Entity<>(null);
        this.voltage = new double[height][width];
        initializeSimulation(width, height);
    }

    private void initializeSimulation(int width, int height) {
//        this.chargeRoot.addChild(new PointCharge(1, this.chargeRoot, width/4d, height/4d));
        this.chargeRoot.addChild(new LineCharge(-5, this.chargeRoot, width/2d, height/2.5d, 250, 0));
        this.chargeRoot.addChild(new LineCharge(5, this.chargeRoot, width/2d, height/1.5d, 250, 0));
    }

    public void populateFieldVectors(int numCols, int numRows, double gap) {
        for(int r = 0; r < numRows; r++) {
            for(int c = 0; c < numCols; c++) {
                vectorRoot.addChild(new FieldVector(vectorRoot, c*gap, r*gap, r, c));
            }
        }
    }

    public double[][] calculateVoltage() {
        int y, x;
        for(ChargedEntity e : chargeRoot.getLowestDescendants()) {
            for(y = 0; y < height; y++) {
                for(x = 0; x < width; x++) {
                    this.voltage[y][x] += e.getSelfCharge() / e.getGlobalDistance(x, y);
                }
            }
        }
        return this.voltage;
    }

    public List<InteractableStructure> getInteractableStructures() {
        return chargeRoot.getDescendantsWithSelf().stream().filter(ce -> ce instanceof InteractableStructure).map(ce -> (InteractableStructure) ce).collect(Collectors.toList());
    }

    public List<FieldVector> calculateElectricField(double dFactor) {
        List<FieldVector> vectors = this.vectorRoot.getChildren();
        double gx, gy;
        double dx, dy;
        double z;
        double mx, my;
        for(FieldVector vector : vectors) {
            vector.clearValues();
        }
        for(ChargedEntity e : chargeRoot.getLowestDescendants()) { //should flip at cross planes?
            gx = e.getGlobalX();
            gy = e.getGlobalY();
            for(FieldVector vector : vectors) {
                dx = (gx - vector.getLocalX()) * dFactor;
                dy = (gy - vector.getLocalY()) * dFactor;
                z = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                mx = (-e.getSelfCharge() * dx) / Math.pow(z, 3);
                my = (-e.getSelfCharge() * dy) / Math.pow(z, 3);
                vector.addMagnitudeX(mx);
                vector.addMagnitudeY(my);
            }
        }
        return vectors;
    }
}
