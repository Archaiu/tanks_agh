package org.example.player;

import org.example.common.POJO.MyPair;


public class PlayerInformation extends Informations
{

    public static void createInstance(MyPair<String, Integer> data)
    {
        if ( instance != null) throw new RuntimeException();
        instance = new PlayerInformation(data.getKey(), data.getValue());
    }

    public PlayerInformation(String name, int index)
    {
        super(name, index);

    }
    private static PlayerInformation instance;
    public static PlayerInformation getInstance()
    {
        if  (instance == null) throw new RuntimeException("PlayerInformation instance has not been created");
        if ( instance.uniIndex != -1 && instance.tank == null) instance.tank = new Tank(instance.uniIndex);
        return instance;
    }



}
