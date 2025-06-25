package org.example;

import javafx.stage.Stage;

public class UserInfo {

    private static final String[] PHOTO_PATHS = {
            "/universities/agh.png",
            "/universities/papieski.png",
            "/universities/PK.png",
            "/universities/uek.png",
            "/universities/ujj.png",
            "/universities/ukenik.png",
            "/universities/up.png",
            "/universities/ur.png"
    };

    private static final String[] PHOTO_PATHS_SELECTED = {
            "/universities/agh_blue.png",
            "/universities/papieski_blue.png",
            "/universities/PK_blue.png",
            "/universities/uek_blue.png",
            "/universities/ujj_blue.png",
            "/universities/ukenik_blue.png",
            "/universities/up_blue.png",
            "/universities/ur_blue.png"
    };

    private static Clasess[] clasess = new Clasess[8];
    private static Tank[] tanks = new Tank[8];
    private static MainPage[] mainPages = new MainPage[8];

    public static int createUser(Stage stage, String userName, int userIndex, Beginning beginningInstance) {
        if (userIndex >= 0 && userIndex < clasess.length) {
            if (clasess[userIndex] == null) {
                clasess[userIndex] = new Clasess(userIndex, beginningInstance, stage);
                mainPages[userIndex] = new MainPage(userIndex);
                System.out.println("Utworzono użytkownika o indeksie: " + userIndex + " z nazwą: " + userName);
                return userIndex;
            } else {
                System.out.println("Użytkownik o indeksie " + userIndex + " już istnieje.");
                throw new IllegalArgumentException("Użytkownik o tej nazwie już istnieje!");
            }
        } else {
            throw new IndexOutOfBoundsException("Indeks użytkownika poza zakresem: " + userIndex);
        }
    }

    public static Clasess getClasess(int number) {
        if (number >= 0 && number < clasess.length) {
            return clasess[number];
        }
        System.err.println("Błąd: Indeks klasy poza zakresem w UserInfo.getClasess(" + number + ")");
        return null;
    }

    public static void setTank(int number, Tank tank) {
        if (number >= 0 && number < tanks.length) {
            tanks[number] = tank;
        } else {
            System.err.println("Błąd: Indeks czołgu poza zakresem w UserInfo.setTank(" + number + ")");
        }
    }

    public static Tank getTank(int number) {
        if (number >= 0 && number < tanks.length) {
            return tanks[number];
        }
        System.err.println("Błąd: Indeks czołgu poza zakresem w UserInfo.getTank(" + number + ")");
        return null;
    }

    public static MainPage getClasess_mainPage(int number) {
        if (number >= 0 && number < mainPages.length) {
            return mainPages[number];
        }
        System.err.println("Błąd: Indeks strony głównej poza zakresem w UserInfo.getClasess_mainPage(" + number + ")");
        return null;
    }

    public static String getPhoto(int i) {
        if (i >= 0 && i < PHOTO_PATHS.length) {
            return PHOTO_PATHS[i];
        } else {
            System.err.println("Błąd: Indeks zdjęcia poza zakresem w UserInfo.getPhoto(" + i + ")");
            return null;
        }
    }

    public static String getPhotoSelected(int i) {
        if (i >= 0 && i < PHOTO_PATHS_SELECTED.length) {
            return PHOTO_PATHS_SELECTED[i];
        } else {
            System.err.println("Błąd: Indeks wybranego zdjęcia poza zakresem w UserInfo.getPhotoSelected(" + i + ")");
            return null;
        }
    }
}