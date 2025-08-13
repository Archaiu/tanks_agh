package org.example.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.example.common.POJO.MyRectangle;
import org.example.player.Controller;
import org.example.common.POJO.MyLine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import javax.management.BadAttributeValueExpException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class MapInfo {

    public static int leftBorder;
    public static int rightBorder;
    public static int bottomBorder;
    public static int topBorder;
    public static boolean[][] map;
    public static BulletsMapInfo mapBullets;
    public static void setCords()
    {
        System.out.println("Try to inicjalize the Map");
        readDataFromFXMLByHand();
        try {
            leftBorder = (int)left.getEndX();
            rightBorder = (int)right.getEndX();
            bottomBorder = (int)bottom.getEndY();
            topBorder = (int)top.getEndY();
            loadMap();
            loadMapForBullets();
        } catch ( BadAttributeValueExpException e)
        {
            System.out.println("Can't load mainPage.fxml in MapInfo");
            System.exit(0);
        }
    }
    public static void loadMap() throws BadAttributeValueExpException
    {
        map = new boolean[bottomBorder - topBorder +10 ][rightBorder - leftBorder + 10];
        int[] VerBorders = {(int) top.getEndY() - 1, (int) bottom.getEndY() - 1};
        int[] HorBorders = {(int) left.getEndX() - 1, (int) right.getEndX() - 1};
        for (int y = (int) left.getStartY() - 1; y <= left.getEndY(); y++) {
            for (int x : HorBorders)
                for (int bias = 0; bias < 2; bias++) {
                    map[y-(int)topBorder+5][x + bias-(int)leftBorder+5] = true;
                }
        }
        for (int x = (int) top.getStartX() - 1; x <= bottom.getEndX(); x++) {
            for (int y : VerBorders)
                for (int bias = 0; bias < 2; bias++) {
                    map[y + bias-(int)topBorder+5][x-(int)leftBorder+5] = true;
                }
        }
        MyRectangle[] ElementsOnMap = {box1,box2,box3};
        System.out.println("left: " + leftBorder + " right: " + rightBorder + " top: " + topBorder + " bottom: " + bottomBorder);
        for ( MyRectangle r : ElementsOnMap)
        {
//            System.out.println("Element: " + r.getLayoutX() + " " + r.getLayoutY() + " " + r.getWidth() + r.getLayoutX()+ " " + r.getHeight()+r.getLayoutY());
        }

        for ( MyRectangle element : ElementsOnMap) {

            for ( int x = (int)element.getLayoutX(); x < (int)element.getLayoutX() + element.getWidth(); x++ )
            {
                for ( int y =  (int)element.getLayoutY(); y < (int)element.getLayoutY() + element.getHeight(); y++ )
                {
                    map[y-(int)topBorder+5][x-(int)leftBorder+5] = true;
                }
            }

        }

    }
    public static void loadMapForBullets() throws BadAttributeValueExpException
    {
        MyRectangle[] elementsOnMap = {box1,box2, box3};
        ArrayList<double[]> horisontalLines= new ArrayList<>();
        ArrayList<double[]> verticalLines= new ArrayList<>();
        for ( MyRectangle element : elementsOnMap)
        {
            verticalLines.add(new double[]{element.getLayoutX(),element.getLayoutY(), element.getHeight() + element.getLayoutY()});
            verticalLines.add(new double[]{element.getLayoutX()+ element.getWidth(),element.getLayoutY(), element.getHeight() + element.getLayoutY()});
            horisontalLines.add(new double[]{element.getLayoutY(), element.getLayoutX(), element.getWidth() + element.getLayoutX()});
            horisontalLines.add(new double[]{element.getLayoutY()+ element.getHeight(), element.getLayoutX(), element.getWidth() + element.getLayoutX()});
        }
        ArrayList<double[]> horisontalBorders = new ArrayList<>();
        ArrayList<double[]> verticalBorders = new ArrayList<>();
        MyLine line = left;
        verticalBorders.add(new double[]{line.getStartX(), line.getStartY(), line.getEndY()});
        line = right;
        verticalBorders.add(new double[]{line.getStartX(), line.getStartY(), line.getEndY()});
        line = top;
        horisontalBorders.add(new double[]{line.getStartY(), line.getStartX(), line.getEndX()});
        line = bottom;
        horisontalBorders.add(new double[]{line.getStartY(), line.getStartX(), line.getEndX()});
        mapBullets = new BulletsMapInfo(verticalLines, horisontalLines,  verticalBorders,horisontalBorders);

    }

    public static boolean[][] getMap() { return map; }
    public static int leftBias() { return leftBorder; }
    public static int topBias() { return topBorder; }

    private static MyLine left;
    private static MyLine right;
    private static MyLine bottom;
    private static MyLine top;

    private static MyRectangle box1;
    private static MyRectangle box2;
    private static MyRectangle box3;

    public static void readDataFromFXMLByHand() {
        try {
            URL fxmlUrl = Controller.class.getResource("/mainPage.fxml");
            if (fxmlUrl == null) {
                System.err.println("There is no FXML file");
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setFeature("http://xml.org/sax/features/namespaces", false);

            DocumentBuilder builder = factory.newDocumentBuilder();

            try (InputStream is = fxmlUrl.openStream()) {
                Document doc = builder.parse(is);
                doc.getDocumentElement().normalize();


                NodeList lineNodes = doc.getElementsByTagName("Line");
//                System.out.println("\n--- Lines ---");
                for (int i = 0; i < lineNodes.getLength(); i++) {
                    Element lineElement = (Element) lineNodes.item(i);
                    String id = lineElement.getAttribute("fx:id");
                    double startX = Double.parseDouble(lineElement.getAttribute("startX"));
                    double startY = Double.parseDouble(lineElement.getAttribute("startY"));
                    double endX = Double.parseDouble(lineElement.getAttribute("endX"));
                    double endY = Double.parseDouble(lineElement.getAttribute("endY"));
//                    System.out.printf("Linia (ID: %s): Start=(%.1f, %.1f), End=(%.1f, %.1f)%n", id, startX, startY, endX, endY);
                    // Tutaj możesz przypisać te wartości do swoich statycznych pól w MapInfo
                    // Przykład: if ("leftWall".equals(id)) MapInfo.leftBorder = (int)endX;
                    switch (id)
                    {
                        case "leftWall" -> left = new MyLine("left",startX,startY, endX, endY);
                        case "rightWall" -> right = new MyLine("right",startX,startY, endX, endY);
                        case "bottomWall" -> bottom = new MyLine("bottom",startX,startY, endX, endY);
                        case "topWall"  -> top = new MyLine("top",startX,startY, endX, endY);
                        default -> throw new IllegalStateException("Unexpected value: " + id);
                    }
                }

                NodeList rectangleNodes = doc.getElementsByTagName("Rectangle");
//                System.out.println("\n--- Prostokąty ---");
                for (int i = 0; i < rectangleNodes.getLength(); i++) {
                    Element rectElement = (Element) rectangleNodes.item(i);
                    String id = rectElement.getAttribute("fx:id");
                    double x = Double.parseDouble(rectElement.getAttribute("layoutX"));
                    double y = Double.parseDouble(rectElement.getAttribute("layoutY"));
                    double width = Double.parseDouble(rectElement.getAttribute("width"));
                    double height = Double.parseDouble(rectElement.getAttribute("height"));

                    switch (id)
                    {
                        case "box1" -> box1 = new MyRectangle("box1", x,y,width,height);
                        case "box2" -> box2 = new MyRectangle("box2", x,y,width,height);
                        case "box3" -> box3 = new MyRectangle("box3", x,y,width,height);
                        default -> throw new IllegalStateException("Unexpected value: " + id);
                    }
                }

            }
        } catch (Exception e) {
            System.err.println("Błąd podczas parsowania FXML jako XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
