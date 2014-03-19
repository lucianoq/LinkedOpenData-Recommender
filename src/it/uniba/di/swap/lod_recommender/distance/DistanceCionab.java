package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.EdgeFilm;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;

import java.util.Collection;

public class DistanceCionab extends Distance {

    private static DistanceCionab d;

    private DistanceCionab() {
        super("cionab");
        d = this;
    }

    public static DistanceCionab getInstance() {
        if (d == null) {
            DistanceCionab tmp = new DistanceCionab();
            tmp.init();
            return tmp;
        } else
            return d;
    }

    public Integer computeDistance(Film a, Film b) {
        int i = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getOutEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getOutEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getObject().equals(efB.getObject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        i++;
        return i;
    }

}
