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

        /*
        //----- création de la "liste" deck contenant 4 Tiles
        this.deck = new ArrayList<>();

        //créer 20 tiles de chaque type, chacune étant un objet différent? -> 100 objets?? ( sauf First et empty)
        int compteur_tiles = 0;

        //créer des tiles de chaque type
        Tile t_tcs = new Tile(TileType.TCS);
        Tile t_ap = new Tile(TileType.AP);
        Tile t_n = new Tile(TileType.N);
        Tile t_is = new Tile(TileType.IS);
        Tile t_hs = new Tile(TileType.HS);

        //ajouter 20 tiles de type TCS
        while(compteur_tiles<20){
            deck.add(t_tcs);
            compteur_tiles++;
        }
        //ajouter 20 tiles de type AP
        while(compteur_tiles<40){
            deck.add(t_ap);
            compteur_tiles++;
        }

        //ajouter 20 tiles de type N
        while(compteur_tiles<60){
            deck.add(t_n);
            compteur_tiles++;
        }

        //ajouter 20 tiles de type IS
        while(compteur_tiles<80) {
            deck.add(t_is);
            compteur_tiles++;
        }

        //ajouter 20 tiles de type HS
        while(compteur_tiles<100){
            deck.add(t_hs);
            compteur_tiles++;
        }
        */
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
