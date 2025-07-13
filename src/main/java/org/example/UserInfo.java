package org.example;

import javafx.stage.Stage;

import javax.swing.*;

public class UserInfo {

    static private int numberOfPlayers = 0;
    static private int numberOfGames = 0;
    static private SingleRound round;
    static private int[] results;
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

    public static int createUser(Stage stage, String userName, Beginning beginningInstance) {
        if ( numberOfPlayers < clasess.length) {
                clasess[numberOfPlayers] = new Clasess(numberOfPlayers, beginningInstance, stage);
                mainPages[numberOfPlayers] = new MainPage(numberOfPlayers);
                System.out.println("Utworzono użytkownika o indeksie: " + numberOfPlayers + " z nazwą: " + userName);
                return numberOfPlayers++;
        } else {
            throw new IndexOutOfBoundsException("Indeks użytkownika poza zakresem: " + numberOfPlayers);
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

    public static int getNumberOfGames() {return numberOfGames;}
    public static int getNumberOfPlayers() {return numberOfPlayers;}
    public static void gameOver(){ numberOfGames++;}
    public static SingleRound getRound() { return round;}
    public static void tankDestroyed(int i)
    {
        System.out.println("Tank number "+i+" is killed!");
    }
    public static void tankDestroyed(Tank tank)
    {
        for ( int i = 0; i < numberOfPlayers; i++)
        {
            if ( getTank(i) == tank)
            {
                tankDestroyed(i);
                return;
            }
        }
        throw new IndexOutOfBoundsException("Tank doesn't exist!");
    }
    public static void newRound(boolean firstRound, int i)
    {
        numberOfGames++;
        if (firstRound)
        {
            results = new int[numberOfPlayers];
        }
        else
        {
            results[i]++;
        }
        round = new SingleRound();
//        try {

//        } catch (NullPointerException ignored){}
        round.startRound();

    }
}