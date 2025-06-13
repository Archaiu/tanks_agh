package org.example;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Controller {

    private double xCord = 0;
    private double yCord = 0;
    private boolean left;
    @FXML
    private AnchorPane mainPlansza;
    private int _number;
    private Timer timer;
    private Tank threadTank;
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
            if ( event.isPrimaryButtonDown())
            {
                left = true;
            }
            else {
                left = false;
            }
            threadTank = tank;
            timer = new Timer();
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
        mainPlansza.setOnMouseDragged(event ->
        {
            xCord = event.getX();
            yCord = event.getY();
            if (event.isPrimaryButtonDown() && ! event.isSecondaryButtonDown())
            {
                left = true;
            }
            else if ( !event.isPrimaryButtonDown() && event.isSecondaryButtonDown())
            {
                left = false;
            }
        });
        StackPane tankToDisplay = tank.getVObjectToDisplay();
        System.out.println("Cords: " + tank.getTranslate().getX() + ", " +tank.getTranslate().getY() + "Ankle: " + Double.toString(360-tank.getRotate().getAngle()));
        mainPlansza.getChildren().add(tank.getVObjectToDisplay());

    }
    class Timer extends AnimationTimer
    {
        private long startTime = System.currentTimeMillis();
        int counter = 30;

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
