package azul.model;

import java.util.*;

public class Game {
    private int nbPlayer;
    private int nbDisk;
    private final ArrayList<Player> players;
    private List<Disk> disks;

    public Game()
    {
        players = new ArrayList<>();

        nbPlayer=0;
        nbDisk=0;
    }

    public void initGame()
    {
        switch(nbPlayer)
        {
            case 2:
                nbDisk=5;
                break;
            case 3 :
                nbDisk=7;
                break;
            case 4:
                nbDisk=9;
                break;
            default:
                break;
        }

        disks = new ArrayList<>(nbDisk);
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public void setFirstPlayer(Player player)
    {
        player.setFirstPlayer(true);
    }

    public void addPlayer(Player player)
    {
        players.add(player);
    }

    public List<Player> getPlayers()
    {
        return players;
    }

    public Player getFirstPlayer()
    {
        Player firstPlayer = null;

        int i = 0;
        while(i < players.size() - 1 && !players.get(i).isFirstPlayer())
            ++i;
        if(players.get(i).isFirstPlayer())
            firstPlayer = players.get(i);

        return firstPlayer;
    }

    public List<Disk> getDisks()
    {
        return disks;
    }

}
