
import edu.uci.ics.jung.graph.Graph;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Graph<Entita, Predicato> graph;
        graph = Grafo.crea();        
        Grafo.scrivi(graph);
        //graph = Grafo.leggi();
        Grafo.stampaDot(graph);
        //Distance d = new Distance(graph);

        //  g.printDOT();
    }
}
