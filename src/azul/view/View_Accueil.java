package azul.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View_Accueil extends JFrame
{
    private final JButton btnValider = new JButton("Valider");
    private ArrayList<JTextField> listePseudos = new ArrayList<>();

    public View_Accueil()
    {
        super("AZUL UTBM - Accueil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        // D'abord on demande le nombre de joueurs
        setContentPane(new PanNbJoueurs());
        resize();
    }

    private void loadPanPseudos(int nbJoueurs)
    {
        PanPseudosJoueurs panPseudosJoueurs = new PanPseudosJoueurs(nbJoueurs);
        setContentPane(panPseudosJoueurs);
        resize();
    }

    public JButton getBtnValider()
    {
        return btnValider;
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

    private class PanNbJoueurs extends JPanel
    {

        public PanNbJoueurs()
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
        public PanPseudosJoueurs(int nbJoueurs)
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
}
