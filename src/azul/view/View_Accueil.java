package azul.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class View_Accueil extends JFrame
{
    JLabel lblVerifyPseudos;
    private final JButton btnValider = new JButton("Valider");
    private ArrayList<JTextField> listePseudos = new ArrayList<>();
    private final ArrayList<JButton> listeBtnsPremierJoueur = new ArrayList<>();

    Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
    int longueur = tailleMoniteur.width;
    int hauteur = tailleMoniteur.height;

    public View_Accueil()
    {
        super("AZUL UTBM - Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        // D'abord on demande le nombre de joueurs
        setContentPane(new PanNbJoueurs());

        setBounds(0,0,longueur,hauteur-25);
    }

    private void loadPanPseudos(int nbJoueurs)
    {
        PanPseudosJoueurs panPseudosJoueurs = new PanPseudosJoueurs(nbJoueurs);
        setContentPane(panPseudosJoueurs);

        setBounds(0,0,longueur-1,hauteur-25);

    }

    public void loadPanAskPremierJoueur()
    {
        PanChoixPremierJoueur panChoixPremierJoueur = new PanChoixPremierJoueur(getPseudos());
        setContentPane(panChoixPremierJoueur);

        setBounds(0,0,longueur-2,hauteur-25);
    }

    public JButton getBtnValider()
    {
        return btnValider;
    }

    public ArrayList<JButton> getListeBtnsPremierJoueur()
    {
        return listeBtnsPremierJoueur;
    }

    public ArrayList<String> getPseudos()
    {
        ArrayList<String> pseudos = new ArrayList<>();
        listePseudos.forEach(e -> pseudos.add(e.getText()));
        return pseudos;
    }

    private void resize()
    {
        pack();
        setSize(new Dimension((int)(getWidth() * 1.5), (int)(getHeight() * 1.5)));
    }

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
        }
    }

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
                View_Accueil.this.setContentPane(new PanNbJoueurs());
                View_Accueil.this.resize();
            });
        }
    }

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
                    View_Accueil.this.setContentPane(new PanPseudosJoueurs(pseudos.size()));
                    View_Accueil.this.resize();
                });

            }
        }
    }
}
