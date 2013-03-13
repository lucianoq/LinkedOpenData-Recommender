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

        Distance d = new Distance(FilmGraph.getGraph());
        ArrayList<Film> films = FilmGraph.getFilms();
        for (int i = 0; i < films.size(); i++) {
            for (int j = i + 1; j < films.size(); j++) {
                double val = d.ldsd(films.get(i), films.get(j));
                System.out.println("ldsd tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle() + " : " + val);
                val = d.ldsdWeighted(films.get(i), films.get(j));
                System.out.println("ldsdWeighted tra : " + films.get(i).getTitle() + " con " + films.get(j).getTitle() + " : " + val);
            }

        }

    }
}
