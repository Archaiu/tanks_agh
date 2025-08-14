package org.example.player;

import com.google.gson.Gson;
import org.example.common.Debugger;
import org.example.common.JSONObjects.CordsOfTanks;
import org.example.common.JSONObjects.InfoAboutUsers;
import org.example.common.JSONObjects.PlayersKeys;
import org.example.common.JSONObjects.SerialObject;
import org.example.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Comunication
{
    private Gson gson = new Gson();

    private Comunication(){}
    private static Comunication comunication;
    public static Comunication getInstance()
    {
        if (comunication == null) comunication = new Comunication();
        return comunication;
    }

    public LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
    public volatile boolean stopConnection;
    private String _input;
    public void createPipes(Socket socket) {
        try {
            socket.setSoTimeout(5000);
            Thread in = new Thread(() ->
            {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while (true) {
                        {
                            if (stopConnection) {
                                reader.close();
                                break;
                            }
                            try {
                                StringBuilder newInput = new StringBuilder();
                                while (true)
                                {
                                    String line = reader.readLine();
                                    if (line == null) stopConnection = true;
                                    else if (line.equals(Server.getInstance().END_MESSAGE())) break;
                                    else newInput.append(line).append('\n');
                                }
                                String input = newInput.toString().trim();
                                if (input.equals("PING"))
                                {
                                    continue;
                                }

                                doSomethingWithInput(input);
//

                            } catch (IOException ignored) {
                            }
                        }
                    }
                } catch (IOException e) {
                    System.exit(1);
                }
            });
            in.start();

            Thread out = new Thread(() ->
            {
                try {
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    while (true) {
                        if (stopConnection) {
                            writer.close();
                            break;
                        }

                        String messageToSend = getMessageToSend();
//                        if ("PING".equals(messageToSend)) {System.out.print("P");}
//                        else { System.out.println(messageToSend); }
                        writer.println(messageToSend);
                        Thread.sleep(100);
                    }
                } catch (IOException | InterruptedException e) {
                    System.exit(1);
                }
            });
            out.start();

            Thread _stop = new Thread(() ->
            {
                try {
                    in.join();
                    out.join();
                    socket.close();
                } catch (InterruptedException | IOException e) {
                    System.out.println("Weird problem with closing");
                    System.exit(1);
                }
            });
            _stop.start();
        } catch (IOException e) { stopConnection =true;}
    }
    private String getMessageToSend()
    {
        if (Gamer.get_gamer().openWindow.equals("game")) { return gson.toJson(new PlayersKeys(1)) + "\n" + Server.getInstance().END_MESSAGE(); }
        if (queue.isEmpty()){return "PING\n" + Server.getInstance().END_MESSAGE();}
        return queue.poll() + "\n" + Server.getInstance().END_MESSAGE();
    }
    public void sendMessage(String message)
    {
        queue.offer(message);
    }
    public String getMessage()
    {
        return _input;
    }
    public void doSomethingWithInputWhichIsString(String input)
    {
        _input = input;
        System.out.println("Input: " + input+ " at state" + Gamer.get_gamer().openWindow);
        switch (Gamer.get_gamer().openWindow) {

            case "ip" ->
            {
                synchronized (Gamer.get_gamer().blocade) {
                    Gamer.get_gamer().openWindow = "connect";
                    Gamer.get_gamer().blocade.notify();
                }
            }
            case "connect" ->
            {
//                System.out.println("In connect state there is new input");
                if (input.equals("START"))
                {
                    Gamer.get_gamer().openWindow = "nick";
                    javafx.application.Platform.runLater(Gamer.get_gamer()::loadNickWindows);
                }
                else if(input.equals("READY")) { Gamer.get_gamer()._windows._connection.showButton();}
                else
                {
                    javafx.application.Platform.runLater(() ->
                            Gamer.get_gamer()._windows._connection.setNumbersOfPlayers(Integer.parseInt(input)));
                }
            }
            case "nick" ->
            {
//                System.out.println("In nick state there is new input");
                if (input.equals("WRONG"))
                {
                    System.out.println("Nick is selected");
                    javafx.application.Platform.runLater(()->
                            Gamer.get_gamer()._windows._nick.setError("Nick incorrect or already exist"));
                }
                else if (input.split(";")[0].equals("CORRECT"))
                {
                    System.out.println("Nick is free");
                    Gamer.get_gamer().openWindow = "uni";
                    javafx.application.Platform.runLater(()->
                    {
                        Gamer.get_gamer().loadUniWindows();
                    });
                }

            }
            case "uni" ->
            {
//                System.out.println("In uni state there is new input");
                if (input.equals("WRONG"))
                {
                    System.out.println("Uni is selected");
                    javafx.application.Platform.runLater(()->
                            Gamer.get_gamer()._windows._uni.setError("Uni is selected"));
                }
                else if (input.equals("TOO LATE"))
                {
                    System.out.println("Game has already started");
                    javafx.application.Platform.runLater(()->
                            Gamer.get_gamer()._windows._uni.setError("Game has aldready started:("));
                }
                else
                {
                    System.out.println("Uni is free");
                    Gamer.get_gamer().openWindow = "waiting";
                    javafx.application.Platform.runLater(()->
                    {
                        Gamer.get_gamer().loadWaitingWindow();
                    });
                }
            }
            case "waiting" ->
            {
                if ( input.equals("READY")) {javafx.application.Platform.runLater(Gamer.get_gamer()._windows._waiting::showButton);}
                else if ( input.equals("START"))Gamer.get_gamer().openWindow = "game";
            }
        }
    }
    public void doSomethingWithInput(String input)
    {
        if ( input.charAt(0) == '{') doSomethingWithInputWhichIsObject(input);
        else doSomethingWithInputWhichIsString(input);
    }
    public void doSomethingWithInputWhichIsObject(String input)
    {
//        System.out.println(input);
        String type = gson.fromJson(input, SerialObject.class).getType();
        switch (type)
        {
            case "InfoAboutUsers" ->
            {
                InfoAboutUsers infoAboutUsers = gson.fromJson(input, InfoAboutUsers.class);

                for ( int i = 0; i < infoAboutUsers.players.size(); i++ )
                {
                    if ( i == infoAboutUsers.numberOfPlayer)
                    {
                        PlayerInformation.createInstance(infoAboutUsers.players.get(i));
                        Gamer.get_gamer().players.add(PlayerInformation.getInstance());
                    }
                    else Gamer.get_gamer().players.add(new OthersInformation(infoAboutUsers.players.get(i)));
                }
                Gamer.get_gamer().numberOfPlayer = infoAboutUsers.numberOfPlayer;
                javafx.application.Platform.runLater(()->
                {
                    synchronized (Gamer.get_gamer().blocade)
                    {
                        Gamer.get_gamer().loadGameWindow();
                    }
                });
            }
            case "CordsOfTanks" ->
            {
                CordsOfTanks cordsOfTanks = gson.fromJson(input, CordsOfTanks.class);
                Debugger.getDebugger().printMessageNTimesPerSecond("cordsOfTanks",input,0.5);
                javafx.application.Platform.runLater(() ->
                {
                    synchronized (Gamer.get_gamer().blocade)
                    {
                        Engine.getEngine().updateObjectsOnMap(cordsOfTanks);
                    }
                });
            }
        }

    }
}
