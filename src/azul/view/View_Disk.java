package azul.view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class View_Disk extends JPanel
{
    private final int btnSize = 50;

    private final ArrayList<JButton> btnsTiles = new ArrayList<>();

    public ArrayList<JButton> GetBtnsTiles()
    {
        return btnsTiles;
    }

    public View_Disk()
    {
        setLayout(new GridLayout(2, 2));

        for(int i = 0; i < 4; ++i)
        {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(btnSize, btnSize));
            btnsTiles.add(btn);
            add(btn);
        }
    }
}
