package org.example.common.POJO;

public class MyLine {
    private String id;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private MyTranslate translate; // Referencja do obiektu MyTranslate
    private MyRotate rotate;       // Referencja do obiektu MyRotate


    public MyLine(String id, double startX, double startY, double endX, double endY) {
        this.id = id;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    // Gettery
    public String getId() { return id; }
    public double getStartX() { return startX; }
    public double getStartY() { return startY; }
    public double getEndX() { return endX; }
    public double getEndY() { return endY; }
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
        return "MyLine{" +
                "id='" + id + '\'' +
                ", startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                ", translate=" + translate +
                ", rotate=" + rotate +
                '}';
    }
}