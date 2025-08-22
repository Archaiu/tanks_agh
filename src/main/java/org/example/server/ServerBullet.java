package org.example.server;


import javafx.geometry.Point2D;
import org.example.common.others.MapInfo;
import org.example.common.POJO.MyCircle;
import org.example.common.POJO.MyTranslate;

import java.util.ArrayList;
import java.util.UUID;

public class ServerBullet {
    private final MyCircle bullet;
    private final MyTranslate translate;
    double ankle;
    double step = 1.2;
    double xVector;
    double yVector;
    static int radius = 2;
    private ServerTank parent;
    private ServerBulletKillTank bulletKillTank = new ServerBulletKillTank();
    private final UUID uuid = UUID.randomUUID();
    private int counter = 1000;
    ServerBullet(ServerTank tank)
    {
        parent = tank;
        if ( parent == null)
        {
            System.err.println("Bullet isn't corrected created");
        }
        var startPoint = calculatePosition(tank);
        bullet = new MyCircle(radius);
        translate = new MyTranslate(startPoint.getX(),startPoint.getY());
        bullet.setTranslate(translate);
        if ( collision())
        {
            Server.getInstance().getRound().tankDestroyed(parent);
            return;
        }
        ankle = (360 - tank.getRotate().getAngle() + 180)%360;
        xVector = step * Math.cos(Math.toRadians(ankle));
        yVector = step * Math.sin(Math.toRadians(ankle));

    }

    public void bulletAction()
    {
        if ( counter == 0 || moveBullet()) {Server.getInstance().getRound().deleteBullet(this);}
        counter--;
        moveBullet();
    }

    private boolean moveBullet()
    {
        translate.setX(translate.getX() + xVector);
        translate.setY(translate.getY() - yVector);
        if (collision())
        {
//            System.out.println("Collision");
            parent = null;
        }
        return bulletKillTank.checkIfTankIsKilled(translate.getX(), translate.getY(), parent);

    }
    Point2D calculatePosition(ServerTank tank)
    {
        double ankle = (360 - tank.getRotate().getAngle() + 180)%360;
        double radians = Math.toRadians(ankle);
        double Ystep = -Math.cos(radians) * tank.getVObjectToDisplay().getPrefHeight()/2.0;
        double Xstep = -Math.sin(radians) * tank.getVObjectToDisplay().getPrefHeight()/2.0;
        Point2D centreOfTank = new Point2D ( tank.getTranslate().getX() + Xstep,tank.getTranslate().getY() + Ystep);
        double newAnkle = (ankle + 180) % 360;
        System.out.println("Ankle: " + ankle + " newAnkle: " + newAnkle);
        double newRadians = Math.toRadians(newAnkle);
        double newX = -(ServerBullet.radius*0.5)* Math.cos(newRadians);
        double newY = (ServerBullet.radius*0.5)* Math.sin(newRadians);
        return new Point2D(centreOfTank.getX() + newX,centreOfTank.getY() + newY);
//        return centreOfTank;
//        return new Point2D(tank.getTranslate().getX(),tank.getTranslate().getY() );
    }
    boolean collision()
    {
        var map = MapInfo.mapBullets;
        boolean itIsCorner = false;
        var iterator = new ArrayList<double[]>(map.verLines());
        iterator.addAll(map.verBorders());
        for (double[] element : iterator) {
            if (radius > Math.abs(translate.getX() - element[0])) {
                if (translate.getY() >= element[1] && translate.getY() <= element[2]) {
//                    System.out.println("Bullet have normal vertical collision");
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
//                    System.out.println("Bullet have normal horisontal collision");
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
//        double minAngleChange = 5.0;
//        if (Math.abs(deltaAngle) < minAngleChange) {
//            deltaAngle = Math.signum(deltaAngle) * minAngleChange;
//        }
        angleOfBullet += 2 * deltaAngle;
        xVector = step * Math.cos(Math.toRadians(angleOfBullet));
        yVector = -step * Math.sin(Math.toRadians(angleOfBullet));
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public MyTranslate getCentre()
    {
        return translate;
    }

}
