package org.example;

import com.sun.tools.javac.Main;
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
    private Tank _tank;
    private Clasess _clasess;
    public void setName(String newName) { _name=newName; }
    public void setPhoto(String newPhoto) { _photo=newPhoto; }
    public static ImageView getPhoto(int i) { return UserInfo[i]._photo}
    public static Tank getTank(int number) { return _users[number]._tank; }
    public static void setTank(int nunber, Tank tank) { _users[nunber]._tank=tank; }
    public static Clasess getClasess(int number) { return _users[number]._clasess; }
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
        _length++;
        return _length-1;
    }
}
