package org.example;

import javafx.scene.shape.Circle;

public class Bullet {
    public Circle bullet;
    Bullet(Tank tank)
    {
        double ankle = 360 - tank.getRotate().getAngle();
    }
}
