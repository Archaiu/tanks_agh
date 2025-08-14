package org.example.server;

import org.example.common.Tank;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

public class Player {
    public Communication communication;
    public Socket _socket;
    public int _number;
    public String[] orders;
    public PrintWriter _out;
    public BufferedReader _in;
    public volatile String _input;
    public volatile String _output = "PING";
    public volatile boolean _endProcess = false;
    public final Object blocade = new Object();
    public volatile boolean stringReceived;

    public volatile String nick;
    public volatile Integer uniIndex;
    public volatile ServerTank tank;

    public record Keys(boolean spaceIsPressed, boolean leftKey, boolean rightKey, double mouseX, double mouseY){}
    private volatile Keys keys = new Keys(false, false, false, 0,0);

    public void setKeys(Keys newKeys){ this.keys = newKeys;}
    public Keys getKeys(){ return this.keys;}

    Player(Socket socket)
    {
        System.out.println("Player's constructor");
        communication = new Communication(this);
        _socket = socket;
        communication.startCommunication();

    }

    public int getNick() { return _number;}


    public Socket getSocket(){return _socket;}

    void endPlayer()
    {
        System.out.println("End connection with socket");
        _endProcess = true;
        if ( !Server.getInstance().state.equals("lobby") && Server.getInstance().getPlayers_().isEmpty()) Server.getInstance().endServer();
    }


}
