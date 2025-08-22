package org.example.player.gameLogic;

public class Informations
{
    public String name;
    public int uniIndex = -1;
    public Tank tank;

    Informations(String name, int uniIndex)
    {
        this.name = name;
        this.uniIndex = uniIndex;
        tank = new Tank(uniIndex);
    }

    @Override
    public String toString()
    {
        return "Name: " + name + " uniIndex: " + uniIndex;
    }
}
