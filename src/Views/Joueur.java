package Views;

import java.util.Scanner;

/**
 * Classe gérant les entrées/affichages lié aux joueurs
 * @author Romain Bourré
 */
public class Joueur {

    /**
     * Demande le nom du joueur à l'utilisateur
     * @return nom du joueur
     */
    public static String getNomJoueur() {
        Scanner s = new Scanner(System.in);
        System.out.print("Veuillez saisir le nom du joueur " + (Controllers.Joueur.getNombreJoueur()+1) + " : ");
        return s.nextLine();
    }

}
