package azul.controller;

import azul.model.*;
import azul.view.*;

import javax.swing.*;
import java.util.ArrayList;

public class AzulController
{
    private Game game;
    private JFrame window;

    public AzulController()
    {
        game = new Game();

        window = new View_Accueil();

        ((View_Accueil) window).getBtnValider().addActionListener(e -> {
            // On vérifie les pseudos
            if(((View_Accueil) window).verifyPseudos())
            {
                window.dispose();

                // On crée les classes "Player" et on les ajoute au Game
                ((View_Accueil) window).getPseudos().forEach(pseudo -> game.addPlayer(new Player(pseudo)));

                // On crée la fenêtre de jeu
                window = new View_Game(game);
                View_Game V_Game = ((View_Game) window);

                try
                {
                    initGame();
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(window,
                            ex.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Events de view_game

                window.setVisible(true);
            }
        });

        window.setVisible(true);
    }

    public void initGame() throws Exception
    {
        game.initGame();
        ((View_Game) window).initGame();
    }
}
