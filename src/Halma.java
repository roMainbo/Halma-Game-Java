import Controllers.Parametres;
import Controllers.ParametresHalma;
import Controllers.Partie;
import Exceptions.MauvaisParametreException;
import Exceptions.MauvaiseSaisieException;
import Extensions.Couleur;
import java.io.*;

/**
 * Classe principal du jeu
 * @author Romain Bourré
 */
public class Halma {

    public static void main(String[] args) {

        int choix;
        boolean run = true;
        ParametresHalma paramBase = Models.ParametresHalma.ChargerParametres(); // CHARGE LES PARAMÈTRES S'IL EN EXISTE D'ENREGISTRÉS, SINON CHARGE LES PARAMÈTRES PAR DEFAULT

        while(run) {

            // AFFICHE LE MENU PRINCIPAL
            Views.Halma.template("Halma", Views.Halma.menuPrincipal(), paramBase.getTailleX());

            try {

                // DEMANDE UN CHOIX À L'UTILSATEUR
                choix = Views.Halma.inputInt();

                switch (choix) {

                    case 0: // QUITTE LE PROGRAMME
                        run = false;
                        break;

                    case 1: // CHARGEMENT D'UNE NOUVELLE PARTIE
                        int nbrIA = 0;
                        int nbrJoueur = 0;
                        Parametres paramPartie = null;
                        do {
                            // ON CREER LES PARAMETRES DE PARTIE A PARTIR DES PARAMETRES DU PROGRAMME EN AJOUTANT UN NOMBRE DE JOUEUR CHOISI PAR L'UTILISATEUR
                            try {
                                Views.Halma.template("Nouvelle partie", "Nombre d'IA : ", paramBase.getTailleX());
                                nbrIA = Views.Halma.inputInt();
                                System.out.print("Nombre de joueur : ");
                                nbrJoueur = Views.Halma.inputInt();
                                paramPartie = new Parametres(paramBase, nbrIA, nbrJoueur);
                            } catch (MauvaiseSaisieException | MauvaisParametreException e) {
                                // ON TRAITE LES ERREURS DE SAISIE OU DE CONFIGURATION
                                paramPartie = new Parametres(paramBase);
                                System.out.println("\n" + e + "\n");
                            }
                        } while (nbrJoueur != paramPartie.getNbrJoueur());
                        // ON CRÉER ET LANCE LA PARTIE
                        Partie newGame = new Partie(paramPartie);
                        newGame.lancerPartie();
                        break;

                    case 2: // CHARGEMENT D'UNE PARTIE SAUVEGARDÉE
                        Partie partieEnr = null;
                        if((partieEnr = Models.Partie.ChargerPartie()) != null) {
                            boolean termine = partieEnr.lancerPartie();
                            if(termine) Models.Partie.SupprimerFichier();
                        }
                        break;

                    case 3: // MODIFICATION DES PARAMÈTRES PRINCIPAUX
                        boolean config = true;
                        int choixParam ;
                        do {
                            // ON AFFICHE LE MENU ET DEMANDE UN CHOIX A L'UTILISATEUR
                            Views.Halma.template("Paramètres", Views.Halma.menuParametres(), paramBase.getTailleX());
                            choixParam = Views.Halma.inputInt();
                            switch (choixParam) {

                                case 1: // CHANGEMENT DE LA TAILLE DU PLATEAU
                                    int tailleX = 0;
                                    int tailleY = 0;
                                    do {
                                        Views.Halma.template("Plateau", "Taille actuel du plateau : " + paramBase.getTailleX() +  " X "  + paramBase.getTailleY() + ".\nQuel taille voulez-vous attribuer : ", paramBase.getTailleX());
                                        try {
                                            tailleX = tailleY = Views.Halma.inputInt();
                                            paramBase.setTailleX(tailleX);
                                            paramBase.setTailleY(tailleY);
                                            // ON SAUVEGARDE LES PARAMÈTRES DANS UN FICHIER
                                            Models.ParametresHalma.EnregistrerParametres();
                                            System.out.println(Couleur.VERT("\nParamètre changés."));
                                        } catch (MauvaiseSaisieException | MauvaisParametreException e) {
                                            // TRAITEMENT DES ERREURS DE SAISIE OU DE MAUVAIS PARAMETRE
                                            System.out.println(Couleur.ROUGE("\n" + e + "\n"));
                                        }
                                    } while (tailleX != paramBase.getTailleX() && tailleY != paramBase.getTailleY());
                                    break;

                                case 2: // ON ACTIVE/DESACTIVE LA COULEUR
                                    String statut;
                                    if(paramBase.getColor()) statut = "active"; else statut = "inactive";
                                    Views.Halma.template("Couleur", "Couleur : " + statut + "\n", paramBase.getTailleX());
                                    if(paramBase.getColor()) {
                                        System.out.print("Voulez-vous désactiver les couleurs ? (O/N) ");
                                        if(true) paramBase.setColor(false);
                                    }
                                    else {
                                        System.out.println("Voulez-vous activer les couleurs ? (O/N) ");
                                        if(true) paramBase.setColor(true);
                                    }
                                    Models.ParametresHalma.EnregistrerParametres();
                                    System.out.println(Couleur.VERT("\nParamètre changé."));
                                    break;

                                case 0:
                                    config = false;
                                    break;

                            }
                        } while (config);

                    default:
                        break;


                }

            }
            catch(MauvaiseSaisieException e) {
                // ON TRAITE LES ERREURS DE SAISIE DU MENU PRINCIPAL
                System.out.println(e);
            }

        }

    }

}
