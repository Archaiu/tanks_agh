package org.example;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.Map;
import java.util.Random;

public class Tank {


    private AnchorPane _pane;
    private TestCordsOfTank[] _cordsOfTank;

    private StackPane sbox;
    private Translate translate;
    private Rotate rotate;
    private Random rand;
    double step= 1;
    double ankleStep = 2.2;
    record ThreeElements(double nextVerticalStep, double nextHorisontalStep, double nextAnkle) {}
    public Tank(int number)
    {
        MapInfo.setCords();
        Image tankPhoto = null;

        ImageView tankObject;


        tankPhoto = new Image(getClass().getResourceAsStream("/tank.jpg"));
        if (tankPhoto == null)
        {
            System.out.println("Error: tankPhoto is null");
            System.exit(0);
        }
        tankObject = new ImageView(tankPhoto);

            System.out.println("Can't load photo of tank");

        ImageView uniObject = new ImageView(UserInfo.getPhoto(number));
        sbox = new StackPane();
//        tankObject.setX(vbox.getWidth());
//        tankObject.setY(vbox.getHeight());
//        uniObject.setX(vbox.getWidth()/2);
//        uniObject.setY(vbox.getHeight()/2);
//
        rand = new Random(System.currentTimeMillis());
        translate = new Translate();
//        translate.setX(rand.nextInt(537-56-60)+56+30);
//        translate.setY(rand.nextInt(350-38+60)+38+30);
        rotate = new Rotate(rand.nextDouble(360));
        setCordsToSpawnTank();
        sbox.getTransforms().addAll(translate, rotate);

        tankObject.setFitWidth(25);
        tankObject.setFitHeight(15);
        uniObject.setFitWidth(15);
        uniObject.setFitHeight(15);


        sbox.getChildren().addAll(tankObject,uniObject);
    }
    public StackPane getVObjectToDisplay() {
        return sbox;
    }

    public void moveTank(double x, double y, boolean flag)
    {
        moveTank(x,y,flag,null);
    }

    public void moveTank(double x, double y, boolean flag, AnchorPane pane)
    {
//        setCircles(pane);



        ThreeElements threeElements = calculateSteps(x, y, flag);

        translate.setY(translate.getY() + threeElements.nextVerticalStep);
        translate.setX(translate.getX() - threeElements.nextHorisontalStep);
        rotate.setAngle(((rotate.getAngle() + threeElements.nextAnkle())+ 360)%360);


//        moveCircles();

        if (!checkIfThereIsCollision(calculateCorners()))
        {
            return;
        }
        rotate.setAngle(((rotate.getAngle() - threeElements.nextAnkle())+ 360)%360);
        translate.setY(translate.getY() - threeElements.nextVerticalStep);
//        moveCircles();
        if (!checkIfThereIsCollision(calculateCorners()))
        {
            return;
        }
        translate.setY(translate.getY() + threeElements.nextVerticalStep);
        translate.setX(translate.getX() + threeElements.nextHorisontalStep);
//        moveCircles();
        if (!checkIfThereIsCollision(calculateCorners()))
        {
            return;
        }
        translate.setY(translate.getY() - threeElements.nextVerticalStep);
//        moveCircles();
    }

