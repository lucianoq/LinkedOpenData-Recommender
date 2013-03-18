import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 18/03/13
 * Time: 14.34
 */

public class ComputeDistance extends Thread {

    private ArrayList<Film> subset;

    public ComputeDistance() {
        this.subset = new ArrayList<Film>();
    }

    public ArrayList<Film> getSubset() {
        return subset;
    }

    @Override
    public void run() {
        for (Film f1 : subset) {
            for (Film f2 : Grafo.getFilms())
                if (!f1.equals(f2)) {
                    Distance.getCio_n_A_B().put(new Coppia(f1, f2), Distance.cio_n_A_B(f1, f2));
                    Distance.getCii_n_A_B().put(new Coppia(f1, f2), Distance.cii_n_A_B(f1, f2));
                    Distance.getNostraDW().put(new Coppia(f1, f2), Distance.nostraDW(f1, f2));
                    Distance.getNostraDW().put(new Coppia(f2, f1), Distance.nostraDW(f2, f1));
                    Distance.getNostraIOW().put(new Coppia(f1, f2), Distance.nostraIOW(f1, f2));
                    Distance.getNostraIIW().put(new Coppia(f1, f2), Distance.nostraIIW(f1, f2));
                    Distance.getPassantD().put(new Coppia(f1, f2), Distance.passantD(f1, f2));
                    Distance.getPassantDW().put(new Coppia(f1, f2), Distance.passantDW(f1, f2));
                    Distance.getPassantI().put(new Coppia(f1, f2), Distance.passantI(f1, f2));
                    Distance.getPassantIW().put(new Coppia(f1, f2), Distance.passantIW(f1, f2));
                    Distance.getPassantC().put(new Coppia(f1, f2), Distance.passantC(f1, f2));
                    Distance.getPassantCW().put(new Coppia(f1, f2), Distance.passantCW(f1, f2));
                    Distance.getNostra().put(new Coppia(f1, f2), Distance.nostra(f1, f2));
                }
            System.out.println("[INFO] [" + new Date() + "] Finito il calcolo del film " + f1.getTitle());
        }
    }
}
