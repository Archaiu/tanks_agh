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


    public Controller() {
        System.out.println("Controller: Domyślny konstruktor wywołany.");
    }


    public Controller(int number) {
        this();
        this._number = number;
        System.out.println("Controller: Konstruktor z numerem wywołany: " + number);
    }

    @FXML
    public void initialize() {
        System.out.println("Controller: Metoda initialize() wywołana.");
    }

    public AnchorPane getMainPlansza() {
        System.out.println("AnchorPane returned!");
        return mainPlansza;
    }

    public void addTank(Tank tank) {
        System.out.println("Try to add Tank");
        if (mainPlansza != null) {
            mainPlansza.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                    return;
                }
                System.out.println("Czolg rusza");
                if (event.isPrimaryButtonDown()) {
                    left = true;
                } else {
                    left = false;
                }
                threadTank = tank;
                timer = new Timer();
                timer.start();

            });
            mainPlansza.setOnMouseReleased(event -> {
                if (!event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {
                    System.out.println("Czolg sie zatrzymuje");
                    timer.stop();
                }
            });
            mainPlansza.setOnMouseDragged(event -> {
                xCord = event.getX();
                yCord = event.getY();
                if (event.isPrimaryButtonDown() && !event.isSecondaryButtonDown()) {
                    left = true;
                } else if (!event.isPrimaryButtonDown() && event.isSecondaryButtonDown()) {
                    left = false;
                }
            });
            StackPane tankToDisplay = tank.getVObjectToDisplay();
            System.out.println("Cords: " + tank.getTranslate().getX() + ", " + tank.getTranslate().getY() + "Ankle: " + Double.toString(360 - tank.getRotate().getAngle()));
            mainPlansza.getChildren().add(tank.getVObjectToDisplay());
        } else {
            System.err.println("Błąd: mainPlansza nie została wstrzyknięta w Controller");
        }
    }

    class Timer extends AnimationTimer {
        private long startTime = System.currentTimeMillis();
        int counter = 30;

        public void handle(long now) {
            if (now > startTime + 10000000) {
                if (counter > 0) {
                    threadTank.moveTank(xCord, yCord, left);
                    startTime = now;
                } else {
                    stop();
                }
            }
        }
    }
}