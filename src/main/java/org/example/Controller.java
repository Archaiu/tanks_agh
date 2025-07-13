package org.example;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import javax.management.BadAttributeValueExpException;

public class Controller {

    private double xCord = 0;
    private double yCord = 0;
    private boolean left;


    @FXML
    private AnchorPane mainPlansza;

    private int _number;
    private Timer timer;
    private Tank threadTank;
    @FXML
    private Line leftWall;
    @FXML
    private Line rightWall;
    @FXML
    private Line topWall;
    @FXML
    private Line bottomWall;
    @FXML
    private Rectangle box1, box2, box3;
    @FXML
    private Label numberOfRound;
    private long systemTime = 0;
    boolean unlimitedBullets = false;

    public Controller(int number)
    {
        _number = number;
    }
    public AnchorPane getMainPlansza()
    {
//        System.out.println("AnchorPane returned!");
        return mainPlansza;
    }

    public void addTank(Tank tank) {
        System.out.println("Try to add Tank");
        threadTank = tank;
        mainPlansza.setOnMousePressed(event ->
        {
            if(event.isPrimaryButtonDown() && event.isSecondaryButtonDown())
            {
                return;
            }
            //System.out.println("Czolg rusza");
            if ( event.isPrimaryButtonDown())
            {
                left = true;
            }
            else {
                left = false;
            }
            timer = new Timer();
            timer.start();
        });
        mainPlansza.setOnMouseReleased( event ->
        {
            if ( !event.isPrimaryButtonDown() && !event.isSecondaryButtonDown())
            {
                //System.out.println("Czolg sie zatrzymuje");
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
        topWall.getScene().setOnKeyPressed(event ->{
            if (event.getCode() == KeyCode.SPACE && System.currentTimeMillis() > systemTime + (unlimitedBullets ? 1 : 650) ) {
                systemTime = System.currentTimeMillis();
                //System.out.println("Try to shot");
                new Bullet(threadTank, this);
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

        public void handle(long now) {
            if (now > startTime + 10000000) {
                if (counter > 0) {
                    threadTank.moveTank(xCord, yCord, left, mainPlansza);
                    startTime = now;
                } else {
                    stop();
                }
            }
        }
    }
    public Line getLine(String word) throws BadAttributeValueExpException
    {
        return switch (word)
        {
            case "left" -> leftWall;
            case "right" -> rightWall;
            case "top" -> topWall;
            case "bottom" -> bottomWall;
            default -> throw new BadAttributeValueExpException(word);
        };
    }
    public Rectangle getRectangle(int number) throws BadAttributeValueExpException
    {
        return switch (number)
        {
            case 1 -> box1;
            case 2 -> box2;
            case 3 -> box3;
            default -> throw new BadAttributeValueExpException(number);
        };
    }
    public Tank getTank()
    {
        return threadTank;
    }
    public void destroyTank()
    {
        if ( timer != null ) {timer.stop();}
        mainPlansza.getChildren().remove(threadTank.getVObjectToDisplay());
        mainPlansza.setOnMousePressed(event ->{});
        mainPlansza.setOnMouseReleased(event ->{});
        mainPlansza.setOnMouseDragged(event ->{});
        topWall.getScene().setOnKeyPressed(event ->{});
        threadTank = null;
    }
    public void setVisualNumberOfRound(int n)
    {
        numberOfRound.setText("Round number: " + n);
    }
}
