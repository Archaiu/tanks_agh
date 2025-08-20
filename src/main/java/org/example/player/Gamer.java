package org.example.player;

import javafx.stage.Stage;
import org.example.player.windows.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Gamer
{
    private int number;
    public final Object blocade = new Object();
    public Socket _socket;
    public String _name;
    public int numberOfPlayer;
    public Windows _windows;
    public volatile boolean host;
    public Stage stage;
    public volatile String openWindow = "ip";
    public ArrayList<Informations> players= new ArrayList<Informations>();

    public volatile boolean resultsLoaded;
    public volatile boolean readyToStartGame = false;


    private Gamer(){_windows = new Windows();}
    private static Gamer _gamer;
    public static Gamer get_gamer()
    {
        if (_gamer == null) _gamer = new Gamer();
        return _gamer;
    }

    Thread _stop;

    public void startConnection(Socket socket) throws IOException
    {
        _socket = socket;
        Comunication.getInstance().createPipes(_socket);
        stage.setOnCloseRequest(WindowEvent -> disconnect());
    }

    public void disconnect() { Comunication.getInstance().stopConnection = true; }

    public void loadNickWindows()
    {
        System.out.println("Loading nick windows");
        openWindow = "nick";
        _windows._nick = new Nick();
        _windows._nick.loadWindow();
    }

    public void loadUniWindows()
    {
        System.out.println("Loading uni windows");
        _windows._uni = new Uni();
        _windows._uni.loadWindow();
    }

    public void loadConnectionWindow()
    {
        openWindow = "connect";
        _windows._connection = new Connection();
        _windows._connection.loadWindow();
    }

    public void loadWaitingWindow()
    {
        openWindow = "waiting";
        _windows._waiting = new Waiting();
        _windows._waiting.loadWindow();
    }

    public void loadGameWindow()
    {
        openWindow = "game";
        _windows._mainPage = new MainPage();
        _windows._mainPage.loadWindow();
        Engine.getEngine().startEngine(players.size());
    }

    public void loadHostWindows()
    {
        openWindow = "host";
        _windows._roundsNumber = new RoundsNumber();
        _windows._roundsNumber.loadWindow();
    }

    public void loadResultWindow()
    {
        synchronized (Gamer.get_gamer().blocade) {
            if (Gamer.get_gamer().resultsLoaded) return;
            Gamer.get_gamer().resultsLoaded = true;
            openWindow = "result";
            _windows._results = new Results();
            _windows._results.loadWindow();
        }
    }

}
