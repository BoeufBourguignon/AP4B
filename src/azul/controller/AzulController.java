package azul.controller;

import azul.model.*;
import azul.view.*;

import javax.swing.*;
import java.util.*;

public class AzulController
{
    private final Game game;
    private JFrame window;
    private Map.Entry<Player, View_Game.PanGameboard> currentPlayerAndGameboard;
    private ArrayList<Tile> selectedTiles = null;

    public AzulController()
    {
        game = new Game();

        window = new View_Accueil();

        ((View_Accueil) window).getBtnValider().addActionListener(e -> askFirstPlayer());

        window.setVisible(true);
    }

    private void askFirstPlayer()
    {
        if(((View_Accueil) window).verifyPseudos())
        {
            ((View_Accueil) window).loadPanAskPremierJoueur();

            ((View_Accueil) window).getListeBtnsPremierJoueur()
                    .forEach(btn -> btn.addActionListener(e -> startGame(btn.getText())));
        }
    }

    private void startGame(String pseudoFirstPlayer)
    {
        // On vérifie les pseudos
        if(((View_Accueil) window).verifyPseudos())
        {
            window.dispose();

            // On crée les classes "Player" et on les ajoute au Game
            ((View_Accueil) window).getPseudos().forEach(pseudo -> {
                Player player = new Player(pseudo);
                game.addPlayer(player);
                if(pseudo.equals(pseudoFirstPlayer))
                    game.setFirstPlayer(player);
            });

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
            V_Game.getListPanGameboards().forEach((player, panGb) ->
                    panGb.getListRowsBtns().forEach(btn     ->
                            btn.addActionListener(e -> stockTiles(panGb, btn.getRow())
                            )
                    )
            );

            // On met l'event sur le bouton de malus
            V_Game.getListPanGameboards().forEach((player, panGb) ->
                    panGb.getBtnMalus().addActionListener(e -> stockTiles(panGb, 5))
            );

            // On indique le premier joueur
            currentPlayerAndGameboard = V_Game.getPlayerGameboard(game.getFirstPlayer());
            ((View_Game) window).getListPanGameboards().forEach((p, gb) -> gb.setPlaying(false));
            currentPlayerAndGameboard.getValue().setPlaying(true);
            currentPlayerAndGameboard.getKey().setFirstPlayer(false);

            // Et on l'autorise à jouer
            setChoosingTilesPhase();

            window.setVisible(true);
        }
    }

    private void initGame()
    {
        try
        {
            game.initGame();
            ((View_Game) window).initGame();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(window,
                    ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void nextPlayer()
    {
        int currentIndex = game.getPlayers().indexOf(currentPlayerAndGameboard.getKey());
        currentIndex = (currentIndex + 1) % game.getPlayers().size();

        currentPlayerAndGameboard = ((View_Game) window).getPlayerGameboard(game.getPlayers().get(currentIndex));
        ((View_Game) window).getListPanGameboards().forEach((p, gb) -> gb.setPlaying(false));
        currentPlayerAndGameboard.getValue().setPlaying(true);
        currentPlayerAndGameboard.getKey().setFirstPlayer(false);
    }

    private void setChoosingTilesPhase()
    {
        // Desactive tous les gameboards
        ((View_Game) window).setGameboardsEnabled(false);
        // Active les disks
        ((View_Game) window).setDisksEnabled(true);
    }

    private void setStockingTilePhase()
    {
        // Desactive les disks
        ((View_Game) window).setDisksEnabled(false);
        // Active le gameboard du joueur
        currentPlayerAndGameboard.getValue().setGameboardEnabled(true);
    }

    private void chooseTiles(View_Game.PanDisk panDisk, TileType type)
    {
        if(selectedTiles == null)
        {
            selectedTiles = game.playerPickTile(currentPlayerAndGameboard.getKey(), panDisk.getDisk(), type);


            panDisk.pickTiles(panDisk, type);

            ((View_Game) window).getCenterPanDisk().drawDisk();
            ((View_Game) window).getCenterPanDisk().getListBtnTiles().forEach(btnTile ->
                    btnTile.addActionListener(e ->
                            chooseTiles(((View_Game)window).getCenterPanDisk(), btnTile.getTile().getType())
                    )
            );

            window.validate();

            // Le joueur doit ensuite choisir où les placer, donc on débloque son plateau
            setStockingTilePhase();
        }
    }

    private void stockTiles(View_Game.PanGameboard gb, int row)
    {
        if(selectedTiles != null)
        {
            boolean isStocked = gb.getGameboard().fillStockline(row, selectedTiles);

            if(isStocked)
            {
                // On n'a plus besoin de la liste de tuiles sélectionnées
                selectedTiles = null;
                // On recharge la vue du stock du gameboard et du malus
                gb.drawStock();
                gb.drawMalus();
                window.validate();

                // Fin du tour de ce joueur, on le fera dans une fonction "nextPlayer()"
                nextPlayer();
                setChoosingTilesPhase();
            }
        }
    }
}
