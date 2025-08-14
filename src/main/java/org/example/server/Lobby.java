package org.example.server;

import javax.management.BadAttributeValueExpException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Lobby
{
    private Lobby() {}
    private static Lobby _lobby;
    public static Lobby getInstance()
    {
        if  (_lobby == null)
        {
            _lobby = new Lobby();
        }
        return _lobby;
    }

    private final int limitOfClients = 8;

    public AtomicReference<Integer> numberOfUsers = new AtomicReference<>(0);
    private volatile boolean hostCanPauseLobby = false;
    public volatile boolean startGame = false;

    public boolean checkIfLobbyCanBeEnded(){return hostCanPauseLobby;}

    private void sendSizeOfLobby()
    {
        Server.getInstance().writeToAll(Integer.toString(Server.getInstance().getPlayers_().size()));
        System.out.println("Size of lobby sended");
    }

    private synchronized boolean createNewUser(Player pl)
    {
        Server.getInstance().getPlayers_().add(pl);
        return Server.getInstance().getPlayers_().size() == 1;
    }

    private void allowHostToStartGame() throws IOException
    {
        if (hostCanPauseLobby) return;
        hostCanPauseLobby = true;
        System.out.println("Allowing host to start game");
        Server.getInstance().getPlayers_().getFirst().communication.writeMessage("READY");
    }

    public void createServerForPublic() throws IOException, BadAttributeValueExpException, InterruptedException
    {
        AtomicReference<Boolean> problemWithPort = new AtomicReference<>(true);
        Server server = Server.getInstance();
        try {
            server.setServerSocket(new ServerSocket(server.port));
            Thread currentThread = Thread.currentThread();
            final Thread control = new Thread( () ->
            {
                try {
                    Scanner scanner = new Scanner(System.in);
                    while (true)
                    {
                        if ( startGame ) return;
                        if (scanner.nextLine().equals("")) break;
                    }
                    problemWithPort.set(false);
                    server.getServerSocket().close();
                    System.exit(1);
                } catch (IOException e) {return ;}
            });
            control.start();
            System.out.println("Server Start To Run");

            Thread lobby = new Thread( () ->
            {
                try(ExecutorService executor = Executors.newFixedThreadPool(limitOfClients))
                {
                    System.out.println("Ready to new clients");
                    server.getServerSocket().setSoTimeout(3000);

                    while (true)
                    {
                        if (startGame)
                        {
                            System.out.println("Host give order");
                            executor.shutdown();
                            server.getServerSocket().close();
                            server.createNickAndTank();
                            return;
                        }
                        if (numberOfUsers.get() >= limitOfClients) continue;

                        try {
                            Socket socket = server.getServerSocket().accept();
                            System.out.println();
                            numberOfUsers.set(numberOfUsers.get() + 1);
                            executor.submit(() ->
                            {
                                System.out.println("New Client Connected");
                                try {
                                    Player p = new Player(socket);
                                    System.out.println("Constructor was triggered");
                                    if (handleNewConnection(p).equals("PLAYER")) {
                                        p.communication.writeMessage("CORRECT;" + (createNewUser(p) ? "HOST" : "USER"));
                                        sendSizeOfLobby();
                                        System.out.println("New user connected!");
                                        if (server.getPlayers_().size() >= server.minimumNumberOfClients) allowHostToStartGame();

                                    } else {
                                        p.communication.writeMessage("ERROR");
                                        p.endPlayer();
                                        numberOfUsers.set(numberOfUsers.get() - 1);
                                        System.out.println("Wrong user");
                                    }
                                    System.out.println();
                                } catch (IOException | InterruptedException e) {
                                    numberOfUsers.set(numberOfUsers.get() - 1);
                                }
                            });
                        } catch (SocketTimeoutException e) {}
                    }
                }
                catch ( SocketException e)
                {
                    System.out.println("Stop looking for new users");
                    Server.getInstance().createNickAndTank();
                }
                catch (IOException e) {System.out.println("Problem with create threads for users");System.exit(1);}



            });
            lobby.start();
        }
        catch (IOException e)
        {
            System.out.println("Problem with ports");
            throw e;
        }
    }
    private String handleNewConnection(Player pl) throws IOException, InterruptedException
    {
        String output;
        synchronized (pl.blocade) {

            if ( !pl.communication.stringReceived) {System.out.println("Wait for input");pl.blocade.wait(5000);}
        }
        output = pl.communication.getMessage();
        System.out.println("Command from socket: " + output);
        return output;
    }
}
