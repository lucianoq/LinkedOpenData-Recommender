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
public class DistanceNostraIOW extends Distance {

    private static DistanceNostraIOW d;

    private DistanceNostraIOW() {
        super("nostraIOW");
        d = this;
    }

    public static DistanceNostraIOW getInstance() {
        if (d == null)
            return new DistanceNostraIOW();
        else
            return d;
    }

    public Double computeDistance(Film a, Film b) {
        double d = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getOutEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getOutEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getObject().equals(efB.getObject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        d += efA.getWeight() * efB.getWeight();
        return d;
    }


}