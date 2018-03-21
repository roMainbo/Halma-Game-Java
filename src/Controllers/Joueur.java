package Controllers;

import Exceptions.QuitterPartieException;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Classe représentant un joueur
 * @author Romain Bourré
 */
public class Joueur implements Serializable {

    private static int nombreJoueur = 0;

    private Partie partie;
    private String nom;
    private String couleur;
    private int numeroJoueur;
    private LinkedList<Pion> pions = new LinkedList();
    private LinkedList<Case> casesCibles = new LinkedList<Case>();

    /**
     * Créer un joueur
     * @param p partie associé au joueur
     * @param nom nom du joueur
     * @param couleur couleur représentant le joueur
     */
    public Joueur(Partie p, String nom, String couleur) {
        if(nom != "" && couleur != "") {
            Joueur.nombreJoueur++;
            if(Joueur.nombreJoueur > 4) Joueur.nombreJoueur -= 4;
            partie = p;
            this.nom = nom;
            this.couleur = couleur;
            this.numeroJoueur = Joueur.nombreJoueur;
        }
    }

    /**
     * Retourne la partie associée au joueur
     * @return une partie
     */
    public Partie getPartie() {
        return partie;
    }

    /**
     * Retourne le nom du joueur
     * @return nom du joueur
     */
    public String getNom() {
        if(ParametresHalma.getInstance().getColor() && couleur != "") {
            return couleur + nom + "\033[0m";
        }
        else {
            return "" + nom;
        }
    }

    /**
     * Retourne la couleur représentant le joueur
     * @return chaîne représentant la couleur
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Récupère les pions du joueur
     * @return liste de pions
     */
    public LinkedList<Pion> getPions() {
        return pions;
    }

    /**
     * Retourne le nombre de pion du joueur
     * @return nombre de pion
     */
    public int getNbrPions() {
        return pions.size();
    }

    /**
     * Retourne le numéro du joueur
     * @return numéro du joueur
     */
    public int getNumeroJoueur() {
        return numeroJoueur;
    }

    /**
     * Retourne le nombre de joueur créé
     * @return nombre de joueur de l'application
     */
    public static int getNombreJoueur() {
        return Joueur.nombreJoueur;
    }

    /**
     * Renvoie la liste des cases cibles du joueur
     * @return liste de cases cibles
     */
    public LinkedList<Case> getCasesCibles() {
        return casesCibles;
    }

    /**
     * Ajoute un pion simple au joueur
     */
    public void ajouterPionSimple() {
        pions.add(new Pion(this));
    }

    /**
     * Ajoute un pion double au joueur
     */
    public void ajouterPionDouble() {
        pions.add(new PionDouble(this));
    }

    /**
     * Supprime tous les pions du joueur
     */
    public void supprimerPions() {
        pions.clear();
    }

    /**
     * Déroule un tour du joueur
     * @throws QuitterPartieException renvoie une exception si le joueur demande à quitter la partie
     */
    public void jouer() throws QuitterPartieException {
        Coup c = new Coup(this, partie);
    }

    /**
     * Retourne le nom du joueur et son nombre de pion
     * @return String
     */
    public String toString() {
        if(ParametresHalma.getInstance().getColor() && couleur != "") {
            return couleur + numeroJoueur + "\033[0m";
        }
        else {
            return "" + numeroJoueur;
        }
    }

    /**
     * Décrémente le nombre de joueur de l'application à la fin d'une instance
     */
    public void finalize() {
        Joueur.nombreJoueur--;
    }


}
