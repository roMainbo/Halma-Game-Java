package Views;

/**
 * Classe gérant les entrées/affichages lié à la partie
 * @author Romain Bourré
 */
public class Partie {

    /**
     * Affiche le sous-menu en cours de partie
     * @return affiche le sous-menu
     */
    public static String dispSousMenu() {

        String display = "";

        display += "1. Enregistrer\n";
        display += "2. Quitter\n";
        display += "0. Retour\n";
        display += "\n";

        return display;

    }

}
