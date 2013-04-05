package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensUser;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensVoting;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;
import it.uniba.di.swap.lod_recommender.recommendation.Recommender;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Graph.load();
//        Graph.updateWeight();
        //Graph.printDot();
        FilmGraph.load();
        //FilmGraph.printDot();
        Distances.init();

        MovieLensVoting.init();

        MovieLensUser user = new MovieLensUser(904);
        user.print();

        for (Distances.Type t : Distances.Type.values()) {
            System.out.println("------------------------------");
            System.out.println("-- INIZIO DISTANZA " + t.name());
            System.out.println("------------------------------");
            Recommender.init(t);

            List<Recommendation> recSimple = Recommender.getRecommendations(user.getProfileSimple(), 10);
            List<Recommendation> recSimpleNegative = Recommender.getRecommendations(user.getProfileSimpleNegative(), 10);
            List<Recommendation> recVotedNostra = Recommender.getRecommendations(user.getProfileVotedNostra(), 10);
            List<Recommendation> recVotedMusto = Recommender.getRecommendations(user.getProfileVotedMusto(), 10);

            System.out.println("\n\nRECOMMENDATION SIMPLE: ");
            for (Recommendation r : recSimple)
                System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistances: " + r.getDistance());


            System.out.println("--------------------------------------------");

            System.out.println("\n\nRECOMMENDATION SIMPLE NEGATIVE: ");
            for (Recommendation r : recSimpleNegative)
                System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistances: " + r.getDistance());

            System.out.println("--------------------------------------------");

            System.out.println("\n\nRECOMMENDATION VOTED NOSTRA: ");
            for (Recommendation r : recVotedNostra)
                System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistances: " + r.getDistance());

            System.out.println("--------------------------------------------");

            System.out.println("\n\nRECOMMENDATION VOTED MUSTO: ");
            for (Recommendation r : recVotedMusto)
                System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistances: " + r.getDistance());

            System.out.println("¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿?¿");
        }
    }
}
