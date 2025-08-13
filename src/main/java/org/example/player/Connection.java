package org.example.player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lobby.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch (IOException e)
        {
            System.err.println("Failed to load FXML for Connection");
        }
        Scene scene = new Scene(root);
        if ( !Gamer.get_gamer().host) button.setVisible(false);
        else button.setDisable(true);
        stage.setScene(scene);
//        try {
//            synchronized (Gamer.get_gamer().blocade)
//            {
//                if (threadNeedToWait)
//                {
//                    ready = true;
//                    System.out.println("State of waiting");
//                    Gamer.get_gamer().blocade.wait();
//                }
//            }
//            Gamer.get_gamer().loadNickWindows();
//
//        } catch (InterruptedException e)
//        {
//            System.err.println("Something interupted thread");
//        }
    }
    public void setNumbersOfPlayers(int players)
    {
        counter.setText("Numbers of Players: " + players);
        if ( players > 2 && Gamer.get_gamer().host) {button.setDisable(false);}
    }

    public void showButton(){button.setDisable(false);}
    public void buttonClicked(ActionEvent event)
    {
        Comunication.getInstance().sendMessage("START");
    }
}
