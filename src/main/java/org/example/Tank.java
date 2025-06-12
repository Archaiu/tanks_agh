package org.example;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class Tank {
    private StackPane sbox;
    Translate translate;
    Rotate rotate;
    Random rand;
    double step= 0.5;

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
        rand = new Random();
        translate = new Translate();
        translate.setX(rand.nextInt(500)+200);
        translate.setY(rand.nextInt(300)+100);
        rotate = new Rotate(rand.nextDouble(360));
        sbox.getTransforms().addAll(translate, rotate);

        tankObject.setFitWidth(50);
        tankObject.setFitHeight(30);
        uniObject.setFitWidth(25);
        uniObject.setFitHeight(25);


        sbox.getChildren().addAll(tankObject,uniObject);
    }
    public StackPane getVObjectToDisplay() {
        return sbox;
    }

    public void moveTank(double x, double y, boolean flag)
    {

        double radians = Math.toRadians(360 - rotate.getAngle());
        System.out.println("Kat: " + Double.toString(360-rotate.getAngle()));
        double directionalCoefficient = Math.tan(radians);
        double offsetParameter = translate.getY() - directionalCoefficient * translate.getX();
        boolean mouseOverLine = y > (directionalCoefficient * x + offsetParameter);
        double VerticalStep = Math.sin(radians) * step;
        double HorizontalStep = Math.cos(radians) * step;
        if ( flag )
        {
            translate.setY(translate.getY() + VerticalStep);
            translate.setX(translate.getX() + HorizontalStep);
        }
        else
        {
            translate.setY(translate.getY() - VerticalStep);
            translate.setX(translate.getX() - HorizontalStep);
        }
        if ( flag && mouseOverLine || !flag && !mouseOverLine)
        {
            rotate.setAngle((rotate.getAngle() + 0.5)%360);
        }
        else
        {
            rotate.setAngle((rotate.getAngle() - 0.5)%360);
        }
    }

    public Rotate getRotate()
    {
        return rotate;
    }
    public Translate getTranslate()
    {
        return translate;
    }
}
