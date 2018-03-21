package Exceptions;

import Extensions.Couleur;

import java.util.InputMismatchException;

/**
 * Gère les erreurs de saisie dans les inputs
 * @author Romain Bourré
 */
public class MauvaiseSaisieException extends InputMismatchException {

    /**
     * Message d'erreur
     * @return message d'erreur
     */
    public String toString() {
        return Couleur.ROUGE("\n* Mauvaise saisie ! *\n");
    }

}
