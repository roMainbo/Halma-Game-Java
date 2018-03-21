package Extensions;

import java.io.Serializable;

/**
 * Classe représentant un élément de chaîne circulaire
 * @author Romain Bourré
 */
public class ElmtCirculaire implements Serializable {

    private Object element;
    private ElmtCirculaire next;
    private ElmtCirculaire previous;

    /**
     * Constructeur d'élement
     * @param o élément à enregistrer
     */
    public ElmtCirculaire(Object o) {
        if(o != null) {
            element = o;
        }
    }

    /**
     * Retourne l'élément
     * @return élément
     */
    public Object getElement() {
        return element;
    }

    /**
     * Retourne l'élément suivant
     * @return élément suivant
     */
    public ElmtCirculaire getNext() {
        return next;
    }

    /**
     * Retourne l'élément précédent
     * @return élément précédent
     */
    public ElmtCirculaire getPrevious() {
        return previous;
    }

    /**
     * Fixe un élément
     * @param o élément à fixer
     */
    public void setElement(Object o) {
        if(o != null) {
            element = o;
        }
    }

    /**
     * Fixe l'élément suivant à cet élément
     * @param e élément suivant
     */
    public void setNext(ElmtCirculaire e) {
        if(e != null) {
            next = e;
        }
    }

    /**
     * Fixe l'élément précédent à cet élément
     * @param e élément précédent
     */
    public void setPrevious(ElmtCirculaire e) {
        if(e != null) {
            previous = e;
        }
    }

}
