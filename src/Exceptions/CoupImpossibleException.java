package Exceptions;

import Extensions.Couleur;

/**
 * Gère les erreurs de coups impossibles
 * @author Romain Bourré
 */
public class CoupImpossibleException extends Exception {

    /**
     * Message d'erreur
     * @return message d'erreur
     */
    public String toString() {
        return Couleur.ROUGE("\n* Ce coup est impossible ! *\n");
    }

}
