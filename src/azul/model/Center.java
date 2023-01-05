package azul.model;

import java.util.*;

public class Center extends Disk {
    //-----Attributs-----
    private Tile FirstTile;


    //-----Méthodes-----

    /**
     * Constructeur
     */
    public Center() {

        super();
        this.FirstTile = new Tile(TileType.First);
        disk.add(0, FirstTile);
    }

    /**
     * Déclaration du type de "FirstTile"
     */
    public void setFirstTile() {
        this.FirstTile = new Tile(TileType.First);
    }

    /**
     *
     * @param tile : le type de tile qu'on cherche
     * @return la liste de toutes les tiles du type recherché
     */
    public ArrayList<Tile> find(TileType tile){

        ArrayList<Tile> liste_retournee = super.find(tile);

        if(FirstTile != null)
        {
            liste_retournee.add(FirstTile);
            disk.remove(FirstTile);
            FirstTile = null;
        }

        return liste_retournee;
    }
}
