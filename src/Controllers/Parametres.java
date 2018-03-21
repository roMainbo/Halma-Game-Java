package Controllers;

import Exceptions.MauvaisParametreException;
import java.io.Serializable;

/**
 * Classe de paramètre d'un partie
 * @author Romain Bourré
 */
public class Parametres implements Serializable {

    private final int MAX_NOMBRE_JOUEUR = 4;
    private final int MAX_NOMBRE_IA = 4;
    private final int MIN_JOUEUR_TOTAL = 2;
    private final int MAX_JOUEUR_TOTAL = 4;

    private int taillePlateauX;
    private int taillePlateauY;
    private int nombreJoueur;
    private int nombreIA;

    /**
     * Créer des paramètres de partie à partir des paramètres principaux
     * Atrribut le nombre de joueur minimum
     * @param p paramètre principaux
     */
    public Parametres(ParametresHalma p) {
        taillePlateauX = p.getTailleX();
        taillePlateauY = p.getTailleY();
        nombreJoueur = 1;
        nombreIA = 1;
    }

    /**
     * Créer des paramètres de partie à partir des paramètres principaux
     * Le nombre de joueur sont précisé en paramètre
     * @param p paramètres principaux
     * @param nbrIA nombre d'IA pour la partie
     * @param nombreJoueur nombre de joueur pour la partie
     * @throws MauvaisParametreException retourne une exception en case de paramètres incorrects
     */
    public Parametres(ParametresHalma p, int nbrIA, int nombreJoueur) throws MauvaisParametreException {
        taillePlateauX = p.getTailleX();
        taillePlateauY = p.getTailleY();
        if(nbrIA + nombreJoueur <= MAX_JOUEUR_TOTAL && nbrIA + nombreJoueur >= MIN_JOUEUR_TOTAL) {
            setNbrIA(nbrIA);
            setNbrJoueur(nombreJoueur);
        }
        else {
            throw new MauvaisParametreException("vous pouvez jouer de " + MIN_JOUEUR_TOTAL + " à " + MAX_NOMBRE_JOUEUR + " joueurs");
        }
    }

    /**
     * Retourne la taille horizontal voulu du plateau
     * @return taille horizontal du plateau
     */
    public int getTailleX() {
        return taillePlateauX;
    }

    /**
     * Retourne la taille verticale voulu du plateau
     * @return taille verticale du plateau
     */
    public int getTailleY() {
        return taillePlateauY;
    }

    /**
     * Retourne le nombre de joueur voulu pour la partie
     * @return nombre de joueur
     */
    public int getNbrJoueur() {
        return nombreJoueur;
    }

    public int getNbrIA() {
        return nombreIA;
    }

    public int getTotalJoueur() {
        return nombreIA + nombreJoueur;
    }

    private void setNbrIA(int n) throws MauvaisParametreException {
        if(n >= 0 && n <= MAX_NOMBRE_IA) {
            nombreIA = n;
        }
        else {
            nombreIA = 0;
            throw new MauvaisParametreException("vous pouvez jouer de 0 à " + MAX_NOMBRE_IA + " IA");
        }
    }

    /**
     * fixe le nombre de joueur de la partie
     * @param n nombre de joueur voulu
     * @throws MauvaisParametreException retourne une exception si le paramètre voulu est incorrect
     */
    private void setNbrJoueur(int n) throws MauvaisParametreException {
        if(n >= 0 && n <= MAX_NOMBRE_JOUEUR) {
            nombreJoueur = n;
        }
        else {
            nombreJoueur = 0;
            throw new MauvaisParametreException("vous pouvez jouer de 0 à " + MAX_NOMBRE_JOUEUR + " joueurs humains");
        }
    }
}
