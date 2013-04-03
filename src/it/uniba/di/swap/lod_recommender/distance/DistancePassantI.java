package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 03/04/13
 * Time: 13.51
 */
public class DistancePassantI extends Distance {

    private static DistancePassantI d;
    private DistanceCionab cionab;
    private DistanceCiinab ciinab;

    private DistancePassantI() {
        super("passantI");
        cionab = DistanceCionab.getInstance();
        ciinab = DistanceCiinab.getInstance();
        d = this;
    }

    public static DistancePassantI getInstance() {
        if (d == null)
            return new DistancePassantI();
        else
            return d;
    }

    public Double computeDistance(Film a, Film b) {
        return 1.0d / (1 + cionab.getDistance(a, b).intValue() + ciinab.getDistance(a, b).intValue());
    }


}
