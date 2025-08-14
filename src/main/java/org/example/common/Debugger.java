package org.example.common;

import org.example.player.Gamer;

import java.util.HashMap;

public class Debugger
{
    private HashMap<String, Long> hashMap = new HashMap<>();

    private Debugger(){}
    private static Debugger instance = new Debugger();
    public static Debugger getDebugger() { return instance; }

    public void printPlayersFromPlayer()
    {
        for ( var el : Gamer.get_gamer().players)
        {
            System.out.println(el);
        }
    }


    public void printMessageNTimesPerSecond(String code, String message, double n)
    {
        if (hashMap.containsKey(code))
        {
            long currentTime = System.currentTimeMillis();
            double period = 1000 / n;
            if (currentTime > hashMap.get(code) + period)
            {
                System.out.println(message);
                hashMap.put(code, currentTime);
            }
        }
        else
        {
            System.out.println(message);
            hashMap.put(code, System.currentTimeMillis());
        }
    }
}
