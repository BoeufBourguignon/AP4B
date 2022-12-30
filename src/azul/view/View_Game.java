package azul.view;

import azul.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View_Game extends JFrame
{
    private final int tileSize = 50;
    private final Dimension dimTile = new Dimension(tileSize, tileSize);
    private final Game game;

    // Gameboards
    private final JPanel panGameboards;
    private ArrayList<ArrayList<JButton>> listBtnsGameboards;
    private ArrayList<PanGameboard> listPanGameboard;

    // Disks
    private final JPanel panDisks; // Panneau d'affichage des disks
    private ArrayList<ArrayList<JButton>> listBtnsDisks; // Pour les events
    private ArrayList<PanDisk> listPanDisks; // Pour draw les disks


    public View_Game(Game game)
    {
        super("AZUL UTBM");

        this.game = game;

        setLayout(new GridLayout(2,1));

        // Partie supérieure : Les disques
        panDisks = new JPanel();
        add(panDisks);

        // Partie inférieure : Les gameboards
        panGameboards = new JPanel();
        add(panGameboards);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initGame()
    {
        // Utilisé une fois pour charger les disques
        game.getDisks().forEach(disk -> {
            PanDisk d = new PanDisk(disk);
            listBtnsDisks.add(d.listTilesBtns);
            panDisks.add(d);
        });

        // Utilisé une fois pour charger les gameboards
        game.getPlayers().forEach(player -> {
            PanGameboard gb = new PanGameboard(player.getGameboard());
            listBtnsGameboards.add(gb.listRowsBtns);
            panGameboards.add(gb);
        });
    }

    public void drawDisks()
    {
        listPanDisks.forEach(PanDisk::drawDisk);
    }

    public void drawDisk(int index)
    {
        listPanDisks.get(index).drawDisk();
    }

    private Color getTileColor(TileType type)
    {
        Color color = Color.GRAY;
        switch(type)
        {
            case AP -> color = Color.BLUE;
            case IS -> color = Color.YELLOW;
            case N -> color = Color.MAGENTA;
            case TCS -> color = Color.ORANGE;
            case HS -> color = Color.GREEN;
        }
        return color;
    }


    /**
     * JPanel illustrant un seul disk
     */
    private class PanDisk extends JPanel
    {
        private final Disk disk;
        private final ArrayList<JButton> listTilesBtns = new ArrayList<>();

        public PanDisk(Disk disk)
        {
            this.disk = disk;

            setLayout(new GridLayout(2,2));

            drawDisk();
        }

        public void drawDisk()
        {
            ArrayList<Tile> tiles = disk.getTiles();

            tiles.forEach(tile ->
            {
                JButton t = new JButton();
                t.setPreferredSize(dimTile);
                t.setForeground(getTileColor(tile.getType()));
                listTilesBtns.add(t);
                add(t);
            });
        }
    }

    private class PanGameboard extends JPanel
    {
        private final Gameboard gb;
        private final JPanel panStock;
        private final JPanel panWall;
        private final ArrayList<JButton> listRowsBtns = new ArrayList<>();

        public PanGameboard(Gameboard gb)
        {
            this.gb = gb;

            panStock = new JPanel();
            panWall = new JPanel();

            add(panStock);
            add(panWall);

            drawStock();
            drawBtns();
            drawWall();
        }

        public void drawStock()
        {
            panStock.removeAll();

            ArrayList<ArrayList<Tile>> stock = gb.getStock();
            JPanel panStock = new JPanel();
            panStock.setLayout(new GridLayout(5, 1));
            for(int i = 0; i < 5; ++i)
            {
                for(int j = 0; j < 5; j++)
                {
                    JPanel panTileTmp = new JPanel();
                    panTileTmp.setPreferredSize(dimTile);
                    if(j >= 4 - i)
                    {
                        panTileTmp.setBackground(getTileColor(stock.get(i).get(j).getType()));
                    }
                    panStock.add(panTileTmp);
                }
            }
        }

        public void drawWall()
        {
            panWall.removeAll();

            ArrayList<ArrayList<Tile>> wall = gb.getWall();
            JPanel panWall = new JPanel();
            panWall.setLayout(new GridLayout(5, 5));
            for(int i = 0; i < 5; ++i)
            {
                for(int j = 0; j < 5; ++j)
                {
                    JPanel panTileTmp = new JPanel();
                    panTileTmp.setPreferredSize(dimTile);
                    TileType type = wall.get(i).get(j).getType();
                    if(type == TileType.Empty)
                    {
                        //Devinons le type attendu selon les coordonnées
                        switch ((5 + (i - j)) % 5)
                        {
                            case 0 -> panTileTmp.setBackground(new Color(200, 200, 255));
                            case 1 -> panTileTmp.setBackground(new Color(255, 255, 200));
                            case 2 -> panTileTmp.setBackground(new Color(255, 200, 255));
                            case 3 -> panTileTmp.setBackground(new Color(255, 243, 200));
                            case 4 -> panTileTmp.setBackground(new Color(200, 255, 200));
                        }
                    }
                    else
                    {
                        panTileTmp.setBackground(getTileColor(type));
                    }
                    panWall.add(panTileTmp);
                }
            }
        }

        private void drawBtns()
        {
            JPanel panBtns = new JPanel();
            panBtns.setLayout(new GridLayout(5,1));
            for(int i = 0; i < 5; ++i)
            {
                JButton btn = new JButton();
                panBtns.add(btn);
                listRowsBtns.add(btn);
            }
            add(panBtns);
        }
    }
}
