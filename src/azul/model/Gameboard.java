package azul.model;

import java.util.*;

public class Gameboard {

    //-----Attributs-----

    private int score;        //  le score du joueur
    private ArrayList<ArrayList<Tile>>  wall;  // le mur du joueur
    private ArrayList<Tile> malus;    // zone malus
    private ArrayList<ArrayList<Tile>> stock;    // stock du joueur

    //mur de "référence", pour savoir comment doivent être disposé les tiles sur le mur final
    private ArrayList<ArrayList<TileType>>  reference_wall;

    private int compteur_lignes_completes;

    private ArrayList<Integer> malus_values;

    //-----Méthodes-----

    /**
     * Constructeur
     */
    public Gameboard() { // fonctionne!

        this.score = 0;

        this.malus_values = new ArrayList<>(List.of(-1,-1,-2,-2,-2,-3,-3)); //liste de référence de valeur pour le malus

        //-----création de la "matrice" wall-----
        this.wall = new ArrayList<>();

        for (int i=0;i<5;i++){

            Tile t_wall = new Tile(TileType.Empty); //On créé une Tile de type "vide" pour pouvoir remplir le wall au début
            ArrayList<Tile> ligneWall = new ArrayList<>(List.of(t_wall,t_wall,t_wall,t_wall,t_wall)); // on créé une liste de 5 tiles vides
            wall.add(ligneWall);
        }
        //-----


        //-----création de la "matrice" stock-----
        this.stock = new ArrayList<>();

        stock.add(new ArrayList<>());
        stock.add(new ArrayList<>());
        stock.add(new ArrayList<>());
        stock.add(new ArrayList<>());
        stock.add(new ArrayList<>());

//        Tile t_stock = new Tile(TileType.Empty); //On créé une Tile de type "vide" pour pouvoir remplir le wall au début

//        ArrayList<Tile> ligne1_stock = new ArrayList<>(List.of(t_stock));
//        stock.add(ligne1_stock);
//        ArrayList<Tile> ligne2_stock = new ArrayList<>(List.of(t_stock,t_stock));
//        stock.add(ligne2_stock);
//        ArrayList<Tile> ligne3_stock = new ArrayList<>(List.of(t_stock,t_stock,t_stock));
//        stock.add(ligne3_stock);
//        ArrayList<Tile> ligne4_stock = new ArrayList<>(List.of(t_stock,t_stock,t_stock,t_stock));
//        stock.add(ligne4_stock);
//        ArrayList<Tile> ligne5_stock = new ArrayList<>(List.of(t_stock,t_stock,t_stock,t_stock,t_stock));
//        stock.add(ligne5_stock);

        //-----


        //-----création du "tableau" malus-----
        this.malus = new ArrayList<>();  //tableau de 7 cases

//        for (int i=0;i<7;i++){
//            Tile t_malus = new Tile(TileType.Empty); //On créé une Tile de type "vide" pour pouvoir remplir le malus au début
//            malus.add(t_malus);
//        }
        //-----

        //-----création de la "matrice" de reference_wall-----
        this.reference_wall = new ArrayList<>();

        ArrayList<TileType> ligne1 = new ArrayList<>(List.of(TileType.TCS,TileType.AP,TileType.N,TileType.IS,TileType.HS));
        reference_wall.add(ligne1);
        ArrayList<TileType> ligne2 = new ArrayList<>(List.of(TileType.HS,TileType.TCS,TileType.AP,TileType.N,TileType.IS));
        reference_wall.add(ligne2);
        ArrayList<TileType> ligne3 = new ArrayList<>(List.of(TileType.IS,TileType.HS,TileType.TCS,TileType.AP,TileType.N));
        reference_wall.add(ligne3);
        ArrayList<TileType> ligne4 = new ArrayList<>(List.of(TileType.N,TileType.IS,TileType.HS,TileType.TCS,TileType.AP));
        reference_wall.add(ligne4);
        ArrayList<TileType> ligne5 = new ArrayList<>(List.of(TileType.AP,TileType.N,TileType.IS,TileType.HS,TileType.TCS));
        reference_wall.add(ligne5);
        //-----
    }

