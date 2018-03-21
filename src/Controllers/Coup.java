package Controllers;

import Exceptions.*;
import Extensions.Couleur;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Classe représentant le coup d'un joueur
 * @author Romain Bourré
 */
public class Coup implements Serializable {

    private Partie partie;
    private Joueur joueur;

    private int srcCoordX;
    private int srcCoordY;
    private int destCoordX;
    private int destCoordY;

    private int force;

    private boolean active;

    /**
     * Créer un nouveau coup à jouer en demandant au joeur de saisir les coordonées
     * @param j joueur associé au coup
     * @param p partie associé au coup
     * @throws QuitterPartieException renvoie une exception si le joueur désir quitter la partie en cours
     */
    public Coup(Joueur j, Partie p) throws QuitterPartieException {

        if(j != null && p != null) {

            partie = p;
            joueur = j;
            active = false;

            LinkedList<Coup> coupsPossibles = null;

            Pion src;
            do {
                Views.Halma.template("HALMA", partie.getPlateau().toString(), partie.getParametres().getTailleX());
                try {
                    Views.Coup.inputSrcCoord(this);
                    // ON TEST SI LA CASE SÉLECTIONNÉ APPARTIENT BIEN AU PLATEAU
                    if(partie.getPlateau().coordValide(srcCoordX, srcCoordY) == null) throw new EnDehorsDuPlateauException();
                    // ON RÉCUPÈRE LE PION DE LA CASE S'IL Y EN A
                    src = partie.getPlateau().getCase(getSrcCoordX(), getSrcCoordY()).getPion();
                    // ON TEST SI LE PION APPARTIENT BIEN AU JOUEUR
                    if(src.getJoueur() != joueur) throw new PionPasAuJoueurEcxeption();
                    // ON CALCUL LES COUPS POSSIBLES DU PION
                    coupsPossibles = src.getCoupsPossible(true);
                }
                catch(CaseVideException | PionPasAuJoueurEcxeption | EnDehorsDuPlateauException | MauvaiseSaisieException e) {
                    // ON GÈRE LES ERREURS DE SAISIE
                    System.out.println(e);
                }
                catch(SousMenuException e) {
                    // ON AFFICHE LES SOUS-MENU QUAND LE JOUEUR RENTRE "0" EN SAISIE
                    int sousMenu = 0;
                    boolean run = true;

                    do {

                        try {
                            // ON AFFICHE LE SOUS-MENU ET ON DEMANDE UN CHOIX À L'UTILISATEUR
                            Views.Halma.template("Sous-menu", Views.Partie.dispSousMenu() + "Choix : ", partie.getParametres().getTailleX());
                            sousMenu = Views.Halma.inputInt();

                            switch (sousMenu) {

                                case 1: // ON SAUVEGARDE LA PARTIE EN COURS
                                    Models.Partie.SauvegardePartie(partie);
                                    break;
                                case 2: // ON DEMANDE À QUITTER LA PARTIE EN COURS
                                    throw new QuitterPartieException();
                                case 0: // ON RETOURNE À LA PARTIE EN COURS
                                    run = false;
                                    break;
                            }
                        }
                        catch(MauvaiseSaisieException e2) {
                            // ON TRAITE LES ERREURS DE SAISIE
                            System.out.println("\n" + e2 + "\n");
                        }

                    } while(run);

                }
            } while( coupsPossibles == null || coupsPossibles.size() == 0 );

            // AFFICHE DANS LA CONSOLE LES POSSIBILITÉS DU PIONS SÉLECTIONNÉ
            Views.Coup.afficherCoupsPossibles(coupsPossibles);

            Iterator<Coup> coups;
            Coup c = null;
            do {
                try {
                    c =  null;
                    coups = coupsPossibles.iterator();
                    // ON DEMANDE A L'UTILISATEUR DE SAISIR LES COORDONNÉES DE LA CASES DE DESTINATION
                    Views.Coup.inputDestCoord(this);
                    // ON CONTRÔLE QUE LA CASE CHOISI FAIT BIEN PARTIT DES SOLUTIONS DU PION
                    while(coups.hasNext() && !equals(c)) {
                        c = coups.next();
                        if(equals(c)) {
                            // LA DESTINATION EST CORRECT, ON ACTIVE LE COUP ET ON L'EXECUTE
                            active = true;
                            calculateForce();
                            executer();
                            // ON DETERMINE S'IL RESTE DES POSSIBILITÉS DE SAUT POUR LE PION (PAS DE RETOUR EN ARRIÈRE)
                            Pion dest = partie.getPlateau().getCase(getDestCoordX(), getDestCoordY()).getPion();
                            if (enleverCoupListe(coupsPossibles = dest.getCoupsPossible(false), this).size() > 0 && getDistance() > 1) {
                                // ON DÉCLENCHE UN NOUVEAU POUR LE PION EN COURS
                                new Coup(joueur, partie, getDestCoordX(), getDestCoordY());
                            }
                            return;
                        }

                     }

                    throw new CoupImpossibleException();

                } catch(PionPasAuJoueurEcxeption | EnDehorsDuPlateauException | CaseVideException | CoupImpossibleException | MauvaiseSaisieException e) {
                    // ON TRAITE LES ERREURS LIÉ À LA SAISIE DE LA DESTINATION
                    System.out.println(e);
                }
            } while (!equals(c));

        }

    }

