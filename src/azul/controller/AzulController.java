package azul.controller;

import azul.model.*;
import azul.view.*;

import javax.swing.*;

public class AzulController
{
    private Game game;
    private View_Accueil view_accueil;
    private View_Game view_game;

    public AzulController()
    {
        game = new Game();

        view_accueil = new View_Accueil();

        SetListeners_Accueil();

        view_accueil.setVisible(true);
    }

    private void SetListeners_Accueil()
    {
        view_accueil.getBtnDeuxJoueurs().addActionListener(e -> StartGame(2));
        view_accueil.getBtnTroisJoueurs().addActionListener(e -> StartGame(3));
        view_accueil.getBtnQuatreJoueurs().addActionListener(e -> StartGame(4));
    }

    private void StartGame(int nbJoueurs)
    {
        view_accueil.dispose();

        view_game = new View_Game(nbJoueurs);

        view_game.setVisible(true);
    }
}
