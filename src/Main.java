import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Grafo.init();
        //Grafo.createFromQuery();
        //Grafo.save();
        Grafo.load();
        Grafo.printDot();

        FilmGraph.init();
        FilmGraph.printDot();

//        System.out.println(Grafo.getFilms().get(0));
//        System.out.println(Grafo.getFilms().get(1));
//        int b =  (Grafo.getFilms().get(0).compareTo(Grafo.getFilms().get(1)));
//        System.out.println(b);

        DistanceLuciano dl = new DistanceLuciano(FilmGraph.getGraph());
        Distance d = new Distance(FilmGraph.getGraph());
        ArrayList<Film> films = Grafo.getFilms();

        for (int i = 0; i < 1; i++) {
            for (int j = i + 1; j < i + 2; j++) {
                //double val = d.ldsd(films.get(i), films.get(j));
                //System.out.println("ldsd tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle() + " : " + val);
                //val = d.ldsdWeighted(films.get(i), films.get(j));
                System.out.println();
                System.out.println("----> Tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle());
                System.out.println("Directed Luciano " + dl.passantD(films.get(i), films.get(j)));
                System.out.println("Directed Simone " + d.ldsd(films.get(i), films.get(j)));
                System.out.println("---");
                System.out.println("DirectedW Luciano " + dl.passantDW(films.get(i), films.get(j)));
                System.out.println("DirectedW Simone " + d.ldsdWeighted(films.get(i), films.get(j)));
                System.out.println("---");
                System.out.println("Indirected Luciano " + dl.passantI(films.get(i), films.get(j)));
                System.out.println("Indirected Simone " + d.ldsdIndirect(films.get(i), films.get(j)));
                System.out.println("---");
                System.out.println("IndirectedW Luciano " + dl.passantIW(films.get(i), films.get(j)));
                System.out.println("IndirectedW Simone " + d.ldsdIndirectWeight(films.get(i), films.get(j)));
                System.out.println("---");
                System.out.println("Combined Luciano " + dl.passantC(films.get(i), films.get(j)));
                System.out.println("Combined Simone " + d.combined(films.get(i), films.get(j)));
                System.out.println("---");
                System.out.println("CombinedW Luciano " + dl.passantCW(films.get(i), films.get(j)));
                System.out.println("CombinedW Simone " + d.combinedWeighted(films.get(i), films.get(j)));
            }

        }

    }
}
