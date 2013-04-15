package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.recommendation.Pair;

import java.util.ArrayList;

class ComputeDistance extends Thread {

    private ArrayList<Film> subset;
    private Distance d;

    public ComputeDistance(Distance d) {
        this.d = d;
        this.subset = new ArrayList<Film>(Graph.getFilms().size() / 8 + 1);
    }

    public ArrayList<Film> getSubset() {
        return subset;
    }

    @Override
    public void run() {
        for (Film f1 : subset) {
            if ((d instanceof DistancePassantDW) || (d instanceof DistancePassantIW) || (d instanceof DistancePassantCW))
                System.out.println("Distanza: " + d.getClass().getName() + " Inizio il film " + f1.getTitle());
            for (Film f2 : Graph.getFilms())
                if (!f1.equals(f2))
                    d.getMap().put(new Pair(f1, f2), d.computeDistance(f1, f2));
        }
    }
}
