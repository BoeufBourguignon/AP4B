package azul.model;

import java.util.*;

public class Game {
    private int nbPlayer;
    private int nbDisk;
    private Deck deck;
    private Discard discard;
    private final ArrayList<Player> players;
    private ArrayList<Disk> disks;
    private Center center;

    public Game()
    {
        players = new ArrayList<>();

        nbPlayer=0;
        nbDisk=0;
    }

    public void initGame()
            throws Exception
    {
        nbPlayer = players.size();

        // Vérification nombre de joueurs
        if(nbPlayer < 2 || nbPlayer > 5)
            throw new Exception("Nombre illégal de joueurs");

        // Instanciation du deck
        deck = new Deck();

        // Instanciation du discard
        discard = new Discard();

        // Instanciation des disks
        nbDisk = 2 * nbPlayer + 1;
        disks = new ArrayList<>();
        for(int i = 0; i < nbDisk; ++i)
        {
            Disk d = new Disk();
            disks.add(d);
            for(int j = 0; j < 4; ++j)
            {
                d.add(deck.drawTile());
            }
        }

        center = new Center();
    }

    public Discard getDiscard()
    {
        return discard;
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
        Player firstPlayer = players.get(0);

        int i = 0;
        while(i < players.size() - 1 && !players.get(i).isFirstPlayer())
            ++i;
        if(players.get(i).isFirstPlayer())
            firstPlayer = players.get(i);

        return firstPlayer;
    }

    public ArrayList<Disk> getDisks()
    {
        return disks;
    }

    public Center getCenter()
    {
        return center;
    }

    public Deck getDeck()
    {
        return deck;
    }

    public ArrayList<Tile> playerPickTile(Player player, Disk disk, TileType type)
    {
        // Prend les tuiles
        ArrayList<Tile> selected  = player.pickTiles(disk, type);

        // Met les autres tuiles au centre si on est pas déjà au centre
        // !!! mais du coup les events prennent toujours en compte les anciens disks, pas le centre !!!
        if(!(disk instanceof Center))
        {
            ArrayList<Tile> diskTiles = disk.getTiles();
            center.getTiles().addAll(diskTiles);
            diskTiles.clear();
        }

        return selected;
    }
}
