import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

public class LIMDB {
	private static Model			m_model	= ModelFactory.createDefaultModel();

	public static final String	NS			= "http://data.linkedmdb.org/resource/";

	public static String getURI() {
		return NS;
	}

	public static final Resource	NAMESPACE	= m_model.createResource(NS);
}