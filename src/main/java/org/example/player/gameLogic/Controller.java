package org.example.player.gameLogic;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import javax.management.BadAttributeValueExpException;

import java.util.ArrayList;

public class Controller
{
    @FXML
    private Label result;
    @FXML
    private AnchorPane mainPlansza;
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
    private ArrayList<Tank> tanks;

    public volatile boolean spaceIsPressed;
    public volatile double mouseX;
    public volatile double mouseY;
    public volatile boolean rightKey;
    public volatile boolean leftKey;

    public Controller()
    {
        tanks = new ArrayList<>();
    }

    @FXML
    private void initialize()
    {
        mainPlansza.requestFocus();
        mainPlansza.setOnKeyPressed(event -> {
            if ( event.getCode() == KeyCode.SPACE) spaceIsPressed = true;});
        EventHandler<MouseEvent> commonEventToMouse = event ->
        {
            mainPlansza.requestFocus();
            mouseY = event.getY();
            mouseX = event.getX();
            leftKey = event.isPrimaryButtonDown();
            rightKey = event.isSecondaryButtonDown();
        };
        mainPlansza.setOnMousePressed(commonEventToMouse);
        mainPlansza.setOnMouseReleased(commonEventToMouse);
        mainPlansza.setOnMouseDragged(commonEventToMouse);
        mainPlansza.setOnMouseMoved(commonEventToMouse);
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

    public void addTanksToController()
    {
        for (int i = 0; i < Gamer.get_gamer().players.size(); i++)
        {
            tanks.add(Gamer.get_gamer().players.get(i).tank);
            mainPlansza.getChildren().add(Gamer.get_gamer().players.get(i).tank.getVObjectToDisplay());
        }
    }

    public void updateResults()
    {
        System.out.println("Update results");
        StringBuffer output = new StringBuffer();
        for ( int i = 0; i < Gamer.get_gamer().players.size(); i++)
        {
            output.append(Gamer.get_gamer().players.get(i).name);
            output.append(":").append(Engine.getEngine().results[i]).append("  ");
        }
        result.setText(output.toString());
    }

    public AnchorPane getPane() { return mainPlansza; }

}
