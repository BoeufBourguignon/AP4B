package azul.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View_Game extends JFrame
{
    private ArrayList<View_Gameboard> listGameboards;
    private ArrayList<View_Disk> listDisks;

    public View_Game(int nbJoueurs)
    {
        super("AZUL UTBM");

        setLayout(new BorderLayout());

        JPanel panDisks = new JPanel();
        listDisks = new ArrayList<>();
        for(int i = 0; i < 2 * nbJoueurs + 1; ++i)
        {
            View_Disk d = new View_Disk();
            listDisks.add(d);
            panDisks.add(d);
        }
        add(panDisks, BorderLayout.CENTER);

        JPanel panGameboards = new JPanel();
        JPanel panGameboardsPrevious = new JPanel(); panGameboardsPrevious.setBackground(Color.YELLOW);
        JPanel panGameboardsNext = new JPanel(); panGameboardsNext.setBackground(Color.CYAN);
        JPanel panGameboardsActual = new JPanel();
        panGameboards.add(panGameboardsPrevious);
        panGameboards.add(panGameboardsActual);
        panGameboards.add(panGameboardsNext);
        //panGameboards.setLayout(new GridLayout(1,3));
        listGameboards = new ArrayList<>();
        for(int i = 0; i < nbJoueurs; ++i)
        {
            View_Gameboard gb = new View_Gameboard();
            listGameboards.add(gb);
        }
        listGameboards.get(0).SetReduced(true);
        panGameboardsPrevious.add(listGameboards.get(0).Draw_Gameboard());
        panGameboardsActual.add(listGameboards.get(1).Draw_Gameboard());
        panGameboardsNext.add(listGameboards.get(0).Draw_Gameboard());
        add(panGameboards, BorderLayout.SOUTH);


        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
