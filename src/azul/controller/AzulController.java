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
            // On crée les classes "Player"
            ArrayList<Player> players = new ArrayList<>();
            ((View_Accueil) window).getPseudos().forEach(pseudo -> players.add(new Player(pseudo)));

            // On crée la fenêtre de jeu, avec tous les joueurs
            window = new View_Game(players);

            // Events de view_game

            window.setVisible(true);
        });

        window.setVisible(true);
    }
}
