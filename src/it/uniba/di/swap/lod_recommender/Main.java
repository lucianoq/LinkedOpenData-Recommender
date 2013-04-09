package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens_exp.MovieLens;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Graph.load();
//        Graph.updateWeight();
        //Graph.printDot();
        FilmGraph.load();
        //FilmGraph.printDot();
        Distances.init();

//        Class.forName("it.uniba.di.swap.lod_recommender.movielens_exp.MovieLens");
        MovieLens.startExperiment();
    }
}