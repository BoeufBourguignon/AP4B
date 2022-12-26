import java.util.*;

public class Center extends Disk {

    //-----Attributs-----
    private Tile FirstTile;
    private ArrayList<Tile> liste_tiles_centre ;
    //faire une liste de tiles


    //-----Méthodes-----

    /**
     * Constructeur
     */
    public Center(){

        this.FirstTile = new Tile(TileType.Empty);
        this.liste_tiles_centre = new ArrayList<>();

    }

    /**
     * Déclaration du type de "FirstTile"
     */
    public void setFirstTile(TileType tile) {
        this.FirstTile = new Tile(TileType.First);
    }

    /**
     * Déclaration de la liste "Liste_tiles_centre"
     */
    public void setListe_tiles_centre(ArrayList<Tile> tiles) {
        this.liste_tiles_centre = tiles;
    }

    /**
     *
     * @param tile : le type de tile qu'on cherche
     * @return la liste de toutes les tiles du type recherché
     */
    public List<Tile> find(TileType tile){

        List<Tile> liste_retournee = new ArrayList<>();

        for(int i = 0; i< liste_tiles_centre.size();i++) {

            if (liste_tiles_centre.get(i).getType() == tile) {
                liste_tiles_centre.remove(i);
                liste_retournee.add(liste_tiles_centre.get(i)); // on ajoute l'élement à la liste retournée
            }
        }
        return liste_retournee;
    }
}
