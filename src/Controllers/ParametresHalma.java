package Controllers;

import Exceptions.MauvaisParametreException;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Classe représentant les paramètres principaux du jeu
 * @author Romain Bourré
 */
public class ParametresHalma implements Serializable {

    private static ParametresHalma instance = null;

    private final int MIN_TAILLE_PLATEAU_X = 10;
    private final int MAX_TAILLE_PLATEAU_X = 99;
    private final int MIN_TAILLE_PLATEAU_Y = 10;
    private final int MAX_TAILLE_PLATEAU_Y = 99;
    private final boolean COLOR = true;

    private int taillePlateauX;
    private int taillePlateauY;
    private boolean color;

    /**
     * On créer des paramètres avec les valeurs par défault
     */
    private ParametresHalma() {

        taillePlateauX = MIN_TAILLE_PLATEAU_X;
        taillePlateauY = MIN_TAILLE_PLATEAU_Y;
        color = COLOR;

    }

    /**
     * Retourne l'instance unique des paramètres principaux
     * @return paramètres principaux
     */
    public static ParametresHalma getInstance() {
        if(ParametresHalma.instance == null) {
            instance = new ParametresHalma();
        }
        return instance;
    }

    /**
     * Retourne le paramètre de la taille du plateau horizontale
     * @return taille horizontale du plateau
     */
    public int getTailleX() {
        return taillePlateauX;
    }

    /**
     * Retourne le paramètre de la taille du plateau verticale
     * @return taille verticale du plateau
     */
    public int getTailleY() {
        return taillePlateauY;
    }

    /**
     * Retourne la couleur représentant le joueur
     * @return couleur du joueur
     */
    public boolean getColor() {
        return color;
    }

    /**
     * Fixe la taille horizontale du plateau en paramètre
     * @param x taille horizontale du plateau
     * @throws MauvaisParametreException renvoie une exception en cas de paramètre incorrect
     */
    public void setTailleX(int x) throws MauvaisParametreException {
        if(x >= MIN_TAILLE_PLATEAU_X && x <= MAX_TAILLE_PLATEAU_X) {
            taillePlateauX = x;
        }
        else {
            throw new MauvaisParametreException("la taille du plateau doit être supérieur ou égal à " + MIN_TAILLE_PLATEAU_X + " et inférieur ou égal à " + MAX_TAILLE_PLATEAU_X);
        }
    }

    /**
     * Fixe la taille verticale du plateau en paramètre
     * @param y taille verticale du plateau
     * @throws MauvaisParametreException renvoie une exception en case de paramètre incorrect
     */
    public void setTailleY(int y) throws MauvaisParametreException {
        if(y >= MIN_TAILLE_PLATEAU_Y && y <= MAX_TAILLE_PLATEAU_Y) {
            taillePlateauY = y;
        }
        else {
            throw new MauvaisParametreException("la taille du plateau doit être supérieur ou égal à " + MIN_TAILLE_PLATEAU_Y + " et inférieur ou égal à " + MAX_TAILLE_PLATEAU_Y);
        }
    }

    /**
     * Fixe le paramètre de l'affichage de couleur
     * @param active true pour activer la couleur, false sinon
     */
    public void setColor(boolean active) {
        color = active;
    }

    /**
     * Permet le chargement du singleton à partir d'un fichier
     * @return l'instance créée
     * @throws ObjectStreamException
     */
    private Object readResolve() throws ObjectStreamException{
        instance = this;
        return instance;
    }

}
