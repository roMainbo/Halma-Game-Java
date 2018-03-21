package Exceptions;

import Extensions.Couleur;

import java.io.Serializable;

/**
 * Gère les erreurs de paramètrage
 * @author Romain Bourré
 */
public class MauvaisParametreException extends Exception {

    /**
     * Message d'erreur à afficher
     */
    private String message;

    /**
     * Constructeur définissant des précisions sur l'erreur
     * @param msg message de précision
     */
    public MauvaisParametreException(String msg) {
        message = msg;
    }

    /**
     * Message d'erreur
     * @return message d'erreur
     */
    public String toString() {
        String display = "";
        display += "\n* Erreur de paramètre";
        if(!message.equals("")) {
            display += " : " + message;
        }
        display += " *\n";

        return Couleur.ROUGE(display);
    }

}
