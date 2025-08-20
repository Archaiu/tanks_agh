package org.example.player.windows;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.common.POJO.MyPair;
import org.example.player.Engine;
import org.example.player.Gamer;
import org.example.player.PlayerScore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Results implements Window
{

    @FXML
    private TableView<PlayerScore> table;
    @FXML
    private TableColumn<PlayerScore,String> place;
    @FXML
    private TableColumn<PlayerScore,String> nick;
    @FXML
    private TableColumn<PlayerScore,String> result;

    private ObservableList<PlayerScore> players;

    @Override
    public void loadWindow()
    {
        Parent root = null;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scores.fxml"));
            loader.setController(this);
            root = loader.load();
        } catch (IOException e) {
            System.err.println("Failed to load FXML for results.fxml");
            System.exit(1);
        }

        place.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        nick.setCellValueFactory(cellData -> cellData.getValue().placeProperty());
        result.setCellValueFactory(cellData -> cellData.getValue().scoreProperty() );
        players = FXCollections.observableArrayList();

        table.setItems(players);

        updateResults();

        Scene scene = new Scene(root);
        Gamer.get_gamer().stage.setScene(scene);
    }

    public void updateResults()
    {
        int [] results = Engine.getEngine().results;
        ArrayList<MyPair<String, Integer>> resultList = new ArrayList<>();
        for (int i = 0; i < results.length; i++)
        {
            resultList.add(new MyPair<>(Gamer.get_gamer().players.get(i).name, results[i]));
        }
        resultList.sort(Comparator.comparingInt(MyPair::getValue));
        Collections.reverse(resultList);
        for  (int i = 0; i < results.length; i++)
        {
            System.out.println(results[i]);
            int place = ((i == 0) || !(Integer.parseInt(players.get(i-1).getScore()) == resultList.get(i).getValue()) ? i+1: Integer.parseInt(players.get(i-1).getPlace()));
            players.add(new PlayerScore(Integer.toString(place),resultList.get(i).getKey(),Integer.toString(resultList.get(i).getValue())));
        }
    }

    public void buttonClicked(ActionEvent actionEvent)
    {
        System.out.println("Nothing happend at this moment");
    }
}
