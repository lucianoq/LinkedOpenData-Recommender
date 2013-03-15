import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 12/03/13
 * Time: 15.46
 * To change this template use File | Settings | File Templates.
 */
public class FilmGraph {

    private static DirectedSparseMultigraph<Film, EdgeFilm> filmGraph;

    public static void init() {
        filmGraph = new DirectedSparseMultigraph<Film, EdgeFilm>();
        UndirectedSparseMultigraph<Risorsa, Edge> g = Grafo.getGraph();

        Collection<Edge> edges = g.getEdges();

        Collection<Film> films = Grafo.getFilms();
        for (Film f : films)
            filmGraph.addVertex(f);

        for (Edge e1 : edges)
            for (Edge e2 : edges)
                if (e1.getObject().equals(e2.getObject()))
                    if (!e1.getSubject().equals(e2.getSubject())) {
                        String label = e1.getTitle() + " " + e1.getObject().title + " " + e2.getTitle();
                        double newWeight = e1.getWeight()*e2.getWeight();
                        EdgeFilm edgeFilm = new EdgeFilm(label, e1.getSubject(), e2.getSubject(), newWeight);
                        filmGraph.addEdge(edgeFilm, e1.getSubject(), e2.getSubject());
                        label = e2.getTitle() + " " + e1.getObject().title + " " + e1.getTitle();
                        edgeFilm = new EdgeFilm(label, e2.getSubject(), e1.getSubject(), newWeight);
                        filmGraph.addEdge(edgeFilm, e2.getSubject(), e1.getSubject());
                    }
    }

    public static void printDot() throws IOException {
        FileOutputStream fout = new FileOutputStream("./filmGraph.dot");
        PrintWriter out = new PrintWriter(fout);
        out.println("digraph dbpedia {");

        Collection<Film> films = Grafo.getFilms();

        for (Film f : films)
            out.println("\"" + f.getIdMovieLens() + "\" [shape=box];");

        out.println();

        Collection<EdgeFilm> edges = filmGraph.getEdges();

        for (EdgeFilm e : edges) {
            String s = "\"" + e.getSubject().getIdMovieLens() + "\" -> \"" + e.getObject().getIdMovieLens();
            s += "\" [weight=" + e.getWeight() + "" +
                    ";label=\"" + e.getLabel() + "\"];";
            out.println(s);
        }

        out.println("}");
        out.close();
        fout.close();
    }

    public static DirectedSparseMultigraph<Film, EdgeFilm> getGraph() {
        return filmGraph;
    }

}