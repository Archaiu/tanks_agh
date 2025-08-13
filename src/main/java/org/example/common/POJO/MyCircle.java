package org.example.common.POJO;

import java.util.UUID;

public class MyCircle {
    private UUID id;
    private volatile double centerX;
    private volatile double centerY;
    final private double radius;
    private MyTranslate translate; // Referencja do obiektu MyTranslate
    private MyRotate rotate;       // Referencja do obiektu MyRotate

    public MyCircle(UUID id, double centerX, double centerY, double radius) {
        this.id = id;
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public MyCircle(UUID id, double radius)
    {
        this.id = id;
        this.radius = radius;
    }

    // Gettery
    public UUID getId() { return id; }
    public double getCenterX() { return centerX; }
    public double getCenterY() { return centerY; }
    public double getRadius() { return radius; }
    public MyTranslate getTranslate() { return translate; }
    public MyRotate getRotate() { return rotate; }

    // Metody do dodawania transformacji (po jednym obiekcie)
    public void setTranslate(MyTranslate translate) { this.translate = translate; }
    public void setRotate(MyRotate rotate) { this.rotate = rotate; }

    // Pomocnicze metody do szybkiego dostępu do wartości z translate/rotate
    public double getTranslateX() { return translate != null ? translate.getX() : 0.0; }
    public double getTranslateY() { return translate != null ? translate.getY() : 0.0; }
    public double getRotateAngle() { return rotate != null ? rotate.getAngle() : 0.0; }

    @Override
    public String toString() {
        return "MyCircle{" +
                "id='" + id + '\'' +
                ", centerX=" + centerX +
                ", centerY=" + centerY +
                ", radius=" + radius +
                ", translate=" + translate +
                ", rotate=" + rotate +
                '}';
    }
}