    private void setCordsToSpawnTank()
    {
        while (true)
        {
            translate.setX(rand.nextInt(537-56-60)+56+30);
            translate.setY(rand.nextInt(350-38+60)+38+30);
            rotate.setAngle(rand.nextInt(360));
            if (!checkIfThereIsCollision(calculateCorners()))
                break;
        }
    }
    public Rotate getRotate()
    {
        return rotate;
    }
    public Translate getTranslate()
    {
        return translate;
    }
    public ThreeElements calculateSteps(double x, double y, boolean flag)
    {
        double radians = Math.toRadians(360 - rotate.getAngle());
//        System.out.println("Kat: " + Double.toString(360-rotate.getAngle()));
        double directionalCoefficient = Math.tan(radians);
        double offsetParameter = translate.getY() - directionalCoefficient * translate.getX();
//        System.out.println("Ankle: " + Double.toString(360-rotate.getAngle()) + "Cords A " + directionalCoefficient + " Cords b:" + offsetParameter);
        boolean turnLeft;
        double angleBetweenTankAndMouse = Math.atan2(-y+translate.getY(), x-translate.getX());
        if (angleBetweenTankAndMouse < 0)
        {
            angleBetweenTankAndMouse = 2*Math.PI + angleBetweenTankAndMouse;
        }
        if ( angleBetweenTankAndMouse < radians && angleBetweenTankAndMouse +2*Math.PI -radians < Math.PI || angleBetweenTankAndMouse > radians && angleBetweenTankAndMouse - radians < Math.PI)
        {
            turnLeft = true;
        }
        else
        {
            turnLeft = false;
        }
//        System.out.println("Ankle of tank: "+ Double.toString(360-rotate.getAngle()) + " Ankle of mouse: " + Math.toDegrees(angleBetweenTankAndMouse)+" turnLeft: " + turnLeft);
        double verticalStep = Math.sin(radians) * step;
        double horizontalStep = Math.cos(radians) * step;
        if ( !flag )
        {
            verticalStep = -verticalStep;
            horizontalStep = -horizontalStep;
        }
        double changeAnkle = ankleStep;
        if ( Math.abs(360-Math.toDegrees(angleBetweenTankAndMouse) - (360 - rotate.getAngle()))< ankleStep )
        {
            changeAnkle = Math.abs(360-Math.toDegrees(angleBetweenTankAndMouse)-(360-rotate.getAngle()));
        }
        changeAnkle = flag ? changeAnkle : -changeAnkle;
        if ( !(flag && turnLeft || !flag && !turnLeft)) {
            changeAnkle = -changeAnkle;
        }
        return new ThreeElements(verticalStep, horizontalStep, changeAnkle);
    }
    public double[][] calculateCorners()
    {
        double[][] corners = new double[4][2];
        double width = 25; // Szerokość obrazka czołgu
        double height = 15; // Wysokość obrazka czołgu

        // Normalizacja kąta JavaFX do zakresu [0, 360) zgodnie z ruchem wskazówek zegara
        double angleDegreesJavaFX = rotate.getAngle();


        // --- Kluczowa zmiana: nie używamy już visualCenterX/Y w sposób bezpośredni jako centrum obrotu ---
        // Punkt obrotu dla StackPane jest jego lokalnym (0,0), które po translacji translate.getX/Y
        // znajduje się na scenie w translate.getX(), translate.getY().
        // To jest czerwona kropka, którą widzisz.
        double pivotSceneX = translate.getX(); // Punkt obrotu na scenie
        double pivotSceneY = translate.getY(); // Punkt obrotu na scenie

        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        // Wierzchołki StackPane w jego lokalnym układzie (od 0,0)
        // Zakładamy, że obrazek czołgu jest centrowany wewnątrz StackPane.
        // Zatem wierzchołki samego StackPane to:
        Point2D[] localStackPaneCorners = new Point2D[4];
        localStackPaneCorners[0] = new Point2D(0, 0); // Top-left
        localStackPaneCorners[1] = new Point2D(width, 0); // Top-right
        localStackPaneCorners[2] = new Point2D(width, height); // Bottom-right
        localStackPaneCorners[3] = new Point2D(0, height); // Bottom-left

        // Kąt w radianach dla Math.cos/sin (przeciwnie do ruchu wskazówek zegara, 0 wzdłuż dodatniej osi X)
        // JavaFX Rotate: 0 deg = góra (-Y), rośnie CW.
        // Standard Math: 0 deg = prawo (+X), rośnie CCW.
        // Mapowanie: Math_Angle = JavaFX_Angle - 90
        double finalAngleRadians = Math.toRadians(angleDegreesJavaFX);

        double cosAngle = Math.cos(finalAngleRadians);
        double sinAngle = Math.sin(finalAngleRadians);

        for (int i = 0; i < 4; i++) {
            double x_local_to_pivot = localStackPaneCorners[i].getX();
            double y_local_to_pivot = localStackPaneCorners[i].getY();

            // Zastosowanie standardowej macierzy obrotu wokół (0,0) lokalnego StackPane
            // x' = x*cos(angle) - y*sin(angle)
            // y' = x*sin(angle) + y*cos(angle)
            double x_rotated_relative_to_pivot = x_local_to_pivot * cosAngle - y_local_to_pivot * sinAngle;
            double y_rotated_relative_to_pivot = x_local_to_pivot * sinAngle + y_local_to_pivot * cosAngle;

            // Dodajemy współrzędne punktu obrotu (translate.getX/Y) aby uzyskać finalne współrzędne na scenie
            corners[i][0] = pivotSceneX + x_rotated_relative_to_pivot;
            corners[i][1] = pivotSceneY + y_rotated_relative_to_pivot;
        }
        return corners;
    }
    public boolean checkIfThereIsCollision(double [][]corners)
    {
        try {
            for (var corner : corners) {
                if (MapInfo.getMap()[(int) corner[1] - MapInfo.topBorder+ 5][(int) corner[0] + 5 - MapInfo.leftBorder]) {
                    System.out.println("Collision!!!");
                    return true;
                }
            }
            System.out.println("No collision!!!");
            return false;

        } catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Out of Bounds???");
            return true;
        }
    }
    public void setCircles(AnchorPane pane)
    {

        if (pane != null && _pane == null)
        {
            _cordsOfTank = new TestCordsOfTank[5];
            for ( int i  = 0; i < _cordsOfTank.length; i++ )
            {
                _cordsOfTank[i] = new TestCordsOfTank();
            }
            _pane = pane;
            for ( var el : _cordsOfTank)
            {
                _pane.getChildren().add(el.circle);
            }
            _cordsOfTank[4].circle.setRadius(2);
            _cordsOfTank[4].circle.setFill(Color.RED);
        }
    }
    public void moveCircles()
    {
        if ( _pane == null )
        {
            System.out.println("Nie mozna stworzyc wierzcholkow");
            return;
        }
        double[][] corners = calculateCorners();
        for ( int i = 0; i < corners.length-1; i++ )
        {
            _cordsOfTank[i].translate.setX(corners[i][0]);
            _cordsOfTank[i].translate.setY(corners[i][1]);
        }
        _cordsOfTank[4].translate.setX(translate.getX());
        _cordsOfTank[4].translate.setY(translate.getY());
    }
}


