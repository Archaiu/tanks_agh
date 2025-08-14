package org.example.mechanics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.player.Tank;

import java.io.IOException;

//
public class MainPage{
    @FXML
    int _number;
    public void showMainPage()
    {
        System.out.println("Try to load mainPage");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainPage.fxml"));
        Parent root = null;
        Controller contr= UserInfo.getClasess(_number).get_controller();
        try {
            fxmlLoader.setController(contr);
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.out.println("Can't load mainPage.fxml");
        }
        Scene scene = new Scene(root);
        Tank tank = UserInfo.getTank(_number);
        contr.addTank(tank);
        Stage stage = UserInfo.getClasess(_number).get_stage();
        stage.setScene(scene);

    }
    public MainPage( int number)
    {
        this._number = number;
    }
}
//

