package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.io.InputStream;
import java.util.Random;

public class Tank {
    private StackPane sbox;
    Translate translate;
    Rotate rotate;
    Random rand;
    double step = 1;

    public Tank(int uniIndex) {
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
        tankObject.setFitWidth(50);
        tankObject.setFitHeight(50);
        tankObject.setPreserveRatio(true);

        Image uniLogo = null;
        ImageView uniLogoView = null;
        String logoPath = UserInfo.getPhoto(uniIndex);

        if (logoPath != null) {
            try (InputStream logoIs = getClass().getResourceAsStream(logoPath)) {
                if (logoIs == null) {
                    System.err.println("BŁĄD: Nie znaleziono logo uniwersytetu: " + logoPath);
                } else {
                    uniLogo = new Image(logoIs);
                    uniLogoView = new ImageView(uniLogo);
                    uniLogoView.setFitWidth(25);
                    uniLogoView.setFitHeight(25);
                    uniLogoView.setPreserveRatio(true);

                }
            } catch (Exception e) {
                System.err.println("BŁĄD podczas ładowania logo uniwersytetu: " + e.getMessage());
                e.printStackTrace();
            }
        }

        translate = new Translate();
        rotate = new Rotate();

        translate.setX(300 - tankObject.getFitWidth() / 2);
        translate.setY(200 - tankObject.getFitHeight() / 2);

        sbox = new StackPane(tankObject);
        if (uniLogoView != null) {
            sbox.getChildren().add(uniLogoView);
        }

        sbox.getTransforms().addAll(translate, rotate);

        rand = new Random();
    }

    public StackPane getVObjectToDisplay() {
        return sbox;
    }

    public Translate getTranslate() {
        return translate;
    }

    public Rotate getRotate() {
        return rotate;
    }

    public void moveTank(double mouseX, double mouseY, boolean flag) {
        double currentX = translate.getX() + sbox.getWidth() / 2;
        double currentY = translate.getY() + sbox.getHeight() / 2;

        double deltaX = mouseX - currentX;
        double deltaY = mouseY - currentY;

        double angleBetweenTankAndMouse = Math.atan2(-deltaY, deltaX);

        double currentAngleDegrees = rotate.getAngle();
        double currentAngleRadians = Math.toRadians(360 - currentAngleDegrees);

        double angleDiff = angleBetweenTankAndMouse - currentAngleRadians;
        if (angleDiff > Math.PI) angleDiff -= 2 * Math.PI;
        if (angleDiff < -Math.PI) angleDiff += 2 * Math.PI;

        double rotationSpeed = Math.toRadians(5);
        boolean turnLeft = angleDiff > 0;

        if (Math.abs(angleDiff) > rotationSpeed) {
            if (turnLeft) {
                rotate.setAngle(((rotate.getAngle() + Math.toDegrees(rotationSpeed)) + 360) % 360);
            } else {
                rotate.setAngle(((rotate.getAngle() - Math.toDegrees(rotationSpeed)) + 360) % 360);
            }
        }

        double angleTolerance = Math.toRadians(10);
        if (Math.abs(angleBetweenTankAndMouse - currentAngleRadians) < angleTolerance ||
                Math.abs(angleBetweenTankAndMouse - currentAngleRadians - 2 * Math.PI) < angleTolerance ||
                Math.abs(angleBetweenTankAndMouse - currentAngleRadians + 2 * Math.PI) < angleTolerance) {
            double verticalStep = Math.sin(currentAngleRadians) * step;
            double horizontalStep = Math.cos(currentAngleRadians) * step;

            if (flag) {
                translate.setX(currentX + horizontalStep - sbox.getWidth() / 2);
                translate.setY(currentY - verticalStep - sbox.getHeight() / 2);
            } else {
                translate.setX(currentX - horizontalStep - sbox.getWidth() / 2);
                translate.setY(currentY + verticalStep - sbox.getHeight() / 2);
            }
        }
    }
}