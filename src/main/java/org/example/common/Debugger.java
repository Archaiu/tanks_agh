package org.example.common;

import org.example.player.Gamer;

public class Debugger
{
    private int time1;
    private int time2;
    private int time3;

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

    public void printMessageNotOften(String message, int period)
    {
        if ( time1 == 0 ) System.out.println(message);
        time1++;
        if (time1 == period) time1 = 0;
    }
    public void printMessageNotOften2(String message, int period)
    {
        if ( time2 == 0 ) System.out.println(message);
        time2++;
        if (time2 == period) time2 = 0;
    }
    public void printMessageNotOften3(String message, int period)
    {
        if ( time3 == 0 ) System.out.println(message);
        time3++;
        if (time3 == period) time3 = 0;
    }
}
