package Views;

/**
 * Classe gérant les entrées/affichages lié au plateau
 * @author Romain Bourré
 */
public abstract class Plateau {

    /**
     * Affiche le plateau du jeu
     * @param p plateau à afficher
     * @return affichage du plateau
     */
    public static String affichagePlateau(Controllers.Plateau p) {

        String display = "";
        String ligne = "";
        String enteteLigne = "";

        // Construction d'une ligne
        ligne += "\n    ";
        for(int x = 0; x < p.getTailleX(); x++) {
            ligne += "---- ";
        }
        ligne += "\n";

        display += ligne;
        for(int y = p.getTailleY()-1; y >= 0; y--) {

            // En-tête de ligne
            enteteLigne = "" + (y + 1);
            if(enteteLigne.length() == 1) enteteLigne = "0" + enteteLigne;
            display += enteteLigne + " | ";

            for(int x = 0; x < p.getTailleX(); x++) {
                if(!p.getCase(x+1,y+1).estLibre()) {
                    display += p.getCase(x+1, y+1);
                }
                else {
                    display += "  ";
                }
                display += " | ";
            }

            display += ligne;

        }

        // En-tête de colonne
        display += "    ";
        for(int x = 0; x < p.getTailleX(); x++) {
            if(x < 9) display += " 0"; else display += " ";
            display += (x + 1) + "  ";

        }

        return display;

    }

}
