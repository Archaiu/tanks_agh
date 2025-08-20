package org.example.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.common.JSONObjects.CordsOfTanks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Round
{

    private long tankTime;
    private long bulletTime;
    private int numberOfAliveTanks = Server.getInstance().getPlayers_().size();

    private static int numberOfRounds;
    private Thread engine;
    private volatile boolean stopEngine;
    Round()
    {
        System.out.println("New round");
        numberOfRounds++;
        for ( var player : Server.getInstance().getPlayers_())
        {
            player.tank.setCordsToSpawnTank();
        }
    }

    private HashMap<UUID,ServerBullet> bullets = new HashMap<>();


    public HashMap<UUID,ServerBullet> getBullets()
    {
        return bullets;
    }

    public void addBullet(ServerBullet bullet)
    {
        bullets.put(bullet.getUuid(),bullet);
    }


    public void deleteBullet(ServerBullet bullet)
    {
        synchronized(this)
        {
            bullets.remove(bullet.getUuid());
        }
    }

    public void tankDestroyed(ServerTank tank)
    {
        tank.killTank();
        numberOfAliveTanks--;
        if ( numberOfAliveTanks < 2 )
        {
            System.out.println("There is only one tank at game");
            for (int i = 0; i < Server.getInstance().getPlayers_().size(); i++) {
                if (Server.getInstance().getPlayers_().get(i).tank.isAlive()) {
                    Server.getInstance().updateResult(i);
                }
            }
            stopEngine = true;
        }
    }

    private String tankCords = new CordsOfTanks().toString();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public void startEngine()
    {
        while (true)
        {
            if (stopEngine) return;
            if ( System.currentTimeMillis() - tankTime > 18 )
            {
                for (var player : Server.getInstance().getPlayers_())
                {
                    player.tank.tankAction();
                }
                for ( var bullet : new ArrayList<ServerBullet> (bullets.values()) )
                {
                    bullet.bulletAction();
                }
                tankCords = gson.toJson(new CordsOfTanks());
                tankTime = System.currentTimeMillis();
            }

//                Server.getInstance().writeToAll(cords);
        //                    Debugger.getDebugger().printMessageNotOften3((new CordsOfTanks()).toString(),300);
        //                    System.out.println("X");
            }
    }



    public String getTankCords()
    {
        return tankCords;
    }




}
