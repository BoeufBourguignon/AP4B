package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    //----fonctions Guillaume
    public void DecorerMur(){

        List<Player> joueurs =  game.getPlayers(); //on récup les joueurs

        for(int i = 0;i<4;i++){

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gamebord du joueur
            jeu.WallTilling(); // on remplit le tableau du joueur
            jeu.clearGrids(); // on vides les lignes du stock qui ont été utilisées


        }


    }

    public List<Player> Verifier_fin(){

        List<Player> joueurs =  game.getPlayers(); //on récup les joueurs
        List<Player> gagnants = new ArrayList<>() ;

        for(int i = 0;i<4;i++){

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gameboard de chaque joueur
            for(int ligne = 0;ligne<5;ligne++) {

                if (jeu.checkLignePleine(ligne)) {//on regarde si une ligne de 5 tiles est pleine

                    if (!gagnants.contains(joueurs.get(i))) { //si le joueur n'est pas encore dans la liste des gagnants
                        gagnants.add(joueurs.get(i)); //on récup le/les joueur/s qui a/ont gagné/s
                    }
                }
            }
        }
        return gagnants;
    }

    public void calculerScores(){

        List<Player> joueurs =  game.getPlayers();

        for(int i = 0;i<4;i++) {

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gameboard de chaque joueur
            jeu.computeScore(); // on calcule le score de chaque joueur
        }
    }
}