    /**
     * Créer un coup en demandant au joueur de saisir les coordonnées de la case de destination
     * Gère la répétition des sauts
     * @param j joueur associé au coup
     * @param p partie associé au coup
     * @param srcX position horizontale de la source
     * @param srcY position verticale de la source
     * @throws CaseVideException renvoie une exception si la case source est vide
     */
    private Coup(Joueur j, Partie p, int srcX, int srcY) throws CaseVideException {

        if(j != null && p != null) {

            partie = p;
            joueur = j;
            active = false;

            Views.Halma.template("Halma",Views.Plateau.affichagePlateau(partie.getPlateau()), ParametresHalma.getInstance().getTailleX());

            // ON INSTALLE LES COORDONNÉES SOURCES DU COUP PRÉCÉDENT
            setSrcCoordX(srcX);
            setSrcCoordY(srcY);

            // ON DÉTERMINE LE DERNIER COUP JOUÉ
            Coup coupPrecedent = partie.getCoups().getLast();

            // ON RÉCUPÈRE LE PION SOURCE
            Pion src = partie.getPlateau().getCase(getSrcCoordX(), getSrcCoordY()).getPion();

            // ON DETERMINE LES COUPS POSSIBLES DU PION SOURCE, EN ENLEVANT SA PROVENANCE DES COUPS À JOUER
            LinkedList<Coup> coupsPossibles;
            // ON AFFICHE LES COUPS POSSIBLES DANS LA CONSOLE
            Views.Coup.afficherCoupsPossibles(enleverCoupListe(coupsPossibles = src.getCoupsPossible(false), coupPrecedent));

            if(coupsPossibles.size() > 0) {
                Iterator<Coup> coups;
                Coup c = null;
                boolean stop = false;
                do {
                    coups = coupsPossibles.iterator();
                    try {
                        // LE JOUEUR SAISIE LES COORDONNÉES DE LA CASE DE DESTINATION
                        Views.Coup.inputDestCoord(this);
                        // ON CONTRÔLE QUE LA CASE CHOISI N'EST PAS CELLE DU PION SOURCE, SINON LE TOUR S'ARRÊTE ICI
                        if(getSrcCoordX() != getDestCoordX() || getSrcCoordX() != getDestCoordY()) {
                            // ON CONTRÔLE QUE LA CASE DE DESTINATION FAIT BIEN PARTIT DES COUPS POSSIBLES
                            while (coups.hasNext() && !equals(c)) {
                                c = coups.next();
                                if (equals(c)) {
                                    // ON ACTIVE ET EXECUTE LE COUP À JOUER
                                    active = true;
                                    calculateForce();
                                    executer();
                                    // ON CONTRÔLE SI D'AUTRES SAUTS SONT POSSIBLES (PAS DE RETOUR EN ARRIÈRE
                                    Pion dest = partie.getPlateau().getCase(getDestCoordX(), getDestCoordY()).getPion();
                                    if (enleverCoupListe(coupsPossibles = dest.getCoupsPossible(false), this).size() > 0) {
                                        // ON CRÉER UN NOUVEAU COUP À PARTIR DE CE PION
                                        new Coup(joueur, partie, getDestCoordX(), getDestCoordY());
                                    }
                                    return;
                                }
                            }
                            // ON LÈVE UNE EXCEPTION SI LE COUP EST IMPOSSIBLE, LE JOUEUR SAISIE À NOUVEAU
                            throw new CoupImpossibleException();
                        }
                        else {
                            // ON ARRÊTE LE TOUR SI LA DESTINATION EST ÉGAL À LA SOURCE
                            stop = true;
                        }
                    } catch(PionPasAuJoueurEcxeption | EnDehorsDuPlateauException | CaseVideException | CoupImpossibleException | MauvaiseSaisieException e) {
                        // ON TRAITE LES DIFFÉRENTES ERREURS POSSIBLES LORS DE LA SAISIE
                        System.out.println(e);
                    }
                } while (!equals(c) && !stop);
            }

        }

    }

