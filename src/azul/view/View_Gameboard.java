package azul.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class View_Gameboard
{
    private int btnSize = 50;

    public JPanel DrawWall()
    {
        JPanel wall = new JPanel();
        wall.setLayout(new GridLayout(5, 5));
        for(int i = 0; i < 5; ++i)
        {
            for(int j = 0; j < 5; ++j)
            {
                // Instancier le bouton
                JPanel btn = new JPanel();
                btn.setPreferredSize(new Dimension(btnSize, btnSize));
                wall.add(btn);
                // Colorier le bouton
                switch ((5 + (i - j)) % 5)
                {
                    case 0 -> btn.setBackground(Color.BLUE);
                    case 1 -> btn.setBackground(Color.YELLOW);
                    case 2 -> btn.setBackground(Color.RED);
                    case 3 -> btn.setBackground(Color.BLACK);
                    case 4 -> btn.setBackground(Color.WHITE);
                }
            }
        }
        return wall;
    }

    public JPanel DrawStock()
    {
        JPanel stock = new JPanel();
        stock.setLayout(new GridLayout(5, 1));
        for(int i = 0; i < 5; ++i)
        {
            for(int j = 0; j < 5; ++j)
            {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(btnSize, btnSize));
                stock.add(btn);
                if(j < 4 - i)
                {
                    btn.setOpaque(false);
                    btn.setContentAreaFilled(false);
                    btn.setBorderPainted(false);
                }
            }
        }
        return stock;
    }

    public View_Gameboard()
    {

    }

    public JPanel Draw_Gameboard()
    {
        JPanel p = new JPanel();
        p.add(DrawStock());
        p.add(DrawWall());
        return p;
    }

    public void SetReduced(boolean isReduced)
    {
        if(isReduced)
            btnSize = 25;
        else
            btnSize = 50;
    }

    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        View_Gameboard gb = new View_Gameboard();
        f.setContentPane(gb.Draw_Gameboard());

        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
