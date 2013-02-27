import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

public class Main {
	// public static final String ENDPOINT = "http://sparql.freebase.com";
	// public static final String ENDPOINT = "http://dbpedia.org/sparql";
	public static final String	ENDPOINT	= "http://data.linkedmdb.org/sparql";
	// public static final String ENDPOINT = "http://live.dbpedia.org/sparql";
	// public static final String DATASET = "en/wikipedia_links_en";
	public static Logger			logger	= Logger.getLogger(Main.class);
	public static Model			model;
	public static PrintWriter	out;
	public static PrintWriter	debug;

	public static void main(String[] args) throws FileNotFoundException {
		BasicConfigurator.configure();
		FileOutputStream fout = new FileOutputStream("/home/lusio/dev/lod-project/OUT");
		FileOutputStream fdebug = new FileOutputStream("/home/lusio/dev/lod-project/DEBUG");
		out = new PrintWriter(fout);
		debug = new PrintWriter(fdebug);

		model = ModelFactory.createDefaultModel();
		// Graph<Entita, Predicato> graph = new DirectedSparseGraph<Entita,
		// Predicato>();

		// Main.loadMovies();
		// Main.loadActors();
		// Main.loadMovieActor();
		Main.loadAll();

		int i = 1;
		StmtIterator it = model.listStatements();
		while (it.hasNext()) {
			Resource sub = it.next().getSubject();
			Property pre = it.next().getPredicate();
			RDFNode obj = it.next().getObject();
			out.print(i + " ");
			out.print("<" + sub + "> ");
			out.print("<" + pre + "> ");
			out.println("<" + obj + "> .");
			i++;
		}
	}

	private static void loadMovies() {
		String query = "";
		query += "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ";
		query += "PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";
		query += "PREFIX db: <http://data.linkedmdb.org/resource/> ";
		query += "PREFIX dbpedia: <http://dbpedia.org/property/> ";
		query += "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> ";
		query += "PREFIX dc: <http://purl.org/dc/terms/> ";
		query += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";

		query += "SELECT ?movie ";
		query += "WHERE { ";
		query += " ?movie rdf:type movie:film . ";
		query += " } LIMIT 100";

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
		query += "PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";
		query += "PREFIX db: <http://data.linkedmdb.org/resource/> ";
		query += "PREFIX dbpedia: <http://dbpedia.org/property/> ";
		query += "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> ";
		query += "PREFIX dc: <http://purl.org/dc/terms/> ";
		query += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";

		query += "SELECT ?movie ?actor ";
		query += "WHERE { ";
		query += " ?movie rdf:type movie:film . ";
		query += " ?actor rdf:type movie:actor . ";
		query += " ?movie movie:actor ?actor . ";
		query += " } LIMIT 100";

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
		query += "PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";
		query += "PREFIX db: <http://data.linkedmdb.org/resource/> ";
		query += "PREFIX dbpedia: <http://dbpedia.org/property/> ";
		query += "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> ";
		query += "PREFIX dc: <http://purl.org/dc/terms/> ";
		query += "PREFIX movie: <http://data.linkedmdb.org/resource/movie/> ";

		query += "SELECT ?a ?b ?c ";
		query += "WHERE { ";
		query += " ?a ?b ?c .";
		query += " ?a rdf:type movie:film .";
		query += " } LIMIT 100000";

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