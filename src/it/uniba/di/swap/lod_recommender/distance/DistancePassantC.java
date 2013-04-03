package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 03/04/13
 * Time: 13.51
 */
public class DistancePassantC extends Distance {

    private static DistancePassantC d;
    private DistanceCionab cionab = DistanceCionab.getInstance();
    private DistanceCiinab ciinab = DistanceCiinab.getInstance();

    private DistancePassantC() {
        super("passantC");
        d = this;
    }

    public static DistancePassantC getInstance() {
        if (d == null)
            return new DistancePassantC();
        else
            return d;
    }

    public Double computeDistance(Film a, Film b) {
        double denom = cd_n_A_B(a, b) + cd_n_A_B(b, a);
        denom += cionab.getDistance(a, b).intValue() + ciinab.getDistance(a, b).intValue();
        double d = 1.0 / (1 + denom);
        return d;
    }

    //numero di archi tra A e B
    private int cd_n_A_B(Film a, Film b) {
        return FilmGraph.getGraph().findEdgeSet(a, b).size();
    }
}
