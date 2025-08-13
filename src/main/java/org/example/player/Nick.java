package org.example.player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Nick implements Window
{
    @FXML
    TextField loginButtom;
    @FXML
    Button nameSetted;
    @FXML //
    Label messageLabel;
    public void loadWindow()
    {
        Stage stage = Gamer.get_gamer().stage;
        Parent root = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/beginning.fxml"));
            fxmlLoader.setController(this);
            root = fxmlLoader.load();
        } catch (IOException e)       {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            e.printStackTrace();
            stage.close();
            System.exit(0);
       }


        stage.setTitle("Tanks AGH");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/app_style.css").toExternalForm());
        stage.setScene(scene);

        stage.show();

    }

    public void buttonClicked(ActionEvent event)
    {
        Comunication.getInstance().sendMessage(loginButtom.getText());
    }
    public void setError(String string)
    {

    }
//          {
//
//              try
//              {
//                  System.out.println("DEBUG: Beginning.stage w tryToCreateUser(): " + stage);
//                  number = UserInfo.createUser(stage,  loginButtom.getText() ,this);
//                  System.out.println("Go to load uni Scene");
//                  UserInfo.getClasess(number).get_uni().showUni();
//              } catch (IllegalArgumentException e)
//              {
//                  if (messageLabel != null) {
//                      messageLabel.setText(e.getMessage() + ", give valid name");
//                  } else {
//                      System.err.println("ERROR: messageLabel is not initialized in FXML or Controller. Check @FXML and fxmlLoader.setController(this).");
//                  }
//              } catch (Exception e) {
//                  System.out.println("Problem with saved user's data");
//                  e.printStackTrace();
//                  try {
//                      TimeUnit.SECONDS.sleep(1);
//                  } catch (InterruptedException ex) {}
//                  if (stage != null) {
//                      stage.close();
//                  }
//                  System.exit(0);
//              }
//          }

    /**
     * package org.example.mechanics;
     *
     * import javafx.application.Application;
     * import javafx.event.ActionEvent;
     * import javafx.fxml.FXML;
     * import javafx.fxml.FXMLLoader;
     * import javafx.scene.Group;
     * import javafx.scene.Parent;
     * import javafx.scene.Scene;
     * import javafx.scene.control.Button;
     * import javafx.scene.control.Label;
     * import javafx.scene.control.TextField;
     * import javafx.stage.Stage;
     *
     * import java.io.IOException;
     * import java.util.concurrent.TimeUnit;
     *
     * public class Beginning extends Application {
     *     Stage stage = null;
     *     int number;
     *     @FXML
     *     TextField loginButtom;
     *     @FXML
     *     Button nameSetted;
     *     @FXML //
     *     Label messageLabel;
     *
     *     @Override
     *     public void start(Stage stagee)
     *     {
     *         stage = stagee;
     *         Parent root = null;
     *         try {
     *             FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/beginning.fxml"));
     *             fxmlLoader.setController(this);
     *             root = fxmlLoader.load();
     *         } catch (IOException e)
     *         {
     *             System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
     *             e.printStackTrace();
     *             stagee.close();
     *             System.exit(0);
     *         }
     *
     *
     *         stage.setTitle("Tanks AGH");
     *         Scene scene = new Scene(root);
     *         scene.getStylesheets().add(getClass().getResource("/app_style.css").toExternalForm());
     *         stage.setScene(scene);
     *
     *         stage.show();
     *     }
     *
     *     public void tryToCreateUser(ActionEvent event)
     *     {
     *
     *         try
     *         {
     *             System.out.println("DEBUG: Beginning.stage w tryToCreateUser(): " + stage);
     *             number = UserInfo.createUser(stage,  loginButtom.getText() ,this);
     *             System.out.println("Go to load uni Scene");
     *             UserInfo.getClasess(number).get_uni().showUni();
     *         } catch (IllegalArgumentException e)
     *         {
     *             if (messageLabel != null) {
     *                 messageLabel.setText(e.getMessage() + ", give valid name");
     *             } else {
     *                 System.err.println("ERROR: messageLabel is not initialized in FXML or Controller. Check @FXML and fxmlLoader.setController(this).");
     *             }
     *         } catch (Exception e) {
     *             System.out.println("Problem with saved user's data");
     *             e.printStackTrace();
     *             try {
     *                 TimeUnit.SECONDS.sleep(1);
     *             } catch (InterruptedException ex) {}
     *             if (stage != null) {
     *                 stage.close();
     *             }
     *             System.exit(0);
     *         }
     *     }
     *
     *     private void createFakeUser()
     *     {
     *         try {
     *             UserInfo.createUser(null, "asd" , null);
     *         } catch (Exception e) {
     *             try {
     *                 TimeUnit.SECONDS.sleep(5);
     *             } catch (InterruptedException ex) {}
     *             stage.close();
     *             return;
     *         }
     *     }
     *
     *     public static void main(String[] args) {
     *         System.out.println("Everything started");
     *         Application.launch(args);
     *     }
     * }
     */
}
