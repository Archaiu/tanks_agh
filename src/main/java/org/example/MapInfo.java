package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import javax.management.BadAttributeValueExpException;
import java.io.FileWriter;
import java.io.IOException;

public class MapInfo {
    public static int leftBorder;
    public static int rightBorder;
    public static int bottomBorder;
    public static int topBorder;
    public static boolean[][] map;
    public static void setCords()
    {
        System.out.println("Try to inicjalize the Map");
        FXMLLoader fxmlLoader = new FXMLLoader(MapInfo.class.getResource("/mainPage.fxml"));
        Controller controller = new Controller(10);
        fxmlLoader.setController(controller);
        try {
            Parent root = fxmlLoader.load();
        }
        catch (IOException e)
        {
            System.err.println("Unable to load FXML in MapInfo");
        }
        System.out.println("Can't load mainPage.fxml");
        try {
            leftBorder = (int)controller.getLine("left").getEndX();
            rightBorder = (int)controller.getLine("right").getEndX();
            bottomBorder = (int)controller.getLine("bottom").getEndY();
            topBorder = (int)controller.getLine("top").getEndY();
            loadMap(controller);
        } catch ( BadAttributeValueExpException e)
        {
            System.out.println("Can't load mainPage.fxml in MapInfo");
            System.exit(0);
        }
    }
    public static void loadMap(Controller controller) throws BadAttributeValueExpException {

        map = new boolean[bottomBorder - topBorder +10 ][rightBorder - leftBorder + 10];
        int[] VerBorders = {(int) controller.getLine("top").getEndY() - 1, (int) controller.getLine("bottom").getEndY() - 1};
        int[] HorBorders = {(int) controller.getLine("left").getEndX() - 1, (int) controller.getLine("right").getEndX() - 1};
        for (int y = (int) controller.getLine("left").getStartY() - 1; y <= controller.getLine("left").getEndY(); y++) {
            for (int x : HorBorders)
                for (int bias = 0; bias < 2; bias++) {
                    map[y-(int)topBorder+5][x + bias-(int)leftBorder+5] = true;
                }
        }
        for (int x = (int) controller.getLine("top").getStartX() - 1; x <= controller.getLine("bottom").getEndX(); x++) {
            for (int y : VerBorders)
                for (int bias = 0; bias < 2; bias++) {
                    map[y + bias-(int)topBorder+5][x-(int)leftBorder+5] = true;
                }
        }
        Rectangle[] ElementsOnMap = {controller.getRectangle(1),controller.getRectangle(2),controller.getRectangle(3)};
        System.out.println("left: " + leftBorder + " right: " + rightBorder + " top: " + topBorder + " bottom: " + bottomBorder);
        for ( Rectangle r : ElementsOnMap)
        {
            System.out.println("Element: " + r.getLayoutX() + " " + r.getLayoutY() + " " + r.getWidth() + r.getLayoutX()+ " " + r.getHeight()+r.getLayoutY());
        }

        for ( Rectangle element : ElementsOnMap) {

            for ( int x = (int)element.getLayoutX(); x < (int)element.getLayoutX() + element.getWidth(); x++ )
            {
                for ( int y =  (int)element.getLayoutY(); y < (int)element.getLayoutY() + element.getHeight(); y++ )
                {
                    map[y-(int)topBorder+5][x-(int)leftBorder+5] = true;
                }
            }

        }
//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter("plik.txt");
//            for ( int y = 0; y < map.length; y++ )
//            {
//                for ( int x = 0; x < map[0].length; x++ )
//                {
//                    fileWriter.write(map[y][x] ? '1' : '0');
//                }
//                fileWriter.write("\n");
//            }
//            fileWriter.close();
//        } catch (IOException e)
//        {
//            System.err.println("Unable to write to plik.txt file");
//        }
    }
    public static boolean[][] getMap() { return map; }
    public static int leftBias() { return leftBorder; }
    public static int topBias() { return topBorder; }
}