    /**
     * Accesseur du code du Gameboard
     *
     * @return la valeur du score associé
     */
    public int getScore() {  // fonctionne!
        return score;
    }

    /**
     * Accesseur du Wall du Gameboard
     *
     * @return le Wall (tableau de Tiles)
     */
    public ArrayList<ArrayList<Tile>>getWall() { // fonctionne!
        return wall;
    }

    public int getCompteur_lignes_completes() {
        return compteur_lignes_completes;
    }

    /**
     * Accesseur du Malus du Gameboard
     *
     * @return le Malus ( liste de Tiles)
     */
    public ArrayList<Tile> getMalus() { // fonctionne!
        return malus;
    }

    /**
     * Accesseur du stock du Gameboard
     *
     * @return le stock ( liste de Tiles)
     */
    public ArrayList<ArrayList<Tile>> getStock() { // fonctionne!
        return stock;
    }

    /**
     * Accesseur du tableau de Référence pour le Wall
     *
     * @return le tableau de référence (tableau d'éléments de l'énumération TileType, correspondant
     * au type de la tuile à placer à chaque endroit dans le Wall)
     */
    public ArrayList<ArrayList<TileType>> getReference_wall() { // fonctionne!
        return reference_wall;
    }


    /**
     *
     * @param stockLine : l'index de la ligne du stock dans laquelle on veut insérer "tiles" ( 0 à 4 en indices)
     * @param tiles : la liste de Tile que l'on veut insérer dans le stock
     * @return : "False" si échec de placement à cette ligne, sinon "True"
     */
    public boolean fillStockline(int stockLine, ArrayList<Tile> tiles)
    { // 0 à 4 //fonctionne!

        TileType type = tiles.get(0).getType();
        boolean canBeStocked = true;

        if(tiles.size() > 0)
        {
            // Cherche la tile First et la met en zone malus
            int i = 0;
            while (i < tiles.size() - 1 && tiles.get(i).getType() != TileType.First)
            {
                i++;
            }
            if (tiles.get(i).getType() == TileType.First)
            {
                // Si tuile premier joueur, on la met en zone malus
                malus.add(0, tiles.get(i));
                tiles.remove(tiles.get(i));
            }

            if(tiles.size() != 0)
            {
                if(stockLine == 5)
                {
                    // Stock dans la zone malus
                    // !!! Normalement ce qui ne peut pas être socké dans la zone malus, va dans la défausse !!!
                    malus.addAll(tiles);
                }
                else
                {
                    //check disponibilité de la ligne stockLine:
                    // Ligne dispo si :
                    //  wall pas déjà rempli avec cette couleur ET vide OU (reste de la place ET tuiles du même type)

                    // Determinons la position de la tile sur le wall, avec son type et la ligne


                    if(stock.get(stockLine).size() == 0 || (stock.get(stockLine).size() < stockLine + 1 && stock.get(stockLine).get(0).getType() == type))
                    {
                        // La ligne peut être stockée
                        // Il faut stocker ce que l'on peut
                        // Puis envoyer le reste en malus
                        tiles.forEach(tile -> {
                            // Si il reste de la place dans le stock
                            if(stock.get(stockLine).size() <= stockLine)
                            {
                                stock.get(stockLine).add(tile);
                            }
                            else
                            {
                                malus.add(tile);
                            }
                        });

                    }
                    else
                    {
                        canBeStocked = false;
                    }
                }
            }
        }

        return canBeStocked; // ajout dans le stock réussi
    }

    /**
     * Fonction qui vide les lignes qui ont été utilisées pour remplir le Wall
     * Les lignes utilisée se verront replies de Tiles de type "Empty"
     */
    public ArrayList<Tile> clearGrids() { //fonctionne!

        int taille_ligne = 1;
        Tile tile_remplacement = new Tile(TileType.Empty);
        ArrayList<Tile> tile_pas_utilisees = new ArrayList<>();

        for(int i=0 ; i<5; i++){ // pour chaque ligne du stock on regarde si elle a été utilisée pour la déco du mur

            if (stock.get(i).get(0).getType() == TileType.Empty ) {

                for(int j = 0;j < taille_ligne;j++){

                    stock.get(i).add(j,tile_remplacement);
                    if(stock.get(i).get(j).getType() != TileType.Empty) { // si la tile qu'on enlève n'est pas de type "empty" -> peut-être le cas au début du jeu
                        tile_pas_utilisees.add(stock.get(i).get(j)); // on ajoute la tile dans la liste à injecter dans la discard à la fin
                    }
                    stock.get(i).remove(j); // tile 3empty" ou pas, on la supprime du stock
                }
            }
            taille_ligne++; //la ligne suivante contient 1 élément en +
        }

        return tile_pas_utilisees; // on retourne la liste des tiles qu'on a pas utilisées dans les lignes pleines du stock
    }

