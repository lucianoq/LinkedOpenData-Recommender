package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 03/04/13
 * Time: 13.51
 */
public class DistanceNostra extends Distance {

    private static DistanceNostra d;
    private DistanceNostraDW dndw;
    private DistanceNostraIIW dniiw;
    private DistanceNostraIOW dniow;

    private DistanceNostra() {
        super("nostra");
        dndw = DistanceNostraDW.getInstance();
        dniiw = DistanceNostraIIW.getInstance();
        dniow = DistanceNostraIOW.getInstance();
        d = this;
    }

    public static DistanceNostra getInstance() {
        if (d == null)
            return new DistanceNostra();
        else
            return d;
    }

    public Double computeDistance(Film a, Film b) {
        double denom = dndw.getDistance(a, b).doubleValue() + dndw.getDistance(b, a).doubleValue();
        denom += dniow.getDistance(a, b).doubleValue() + dniiw.getDistance(a, b).doubleValue();
        double d = 1.0 / (1.0 + denom);
        return d;
    }


}