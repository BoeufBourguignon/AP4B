package azul.model;

import java.util.*;

public class Player
{
    private final String nickname;
    private boolean isFirstPlayer;
    private final Gameboard gameboard;

    public Player(String nickname)
    {
        this.nickname = nickname;
        this.gameboard = new Gameboard();
    }

    public String getNickname()
    {
        return this.nickname;
    }

    public boolean isFirstPlayer()
    {
        return this.isFirstPlayer;
    }

    public void setFirstPlayer(boolean isFirstPlayer)
    {
        this.isFirstPlayer = isFirstPlayer;
    }

    public Gameboard getGameboard()
    {
        return this.gameboard;
    }

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
