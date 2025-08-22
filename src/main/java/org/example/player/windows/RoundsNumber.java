package org.example.player.windows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.player.gameLogic.Comunication;
import org.example.player.gameLogic.Gamer;

import java.io.IOException;

public class RoundsNumber implements Window
{
    @FXML
    TextField number;
    @FXML
    Label error;

    @Override
    public void loadWindow()
    {
        Parent root = null;
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLfiles/roundsNumber.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Unable to load FXML for host");
        }
        Scene scene = new Scene(root);
        Gamer.get_gamer().stage.setScene(scene);
    }

    public void buttonClicked(ActionEvent event)
    {
        if (number.getText().isEmpty())
        {
            error.setText("Please enter a number");
            return;
        }
        try
        {
            int num = Integer.parseInt(number.getText());
            if ( num < 1 || num > 20)
            {
                error.setText("Please enter a number between 1 and 20");
                return;
            }
            Comunication.getInstance().sendMessage(Integer.toString(num));
            System.out.println("Correct number of round was send");
            Gamer.get_gamer().loadNickWindows();
        } catch (NumberFormatException e)
        {
            error.setText("Please enter a NUMBER");
            return;
        }

    }
}
