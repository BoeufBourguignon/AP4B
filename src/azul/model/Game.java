package azul.model;

import java.util.*;

public class Game {
    private int nbPlayer;
    private int nbDisk;
    private List<Player> players;
    private List<Disk> disks;

    public Game()
    {
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
        player.isFirstPlayer=true;
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
        for (int i=0; i<players.size(); i++)
        {
            if (players.get(i).isFirstPlayer==true)
            {
                return players.get(i);
            }
        }
        return null;
    }

    public List<Disk> getDisks()
    {
        return disks;
    }

}