    /**
     * Fonction qui s'occupe de remplir le Wall à partir des lignes remplies de Tile ayant un type différent de "Empty"
     *
     * @return tuiles à jeter
     */
    public ArrayList<Tile> wallTilling(){  //fonctionne

//        int taille_ligne = 1; // la première ligne contient qu'un élément
//
//        for(int i = 0 ;i < 5;i++){
//
//            if (stock.get(i).toArray().length >0 ) {
//
//                int compteur_elements_ligne = 0; // index de l'élément sur la ligne actuelle
//
//                for(int j=0 ; j<taille_ligne ;j++){
//
//                    if (stock.get(i).get(j).getType() == TileType.Empty) { //si l'élément de la ligne est une tile "vide"
//                        break;
//
//                    }else {
//
//                        compteur_elements_ligne += 1;
//
//                        if (compteur_elements_ligne == taille_ligne) { // si la ligne n'est pas vide => procédure de placement
//
//                            Tile tile_vide = new Tile(TileType.Empty);
//                            Tile tile_a_placer = stock.get(i).get(0); // on récupère la première Tile de la ligne
//
//                            stock.get(i).remove(0); //on retire la première tile du stock
//                            stock.get(i).add(0,tile_vide); // on mets une tile vide à la place
//
//                            TileType type = tile_a_placer.getType(); // on récupère son type
//
//                            int indice = reference_wall.get(i).indexOf(type); // on récupère, sur le wall, l'indice au quel se situe le type de la tile à placer
//                            wall.get(i).set(indice, tile_a_placer); //ajoute la tile à cet indice dans le Wall
//                        }
//                    }
//                }
//            }
//            taille_ligne+=1; // la ligne suivante contient 1 élément en +
//        }
        ArrayList<Tile> unused = new ArrayList<>();

        for(int i = 0; i < 5; ++i)
        {
            if(stock.get(i).size() == i + 1) // La ligne de stock est pleine
            {
                // On met la première tuile dans le wall
                Tile tile_a_placer = stock.get(i).get(0);
                int indice = reference_wall.get(i).indexOf(tile_a_placer.getType());
                wall.get(i).set(indice, tile_a_placer);

                // On vide la ligne du stock et on la met dans la liste des tuiles pas utilisées
                stock.get(i).remove(0);
                unused.addAll(stock.get(i));
                stock.get(i).clear();
            }
        }

        return unused;
        //return clearGrids(); // la liste des tiles pas utilisées dans la déco du mur
    }

    public ArrayList<Tile> calculerMalus(Tile tileFirst)
    {
        ArrayList<Tile> tuilesAJeter;
        switch(malus.size())
        {
            case 7:
                score -= 3;
            case 6:
                score -= 3;
            case 5:
                score -= 2;
            case 4:
                score -= 2;
            case 3:
                score -= 2;
            case 2:
                score -= 1;
            case 1:
                score -= 1;
        }
        if(score < 0) score = 0;

        malus.remove(tileFirst);

        tuilesAJeter = new ArrayList<>(malus);
        malus.clear();

        return tuilesAJeter;
    }

    /**
     * check si une ligne du wall est pleine ou pas
     * @param ligne la ligne à examiner
     * @return true ( remplie) ou false
     */
    public boolean checkLignePleine(int ligne){ // check si une ligne est pleine


        if(        (this.wall.get(ligne).get(0).getType() != TileType.Empty)
                && (this.wall.get(ligne).get(1).getType() != TileType.Empty)
                && (this.wall.get(ligne).get(2).getType() != TileType.Empty)
                && (this.wall.get(ligne).get(3).getType() != TileType.Empty)
                && (this.wall.get(ligne).get(4).getType() != TileType.Empty)
        ){

            return true;
        }

        return false;
    }

