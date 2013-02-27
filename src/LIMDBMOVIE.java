import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class LIMDBMOVIE {
	private static Model			m_model	= ModelFactory.createDefaultModel();

	public static final String	NS			= "http://data.linkedmdb.org/resource/movie";

	public static String getURI() {
		return NS;
	}

	public static final Resource	NAMESPACE				= m_model.createResource(NS);

	public static final Property	actor						= m_model.createProperty("http://data.linkedmdb.org/resource/movie/actor");
	public static final Property	director					= m_model.createProperty("http://data.linkedmdb.org/resource/movie/director");
	public static final Property	editor					= m_model.createProperty("http://data.linkedmdb.org/resource/movie/editor");
	public static final Property	film_subject			= m_model.createProperty("http://data.linkedmdb.org/resource/movie/film_subject");
	public static final Property	filmid					= m_model.createProperty("http://data.linkedmdb.org/resource/movie/filmid");
	public static final Property	initial_release_date	= m_model.createProperty("http://data.linkedmdb.org/resource/movie/initial_release_date");
	public static final Property	music_contributor		= m_model.createProperty("http://data.linkedmdb.org/resource/movie/music_contributor");
	public static final Property	performance				= m_model.createProperty("http://data.linkedmdb.org/resource/movie/performance");
	public static final Property	producer					= m_model.createProperty("http://data.linkedmdb.org/resource/movie/producer");
	public static final Property	sequel					= m_model.createProperty("http://data.linkedmdb.org/resource/movie/sequel");
	public static final Property	prequel					= m_model.createProperty("http://data.linkedmdb.org/resource/movie/prequel");
	public static final Property	film_of_distributor	= m_model.createProperty("http://data.linkedmdb.org/resource/movie/film_of_distributor");

	public static final Resource	filmRes					= m_model.createResource("http://data.linkedmdb.org/resource/movie/film");
	public static final Resource	actorRes					= m_model.createResource("http://data.linkedmdb.org/resource/movie/actor");
}