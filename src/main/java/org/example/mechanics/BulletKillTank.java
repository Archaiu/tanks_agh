package org.example.mechanics;

import javafx.geometry.Point2D;
import org.example.player.Tank;

import java.util.Arrays;

public class BulletKillTank {
    private double _x;
    private double _y;
    private Tank _tank;
//    BulletKillTank(double x, double y, ServerTank tank)
//    {
//        _x = x;
//        _y = y;
//        _tank = tank;
////        if (tank != null)
////        {
////            System.out.println("Skipping centrain tank");
////        }
////        else
////        {
////            System.out.println("Don't skipping anything");
////        }
//    }
    public boolean checkIfTankIsKilled(double x, double y, Tank _tank)
    {
        _x = x;
        _y = y;
        for ( int i = 0; i < UserInfo.getNumberOfPlayers(); i++ )
        {
            Tank tank = UserInfo.getClasess(i).get_controller().getTank();
            if ( tank == _tank )
            {
                System.out.println( "Skip checking");
                continue;
            }
            else if ( _tank == null)
            {
                System.out.println( "Don't skip checking");
            }
            if ( checkThisTank(tank))
            {
                UserInfo.getRound().tankDestroyed(i);
                return true;
            }
        }
        return false;
    }
    public boolean checkThisTank(Tank tank)
    {
        System.out.println( "Attempt to catch destruction");
        double [][]corners = tank.calculateCorners();
        return ( checkWalls(corners) || checkCorners(corners));
    }
    public boolean checkWalls(double[][] corners)
    {
        for ( int i = 0; i < 4;i++)
        {
            int first = i;
            int second = (i + 1)%4;
            int third = (i + 2)%4;
            int fourth = (i + 3)%4;
            double xVec1 = corners[fourth][0] - corners[first][0];
            double yVec1 = corners[fourth][1] - corners[first][1];
            double xVec2 = corners[third][0] - corners[second][0];
            double yVec2 = corners[third][1] - corners[second][1];
            double len = Math.sqrt(xVec1 * xVec1 + yVec1 * yVec1);
            xVec1 = xVec1 * Bullet.radius / len;
            yVec1 = yVec1 * Bullet.radius / len;
            yVec2 = yVec2 * Bullet.radius / len;
            xVec2 = xVec2 * Bullet.radius / len;
            if ( checkIfCentreIsInRectangle(new Point2D(corners[third][0],corners[third][1]),new Point2D(corners[fourth][0],corners[fourth][1]), new Point2D(corners[third][0]+xVec2,corners[third][1] + yVec2), new Point2D(corners[fourth][0]+xVec1, corners[fourth][1]+yVec1)))
            {
                return true;
            }
        }
        return false;
    }
    private boolean checkIfCentreIsInRectangle(Point2D... tab)
    {
        Arrays.sort(tab, (Point2D a, Point2D b) -> Double.compare(a.getX(), b.getX()));
        if ( tab[1].getX()-tab[0].getX() < 0.1 )
        {
            return caseWithVerticalRectange(tab);
        }
        return caseWithObliqueLines(tab);
    }
    public boolean checkCorners(double[][] corners)
    {
        for ( var el : corners)
        {
            if ( Bullet.radius > Math.sqrt(Math.pow(el[0]-_x,2)+Math.pow(el[1]-_y,2)))
            {
                return true;
            }
        }
        return false;
    }
    private boolean caseWithVerticalRectange(Point2D[] tab)
    {
        double bottom = Math.min(tab[0].getY(), tab[1].getY());
        double top = Math.max(tab[0].getY(), tab[1].getY());
        if ( tab[0].getX() <= _x && _x <= tab[3].getX() && bottom <= _y && _y <= top )
        {
            return true;
        }
        return false;
    }
    private boolean caseWithObliqueLines(Point2D[] tab)
    {
        boolean rectangleCrookedRight = tab[0].getY() < tab[1].getY();
        double a1, a2, b1, b2;
        if ( tab[0].getX() <= _x && _x <= tab[1].getX())
        {
            a1 = (tab[1].getY() - tab[0].getY()) / (tab[1].getX() - tab[0].getX());
            a2 = (tab[2].getY() - tab[0].getY()) / (tab[2].getX() - tab[0].getX());
            b1 = tab[0].getY() - a1 * tab[0].getX();
            b2 = tab[0].getY() - a2 * tab[0].getX();
        }
        else if ( tab[1].getX() <= _x && _x <= tab[2].getX())
        {
            a1 = (tab[2].getY() - tab[0].getY()) / (tab[2].getX() - tab[0].getX());
            a2 = (tab[3].getY() - tab[1].getY()) / (tab[3].getX() - tab[1].getX());
            b1 = tab[0].getY() - a1 * tab[0].getX();
            b2 = tab[1].getY() - a2 * tab[1].getX();
        }
        else if ( tab[2].getX() <= _x && _x <= tab[3].getX())
        {
            a1 = (tab[3].getY() - tab[1].getY()) / (tab[3].getX() - tab[1].getX());
            a2 = (tab[3].getY() - tab[2].getY()) / (tab[3].getX() - tab[2].getX());
            b1 = tab[1].getY() - a1 * tab[1].getX();
            b2 = tab[2].getY() - a2 * tab[2].getX();
        }
        else
        {
            return false;
        }
        double y1 = a1 * _x + b1;
        double y2 = a2 * _x + b2;
        if ( (y1 <= _y && _y <= y2) || (y2 <= _y && _y <= y1) )
        {
            return true;
        }
        return false;
    }
}
