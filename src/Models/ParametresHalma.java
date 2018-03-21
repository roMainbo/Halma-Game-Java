package Models;

import Extensions.Couleur;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Gestion des saugardes/accés des paramètres principaux du jeu
 * @author Romain Bourré
 */
public class ParametresHalma {

    private static final String fichierParametreHalma = "param.halma";

    /**
     * Charge les paramètres enregistrés sur fichier s'ils existent, sinon charge les paramètres par default
     * @return Parametres principaux
     */
    public static Controllers.ParametresHalma ChargerParametres() {

        try {
            // ON ESSAYE CHARGER LES PARAMÈTRES A PARTIR DU FICHIER
            FileInputStream fileIn = new FileInputStream(fichierParametreHalma);
            ObjectInputStream ois = new ObjectInputStream(fileIn);
            return (Controllers.ParametresHalma)ois.readObject();
        }
        catch (Exception e) {
            // SINON ON CHARGE LES PARAMÈTRES PAR DEFAULT
            return Controllers.ParametresHalma.getInstance();
        }

    }

    /**
     * Enregistre les paramètres principaux dans un fichier
     */
    public static void EnregistrerParametres() {

        try {
            // ON ENREGISTRE LES PARAMETRES SUR FICHIER
            FileOutputStream file = new FileOutputStream(fichierParametreHalma);
            ObjectOutputStream oos = new ObjectOutputStream(file);
            oos.writeObject(Controllers.ParametresHalma.getInstance());
        }
        catch (Exception e) {
            // ON TRAITE LES ERREURS D'ENREGISTREMENT
            System.out.println(Couleur.ROUGE("\nImpossible d'enregistrer les paramètres : " + e + "\n"));
        }

    }

}
