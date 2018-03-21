package Views;

import Controllers.ParametresHalma;
import Exceptions.MauvaiseSaisieException;
import Exceptions.SousMenuException;
import Extensions.Couleur;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Classe gérant les entrées/affichages lié au coup
 * @author Romain Bourré
 */
public abstract class Coup {

    /**
     * Demande la saisie de coordonnée source pour un coup
     * @param c coups en cours construction
     * @throws MauvaiseSaisieException renvoie une exception en cas de mauvaise saisie
     * @throws SousMenuException renvoie une exception en cas de demande de l'utilisateur pour afficher le sous-menu
     */
    public static void inputSrcCoord(Controllers.Coup c) throws MauvaiseSaisieException, SousMenuException {

        Scanner s = new Scanner(System.in);
        int x;
        int y;

        try {
            System.out.println("\nQuel pion voulez-vous désigner " + c.getJoueur().getNom() + " ?");
            System.out.print("X : ");
            x = s.nextInt();
            if(x != 0) c.setSrcCoordX(x); else throw new SousMenuException();
            System.out.print("Y : ");
            y = s.nextInt();
            if(y != 0) c.setSrcCoordY(y); else throw new SousMenuException();
        } catch (InputMismatchException e) {
            throw new MauvaiseSaisieException();
        }

    }

    /**
     * Demande la saisie de coordonnées de destination pour un coup
     * @param c coups en cours de construction
     * @throws MauvaiseSaisieException renvoie une exception en case de mauvaise saisie
     */
    public static void inputDestCoord(Controllers.Coup c) throws MauvaiseSaisieException {

        Scanner s = new Scanner(System.in);

        try {
            System.out.println("\nOù voulez-vous le déplacer ?");
            System.out.print("X : ");
            c.setDestCoordX(s.nextInt());
            System.out.print("Y : ");
            c.setDestCoordY(s.nextInt());
        } catch(InputMismatchException e) {
            throw new MauvaiseSaisieException();
        }

    }

    /**
     * Affiche les coups possible d'un pion
     * @param coups coups possibles
     */
    public static void afficherCoupsPossibles(LinkedList<Controllers.Coup> coups) {

        String display = "";

        int nbrCoup;
        if((nbrCoup = coups.size()) > 0 ){
            display += "\n" + nbrCoup + " coup(s) possibles :\n";
            Iterator<Controllers.Coup> coupIterator = coups.iterator();
            Controllers.Coup c;
            while (coupIterator.hasNext()) {
                c = coupIterator.next();
                display += "--> " + c.getDestCoordX() + ", " + c.getDestCoordY() + "\n";
            }
        }

        if(ParametresHalma.getInstance().getColor()) display = Couleur.VERT(display);

        System.out.println(display);

    }

}
