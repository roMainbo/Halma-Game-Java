package Exceptions;

import Extensions.Couleur;

/**
 * Gère les erreurs pour les sélections de pions n'appartenant pas au joueur en cours
 * @author Romain Bourré
 */
public class PionPasAuJoueurEcxeption extends Exception {

    /**
     * Message d'erreur
     * @return message d'erreur
     */
    public String toString() {
        return Couleur.ROUGE("\n* Vous essayez de déplacer un pion qui ne vous appartient pas ! *\n");
    }

}
