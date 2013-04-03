package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.EdgeFilm;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 03/04/13
 * Time: 13.51
 */
public class DistanceNostraDW extends Distance {

    private static DistanceNostraDW d;

    private DistanceNostraDW() {
        super("nostraDW");
        d = this;
    }

    public static DistanceNostraDW getInstance() {
        if (d == null)
            return new DistanceNostraDW();
        else
            return d;
    }

    public Double computeDistance(Film a, Film b) {
        Collection<EdgeFilm> set = FilmGraph.getGraph().findEdgeSet(a, b);
        double ret = 0;
        for (EdgeFilm ef : set)
            ret += ef.getWeight();
        return ret;
    }


}