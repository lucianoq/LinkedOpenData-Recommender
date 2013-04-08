package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensTrainingSet;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensUser;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensVoting;
import it.uniba.di.swap.lod_recommender.mysql.DBAccess;
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

        MovieLensTrainingSet.init(MovieLensTrainingSet.ALL);

        //int i = 1;
        for (int i : MovieLensVoting.users()) {
            MovieLensVoting.init("./config/trainingSet/user" + i + ".txt");
            MovieLensUser user = new MovieLensUser(i);
            user.print();

            for (Distances.Type t : Distances.Type.values()) {
                Recommender.init(t);
                for (Profile.Type p : Profile.Type.values()) {
//                    System.out.println("\n\n-- DISTANZA " + t.name());
//                    System.out.println("-- RECOMMENDATION " + p.name());

                    List<Recommendation> rec = Recommender.getRecommendations(user.getProfile(p), 100);

//                    double start = rec.get(0).getDistance();
                    for (Recommendation r : rec) {
                        DBAccess.insert(
                                r.getFilm().getIdMovieLens(),
                                user.getId(),
                                rec.indexOf(r),
                                t.ordinal(),
                                p.ordinal(),
                                r.getFilm().getTitle(),
                                t.name(),
                                p.name());
//                        System.out.println("ID: " + r.getFilm().getIdMovieLens() + "\t\tFilm: " + r.getFilm().getTitle() + "\t\t\tGap: " + (r.getDistance() - start));
                    }
                }
            }
            DBAccess.commit();
        }
        DBAccess.close();
    }
}
