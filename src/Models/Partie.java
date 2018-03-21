package Models;

import Extensions.Couleur;
import java.io.*;

/**
 * Gestion des saugardes/accés de la partie
 * @author Romain Bourré
 */
public class Partie {

    private static final String fichierSauvegarde = "save.halma";

    /**
     * Sauvegarde une partie
     * @param p partie à sauvegarder
     */
    public static void SauvegardePartie(Controllers.Partie p) {
        FileOutputStream file = null;
        try {
            file = new FileOutputStream(fichierSauvegarde);
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(p);
            System.out.println(Couleur.VERT("\nPartie enregistrée.\n"));
            file.close();
        }
        catch (Exception e) {
            // TRAITE LES ERREURS DE SAUVEGARDE
            System.out.println(Couleur.ROUGE("\nImpossible d'enregistrer les paramètres : " + e + "\n"));
        }
    }

    /**
     * Charge une partie
     * @return partie
     */
    public static Controllers.Partie ChargerPartie() {
        try {
            FileInputStream fileIn = new FileInputStream(fichierSauvegarde);
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            Controllers.Partie partieEnr = (Controllers.Partie) ois.readObject();
            fileIn.close();
            return partieEnr;
        } catch (Exception e) {
            // TRAITEMENT DES ERREURS DE CHARGEMENT
            System.out.println(Couleur.ROUGE("* Erreur de chargement de la partie : " + e + "*"));
        }

        return null;
    }

    /**
     * Supprime le fichier de sauvegarde de la partie
     */
    public static void SupprimerFichier() {
        new File(fichierSauvegarde).delete();
    }

}
