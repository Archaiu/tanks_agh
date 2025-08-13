package org.example.common.POJO;

public class MyRotate {
    private volatile double angle;


    public MyRotate(double angle)
    {
        this.angle = angle;
    }


    public double getAngle() { return angle; }

    public void setAngle(double angle) { this.angle = angle; }

    @Override
    public String toString() {
        return "MyRotate{" +
                "angle=" + angle +
                '}';
    }

    
}