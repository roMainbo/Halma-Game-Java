package Extensions;

/**
 * Classe d'outils
 * @author Romain Bourré
 */
public class Outils {

    /**
     * Décompose un nombre entier en somme d'entiers consécutifs partant de 1.
     * @param entier entier à décomposer
     * @return dernier entier de la somme
     */
    public static int composeInt(int entier) {

        int i = 0;
        int somme = 0;
        while(somme < entier) {
            i++;
            somme = somme + i;
        }

        return i;
    }

    /**
     * Efface la console
     * @return code ASCII
     */
    public static String clear() {
        return "\033[H\033[2J";
    }

}
