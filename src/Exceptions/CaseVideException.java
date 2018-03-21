package Exceptions;

import Extensions.Couleur;

/**
 * Classe gérant les erreurs de cases vides
 * @author Romain Bourré
 */
public class CaseVideException extends Exception {

    /**
     * Message d'erreur
     * @return message d'erreur
     */
    public String toString() {
        return Couleur.ROUGE("\n* Vous avez sélectionné une case vide ! *\n");
    }

}
