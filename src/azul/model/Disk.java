package azul.model;

import java.util.*;

import static java.lang.Math.random;

public class Disk {
        
        private ArrayList<Tile> disk;

        public Disk()
        {
            disk = new ArrayList<>();
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

        public void add(Tile tile)
        {
            disk.add(tile);
        }

        public List<Tile> getTiles()
        {
            return disk;
        }
}

