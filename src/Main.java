
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main {

    // public static final String ENDPOINT = "http://sparql.freebase.com";
    public static final String ENDPOINT = "http://dbpedia.org/sparql";
    //public static final String ENDPOINT = "http://data.linkedmdb.org/sparql";
    // public static final String ENDPOINT = "http://live.dbpedia.org/sparql";
    // public static final String DATASET = "en/wikipedia_links_en";
    public static Logger logger = Logger.getLogger(Main.class);
    public static Model model;
    public static ArrayList<Entita> film;
    public static ArrayList<Entita> actor;
    public static ArrayList<Entita> movieactor;
    // public static ArrayList<Resource> director;
    public static PrintWriter out;
    public static PrintWriter debug;
    public static Graph<Entita, Predicato> graph;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BasicConfigurator.configure();

        Model model = ModelFactory.createDefaultModel();
        ArrayList<FilmType> films = leggifilmDaFile("./film_test.txt");
        ArrayList<FilmProperties> filmProperties = leggiPropertiesDaFile("./film_properties_dbpedia.txt");

//        FileOutputStream fout = new FileOutputStream("./OutGraph.dot");
//        out = new PrintWriter(fout);
//        out.println("graph dbpedia {");
        //creagrafo(films, filmProperties);

//        FileOutputStream fos = new FileOutputStream("./graph.tmp");
//        ObjectOutputStream o = new ObjectOutputStream(fos);
//        o.writeObject(graph);
//        o.close();
//        fos.close();

//        out.println("}");
//        out.close();

        FileInputStream fis = new FileInputStream("./graph.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);

        graph = (UndirectedSparseMultigraph<Entita, Predicato>) ois.readObject();
        //Distance d = new Distance(graph);

        //  g.printDOT();
    }

    private static void distances() {
        DijkstraShortestPath<Entita, Predicato> sp = new DijkstraShortestPath<Entita, Predicato>(graph);
        System.out.println("DijkstraShortestPath " + new Date() + "\n");
        System.out.println("Sto per avviare getPath");
        Entita star_Trek_First_Contact = new Entita("http://dbpedia.org/resource/Star_Trek:_First_Contact");
        Entita star_Trek_VI_The_Undiscovered_Country = new Entita("http://dbpedia.org/resource/Star_Trek_VI:_The_Undiscovered_Country");

        System.out.println(sp.getDistance(star_Trek_First_Contact, star_Trek_VI_The_Undiscovered_Country));
        //out.println(sp.getPath(film1, film2));
        List<Predicato> path = sp.getPath(star_Trek_First_Contact, star_Trek_VI_The_Undiscovered_Country);

        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i));
        }

        Entita american_films = new Entita("http://dbpedia.org/resource/Category:American_films");
        Entita donald = new Entita("http://dbpedia.org/resource/Donald_Peterman");

        System.out.println(sp.getDistance(american_films, donald));
        //out.println(sp.getPath(film1, film2));
        List<Predicato> path2 = sp.getPath(american_films, donald);

        for (int i = 0; i < path2.size(); i++) {
            System.out.println(path2.get(i));
        }
    }

    private static ArrayList<FilmType> leggifilmDaFile(String path) throws IOException {
        BufferedReader inp = new BufferedReader(new FileReader(path));
        ArrayList<FilmType> film = new ArrayList<FilmType>();
        String tmp;
        String[] tmp1;
        while ((tmp = inp.readLine()) != null) {
            tmp1 = tmp.split("\t");
            FilmType tempFilm = new FilmType(Integer.parseInt(tmp1[0]), tmp1[1], tmp1[2]);
            film.add(tempFilm);
        }
        inp.close();
        return film;
    }

    private static ArrayList<FilmProperties> leggiPropertiesDaFile(String path) throws IOException {
        BufferedReader inp = new BufferedReader(new FileReader(path));
        ArrayList<FilmProperties> filmProperties = new ArrayList<FilmProperties>();
        String tmp;
        String[] tmp1;
        while ((tmp = inp.readLine()) != null) {
            tmp1 = tmp.split("\t");
            FilmProperties tempFilmProp = new FilmProperties(tmp1[0], tmp1[1]);
            filmProperties.add(tempFilmProp);
        }
        inp.close();
        return filmProperties;
    }

    private static ArrayList<Resource> querySPARQL(String uri, String property) {
        String query = "";

        //select ?object 
        //where {<http://dbpedia.org/resource/Star_Trek:_First_Contact> <http://dbpedia.org/property/producer> ?object .} 
        //
        query += "select ?object ";
        query += "WHERE { ";
        query += "<" + uri + ">  <" + property + "> ?object .";
        query += " }";

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

    private static void creagrafo(ArrayList<FilmType> film, ArrayList<FilmProperties> filmProperties) throws FileNotFoundException {
        graph = new UndirectedSparseMultigraph<Entita, Predicato>();
        for (int i = 0; i < film.size(); i++) {
            Entita entityFilmSrc = new Entita(film.get(i).getUri());
            graph.addVertex(entityFilmSrc);

            // Scrittura file.dot
            out.println(film.get(i).getTitle().replace(" ", "_") + "[shape=box];");
            String fileDot = "";

            for (int j = 0; j < filmProperties.size(); j++) {
                ArrayList<Resource> resourceDest = querySPARQL(film.get(i).getUri(), filmProperties.get(j).get_uri());
                for (int t = 0; t < resourceDest.size(); t++) {
                    Entita entityFilmDest = new Entita(resourceDest.get(t).getURI());

                    if (!resourceDest.get(t).getLocalName().replace(".", "_").replace("-", "_").replace(" ", "_").isEmpty()) {

                        // Scrittura file.dot
                        String fileDotTmp = film.get(i).getTitle().replace(" ", "_").replace(".", "_") + " -- " + resourceDest.get(t).getLocalName().replace(".", "_").replace("-", "_").replace(" ", "_") + " [label=\"" + filmProperties.get(j).get_title() + "\"];";

                        if (!fileDot.contains(fileDotTmp)) {

                            Predicato predicate = new Predicato(filmProperties.get(j).get_uri(), entityFilmSrc.toString(), entityFilmDest.toString());
                            graph.addVertex(entityFilmDest);
                            graph.addEdge(predicate, entityFilmSrc, entityFilmDest);

                            // Scrittura file.dot
                            fileDot = fileDot + fileDotTmp + "\n";
                        }
                    }
                }
            }
            out.println(fileDot);
        }
    }
}
