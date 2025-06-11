package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;

public class Uni {
    @FXML
    private AnchorPane  _pane;
    private int _number;
    private boolean _choise = false;
    @FXML
    RadioButton button1, button2, button3, button4, button5, button6, button7, button8;
    RadioButton []buttons = new RadioButton[8];
    public Uni(int number) {_number = number; }
    public void showUni(){
        System.out.println("Try to load Uni scene");
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/uni.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (Exception ex) {
            System.out.println("Error in loading Uni scene");
            ex.printStackTrace();
            UserInfo.getClasess(_number).get_stage().close();
            System.exit(0);
        }
        setUpButtons();
        Scene scene = new Scene(root);
        System.out.println("Uni scene Loaded");
        for (int i = 0; i < 8; i++)
        {
            ImageView wrapper = new ImageView(UserInfo.getPhoto(i));
            wrapper.setFitHeight(50);
            wrapper.setFitWidth(50);
            buttons[i].setGraphic(wrapper);
        }
        System.out.println("Photos to Uni loaded");
        UserInfo.getClasess(_number).get_stage().setScene(scene);
    }

    public void confirmUni(ActionEvent event) throws IOException
    {
        if ( !_choise ) {
            return;
        }
        int chosedUni = -1;
        for ( int i = 0; i < 8; i++ )
        {
            if (buttons[i].isSelected())
            {
                chosedUni = i;
                break;
            }
        }
        UserInfo.setTank(_number, new Tank(chosedUni));
        _pane.getChildren().clear();
        StackPane tankView = UserInfo.getTank(_number).getVObjectToDisplay();
        _pane.getChildren().add(tankView);
        UserInfo.getClasess(_number).get_mainPage().showMainPage();
//        AnchorPane.setTopAnchor(tankView, 0.0);
//        AnchorPane.setRightAnchor(tankView, 0.0);
//        AnchorPane.setBottomAnchor(tankView, 0.0);
//        AnchorPane.setLeftAnchor(tankView, 0.0);
    }

    public void buttonSelected( ActionEvent event ){
        for ( int i = 0; i < 8; i++ ) {
            if ( buttons[i].isSelected() ) {
                _choise = true;
            }
        }
    }

    private void setUpButtons(){
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
        buttons[4] = button5;
        buttons[5] = button6;
        buttons[6] = button7;
        buttons[7] = button8;
    }
};
