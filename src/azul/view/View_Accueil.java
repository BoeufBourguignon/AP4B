package azul.view;

import javax.swing.*;
import java.awt.*;

public class View_Accueil extends JFrame
{
    private final JButton btnDeuxJoueurs;
    private final JButton btnTroisJoueurs;
    private final JButton btnQuatreJoueurs;

    public JButton getBtnDeuxJoueurs()
    {
        return btnDeuxJoueurs;
    }

    public JButton getBtnTroisJoueurs()
    {
        return btnTroisJoueurs;
    }

    public JButton getBtnQuatreJoueurs()
    {
        return btnQuatreJoueurs;
    }

    public View_Accueil()
    {
        super("AZUL UTBM - Accueil");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        btnDeuxJoueurs = new JButton("Deux joueurs");
        btnTroisJoueurs = new JButton("Trois joueurs");
        btnQuatreJoueurs = new JButton("Quatre joueurs");

        JPanel panLabelTitre = new JPanel();
        panLabelTitre.add(new JLabel("Veuillez choisir le nombre de joueurs"));
        add(panLabelTitre);

        JPanel panBoutonsJoueurs = new JPanel();
        panBoutonsJoueurs.add(btnDeuxJoueurs);
        panBoutonsJoueurs.add(btnTroisJoueurs);
        panBoutonsJoueurs.add(btnQuatreJoueurs);
        add(panBoutonsJoueurs);

        pack();
        setSize(new Dimension((int)(getWidth() * 1.5), (int)(getHeight() * 1.5)));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
