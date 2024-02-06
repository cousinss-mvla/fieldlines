package me.cousinss.fieldlines;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import me.cousinss.fieldlines.engine.FLSimulation;
import me.cousinss.fieldlines.engine.FieldVector;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

public class FieldLines extends Application {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final double VOLTAGE_MULTIPLIER = 2;
    private static final double VOLTAGE_POWER =2;
    private static final double VECTOR_GAP = 35;
    private static final int NUM_ROWS = (int)(HEIGHT / VECTOR_GAP)+1;
    private static final int NUM_COLS = (int)(WIDTH / VECTOR_GAP)+1;
    private static final double RAD_TO_DEG = 180 / Math.PI;
    private static final double DISTANCE_FACTOR = 1;
    private static final boolean LABEL_VECTORS_DEBUG = false;
    /**
     * Between 0+ and 1. Greater = More falloff.
     */
    private static final double VECTOR_FALLOFF = 0.2;

    @Override
    public void start(Stage stage) {
        FLSimulation sim = new FLSimulation(WIDTH, HEIGHT);
        sim.populateFieldVectors(NUM_COLS, NUM_ROWS, VECTOR_GAP);
        ImageView voltageRaster = new ImageView(voltageRaster(sim.calculateVoltage()));
        GridPane vectors = displayVectors(sim.calculateElectricField(DISTANCE_FACTOR));
        StackPane root = new StackPane(voltageRaster, vectors);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("FieldLines");
        stage.setScene(scene);
        stage.show();
    }

    private Image voltageRaster(double[][] voltage) {
        int width = voltage[0].length;
        int height = voltage.length;
        double v, magnitude;
        boolean pos;
        int y, x;
        WritableImage img = new WritableImage(width, height);
        PixelWriter pw  = img.getPixelWriter();
        for (y = 0 ; y < height ; y++) {
            for (x = 0 ; x < width ; x++) {
                v = voltage[y][x];
                pos = v > 0;
                magnitude = Math.pow(Math.abs(v), 1.0/VOLTAGE_POWER);
                if(pos) {
                    pw.setColor(x, y, Color.rgb((int) Math.min(VOLTAGE_MULTIPLIER * (255f * magnitude), 255), 0, 0));
                } else {
                    pw.setColor(x, y, Color.rgb(0, 0, (int) Math.min(VOLTAGE_MULTIPLIER * (255f * magnitude), 255)));
                }
            }
        }
        return img;
    }

    private GridPane displayVectors(List<FieldVector> vectors) {
        GridPane pane = new GridPane();
//        pane.setTranslateX(-VECTOR_GAP/2);
//        pane.setTranslateY(-VECTOR_GAP/2);
        double maxMagnitude = 0;
        double minMagnitude = Double.MAX_VALUE;
        for(FieldVector v : vectors) {
            maxMagnitude = Math.max(maxMagnitude, v.getMagnitude());
            minMagnitude = Math.min(minMagnitude, v.getMagnitude());
        }
        double mRange = maxMagnitude-minMagnitude;
        for(FieldVector v : vectors) {
            pane.add(drawVector(v, minMagnitude, mRange), v.getColID(), v.getRowID());
        }
        for(int i = 0; i < NUM_ROWS; i++) {
            RowConstraints r = new RowConstraints(VECTOR_GAP);
            r.setValignment(VPos.TOP);
            pane.getRowConstraints().add(r);
        }
        for(int i = 0; i < NUM_COLS; i++) {
            ColumnConstraints c = new ColumnConstraints(VECTOR_GAP);
            c.setHalignment(HPos.LEFT);
            pane.getColumnConstraints().add(c);
        }
        return pane;
    }

    private Node drawVector(FieldVector v, double mMin, double mRange) {
        StackPane p = new StackPane();
        ImageView n = new ImageView(new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("vectorarrow.png"))));
        n.setRotate(v.getAngle() * RAD_TO_DEG);
        n.setOpacity(Math.pow((v.getMagnitude() - mMin) / mRange, VECTOR_FALLOFF));
        p.setPrefWidth(VECTOR_GAP);
        p.setPrefHeight(VECTOR_GAP);
        Label text = new Label(String.format("%1.2e",v.getMagnitudeX()) + ", " + String.format("%1.2e",v.getMagnitudeY()));
        text.setTextFill(Color.LIGHTGREEN);
        p.getChildren().add(n);
        if(LABEL_VECTORS_DEBUG) {
            p.getChildren().add(text);
        }
        return p;
    }

    public static void main(String[] args) {
        launch();
    }
}