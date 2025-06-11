package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Tank {
    private StackPane sbox;
    public Tank(int number)
    {
        Image tankPhoto = null;

        ImageView tankObject;


        tankPhoto = new Image(getClass().getResourceAsStream("/tank.jpg"));
        if (tankPhoto == null)
        {
            System.out.println("Error: tankPhoto is null");
            System.exit(0);
        }
        tankObject = new ImageView(tankPhoto);

            System.out.println("Can't load photo of tank");

        ImageView uniObject = new ImageView(UserInfo.getPhoto(number));
        sbox = new StackPane();
//        tankObject.setX(vbox.getWidth());
//        tankObject.setY(vbox.getHeight());
//        uniObject.setX(vbox.getWidth()/2);
//        uniObject.setY(vbox.getHeight()/2);
//
        tankObject.setFitWidth(50);
        tankObject.setFitHeight(50);
        uniObject.setFitWidth(25);
        uniObject.setFitHeight(25);


        sbox.getChildren().addAll(tankObject,uniObject);
    }
    public StackPane getVObjectToDisplay() {
        return sbox;
    }

}
