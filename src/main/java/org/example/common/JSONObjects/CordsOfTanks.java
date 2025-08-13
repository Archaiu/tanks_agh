package org.example.common.JSONObjects;

import org.example.common.POJO.MyPair;
import org.example.common.POJO.MyRotate;
import org.example.common.POJO.MyTranslate;
import org.example.server.Server;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CordsOfTanks extends SerialObject
{
    public ArrayList<MyPair<MyTranslate, MyRotate>> tanks;
    public HashMap<UUID,Double> bullets;
    public CordsOfTanks()
    {
        type = "CordsOfTanks";
        this.tanks = getInfo();
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
