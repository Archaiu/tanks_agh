package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Upewnij się, że ten import jest!
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
    @FXML // <-- Ta linia MUSI tu być, aby messageLabel zostało wstrzyknięte z FXML
    Label messageLabel; // <-- Ta linia MUSI tu być, aby móc używać messageLabel

    @Override
    public void start(Stage stagee)
    {
        stage = stagee;
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/beginning.fxml"));
            fxmlLoader.setController(this); // <-- TA LINIA MUSI BYĆ ODKOMENTOWANA!!!
            root = fxmlLoader.load();
        } catch (IOException e)
        {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            e.printStackTrace();
            stagee.close();
            System.exit(0);
        }


        stage.setTitle("Tanks AGH");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/app_style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();
    }

    public void tryToCreateUser(ActionEvent event)
    {

        try
        {
            System.out.println("DEBUG: Beginning.stage w tryToCreateUser(): " + stage);
            // Tutaj loginButtom.getText() zadziała TYLKO jeśli fxmlLoader.setController(this); jest odkomentowane
            number = UserInfo.createUser(stage,  loginButtom.getText(), 0 ,this);
            System.out.println("Go to load uni Scene");
            UserInfo.getClasess(number).get_uni().showUni();
        } catch (IllegalArgumentException e)
        {
            // Ten blok kodu jest teraz poprawny, bo messageLabel będzie zainicjowane
            if (messageLabel != null) {
                messageLabel.setText(e.getMessage() + ", give valid name");
            } else {
                System.err.println("ERROR: messageLabel is not initialized in FXML or Controller. Check @FXML and fxmlLoader.setController(this).");
            }
        } catch (Exception e) {
            System.out.println("Problem with saved user's data");
            e.printStackTrace();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {}
            if (stage != null) {
                stage.close();
            }
            System.exit(0);
        }
    }

    private void createFakeUser()
    {
        try {
            UserInfo.createUser(null, "asd", 0, null);
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