package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.EdgeFilm;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;

import java.util.Collection;

public class DistancePassantC_W extends Distance {

    private static DistancePassantC_W d;
    private DistanceNostraIIW dniiw;
    private DistanceNostraIOW dniow;

    private DistancePassantC_W() {
        super("passantC_W");
        d = this;
        this.init();
    }

    public static DistancePassantC_W getInstance() {
        return d == null ? new DistancePassantC_W() : d;
    }

    public Double computeDistance(Film a, Film b) {
        double denom = sumWeightDirect(a, b) + sumWeightDirect(b, a);
        denom += dniow.getDistance(a, b).doubleValue() + dniiw.getDistance(a, b).doubleValue();
        double d = 1.0 / (1.0 + denom);
        return d;
    }

    @Override
    protected void compute() {
        dniiw = DistanceNostraIIW.getInstance();
        dniow = DistanceNostraIOW.getInstance();
        super.compute();
    }

    private Double sumWeightDirect(Film a, Film b) {
        Collection<EdgeFilm> set = FilmGraph.getGraph().findEdgeSet(a, b);
        double ret = 0;
        for (EdgeFilm ef : set)
            ret += ef.getWeight();
        return ret;
    }

}