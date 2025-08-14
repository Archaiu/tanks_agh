package org.example.common.JSONObjects;

import org.example.common.POJO.MyPair;
import org.example.common.POJO.MyRotate;
import org.example.common.POJO.MyTranslate;
import org.example.server.Server;
import org.example.server.ServerBullet;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CordsOfTanks extends SerialObject
{
    public ArrayList<MyPair<MyTranslate, MyRotate>> tanks;
    public ArrayList<MyPair<UUID, MyTranslate>> bullets;
    public ArrayList<UUID> bulletsToRemove;
    public CordsOfTanks()
    {
        type = "CordsOfTanks";
        this.tanks = getInfo();
        bullets = getBullets();
        bulletsToRemove = getBulletsToRemove();
    }

    public ArrayList<MyPair<MyTranslate, MyRotate>> getInfo()
    {
        ArrayList<MyPair<MyTranslate,MyRotate>> info = new ArrayList<>();
        for ( var player : Server.getInstance().getPlayers_())
        {
            info.add( new MyPair<MyTranslate, MyRotate>(player.tank.getTranslate(),player.tank.getRotate()));
        }
        return info;
    }

    public  ArrayList<MyPair<UUID, MyTranslate>> getBullets()
    {
        ArrayList<MyPair<UUID, MyTranslate>> bullets = new ArrayList<>();
        try
        {
            for (var el : Server.getInstance().getRound().getBullets().values())
            {
                bullets.add(new MyPair<>(el.getUuid(), el.getCentre()));
            }
        } catch ( NullPointerException ignored){}
        return bullets;
    }

    public ArrayList<UUID> getBulletsToRemove()
    {
        if (Server.getInstance().getRound() == null) return new ArrayList<>();
        synchronized (Server.getInstance().getRound())
        {
            return Server.getInstance().getRound().popBulletsToDelete();
        }
    }

    @Override
    public String toString()
    {
        StringBuilder info = new StringBuilder();
        for ( var el : tanks )
        {
            info.append(el.toString()).append("\n");
        }
        return info.toString();
    }
}
