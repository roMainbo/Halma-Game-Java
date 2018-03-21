package Controllers;

import Exceptions.CaseNonVideException;
import Exceptions.CaseVideException;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Représente un pion simple
 * @author Romain Bourré
 */
public class PionDouble extends Pion implements Serializable {

    public PionDouble(Joueur j) {
        super(j);
    }

    /**
     * Analyse les coups possible en cumulant les déplacements d'un pion simple et ceux d'un pion double
     * @param mouvSimple si égal à false, ne prend que les sauts en compte sinon tous les coups
     * @return liste des coups possible pour un pion simple, et un pion double
     * @throws CaseVideException renvoie une exception si la case est vide
     */
    @Override
    public LinkedList<Coup> getCoupsPossible(boolean mouvSimple) throws CaseVideException {

        LinkedList<Coup> coups = super.getCoupsPossible(mouvSimple);

        if(getaCase() == null) {
            throw  new CaseVideException();
        }
        else {

            Joueur joueur = getJoueur();
            Partie partie = getJoueur().getPartie();
            Case thisCase = getaCase();

            int analyseX = 0;
            int analyseY = 0;
            int finAnalyseX = 0;
            int finAnalyseY = 0;
            int directionX;
            int directionY;

            // ON DÉTERMINE LES COORDONNÉES DE DÉPART DE L'ANALYSE
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

            // ON DÉTERMINE LES COORDONNÉES DE FIN D'ANALYSE
            if ((analyseX + 2) > (partie.getPlateau().getTailleX() - 1))
                finAnalyseX += partie.getPlateau().getTailleX();
            else finAnalyseX += analyseX + 2;
            if ((analyseY + 2) > (partie.getPlateau().getTailleY() - 1))
                finAnalyseY += partie.getPlateau().getTailleY();
            else finAnalyseY += analyseY + 2;

            // ON COMMENCE L'ANALYSE
            for (int y = analyseY; y <= finAnalyseY; y++) {
                for (int x = analyseX; x <= finAnalyseX; x++) {

                    Case aCase = partie.getPlateau().getCase(x, y);

                    directionX = aCase.getCoordX() - thisCase.getCoordX();
                    directionY = aCase.getCoordY() - thisCase.getCoordY();

                    // DÉPLACEMENT SIMPLE D'UN PION DOUBLE
                    // ON VÉRIFIE QUE LA CASE EST LIBRE ET QU'ELLE NE CORRESPOND PAS À LA CASE D'ORIGINE
                    if (aCase.estLibre() && !aCase.equals(getaCase())) {
                        if ((aCase = partie.getPlateau().coordValide(x + directionX, y + directionY)) != null) {
                            // ON REGARDE SI LA CASE SUIVANTE EST LIBRE
                            if (aCase.estLibre()) {
                                if (mouvSimple)
                                    coups.add(new Coup(joueur, partie, thisCase.getCoordX(), thisCase.getCoordY(), aCase.getCoordX(), aCase.getCoordY()));
                            }
                        }
                    }
                    // DÉPLACEMENT PAR SAUT D'UN PION SIMPLE
                    // SI LA CASE N'EST PAS VIDE
                    else if (!aCase.estLibre() && !aCase.equals(getaCase())) {
                        if ((aCase = partie.getPlateau().coordValide(aCase.getCoordX() + directionX, aCase.getCoordY() + directionY)) != null) {
                            // ON REGARDE SI LA SUIVANTE N'EST PAS LIBRE NON PLUS
                            if (!aCase.estLibre()) {
                                if ((aCase = partie.getPlateau().coordValide(aCase.getCoordX() + directionX, aCase.getCoordY() + directionY)) != null) {
                                    // SI CELLE QUI EST ENCORE APRÈS EST LIBRE ALORS ON ENREGISTRE LE COUP
                                    if (aCase.estLibre()) {
                                        coups.add(new Coup(joueur, partie, thisCase.getCoordX(), thisCase.getCoordY(), aCase.getCoordX(), aCase.getCoordY()));
                                    }

                                }

                            }

                        }

                    }

                }
            }

            return coups;
        }
    }


    /**
     * Affichage du type de pion et du joueur associé
     * @return affichage du pion
     */
    public String toString() {
        return "D" + super.getJoueur();
    }

}
