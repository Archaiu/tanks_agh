package org.example.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.common.Debugger;
import org.example.common.JSONObjects.CordsOfTanks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Round
{

    private long tankTime;
    private long bulletTime;

    private static int numberOfRounds;
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

    private HashMap<UUID,ServerBullet> bullets = new HashMap<>();
    private ArrayList<UUID> bulletsToDelete =  new ArrayList<>();

    public ArrayList<UUID> popBulletsToDelete()
    {
        synchronized (this)
        {
            var output = bulletsToDelete;
            bulletsToDelete = new ArrayList<>();
            return output;
        }
    }

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
            bulletsToDelete.add(bullet.getUuid());
            bullets.remove(bullet.getUuid());
        }
    }

    public void tankDestroyed(ServerTank tank)
    {

    }

    private String tankCords = new CordsOfTanks().toString();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public void startEngine()
    {
        while (true)
        {
            if (stopEngine) return;
            if ( System.currentTimeMillis() - tankTime > 10 )
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

    public void stopEngine()
    {

    }


}
