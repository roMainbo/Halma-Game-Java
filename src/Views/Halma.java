package Views;

import Exceptions.MauvaiseSaisieException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe gérant les entrées/affichages lié à la classe principale
 * @author Romain Bourré
 */
public class Halma {

    /**
     * Template. Rajoute un titre à tout affichage demandé, adapté à la taille du jeu
     * @param titre titre à afficher
     * @param contenu contenu en dessous du titre
     * @param width taille du titre
     */
    public static void template(String titre, String contenu, int width) {

        String display = "";
        String ligne = "";
        String sousligne = "";

        display += Extensions.Outils.clear();

        ligne += "****";
        for(int x = 0; x < width; x++) {
            ligne += "*****";
        }

        for(int x = 0; x < (ligne.length() - titre.length() - 2)/2; x++) {
            sousligne += " ";
        }


        display += ligne + "\n";
        display += "*" + sousligne + titre.toUpperCase() + sousligne + "*\n";
        display += ligne + "\n";
        display += "\n";

        display += contenu;

        System.out.print(display);

    }

    /**
     * Affichage du menu principal
     * @return menu principal
     */
    public static String menuPrincipal() {

        String display = "";

        display += "1. Nouvelle partie\n";
        display += "2. Charger partie\n";
        display += "3. Paramètres\n";
        display += "0. Quitter\n";

        display += "\nChoix : ";

        return display;
    }

    /**
     * Affichage du menu de paramètre
     * @return menu de paramètre
     */
    public static String menuParametres() {
        String display = "";

        display += "1. Taille du plateau\n";
        display += "2. Couleur\n";
        display += "0. Retour\n";

        display += "\nChoix : ";
        return display;
    }

    /**
     * Demande la saisie d'un entier
     * @return valeur saisie
     * @throws MauvaiseSaisieException renvoie une exception si la valeur saisie n'est pas un entier
     */
    public static int inputInt() throws MauvaiseSaisieException {
        try {
            Scanner s = new Scanner(System.in);
            return s.nextInt();
        }
        catch(InputMismatchException e) {
            throw new MauvaiseSaisieException();
        }
    }

}
