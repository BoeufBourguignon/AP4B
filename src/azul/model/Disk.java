package azul.model;

import java.util.*;

import static java.lang.Math.random;

public class Disk {
        
        protected ArrayList<Tile> disk;

        public Disk()
        {
            disk = new ArrayList<>();
        }

        public ArrayList<Tile> find (TileType type)
        {
            ArrayList<Tile> TypeOndisk = new ArrayList<>();
            for (int i=0; i<disk.size(); i++)
            {
                if (disk.get(i).getType()==type)
                {
                    Tile tileFound = disk.get(i);
                    TypeOndisk.add(tileFound);
                }
            }
            TypeOndisk.forEach(tile -> disk.remove(tile));
            return TypeOndisk;
        }

        public void add(Tile tile)
        {
            disk.add(tile);
        }

        public ArrayList<Tile> getTiles()
        {
            return disk;
        }
}

