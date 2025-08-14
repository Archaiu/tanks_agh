package org.example.mechanics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SingleRound {
    private Set<Bullet> bullets = new HashSet<Bullet>();
    private boolean[] isTankAlive = new boolean[UserInfo.getNumberOfPlayers()];
    {
        for( int i = 0; i< UserInfo.getNumberOfPlayers(); i++ )
        {
            isTankAlive[i] = true;
        }
    }
    private int numberOfAliveTanks = UserInfo.getNumberOfPlayers();
    public void tankDestroyed(int i)
    {
        isTankAlive[i] = false;
        numberOfAliveTanks--;
        UserInfo.getClasess(i).get_controller().destroyTank();
        if ( numberOfAliveTanks == 0 )
        {
            UserInfo.newRound(false, i);
        }
    }
    public void startRound()
    {
        System.out.println("Starting round");
        for (int i = 0; i < UserInfo.getNumberOfPlayers(); i++)
        {
            try{
            UserInfo.getClasess(i).get_controller().destroyTank();
            } catch ( NullPointerException ignored) {}
            UserInfo.getTank(i).spawnTank();
            UserInfo.getClasess(i).get_mainPage().showMainPage();
            for (int j = 0; j < UserInfo.getNumberOfPlayers(); j++) {
                UserInfo.getClasess(i).get_controller().setVisualNumberOfRound(UserInfo.getNumberOfGames());
            }
        }
    }
    public boolean bulletExists(Bullet bullet)
    {
        return bullets.contains(bullet);
    }
    public void bulletCreated(Bullet bullet)
    {
        bullets.add(bullet);
    }
    public void bulletDestroyed(Bullet bullet)
    {
        bullets.remove(bullet);
    }
}
