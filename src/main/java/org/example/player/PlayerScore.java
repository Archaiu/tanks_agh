package org.example.player;

import javafx.beans.property.SimpleStringProperty;

public class PlayerScore {
    private final SimpleStringProperty place;
    private final SimpleStringProperty nickname;
    private final SimpleStringProperty score;

    public PlayerScore(String place, String nickname, String score) {
        this.place = new SimpleStringProperty(place);
        this.nickname = new SimpleStringProperty(nickname);
        this.score = new SimpleStringProperty(score);
    }

    public String getPlace() { return place.get(); }
    public String getNickname() { return nickname.get(); }
    public String getScore() { return score.get(); }

    public SimpleStringProperty placeProperty() { return place; }
    public SimpleStringProperty nicknameProperty() { return nickname; }
    public SimpleStringProperty scoreProperty() { return score; }
}