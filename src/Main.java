import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main {
   // public static final String ENDPOINT = "http://sparql.freebase.com";
   // public static final String ENDPOINT = "http://dbpedia.org/sparql";

   public static final String ENDPOINT = "http://data.linkedmdb.org/sparql";
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

   public static void main(String[] args) throws IOException {
      BasicConfigurator.configure();
      FileOutputStream fout = new FileOutputStream("./OUT");
      FileOutputStream fdebug = new FileOutputStream("./DEBUG");

      out = new PrintWriter(fout);
      debug = new PrintWriter(fdebug);
      InputStream in = FileManager.get().open("./temp.nt");
      if (in == null) {
         throw new IllegalArgumentException("File: linkedmdb not found");
      }

      graph = new UndirectedSparseGraph<Entita, Predicato>();

      Model model = ModelFactory.createDefaultModel();
      System.out.println(" Inizio " + new Date() + "\n");
      System.out.println("Sto per fare il read ");
      // model.read(in, null, "RDF/XML");
      model.read(in, null, "N-TRIPLE");

      // film = new ArrayList<Entita>(85700);
      // actor = new ArrayList<Entita>(55000);
      // movieactor = new ArrayList<Entita, Entita>(60000);

      graph.addVertex(new Entita(LIMDBMOVIE.filmRes.getURI()));
      graph.addVertex(new Entita(LIMDBMOVIE.actorRes.getURI()));
      ResIterator it;

      it = model.listResourcesWithProperty(RDF.type, LIMDBMOVIE.filmRes);
      while (it.hasNext()) {
         Resource r = it.nextResource();
         graph.addVertex(new Entita(r.getURI()));
         //System.out.println("graph.addVertex film " + r.getURI() + " "+ LIMDBMOVIE.filmRes.getURI());
      }
      System.out.println("filmRes " + new Date() + "\n");
      it = model.listResourcesWithProperty(RDF.type, LIMDBMOVIE.actorRes);
      while (it.hasNext()) {
         Resource r = it.nextResource();
         graph.addVertex(new Entita(r.getURI()));
         //System.out.println("graph.addVertex actor " + r.getURI());
      }
      System.out.println("actorRes " + new Date() + "\n");
      StmtIterator iter = model.listStatements(new SimpleSelector(null, LIMDBMOVIE.actor, (Resource) null));

      while (iter.hasNext()) {
         Statement s = iter.nextStatement();
         Entita e1 = new Entita(s.getSubject().getURI());
         Entita e2 = new Entita(((Resource) s.getObject()).getURI());
         Predicato p = new Predicato(LIMDBMOVIE.actor.getURI(), 0.5);
         graph.addEdge(p, e1, e2);
         //System.out.println("graph.addEdge " + p + " "+ e1 + " "+ e2);
      }
      System.out.println("addEdge " + new Date() + "\n");
      // debug.println(graph);

      Entita titanic = new Entita("http://data.linkedmdb.org/resource/film/72");
      Entita shutter = new Entita("http://data.linkedmdb.org/resource/film/51653");
      // Entita film1 = new Entita("film1");
      // Entita film2 = new Entita("film2"); 
      System.out.println("Inizio Dijkstra " + new Date() + "\n");
      System.out.println("Sto per avviare DijkstraShortestPath");
      /*UnweightedShortestPath<Entita,Predicato> unw = new UnweightedShortestPath<Entita, Predicato>(graph);
       System.out.println(unw.getDistance(film1, film2));
       */
      DijkstraShortestPath<Entita, Predicato> sp = new DijkstraShortestPath<Entita, Predicato>(graph);
      System.out.println("DijkstraShortestPath " + new Date() + "\n");
      System.out.println("Sto per avviare getPath");
      out.println(sp.getDistance(titanic, shutter));
      //out.println(sp.getPath(film1, film2));
      List<Predicato> path = sp.getPath(titanic, shutter);

      for (int i = 0; i < path.size(); i++) {
         out.println(path.get(i));
      }
      System.out.println("Fine " + new Date() + "\n");
      //out.println(distance(titanic, shutter));

      // for (int i = 0; i < film.size(); i++) {
      // out.println(film.get(i));
      // }
      //
      // for (int i = 0; i < actor.size(); i++) {
      // out.println(actor.get(i));
      // }

      // model.write(out);

      // Main.loadMovies();
      // Main.loadActors();
      // Main.loadMovieActor();
      // Main.loadAll();

      // int i = 1;
      // StmtIterator it = model.listStatements();
      // while (it.hasNext()) {
      // Resource sub = it.next().getSubject();
      // Property pre = it.next().getPredicate();
      // RDFNode obj = it.next().getObject();
      // out.print(i + " ");
      // out.print("<" + sub + "> ");
      // out.print("<" + pre + "> ");
      // out.println("<" + obj + "> .");
      // i++;
      // }

      in.close();
      out.close();
      debug.close();
   }

   public static double distance(Entita e1, Entita e2) {
      // double d = 0;
      // LinkedHashMap<Entita, Number> map = new DijkstraDistance<Entita,
      // Predicato>(graph).getDistanceMap(e1, 2);

      System.out.println("Sto per creare Dijkstra");
      DijkstraDistance<Entita, Predicato> dd = new DijkstraDistance<Entita, Predicato>(graph);

      // out.println(map);
      return dd.getDistance(e1, e2).doubleValue();
   }

   private static void loadMovies() {
      String query = "";
      query += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
      query += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";
      query += "SELECT ?movie ";
      query += "WHERE { ";
      query += " ?movie rdf:type movie:film . ";
      query += " }";

      System.out.println(query);
      Query q = QueryFactory.create(query);

      QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, q);
      ResultSet rs = qexec.execSelect();

      while (rs.hasNext()) {
         QuerySolution qs = rs.next();
         Resource sub = qs.getResource("movie");
         Property prop = RDF.type;
         RDFNode obj = (Resource) LIMDBMOVIE.filmRes;
         model.add(sub, prop, obj);
      }
      qexec.close();
   }

   private static void loadMoviesByTitle(String title, Model model) {
      String query = "";
      query += "PREFIX foaf: <http://xmlns.com/foaf/0.1/>";
      query += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>";
      query += "SELECT ?film_resource ";
   
      query += "WHERE { ";
      query += " ?film_resource rdf:type <http://dbpedia.org/ontology/Film> .";
      query += " ?film_resource foaf:name ?film_title .";
      query += " FILTER contains(?film_title, \"" + title + "\")";
      query += " }";

      System.out.println(query);
      Query q = QueryFactory.create(query);

      QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, q);
      ResultSet rs = qexec.execSelect();

      while (rs.hasNext()) {
         QuerySolution qs = rs.next();
         Resource sub = qs.getResource("film_resource");
         Property prop = RDF.type;
         RDFNode obj = (Resource) LIMDBMOVIE.filmRes;
         model.add(sub, prop, obj);
      }
      qexec.close();
   }

   private static void loadActors() {
      String query = "";
      query += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
      query += "PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";
      query += "PREFIX db: <http://data.linkedmdb.org/resource/> ";
      query += "PREFIX dbpedia: <http://dbpedia.org/property/> ";
      query += "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> ";
      query += "PREFIX dc: <http://purl.org/dc/terms/> ";
      query += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";

      query += "SELECT ?actor ";
      query += "WHERE { ";
      query += " ?actor rdf:type movie:actor . ";
      query += " } LIMIT 100";

      System.out.println(query);
      Query q = QueryFactory.create(query);

      QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, q);
      ResultSet rs = qexec.execSelect();

      while (rs.hasNext()) {
         QuerySolution qs = rs.next();
         Resource sub = qs.getResource("actor");
         Property prop = RDF.type;
         RDFNode obj = (Resource) LIMDBMOVIE.actorRes;
         model.add(sub, prop, obj);
      }
      qexec.close();
   }

   private static void loadMovieActor() {
      String query = "";
      query += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
      query += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";

      query += "SELECT ?movie ?actor ";
      query += "WHERE { ";
      query += " ?movie rdf:type movie:film . ";
      query += " ?actor rdf:type movie:actor . ";
      query += " ?movie movie:actor ?actor . ";
      query += " }";

      System.out.println(query);
      Query q = QueryFactory.create(query);

      QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, q);
      ResultSet rs = qexec.execSelect();

      while (rs.hasNext()) {
         QuerySolution qs = rs.next();
         Resource sub = qs.getResource("movie");
         Property prop = LIMDBMOVIE.actor;
         Resource obj = qs.getResource("actor");
         model.add(sub, prop, obj);
      }
      qexec.close();
   }

   private static void loadAll() {
      String query = "";
      query += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
      query += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";

      query += "SELECT ?a ?b ?c ";
      query += "WHERE { ";
      query += " ?a ?b ?c .";
      query += " ?a rdf:type movie:film .";
      query += " } LIMIT 50000";

      System.out.println(query);
      Query q = QueryFactory.create(query);

      QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, q);
      ResultSet rs = qexec.execSelect();

      while (rs.hasNext()) {
         QuerySolution qs = rs.next();
         String uri = qs.getResource("b").getURI();
         Property prop = model.createProperty(uri);
         Resource sub = qs.getResource("a");
         RDFNode obj = qs.get("c");
         debug.println("<" + sub + "> <" + uri + "> <" + obj + "> .");
         // out.println(LIMDBMOVIE.actor.getURI());
         if ((LIMDBMOVIE.actor.getURI().equals(uri)) || (LIMDBMOVIE.director.getURI().equals(uri)) || (LIMDBMOVIE.prequel.getURI().equals(uri)) || (LIMDBMOVIE.sequel.getURI().equals(uri))
                 || (LIMDBMOVIE.film_subject.getURI().equals(uri))) {
            model.add(sub, prop, obj);
         }
      }
      qexec.close();
   }
}