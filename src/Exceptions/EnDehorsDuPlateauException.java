package Exceptions;

import Extensions.Couleur;

/**
 * Gère les erreurs pour les sélections de cases en dehors du plateau
 * @author Romain Bourré
 */
public class EnDehorsDuPlateauException extends Exception {

    /**
     * Message d'erreur
     * @return message d'erreur
     */
    public String toString() {
        return Couleur.ROUGE("\n* Les coordonnées saisie ne sont pas sur le plateau ! *\n");
    }

}
