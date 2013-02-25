import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Resource;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;

public class Main {
	// public static final String ENDPOINT = "http://sparql.freebase.com";
	// public static final String ENDPOINT = "http://dbpedia.org/sparql";
	public static final String	ENDPOINT	= "http://data.linkedmdb.org/sparql";
	// public static final String ENDPOINT = "http://live.dbpedia.org/sparql";
	public static final String	DATASET	= "en/wikipedia_links_en";

	public static Logger			logger	= Logger.getLogger(Main.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();

		Graph<Entity, Property> graph = new DirectedSparseGraph<Entity, Property>();

		String QUERY = "";
		QUERY += "PREFIX owl: <http://www.w3.org/2002/07/owl#> ";
		QUERY += "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> ";
		QUERY += "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ";
		QUERY += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
		QUERY += "PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";
		QUERY += "PREFIX oddlinker: <http://data.linkedmdb.org/resource/oddlinker/> ";
		QUERY += "PREFIX map: <file:/C:/d2r-server-0.4/mapping.n3#> ";
		QUERY += "PREFIX db: <http://data.linkedmdb.org/resource/> ";
		QUERY += "PREFIX dbpedia: <http://dbpedia.org/property/> ";
		QUERY += "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> ";
		QUERY += "PREFIX dc: <http://purl.org/dc/terms/> ";
		QUERY += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";

		QUERY += "SELECT distinct ?movie ";
		QUERY += "WHERE { ";
		// QUERY +=
		// "?movie movie:actor <http://data.linkedmdb.org/resource/actor/11285> ";
		QUERY += " ?movie rdf:type movie:film . ";
		// QUERY += "?movie movie:actor ?actor .";
		// QUERY += "?actor movie:actor_name 'Quentin Tarantino' .";
		// QUERY += "?m dc:title 'The Poseidon Adventure' . ";
		// QUERY += "?m movie:filmid 1019 . ";
		// QUERY +=
		// "http://data.linkedmdb.org/page/actor/426 movie:actor ?movie } ";
		QUERY += " } ";
		// QUERY += " } LIMIT 1";

		System.out.println(QUERY);
		Query query = QueryFactory.create(QUERY);

		// Remote execution.
		QueryExecution qexec = QueryExecutionFactory.sparqlService(ENDPOINT, query);

		// Set the DBpedia specific timeout.
		// (QueryEngineHTTP) qexec).addParam("timeout", "10000");

		// Execute.
		ResultSet rs = qexec.execSelect();
		// ResultSetFormatter.out(System.out, rs, query);

		ArrayList<Resource> movies = new ArrayList<Resource>();
		while (rs.hasNext()) {
			QuerySolution q = rs.next();
			movies.add(q.getResource("movie"));
		}

		int i = 1;
		for (Resource r : movies) {
			System.out.print(i + " ");
			System.out.println(r.getURI());
			System.out.println(r.getLocalName());
			System.out.println(r.getProperty(arg0))
			i++;
		}
		qexec.close();

	}
}