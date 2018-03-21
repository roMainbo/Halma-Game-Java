package Controllers;

import Exceptions.CaseNonVideException;
import Exceptions.CaseVideException;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Classe abstraite représentant les pions
 */
public class Pion implements Serializable {

    private Joueur joueur;
    private Case aCase;

    /**
     * Création d'un pion attribuer à un joueur
     * @param j joueur auquel appartient le pion
     */
    public Pion(Joueur j) {
        if(j != null) {
            joueur = j;
        }
    }

    /**
     * Retourne le joueur du pion
     * @return joueur
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Retourne la case sur laquelle est posée le pion
     * @return case
     */
    public Case getaCase() {
        return aCase;
    }

    /**
     * Pose un pion sur une case
     * @param c case
     */
    public void setaCase(Case c) {
        if(c != null) {
            aCase = c;
        }
    }

    /**
     * Pose un pion sur une case
     * @param c case de destination
     */
    public void poserPion(Case c) {
        if(c != null && c.estLibre()) {
            aCase.enleverPion();
            setaCase(c);
            aCase.setPionBrut(this);
        }
    }

    /**
     * Détermine les coups possible d'un pion
     * @param mouvSimple si égal à false, ne prend que les sauts en compte sinon tous les coups
     * @throws CaseVideException renvoie une exception si une case est vide
     * @return liste de coups possible du pion
     */
    public LinkedList<Coup> getCoupsPossible(boolean mouvSimple) throws CaseVideException {

        if(getaCase() == null) {
            throw new CaseVideException();
        }
        else {

            Joueur joueur = getJoueur();
            Partie partie = getJoueur().getPartie();
            Case thisCase = getaCase();
            LinkedList<Coup> coups = new LinkedList();

            int analyseX = 0;
            int analyseY = 0;
            int finAnalyseX = 0;
            int finAnalyseY = 0;
            int directionX;
            int directionY;

            //On determine les coordonnées de départ de l'analyse
            if (getaCase().getCoordX() == 1) {
                analyseX += getaCase().getCoordX();
                finAnalyseX--;
            } else {
                analyseX = getaCase().getCoordX() - 1;
            }

            if (getaCase().getCoordY() == 1) {
                analyseY += getaCase().getCoordY();
                finAnalyseY--;
            } else {
                analyseY = getaCase().getCoordY() - 1;
            }

            //On détermine les coordonnées de fin de l'analyse
            if ((analyseX + 2) > (partie.getPlateau().getTailleX() - 1))
                finAnalyseX += partie.getPlateau().getTailleX();
            else finAnalyseX += analyseX + 2;

            if ((analyseY + 2) > (partie.getPlateau().getTailleY() - 1))
                finAnalyseY += partie.getPlateau().getTailleY();
            else finAnalyseY += analyseY + 2;

            //Départ de l'analyse
            for (int y = analyseY; y <= finAnalyseY; y++) {
                for (int x = analyseX; x <= finAnalyseX; x++) {

                    Case aCase = partie.getPlateau().getCase(x, y);

                    //Mouvement simple
                    //On vérifie que la case est libre et quel ne correspond pas à la case d'origine
                    if (aCase.estLibre() && !aCase.equals(getaCase())) {

                        if (mouvSimple)
                            coups.add(new Coup(joueur, partie, thisCase.getCoordX(), thisCase.getCoordY(), x, y));

                    }
                    //Sinon on regarde la case qui se situe après pour savoir si l'on peut effectuer un saut
                    else if (!aCase.estLibre() && !aCase.equals(getaCase())) {

                        directionX = aCase.getCoordX() - thisCase.getCoordX();
                        directionY = aCase.getCoordY() - thisCase.getCoordY();

                        //On test s'il existe une case avec le déplacement
                        if ((aCase = partie.getPlateau().coordValide(aCase.getCoordX() + directionX, aCase.getCoordY() + directionY)) != null) {

                            //On test si la case est vide pour pouvoir faire le saut
                            if (aCase.estLibre()) {

                                coups.add(new Coup(joueur, partie, thisCase.getCoordX(), thisCase.getCoordY(), aCase.getCoordX(), aCase.getCoordY()));

                            }

                        }

                    }

                }
            }

            return coups;

        }

    }

    /**
     * Déplace un pion
     * @param x coordonnée horizontale de destination
     * @param y coordonnée verticale de destination
     * @throws CaseNonVideException renvoie une exception au cas où la case visé n'est pas vide
     */
    public void deplacer(int x, int y) throws CaseNonVideException {

        Case dest = getJoueur().getPartie().getPlateau().getCase(x, y);

        if(dest.estLibre()) {
            poserPion(dest);
        }
        else {
            throw new CaseNonVideException();
        }
    }

    /**
     * Enlève le pion d'une case en intervenant sur cette dernière
     */
    public void retirerPion() {
        aCase.unsetPionBrut();
        aCase = null;
    }

    /**
     * Met la case associé a null sans intervenir sur cette dernière
     */
    public void unsetPionBrut() {
        aCase = null;
    }

    /**
     * Affichage du type de pion et du joueur associé
     * @return affichage du pion
     */
    public String toString() {
        return "S" + getJoueur();
    }

}
