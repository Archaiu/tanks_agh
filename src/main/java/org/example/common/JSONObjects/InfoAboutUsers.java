package org.example.common.JSONObjects;

import org.example.common.POJO.MyPair;
import org.example.server.Server;


import java.util.ArrayList;

public class InfoAboutUsers extends SerialObject
{
    public final int numberOfPlayer;
    public final ArrayList<MyPair<String,Integer>> players;
    public InfoAboutUsers(int numberOfPlayer)
    {
        type = "InfoAboutUsers";
        this.numberOfPlayer = numberOfPlayer;
        this.players = getNicsAndUni();
    }
    public static ArrayList<MyPair<String,Integer>> getNicsAndUni()
    {
        ArrayList<MyPair<String,Integer>> nicsAndUni = new ArrayList<>();
        for ( var el : Server.getInstance().getPlayers_())
        {
            nicsAndUni.add(new MyPair<>(el.nick, el.uniIndex));
        }
        return nicsAndUni;
    }
}