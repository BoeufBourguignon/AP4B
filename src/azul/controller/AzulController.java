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
                // On crée les classes "Player"
                ((View_Accueil) window).getPseudos().forEach(pseudo -> game.addPlayer(new Player(pseudo)));

                // On crée la fenêtre de jeu
                window = new View_Game(game);
                View_Game V_Game = ((View_Game) window);

                initGame();

                // Events de view_game

                window.setVisible(true);
            }
        });

        window.setVisible(true);
    }

    public void initGame()
    {
        game.initGame();
        ((View_Game) window).initGame();
    }
}
