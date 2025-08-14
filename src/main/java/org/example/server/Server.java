package org.example.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.common.JSONObjects.InfoAboutUsers;
import org.example.common.MapInfo;
import org.example.common.JSONObjects.SerialObject;
import javax.management.BadAttributeValueExpException;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {

    public String END_MESSAGE()
    {
        return "$$%%xxxxxxxxxxxxxx%%$$";
    }


    public final Object monitor = new Object();
    private static Server _server;
    private Lobby _lobby = Lobby.getInstance();
    private ServerSocket _serverSocket;
    public ServerSocket getServerSocket() {return _serverSocket;}
    public void setServerSocket(ServerSocket serverSocket) {this._serverSocket = serverSocket;}
    private volatile ArrayList<Player> players_ = new ArrayList<>();
    public ArrayList<Player> getPlayers_() {return players_;}
    public final int port = 5556;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Round round;
    public Round getRound() {return round;}

    public volatile boolean writeAllTheTime = false;
    public volatile String state = "lobby";
    public final int minimumNumberOfClients = 1;


    public static Server getInstance()
    {
        if(_server == null){
            _server = new Server();
        }
        return _server;
    }
    private static void initalizeServer() throws IOException, BadAttributeValueExpException, InterruptedException
    {
        _server = Server.getInstance();
        try{_server._lobby.createServerForPublic();}
        catch(Exception e) {System.out.println("Can't create lobby"); }


    }
    public static void main(String[] args)  throws Exception
    {
        initalizeServer();
    }
    public void createNickAndTank()
    {
        for ( int i = 0; i < players_.size(); i++ )
        {
            players_.get(i)._number=i;
            System.out.println("Player with number: " + players_.get(i)._number + " " + players_.get(i)._socket.getInetAddress().getHostName());
        }
        writeToAll("START");
        state = "accounts";
        synchronized (monitor)
        {
            try {monitor.wait();}
            catch (InterruptedException ignored){}
        }
//        initializeMap();
        startGame();
    }

    public void writeToAll(SerialObject serialObject)
    {
        writeToAll(gson.toJson(serialObject));
    }

    public void writeToAll(String arg)
    {
        for ( var player : Server.getInstance().getPlayers_())
        {
            synchronized (player.blocade)
            {
                player.communication.writeMessage(arg);
            }
        }
    }

    public void startGame()
    {
        System.out.println("Game started");
        state = "game";
        MapInfo.setCords();
        for ( int i = 0; i < players_.size(); i++ )
        {
            players_.get(i).tank = new ServerTank(players_.get(i).uniIndex, players_.get(i));
            players_.get(i).communication.writeMessage(new InfoAboutUsers(i));
        }
        startRound();
    }

    public void startRound()
    {

        round = new Round();
        round.startEngine();
        startGame();
    }

//    public void initializeMap()
//    {
//        System.out.println("Map initialized");
//    }

    public void endServer()
    {
        System.out.println("Server ended");
        System.exit(0);
    }


}
