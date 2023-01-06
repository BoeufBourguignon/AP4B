package azul.view;

import azul.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class View_Game extends JFrame
{
    private final int tileSize = 50;
    private final Dimension dimTile = new Dimension(tileSize, tileSize);
    private final GridBagConstraints c = new GridBagConstraints();
    private final Game game;

    // Gameboards
    private final JPanel panGameboards;
    private final HashMap<Player, PanGameboard> listPanGameboards = new HashMap<>();


    // Disks
    private final JPanel panDisks; // Panneau d'affichage des disks
    private final ArrayList<PanDisk> listPanDisks = new ArrayList<>();
    private PanDisk panCenter;

    /**
     * Constructeur de la fenêtre affichant les éléments du jeu (disques, plateaux de jeu)
     * @param game Proxy du modèle
     */
    public View_Game(Game game)
    {
        super("AZUL UTBM");

        this.game = game;

        setLayout(new GridBagLayout());

        // Partie supérieure : Les disques
        panDisks = new JPanel();
        c.gridx = 0; c.gridy = 0;
        add(panDisks, c);

        // Partie inférieure : Les gameboards
        panGameboards = new JPanel();
        panGameboards.setLayout(new GridBagLayout());
        c.gridx = 0; c.gridy = 1;
        add(panGameboards, c);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize());
    }

    /**
     * Initialise la vue des éléments du jeu, en se basant sur les données du modèle
     */
    public void initGame()
    {
        // Partie supérieure : Disques
        // Un panel avec text et disques, un autre panel avec text et centre

        //   Disks
        JPanel panDisks_BagDisks = new JPanel();
        panDisks_BagDisks.setLayout(new GridBagLayout());

        c.gridy = 0; c.gridx = 0;
        panDisks_BagDisks.add(new JLabel("Disques", JLabel.CENTER), c);
        c.gridy = 1; c.gridx = 0;
        JPanel panDisks_Disks = new JPanel();
        panDisks_BagDisks.add(panDisks_Disks, c);
        game.getDisks().forEach(disk -> {
            PanDisk d = new PanDisk(disk);
            listPanDisks.add(d);
            panDisks_Disks.add(d);
        });

        panDisks.add(panDisks_BagDisks);

        //  Centre
        JPanel panDisks_BagCenter = new JPanel();
        panDisks_BagCenter.setLayout(new GridBagLayout());

        c.gridy = 0; c.gridx = 0;
        panDisks_BagCenter.add(new JLabel("Centre", JLabel.CENTER), c);
        c.gridy = 1; c.gridx = 0;
        panCenter = new PanDisk(game.getCenter());
        panDisks_BagCenter.add(panCenter, c);
        listPanDisks.add(panCenter);

        panDisks.add(panDisks_BagCenter);


        // Partie inférieure : Gameboards
        for(int i = 0; i < game.getPlayers().size(); ++i)
        {
            PanGameboard gb = new PanGameboard(game.getPlayers().get(i));
            listPanGameboards.put(game.getPlayers().get(i), gb);
            c.insets = new Insets(5,5,5,5);
            if(i == 0)
            {
                c.gridy = 0; c.gridx = 0;
            }
            else if(i == 1)
            {
                c.gridy = 0; c.gridx = 1;
            }
            else
            {
                if(game.getPlayers().size() == 3)
                {
                    c.gridy = 1;
                    c.gridx = 0;
                    c.gridwidth = 2;
                }
                else if(i == 2)
                {
                    c.gridy = 1;
                    c.gridx = 0;
                }
                else
                {
                    c.gridy = 1;
                    c.gridx = 1;
                }
            }
            panGameboards.add(gb, c);
        }
    }

    /**
     * Accesseur de la liste des panels des disques
     * @return Liste de panels
     */
    public ArrayList<PanDisk> getListPanDisks()
    {
        return listPanDisks;
    }

    /**
     * Accesseur du panel du centre
     * @return Panel
     */
    public PanDisk getCenterPanDisk()
    {
        return panCenter;
    }

    /**
     * Récupère une map qui associe à chaque joueur le panel de sa gameboard
     * @return Map des joueurs et la vue de leur gameboard
     */
    public HashMap<Player, PanGameboard> getListPanGameboards()
    {
        //return new ArrayList<>(listPanGameboards.values());
        return listPanGameboards;
    }

    /**
     * Active o désactive tous les disques
     * @param state True si activés, False sinon
     */
    public void setDisksEnabled(boolean state)
    {
        listPanDisks.forEach(panDisk -> panDisk.getListBtnTiles().forEach(btn -> btn.setEnabled(state)));
        panCenter.getListBtnTiles().forEach(btn -> btn.setEnabled(state));
    }

    /**
     * Active ou désactive toutes les gameboards
     * @param state True si activées, False sinon
     */
    public void setGameboardsEnabled(boolean state)
    {
        listPanGameboards.values().forEach(panGb -> panGb.setGameboardEnabled(state));
    }

    /**
     * Récupère la map d'un joueur et de sa gameboard
     * @param player Player dont on souhaite récupérer la vue de la gameboard
     * @return Le player et sa gameboard
     */
    public Map.Entry<Player, PanGameboard> getPlayerGameboard(Player player)
    {
        return new AbstractMap.SimpleEntry<>(player, listPanGameboards.get(player));
    }

    /**
     * Récupère la couleur d'une tuile pour la vue
     * @param type Type de la tuile
     * @return Couleur de la tuile
     */
    private Color getTileColor(TileType type)
    {
        Color color = Color.LIGHT_GRAY;
        switch(type)
        {
            case AP -> color = Color.BLUE;
            case IS -> color = Color.YELLOW;
            case N -> color = Color.MAGENTA;
            case TCS -> color = Color.ORANGE;
            case HS -> color = Color.GREEN;
            case First -> color = Color.RED;
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
            setBackground(new Color(255, 220, 177));

            drawDisk();
        }

        /**
         * Dessine le disk selon l'instance de disk en attribut
         */
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

        /**
         * Récupère les boutons de toutes les tuiles du disque
         * @return Liste de boutons
         */
        public ArrayList<BtnTile> getListBtnTiles()
        {
            return listBtnTiles;
        }

        /**
         * Récupère le disque du modèle lié à cette vue
         * @return Référence vers un disque
         */
        public Disk getDisk()
        {
            return disk;
        }

        /**
         * Supprime le type de tuile sélectionné sur une vue d'un disque
         * @param panDisk Vue du disque ciblé
         * @param type Type de tuile à supprimer
         */
        public void pickTiles(PanDisk panDisk, TileType type)
        {
            // Si disk normal : on supprime les tiles du disk et on ajoute les autres au centre (que dans la liste)
            // Si disk center : on supprime les tiles du disk

            ArrayList<BtnTile> selectedBtnTiles = new ArrayList<>();
            ArrayList<BtnTile> nonSelectedBtnTiles = new ArrayList<>();

            listBtnTiles.forEach(btnTile -> {
                if(btnTile.getTile().getType() == type)
                    selectedBtnTiles.add(btnTile);
                else if(!(panDisk.getDisk() instanceof Center))
                    nonSelectedBtnTiles.add(btnTile);
            });

            if(panDisk.getDisk() instanceof Center)
            {
                selectedBtnTiles.forEach(btnTile -> {
                    remove(btnTile);
                    listBtnTiles.remove(btnTile);
                });
            }
            else
            {
                selectedBtnTiles.forEach(this::remove);
                nonSelectedBtnTiles.forEach(this::remove);
            }
        }

        /**
         * Bouton illustrant une seule tuile
         */
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
                if(tile.getType() == TileType.AP)
                {
                    setForeground(Color.WHITE);
                }
            }

            /**
             * Accesseur de la tuile du modèle liée à la vue
             * @return Référence vers une tuile
             */
            public Tile getTile()
            {
                return tile;
            }
        }
    }

    /**
     * JPanel illustrant une seul gameboard
     */
    public class PanGameboard extends JPanel
    {
        private final Player player;
        private final JPanel panStock;
        private final JPanel panBtns;
        private final JPanel panWall;
        private final JPanel panMalus;
        private final ArrayList<BtnRow> listRowsBtns = new ArrayList<>();
        private final JButton btnMalus;
        private final JLabel score;

        private PanGameboard(Player p)
        {
            this.player = p;

            panStock = new JPanel();
            panBtns = new JPanel();
            panWall = new JPanel();
            panMalus = new JPanel();
            score = new JLabel();

            btnMalus = new JButton();
            btnMalus.setPreferredSize(dimTile);

            panStock.setOpaque(false);

            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.insets = new Insets(5, 5, 5, 5);

            c.gridy = 0; c.gridx = 0; c.gridwidth = 1;
            add(score);
            c.gridy = 0; c.gridx = 1; c.gridwidth = 2;
            add(new JLabel(player.getNickname(), JLabel.CENTER), c);
            c.gridy = 1; c.gridx = 0; c.gridwidth = 1;
            add(panStock, c);
            c.gridy = 1; c.gridx = 1;
            add(panBtns, c);
            c.gridy = 1; c.gridx = 2;
            add(panWall, c);
            c.gridy = 2; c.gridx = 0; c.gridwidth = 3;
            JPanel panMalusGroup = new JPanel();
            panMalusGroup.add(panMalus);
            panMalusGroup.add(btnMalus);
            add(panMalusGroup, c);

            drawStock();
            drawBtns();
            drawWall();
            drawMalus();
            drawScore();
        }

        /**
         * Met à jour uniquement le stock
         */
        public void drawStock()
        {
            panStock.removeAll();

            ArrayList<ArrayList<Tile>> stock = player.getGameboard().getStock();
            panStock.setLayout(new GridLayout(5, 1));
            for(int i = 0; i < 5; ++i)
            {
                // Pour dessiner les lignes il faut y aller à l'envers
                for(int j = 0; j < 5; j++)
                {
                    JPanel panTileTmp = new JPanel();
                    panTileTmp.setPreferredSize(dimTile);
                    if(j >= 4 - i)
                    {
                        panTileTmp.setBackground(getTileColor(
                                stock.get(i).size() > 4 - j
                                ? stock.get(i).get(4 - j).getType()
                                : TileType.Empty
                        ));
                    }
                    else
                    {
                        panTileTmp.setOpaque(false);
                    }
                    panStock.add(panTileTmp);
                }
            }
        }

        /**
         * Met à jour uniquement le mur
         */
        public void drawWall()
        {
            panWall.removeAll();

            ArrayList<ArrayList<Tile>> wall = player.getGameboard().getWall();
            ArrayList<ArrayList<TileType>> referenceWall = player.getGameboard().getReference_wall();
            panWall.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
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
                        switch (referenceWall.get(i).get(j))
                        {
                            case AP -> panTileTmp.setBackground(new Color(159, 182, 255, 150)); // AP
                            case IS -> panTileTmp.setBackground(new Color(255, 255, 200, 150)); // IS
                            case N -> panTileTmp.setBackground(new Color(255, 200, 255, 150)); // N
                            case TCS -> panTileTmp.setBackground(new Color(255, 207, 173, 150)); // TCS
                            case HS -> panTileTmp.setBackground(new Color(200, 255, 200, 150)); // HS
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

        /**
         * Dessine uniquement les boutons, un bouton étant associé à une ligne du stock et du mur
         */
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

        /**
         * Met à jour uniquement le malus
         */
        public void drawMalus()
        {
            panMalus.removeAll();

            panMalus.setLayout(new GridLayout(1, 7));

            ArrayList<Tile> malus = player.getGameboard().getMalus();
            for(int i = 0; i < 7; ++i)
            {
                JPanel t = new JPanel();
                t.setLayout(new BorderLayout());
                t.setPreferredSize(dimTile);
                JLabel lbl = new JLabel();
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                t.add(lbl, BorderLayout.CENTER);
                if(i < 2)
                    lbl.setText("-1");
                else if(i < 5)
                    lbl.setText("-2");
                else
                    lbl.setText("-3");

                if(i < malus.size())
                {
                    t.setBackground(getTileColor(malus.get(i).getType()));
                }

                panMalus.add(t);
            }
        }

        /**
         * Met à jour uniquement le score
         */
        public void drawScore()
        {
            score.setText("Score : " + player.getGameboard().getScore());
        }

        /**
         * Récupère les boutons des lignes du stock et du wall
         * @return Liste de boutons
         */
        public ArrayList<BtnRow> getListRowsBtns()
        {
            return listRowsBtns;
        }

        /**
         * Récupère le bouton du malus, servant à stocker des tuiles directement dans le malus
         * @return Bouton
         */
        public JButton getBtnMalus()
        {
            return btnMalus;
        }

        /**
         * Récupère la gameboard du modèle liée à la vue
         * @return Référence vers une gameboard
         */
        public Gameboard getGameboard()
        {
            return player.getGameboard();
        }

        /**
         * Active ou désactive la gameboard
         * @param state True si activé, False sinon
         */
        public void setGameboardEnabled(boolean state)
        {
            listRowsBtns.forEach(btn -> btn.setEnabled(state));
            btnMalus.setEnabled(state);
        }

        /**
         * Met la gameboard en évidence dans la vue, pour signaler que son joueur doit jouer
         * @param isPlaying True si le joueur doit jouer, False sinon
         */
        public void setPlaying(boolean isPlaying)
        {
            setBackground(isPlaying ? new Color(200, 255, 200) : null);
        }

        /**
         * Bouton illustrant une ligne du stock et du wall
         */
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
