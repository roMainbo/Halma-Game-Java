package Controllers;

import Exceptions.CaseNonVideException;
import Exceptions.CaseVideException;
import Exceptions.QuitterPartieException;
import Extensions.Couleur;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Classe représentant une partie
 * @author Romain Bourré
 */
public class Partie implements Serializable {

    private Parametres parametres;

    private Plateau plateau;
    private LinkedList<Joueur> joueurs;
    private LinkedList<Coup> coups;
    private Extensions.ListeCirculaire joueursCirculaire;

    /**
     * Créer une nouvelle partie
     * @param p paramètre de partie
     */
    public Partie(Parametres p) {

        parametres = p;
        joueursCirculaire = null;

        // ON CREÉER LE PLATEAU
        plateau = new Plateau(parametres.getTailleX(), parametres.getTailleY());

        // ON CREÉER LES DIFFÉRENTES LISTES
        joueurs = new LinkedList<Joueur>();
        coups = new LinkedList<Coup>();

        // ON CREÉR LES JOUEURS
        System.out.println("\n");
        for(int i = 1; i <= parametres.getNbrJoueur(); i++) {
            joueurs.add(new Joueur(this, Views.Joueur.getNomJoueur(), Couleur.getCouleurJoueur(i-1)));
        }

        for(int i = 1; i <= parametres.getNbrIA(); i++) {
            joueurs.add(new IA(this, "IA" + (Joueur.getNombreJoueur()+1), Couleur.getCouleurJoueur(4)));
        }

        // ON CREÉER ET ON PLACE LES PIONS DES JOUEURS
        creerPions();
        placerPions();

        //simulation();

        //Sauvegarde();

    }

    /**
     * Retourne les paramètres de la partie
     * @return paramètres de partie
     */
    public Parametres getParametres() {
        return parametres;
    }

    /**
     * Retourne le plateau de la partie
     * @return plateau
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Retourne la liste des joueurs de la partie
     * @return liste de joueurs
     */
    public LinkedList<Joueur> getJoueurs() {
        return joueurs;
    }

    /**
     * Retourne la liste des coups de la partie
     * @return liste de coups
     */
    public LinkedList<Coup> getCoups() {
        return coups;
    }


    /**
     * Créer les pions nécéssaires pour chaque joueur de la partie
     * prend en compte les pions simple ou double
     */
    private void creerPions() {
        Iterator<Joueur> joueurIterator = joueurs.iterator();
        Joueur j;
        // POUR 2 JOUEURS
        if(parametres.getTotalJoueur() == 2) {
            while(joueurIterator.hasNext()) {
                j = joueurIterator.next();
                // CRÉATION DES PIONS SIMPLES
                for(int ps = 1; ps <= 12; ps++) {
                    j.ajouterPionSimple();
                }
                // CRÉATION DES PIONS DOUBLES
                for(int pd = 1; pd <= 3; pd++) {
                    j.ajouterPionDouble();
                }
            }
        }
        // POUR 3 ET 4 JOUEURS
        else if(parametres.getTotalJoueur() == 3 || parametres.getTotalJoueur() == 4) {
            while(joueurIterator.hasNext()) {
                j = joueurIterator.next();
                // CRÉATIONS DES PIONS SIMPLES
                for(int ps = 1; ps <= 6; ps++) {
                    j.ajouterPionSimple();
                }
            }
        }
    }

    /**
     * Détermine la zone pour chaque joueur et lance le placement de ses pions
     */
    private void placerPions() {
        Iterator<Joueur> joueurIterator1 = joueurs.iterator();
        Joueur j2;
        if(joueurs.size() > 2) {
            while (joueurIterator1.hasNext()) {
                j2 = joueurIterator1.next();
                // ON LANCE LA PROCÉDURE DE PLACEMENT DES PIONS POUR LA ZONE
                initZone(j2, j2.getNumeroJoueur());
            }
        }
        else {
            j2 = joueurIterator1.next();
            initZone(j2, 1);
            j2 = joueurIterator1.next();
            initZone(j2, 3);
        }
    }

