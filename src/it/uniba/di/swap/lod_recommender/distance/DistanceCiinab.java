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
public class DistanceCiinab extends Distance {

    private static DistanceCiinab d;

    private DistanceCiinab() {
        super("ciinab");
        d = this;
    }

    public static DistanceCiinab getInstance() {
        if (d == null)
            return new DistanceCiinab();
        else
            return d;
    }

    public Integer computeDistance(Film a, Film b) {
        int i = 0;
        Collection<EdgeFilm> collA = FilmGraph.getGraph().getInEdges(a);
        Collection<EdgeFilm> collB = FilmGraph.getGraph().getInEdges(b);

        for (EdgeFilm efA : collA)
            for (EdgeFilm efB : collB)
                if (efA.getSubject().equals(efB.getSubject()))
                    if (efA.getLabelModified().equals(efB.getLabelModified()))
                        i++;
        return i;
    }

}
