package org.example.common.POJO;

public class MyVBox {
    private String id;
    private double layoutX;
    private double layoutY;
    private double prefWidth;
    private double prefHeight;
    private volatile MyTranslate translate; // Referencja do obiektu MyTranslate
    private volatile MyRotate rotate;       // Referencja do obiektu MyRotate

    public MyVBox() {}

    public MyVBox(String id, double layoutX, double layoutY, double prefWidth, double prefHeight) {
        this.id = id;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;
    }

    // Gettery
    public String getId() { return id; }
    public double getLayoutX() { return layoutX; }
    public double getLayoutY() { return layoutY; }
    public double getPrefWidth() { return prefWidth; }
    public double getPrefHeight() { return prefHeight; }
    public MyTranslate getTranslate() { return translate; }
    public MyRotate getRotate() { return rotate; }

    // Metody do dodawania transformacji (po jednym obiekcie)
    public void setTranslate(MyTranslate translate) { this.translate = translate; }
    public void setRotate(MyRotate rotate) { this.rotate = rotate; }

    // Pomocnicze metody do szybkiego dostępu do wartości z translate/rotate
    public double getTranslateX() { return translate != null ? translate.getX() : 0.0; }
    public double getTranslateY() { return translate != null ? translate.getY() : 0.0; }
    public double getRotateAngle() { return rotate != null ? rotate.getAngle() : 0.0; }

    @Override
    public String toString() {
        return "MyVBox{" +
                "id='" + id + '\'' +
                ", layoutX=" + layoutX +
                ", layoutY=" + layoutY +
                ", prefWidth=" + prefWidth +
                ", prefHeight=" + prefHeight +
                ", translate=" + translate +
                ", rotate=" + rotate +
                '}';
    }
}