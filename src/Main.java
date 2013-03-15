import java.io.IOException;
import java.util.ArrayList;

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

        Distance dl = new Distance(FilmGraph.getGraph());
        Distance d = new Distance(FilmGraph.getGraph());
        ArrayList<Film> films = Grafo.getFilms();

        for (int i = 0; i < films.size(); i++) {
            for (int j = i + 1; j < films.size(); j++) {
                //double val = d.ldsd(films.get(i), films.get(j));
                //System.out.println("ldsd tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle() + " : " + val);
                //val = d.ldsdWeighted(films.get(i), films.get(j));
                System.out.println();
                System.out.println("----> Tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle());
                System.out.println("Directed " + dl.passantD(films.get(i), films.get(j)));
                System.out.println("DirectedW " + dl.passantDW(films.get(i), films.get(j)));
                System.out.println("Indirected " + dl.passantI(films.get(i), films.get(j)));
                System.out.println("IndirectedW " + dl.passantIW(films.get(i), films.get(j)));
                System.out.println("Combined " + dl.passantC(films.get(i), films.get(j)));
                System.out.println("CombinedW " + dl.passantCW(films.get(i), films.get(j)));
            }

        }

    }
}
