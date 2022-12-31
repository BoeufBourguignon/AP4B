package azul.controller;

import azul.model.*;
import azul.view.*;

import javax.swing.*;
import java.util.ArrayList;

public class AzulController
{
    private final Game game;
    private JFrame window;
    private View_Game.PanGameboard currentGameboard;
    private ArrayList<Tile> selectedTiles = null;

    public AzulController()
    {
        game = new Game();

        window = new View_Accueil();

        ((View_Accueil) window).getBtnValider().addActionListener(e -> startGame());

        window.setVisible(true);
    }

    private void startGame()
    {
        // On vérifie les pseudos
        if(((View_Accueil) window).verifyPseudos())
        {
            window.dispose();

            // On crée les classes "Player" et on les ajoute au Game
            ((View_Accueil) window).getPseudos().forEach(pseudo -> game.addPlayer(new Player(pseudo)));

            // On crée la fenêtre de jeu
            window = new View_Game(game);
            View_Game V_Game = ((View_Game) window);

            initGame(); // Crée les disks et les gameboards // Le jeu peut commencer

            // On met les évènements sur les boutons des tuiles des disques
            V_Game.getListPanDisks().forEach(panDisk ->
                    panDisk.getListBtnTiles().forEach(btnTile ->
                            btnTile.addActionListener(e -> chooseTiles(panDisk, btnTile.getTile().getType())
                            )
                    )
            );

            // On met les évènements sur les boutons des gamboards
            V_Game.getListPanGameboards().forEach(panGb ->
                    panGb.getListRowsBtns().forEach(btn     ->
                            btn.addActionListener(e -> stockTiles(panGb, btn.getRow())
                            )
                    )

            );

            // On indique le premier joueur
            currentGameboard = V_Game.getPlayerGameboard(game.getFirstPlayer());

            // Et on l'autorise à jouer
            V_Game.setDisksEnabled(true);

            //currentPlayerIndex = (currentPlayerIndex + 1) % nbPlayer;

            // Events de view_game

            window.setVisible(true);
        }
    }

    private void initGame()
    {
        try
        {
            game.initGame();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(window,
                    ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
        ((View_Game) window).initGame();
    }

    private void chooseTiles(View_Game.PanDisk panDisk, TileType type)
    {
        if(selectedTiles == null)
        {
            selectedTiles = panDisk.getDisk().find(type);
            panDisk.removeTileType(type);
            window.validate();

            // Le joueur doit ensuite choisir où les placer, donc on débloque son plateau
            // (et au cas ou on bloque tous les autres)
            ((View_Game) window).setGameboardsEnabled(false);
            currentGameboard.setGameboardEnabled(true);
            // et on bloque les disques
            ((View_Game) window).setDisksEnabled(false);
        }
    }

    private void stockTiles(View_Game.PanGameboard gb, int row)
    {
        if(selectedTiles.size() > 0)
        {
            // On stock les tuiles selectionnées
            gb.getGameboard().fillStockline(row, selectedTiles);
            // On n'a plus besoin de la liste de tuiles sélectionnées
            selectedTiles = null;
            // On recharge la vue du stock du gameboard
            gb.drawStock();
            window.validate();

            // Fin du tour de ce joueur, on le fera dans une fonction "nextPlayer()"
        }
    }
}
