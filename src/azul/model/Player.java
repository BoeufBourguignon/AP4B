package azul.model;

import java.util.*;

public class Player
{
    private final String nickname;
    private boolean isFirstPlayer;
    private final Gameboard gameboard;

    /**
     * Constructeur
     * @param nickname Pseudo du joueur
     */
    public Player(String nickname)
    {
        this.nickname = nickname;
        this.gameboard = new Gameboard();
    }

    /**
     * Accesseur du pseudo du joueur
     * @return nickname
     */
    public String getNickname()
    {
        return this.nickname;
    }

    /**
     * Accesseur du booléen indiquant si le joueur est le premier à jouer
     * @return isFirstPlayer
     */
    public boolean isFirstPlayer()
    {
        return this.isFirstPlayer;
    }

    /**
     * Mutateur du booléen indiquant si le joueur est le premier à jouer
     * @param isFirstPlayer Premier à jouer
     */
    public void setFirstPlayer(boolean isFirstPlayer)
    {
        this.isFirstPlayer = isFirstPlayer;
    }

    /**
     * Accesseur du gameboard du joueur
     * @return gameboard
     */
    public Gameboard getGameboard()
    {
        return this.gameboard;
    }

    /**
     * Permet au joueur de piocher toutes les tuiles d'un type sur un disk
     * @param disk Disque sur lequel piocher
     * @param tileType Type à piocher
     * @return Liste des tuiles piochées
     */
    public ArrayList<Tile> pickTiles(Disk disk, TileType tileType)
    {
        // Pioche les tuiles
        ArrayList<Tile> pickedTiles = disk.find(tileType);

        // Vérifie que des tuiles ont été piochées
        if(pickedTiles.size() != 0)
        {
            // Vérifie si la tuile de premier joueur a été piochée
            int i = 0;
            while(i < pickedTiles.size() - 1 && pickedTiles.get(i).getType() != TileType.First) {
                i++;
            }
            if(pickedTiles.get(i).getType() == TileType.First) {
                // Si la tuile First est présente dans la liste, on met le Player premier joueur
                this.isFirstPlayer = true;
            }
        }

        return pickedTiles;
    }
}
