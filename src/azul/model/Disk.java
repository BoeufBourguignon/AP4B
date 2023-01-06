package azul.model;

import java.util.*;

import static java.lang.Math.random;

public class Disk {
        
        protected ArrayList<Tile> disk;

        
       /**
       * Construteur de disk
       */
        public Disk()
        {
            disk = new ArrayList<>();
        }

        /**
        *
        * @param type : le type de tile à chercher
        * @return : la liste de toutes les tiles d'un disk du type demandé 
        */
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
        
        /**
        *
        * @param tile : la tile a ajouter sur un disk
        */
        public void add(Tile tile)
        {
            disk.add(tile);
        }

        /**
        * Accesseur d'un disk
        *
        * @return la liste de toutes les tiles d'un disk
        */
        public ArrayList<Tile> getTiles()
        {
            return disk;
        }
}

