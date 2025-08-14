package org.example.common;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.example.common.MapInfo;
import org.example.mechanics.TestCordsOfTank;
import org.example.mechanics.UserInfo;

import java.io.InputStream;
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
    static int _width = 25;
    static int _height = 15;
    record ThreeElements(double nextVerticalStep, double nextHorisontalStep, double nextAnkle) {}
    public Tank(int number)
    {
        Image tankPhoto = null;
        ImageView tankObject;

        try (InputStream tankIs = getClass().getResourceAsStream("/tank.jpg")) {
            if (tankIs == null) {
                System.err.println("BŁĄD: Nie znaleziono obrazka czołgu: /tank.jpg. Sprawdź ścieżkę zasobu.");
                System.exit(1);
            }
            tankPhoto = new Image(tankIs);
        } catch (Exception e) {
            System.err.println("BŁĄD podczas ładowania obrazka czołgu: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        tankObject = new ImageView(tankPhoto);
        tankObject.setFitWidth(_width);
        tankObject.setFitHeight(_height);
        tankObject.setPreserveRatio(true);

        Image uniLogo = null;
        ImageView uniLogoView = null;
        String logoPath = UserInfo.getPhoto(number);

        if (logoPath != null) {
            try (InputStream logoIs = getClass().getResourceAsStream(logoPath)) {
                if (logoIs == null) {
                    System.err.println("BŁĄD: Nie znaleziono logo uniwersytetu: " + logoPath);
                } else {
                    uniLogo = new Image(logoIs);
                    uniLogoView = new ImageView(uniLogo);
                    uniLogoView.setFitWidth(15);
                    uniLogoView.setFitHeight(15);
                    uniLogoView.setPreserveRatio(true);
                }
            } catch (Exception e) {
                System.err.println("BŁĄD podczas ładowania logo uniwersytetu: " + e.getMessage());
                e.printStackTrace();
            }
        }
//        ImageView uniObject = new ImageView(UserInfo.getPhoto(number));
        sbox = new StackPane(tankObject);
        if (uniLogoView != null) {
            sbox.getChildren().add(uniLogoView);
        }
        rand = new Random(System.currentTimeMillis());
        translate = new Translate();

        rotate = new Rotate(rand.nextDouble(360));
        sbox.getTransforms().addAll(translate, rotate);

        setCordsToSpawnTank();


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
        setCircles(pane);



        ThreeElements threeElements = calculateSteps(x, y, flag);

        translate.setY(translate.getY() + threeElements.nextVerticalStep);
        translate.setX(translate.getX() - threeElements.nextHorisontalStep);
        rotate.setAngle(((rotate.getAngle() + threeElements.nextAnkle())+ 360)%360);


        moveCircles();

        if (!checkIfThereIsCollision(calculateCorners()))
        {
            return;
        }
        rotate.setAngle(((rotate.getAngle() - threeElements.nextAnkle())+ 360)%360);
        translate.setY(translate.getY() - threeElements.nextVerticalStep);
        moveCircles();
        if (!checkIfThereIsCollision(calculateCorners()))
        {
            return;
        }
        translate.setY(translate.getY() + threeElements.nextVerticalStep);
        translate.setX(translate.getX() + threeElements.nextHorisontalStep);
        moveCircles();
        if (!checkIfThereIsCollision(calculateCorners()))
        {
            return;
        }
        translate.setY(translate.getY() - threeElements.nextVerticalStep);
        moveCircles();
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
        Point2D front = calculateFront();
        double radians = Math.toRadians(360 - rotate.getAngle());
//        System.out.println("Kat: " + Double.toString(360-rotate.getAngle()));
        double directionalCoefficient = Math.tan(radians);
        double offsetParameter = front.getY() - directionalCoefficient * front.getX();
//        System.out.println("Ankle: " + Double.toString(360-rotate.getAngle()) + "Cords A " + directionalCoefficient + " Cords b:" + offsetParameter);
        boolean turnLeft;
        double angleBetweenTankAndMouse = Math.atan2(-y+front.getY(), x-front.getX());
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
        if ( Math.abs(Math.toDegrees(angleBetweenTankAndMouse) - (540 - rotate.getAngle()))%360< ankleStep )
        {
            //System.out.println("Try to set new angle");
            changeAnkle = Math.abs(Math.toDegrees(angleBetweenTankAndMouse)-(540-rotate.getAngle()))%360;
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
        double width = 25;
        double height = 15;

        double angleDegreesJavaFX = rotate.getAngle();

        double pivotSceneX = translate.getX();
        double pivotSceneY = translate.getY();

        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        Point2D[] localStackPaneCorners = new Point2D[4];
        localStackPaneCorners[0] = new Point2D(0, 0);
        localStackPaneCorners[1] = new Point2D(width, 0);
        localStackPaneCorners[2] = new Point2D(width, height);
        localStackPaneCorners[3] = new Point2D(0, height);

        double finalAngleRadians = Math.toRadians(angleDegreesJavaFX);

        double cosAngle = Math.cos(finalAngleRadians);
        double sinAngle = Math.sin(finalAngleRadians);

        for (int i = 0; i < 4; i++) {
            double x_local_to_pivot = localStackPaneCorners[i].getX();
            double y_local_to_pivot = localStackPaneCorners[i].getY();

            double x_rotated_relative_to_pivot = x_local_to_pivot * cosAngle - y_local_to_pivot * sinAngle;
            double y_rotated_relative_to_pivot = x_local_to_pivot * sinAngle + y_local_to_pivot * cosAngle;

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
                    //System.out.println("Collision!!!");
                    return true;
                }
            }
            //System.out.println("No collision!!!");
            return false;

        } catch (ArrayIndexOutOfBoundsException e)
        {
            //System.out.println("Out of Bounds???");
            return true;
        }
    }
    public void setCircles(AnchorPane pane)
    {

        if (pane != null && _pane == null)
        {
            _cordsOfTank = new TestCordsOfTank[4];
            for ( int i  = 0; i < _cordsOfTank.length; i++ )
            {
                _cordsOfTank[i] = new TestCordsOfTank();
            }
            _pane = pane;
            for ( var el : _cordsOfTank)
            {
                _pane.getChildren().add(el.circle);
            }
            _cordsOfTank[0].changeColor(Color.RED);
            _cordsOfTank[1].changeColor(Color.GREEN);
            _cordsOfTank[2].changeColor(Color.BROWN);
            _cordsOfTank[3].changeColor(Color.BLUE);

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
        for ( int i = 0; i < corners.length; i++ )
        {
            _cordsOfTank[i].translate.setX(corners[i][0]);
            _cordsOfTank[i].translate.setY(corners[i][1]);
        }
    }
    private Point2D calculateFront(){
        double ankle = (360 - rotate.getAngle() + 90)%360;
        double radians = Math.toRadians(ankle);
        double Ystep = Math.sin(radians) * sbox.getHeight()/2.0;
        double Xstep = -Math.cos(radians) * sbox.getHeight()/2.0;
        return new Point2D ( translate.getX() + Xstep,translate.getY() + Ystep);
}
}


