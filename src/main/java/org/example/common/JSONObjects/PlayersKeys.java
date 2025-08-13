package org.example.common.JSONObjects;

import org.example.player.Controller;
import org.example.player.Gamer;

public class PlayersKeys extends SerialObject
{
    public PlayersKeys()
    {
    }
    public PlayersKeys(int i)
    {
        type = "PlayersKeys";
        Controller controller = Gamer.get_gamer()._windows._mainPage.getController();
        spaceIsPressed = controller.spaceIsPressed;
        rightKey = controller.rightKey;
        leftKey = controller.leftKey;
        mouseX = controller.mouseX;
        mouseY = controller.mouseY;
    }

    public boolean spaceIsPressed;
    public double mouseX;
    public double mouseY;
    public boolean rightKey;
    public boolean leftKey;

    @Override
    public String toString()
    {
        return "SpaceIsPressed: " + spaceIsPressed + ", mouseX: " + mouseX + ", mouseY: " + mouseY +  ", rightKey: " + rightKey + ", leftKey: " + leftKey;
    }


}
