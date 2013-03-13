import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Grafo.init();
        //Grafo.createFromQuery();
        //Grafo.save();
        Grafo.load();
        Grafo.printDot();

        FilmGraph.init();
        FilmGraph.printDot();


        //Distance d = new Distance(g);
    }
}
