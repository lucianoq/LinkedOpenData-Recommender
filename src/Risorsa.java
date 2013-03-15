import java.io.Serializable;

public class Risorsa implements Serializable {
    protected String uri;
    protected String title;

    public Risorsa(String uri) {
        this.uri = uri;

        this.title = uri.
                replace("http://dbpedia.org/resource/", "")
                .replace("http://dbpedia.org/ontology/", "")
                .replace("http://purl.org/dc/terms/", "")
                .replace("http://dbpedia.org/property/", "")
                .replace("http://dbpedia.org/resource/", "")
                .replace(".", "_").replace(":", "_").replace(",", "_").replace("-", "_");
    }

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Risorsa risorsa = (Risorsa) o;

        if (uri != null ? !uri.equals(risorsa.uri) : risorsa.uri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Risorsa{" +
                "uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
