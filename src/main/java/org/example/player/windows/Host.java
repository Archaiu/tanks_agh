package org.example.player.windows;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.player.gameLogic.Comunication;
import org.example.player.gameLogic.Gamer;

import java.io.IOException;
import java.net.Socket;

public class Host extends Application
{


    @FXML
    public Button confirm;
    @FXML
    public TextField host, port;
    @FXML
    public Label error;

    public Scene scene;
    public void start(Stage stage)
    {
        Gamer.get_gamer().stage = stage;
        loadEverything();

    }

    public void loadEverything()
    {
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/writeIP.fxml"));
        loader.setController(this);
        try
        {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(root);
        Gamer.get_gamer()._windows._host = this;

        Gamer.get_gamer().stage.setScene(scene);
        Gamer.get_gamer().stage.show();
    }

    public void confirmIP(ActionEvent a)
    {
        System.out.println("Button to confirm IP clicked");
        Socket socket = null;
        try
        {
            if (host.getText().isEmpty()) host.setText("localhost");
            if (port.getText().isEmpty()) port.setText("5555");
            socket = new Socket(host.getText(), Integer.parseInt(port.getText()));
            Gamer.get_gamer().serverIp = host.getText();
            Gamer.get_gamer().serverPort = port.getText();
            Gamer.get_gamer().startConnection(socket);
            synchronized (Gamer.get_gamer().blocade){
                Comunication.getInstance().sendMessage("PLAYER");
                Gamer.get_gamer().blocade.wait(3000);
            }
            String message = Comunication.getInstance().getMessage();
            if ( message == null || !message.split(";")[0].equals("CORRECT")) throw new InterruptedException();
            if (message.split(";")[1].equals("HOST")) Gamer.get_gamer().host = true;
            else if ( message.split(";")[1].equals("USER")) Gamer.get_gamer().host = false;
            else throw new InterruptedException();
            System.out.println("connected to server");
            Gamer.get_gamer().loadConnectionWindow();

        } catch ( IOException | InterruptedException e)
        {
            if ( socket != null ) { Gamer.get_gamer().disconnect(); }
            System.out.println("Can't connect to server");
            error.setText("Can't connect to server on this IP and port, try once again");
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
