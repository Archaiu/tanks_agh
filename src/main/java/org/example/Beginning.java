package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Beginning extends Application {
    Stage stage = null;
    int number;
    @FXML
    TextField loginButtom;
    @FXML
    Button nameSetted;
//    @FXML
    @Override
    public void start(Stage stagee)
    {
        stage = stagee;
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/beginning.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e)
        {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            e.printStackTrace();
            stagee.close();
            System.exit(0);
        }


        stage.setTitle("Tanks AGH");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void tryToCreateUser(ActionEvent event)
    {

        try
        {
            System.out.println("DEBUG: Beginning.stage w tryToCreateUser(): " + stage);
            number = UserInfo.CreateUser(stage, this, loginButtom.getText());
            System.out.println("Go to load uni Scene");
            UserInfo.getClasess(number).get_uni().showUni();
        } catch (IllegalArgumentException e)
        {
            nameSetted.setText(e.getMessage() + ", give valid name");
        } catch (Exception e) {
            System.out.println("Problem with saved user's data");
            e.printStackTrace();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {}
            stage.close();
            System.exit(0);
        }


    }




    private void createFakeUser()
    {
        try {
            UserInfo.CreateUser(null,null, "asd");
        } catch (Exception e) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {}
            stage.close();
            return;
        }
    }

    public static void main(String[] args) {
        System.out.println("Everything started");
        Application.launch(args);
    }
}
