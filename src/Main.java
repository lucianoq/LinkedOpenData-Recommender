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

        Distance d = new Distance(FilmGraph.getGraph());
        ArrayList<Film> films = Grafo.getFilms();

        for (Film f: films)
            System.out.println(f);

//        for (int i = 0; i < films.size(); i++) {
//            for (int j = i + 1; j < films.size(); j++) {
        int i = 1;
        int j = 7;
                double val = d.ldsd(films.get(i), films.get(j));
                System.out.println("ldsd tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle() + " : " + val);
                val = d.ldsdWeighted(films.get(i), films.get(j));
                System.out.println("ldsdWeighted tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle() + " : " + val);
                val = d.ldsdIndirect(films.get(i), films.get(j));
                System.out.println("ldsdIndirect" +
                        " tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle() + " : " + val);
//            }
//
//        }

    }
}
