package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Discard {

    private ArrayList<Tile> discard;

    public Discard(){

        this.discard = new ArrayList<>();
    }

    public void addTiles(Tile tile){
        discard.add(tile);

    }

    public ArrayList<Tile> getDiscard() {
        return discard;
    }

    public Tile drawTile() {

        Random random = new Random();
        int nombre;
        int taille_deck = discard.size() - 1; // on fait "-1" vu qu'on compte sur les index
        Tile tile_a_retourner;

        nombre = random.nextInt(taille_deck + 1); //génère un nombre en 0 et le nombre de tiles dans le deck

        tile_a_retourner = discard.get(nombre); // on retourne la tile situé à cet index
        discard.remove(nombre); // on la supprime du deck vu qu'elle est utilisée

        return tile_a_retourner;
    }
}
