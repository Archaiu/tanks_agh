package org.example.server;

import org.example.common.Debugger;
import org.example.common.JSONObjects.PlayersKeys;
import org.example.common.JSONObjects.SerialObject;

import java.io.*;
import java.net.SocketException;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.player.Engine;

public class Communication
{
    private final Player player;


    public volatile boolean stringReceived;
    public LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private volatile String _input;
    public Communication(Player arg){player=arg;}
    private BufferedReader in;
    private PrintWriter out;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public void startCommunication()
    {
        System.out.println("Starting communication with player");
        try {player.getSocket().setSoTimeout(3000);}
        catch (SocketException e) {System.out.println("Weird error");System.exit(1);}
//        System.out.println("XXXXXXXXX");
        Server server = Server.getInstance();
        Thread wr = new Thread(()->
        {
//            System.out.println("XXXXXXXXX");
            try {
                out = new PrintWriter(player.getSocket().getOutputStream(), true);
                while (true) {
                    if (player._endProcess) {
                        return;
                    }

                    String messageToSend = getMessageToSend();
                    out.println(messageToSend);


                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ignored) {
                    }
                }
            } catch (IOException e) {player.endPlayer();}
        });
        wr.start();

        Thread pn = new Thread(()->
        {
            try {
                in = new BufferedReader(new InputStreamReader(player.getSocket().getInputStream()));
                while (true) {
                    if (player._endProcess) {
                        return;
                    }
                    try {
                        StringBuilder newInput = new StringBuilder();
                        while (true)
                        {
                            String line = in.readLine();
                            if (line == null) {player.endPlayer(); return;}
                            else if (line.equals(Server.getInstance().END_MESSAGE())) break;
                            else newInput.append(line).append('\n');
                        }
                        String input = newInput.toString().trim();

                        if (input.equals("PING")) {continue;}
                        doSomethingWithInput(input);

                    } catch (IOException e) {
                        player.endPlayer();
                    }
                }
            }catch (IOException e) { player.endPlayer();}
        });
        pn.start();

        new Thread( () ->
        {
            try
            {
                wr.join();
                pn.join();
                in.close();
                out.close();
                player._socket.close();
                if (Server.getInstance().getPlayers_().remove(player)) Lobby.getInstance().numberOfUsers.set(Lobby.getInstance().numberOfUsers.get()-1);
                System.out.println("Connection with player stopped!");
            } catch ( IOException | InterruptedException ignored){System.exit(1);}
            System.out.println("End player");

        }).start();

    }
    public String getMessageToSend()
    {
        String defMessage = (Server.getInstance().state.equals("game") && Server.getInstance().getRound() != null) ? Server.getInstance().getRound().getTankCords() : "PING";
        if (queue.isEmpty()) { return defMessage + "\n" + Server.getInstance().END_MESSAGE(); }
        return queue.poll() + "\n" + Server.getInstance().END_MESSAGE();
    }

    public void writeMessage(SerialObject serialObject)
    {
//        System.out.println((gson.toJson(serialObject)));
        writeMessage(gson.toJson(serialObject));
    }

    public void writeMessage(String message)
    {
        queue.offer(message);
    }

    public String getMessage()
    {
        return _input;
    }

    public void doSomethingWithInput(String string)
    {
        if ( string.isEmpty() || string.charAt(0) != '{') doSomethingWithInputWhichIsString(string);
        else doSomethingWithInputWhichIsJSON(string);
    }
    public void doSomethingWithInputWhichIsString(String string)
    {
        _input = string;
        System.out.println("Got input: "+string + " at state "+Server.getInstance().state);
        switch ( Server.getInstance().state)
        {
            case "lobby" ->
            {
                if ( Lobby.getInstance().checkIfLobbyCanBeEnded() && player == Server.getInstance().getPlayers_().getFirst() && string.equals("START"))
                {
                    Lobby.getInstance().startGame = true;
                    return;
                }
                synchronized (player.blocade)
                {
                    stringReceived = true;
                    player.blocade.notify();
                }
            }
            case "accounts" ->
            {

//                System.out.println("State of conditions:  player.uniIndex != null: "+(player.uniIndex != null) + " string.equals(\"START\"): " +string.equals("START") + " player == Server.getInstance().getPlayers_().getFirst(): " + (player == Server.getInstance().getPlayers_().getFirst()) + " CreatingAccounts.getInstance().hostCanStartGame: " + CreatingAccounts.getInstance().hostCanStartGame);
                if (player.nick == null)
                {
                    writeMessage(CreatingAccounts.getInstance().checkIfNickIsFreeAndCreate(string, player._number) ? ("CORRECT;" + string) : "WRONG");
                }
                else if ( player.uniIndex != null && string.equals("START") && player == Server.getInstance().getPlayers_().getFirst() && CreatingAccounts.getInstance().hostCanStartGame )
                {
                    synchronized (Server.getInstance().monitor)
                    {
                        Server.getInstance().monitor.notify();
                    }
                }
                else
                {
                    if ( Server.getInstance().state.equals("game") ) { writeMessage("TOO LATE");return;};;
                    if (CreatingAccounts.getInstance().checkIfUniIsFreeAndCreate(string, player._number))
                    {
                        writeMessage("CORRECT;" + string);
                        synchronized (Server.getInstance().monitor)
                        {
                            CreatingAccounts.getInstance().numbersOfPlayersWithAccount++;
                            System.out.println("Number of created users: " + CreatingAccounts.getInstance().numbersOfPlayersWithAccount);
                            if (CreatingAccounts.getInstance().numbersOfPlayersWithAccount == Server.getInstance().minimumNumberOfClients)
                            {
                                Server.getInstance().getPlayers_().getFirst().communication.writeMessage("READY");
                                CreatingAccounts.getInstance().hostCanStartGame = true;
                            }
                        }
                        if ( CreatingAccounts.getInstance().numbersOfPlayersWithAccount == Server.getInstance().getPlayers_().size())
                        {
                            CreatingAccounts.getInstance().hostCanStartGame = true;
                        }
                    }
                    else
                    {
                        writeMessage("WRONG");
                    }
                }
            }
            case "game" ->
            {
                if ( string.charAt(0) != '{' && string.charAt(0) != '[')
                {
                    writeMessage("TOO LATE");
                }
            }
        }


    }

    public void doSomethingWithInputWhichIsJSON(String string)
    {
        String type = gson.fromJson(string, SerialObject.class).type;
        switch (type)
        {
            case "PlayersKeys" ->
            {
                PlayersKeys playersKeys = gson.fromJson(string, PlayersKeys.class);
                player.setKeys(new Player.Keys(playersKeys.spaceIsPressed, playersKeys.leftKey, playersKeys.rightKey, playersKeys.mouseX, playersKeys.mouseY));
            }
        }
    }
}
