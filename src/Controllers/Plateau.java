package Controllers;

import java.io.Serializable;

/**
 * Classe représentant un plateau de jeu
 * @author Romain Bourré
 */
public class Plateau implements Serializable {

    private int tailleX;
    private int tailleY;
    private Case cases[][];

    /**
     * Initialisation d'un plateau de jeu vide
     * @param X nombre de case sur l'axe horizontal
     * @param Y nombre de case sur l'axe vertical
     */
    public Plateau(int X, int Y) {

        if(X > 0 && Y > 0) {
            tailleX = X;
            tailleY = Y;

            cases = new Case[X][Y];

            // Création des cases du plateau
            for(int y = 1; y <= Y; y++) {
                for(int x = 1; x <= X; x++) {
                    cases[x-1][y-1] = new Case(x, y);
                }
            }
        }

    }

    /**
     * @return nombre de case de l'axe horizontal
     */
    public int getTailleX() {
        return tailleX;
    }

    /**
     * @return nombre de case de l'axe vertical
     */
    public int getTailleY() {
        return tailleY;
    }

    /**
     * Retourne à partir de coordonnée la case du plateau correspondant
     * @param X coordonnée de l'axe horizontal
     * @param Y coordonnée de l'axe vertical
     * @return case correspondante
     */
    public Case getCase(int X, int Y) {
        return cases[X-1][Y-1];
    }

    /**
     * Contrôle si des coordonnée corresponde bien à une case du plateau
     * @param x position horizontale de la case
     * @param y position verticale de la case
     * @return la case concerné si les coordonnées sont valides, null si non
     */
    public Case coordValide(int x, int y) {
        x--;
        y--;
        if(x < 0 || x > (tailleX-1)) return null;
        if(y < 0 || y > (tailleY-1)) return null;
        return getCase(x+1, y+1);
    }

    /**
     * Affiche le plateau en mode console, avec en-tête de ligne et de colonne
     * @return String plateau en mode console
     */
    public String toString() {
        return Views.Plateau.affichagePlateau(this);
    }

}
