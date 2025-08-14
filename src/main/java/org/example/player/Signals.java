package org.example.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.example.server.*;

public class Signals {
    public static void main(String[] args) {
        String pass = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter option \"y\": ");
        if (input.nextLine().equals("y")) {
            pass = "PLAYER";
        }
        try
        {
            Socket socket = new Socket("localhost", Server.getInstance().port);
            System.out.println("Connected to the server!");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Write, that I'm player");
            out.println(pass);
            String serverResponse = in.readLine();
            System.out.println("Readed line: "+serverResponse);
            if (serverResponse.split(";")[1].equals("HOST"))
            {
                System.out.println("Your are host, write to stop lobby");
                System.out.println(in.readLine());
            }
            else
            {
                System.out.println("Your are not host, try anyway");
            }
            System.in.read();
            System.out.println("Order to start game");
            out.println("START");
            String word = input.nextLine();
            out.println(word);
            System.out.println(in.readLine());
            input.nextLine();
        }
        catch (Exception ex) { System.out.println("Can't connect to server"); }
        input.nextLine();

    }
}
