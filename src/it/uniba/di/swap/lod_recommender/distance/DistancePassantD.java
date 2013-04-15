package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 03/04/13
 * Time: 13.51
 */
public class DistancePassantD extends Distance {

    private static DistancePassantD d;

    private DistancePassantD() {
        super("passantD");
        d = this;
        this.init();
    }

    public static DistancePassantD getInstance() {
        return d == null ? new DistancePassantD() : d;
    }

    public Double computeDistance(Film a, Film b) {
        return 1.0d / (1 + cd_n_A_B(a, b) + cd_n_A_B(b, a));
    }

    //numero di archi tra A e B
    private int cd_n_A_B(Film a, Film b) {
        return FilmGraph.getGraph().findEdgeSet(a, b).size();
    }
}
