import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Resource;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Grafo {

    private static final String FILEFILMPROP = "./film_properties_dbpedia.txt";
    private static final String FILEFILM = "./film_test.txt";
    private static final String ENDPOINT = "http://dbpedia.org/sparql";
    // public static final String ENDPOINT = "http://sparql.freebase.com";
    // public static final String ENDPOINT = "http://live.dbpedia.org/sparql";
    private static Logger logger = Logger.getLogger(Main.class);
    private static ArrayList<Film> films;
    private static ArrayList<Property> properties;
    private static UndirectedSparseMultigraph<Risorsa, Edge> graph;

    private static ArrayList<Resource> querySPARQL(String uri, String property) {
        String query = "";

        query += "select ?object ";
        query += "WHERE { ";
        query += "<" + uri + ">  <" + property + "> ?object .";
        query += " }";

        //System.err.println(query);
        Query q = QueryFactory.create(query);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, q);
        ResultSet rs = qexec.execSelect();
        ArrayList<Resource> sub = new ArrayList<Resource>();
        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            try {
                sub.add(qs.getResource("object"));
            } catch (Exception e) {
            }
        }
        qexec.close();
        return sub;
    }

    public static void load() throws IOException, ClassNotFoundException, InterruptedException {
        films = Film.readFromFile(FILEFILM);
        properties = Property.readFromFile(FILEFILMPROP);
        try {
            FileInputStream fis = new FileInputStream("./serialized/graphComplete.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            graph = (UndirectedSparseMultigraph<Risorsa, Edge>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Grafo Caricato.");
            System.out.println("Vertici : " + graph.getVertices().size());
            System.out.println("Archi : " + graph.getEdges().size());
        } catch (FileNotFoundException e) {
            graph = new UndirectedSparseMultigraph<Risorsa, Edge>();
            createFromQuery();
            System.out.println("Grafo inizializzato.");
        }
    }

    public static void updateWeight() {
        Collection<Edge> edge = graph.getEdges();
        Collection<Edge> edgeDel = new ArrayList<Edge>();
        Collection<Edge> edgeNew = new ArrayList<Edge>();
        for (Edge e1 : edge) {
            for (int i = 0; i < properties.size(); i++)
                if (e1.getProperty().getIdProperty() == properties.get(i).getIdProperty())
                    if (e1.getWeight() != properties.get(i).getWeight()) {
                        edgeDel.add(e1);
                        e1.setWeight(properties.get(i).getWeight());
                        edgeNew.add(e1);
                    }
        }

        for (Edge e2 : edgeDel) {
            graph.removeEdge(e2);
        }


        for (Edge e2 : edgeNew) {
            graph.addEdge(e2, e2.getSubject(), e2.getObject());
        }
    }

    public static void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("./serialized/graphComplete.bin");
        ObjectOutputStream o = new ObjectOutputStream(fos);
        o.writeObject(graph);
        o.close();
        fos.close();
    }

    public static void printDot() throws IOException {
        FileOutputStream fout = new FileOutputStream("./dot/graphComplete.dot");
        PrintWriter out = new PrintWriter(fout);
        out.println("graph dbpedia {");


        for (Film f : films)
            out.println("\"" + f.getIdMovieLens() + "\" [shape=box];");

        out.println();


        Collection<Edge> edges = graph.getEdges();

        for (Edge e : edges) {
            String s = "\"" + e.getSubject().getIdMovieLens() + "\" -- \"" + e.getObject().getTitle();
            s += "\" [weight=" + e.getWeight() + "; label=\"" + e.getProperty().getIdProperty() + "\"];";
            out.println(s);
        }

        out.println("}");
        out.close();
        fout.close();
    }

    public static void createFromQuery() throws IOException, InterruptedException {
//        graph = new UndirectedSparseMultigraph<Risorsa, Edge>();
        BasicConfigurator.configure();
        for (int i = 0; i < films.size(); i++) {
            graph.addVertex(films.get(i));
            for (int j = 0; j < properties.size(); j++) {
                Thread.sleep(250);
                ArrayList<Resource> resourceDest = querySPARQL(films.get(i).getUri(), properties.get(j).getUri());
                for (int t = 0; t < resourceDest.size(); t++) {
                    Risorsa risorsaFilmDest = new Risorsa(resourceDest.get(t).getURI());

                    if (!resourceDest.get(t).getLocalName().isEmpty()) {
                        Property property = properties.get(j);
                        Edge prop = new Edge(property, films.get(i), risorsaFilmDest, property.getWeight());
                        graph.addVertex(risorsaFilmDest);
                        graph.addEdge(prop, films.get(i), risorsaFilmDest);
                    }
                }
            }
            System.out.println("film " + i + "  iniziato " + new Date() + " : " + films.get(i).getTitle());
        }
    }

    public static ArrayList<Film> getFilms() {
        return films;
    }

    public static ArrayList<Property> getProperties() {
        return properties;
    }

    public static UndirectedSparseMultigraph<Risorsa, Edge> getGraph() {
        return graph;
    }
}
