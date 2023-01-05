package azul.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Discard {

    //-----Attributs-----
    private ArrayList<Tile> discard;

    //-----Méthodes-----

    /**
     * Constructeur
     */
    public Discard(){

        this.discard = new ArrayList<>();
    }

    /**
     * ajoute une Tile à la discard
     * @param tile la Tile à ajouter
     */
    public void addTiles(Tile tile){
        discard.add(tile);

    }

    /**
     * @return la discard sous forme de liste
     */
    public ArrayList<Tile> getDiscard() {
        return discard;
    }

    /**
     * choisis une Tile au hasard parmi celles disponibles
     * @return la Tile choisie
     */
    public Tile drawTile() {

        Random random = new Random();
        int nombre;
        int taille_discard = discard.size() - 1; // on fait "-1" vu qu'on compte sur les index
        Tile tile_a_retourner;

        nombre = random.nextInt(taille_discard + 1); //génère un nombre en 0 et le nombre de tiles dans le deck

        tile_a_retourner = discard.get(nombre); // on retourne la tile situé à cet index
        discard.remove(nombre); // on la supprime du deck vu qu'elle est utilisée

        return tile_a_retourner;
    }
}
