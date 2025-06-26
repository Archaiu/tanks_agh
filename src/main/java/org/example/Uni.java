package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Uni {
    @FXML
    private AnchorPane _pane;

    @FXML private AnchorPane mainPlansza;

    @FXML private ImageView uniImageView1, uniImageView2, uniImageView3, uniImageView4, uniImageView5, uniImageView6, uniImageView7, uniImageView8;

    private int _number;
    private boolean _choise = false;

    private ArrayList<ImageView> uniImageViewsList = new ArrayList<>();
    private int currentlySelectedUniIndex = -1;

    private Image[] normalImages = new Image[8];
    private Image[] selectedImages = new Image[8];

    public Uni(int number) {
        _number = number;
    }

    @FXML
    public void initialize() {
        System.out.println("Uni: Metoda initialize() wywołana.");

        uniImageViewsList.add(uniImageView1);
        uniImageViewsList.add(uniImageView2);
        uniImageViewsList.add(uniImageView3);
        uniImageViewsList.add(uniImageView4);
        uniImageViewsList.add(uniImageView5);
        uniImageViewsList.add(uniImageView6);
        uniImageViewsList.add(uniImageView7);
        uniImageViewsList.add(uniImageView8);

        for (int i = 0; i < uniImageViewsList.size(); i++) {
            final int index = i;
            uniImageViewsList.get(i).setOnMouseClicked(event -> handleUniSelection(index));
        }

        loadUniImages();
    }

    public void showUni(){
        System.out.println("Try to load Uni scene");
        Parent root = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/uni.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("BŁĄD: Nie można załadować uni.fxml: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Photos to Uni loaded");
        Scene scene = new Scene(root);
        UserInfo.getClasess(_number).get_stage().setScene(scene);
    }

    private void loadUniImages() {
        for (int i = 0; i < 8; i++) {
            String normalPath = UserInfo.getPhoto(i);
            String selectedPath = UserInfo.getPhotoSelected(i);

            if (normalPath == null || selectedPath == null) {
                System.err.println("BŁĄD: Ścieżka do obrazka uniwersytetu jest nullem dla indeksu " + i);
                System.exit(1);
            }

            try (InputStream normalIs = getClass().getResourceAsStream(normalPath)) {
                if (normalIs == null) {
                    System.err.println("BŁĄD KRYTYCZNY: Nie znaleziono normalnego obrazka: " + normalPath);
                    System.exit(1);
                }
                normalImages[i] = new Image(normalIs);
            } catch (Exception e) {
                System.err.println("WYJĄTEK podczas ładowania normalnego obrazka " + normalPath + ": " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }

            try (InputStream selectedIs = getClass().getResourceAsStream(selectedPath)) {
                if (selectedIs == null) {
                    System.err.println("BŁĄD KRYTYCZNY: Nie znaleziono wybranego (blue) obrazka: " + selectedPath);
                    System.exit(1);
                }
                selectedImages[i] = new Image(selectedIs);
            } catch (Exception e) {
                System.err.println("WYJĄTEK podczas ładowania wybranego (blue) obrazka " + selectedPath + ": " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }

            if (uniImageViewsList.get(i) != null) {
                uniImageViewsList.get(i).setImage(normalImages[i]);
            } else {
                System.err.println("BŁĄD: uniImageView" + (i+1) + " nie został prawidłowo wstrzyknięty z FXML.");
                System.exit(1);
            }
        }
    }

    private void handleUniSelection(int newIndex) {
        if (currentlySelectedUniIndex != -1 && currentlySelectedUniIndex < uniImageViewsList.size()) {
            uniImageViewsList.get(currentlySelectedUniIndex).setImage(normalImages[currentlySelectedUniIndex]);
        }

        if (newIndex >= 0 && newIndex < uniImageViewsList.size()) {
            uniImageViewsList.get(newIndex).setImage(selectedImages[newIndex]);
            currentlySelectedUniIndex = newIndex;
            _choise = true;
        }
    }

    @FXML
    public void confirmUni(ActionEvent event) throws IOException {
        if (!_choise || currentlySelectedUniIndex == -1) {
            System.out.println("Musisz wybrać uniwersytet!");
            return;
        }

        UserInfo.setTank(_number, new Tank(currentlySelectedUniIndex));

        if (currentlySelectedUniIndex != -1 && currentlySelectedUniIndex < uniImageViewsList.size()) {
            uniImageViewsList.get(currentlySelectedUniIndex).setImage(normalImages[currentlySelectedUniIndex]);
            currentlySelectedUniIndex = -1;
        }

        if (mainPlansza != null) {
            mainPlansza.getChildren().clear();
            StackPane tankView = UserInfo.getTank(_number).getVObjectToDisplay();
            mainPlansza.getChildren().add(tankView);
        } else {
            System.err.println("Błąd: mainPlansza nie została wstrzyknięta w UniController. Nie można dodać czołgu.");
            _pane.getChildren().clear();
            StackPane tankView = UserInfo.getTank(_number).getVObjectToDisplay();
            _pane.getChildren().add(tankView);
        }


        UserInfo.getClasess(_number).get_mainPage().showMainPage();
    }
}