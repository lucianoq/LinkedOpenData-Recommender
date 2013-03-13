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

    private static UndirectedSparseMultigraph<Film, EdgeFilm> filmGraph;

    public static void init() {
        filmGraph = new UndirectedSparseMultigraph<Film, EdgeFilm>();
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
                        EdgeFilm edgeFilm = new EdgeFilm(label, e1.getSubject(), e2.getSubject());
                        filmGraph.addEdge(edgeFilm, e1.getSubject(), e2.getSubject());
                    }
    }

    public static void printDot() throws IOException {
        FileOutputStream fout = new FileOutputStream("./filmGraph.dot");
        PrintWriter out = new PrintWriter(fout);
        out.println("graph dbpedia {");

        Collection<Film> films = Grafo.getFilms();

        for (Film f : films)
            out.println("\"" + f.getIdMovieLens() + "\" [shape=box];");

        out.println();

        Collection<EdgeFilm> edges = filmGraph.getEdges();

        for (EdgeFilm e : edges)
            out.println("\"" + e.getSubject().getIdMovieLens() + "\" -- \"" + e.getObject().getIdMovieLens() + "\" [label=\"" + e.getLabel() + "\"];");

        out.println("}");
        out.close();
        fout.close();
    }
}