package azul.model;

import java.util.*;

public class Game {
    private int nbPlayer;
    private int nbDisk;
    private Deck deck;
    private ArrayList<Tile> discard;
    private final ArrayList<Player> players;
    private ArrayList<Disk> disks;
    private Center center;

     /**
     * Constructeur de Game
     */
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
        discard = new ArrayList<>();

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
    
     /**
     * Mutateur du nombre total de joueur
     *
     * @param nbPlayer : le nombre de joueurs choisis en debut de partie
     */
    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

     /**
     * Mutateur du premier joueur
     *
     * @param player : le joueur étant le premier joueur
     */
    public void setFirstPlayer(Player player)
    {
        player.setFirstPlayer(true);
    }

     /**
     *
     * @param player : le joueur à ajouter à la liste qui regroupe tous les joueurs du jeu
     */
    public void addPlayer(Player player)
    {
        players.add(player);
    }

     /**
     * Accesseur des joueurs
     *
     * @return une liste avec tous les joueurs
     */
    public List<Player> getPlayers()
    {
        return players;
    }

     /**
     * Accesseur du premier joueur
     *
     * @return le joueur ayant la tuile numéro 1 
     */
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
    
     /**
     * Accesseur des disks
     *
     * @return une liste de disks
     */
    public ArrayList<Disk> getDisks()
    {
        return disks;
    }

     /**
     * Accesseur du Centre du plateau de jeu
     *
     * @return le centre
     */
    public Center getCenter()
    {
        return center;
    }

    
     /**
     *
     * @param player : joueur dont c'est le tour de jouer
     * @param disk : disk selectionné par le joueur
     * @param type : type de tile choisit  par le joueur
     * @return : la liste contenant toutes les tiles de la catégorie choisit du disk choisit 
     */
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
