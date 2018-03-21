package Controllers;

import Exceptions.CaseVideException;
import java.io.Serializable;

/**
 * Classe représentant une case d'un plateau
 * @author Romain Bourré
 */
public class Case implements Serializable {

    private int coordX;
    private int coordY;
    private Pion pion;

    /**
     * Création d'une case de plateau
     * @param X position sur l'axe horizontal
     * @param Y position sur l'axe vertical
     */
    public Case(int X, int Y) {
        if(X > 0 && Y > 0) {
            coordX = X;
            coordY = Y;
            pion = null;
        }
    }

    /**
     * Retourne la position sur l'axe horizontal d'une case
     * @return position horizontal
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * Retourne la position sur l'axe vertical d'une case
     * @return position vertical
     */
    public int getCoordY() {
        return coordY;
    }

    /**
     * Test si la case est libre
     * @return true|false
     */
    public boolean estLibre() {
        return (pion == null);
    }

    /**
     * Renvoie le pion posé sur la case
     * @return Pion pion
     * @throws CaseVideException renvoie une exception si la case est vide
     */
    public Pion getPion() throws CaseVideException {
        if(pion != null) {
            return pion;
        }
        else {
            throw new CaseVideException();
        }
    }

    /**
     * Place un pion dans la case de façon brut
     * @param p pion à placer
     */
    public void setPionBrut(Pion p) {
            this.pion = p;
    }

    /**
     * Place un pion en adaptant les références de chacun
     * @param p pion à placer
     */
    public void poserPion(Pion p) {
        if(estLibre()) {
            setPionBrut(p);
            p.setaCase(this);
        }
    }

    /**
     * Contrôle si une case est situé à côté
     * @param c case à controller
     * @return true si à coté, false sinon
     */
    public boolean estACote(Case c) {
        int differenceX = c.coordX - coordX;
        int differenceY = c.coordY - coordY;

        if( ( differenceX == 1 || differenceX == -1 || differenceX == 0 ) && ( differenceY == 1 || differenceY == -1 || differenceY == 0 ) ) return true;

        return false;
    }

    /**
     * Enlève le pion de la case en adaptant les références de chacun
     */
    public void enleverPion() {
        pion.unsetPionBrut();
        pion = null;
    }

    /**
     * Enlève un pion de la case, sans intervenir sur ce dernier
     */
    public void unsetPionBrut() {
        pion = null;
    }

    public int getDistance(Case c) {
        if(c != null) {
            int distanceX = Math.abs(c.coordX - this.coordX);
            int distanceY = Math.abs(c.coordY - this.coordY);
            if(distanceX > distanceY) {
                return distanceX;
            }
            else {
                return distanceY;
            }
        }
        return -1;
    }

    /**
     * Affichage du pion
     * @return représentation du pion posé sur la case s'il existe
     */
    public String toString() {
        return "" + pion;
    }

}