    /**
     * check si une colonne du wall est pleine ou pas
     * @param colonne la colonne à examiner
     * @return true ( remplie) ou false
     */
    public boolean checkColonnePleine(int colonne){ // check si une ligne est pleine

        int compteur_colonne = 1;
        for(int i = 0 ;i < 5;i++){

            if( (this.wall.get(i).get(colonne).getType() != TileType.Empty) ) {

                compteur_colonne++;
            }
            else{
                //colonne ++;
                compteur_colonne = 1;
            }

        }

        //colonne pleine ?
        return compteur_colonne == 5;

    }

    /**
     * calcule la valeur d'une ligne non complète
     * @param ligne la ligne à éxaminer
     * @return la valeur de cette ligne
     */
    public int valeur_ligne(int ligne){

        int valeur = 0;

        for(int i = 0;i<5;i++){

            if(this.wall.get(ligne).get(i).getType() != TileType.Empty){
                valeur+=1;
            }
        }

        if(valeur <5){ //quand 5 éléments sont alignés, c'est la fonction checkLignePleine qui renverra "true" et affectera +5 au score
            return valeur;
        }
        else return 0;
    }

    /**
     * calcule la valeur d'une colonne non complète
     * @param colonne la ligne à éxaminer
     * @return la valeur de cette colonne
     */
    public int valeur_colonne(int colonne){

        int valeur = 0;

        for(int i = 0;i<5;i++){

            if(this.wall.get(i).get(colonne).getType() != TileType.Empty){
                valeur+=1;
            }
        }
        return valeur;
    }

    /**
     * vide le malus mais laisse la Tile "first" si elle est présente
     * @return la liste des éléments à ajouter dans la discard
     */
    public ArrayList<Tile> clearMalus(){

        ArrayList<Tile> elems = new ArrayList<>();
        for(Tile tile : malus){

            if(tile.getType() != TileType.First) {
                elems.add(tile);
            }
        }
        malus.removeAll(elems);

        return elems; // les éléments du malus à mettre dans la discard
    }

    /**
     * retire la tile "first" si elle est présente
     * @return True pour attester que la tile était présente et a été retiré ( = ajouté au center) sinon false
     */
    public boolean removeTile_First(){ //si la tile "1" se trouve dans le gameboard, on le retire et on retourne true pour dire qu'elle a été trouvée

        if(malus.size() != 0 && malus.get(0).getType() == TileType.First){
            malus.remove(0);
            return true;
        }
        return false;
    }

    /**
     * calcul du score du Gameboard
     */
    public void computeScore(){


        for(int ligne = 0;ligne<5;ligne++){
            if(checkLignePleine(ligne)){
                this.compteur_lignes_completes ++;

                if(this.compteur_lignes_completes<2){
                    this.score +=5;
                }else{
                    this.score+=2;
                }
            }
            score +=valeur_ligne(ligne);
        }

        for(int colonne = 0;colonne <5; colonne++){

            if (checkColonnePleine(colonne) ){
                this.score+=7;
            }
            score +=valeur_colonne(colonne);
        }

        //on teste si chaque tile est présente 5fois dans le mur, si oui on ajoute 10 au score, sinon on fait rien
        if( Collections.frequency(this.wall,TileType.TCS) == 5){

            this.score +=10;
        }

        if( Collections.frequency(this.wall,TileType.AP) == 5){

            this.score +=10;
        }

        if( Collections.frequency(this.wall,TileType.N) == 5){

            this.score +=10;
        }

        if( Collections.frequency(this.wall,TileType.IS) == 5){

            this.score +=10;
        }

        if( Collections.frequency(this.wall,TileType.HS) == 5){

            this.score +=10;
        }

        //section malus

        for(int i = 0; i < malus.size();i++){

            if(malus.get(i).getType() != TileType.Empty){
                this.score -= malus_values.get(i);// on retire la valeur associée à la case occupée
            }
        }
    }
}

