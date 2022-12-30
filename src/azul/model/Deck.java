package azul.model;

import java.util.ArrayList;
import java.util.List;

import static azul.model.TileType.TCS;

public class Deck {
    private List<Tile> deck = new ArrayList<>(101);

    public Deck()
    {
        for (int i=0; i<5; i++)
        {
            for (int j = 0; j < 20; j++)
            {
                Tile tile = new Tile(i);
                deck.add(tile);
            }
        }
        Tile tile = new Tile(6);
        deck.add(tile);
    }

    public Tile drawTile()
    {

        return null;
    }

    public List<Tile> getDeck()
    {
        return deck;
    }
}
