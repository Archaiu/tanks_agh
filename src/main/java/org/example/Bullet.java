package org.example;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class Bullet {
    private Circle bullet;
    private Translate translate;
    double ankle;
    double step = 1.5;
    double xVector;
    double yVector;
    int radius = 2;
    Bullet(Tank tank, Controller controller)
    {
        var startPoint = calculatePosition(tank);
        bullet = new Circle( radius, Color.PURPLE);
        bullet.getTransforms().add(translate = new Translate(startPoint.getX(),startPoint.getY()));
        ankle = (360 - tank.getRotate().getAngle() + 180)%360;
        controller.getMainPlansza().getChildren().add(bullet);
        AnimationTimer timer = new AnimationTimer()
        {
            private long startTime = System.currentTimeMillis();
            int counter = 1000;

            public void handle(long now) {
                if (System.currentTimeMillis() > startTime + 6 ) {
                    if (counter > 0) {
                        moveBullet();
                        startTime = System.currentTimeMillis();
                        counter--;
                    } else {
                        controller.getMainPlansza().getChildren().remove(bullet);
                        stop();
                    }
                }
            }
        };
        System.out.println("Shot at cords" + translate.getX() + " " + translate.getY());
        xVector = step * Math.cos(Math.toRadians(ankle));
        yVector = step * Math.sin(Math.toRadians(ankle));

        timer.start();
    }
    void moveBullet()
    {
        translate.setX(translate.getX() + xVector);
        translate.setY(translate.getY() - yVector);
        collision();


    }
    Point2D calculatePosition(Tank tank)
    {
        double ankle = (360 - tank.getRotate().getAngle() + 90)%360;
        double radians = Math.toRadians(ankle);
        double Ystep = Math.sin(radians) * tank.getVObjectToDisplay().getHeight()/2.0;
        double Xstep = -Math.cos(radians) * tank.getVObjectToDisplay().getHeight()/2.0;
        return new Point2D ( tank.getTranslate().getX() + Xstep,tank.getTranslate().getY() + Ystep);
    }
    boolean collision() {
        var map = MapInfo.mapBullets;
        boolean itIsCorner = false;
        var iterator = new ArrayList<double[]>(map.verLines());
        iterator.addAll(map.verBorders());
        for (double[] element : iterator) {
            if (radius > Math.abs(translate.getX() - element[0])) {
                if (translate.getY() >= element[1] && translate.getY() <= element[2]) {
        //            System.out.println("Bullet have normal vertical collision");
                    xVector *= -1;
                    return true;
                } else if (radius >= length(element[0], element[1]) || radius >= length(element[0], element[2])) {
                    System.out.println("Bullet touch corner");
                    bulletTouchCorner(radius >= length(element[0], element[1])?new Point2D(element[0],element[1]):new Point2D(element[0],element[2]));
                    return true;
                }
            }
        }
        iterator = new ArrayList<double[]>(map.horLines());
        iterator.addAll(map.horBorders());
        for (double[] element : iterator) {
            if (radius > Math.abs(translate.getY() - element[0])) {
                if (translate.getX() >= element[1] && translate.getX() <= element[2]) {
        //            System.out.println("Bullet have normal horisontal collision");
                    yVector *= -1;
                    return true;
                }
            }
        }
        return false;
    }
    double length(double x, double y)
    {
        return Math.sqrt(Math.pow(Math.abs(x-translate.getX()), 2) + Math.pow(Math.abs(y-translate.getY()), 2));
    }
    void bulletTouchCorner(Point2D point)
    {
        // a = (translate.getY() - point.getY()) / (translate.getX() - point.getX());
        double angleBulletAndCorner = (Math.toDegrees(Math.atan2(-point.getY() + translate.getY(), point.getX() - translate.getX()))+360)%360;
        double angleOfBullet = (Math.toDegrees(Math.atan2(-yVector, xVector))+360)%360;
        double deltaAngle = angleBulletAndCorner - angleOfBullet;
        System.out.println("Angle of bullet vector = " + angleOfBullet + " Angle between bullet and corner = " + angleBulletAndCorner + " deltaAngle" + deltaAngle);
        double minAngleChange = 5.0;
        if (Math.abs(deltaAngle) < minAngleChange) {
            deltaAngle = Math.signum(deltaAngle) * minAngleChange;
        }
        angleOfBullet += 2 * deltaAngle;
        xVector = step * Math.cos(Math.toRadians(angleOfBullet));
        yVector = -step * Math.sin(Math.toRadians(angleOfBullet));
    }
}
