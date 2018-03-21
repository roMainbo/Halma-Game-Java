package Extensions;

import javax.lang.model.element.Element;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Classe générant une chaîne circulaire
 * @author Romain Bourré
 */
public class ListeCirculaire implements Serializable {

    /**
     * Tableau des éléments
     */
    private ElmtCirculaire elmts[];
    /**
     * Curseur de lecture
     */
    private ElmtCirculaire cursor;

    /**
     * Génère une liste circulaire à partir d'une Linkedlist
     * @param l Linkedlist à transformer en chaîne circulaire
     */
    public ListeCirculaire(LinkedList l) {
        elmts = new ElmtCirculaire[l.size()];
        for(int i = 0; i < l.size(); i++) {
            elmts[i] = new ElmtCirculaire(l.get(i));
            if(i == 0) {
                elmts[i].setPrevious(elmts[i]);
                elmts[i].setNext(elmts[i]);
            }
            else if(i > 0 && i < l.size()) {
                elmts[i].setPrevious(elmts[i-1]);
                elmts[i-1].setNext(elmts[i]);
                elmts[i].setNext(elmts[0]);
            }
        }
        cursor = null;
    }

    /**
     * Retourne l'élément suivant de la chaîne circulaire
     * @return élément suivant
     */
    public Object next() {
        if(cursor == null) {
            cursor = elmts[0];
        }
        else {
            cursor = cursor.getNext();
        }
        return cursor.getElement();
    }

    /**
     * Retourne l'élément précédent de la chaîne circulaire
     * @return élément précédent
     */
    public Object previous() {
        if(cursor == null) {
            cursor = elmts[elmts.length-1];
        }
        else {
            cursor = cursor.getPrevious();
        }
        return cursor.getElement();
    }

    /**
     * Retourne un élément précis de la chaîne circulaire
     * @param i index de l'élément à récupérer
     * @return élement
     */
    public Object getIndex(int i) {
        return elmts[i].getElement();
    }

    /**
     * Fixe le curseur de lecture à une position précise
     * @param i index à positioner au curseur de lecture
     */
    public void setCursor(int i) {
        cursor = elmts[i];
    }

}
