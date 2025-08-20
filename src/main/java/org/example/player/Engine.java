package org.example.player;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.example.common.Debugger;
import org.example.common.JSONObjects.CordsOfTanks;
import org.example.common.POJO.MyPair;
import org.example.common.POJO.MyRotate;
import org.example.common.POJO.MyTranslate;
import org.example.server.Server;
import org.example.server.ServerTank;

import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Engine
{
    private HashMap<UUID,Bullet> bullets =  new HashMap<>();
    public int[] results;

    private Engine()
    {}
    private static Engine _engine;
    public static Engine getEngine()
    {
        if ( _engine == null) _engine = new Engine();
        return _engine;
    }

    public void startEngine(int number)
    {
        Debugger.getDebugger().printPlayersFromPlayer();
        Gamer.get_gamer()._windows._mainPage.getController().addTanksToController();
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now)
            {
                Controller controller = Gamer.get_gamer()._windows._mainPage.getController();
            }
        };
        results = new int[number];
        Gamer.get_gamer()._windows._mainPage.getController().updateResults();
        timer.start();
    }

    private void updateCordsOfTank(ArrayList<MyPair<MyTranslate, MyRotate>> tanks)
    {
        synchronized (Gamer.get_gamer().blocade)
        {
             {
                for (int i = 0; i < Gamer.get_gamer().players.size(); i++) {
                    Gamer.get_gamer().players.get(i).tank.getTranslate().setX(tanks.get(i).getKey().getX());
                    Gamer.get_gamer().players.get(i).tank.getTranslate().setY(tanks.get(i).getKey().getY());
                    Gamer.get_gamer().players.get(i).tank.getRotate().setAngle(tanks.get(i).getValue().getAngle());
                }
            }
        }
    }

    public void updateObjectsOnMap(CordsOfTanks cordsOfTanks)
    {
        updateCordsOfTank(cordsOfTanks.tanks);
        updateBullets(cordsOfTanks.bullets);
        if (!Arrays.equals(cordsOfTanks.results, results))
        {
            results = cordsOfTanks.results;
            System.out.println(Arrays.toString(results));
            Gamer.get_gamer()._windows._mainPage.getController().updateResults();
        }
    }

    private void updateBullets(ArrayList<MyPair<UUID, MyTranslate>>  bulletsToUpdate)
    {
        HashMap<UUID, Bullet> newBullets = new HashMap<>();
        for (MyPair<UUID, MyTranslate> el : bulletsToUpdate) {
            if (bullets.containsKey(el.getKey()))
            {
                bullets.get(el.getKey()).setCords(el.getValue().getX(),  el.getValue().getY());

            }
            else
            {
                Bullet bullet = new Bullet();
                bullets.put(el.getKey(), bullet);
                bullet.setCords(el.getValue().getX(),  el.getValue().getY());
                Gamer.get_gamer()._windows._mainPage.getController().getPane().getChildren().add(bullet.getCircle());
                System.out.println("Add new bullet");
            }
            newBullets.put(el.getKey(), bullets.get(el.getKey()));
        }
        for (var el : bullets.keySet())
        {
            if (!newBullets.containsKey(el))
            {
                Gamer.get_gamer()._windows._mainPage.getController().getPane().getChildren().remove(bullets.get(el).getCircle());
            }
        }
        bullets = newBullets;
    }
}
