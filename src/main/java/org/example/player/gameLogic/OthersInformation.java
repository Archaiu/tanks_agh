package org.example.player.gameLogic;


import org.example.common.POJO.MyPair;

public class OthersInformation extends Informations
{
    public OthersInformation(MyPair<String,Integer> player)
    {
        super(player.getKey(), player.getValue());

    }
}
