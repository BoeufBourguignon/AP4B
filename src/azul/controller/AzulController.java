package azul.controller;

import azul.model.*;
import azul.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AzulController
{
    private final Game game;
    private JFrame window;
    private Map.Entry<Player, View_Game.PanGameboard> currentPlayerAndGameboard;
    private ArrayList<Tile> selectedTiles = null;

    /**
     * Instancie un objet Game
     * Crée et affiche la fenêtre d'accueil
     * Ajoute un event listener sur le bouton "valider" lorsque les pseudos des joueurs ont été renseignés
     */
    public AzulController()
    {
        game = new Game();

        window = new View_Accueil();

        ((View_Accueil) window).getBtnValider().addActionListener(e -> askFirstPlayer());

        window.setVisible(true);
    }

    /**
     * Vérifie la validité des pseudos entrés
     * Charge la fenêtre d'accueil demandant le premier joueur
     */
    private void askFirstPlayer()
    {
        if(((View_Accueil) window).verifyPseudos())
        {
            ((View_Accueil) window).loadPanAskPremierJoueur();

            ((View_Accueil) window).getListeBtnsPremierJoueur()
                    .forEach(btn -> btn.addActionListener(e -> startGame(btn.getText())));
        }
    }

    /**
     * Instancie les objets nécessaires au démarrage de la partie (disques, joueurs)
     * Crée la fenêtre du jeu
     * Charge les events sur les disks
     * @param pseudoFirstPlayer Pseudo du premier joueur
     */
    private void startGame(String pseudoFirstPlayer)
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

        putEventsOnDisks();

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

    /**
     * Initialise le modèle
     * Initalise la vue du jeu, qui est liée au modèle
     */
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

    /**
     * Met à jour le joueur courrant avec le prochain joueur
     */
    private void nextPlayer()
    {
        int currentIndex = game.getPlayers().indexOf(currentPlayerAndGameboard.getKey());
        currentIndex = (currentIndex + 1) % game.getPlayers().size();

        currentPlayerAndGameboard = ((View_Game) window).getPlayerGameboard(game.getPlayers().get(currentIndex));
        ((View_Game) window).getListPanGameboards().forEach((p, gb) -> gb.setPlaying(false));
        currentPlayerAndGameboard.getValue().setPlaying(true);
        currentPlayerAndGameboard.getKey().setFirstPlayer(false);
    }

    /**
     * Desactive les gameboards et active les disques
     */
    private void setChoosingTilesPhase()
    {
        // Desactive tous les gameboards
        ((View_Game) window).setGameboardsEnabled(false);
        // Active les disks
        ((View_Game) window).setDisksEnabled(true);
    }

    /**
     * Desactive les disques et active les gameboards
     */
    private void setStockingTilePhase()
    {
        // Desactive les disks
        ((View_Game) window).setDisksEnabled(false);
        // Active le gameboard du joueur
        currentPlayerAndGameboard.getValue().setGameboardEnabled(true);
    }

    /**
     * Permet au joueur courrant de choisir un type de tuiles sur un disk
     * Le type de la tuile est déterminé selon quel bouton de tuile a été cliqué
     * @param panDisk Vue du disque où le type de tuile a été choisi
     * @param type Type de tuile choisi
     */
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

    /**
     * Permet de stocker des tuiles sélectionnées sur une ligne du stock d'un gameboard
     * @param gb Vue du gameboard du joueur courrant
     * @param row Ligne de stock choisie
     */
    private void stockTiles(View_Game.PanGameboard gb, int row)
    {
        if(selectedTiles != null)
        {
            boolean isStocked = gb.getGameboard().fillStockline(row, selectedTiles);

            // Si les tuiles sélectionnées ne peuvent pas être stockées sur la ligne choisie, on ne fait rien
            if(isStocked)
            {
                // On n'a plus besoin de la liste de tuiles sélectionnées
                selectedTiles = null;
                // On recharge la vue du stock du gameboard et du malus
                gb.drawStock();
                gb.drawMalus();
                window.validate();

                boolean are_empty = verifierFinManche(); //disks + centre sont vides

                if(are_empty){
                    DecorerMur();
                    calculerScores();

                    MalusToDiscard();
                    retirerTile1();// enlève le "1" du gameboard où elle se trouve et la met dans le Center pour la manche suivante
                    //Actualisation des gameboards
                    updateView_Gameboards();


                    if( !Verifier_fin().isEmpty() ){ // Si quelqu'un a fini


                        //créer une fenêtre pour dire le gagnant
                        JFrame annonce_gagnant = new JFrame();// on créé une fenêtre pour display le gagnant
                        annonce_gagnant.setLayout(new BorderLayout());
                        annonce_gagnant.setTitle("Le gagnant !");
                        //Définit sa taille : 400 pixels de large et 200 pixels de haut
                        annonce_gagnant.setSize(400, 200);
                        JLabel label = new JLabel("Le gagnant est:" + AnnoncerGagnant().getNickname() );
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                        annonce_gagnant.add(label, BorderLayout.CENTER);

                        annonce_gagnant.setLocationRelativeTo(null);
                        annonce_gagnant.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        annonce_gagnant.setVisible(true);


                    }else{

                        assezDeTuiles(); // s'il n'y a pas plus assez de tiles dans le deck, on remplit celui-ci avec la discard
                        fillDisks(); // remplissage des disks
                        // Réaffichage des disks
                        updateView_Disks();

                        //Le prochain joueur est celui qui a la tuile first
                        currentPlayerAndGameboard = ((View_Game) window).getPlayerGameboard(game.getFirstPlayer());
                        ((View_Game) window).getListPanGameboards().forEach((p, gbs) -> gbs.setPlaying(false));
                        currentPlayerAndGameboard.getValue().setPlaying(true);
                        currentPlayerAndGameboard.getKey().setFirstPlayer(false);
                    }
                }
                else {
                    //Le prochaine joueur est celui qui vient dans l'ordre
                    nextPlayer();
                }

                setChoosingTilesPhase();
            }
        }
    }

    /**
     * Décore le mur de tous les joueurs
     */
    private void DecorerMur(){

        List<Player> joueurs =  game.getPlayers(); //on récup les joueurs
        List<Tile> unused_tiles;

        Discard discard_content = game.getDiscard();

        for (Player joueur : joueurs) {

            Gameboard jeu = joueur.getGameboard(); // on récup le gamebord du joueur
            unused_tiles = jeu.wallTilling(); // on remplit le tableau du joueur

            discard_content.getDiscard().addAll(unused_tiles); // on ajoute toutes les tiles non utilisée à la discard
        }
    }

    /**
     * vérifie si un on est dans une situation de victoire
     * @return la liste des joueurs présentant une situation gagnante
     */
    private List<Player> Verifier_fin(){

        List<Player> joueurs =  game.getPlayers(); //on récup les joueurs
        List<Player> gagnants = new ArrayList<>();

        for(int i = 0;i<joueurs.size();i++){

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gameboard de chaque joueur
            for(int ligne = 0;ligne<5;ligne++) {

                if (jeu.checkLignePleine(ligne)) {//on regarde si une ligne de 5 tiles est pleine

                    if (!gagnants.contains(joueurs.get(i))) { //si le joueur n'est pas encore dans la liste des gagnants
                        gagnants.add(joueurs.get(i)); //on récup le/les joueur/s qui a/ont gagné/s
                    }
                }
            }
        }
        return gagnants; // liste des gagnants
    }

    /**
     * calcule le score pour chaque joueur
     */
    private void calculerScores(){

        List<Player> joueurs =  game.getPlayers();

        for (Player joueur : joueurs) {

            Gameboard jeu = joueur.getGameboard(); // on récup le gameboard de chaque joueur
            jeu.computeScore(); // on calcule le score de chaque joueur
        }
    }

    /**
     * vérifie si les Disks et le center sont vides
     * @return True (les 2 sont vides) sinon false
     */
    private boolean verifierFinManche() {

        ArrayList<Disk> disks = game.getDisks();
        boolean tout_les_disks_vides = false;
        int nb_disk_vides = 0;

        for (Disk disk : disks) { //on regarde pour chaque disk s'il est vide

            if (disk.getTiles().size() == 0) { // disk vide ?
                nb_disk_vides++;
            }
        }

        if (nb_disk_vides == disks.size() && game.getCenter().getTiles().size() == 0) { // si tous les disks sont vides
            tout_les_disks_vides = true;
        }

        return tout_les_disks_vides;
    }


    /**
     * recharge le Deck  avec le contenu de la Discard s'il il n'y a plus assez de Tiles dans celui-ci pour remplir les Disks
     */
    private void assezDeTuiles(){

        List<Tile> deck = game.getDeck().getDeck(); // on récup le contenu du Deck du jeu

        while( deck.size() < (game.getDisks().size()*4) ){ //s'il n'y a pas assez de tuiles pour remplir tous les disks du jeu

            Discard discard = game.getDiscard(); // on récup la discard
            Tile tile_recuperee = discard.drawTile(); // on pioche une tile
            deck.add(tile_recuperee); // on l'ajoute au deck
        }
    }

    /**
     * envoie les Tiles contenues dans le malus de chaque joueur vers la Discard
     */
    private void MalusToDiscard(){

        List<Player> joueurs =  game.getPlayers();

        for (Player joueur : joueurs) {

            Gameboard jeu = joueur.getGameboard(); // on récup le gameboard de chaque joueur
            Discard discard = game.getDiscard(); // on récup la discard
            discard.getDiscard().addAll(jeu.clearMalus()); // on ajoute toutes les tiles du malus à la discard ( sans prendre la tile "1" si elle y est)
        }
    }

    /**
     * Détermine un unique gagnant
     * @return Le gagnant unique
     */
    private Player AnnoncerGagnant() {
        List<Player> Gagnant_s = Verifier_fin();
        int numG = 0;
        if (Gagnant_s.size() > 1) {
            List<Player> joueurs = game.getPlayers();
            int lignemax = 0, ligne_complete = 0;
            for (int i = 0; i < Gagnant_s.size(); i++) {
                Gameboard gameboardGG = joueurs.get(i).getGameboard(); //recuperation gameboard gagnants
                ligne_complete = gameboardGG.getCompteur_lignes_completes();
                if (lignemax < ligne_complete) {
                    lignemax = ligne_complete;
                    numG = i;
                }
            }
        }
        return Gagnant_s.get(numG);
    }

    /**
     * retire la Tile "first" du Gameboard où elle se trouve et est positionné dans le Center
     */
    private void retirerTile1(){

        List<Player> joueurs =  game.getPlayers();

        for (Player joueur : joueurs) {

            Gameboard jeu = joueur.getGameboard(); // on récup le gameboard de chaque joueur
            if (jeu.removeTile_First()) { //si on a trouvé la Tile "1" dans le gameboard
                game.getCenter().setFirstTile();
            }
        }
    }

    /**
     * remplissage des Disks pour la manche suivante
     */
    private void fillDisks(){

        for(Disk disk : game.getDisks()){

            int number_of_tiles = 0;

            while(number_of_tiles < 4){ //tant qu'il n'y a pas 4 tiles dans le disk on en pioche une du deck
                Tile tile_to_add = game.getDeck().drawTile();
                disk.add(tile_to_add); // on ajoute la tile
                number_of_tiles++;
            }
        }
    }

    /**
     * Met les évènement sur les tuiles des disques
     */
    private void putEventsOnDisks()
    {
        View_Game V_Game = ((View_Game) window);
        // On met les évènements sur les boutons des tuiles des disques
        V_Game.getListPanDisks().forEach(panDisk ->
                panDisk.getListBtnTiles().forEach(btnTile ->
                        btnTile.addActionListener(e -> chooseTiles(panDisk, btnTile.getTile().getType())
                        )
                )
        );
    }

    /**
     * Met la vue du gameboard a jour
     */
    private void updateView_Gameboards()
    {
        ((View_Game) window).getListPanGameboards().forEach((player, gbs) -> {
            //On met à jour les murs dans la vue
            gbs.drawWall();
            gbs.drawStock();
            //On met à jour la zone malus
            gbs.drawMalus();
            //On met à jour le score
            gbs.drawScore();
        });
        //On met à jour le centre (avec la tuile 1)
        ((View_Game) window).getCenterPanDisk().drawDisk();
        window.validate();
    }

    /**
     * Met la vue du disk à jour
     */
    private void updateView_Disks()
    {
        ((View_Game) window).getListPanDisks().forEach(View_Game.PanDisk::drawDisk);
        ((View_Game) window).getCenterPanDisk().drawDisk();
        window.validate();
        //Reaffectation des events sur les disks
        putEventsOnDisks();
    }
}
