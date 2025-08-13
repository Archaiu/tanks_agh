package org.example.player;

import javafx.animation.AnimationTimer;
import org.example.common.Debugger;
import org.example.common.JSONObjects.CordsOfTanks;

public class Engine
{
    private Engine()
    {}
    private static Engine _engine;
    public static Engine getEngine()
    {
        if ( _engine == null) _engine = new Engine();
        return _engine;
    }

    public void startEngine()
    {
        Debugger.getDebugger().printPlayersFromPlayer();
        Gamer.get_gamer()._windows._mainPage.getController().addTanksToController();
        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now)
            {
                Controller controller = Gamer.get_gamer()._windows._mainPage.getController();
            }
        };
        timer.start();
    }

    public void updateCordsOfTank(CordsOfTanks cordsOfTanks)
    {
        synchronized (Gamer.get_gamer().blocade)
        {
             {
                for (int i = 0; i < Gamer.get_gamer().players.size(); i++) {
                    Gamer.get_gamer().players.get(i).tank.getTranslate().setX(cordsOfTanks.tanks.get(i).getKey().getX());
                    Gamer.get_gamer().players.get(i).tank.getTranslate().setY(cordsOfTanks.tanks.get(i).getKey().getY());
                    Gamer.get_gamer().players.get(i).tank.getRotate().setAngle(cordsOfTanks.tanks.get(i).getValue().getAngle());
                }
            }
        }
    }
}
