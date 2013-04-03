package it.uniba.di.swap.lod_recommender.recommendation;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;

import java.util.ArrayList;
import java.util.Date;

public class ComputeDistance extends Thread {

    private ArrayList<Film> subset;

    public ComputeDistance() {
        this.subset = new ArrayList<Film>();
    }

    public ArrayList<Film> getSubset() {
        return subset;
    }

    @Override
    public void run() {
        for (Film f1 : subset) {
            for (Film f2 : Graph.getFilms())
                if (!f1.equals(f2)) {
                   // Distance.getCio_n_A_B().put(new Pair(f1, f2), Distance.cio_n_A_B(f1, f2));
//                    Distance.getCii_n_A_B().put(new Pair(f1, f2), Distance.cii_n_A_B(f1, f2));
//                    Distance.getNostraDW().put(new Pair(f1, f2), Distance.nostraDW(f1, f2));
//                    Distance.getNostraDW().put(new Pair(f2, f1), Distance.nostraDW(f2, f1));
//                    Distance.getNostraIOW().put(new Pair(f1, f2), Distance.nostraIOW(f1, f2));
//                    Distance.getNostraIIW().put(new Pair(f1, f2), Distance.nostraIIW(f1, f2));
//                    Distance.getPassantD().put(new Pair(f1, f2), Distance.passantD(f1, f2));
//                    Distance.getPassantDW().put(new Pair(f1, f2), Distance.passantDW(f1, f2));
//                    Distance.getPassantI().put(new Pair(f1, f2), Distance.passantI(f1, f2));
//                    Distance.getPassantIW().put(new Pair(f1, f2), Distance.passantIW(f1, f2));
//                    Distance.getPassantC().put(new Pair(f1, f2), Distance.passantC(f1, f2));
//                    Distance.getPassantCW().put(new Pair(f1, f2), Distance.passantCW(f1, f2));
//                    Distance.getNostra().put(new Pair(f1, f2), Distance.nostra(f1, f2));
                }
            System.out.println("[INFO] [" + new Date() + "] Finito il calcolo del film " + f1.getTitle());
        }
    }
}
