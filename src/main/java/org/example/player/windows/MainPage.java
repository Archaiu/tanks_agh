package org.example.player.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.example.player.Controller;
import org.example.player.Gamer;

import java.io.IOException;

public class MainPage implements Window
{
    private final Controller _controller;
    public Controller getController()
    {
        return _controller;
    }

    public MainPage()
    {
       _controller = new Controller();
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
