  //fonction avec rajout de ce qu'il manquait 
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

                // Fin du tour de ce joueur
                // Vérifions si centre et disk vides
                 /*
                Si plus de tuiles
                   // On fait le wall tilling de tous les joueurs, on récupère les tuiles en trop et on les met dans la défausse
                       // On compte les points
                    //(inclus dans le calcul du score -> )On compte les points du malus
                     //on met les tuiles du malus dans la défausse (sauf tuile "First")
                   // On vérifie si c'est la fin
                    //Si c'est pas la fin
                        //Nouvelle manche
                            //On remplit le deck avec la discard tant que y'a besoin
                            //On init les disks et le centre
                                //Draw 4 tuiles sur chaque disk
                                //Remet une tuile "First" au centre
                    Si c'est la fin, on annonce le gagnant dans une nouvelle fenêtre
                 */
                // Ajout commence ici
                boolean are_empty = verifierFinManche(); //disks + centre sont vides

                if(are_empty){
                    DecorerMur();
                    calculerScores();
                    MalusToDiscard();
                }

                if( !Verifier_fin().isEmpty() ){ 


                    //créer une fenêtre pour dire le gagnant
                    JFrame annonce_gagnant = new JFrame();// on créé une fenêtre pour display le gagnant
                    annonce_gagnant.setTitle("Le gagnant !");
                    //Définit sa taille : 400 pixels de large et 200 pixels de haut
                    annonce_gagnant.setSize(400, 200);
                    JLabel label = new JLabel("Le gagnant est:" + AnnoncerGagnant() );
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    annonce_gagnant.add(label, BorderLayout.CENTER);

                    annonce_gagnant.setLocationRelativeTo(null);
                    annonce_gagnant.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    annonce_gagnant.setVisible(true);


                }else{

                    assezDeTuiles(); // s'il n'y a pas plus assez de tiles dans le deck, on remplit celui-ci avec la discard
                    retirerTile1();// enlève le "1" du gameboard où elle se trouve et la met dans le Center pour la manche suivante
                    fillDisks(); // remplissage des disks
                }
                //finit ici

                nextPlayer();
                setChoosingTilesPhase();
            }
        }
    }
    //----fonctions Guillaume

    /**
     * Décore le mur de tous les joueurs
     */
    private void DecorerMur(){

        List<Player> joueurs =  game.getPlayers(); //on récup les joueurs
        List<Tile> unused_tiles;

        Discard discard_content = game.getDiscard();

        for(int i = 0;i<joueurs.size();i++){

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gamebord du joueur
            unused_tiles = jeu.WallTilling(); // on remplit le tableau du joueur

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

        for(int i = 0;i<joueurs.size();i++) {

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gameboard de chaque joueur
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

        for (int i = 0; i < disks.size(); i++) { //on regarde pour chaque disk s'il est vide

            if (disks.get(i).getTiles().size() == 0) { // disk vide ?
                nb_disk_vides++;
            }
        }

        if (nb_disk_vides == disks.size()) { // si tous les disks sont vides
            tout_les_disks_vides = true;
        }

        return tout_les_disks_vides;
    }


    /**
     * recharge le Deck  avec le contenu de la Discard s'il il n'y a plus assez de Tiles dans celui-ci pour remplir les Disks 
     */
    private void assezDeTuiles(){

        List<Tile> deck = game.getDeck().getDeck(); // on récup le contenu du Deck du jeu

        while( deck.size() < (game.getNbDisk()*4) ){ //s'il n'y a pas assez de tuiles pour remplir tous les disks du jeu

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

        for(int i = 0;i<joueurs.size();i++) {

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gameboard de chaque joueur
            Discard discard = game.getDiscard(); // on récup la discard
            discard.getDiscard().addAll(jeu.clearMalus()); // on ajoute toutes les tiles du malus à la discard ( sans prendre la tile "1" si elle y est)
        }
    }

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

        for(int i = 0;i<joueurs.size();i++) {

            Gameboard jeu = joueurs.get(i).getGameboard(); // on récup le gameboard de chaque joueur
            if(jeu.removeTile_First()){ //si on a trouvé la Tile "1" dans le gameboard
                game.getCenter().setFirstTile();
            }
        }
    }

    /**
     * remplissage des Disks pour la manche suivante
     */
    private void fillDisks(){

        List<Disk> liste_disks = game.getDisks(); // on récup les disks du jeu

        for(int i =0; i< game.getNbDisk();i++){

            int number_of_tiles = 0;

            while(number_of_tiles < 4){ //tant qu'il n'y a pas 4 tiles dans le disk on en pioche une du deck
                Tile tile_to_add = game.getDeck().drawTile();
                liste_disks.get(i).add(tile_to_add); // on ajoute la tile
                number_of_tiles++;
            }
        }
    }
}
