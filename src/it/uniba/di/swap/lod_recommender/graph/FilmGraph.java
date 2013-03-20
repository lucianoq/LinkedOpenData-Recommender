package it.uniba.di.swap.lod_recommender.graph;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import it.uniba.di.swap.lod_recommender.Film;
import it.uniba.di.swap.lod_recommender.Risorsa;

import java.io.*;
import java.util.Collection;

public class FilmGraph implements Serializable {

    private static DirectedSparseMultigraph<Film, EdgeFilm> filmGraph;

    public static void init() {
        filmGraph = new DirectedSparseMultigraph<Film, EdgeFilm>();
        UndirectedSparseMultigraph<Risorsa, Edge> g = Graph.getGraph();

        Collection<Edge> edges = g.getEdges();

        Collection<Film> films = Graph.getFilms();
        for (Film f : films)
            filmGraph.addVertex(f);

        for (Edge e1 : edges)
            for (Edge e2 : edges)
                if (e1.getObject().equals(e2.getObject()))
                    if (!e1.getSubject().equals(e2.getSubject())) {
                        String label = e1.getTitle() + " " + e1.getObject().getTitle() + " " + e2.getTitle();
                        double newWeight = e1.getWeight()*e2.getWeight();
                        EdgeFilm edgeFilm = new EdgeFilm(label, e1.getSubject(), e2.getSubject(), newWeight);
                        filmGraph.addEdge(edgeFilm, e1.getSubject(), e2.getSubject());
                        label = e2.getTitle() + " " + e1.getObject().getTitle() + " " + e1.getTitle();
                        edgeFilm = new EdgeFilm(label, e2.getSubject(), e1.getSubject(), newWeight);
                        filmGraph.addEdge(edgeFilm, e2.getSubject(), e1.getSubject());
                    }
    }

    public static void printDot() throws IOException {
        FileOutputStream fout = new FileOutputStream("./dot/filmGraph.dot");
        PrintWriter out = new PrintWriter(fout);
        out.println("digraph dbpedia {");

        Collection<Film> films = Graph.getFilms();

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

    public static void load()throws IOException,ClassNotFoundException{
        try {
            FileInputStream fis = new FileInputStream("./serialized/filmGraph.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            filmGraph = (DirectedSparseMultigraph<Film, EdgeFilm>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("[INFO] it.uniba.di.swap.lod_recommender.Film graph loaded.");
            System.out.println("[INFO] it.uniba.di.swap.lod_recommender.Film graph Vertices : " + filmGraph.getVertices().size());
            System.out.println("[INFO] it.uniba.di.swap.lod_recommender.Film graphEdges : " + filmGraph.getEdges().size());
            System.out.println("----------------------------------------------------");
        } catch (FileNotFoundException e) {
            init();
            save();
            System.out.println("[INFO] it.uniba.di.swap.lod_recommender.Film graph builded.");
            System.out.println("[INFO] it.uniba.di.swap.lod_recommender.Film graph Vertices : " + filmGraph.getVertices().size());
            System.out.println("[INFO] it.uniba.di.swap.lod_recommender.Film graphEdges : " + filmGraph.getEdges().size());
            System.out.println("----------------------------------------------------");
        }
    }

    public static void save() throws IOException {
        new File("./serialized").mkdirs();
        FileOutputStream fos = new FileOutputStream("./serialized/filmGraph.bin");
        ObjectOutputStream o = new ObjectOutputStream(fos);
        o.writeObject(filmGraph);
        o.close();
        fos.close();
    }

    public static DirectedSparseMultigraph<Film, EdgeFilm> getGraph() {
        return filmGraph;
    }

}