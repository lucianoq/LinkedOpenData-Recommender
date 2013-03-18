import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Distance {
    //    public static final int NUM_COPPIE_FILM = 270000;
    public static final int NUM_COPPIE_FILM = 2500;
    private static Map<Coppia, Double> passantD;
    private static Map<Coppia, Double> passantDW;
    private static Map<Coppia, Double> passantI;
    private static Map<Coppia, Double> passantIW;
    private static Map<Coppia, Double> passantC;
    private static Map<Coppia, Double> passantCW;
    private static Map<Coppia, Double> nostra;
    private static Map<Coppia, Integer> cio_n_A_B;
    private static Map<Coppia, Integer> cii_n_A_B;

    public static void fill() {
        System.out.println("[INFO] [" + new Date() + "] Inizio il calcolo di tutte le distanze.");
        passantD = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        passantDW = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        passantI = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        passantC = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        passantIW = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        passantCW = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        nostra = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        cio_n_A_B = new ConcurrentHashMap<Coppia, Integer>(NUM_COPPIE_FILM);
        cii_n_A_B = new ConcurrentHashMap<Coppia, Integer>(NUM_COPPIE_FILM);

        ArrayList<Film> graph = Grafo.getFilms();
        ArrayList<ComputeDistance> threads = new ArrayList<ComputeDistance>();

        for (int i = 0; i < 8; i++) {
            threads.add(new ComputeDistance());
        }

        for (int i = 0; i < graph.size(); i++) {
            threads.get(i % 8).getSubset().add(graph.get(i));
        }

        for (int i = 0; i < 8; i++) {
            threads.get(i).start();
        }

        for (int i = 0; i < 8; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[INFO] Thread " + i + " finito.");
        }

        System.out.println("[INFO] [" + new Date() + "] Fine del calcolo di tutte le distanze.");
    }

    public static double getDistancePassantD(Film f1, Film f2) {
        return passantD.get(new Coppia(f1, f2));
    }

    public static double getDistancePassantDW(Film f1, Film f2) {
        return passantDW.get(new Coppia(f1, f2));
    }

    public static double getDistancePassantI(Film f1, Film f2) {
        return passantI.get(new Coppia(f1, f2));
    }

    public static double getDistancePassantIW(Film f1, Film f2) {
        return passantIW.get(new Coppia(f1, f2));
    }

    public static double getDistancePassantC(Film f1, Film f2) {
        return passantC.get(new Coppia(f1, f2));
    }

    public static double getDistancePassantCW(Film f1, Film f2) {
        return passantCW.get(new Coppia(f1, f2));
    }

    public static double getDistanceNostra(Film f1, Film f2) {
        return nostra.get(new Coppia(f1, f2));
    }

    protected static double nostra(Film a, Film b) {
        return 0.5;
        //TODO
    }

    protected static double passantD(Film a, Film b) {
        double d = 1.0d / (1 + cd_n_A_B(a, b) + cd_n_A_B(b, a));
        return d;
    }

    protected static double passantDW(Film a, Film b) {
        double i = 0;
        double j = 0;
        Collection<EdgeFilm> coll = FilmGraph.getGraph().getEdges();

        for (EdgeFilm ef : coll) {
            i += ((cd_L_A_B(ef, a, b)) ? 1.0d : 0.0d) / (1 + Math.log(cd_L_A_n(ef, a)));
            j += ((cd_L_A_B(ef, b, a)) ? 1.0d : 0.0d) / (1 + Math.log(cd_L_A_n(ef, b)));
        }

        double d = 1.0d / (1 + i + j);
        return d;
    }

    protected static double passantI(Film a, Film b) {
        return 1.0d / (1 + cio_n_A_B.get(new Coppia(a, b)) + cii_n_A_B.get(new Coppia(a, b)));
    }

    protected static double passantIW(Film a, Film b) {
        double i = 0;
        double j = 0;
        Collection<EdgeFilm> coll = FilmGraph.getGraph().getEdges();

        for (EdgeFilm ef : coll) {
            i += ((cii_L_A_B(ef, a, b)) ? 1.0d : 0.0d) / (1 + Math.log(cii_L_A_n(ef, a)));
            j += ((cio_L_A_B(ef, b, a)) ? 1.0d : 0.0d) / (1 + Math.log(cio_L_A_n(ef, a)));
        }

        double d = 1.0 / (1 + i + j);
        return d;
    }

    protected static double passantC(Film a, Film b) {
        double denom = cd_n_A_B(a, b) + cd_n_A_B(b, a);
        denom += cio_n_A_B.get(new Coppia(a, b)) + cii_n_A_B.get(new Coppia(a, b));
        double d = 1.0 / (1 + denom);
        return d;
    }

    protected static double passantCW(Film a, Film b) {
        double den1 = 0;
        double den2 = 0;
        double den3 = 0;
        double den4 = 0;
        Collection<EdgeFilm> coll = FilmGraph.getGraph().getEdges();

        for (EdgeFilm ef : coll) {
            den1 += ((cd_L_A_B(ef, a, b)) ? 1 : 0) / (1 + Math.log(cd_L_A_n(ef, a)));
            den2 += ((cd_L_A_B(ef, b, a)) ? 1 : 0) / (1 + Math.log(cd_L_A_n(ef, b)));
            den3 += ((cii_L_A_B(ef, a, b)) ? 1 : 0) / (1 + Math.log(cii_L_A_n(ef, a)));
            den4 += ((cio_L_A_B(ef, b, a)) ? 1 : 0) / (1 + Math.log(cio_L_A_n(ef, a)));
        }

        return 1.0 / (1 + den1 + den2 + den3 + den4);
    }

    //vero se c'è arco L tra A e B
    private static boolean cd_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> coll = FilmGraph.getGraph().findEdgeSet(a, b);
        for (EdgeFilm ef : coll)
            if (ef.getLabelModified().equals(l.getLabelModified()))
                return true;
        return false;
    }

    //numero di archi tra A e B
    private static int cd_n_A_B(Film a, Film b) {
        return FilmGraph.getGraph().findEdgeSet(a, b).size();
    }

    //numero di risorse con arco L entrante, proveniente da A
    private static int cd_L_A_n(EdgeFilm l, Film a) {
        Collection<EdgeFilm> coll = FilmGraph.getGraph().getOutEdges(a);
        HashSet<Film> hs = new HashSet<Film>();

        for (EdgeFilm ef : coll) {
            if (ef.getLabelModified().equals(l.getLabelModified())) {
                hs.add(ef.getObject());
            }
        }
        return hs.size();
    }

    //vero se esiste C tale che A->C e B->C con archi tutti L
    private static boolean cio_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getOutEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getOutEdges(b);
        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getSubject().equals(efB.getSubject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        if (efA.getLabelModified().equals(l.getLabelModified()))
                            return true;
        return false;
    }

    //vero se esiste C tale che C->A e C->B con archi tutti L
    private static boolean cii_L_A_B(EdgeFilm l, Film a, Film b) {
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getInEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getInEdges(b);
        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getObject().equals(efB.getObject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        if (efA.getLabelModified().equals(l.getLabelModified()))
                            return true;
        return false;
    }

    //Numero di archi L tale che esiste C tale che A->C e B->C con archi tutti L
    protected static int cio_n_A_B(Film a, Film b) {
        int i = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getOutEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getOutEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getObject().equals(efB.getObject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        i++;
        return i;
    }

    //Numero di archi L tale che esiste C tale che C->A e C->B con archi tutti L
    protected static int cii_n_A_B(Film a, Film b) {
        int i = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getInEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getInEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getSubject().equals(efB.getSubject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        i++;
        return i;
    }

    //Numero di risorse n tale che Cio_L_A_n = true
    private static int cio_L_A_n(EdgeFilm l, Film a) {
        int i = 0;
        Collection<Film> coll = FilmGraph.getGraph().getVertices();
        for (Film f : coll)
            if (!f.equals(a))
                if (cio_L_A_B(l, a, f))
                    i++;
        return i;
    }

    //Numero di risorse n tale che Cii_L_A_n = true
    private static int cii_L_A_n(EdgeFilm l, Film a) {
        int i = 0;
        Collection<Film> coll = FilmGraph.getGraph().getVertices();
        for (Film f : coll)
            if (!f.equals(a))
                if (cii_L_A_B(l, a, f))
                    i++;
        return i;
    }

    public static Map<Coppia, Integer> getCii_n_A_B() {
        return cii_n_A_B;
    }

    public static Map<Coppia, Integer> getCio_n_A_B() {
        return cio_n_A_B;
    }

    public static Map<Coppia, Double> getNostra() {
        return nostra;
    }

    public static Map<Coppia, Double> getPassantCW() {
        return passantCW;
    }

    public static Map<Coppia, Double> getPassantC() {
        return passantC;
    }

    public static Map<Coppia, Double> getPassantIW() {
        return passantIW;
    }

    public static Map<Coppia, Double> getPassantI() {
        return passantI;
    }

    public static Map<Coppia, Double> getPassantDW() {
        return passantDW;
    }

    public static Map<Coppia, Double> getPassantD() {
        return passantD;
    }
}