    /**
     * Création d'un coup avec des coordonnées source et destination pré-établits
     * Ce coup ne peut pas être executer, juste stocké
     * @param j joueur associé
     * @param p partie associé
     * @param srcX position horizontale de la case source
     * @param srcY position verticale de la case source
     * @param destX position horizontale de la case de destination
     * @param destY position verticale de la case de destination
     */
    public Coup(Joueur j, Partie p, int srcX, int srcY, int destX, int destY) {

        if(j != null && p != null) {

            joueur = j;
            partie = p;
            active = false;

            setSrcCoordX(srcX);
            setSrcCoordY(srcY);
            setDestCoordX(destX);
            setDestCoordY(destY);

            calculateForce();

        }

    }




    /**
     * Retourne la partie associée au coup
     * @return partie associée
     */
    public Partie getPartie() {
        return partie;
    }

    /**
     * Retourne le joueur à l'initiative du coup
     * @return joueur
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Retourne la position horizontale de la case source
     * @return Case
     */
    public int getSrcCoordX() {
        return srcCoordX;
    }

    /**
     * Retourne la position verticale de la case source
     * @return Case
     */
    public int getSrcCoordY() {
        return srcCoordY;
    }

    /**
     * Retourne la position horizontale de la case de destination
     * @return Case
     */
    public int getDestCoordX() {
        return destCoordX;
    }

    /**
     * Retourne la position verticale de la case de destination
     * @return Case
     */
    public int getDestCoordY() {
        return destCoordY;
    }

    public int getForce() {
        return force;
    }

    /**
     * enregistre la position horizontale de la case source
     * @param x position horizontale
     */
    public void setSrcCoordX(int x) {
        if(x > 0 && x <= partie.getPlateau().getTailleX()) {
            srcCoordX = x;
        }
    }

    /**
     * Enregistre la position verticale de la case source
     * @param y position verticale
     */
    public void setSrcCoordY(int y) {
        if(y > 0 && y <= partie.getPlateau().getTailleY()) {
            srcCoordY = y;
        }
    }

    /**
     * Enegistre la position horizontale de la case de destination
     * @param x position horizontale
     */
    public void setDestCoordX(int x) {
        if(x > 0 && x <= partie.getPlateau().getTailleX()) {
            destCoordX = x;
        }
    }

    /**
     * Enregistre la position verticale de la case de destination
     * @param y position verticale
     */
    public void setDestCoordY(int y) {
        if(y > 0 && y <= partie.getPlateau().getTailleY()) {
            destCoordY = y;
        }
    }

