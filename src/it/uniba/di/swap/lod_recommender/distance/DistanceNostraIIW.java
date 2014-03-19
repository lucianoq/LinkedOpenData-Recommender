package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.EdgeFilm;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;

import java.util.Collection;

public class DistanceNostraIIW extends Distance {

    private static DistanceNostraIIW d;

    private DistanceNostraIIW() {
        super("nostraIIW");
        d = this;
        this.init();
    }

    public static DistanceNostraIIW getInstance() {
        return d == null ? new DistanceNostraIIW() : d;
    }

    public Double computeDistance(Film a, Film b) {
        double d = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getInEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getInEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getSubject().equals(efB.getSubject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        d += efA.getWeight() * efB.getWeight();
        return d;
    }


}