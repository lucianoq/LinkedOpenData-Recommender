package it.uniba.di.swap.lod_recommender.graph;

import java.io.Serializable;

public class GraphResource implements Serializable {
    private String uri;
    private String title;

    public GraphResource(String uri) {
        this.uri = uri;

        this.title = uri.
                replace("http://dbpedia.org/resource/", "")
                .replace("http://dbpedia.org/ontology/", "")
                .replace("http://purl.org/dc/terms/", "")
                .replace("http://dbpedia.org/property/", "")
                .replace("http://dbpedia.org/resource/", "")
                .replace("http://dbpedia.org/page/", "")
                .replace("http://live.dbpedia.org/page/","")
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

        GraphResource graphResource = (GraphResource) o;

        if (uri != null ? !uri.equals(graphResource.uri) : graphResource.uri != null) return false;

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
        return "it.uniba.di.swap.lod_recommender.graph.GraphResource{" +
                "uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
