package org.example.common.POJO;

public class MyTranslate {
    private volatile double x;
    private volatile double y;

    public MyTranslate() {}

    public MyTranslate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    @Override
    public String toString() {
        return "MyTranslate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}