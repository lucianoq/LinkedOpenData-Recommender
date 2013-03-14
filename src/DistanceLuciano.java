import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 14/03/13
 * Time: 16.45
 */
public class DistanceLuciano {

    private UndirectedSparseMultigraph<Film, EdgeFilm> filmGraph;

    public double passantD(Film a, Film b) {
        double d = 1 / (1 + cd_n_A_B(a, b) + cd_n_A_B(b, a));
        return d;
    }

    public double passantDW(Film a, Film b) {
        double i = 0;
        double j = 0;
        Collection<EdgeFilm> coll = filmGraph.getEdges();

        for (EdgeFilm ef : coll)
            i += ((cd_L_A_B(ef, a, b)) ? 1 : 0) / (1 + Math.log(cd_L_A_n(ef, a)));

        for (EdgeFilm ef : coll)
            i += ((cd_L_A_B(ef, b, a)) ? 1 : 0) / (1 + Math.log(cd_L_A_n(ef, b)));

        double d = 1 / (1 + i + j);
        return d;
    }

    public double passantI(Film a, Film b) {
        return 1 / (1 + cio_n_A_B(a,b) + cii_n_A_B(a,b));
    }

    public double passantIW(Film a, Film b) {
        double d;

        return d;
    }

    public double passantC(Film a, Film b) {
        double d;

        return d;
    }

    public double passantCW(Film a, Film b) {
        double d;

        return d;
    }


    public DistanceLuciano(UndirectedSparseMultigraph<Film, EdgeFilm> filmGraph) {
        this.filmGraph = filmGraph;
    }

    //vero se c'è arco L tra A e B
    private boolean cd_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> coll = filmGraph.findEdgeSet(a, b);
        for (EdgeFilm ef : coll)
            if (ef.getLabel().equals(l.getLabel()))
                return true;
        return false;
    }

    //numero di archi tra A e B
    private int cd_n_A_B(Film a, Film b) {
        return filmGraph.findEdgeSet(a, b).size();
    }

    //numero di risorse con arco L entrante proveniente da A
    private int cd_L_A_n(EdgeFilm l, Film a) {
        Collection<EdgeFilm> coll = filmGraph.getOutEdges(a);
        HashSet<Film> hs = new HashSet<Film>();

        for (EdgeFilm ef : coll)
            if (ef.getLabel().equals(l.getLabel()))
                hs.add(ef.getObject());
        return hs.size();
    }

    //vero se esiste C tale che A->C e B->C con archi tutti L
    private boolean cio_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> coll = filmGraph.getEdges();
        for (EdgeFilm ef1 : coll)
            for (EdgeFilm ef2 : coll)
                if (ef1.getSubject().equals(a))
                    if (ef2.getSubject().equals(b))
                        if (ef1.getObject().equals(ef2.getObject()))
                            if (ef1.getLabel().equals(ef2.getLabel()))
                                if (ef1.getLabel().equals(l.getLabel()))
                                    return true;
        return false;
    }

    //vero se esiste C tale che C->A e C->B con archi tutti L
    private boolean cii_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> coll = filmGraph.getEdges();
        for (EdgeFilm ef1 : coll)
            for (EdgeFilm ef2 : coll)
                if (ef1.getObject().equals(a))
                    if (ef2.getObject().equals(b))
                        if (ef1.getSubject().equals(ef2.getSubject()))
                            if (ef1.getLabel().equals(ef2.getLabel()))
                                if (ef1.getLabel().equals(l.getLabel()))
                                    return true;
        return false;
    }

    //Numero di archi L tale che Cio_L_A_B = true
    private int cio_n_A_B(Film a, Film b) {
        int i = 0;
        Collection<EdgeFilm> coll = filmGraph.getEdges();
        for (EdgeFilm ef1 : coll)
            if (cio_L_A_B(ef1, a, b) == true)
                i++;
        return i;
    }

    //Numero di archi L tale che Cii_L_A_B = true
    private int cii_n_A_B(Film a, Film b) {
        int i = 0;
        Collection<EdgeFilm> coll = filmGraph.getEdges();
        for (EdgeFilm ef1 : coll)
            if (cii_L_A_B(ef1, a, b) == true)
                i++;
        return i;
    }

    //Numero di risorse n tale che Cio_L_A_n = true
    private int cio_L_A_n(EdgeFilm l, Film a) {
        int i = 0;
        Collection<Film> coll = filmGraph.getVertices();
        for (Film f : coll)
            if (!f.equals(a))
                if (cio_L_A_B(l, a, f) == true)
                    i++;
        return i;
    }

    //Numero di risorse n tale che Cii_L_A_n = true
    private int cii_L_A_n(EdgeFilm l, Film a) {
        int i = 0;
        Collection<Film> coll = filmGraph.getVertices();
        for (Film f : coll)
            if (!f.equals(a))
                if (cii_L_A_B(l, a, f) == true)
                    i++;
        return i;
    }
}