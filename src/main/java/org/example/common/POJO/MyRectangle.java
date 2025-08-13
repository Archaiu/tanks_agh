package org.example.common.POJO;

public class MyRectangle {
    private String id;
    private double x;
    private double y;
    private double width;
    private double height;
    private volatile MyTranslate translate; // Referencja do obiektu MyTranslate
    private volatile MyRotate rotate;       // Referencja do obiektu MyRotate

    public MyRectangle(String id, double x, double y, double width, double height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Gettery
    public String getId() { return id; }
    public double getLayoutX() { return x; }
    public double getLayoutY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
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
        return "MyRectangle{" +
                "id='" + id + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", translate=" + translate +
                ", rotate=" + rotate +
                '}';
    }
}
