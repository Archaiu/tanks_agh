package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

//
public class MainPage{
    @FXML
    private AnchorPane mainPlansza;
    int _number;
    public void showMainPage()
    {
        System.out.println("Try to load mainPage");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            mainPlansza = ((Controller)fxmlLoader.getController()).getMainPlansza();
        } catch (IOException e) {
            System.out.println("Can't load mainPage.fxml");
        }
        Scene scene = new Scene(root);
        Tank tank = UserInfo.getTank(_number);
        mainPlansza.getChildren().add(tank.getVObjectToDisplay());
        Stage stage = UserInfo.getClasess(_number).get_stage();
        stage.setScene(scene);

    }
    public MainPage( int number)
    {
        this._number = number;
    }
}
//

