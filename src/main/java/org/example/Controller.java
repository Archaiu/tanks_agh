package org.example;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Controller {
    @FXML
    private AnchorPane mainPlansza;
    int _number;
    public AnchorPane getMainPlansza()
    {
        System.out.println("AnchorPane returned!");
        return mainPlansza;
    }
    public Controller(int number)
    {
        this._number = number;
    }
}