    /**
     * Initialise une zone déterminée avec les pions d'un joueur
     * @param j joueur à positioner
     * @param position position sur le plateau
     */
    private void initZone(Joueur j, int position) {
        LinkedList<Pion> pions = new LinkedList<Pion>();
        pions.addAll(j.getPions());
        int compose = Extensions.Outils.composeInt(pions.size());
        int taillePlateauX = plateau.getTailleX();
        int taillePlateauY = plateau.getTailleY();
        int ox;
        int oy;


        switch(position) { // ON BALAYE LA ZONE CONCERNÉE
            case 1: // REPRÉSENTE LA ZONE EN BAS À DROITE
                for(int y = 0; y < compose; y++) {
                    for (int x = (taillePlateauX-1); x >= (taillePlateauX - (compose - y)); x--) {
                        // ON CHERCHE LE BON PION À PLACER PUIS ON LE SUPPRIME DE LA LISTE
                        pions.remove(trouverPion(pions.iterator(), plateau.getCase(x+1, y+1)));
                        // ON DETERMINE LA CASE OPPOSÉE (CASE CIBLE) ET ON L'AJOUTE A LA LISTE DES CASES CIBLES DU JOUEUR
                        ox = (plateau.getTailleX()) - x;
                        oy = (plateau.getTailleY()) - y;
                        j.getCasesCibles().add(plateau.getCase(ox, oy));
                    }
                }
                break;
            case 2:// REPRÉSENTE LA ZONE EN BAS À GAUCHE
                for(int y = 0; y < compose; y++) {
                    for (int x = (compose - 1 - y); x >= 0; x--) {
                        // ON CHERCHE LE BON PION À PLACER PUIS ON LE SUPPRIME DE LA LISTE
                        pions.remove(trouverPion(pions.iterator(), plateau.getCase(x+1, y+1)));
                        // ON DETERMINE LA CASE OPPOSÉE (CASE CIBLE) ET ON L'AJOUTE A LA LISTE DES CASES CIBLES DU JOUEUR
                        ox = (plateau.getTailleX()) - x;
                        oy = (plateau.getTailleY()) - y;
                        j.getCasesCibles().add(plateau.getCase(ox, oy));
                    }
                }
                break;
            case 3: // REPRÉSENTE LA ZONE EN HAUT À GAUCHE
                for(int y = taillePlateauY-1; y >= compose; y--) {
                    for (int x = 0; x < (compose - (taillePlateauY - 1 - y)); x++) {
                        // ON CHERCHE LE BON PION À PLACER PUIS ON LE SUPPRIME DE LA LISTE
                        pions.remove(trouverPion(pions.iterator(), plateau.getCase(x+1, y+1)));
                        // ON DETERMINE LA CASE OPPOSÉE (CASE CIBLE) ET ON L'AJOUTE A LA LISTE DES CASES CIBLES DU JOUEUR
                        ox = (plateau.getTailleX()) - x;
                        oy = (plateau.getTailleY()) - y;
                        j.getCasesCibles().add(plateau.getCase(ox, oy));
                    }
                }
                break;
            case 4: // REPRÉSENTE LA ZONE EN HAUT À DROITE
                for(int y = taillePlateauY-1; y >= compose; y--) {
                    for (int x = taillePlateauX-1; x > (taillePlateauY - 1 + (taillePlateauY - 1 - y)) - compose; x--) {
                        // ON CHERCHE LE BON PION À PLACER PUIS ON LE SUPPRIME DE LA LISTE
                        pions.remove(trouverPion(pions.iterator(), plateau.getCase(x+1, y+1)));
                        // ON DETERMINE LA CASE OPPOSÉE (CASE CIBLE) ET ON L'AJOUTE A LA LISTE DES CASES CIBLES DU JOUEUR
                        ox = (plateau.getTailleX()) - x;
                        oy = (plateau.getTailleY()) - y;
                        j.getCasesCibles().add(plateau.getCase(ox, oy));
                    }
                }
                break;
        }
    }


    /**
     * Place sur une case le pion adapté d'un joueur
     * @param pionIterator Liste de pion d'un joueur
     * @param c Case du plateau
     * @return le pion placé
     */
    private Pion trouverPion(Iterator<Pion> pionIterator, Case c) {
        int taillePlateauX = plateau.getTailleX();
        int taillePlateauY = plateau.getTailleY();
        int x = c.getCoordX()-1;
        int y = c.getCoordY()-1;
        Pion p = null;
        if( // ON REGARDE SI LA CASE EST DANS UN COIN
                (
                        (x == taillePlateauX-1 && y <= 1) ||
                                (x >= taillePlateauX-1-1 && y == 0) ||
                                (x == taillePlateauX-1 && y >= taillePlateauY-1-1) ||
                                (x >= taillePlateauX-1-1 && y == taillePlateauY-1) ||
                                (x <= 1 && y == 0) ||
                                (x == 0 && y <= 1) ||
                                (x == 0 && y >= taillePlateauY-1-1) ||
                                (x <= 1 && y == taillePlateauY-1)

                ) &&
                        parametres.getTotalJoueur() == 2
                ) {
            while(pionIterator.hasNext() && c.estLibre()) { // ON CHERCHE UN PION DOUBLE ET ON LE PLACE
                if((p = pionIterator.next()).getClass() == PionDouble.class) {
                    c.poserPion(p);
                }
            }
        }
        else {
            while(pionIterator.hasNext() && c.estLibre()) { // ON CHERCHE UN PION SIMPLE ET ON LE PLACE
                if((p = pionIterator.next()).getClass() == Pion.class) {
                    c.poserPion(p);
                }
            }
        }
        return p;
    }

