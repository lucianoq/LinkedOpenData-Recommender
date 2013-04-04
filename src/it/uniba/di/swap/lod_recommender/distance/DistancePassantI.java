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
        d = this;
        this.init();
    }

    public static DistancePassantI getInstance() {
        return d == null ? new DistancePassantI() : d;
    }

    public Double computeDistance(Film a, Film b) {

        System.out.println("Dimensioni cionab " + cionab.getMap().size());
        System.out.println("Dimensioni ciinab " + ciinab.getMap().size());
        return 1.0d / (1 + cionab.getDistance(a, b).intValue() + ciinab.getDistance(a, b).intValue());
    }

    @Override
    protected void compute() {
        cionab = DistanceCionab.getInstance();
        ciinab = DistanceCiinab.getInstance();
        super.compute();
    }

}
