package azul.view;

import azul.model.TileType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;

public class View_Accueil extends JFrame
{
    JLabel lblVerifyPseudos;
    private final JButton btnValider;
    private ArrayList<JTextField> listePseudos = new ArrayList<>();
    private final ArrayList<JButton> listeBtnsPremierJoueur = new ArrayList<>();

    /**
     * Initialise quelques paramètres (police, taille de la fenêtre)
     * Crée une fenêtre demandant le nombre de joueurs
     */
    public View_Accueil()
    {
        super("AZUL UTBM - Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(Toolkit.getDefaultToolkit().getScreenSize()); // Fenêtre plein écran

        // Change la police par défaut
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, new javax.swing.plaf.FontUIResource("Futura",Font.BOLD,14));
        }

        // Instancie le bouton de validation des pseudos des joueurs
        btnValider = new JButton("Valider");

        // D'abord on demande le nombre de joueurs
        loadNewPanel(new PanNbJoueurs());
    }

    /**
     * Charge le panel du choix des pseudos des joueurs
     * @param nbJoueurs Nombre de joueurs
     */
    private void loadPanPseudos(int nbJoueurs)
    {
        loadNewPanel(new PanPseudosJoueurs(nbJoueurs));
    }

    /**
     * Charge le panel du choix du premier joueur
     */
    public void loadPanAskPremierJoueur()
    {
        loadNewPanel(new PanChoixPremierJoueur(getPseudos()));
    }

    /**
     * Charge un nouveau panel
     * @param pan Panel à charger
     */
    private void loadNewPanel(JPanel pan)
    {
        JPanel content = new JPanel();
        setContentPane(content);
        content.setLayout(new GridBagLayout());
        getContentPane().add(pan);
        validate();
    }

    /**
     * Récupère le bouton de validation des pseudos, pour l'utiliser dans le contrôleur
     * @return Bouton de validation
     */
    public JButton getBtnValider()
    {
        return btnValider;
    }

    /**
     * Récupère la liste des boutons des joueurs pouvant, utilisés pour choisir le premier joueur
     * @return Liste de boutons
     */
    public ArrayList<JButton> getListeBtnsPremierJoueur()
    {
        return listeBtnsPremierJoueur;
    }

    /**
     * Récupère la liste des pseudos renseignés dans le formulaire
     * @return Liste de pseudos
     */
    public ArrayList<String> getPseudos()
    {
        ArrayList<String> pseudos = new ArrayList<>();
        listePseudos.forEach(e -> pseudos.add(e.getText()));
        return pseudos;
    }

    /**
     * Vérifie tous les pseudos renseignés
     * Les pseudos ne doivent pas être vides et ne doivent pas être en double
     * @return True si aucun problème avec les pseudos, sinon False
     */
    public boolean verifyPseudos()
    {
        boolean isVerified = true;
        ArrayList<String> pseudos = getPseudos();
        for(int i = 0; i < pseudos.size() -1; ++i)
        {
            String pseudoToCompare = pseudos.get(i);

            // Pseudo vide ?
            if(pseudoToCompare.length() == 0)
            {
                isVerified = false;
                break;
            }

            // Pseudos identiques ?
            for(int j = i + 1; j < pseudos.size(); ++j)
            {
                if (pseudoToCompare.equals(pseudos.get(j)))
                {
                    isVerified = false;
                    break;
                }
            }
        }
        // Montre ou cache le message d'alerte
        lblVerifyPseudos.setVisible(!isVerified);
        return isVerified;
    }

    /**
     * Classe interne privée générant le panel demandant le nombre de joueurs
     */
    private class PanNbJoueurs extends JPanel
    {

        private PanNbJoueurs()
        {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            JPanel panLabelTitre = new JPanel();
            panLabelTitre.add(new JLabel("Veuillez choisir le nombre de joueurs"));
            add(panLabelTitre);

            JPanel panBoutonsJoueurs = new JPanel();
            JButton btnDeuxJoueurs = new JButton("Deux joueurs");
            panBoutonsJoueurs.add(btnDeuxJoueurs);
            JButton btnTroisJoueurs = new JButton("Trois joueurs");
            panBoutonsJoueurs.add(btnTroisJoueurs);
            JButton btnQuatreJoueurs = new JButton("Quatre joueurs");
            panBoutonsJoueurs.add(btnQuatreJoueurs);
            add(panBoutonsJoueurs);

            btnDeuxJoueurs.addActionListener(e -> loadPanPseudos(2));
            btnTroisJoueurs.addActionListener(e -> loadPanPseudos(3));
            btnQuatreJoueurs.addActionListener(e -> loadPanPseudos(4));

            JPanel panBoutonRegles = new JPanel();
            JButton btnRegles = new JButton("Règles");
            panBoutonRegles.add(btnRegles);
            add(panBoutonRegles);

            btnRegles.addActionListener(e -> loadNewPanel(new PanRegles()));
        }
    }

    /**
     * Classe interne privée générant le panel demandant les pseudos des joueurs
     */
    private class PanPseudosJoueurs extends JPanel
    {
        private PanPseudosJoueurs(int nbJoueurs)
        {
            listePseudos = new ArrayList<>();

            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

            JPanel panLabelTitre = new JPanel();
            panLabelTitre.add(new JLabel("Veuillez entrer les pseudos des joueurs"));
            add(panLabelTitre);

            for(int i = 0; i < nbJoueurs; ++i)
            {
                JPanel panTmp = new JPanel();
                JTextField txtTmp = new JTextField();
                txtTmp.setColumns(10);
                panTmp.add(new JLabel("Joueur " + (i+1)));
                panTmp.add(txtTmp);
                listePseudos.add(txtTmp);
                add(panTmp);
            }

            JPanel panVerifyPseudos = new JPanel();
            lblVerifyPseudos = new JLabel("Les pseudos ne sont pas corrects");
            lblVerifyPseudos.setForeground(Color.RED);
            lblVerifyPseudos.setVisible(false);
            panVerifyPseudos.add(lblVerifyPseudos);
            add(panVerifyPseudos);

            JPanel panBtns = new JPanel();
            JButton btnRetour = new JButton("Retour");
            panBtns.add(btnRetour);
            panBtns.add(btnValider);
            add(panBtns);

            btnRetour.addActionListener(e -> {
                loadNewPanel(new PanNbJoueurs());
            });
        }
    }

    /**
     * Classe interne privée générant le panel demandant le premier joueur
     */
    private class PanChoixPremierJoueur extends JPanel
    {
        private PanChoixPremierJoueur(ArrayList<String> pseudos)
        {
            if(pseudos.size() != 0)
            {
                setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

                JPanel panLabelTitre = new JPanel();
                panLabelTitre.add(new JLabel("Quel joueur est allé au portugal le plus récemment ?"));
                add(panLabelTitre);

                JPanel panBoutonsJoueurs = new JPanel();
                pseudos.forEach(pseudo -> {
                    JButton btnTmp = new JButton(pseudo);
                    panBoutonsJoueurs.add(btnTmp);
                    listeBtnsPremierJoueur.add(btnTmp);
                });
                add(panBoutonsJoueurs);

                JPanel panBtns = new JPanel();
                JButton btnRetour = new JButton("Retour");
                panBtns.add(btnRetour);
                add(panBtns);

                btnRetour.addActionListener(e -> {
                    loadNewPanel(new PanPseudosJoueurs(pseudos.size()));
                });

            }
        }
    }

    /**
     * Classe interne privée expliquant les règles
     */
    private class PanRegles extends JPanel
    {
        private PanRegles()
        {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            Dimension spacing = new Dimension(0, 30);

            JPanel panLienRegles = new JPanel();
            JButton btnRegles = new JButton("Voir les règles en ligne");
            panLienRegles.add(btnRegles);
            add(panLienRegles);

            btnRegles.addActionListener(e -> {
                if(Desktop.isDesktopSupported())
                {
                    try {
                        URI link = new URI("https://www.regledujeu.fr/azul/");
                        Desktop.getDesktop().browse(link);
                    } catch (URISyntaxException | IOException ex) {
                        throwDialogErrorLienRegles();
                    }
                }
                else
                {
                    throwDialogErrorLienRegles();
                }
            });

            add(Box.createRigidArea(spacing));

            JPanel panNouvellesTiles = new JPanel();
            panNouvellesTiles.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.gridy = c.gridx = 0; c.gridwidth = 2;
            panNouvellesTiles.add(new JLabel("Cette version du jeu de Azul change les tuiles."), c);
            c.gridy++; c.insets = new Insets(0, 0, 30, 0);
            panNouvellesTiles.add(new JLabel("Voici celles utilisées ici :"), c);

            c.gridy++; c.gridwidth = 1; c.insets = new Insets(0,0,0,0);
            panNouvellesTiles.add(createPanTiles(TileType.AP), c);
            c.gridx = 1;
            panNouvellesTiles.add(new JLabel("Algorithmique & programmation"), c);
            c.gridy++; c.gridx = 0;
            panNouvellesTiles.add(createPanTiles(TileType.IS), c);
            c.gridx = 1;
            panNouvellesTiles.add(new JLabel("Systèmes d'informations"), c);
            c.gridy++; c.gridx = 0;
            panNouvellesTiles.add(createPanTiles(TileType.N), c);
            c.gridx = 1;
            panNouvellesTiles.add(new JLabel("Réseaux"), c);
            c.gridy++; c.gridx = 0;
            panNouvellesTiles.add(createPanTiles(TileType.TCS), c);
            c.gridx = 1;
            panNouvellesTiles.add(new JLabel("Informatique théorique"), c);
            c.gridy++; c.gridx = 0;
            panNouvellesTiles.add(createPanTiles(TileType.HS), c);
            c.gridx = 1;
            panNouvellesTiles.add(new JLabel("Hardware & systèmes"), c);
            c.gridy++; c.gridx = 0;
            panNouvellesTiles.add(createPanTiles(TileType.First), c);
            c.gridx = 1;
            panNouvellesTiles.add(new JLabel("Tuile du premier joueur. Celui qui la pioche jouera en premier au tour suivant"), c);

            add(panNouvellesTiles);

            add(Box.createRigidArea(spacing));

            JPanel panBoutonRetour = new JPanel();
            JButton btnRetour = new JButton("Retour");
            panBoutonRetour.add(btnRetour);
            add(panBoutonRetour);

            btnRetour.addActionListener(e -> loadNewPanel(new PanNbJoueurs()));
        }

        private JPanel createPanTiles(TileType type)
        {
            Dimension dimTile = new Dimension(50,50);

            JPanel panTiles = new JPanel();

            JPanel panTileNormale = new JPanel();
            panTileNormale.setPreferredSize(dimTile);
            switch(type)
            {
                case AP -> panTileNormale.setBackground(Color.BLUE);
                case IS -> panTileNormale.setBackground(Color.YELLOW);
                case N -> panTileNormale.setBackground(Color.MAGENTA);
                case TCS -> panTileNormale.setBackground(Color.ORANGE);
                case HS -> panTileNormale.setBackground(Color.GREEN);
                case First -> panTileNormale.setBackground(Color.RED);
            }
            panTiles.add(panTileNormale);

            if(type != TileType.First)
            {
                JPanel panTilePastel = new JPanel();
                panTilePastel.setPreferredSize(dimTile);
                switch (type)
                {
                    case AP -> panTilePastel.setBackground(new Color(159, 182, 255, 150)); // AP
                    case IS -> panTilePastel.setBackground(new Color(255, 255, 200, 150)); // IS
                    case N -> panTilePastel.setBackground(new Color(255, 200, 255, 150)); // N
                    case TCS -> panTilePastel.setBackground(new Color(255, 207, 173, 150)); // TCS
                    case HS -> panTilePastel.setBackground(new Color(200, 255, 200, 150)); // HS
                }
                panTiles.add(panTilePastel);
            }

            return panTiles;
        }

        private void throwDialogErrorLienRegles()
        {
            JOptionPane.showMessageDialog(View_Accueil.this,
                    "L'application ne peut pas ouvrir directement les règles dans votre navigateur\n" +
                            "Veuillez utiliser ce lien dans votre navigateur : https://www.regledujeu.fr/azul/",
                    "Erreur ouverture du lien",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
