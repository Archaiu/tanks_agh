package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;

public class UserInfo {
    private static Image[] photos= new Image[8];
    static{
        try {
            photos[0] = new Image(UserInfo.class.getResourceAsStream("/universities/agh.png"));
            System.out.println("No problem with reading .png");
            photos[1] = new Image(UserInfo.class.getResourceAsStream("/universities/papieski.jpg"));
            System.out.println("No problem with reading .jpg");
            photos[2] = new Image(UserInfo.class.getResourceAsStream("/universities/PK.png"));
            photos[3] = new Image(UserInfo.class.getResourceAsStream("/universities/uek.jpg"));
            photos[4] = new Image(UserInfo.class.getResourceAsStream("/universities/uj.png"));
            photos[5] = new Image(UserInfo.class.getResourceAsStream("/universities/uken.png"));
            photos[6] = new Image(UserInfo.class.getResourceAsStream("/universities/up.jpg"));
            photos[7] = new Image(UserInfo.class.getResourceAsStream("/universities/ur.jpg"));
            for (var photo : photos) {
                if (photo == null) {
                    throw new RuntimeException("Can't load photos");
                }
            }
        }
        catch (RuntimeException e) {
            System.out.println(e);
            System.exit(-1);
        }
    }
    private static UserInfo [] _users = new UserInfo [8];
    private static int _length = 0;
    private String _name;
    private String _photo;
    private Beginning _beginning;
    private Uni _uni;
    private Stage _stage;
    private Tank _tank;
    private MainPage _main = new MainPage();
    public void setName(String newName) { _name=newName; }
    public void setPhoto(String newPhoto) { _photo=newPhoto; }
    public void setBeginning (Beginning newBeginning) { _beginning=newBeginning; }
    public void setStage(Stage newStage) {_stage=newStage; }
    public void setUni(Uni newUni) { _uni=newUni; }
    public static Stage getStage(int number) { return _users[number]._stage; }
    public static Uni getUni(int number) { return _users[number]._uni; }
    public static Image getPhoto(int i){return photos[i];}
    public static MainPage getMain( int number) { return _users[number]._main; }
    public static Tank getTank(int number) { return _users[number]._tank; }
    public static void setTank(int nunber, Tank tank) { _users[nunber]._tank=tank; }
    public static int CreateUser(Stage stage, Beginning beginning, String name) throws Exception
    {
        System.out.println("Try to create user");
        if ( _length == 8 )
            throw new Exception("Can't create new user");
        if (name.isEmpty() || name.length() > 8)
            throw new IllegalArgumentException("Name need to be between 1 and 8 characters");
        for (int i = 0; i<_length; i++)
            if (_users[i]._name.equals(name))
                throw new Exception("Name already exists");
        System.out.println("Everything seems correct");
        _users[_length] = new UserInfo();
        _users[_length].setName(name);
        _users[_length].setBeginning(beginning);
        _users[_length].setStage(stage);
        _users[_length].setUni(new Uni(_length));
        _length++;
        return _length-1;
    }
}
