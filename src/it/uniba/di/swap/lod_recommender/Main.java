package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensUser;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensVoting;
import it.uniba.di.swap.lod_recommender.profile.Profile;
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

        MovieLensUser user = new MovieLensUser(1);
        user.print();

        for (Distances.Type t : Distances.Type.values()) {
            Recommender.init(t);
            for (Profile.Type p : Profile.Type.values()) {
                System.out.println("\n\n-- DISTANZA " + t.name());
                System.out.println("-- RECOMMENDATION " + p.name());

                List<Recommendation> rec = Recommender.getRecommendations(user.getProfile(p), 100);

                double start = rec.get(0).getDistance();
                for (Recommendation r : rec)
                    System.out.println("ID: " + r.getFilm().getIdMovieLens() + "\t\tFilm: " + r.getFilm().getTitle() + "\t\t\tGap: " + (r.getDistance() - start));


//            System.out.println("--------------------------------------------");
//
//            start = recSimpleNegative.get(0).getDistance();
//            System.out.println("\n\nRECOMMENDATION SIMPLE NEGATIVE: ");
//            for (Recommendation r : recSimpleNegative)
//                System.out.println("ID: " + r.getFilm().getIdMovieLens() + "\t\tFilm: " + r.getFilm().getTitle() + "\t\tDistances: " + (r.getDistance() - start));
//
//            System.out.println("--------------------------------------------");
//
//            start = recVotedNostra.get(0).getDistance();
//            System.out.println("\n\nRECOMMENDATION VOTED NOSTRA: ");
//            for (Recommendation r : recVotedNostra)
//                System.out.println("ID: " + r.getFilm().getIdMovieLens() + "\t\tFilm: " + r.getFilm().getTitle() + "\t\tDistances: " + (r.getDistance() - start));
//
//            System.out.println("--------------------------------------------");
//
//            start = recVotedMusto.get(0).getDistance();
//            System.out.println("\n\nRECOMMENDATION VOTED MUSTO: ");
//            for (Recommendation r : recVotedMusto)
//                System.out.println("ID: " + r.getFilm().getIdMovieLens() + "\t\tFilm: " + r.getFilm().getTitle() + "\t\tDistances: " + (r.getDistance() - start));

            }
        }
    }
}
