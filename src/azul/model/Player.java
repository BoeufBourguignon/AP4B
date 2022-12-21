package azul.model;

import java.util.ArrayList;

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

    public Gameboard getGameboard()
    {
        return this.gameboard;
    }

    public ArrayList<Tile> pickTiles(Disk disk, TileType tileType)
            throws Exception
    {
        // Pioche les tuiles
        ArrayList<Tile> pickedTiles = disk.find(tileType);

        // Vérifie que des tuiles ont été piochées
        if(pickedTiles.size() == 0)
            throw new Exception("Aucune tuile n'a été piochée");

        // Vérifie si la tuile de premier joueur a été piochée
        int i = 0;
        while(i < pickedTiles.size() - 1 || pickedTiles.get(i).getType() != TileType.First) {
            i++;
        }
        if(pickedTiles.get(i).getType() == TileType.First) {
            // Si la tuile First est présente dans la liste, on l'enlève et on met le Player premier joueur
            pickedTiles.remove(i);
            this.isFirstPlayer = true;
        }

        return pickedTiles;
    }
}
