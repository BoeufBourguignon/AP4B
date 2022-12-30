package azul.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;

public class Disk {
        
        private List<Tile> disk;
        private Deck deck;

        public Disk()
        {
            for (int i=0; i<4; i++)
            {
                int rand = (int) Math.random()*(100);
                if (deck.getDeck().get(rand).getType()!=TileType.First)
                {
                    disk.add(deck.getDeck().get(rand));
                }
            }
        }

        public List<Tile> find (TileType type)
        {
            List<Tile> TypeOndisk = null;
            for (int i=0; i<disk.size(); i++)
            {
                if (disk.get(i).getType()==type)
                {
                    TypeOndisk.add(disk.get(i));
                }
            }
            return TypeOndisk;
        }
}

