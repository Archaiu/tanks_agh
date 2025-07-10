package org.example;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class TestCordsOfTank
{
    public Circle circle = new Circle(4);
    public Translate translate = new Translate();
    public TestCordsOfTank()
    {
        circle.getTransforms().add(translate);
    }
    public void changeColor(Color color)
    {
        circle.setFill(color);
    }

}
