package org.example.player.gameLogic;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;


public class Bullet {
    private final Circle bullet;
    private final Translate translate =  new Translate();
    private static final int radius = 2;
    Bullet() {

        bullet = new Circle(radius, Color.PURPLE);
        bullet.getTransforms().add(translate);
    }


    public Circle getCircle() { return bullet;}

    public void setCords(double x, double y)
    {
        translate.setX(x);
        translate.setY(y);
    }



}
