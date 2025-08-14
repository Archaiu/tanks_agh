package org.example.player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class MainPage implements Window
{
    private final Controller _controller = new Controller();
    public Controller getController()
    {
        return _controller;
    }

    @Override
    public void loadWindow()
    {
        Parent root = null;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
            loader.setController(_controller);
            root = loader.load();
        } catch (IOException e) {
            System.out.println("Error loading FXML for mainPage");
            System.exit(1);
        }
        Scene scene = new Scene(root);
        Gamer.get_gamer().stage.setScene(scene);
    }
}
