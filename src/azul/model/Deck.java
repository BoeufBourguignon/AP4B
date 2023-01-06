package azul.model;

import java.util.*;

public class Deck {

    //-----Attributs-----
    private ArrayList<Tile> deck;

    //-----Méthodes-----

    /**
     * Constructeur
     */
    public Deck(){

        //----- création de la "liste" deck contenant 4 Tiles
        this.deck = new ArrayList<>();

        for(int i = 0; i < 20; ++i)
        {
            this.deck.add(new Tile(TileType.AP));
            this.deck.add(new Tile(TileType.TCS));
            this.deck.add(new Tile(TileType.N));
            this.deck.add(new Tile(TileType.IS));
            this.deck.add(new Tile(TileType.HS));
        }
    }

    /**
     * @return le deck sous forme de liste
     */
    public List<Tile>getDeck(){
        return this.deck;
    }

    /**
     * choisis une Tile au hasard parmi celles disponibles
     * @return la Tile choisie
     */
    public Tile drawTile(){

        Random random = new Random();
        int nombre;
        int taille_deck = deck.size() - 1; // on fait "-1" vu qu'on compte sur les index
        Tile tile_a_retourner;

        nombre = random.nextInt(taille_deck+1); //génère un nombre en 0 et le nombre de tiles dans le deck

        tile_a_retourner = deck.get(nombre); // on retourne la tile situé à cet index
        deck.remove(nombre); // on la supprime du deck vu qu'elle est utilisée

        return tile_a_retourner;

    }
}