    /**
     * Supprime le coup retournant à la case précédente
     * @param coups liste de coups possible d'un pion
     * @param coupPrecedent coup précédent du pion
     * @return liste de coups
     */
    public LinkedList<Coup> enleverCoupListe(LinkedList<Coup> coups, Coup coupPrecedent) {
        Iterator<Coup> coupIterator = coups.iterator();
        Coup c;
        // ON RECHERCHE DANS LA LISTE LE COUP PRÉCÉDENT A ENLEVER DE LA LISTE
        while(coupIterator.hasNext()) {
            c = coupIterator.next();
            if(coupPrecedent.srcCoordX == c.destCoordX && coupPrecedent.srcCoordY == c.destCoordY && coupPrecedent.destCoordX == c.srcCoordX && coupPrecedent.destCoordY == c.srcCoordY) {
                coups.remove(c);
            }

        }
        return coups;
    }


    /**
     * Retourne la distance que représente le coup
     * @return distance
     */
    public int getDistance() {
        Case src = partie.getPlateau().getCase(srcCoordX, srcCoordY);
        Case dest = partie.getPlateau().getCase(destCoordX, destCoordY);
        return src.getDistance(dest);
    }

    /**
     * Calcul la puissance d'un coup
     */
    public void calculateForce() {
        // ON PART DU PRINCIPE QUE LA CASE DE DESTINATION DEVIENT NOTRE CASE SOURCE
        Case src = partie.getPlateau().getCase(destCoordX, destCoordY);
        Case dest = null;
        double force = 100;

        // ON REGARDE SI LA CASE SOURCE EST DÉJÀ SITUÉ DANS LES CASES À ATTEINDRE ...
        if(!joueur.getCasesCibles().contains(src)) {
            Iterator<Case> caseIterator = joueur.getCasesCibles().iterator();
            Case c;
            boolean run = true;
            // ON RECHERCHE LA CASE CIBLE LA PLUS APPROPRIÉE
            while(caseIterator.hasNext() && run) {
                c = caseIterator.next();
                if(!src.equals(c) && c.estLibre()) {
                    dest = c;
                    run = false;
                }
            }

            // SINON ON PREND LA PREMIÈRE PAR DEFAULT
            if(dest == null) {

                dest = joueur.getCasesCibles().getFirst();

            }

            // ON CALCUL LA FORCE QUI EN DÉCOULE
            double distance = src.getDistance(dest);
            double taillePlateau = partie.getPlateau().getCase(partie.getPlateau().getTailleX(), partie.getPlateau().getTailleY()).getDistance(partie.getPlateau().getCase(1,1));
            force = (distance / taillePlateau) * 100;

        }
        else { // SI OUI ON FIXE UNE FORCE MAXIMUM POUR CE COUP
            force = 0;
        }

        this.force = 100 - (int)force;

    }

    /**
     * Active le coup pour execution
     */
    public void activer() {
        active = true;
    }

    /**
     * Execute le coup
     * @throws PionPasAuJoueurEcxeption renvoie une exception au cas où le pion n'est pas au joueur
     * @throws CaseVideException renvoie une exception si une case est vide
     * @throws EnDehorsDuPlateauException renvoie une exception si une case est en dehors du plateau
     */
    public void executer() throws PionPasAuJoueurEcxeption, CaseVideException, EnDehorsDuPlateauException {
        if(active) {
            try {
                Plateau p = partie.getPlateau();
                Pion pion = p.getCase(srcCoordX, srcCoordY).getPion();
                // ON EXECUTE LE DEPLACEMENT DU PION SOURCE
                pion.deplacer(destCoordX, destCoordY);
                // ON AJOUTE LE COUP À LA LISTE DES COUPS DE LA PARTIE
                partie.getCoups().add(this);
            }
            catch(CaseNonVideException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Test si un coup est égal à un autre
     * @param c coup à comparer
     * @return true si les deux coups sont égaux
     */
    public boolean equals(Coup c) {
        if( c != null && ( this.srcCoordX == c.srcCoordX && this.srcCoordY == c.srcCoordY && this.destCoordX == c.destCoordX && this.destCoordY == c.destCoordY ) ) {
            return true;
        }
        return false;
    }

}


