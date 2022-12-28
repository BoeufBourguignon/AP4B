import java.util.*;

public class Gameboard {

    //-----Attributs-----


    private int score;        //  le score du joueur
    private ArrayList<ArrayList<Tile>>  wall;  // le mur du joueur
    private ArrayList<Tile> malus;    // zone malus
    private ArrayList<ArrayList<Tile>> stock;    // stock du joueur

    //mur de "référence", pour savoir comment doivent être disposé les tiles sur le mur final
    private ArrayList<ArrayList<TileType>>  reference_wall;


    //-----Méthodes-----

    /**
     * Constructeur
     */
    public Gameboard() { // fonctionne!

        this.score = 0;

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

        Tile t_stock = new Tile(TileType.Empty); //On créé une Tile de type "vide" pour pouvoir remplir le wall au début

        ArrayList<Tile> ligne1_stock = new ArrayList<>(List.of(t_stock));
        stock.add(ligne1_stock);
        ArrayList<Tile> ligne2_stock = new ArrayList<>(List.of(t_stock,t_stock));
        stock.add(ligne2_stock);
        ArrayList<Tile> ligne3_stock = new ArrayList<>(List.of(t_stock,t_stock,t_stock));
        stock.add(ligne3_stock);
        ArrayList<Tile> ligne4_stock = new ArrayList<>(List.of(t_stock,t_stock,t_stock,t_stock));
        stock.add(ligne4_stock);
        ArrayList<Tile> ligne5_stock = new ArrayList<>(List.of(t_stock,t_stock,t_stock,t_stock,t_stock));
        stock.add(ligne5_stock);

        //-----


        //-----création du "tableau" malus-----
        this.malus = new ArrayList<>();  //tableau de 7 cases

        for (int i=0;i<7;i++){
            Tile t_malus = new Tile(TileType.Empty); //On créé une Tile de type "vide" pour pouvoir remplir le malus au début
            malus.add(t_malus);
        }
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
    public boolean fillStockline(int stockLine, List<Tile> tiles) { // 0 à 4 //fonctionne!

        TileType type = tiles.get(0).getType(); //récupération du type des tile de la liste "tiles"
        int longueur = tiles.size(); //longueur de la liste de tile

        //check si on rentre avec la tile "first"

        if(type == TileType.First){
            malus.remove(0);
            malus.add(0,tiles.get(0)); // on ajoute la tile "First" au début du malus
            return false;
        }

        //check disponibilité de la ligne stockLine:
        if (wall.get(stockLine).contains(type) || (stock.get(stockLine).get(0).getType() != type && stock.get(stockLine).get(0).getType() != TileType.Empty )   ) { //vérifie si le mur contient déjà une tile du même type sur la ligne "stockline" OU si la ligne du stock  contient déjà  un autre type

           return false; //échec de l'ajout dans la ligne souhaité
        }

        //cas où la liste est trop longue par rapport à la ligne dans le stock
        int compteur_indice_malus = 1; // on commence à 1 pour liasser la 1ère case vide pour a tile "First"
        while (longueur > stockLine + 1) {

            malus.remove(compteur_indice_malus);
            malus.add(0,tiles.get(0));
            tiles.remove(0);
            longueur -=1;
        }

        //on rajotue le retse dans le stock
        int compteur_ligne_stock = 0;
        while(longueur != 0){

            stock.get(stockLine).remove(compteur_ligne_stock); // on retire la tile vide
            stock.get(stockLine).add(0,tiles.get(0)); // on rempli avec la nouvelle tile
            tiles.remove(0); // on retire l'élément que l'on vient d'ajouter
            longueur -=1;
            compteur_ligne_stock++;
        }

        return true; // ajout dans le stock réussi
    }

    /**
     * Fonction qui vide les lignes qui ont été utilisées pour remplir le Wall
     * Les lignes utilisée se verront replies de Tiles de type "Empty"
     */
    public void clearGrids() { //fonctionne!

        int taille_ligne = 1;
        Tile tile_remplacement = new Tile(TileType.Empty);

        for(int i=0 ; i<5; i++){ // pour chaque ligne du stock on regarde si elle a été utilisée pour la déco du mur

            if (stock.get(i).get(0).getType() == TileType.Empty ) {

                for(int j = 0;j < taille_ligne;j++){

                    stock.get(i).remove(j);
                    stock.get(i).add(j,tile_remplacement);
                }
            }
            taille_ligne++; //la ligne suivante contient 1 élément en +
        }
    }

    /**
     * Fonction qui s'occupe de remplir le Wall à partir des lignes remplies de Tile ayant un type différent de "Empty"
     */
    public void WallTilling(){  //fonctionne

        int taille_ligne = 1; // la première ligne contient qu'un élément

        for(int i = 0 ;i < 5;i++){

            if (stock.get(i).toArray().length >0 ) {

                int compteur_elements_ligne = 0; // index de l'élément sur la ligne actuelle

                for(int j=0 ; j<taille_ligne ;j++){

                    if (stock.get(i).get(j).getType() == TileType.Empty) { //si l'élément de la ligne est une tile "vide"
                        break;

                    }else {

                        compteur_elements_ligne += 1;

                        if (compteur_elements_ligne == taille_ligne) { // si la ligne n'est pas vide => procédure de placement

                            Tile tile_vide = new Tile(TileType.Empty);
                            Tile tile_a_placer = stock.get(i).get(0); // on récupère la première Tile de la ligne

                            stock.get(i).remove(0); //on retire la première tile du stock
                            stock.get(i).add(0,tile_vide); // on mets une tile vide à la place

                            TileType type = tile_a_placer.getType(); // on récupère son type

                            int indice = reference_wall.get(i).indexOf(type); // on récupère, sur le wall, l'indice au quel se situe le type de la tile à placer
                            wall.get(i).set(indice, tile_a_placer); //ajoute la tile à cet indice dans le Wall
                        }
                    }
                }
            }
            taille_ligne+=1; // la ligne suivante contient 1 élément en +
        }
    }
}
	