    /**
     * Lance la partie
     * @return true si la partie est terminée, false si interrompu
     */
    public boolean lancerPartie() {
        if(joueursCirculaire == null) joueursCirculaire = new Extensions.ListeCirculaire(joueurs); else joueursCirculaire.previous();
        Joueur joueurGagnant = null;
        int nbrTour = 0;
        try {
            while ((joueurGagnant = verifierPartie()) == null) {
                nbrTour++;
                ((Joueur) joueursCirculaire.next()).jouer();
            }
            Views.Halma.template("HALMA", getPlateau().toString() + "\n", getParametres().getTailleX());
            Views.Halma.template("GAGNE !", joueurGagnant.getNom() + Couleur.VERT(" gagne en " + nbrTour + " tours ! Bravo !\n\n"), parametres.getTailleX());
            return true;
        }
        catch(QuitterPartieException e) {
            // LE JOUEUR A DEMANDÉ A QUITTER LA PARTIE EN COURS
            return false;
        }
    }

    /**
     * Vérifie s'il y a un gagnant parmi les joueur
     * @return joueur gagnant ou null
     */
    private Joueur verifierPartie() {
        Iterator<Joueur> joueurIterator = joueurs.iterator();
        Joueur j;
        Joueur joueurGagnant = null;
        // ON RÉCUPÈRE LA LISTE DES JOUEURS
        while(joueurIterator.hasNext() && joueurGagnant == null) {
            j = joueurIterator.next();
            joueurGagnant = j;
            // ON RÉCUPÈRE LA LISTE DES CASES CIBLES DU JOUEURS
            Iterator<Case> casesCiblesIterator = j.getCasesCibles().iterator();
            Case c = null;
            while(casesCiblesIterator.hasNext()) {
                c = casesCiblesIterator.next();
                try {
                    // ON VÉRIFIE QUE C'EST BIEN UN PION DU JOUEUR QUI EST POSÉ
                    if (!c.getPion().getJoueur().equals(j)) {
                        joueurGagnant = null;
                    }
                }
                catch(CaseVideException e) {
                    // ON VÉRIFIE QUE LA CASE N'EST PAS VIDE
                    joueurGagnant = null;
                }
            }
        }
        return joueurGagnant;
    }



    /**
     * temporaire
     */
    public void simulation() {

        int taillePlateauX = plateau.getTailleX();
        int taillePlateauY = plateau.getTailleY();

        int compose = Extensions.Outils.composeInt(joueurs.getFirst().getNbrPions());
        Case actuel;
        Case newCase;
        int nX;
        int nY;
        Pion intermediaire;

        System.out.println(plateau);

        for(int y = 0; y < compose; y++) {
            for (int x = (taillePlateauX-1); x >= (taillePlateauX - (compose - y)); x--) {

                actuel = plateau.getCase(x+1, y+1);

                nX = (plateau.getTailleX()-1) - x;
                nY = (plateau.getTailleY()-1) - y;

                newCase = plateau.getCase(nX+1, nY+1);

                try {
                    intermediaire = actuel.getPion();
                    actuel.enleverPion();
                    actuel.poserPion(newCase.getPion());
                    newCase.setPionBrut(intermediaire);
                    newCase.getPion().setaCase(newCase);
                } catch(CaseVideException e) { System.out.println("Case : " + (x+1) + ", " + (y+1)); }



            }
        }




        for(int y = 0; y < compose; y++) {
            for (int x = (compose - 1 - y); x >= 0; x--) {

                actuel = plateau.getCase(x+1, y+1);

                nX = (plateau.getTailleX()-1) - x;
                nY = (plateau.getTailleY()-1) - y;

                newCase = plateau.getCase(nX+1, nY+1);

                try {
                    intermediaire = actuel.getPion();
                    actuel.enleverPion();
                    actuel.poserPion(newCase.getPion());
                    newCase.setPionBrut(intermediaire);
                    newCase.getPion().setaCase(newCase);
                } catch(CaseVideException e) {}

            }
        }

        try {
            plateau.getCase(5, 10).getPion().deplacer(6, 10);
            plateau.getCase(10, 5).getPion().deplacer(10, 6);
        } catch (CaseVideException | CaseNonVideException e) {}

    }

}
