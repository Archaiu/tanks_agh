package org.example.player.windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.player.Comunication;
import org.example.player.Gamer;

import java.io.IOException;

public class Waiting implements Window
{
    @FXML
    Button button;
    @FXML
    VBox users;
    @Override
    public void loadWindow()
    {
        Stage stage = Gamer.get_gamer().stage;
        Parent root = null;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/waiting.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch ( IOException e)
        {
            System.out.println("Can't load fxml file");
            System.exit(1);
        }
        if ( !Gamer.get_gamer().readyToStartGame)
        {
            button.setDisable(true);
        }
        else
        {
            button.setDisable(false);
            button.setVisible(true);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    public void buttonClicked( ActionEvent event)
    {
        button.setDisable(true);
        button.setVisible(false);
        Comunication.getInstance().sendMessage("START");
    }
    public void showButton()
    {
        button.setVisible(true);
        button.setDisable(false);
    }
}
