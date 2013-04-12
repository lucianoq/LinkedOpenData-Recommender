package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens_exp.Metrics;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Graph.load();
//        Graph.updateWeight();
        //Graph.printDot();
        FilmGraph.load();
        //FilmGraph.printDot();
        Distances.init();

        Class.forName("it.uniba.di.swap.lod_recommender.movielens_exp.MovieLens");

        for (Configuration c : Configuration.getConfigurations()) {
            double microPrec = Metrics.microPrecision(c);
            double macroPrec = Metrics.macroPrecision(c);
            double microMRR = Metrics.microMRR(c);
            double macroMRR = Metrics.macroMRR(c);
            double microPrecInTest = Metrics.microPrecisionInTest(c);
            double macroPrecInTest = Metrics.macroPrecisionInTest(c);
            System.out.print("PRECISIONI: ");
            System.out.println(microPrec);
            System.out.println(macroPrec);

            System.out.print("PRECISIONI IN TEST: ");
            System.out.println(microPrecInTest);
            System.out.println(macroPrecInTest);

            System.out.print("MRR: ");
            System.out.println(microMRR);
            System.out.println(macroMRR);
        }
    }
}