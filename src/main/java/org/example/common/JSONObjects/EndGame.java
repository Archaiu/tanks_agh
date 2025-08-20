package org.example.common.JSONObjects;

import org.example.server.Server;

public class EndGame extends SerialObject
{
    public int[] results;

    public EndGame()
    {
        type = "EndGame";
        results = Server.getInstance().getResults();
    }

}
