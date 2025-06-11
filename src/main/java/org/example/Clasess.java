package org.example;

import javafx.stage.Stage;

public class Clasess {
    private Beginning _beginning;
    private Controller _controller;
    private MainPage _mainPage;
    private Uni _uni;
    private Stage _stage;
    public Clasess(int number, Beginning beginning, Stage stage)
    {
        _beginning = beginning;
        _controller = new Controller(number);
        _mainPage = new MainPage(number);
        _uni = new Uni(number);
        _stage = stage;
    }

    public Beginning get_beginning() {
        return _beginning;
    }
    public Controller get_controller() {
        return _controller;
    }
    public MainPage get_mainPage() {
        return _mainPage;
    }
    public Uni get_uni() {
        return _uni;
    }
    public Stage get_stage() {
        return _stage;
    }

}
