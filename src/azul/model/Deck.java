package azul.model;

import java.util.*;

public class Deck {

    private final ArrayList<Tile> deck;

    public Deck()
    {
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

    public List<Tile>getDeck(){
        return this.deck;
    }

    public Tile drawTile(){

        Random random = new Random();
        int nombre;
        int taille_deck = deck.size() - 1; // on fait "-1" vu qu'on compte sur les index
        Tile tile_a_retourner;

        nombre = random.nextInt(100); //génère un nombre en 0 et 99

        if (nombre > taille_deck ){

            tile_a_retourner = deck.get(taille_deck);
            deck.remove(taille_deck); // on la supprime du deck vu qu'elle est utilisée

            return tile_a_retourner;
        }
        tile_a_retourner = deck.get(nombre); // on retourne la tile situé à cet index
        deck.remove(nombre); // on la supprime du deck vu qu'elle est utilisée

        return tile_a_retourner;

    }
}
