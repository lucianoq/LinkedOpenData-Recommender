package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;

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
        double d = 1.0d / (1 + cd_n_A_B(a, b) + cd_n_A_B(b, a));
        return d;
    }

    //numero di archi tra A e B
    private int cd_n_A_B(Film a, Film b) {
        return FilmGraph.getGraph().findEdgeSet(a, b).size();
    }
}
