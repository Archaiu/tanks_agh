package org.example.server;

public class CreatingAccounts
{
    public volatile int numbersOfPlayersWithAccount;
    public volatile boolean hostCanStartGame;

    private CreatingAccounts(){}
    private static CreatingAccounts instance;
    public static CreatingAccounts getInstance()
    {
        if ( instance == null) instance = new CreatingAccounts();
        return instance;
    }

    public synchronized boolean checkIfNickIsFreeAndCreate(String nick, int index)
    {
        if (!checkIfNickIsCorrect(nick)) return false;
        for ( Player player : Server.getInstance().getPlayers_())
        {
            if (nick.equals(player.nick)) return false;
        }
        Server.getInstance().getPlayers_().get(index).nick = nick;
        return true;
    }

    public boolean checkIfNickIsCorrect(String nick)
    {
        return !(nick.isEmpty() || nick.length() > 8);

    }

    public synchronized boolean checkIfUniIsFreeAndCreate(String uniIndex, int index)
    {
        int newIndex = Integer.parseInt(uniIndex);
        for ( Player player : Server.getInstance().getPlayers_())
        {
            if (player.uniIndex != null && player.uniIndex == newIndex) return false;
        }
        Server.getInstance().getPlayers_().get(index).uniIndex = newIndex;
        return true;
    }


}
