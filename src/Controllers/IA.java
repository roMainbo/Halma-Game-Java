package Controllers;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Classe représentant une IA
 * @author Romain Bourré
 */
public class IA extends Joueur {

    /**
     * Constructeur de l'IA
     * @param p partie associée
     * @param nom nom représentant l'IA
     * @param couleur couleur reprénsentant l'IA
     */
    public IA(Partie p, String nom, String couleur) {
        super(p, nom, couleur);
    }

    /**
     * Joue un tour de l'IA
     */
    @Override
    public void jouer() {

        Views.Halma.template("HALMA", getPartie().getPlateau().toString() + "\n", getPartie().getParametres().getTailleX());

        // ON RÉCUPÈRE LES PIONS DE L'IA
        Iterator<Pion> pionDuJoueur = this.getPions().iterator();

        Pion p;
        int force = 0;

        // LISTE REPRÉSENTANT LES MEILLEURS COUPS POSSIBLES DE CHAQUE PION
        LinkedList<Coup> tousLesMeilleursCoups = new LinkedList<Coup>();
        // LISTE REPRÉSENTANT TOUS LES COUPS POSSIBLES DU JOUEUR
        LinkedList<Coup> tousLesCoups = new LinkedList<Coup>();
        // REPRÉSENTE LE MEILLEUR COUP À JOUER POUR CE TOUR
        Coup meilleurCoup = null;

        LinkedList<Coup> temp;

        try {

            while (pionDuJoueur.hasNext()) {

                p = pionDuJoueur.next();

                // ON DETERMINE LA FORCE ACTUEL DU PION
                force = new Coup(this, getPartie(), p.getaCase().getCoordX(), p.getaCase().getCoordY(), p.getaCase().getCoordX(), p.getaCase().getCoordY()).getForce();

                if((temp = p.getCoupsPossible(true)).size() > 0 ) tousLesCoups.addAll(temp);

                // ON RÉCUPÈRE LE MEILLEUR COUP DU PION S'IL EN A UN ET ON L'AJOUTE A LA LISTE DES MEILLEURS COUPS
                Coup meilleurCoupDuPion;
                if( ( meilleurCoupDuPion = determinerMeilleurCoup(temp, force) ) != null ) {
                    tousLesMeilleursCoups.add(meilleurCoupDuPion);
                }


            }

            // ON DETERMINE LE MEILLEUR COUP PARMI LA LISTE DES MEILLEURS COUP
            meilleurCoup = determinerMeilleurCoup(tousLesMeilleursCoups, 0);

            if(meilleurCoup != null) {

                // ACTIVATION ET EXECUTION DU MEILLEUR COUP
                meilleurCoup.activer();
                meilleurCoup.executer();

                if (meilleurCoup.getDistance() != 1) {

                    // ON DETERMINE SI DES SAUTS SONT ENCORE POSSIBLE
                    LinkedList<Coup> coupsSuivants;
                    while (meilleurCoup != null && (coupsSuivants = meilleurCoup.enleverCoupListe(getPartie().getPlateau().getCase(meilleurCoup.getDestCoordX(), meilleurCoup.getDestCoordY()).getPion().getCoupsPossible(false), meilleurCoup)).size() > 0) {

                        // ON DETERMINE LE MEILLEUR SAUT A FAIRE
                        meilleurCoup = determinerMeilleurCoup(coupsSuivants, meilleurCoup.getForce());
                        if(meilleurCoup != null) {
                            // ON ACTIVE ET EXECUTE LE COUP
                            meilleurCoup.activer();
                            meilleurCoup.executer();
                        }

                    }

                }

            }
            else {

                int nombre = (int)(Math.random() * tousLesCoups.size());
                meilleurCoup = tousLesCoups.get(nombre);
                if(meilleurCoup != null) {
                    // ACTIVATION ET EXECUTION DU MEILLEUR COUP
                    meilleurCoup.activer();
                    meilleurCoup.executer();
                }

            }

        }
        catch(Exception e) {}

    }

    /**
     * Détermine le meilleur coup parmi une liste de coup
     * @param l liste de coup
     * @param forceMax force à depasser pour le coup
     * @return meilleur coup
     */
    private Coup determinerMeilleurCoup(LinkedList<Coup> l, int forceMax) {
        if(l.size() > 0) {
            Coup c;
            Coup meilleurCoup = null;
            Iterator<Coup> coupIterator = l.iterator();
            while(coupIterator.hasNext()) {
                c = coupIterator.next();
                if( ( meilleurCoup == null || c.getForce() > meilleurCoup.getForce() ) && c.getForce() > forceMax) {
                    meilleurCoup = c;
                }
            }
            return meilleurCoup;
        }
        return null;
    }
}
