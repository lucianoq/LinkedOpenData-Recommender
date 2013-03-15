import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 14/03/13
 * Time: 16.45
 */
public class DistanceLuciano {

    private DirectedSparseMultigraph<Film, EdgeFilm> filmGraph;

    public DistanceLuciano(DirectedSparseMultigraph<Film, EdgeFilm> filmGraph) {
        this.filmGraph = filmGraph;
    }

    public double passantD(Film a, Film b) {
        double d = 1.0d / (1 + cd_n_A_B(a, b) + cd_n_A_B(b, a));
        return d;
    }

    public double passantDW(Film a, Film b) {
        double i = 0;
        double j = 0;
        Collection<EdgeFilm> coll = filmGraph.getEdges();

        for (EdgeFilm ef : coll) {
            i += ((cd_L_A_B(ef, a, b)) ? 1.0d : 0.0d) / (1 + Math.log(cd_L_A_n(ef, a)));
            j += ((cd_L_A_B(ef, b, a)) ? 1.0d : 0.0d) / (1 + Math.log(cd_L_A_n(ef, b)));
        }

        double d = 1.0d / (1 + i + j);
        return d;
    }

    public double passantI(Film a, Film b) {
        return 1.0d / (1 + cio_n_A_B(a, b) + cii_n_A_B(a, b));
    }

    public double passantIW(Film a, Film b) {
        double i = 0;
        double j = 0;
        Collection<EdgeFilm> coll = filmGraph.getEdges();

        for (EdgeFilm ef : coll) {
            i += ((cii_L_A_B(ef, a, b)) ? 1.0d : 0.0d) / (1 + Math.log(cii_L_A_n(ef, a)));
            j += ((cio_L_A_B(ef, b, a)) ? 1.0d : 0.0d) / (1 + Math.log(cio_L_A_n(ef, a)));
        }

        double d = 1.0 / (1 + i + j);
        return d;
    }

    public double passantC(Film a, Film b) {
        double d = 1.0 / (1 + cd_n_A_B(a, b) + cd_n_A_B(b, a) + cio_n_A_B(a, b) + cii_n_A_B(a, b));
        return d;
    }

    public double passantCW(Film a, Film b) {
        double d = 0;
        double den1 = 0;
        double den2 = 0;
        double den3 = 0;
        double den4 = 0;
        Collection<EdgeFilm> coll = filmGraph.getEdges();

        for (EdgeFilm ef : coll) {
            den1 += ((cd_L_A_B(ef, a, b)) ? 1 : 0) / (1 + Math.log(cd_L_A_n(ef, a)));
            den2 += ((cd_L_A_B(ef, b, a)) ? 1 : 0) / (1 + Math.log(cd_L_A_n(ef, b)));
            den3 += ((cii_L_A_B(ef, a, b)) ? 1 : 0) / (1 + Math.log(cii_L_A_n(ef, a)));
            den4 += ((cio_L_A_B(ef, b, a)) ? 1 : 0) / (1 + Math.log(cio_L_A_n(ef, a)));
        }

        d = 1.0 / (1 + den1 + den2 + den3 + den4);
        return d;
    }

    //vero se c'è arco L tra A e B
    private boolean cd_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> coll = filmGraph.findEdgeSet(a, b);
        for (EdgeFilm ef : coll)
            if (ef.getLabelModified().equals(l.getLabelModified()))
                return true;
        return false;
    }

    //numero di archi tra A e B
    private int cd_n_A_B(Film a, Film b) {
        return filmGraph.findEdgeSet(a, b).size();
    }

    //numero di risorse con arco L entrante, proveniente da A
    private int cd_L_A_n(EdgeFilm l, Film a) {
        Collection<EdgeFilm> coll = filmGraph.getOutEdges(a);
        HashSet<Film> hs = new HashSet<Film>();

        for (EdgeFilm ef : coll) {
            if (ef.getLabelModified().equals(l.getLabelModified())) {
                hs.add(ef.getObject());
            }
        }
        return hs.size();
    }

    //vero se esiste C tale che A->C e B->C con archi tutti L
    private boolean cio_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> collA = filmGraph.getOutEdges(a);
        Collection<EdgeFilm> collB = filmGraph.getOutEdges(b);
        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getObject().equals(efB.getObject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        if (efA.getLabelModified().equals(l.getLabelModified()))
                            return true;
        return false;
    }

    //vero se esiste C tale che C->A e C->B con archi tutti L
    private boolean cii_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> collA = filmGraph.getInEdges(a);
        Collection<EdgeFilm> collB = filmGraph.getInEdges(b);
        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getSubject().equals(efB.getSubject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        if (efA.getLabelModified().equals(l.getLabelModified()))
                            return true;
        return false;
    }

    //Numero di archi L tale che Cio_L_A_B = true
    private int cio_n_A_B(Film a, Film b) {
        int i = 0;
        Collection<EdgeFilm> coll = filmGraph.getEdges();
        for (EdgeFilm ef1 : coll)
            if (cio_L_A_B(ef1, a, b))
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
