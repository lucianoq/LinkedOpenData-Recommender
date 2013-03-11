
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Simone
 */
public class Grafo {

    public static final String FILEFILMPROP = "./film_properties_dbpedia.txt";
    public static final String FILEFILM = "./film_test.txt";
    // public static final String ENDPOINT = "http://sparql.freebase.com";
    public static final String ENDPOINT = "http://dbpedia.org/sparql";
    //public static final String ENDPOINT = "http://data.linkedmdb.org/sparql";
    // public static final String ENDPOINT = "http://live.dbpedia.org/sparql";
    // public static final String DATASET = "en/wikipedia_links_en";
    public static Logger logger = Logger.getLogger(Main.class);
    public static ArrayList<FilmType> films;
    public static ArrayList<FilmProperties> filmProperties;

    public static Graph<Entita, Predicato> leggi() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("./graph.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Graph<Entita, Predicato> g = (UndirectedSparseMultigraph<Entita, Predicato>) ois.readObject();
        ois.close();
        fis.close();
        return g;
    }

    public static void scrivi(Graph<Entita, Predicato> g) throws IOException {
        FileOutputStream fos = new FileOutputStream("./graph.tmp");
        ObjectOutputStream o = new ObjectOutputStream(fos);
        o.writeObject(g);
        o.close();
        fos.close();
    }

    public static void stampaDot(Graph<Entita, Predicato> g) throws FileNotFoundException {
        FileOutputStream fout = new FileOutputStream("./OutGraph.dot");
        PrintWriter out = new PrintWriter(fout);
        out.println("graph dbpedia {");
        Collection<Predicato> collPred = g.getEdges();
        HashSet<String> hs = new HashSet<String>();
        for (Predicato p : collPred) {
            hs.add(p.getSubjectName());
            out.println(p.getSubjectName() + " -- " + p.getObjectName() + " [label=\"" + p.getUriName() + "\"];");
        }
        for (String s : hs) {
            out.println(s + "[shape=box]");
        }
        out.println("}");
        out.close();
    }

    public static Graph<Entita, Predicato> crea() throws IOException {

        filmProperties = leggiPropertiesDaFile(FILEFILMPROP);
        films = leggifilmDaFile(FILEFILM);

        BasicConfigurator.configure();
        Graph<Entita, Predicato> graph = new UndirectedSparseMultigraph<Entita, Predicato>();
        for (int i = 0; i < films.size(); i++) {
            Entita entityFilmSrc = new Entita(films.get(i).getUri());
            graph.addVertex(entityFilmSrc);
            for (int j = 0; j < filmProperties.size(); j++) {
                ArrayList<Resource> resourceDest = querySPARQL(films.get(i).getUri(), filmProperties.get(j).get_uri());
                for (int t = 0; t < resourceDest.size(); t++) {
                    Entita entityFilmDest = new Entita(resourceDest.get(t).getURI());

                    if (!resourceDest.get(t).getLocalName().isEmpty()) {
                        Predicato predicate = new Predicato(filmProperties.get(j).get_uri(), entityFilmSrc.toString(), entityFilmDest.toString());
                        graph.addVertex(entityFilmDest);
                        graph.addEdge(predicate, entityFilmSrc, entityFilmDest);
                    }
                }
            }
        }
        return graph;
    }

//    public static Graph<Entita, Predicato> creall() {
//        BasicConfigurator.configure();
//        Graph<Entita, Predicato> graph = new UndirectedSparseMultigraph<Entita, Predicato>();
//        for (int i = 0; i < films.size(); i++) {
//            Entita entityFilmSrc = new Entita(films.get(i).getUri());
//            graph.addVertex(entityFilmSrc);
//
//            // Scrittura file.dot
//            out.println(films.get(i).getTitle().replace(" ", "_") + "[shape=box];");
//            String fileDot = "";
//
//            for (int j = 0; j < filmProperties.size(); j++) {
//                ArrayList<Resource> resourceDest = querySPARQL(films.get(i).getUri(), filmProperties.get(j).get_uri());
//                for (int t = 0; t < resourceDest.size(); t++) {
//                    Entita entityFilmDest = new Entita(resourceDest.get(t).getURI());
//
//                    if (!resourceDest.get(t).getLocalName().replace(".", "_").replace("-", "_").replace(" ", "_").isEmpty()) {
//
//                        // Scrittura file.dot
//                        String fileDotTmp = films.get(i).getTitle().replace(" ", "_").replace(".", "_") + " -- " + resourceDest.get(t).getLocalName().replace(".", "_").replace("-", "_").replace(" ", "_") + " [label=\"" + filmProperties.get(j).get_title() + "\"];";
//
//                        if (!fileDot.contains(fileDotTmp)) {
//
//                            Predicato predicate = new Predicato(filmProperties.get(j).get_uri(), entityFilmSrc.toString(), entityFilmDest.toString());
//                            graph.addVertex(entityFilmDest);
//                            graph.addEdge(predicate, entityFilmSrc, entityFilmDest);
//
//                            // Scrittura file.dot
//                            fileDot = fileDot + fileDotTmp + "\n";
//                        }
//                    }
//                }
//            }
//            out.println(fileDot);
//
//        }
//        return graph;
//    }
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
}
