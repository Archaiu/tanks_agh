package org.example.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.common.Debugger;
import org.example.common.JSONObjects.CordsOfTanks;

import java.util.ArrayList;

public class Round
{
    private static int numberOfRounds;
    private ArrayList<ServerBullet> bullets;
    private Thread engine;
    private volatile boolean stopEngine;
    Round()
    {
        numberOfRounds++;
        for ( var player : Server.getInstance().getPlayers_())
        {
            player.tank.setCordsToSpawnTank();
        }
        Server.getInstance().writeToAll(new CordsOfTanks());
    }

    public void TankDestroyed(int i)
    {

    }

    private String tankCords = new CordsOfTanks().toString();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public void startEngine()
    {
        try
        {
            while (true)
            {
                if (stopEngine) return;
                for (var player : Server.getInstance().getPlayers_())
                {
                    player.tank.tankAction();
                }
                tankCords = gson.toJson(new CordsOfTanks());
//                Server.getInstance().writeToAll(cords);
        //                    Debugger.getDebugger().printMessageNotOften3((new CordsOfTanks()).toString(),300);
        //                    System.out.println("X");
                Thread.sleep(8);
            }
        }catch(InterruptedException ignored){System.exit(1);}
    }

    public String getTankCords()
    {
        return tankCords;
    }

    public void stopEngine()
    {

    }
}
