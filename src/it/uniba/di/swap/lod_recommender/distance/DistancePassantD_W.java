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
public class DistancePassantD_W extends Distance {

    private static DistancePassantD_W d;

    private DistancePassantD_W() {
        super("passantD_W");
        d = this;
        this.init();
    }

    public static DistancePassantD_W getInstance() {
        return d == null ? new DistancePassantD_W() : d;
    }

    public Double computeDistance(Film a, Film b) {
        return 1.0d / (sumWeightDirect(a, b) + sumWeightDirect(b, a));
    }

    private Double sumWeightDirect(Film a, Film b) {
        Collection<EdgeFilm> set = FilmGraph.getGraph().findEdgeSet(a, b);
        double ret = 0;
        for (EdgeFilm ef : set)
            ret += ef.getWeight();
        return ret;
    }


}