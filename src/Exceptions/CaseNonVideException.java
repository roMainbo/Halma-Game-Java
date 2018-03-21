package Exceptions;

import Extensions.Couleur;

/**
 * Classe gérant les erreurs de cases non vides
 * @author Romain Bourré
 */
public class CaseNonVideException extends Exception {

    /**
     * Message d'erreur
     * @return message d'erreur
     */
    public String toString() {
        return Couleur.ROUGE("\n* la case où déplacer le pion n'est pas vide *\n");
    }

}
