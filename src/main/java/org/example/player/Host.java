package org.example.player;

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
import org.example.server.Server;

import javax.management.BadAttributeValueExpException;
import java.io.IOException;
import java.net.Socket;

public class Host extends Application
{


    @FXML
    public Button confirm;
    @FXML
    public TextField host;
    @FXML
    public Label error;
    public void start(Stage stage) throws IOException
    {
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/writeIP.fxml"));
        loader.setController(this);
        root = loader.load();

        Scene scene = new Scene(root);
        Gamer.get_gamer()._windows._host = this;
        Gamer.get_gamer().stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    public void confirmIP(ActionEvent a)
    {
        System.out.println("Button to confirm IP clicked");
        Socket socket = null;
        try
        {
            if (host.getText().isEmpty()) host.setText("localhost");
            socket = new Socket(host.getText(), Server.getInstance().port);
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
            error.setText("Can't connect to server on this IP, try once again");
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
