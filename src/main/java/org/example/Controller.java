package org.example;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Controller {

    @FXML
    private AnchorPane mainPlansza;
    int _number;
    Timer timer;
    public AnchorPane getMainPlansza()
    {
        System.out.println("AnchorPane returned!");
        return mainPlansza;
    }
    public Controller(int number)
    {
        this._number = number;
    }
    public void addTank(Tank tank)
    {
        System.out.println("Try to add Tank");
        mainPlansza.setOnMousePressed(event ->
        {
            if(event.isPrimaryButtonDown() && event.isSecondaryButtonDown())
            {
                return;
            }
            System.out.println("Czolg rusza");
            boolean flag;
            if ( event.isPrimaryButtonDown())
            {
                flag = true;
            }
            else {
                flag = false;
            }
            timer = new Timer(tank,event.getX(),event.getY(),flag);
            timer.start();

        });
        mainPlansza.setOnMouseReleased( event ->
        {
            if ( !event.isPrimaryButtonDown() && !event.isSecondaryButtonDown())
            {
                System.out.println("Czolg sie zatrzymuje");
                timer.stop();
            }
        });
        StackPane tankToDisplay = tank.getVObjectToDisplay();
        System.out.println("Cords: " + tank.getTranslate().getX() + ", " +tank.getTranslate().getY() + "Ankle: " + Double.toString(360-tank.getRotate().getAngle()));
        mainPlansza.getChildren().add(tank.getVObjectToDisplay());

    }
    class Timer extends AnimationTimer
    {
        private Tank threadTank;
        private long startTime;
        private double xCord;
        private double yCord;
        private boolean left;
        int counter = 30;


        Timer(Tank tank, double x, double y,  boolean flag)
        {
            System.out.println("Tworzy się AnimationTimer");
            threadTank = tank;
            startTime = System.currentTimeMillis();
            xCord = x;
            yCord = y;
            left = flag;
        }
        public void handle(long now)
        {
            if ( now > startTime + 10000000 )
            {
                if (counter > 0) {
                    threadTank.moveTank(xCord, yCord, left);
                    startTime = now;
                }
                else {
                    stop();
                }
            }
        }
    }
}
