package azul.view;

import azul.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class View_Game extends JFrame
{
    private final int tileSize = 50;
    private final Dimension dimTile = new Dimension(tileSize, tileSize);
    private final Game game;

    // Gameboards
    private final JPanel panGameboards;
    private HashMap<Player, PanGameboard> listPanGameboards = new HashMap<>();


    // Disks
    private final JPanel panDisks; // Panneau d'affichage des disks
    private ArrayList<PanDisk> listPanDisks = new ArrayList<>();


    public View_Game(Game game)
    {
        super("AZUL UTBM");

        this.game = game;

        JPanel container = new JPanel();
        JScrollPane scrl = new JScrollPane(container);
        add(scrl);

        container.setLayout(new GridLayout(2,1));

        // Partie supérieure : Les disques
        panDisks = new JPanel();
        container.add(panDisks);

        // Partie inférieure : Les gameboards
        panGameboards = new JPanel();
        container.add(panGameboards);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initGame()
    {
        // Utilisé une fois pour charger les disques
        game.getDisks().forEach(disk -> {
            PanDisk d = new PanDisk(disk);
            listPanDisks.add(d);
            panDisks.add(d);
        });

        // Utilisé une fois pour charger les gameboards
        game.getPlayers().forEach(player -> {
            PanGameboard gb = new PanGameboard(player.getGameboard());
            //gb.setBorder(BorderFactory.createLineBorder(Color.black));
            listPanGameboards.put(player, gb);
            panGameboards.add(gb);
        });
//        gb.setBorder(BorderFactory.createLineBorder(Color.black));
//        gb.add(new JLabel("TEST"));

        pack();
    }

    public ArrayList<PanDisk> getListPanDisks()
    {
        return listPanDisks;
    }

    public ArrayList<PanGameboard> getListPanGameboards()
    {
        return new ArrayList<>(listPanGameboards.values());
    }

    public void setDisksEnabled(boolean state)
    {
        listPanDisks.forEach(panDisk -> panDisk.getListBtnTiles().forEach(btn -> btn.setEnabled(state)));
    }

    public void setGameboardsEnabled(boolean state)
    {
        listPanGameboards.values().forEach(panGb -> panGb.setGameboardEnabled(state));
    }

    public PanGameboard getPlayerGameboard(Player player)
    {
        return listPanGameboards.get(player);
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




    //******************************************
    //              INNER CLASSES
    //******************************************

    /**
     * JPanel illustrant un seul disk
     */
    public class PanDisk extends JPanel
    {
        private final Disk disk;
        private final ArrayList<BtnTile> listBtnTiles = new ArrayList<>(); // On garde les boutons pour faire les events

        private PanDisk(Disk disk)
        {
            this.disk = disk;

            setLayout(new GridLayout(2,2));

            drawDisk();
        }

        public void drawDisk()
        {
            removeAll();

            disk.getTiles().forEach(tile ->
            {
                BtnTile t = new BtnTile(tile);
                t.setEnabled(false);
                listBtnTiles.add(t);
                add(t);
            });
        }

        public ArrayList<BtnTile> getListBtnTiles()
        {
            return listBtnTiles;
        }

        public Disk getDisk()
        {
            return disk;
        }

        public void removeTileType(TileType type)
        {
            ArrayList<BtnTile> btnTilesASuppr = new ArrayList<>();
            listBtnTiles.forEach(btnTile -> {
                if(btnTile.getTile().getType() == type)
                    btnTilesASuppr.add(btnTile);
            });
            btnTilesASuppr.forEach(btnTile -> {
                listBtnTiles.remove(btnTile);
                remove(btnTile);
            });
        }


        public class BtnTile extends JButton
        {
            private final Tile tile;

            private BtnTile(Tile tile)
            {
                super(tile.getType().toString());
                this.tile = tile;

                setMargin(new Insets(1,1,1,1));
                setPreferredSize(dimTile);
                setBackground(getTileColor(tile.getType()));
            }

            public Tile getTile()
            {
                return tile;
            }
        }
    }

    public class PanGameboard extends JPanel
    {
        private final Gameboard gb;
        private final JPanel panStock;
        private final JPanel panBtns;
        private final JPanel panWall;
        private final ArrayList<BtnRow> listRowsBtns = new ArrayList<>();

        private PanGameboard(Gameboard gb)
        {
            this.gb = gb;

            panStock = new JPanel();
            panBtns = new JPanel();
            panWall = new JPanel();

            //setLayout(new GridLayout(1,3));

            add(panStock);
            add(panBtns);
            add(panWall);

            drawStock();
            drawBtns();
            drawWall();
        }

        public void drawStock()
        {
            panStock.removeAll();

            ArrayList<ArrayList<Tile>> stock = gb.getStock();
            panStock.setLayout(new GridLayout(5, 1));
            for(int i = 0; i < 5; ++i)
            {
                for(int j = 0; j < 5; j++)
                {
                    JPanel panTileTmp = new JPanel();
                    panTileTmp.setPreferredSize(dimTile);
                    if(j >= 4 - i)
                        panTileTmp.setBackground(getTileColor(stock.get(i).get((i + j) % 4).getType()));
                    panStock.add(panTileTmp);
                }
            }
        }

        public void drawWall()
        {
            panWall.removeAll();

            ArrayList<ArrayList<Tile>> wall = gb.getWall();
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
            panBtns.setLayout(new GridLayout(5,1));
            for(int i = 0; i < 5; ++i)
            {
                BtnRow btn = new BtnRow(i);
                btn.setEnabled(false);
                listRowsBtns.add(btn);
                panBtns.add(btn);
            }
        }

        public ArrayList<BtnRow> getListRowsBtns()
        {
            return listRowsBtns;
        }

        public Gameboard getGameboard()
        {
            return gb;
        }

        public void setGameboardEnabled(boolean state)
        {
            listRowsBtns.forEach(btn -> btn.setEnabled(state));
        }


        public class BtnRow extends JButton
        {
            private final int row;

            private BtnRow(int row)
            {
                super();
                this.row = row;

                setPreferredSize(dimTile);
            }

            public int getRow()
            {
                return row;
            }
        }
    }
}
