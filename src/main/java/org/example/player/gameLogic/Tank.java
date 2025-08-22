package org.example.player.gameLogic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.example.common.others.PhotoInfo;


import java.io.InputStream;
import java.util.Random;

public class Tank {


    private AnchorPane _pane;

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
        String logoPath = PhotoInfo.getInstance().getPhoto(number);

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




    }


    public Rotate getRotate()
    {
        return rotate;
    }
    public Translate getTranslate()
    {
        return translate;
    }
    public StackPane getVObjectToDisplay() {
        return sbox;
    }


}


