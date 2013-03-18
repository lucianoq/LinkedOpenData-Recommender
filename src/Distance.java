import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Distance implements Serializable {
    //    public static final int NUM_COPPIE_FILM = 270000;
    public static final int NUM_COPPIE_FILM = 2500;
    private static Map<Coppia, Double> passantD;
    private static Map<Coppia, Double> passantDW;
    private static Map<Coppia, Double> passantI;
    private static Map<Coppia, Double> passantIW;
    private static Map<Coppia, Double> passantC;
    private static Map<Coppia, Double> passantCW;
    private static Map<Coppia, Double> nostra;
    private static Map<Coppia, Double> nostraDW;
    private static Map<Coppia, Double> nostraIOW;
    private static Map<Coppia, Double> nostraIIW;
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
        nostraDW = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        nostraIOW = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);
        nostraIIW = new ConcurrentHashMap<Coppia, Double>(NUM_COPPIE_FILM);


        ArrayList<Film> films = Grafo.getFilms();
        ArrayList<ComputeDistance> threads = new ArrayList<ComputeDistance>();

        for (int i = 0; i < 8; i++) {
            threads.add(new ComputeDistance());
        }

        for (int i = 0; i < films.size(); i++) {
            threads.get(i % 8).getSubset().add(films.get(i));
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

    public static void load() throws IOException, ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream("./serialized/cionab.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            cio_n_A_B = (ConcurrentHashMap<Coppia, Integer>) ois.readObject();
            ois.close();
            fis.close();


            fis = new FileInputStream("./serialized/ciinab.bin");
            ois = new ObjectInputStream(fis);
            cii_n_A_B = (ConcurrentHashMap<Coppia, Integer>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/nostraDW.bin");
            ois = new ObjectInputStream(fis);
            nostraDW = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/nostraIOW.bin");
            ois = new ObjectInputStream(fis);
            nostraIOW = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/nostraIIW.bin");
            ois = new ObjectInputStream(fis);
            nostraIIW = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/passantD.bin");
            ois = new ObjectInputStream(fis);
            passantD = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/passantDW.bin");
            ois = new ObjectInputStream(fis);
            passantDW = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/passantI.bin");
            ois = new ObjectInputStream(fis);
            passantI = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/passantIW.bin");
            ois = new ObjectInputStream(fis);
            passantIW = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/passantC.bin");
            ois = new ObjectInputStream(fis);
            passantC = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/passantCW.bin");
            ois = new ObjectInputStream(fis);
            passantCW = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

            fis = new FileInputStream("./serialized/nostra.bin");
            ois = new ObjectInputStream(fis);
            nostra = (ConcurrentHashMap<Coppia, Double>) ois.readObject();
            ois.close();
            fis.close();

        } catch (FileNotFoundException e) {
            fill();
        } catch (InvalidClassException e) {
            fill();
        }
    }

    public static void save() throws IOException {
        new File("./serialized").mkdirs();
        FileOutputStream fos = new FileOutputStream("./serialized/passantD.bin");
        ObjectOutputStream o = new ObjectOutputStream(fos);
        o.writeObject(passantD);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/passantDW.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(passantDW);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/passantI.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(passantI);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/passantIW.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(passantIW);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/passantC.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(passantC);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/passantCW.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(passantCW);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/nostra.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(nostra);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/cionab.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(cio_n_A_B);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/ciinab.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(cii_n_A_B);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/nostraDW.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(nostraDW);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/nostraIOW.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(nostraIOW);
        o.close();
        fos.close();

        fos = new FileOutputStream("./serialized/nostraIIW.bin");
        o = new ObjectOutputStream(fos);
        o.writeObject(nostraIIW);
        o.close();
        fos.close();
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

    public static double getDistanceNostraDW(Film f1, Film f2) {
        return nostraDW.get(new Coppia(f1, f2));
    }

    public static double getDistanceNostraIOW(Film f1, Film f2) {
        return nostraIOW.get(new Coppia(f1, f2));
    }

    public static double getDistanceNostraIIW(Film f1, Film f2) {
        return nostraIIW.get(new Coppia(f1, f2));
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

    protected static double nostra(Film a, Film b) {

        double denom = getDistanceNostraDW(a, b) + getDistanceNostraDW(b, a);
        denom += getDistanceNostraIOW(a, b) + getDistanceNostraIIW(a, b);
        double d = 1.0 / (1 + denom);
        return d;
    }

    //sommatoria dei pesi degli archi Diretti Wpesati
    protected static double nostraDW(Film a, Film b) {
        Collection<EdgeFilm> set = FilmGraph.getGraph().findEdgeSet(a, b);
        double ret = 0;
        for (EdgeFilm ef : set)
            ret += ef.getWeight();
        return ret;
    }

    //sommatoria dei pesi degli archi Indiretti Ouscenti Wpesati
    protected static double nostraIOW(Film a, Film b) {
        double d = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getOutEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getOutEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getObject().equals(efB.getObject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        d += efA.getWeight() * efB.getWeight();
        return d;
    }

    //sommatoria dei pesi degli archi Indiretti Ientranti Wpesati
    protected static double nostraIIW(Film a, Film b) {
        double d = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getInEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getInEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getSubject().equals(efB.getSubject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        d += efA.getWeight() * efB.getWeight();
        return d;
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

    public static Map<Coppia, Double> getNostraDW() {
        return nostraDW;
    }

    public static Map<Coppia, Double> getNostraIOW() {
        return nostraIOW;
    }

    public static Map<Coppia, Double> getNostraIIW() {
        return nostraIIW;
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
