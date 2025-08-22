package org.example.player.windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.player.gameLogic.Comunication;
import org.example.player.gameLogic.Gamer;

import java.io.IOException;

public class Connection implements Window
{
    public volatile boolean ready = false;
    public volatile boolean threadNeedToWait = true;

    @FXML
    Button button;
    @FXML
    Label counter;
    @Override
    public void loadWindow()
    {
        Stage stage = Gamer.get_gamer().stage;
        Parent root = null;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLfiles/lobby.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch (IOException e)
        {
            System.err.println("Failed to load FXML for Connection");
        }
        Scene scene = new Scene(root);
        if ( !Gamer.get_gamer().host) button.setVisible(false);
        synchronized (Gamer.get_gamer().blocade)
        {
            if (!ready) button.setDisable(true);
        }
        stage.setScene(scene);
    }
    public void setNumbersOfPlayers(int players)
    {
        counter.setText("Numbers of Players: " + players);
        if ( players > 2 && Gamer.get_gamer().host) {button.setDisable(false);}
    }

    public void showButton()
    {
        synchronized (Gamer.get_gamer().blocade)
        {
            ready = true;
            button.setDisable(false);
            button.setVisible(true);
        }
    }
    public void buttonClicked(ActionEvent event)
    {
        Comunication.getInstance().sendMessage("START");
    }
}
