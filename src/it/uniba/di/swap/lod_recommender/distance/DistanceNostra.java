package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;

public class DistanceNostra extends Distance {

    private static DistanceNostra d;
    private DistanceNostraDW dndw;
    private DistanceNostraIIW dniiw;
    private DistanceNostraIOW dniow;

    private DistanceNostra() {
        super("nostra");
        d = this;
        this.init();
    }

    public static DistanceNostra getInstance() {
        return d == null ? new DistanceNostra() : d;
    }

    public Double computeDistance(Film a, Film b) {
        double denom = dndw.getDistance(a, b).doubleValue() + dndw.getDistance(b, a).doubleValue();
        denom += dniow.getDistance(a, b).doubleValue() + dniiw.getDistance(a, b).doubleValue();
        double d = 1.0 / (1.0 + denom);
        return d;
    }

    @Override
    protected void compute() {
        dndw = DistanceNostraDW.getInstance();
        dniiw = DistanceNostraIIW.getInstance();
        dniow = DistanceNostraIOW.getInstance();
        super.compute();
    }

}