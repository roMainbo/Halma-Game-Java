package Extensions;

import Controllers.ParametresHalma;

/**
 * Classe abstraite gérant l'affichage de couleur en console
 * @author Romain Bourré
 */
public abstract class Couleur {

    /**
     * Tableau prédéfini de couleur
     */
    private static String couleursPlayers[] = { "\033[33m", "\033[34m", "\033[36m", "\033[35m", "\033[31m"};

    /**
     * fixe la couleur par défault de la console
     * @param msg message à afficher
     * @param active paramètre de couleur activé/désactivé
     * @return message final
     */
    private static String couleur(String msg, boolean active) {
        if(active) msg += "\033[0m";
        return msg;
    }

    /**
     * Donne la couleur ROUGE à un message donné si les couleurs sont activée dans les paramètres
     * @param msg message à coloriser
     * @return message colorisé
     */
    public static String ROUGE(String msg) {
        String color = "\033[31m";
        boolean active = ParametresHalma.getInstance().getColor();
        if(active) msg = color + msg;
        return couleur(msg, active);
    }

    /**
     * Donne la couleur VERT à un message donné si les couleurs sont activée dans les paramètres
     * @param msg message à coloriser
     * @return message colorisé
     */
    public static String VERT(String msg) {
        String color = "\033[32m";
        boolean active = ParametresHalma.getInstance().getColor();
        if(active) msg = color + msg;
        return couleur(msg, active);
    }

    /**
     * Donne la couleur JAUNE à un message donné si les couleurs sont activée dans les paramètres
     * @param msg message à coloriser
     * @return message colorisé
     */
    public static String JAUNE(String msg) {
        String color = "\033[33m";
        boolean active = ParametresHalma.getInstance().getColor();
        if(active) msg = color + msg;
        return couleur(msg, active);
    }

    /**
     * Donne la couleur BLEU à un message donné si les couleurs sont activée dans les paramètres
     * @param msg message à coloriser
     * @return message colorisé
     */
    public static String BLEU(String msg) {
        String color = "\033[34m";
        boolean active = ParametresHalma.getInstance().getColor();
        if(active) msg = color + msg;
        return couleur(msg, active);
    }

    /**
     * Donne la couleur CYAN à un message donné si les couleurs sont activée dans les paramètres
     * @param msg message à coloriser
     * @return message colorisé
     */
    public static String CYAN(String msg) {
        String color = "\033[36m";
        boolean active = ParametresHalma.getInstance().getColor();
        if(active) msg = color + msg;
        return couleur(msg, active);
    }

    /**
     * Donne la couleur MAGENTA à un message donné si les couleurs sont activée dans les paramètres
     * @param msg message à coloriser
     * @return message colorisé
     */
    public static String MAGENTA(String msg) {
        String color = "\033[35m";
        boolean active = ParametresHalma.getInstance().getColor();
        if(active) msg = color + msg;
        return couleur(msg, active);
    }

    /**
     * Retourne une couleur du tableau de couleur prédéfini selon l'index
     * @param i index de couleur
     * @return chaîne de caractère représentant la couleur
     */
    public static String getCouleurJoueur(int i) {
        return couleursPlayers[i];
    }

}
