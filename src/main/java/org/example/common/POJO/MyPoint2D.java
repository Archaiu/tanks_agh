package org.example.common.POJO;

public class MyPoint2D
{
    private volatile double _x;
    private volatile double _y;
    public MyPoint2D(double x, double y)
    {
        _x = x;
        _y = y;
    }

    public double getX()
    {
        return _x;
    }
    public double getY()
    {
        return _y;
    }
    public void setX(double x)
    {
        _x = x;
    }
    public void setY(double y)
    {
        _y = y;
    }
}
