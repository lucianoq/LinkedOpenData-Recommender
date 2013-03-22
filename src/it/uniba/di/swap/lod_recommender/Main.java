package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensType;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensVoting;
import it.uniba.di.swap.lod_recommender.profile.NostraVotedProfile;
import it.uniba.di.swap.lod_recommender.profile.SimpleProfile;
import it.uniba.di.swap.lod_recommender.profile.VotedProfile;
import it.uniba.di.swap.lod_recommender.recommendation.Distance;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;
import it.uniba.di.swap.lod_recommender.recommendation.Recommender;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Graph.load();
        //Graph.updateWeight();
        Graph.printDot();

        FilmGraph.load();
        FilmGraph.printDot();

        Distance.load();

        Recommender.init();

        it.uniba.di.swap.lod_recommender.movielens.MovieLensVoting.init();
        Set<Film> likedSimple = new HashSet<Film>();
        Map<Film, Number> likedWeight = new HashMap<Film, Number>();
        ArrayList<Integer> user = MovieLensVoting.users();
        ArrayList<MovieLensType> films = MovieLensVoting.userVotes(user.get(0));
        for (it.uniba.di.swap.lod_recommender.movielens.MovieLensType m : films) {
            likedSimple.add(Film.getFilmByID(m.getIdItem()));
            likedWeight.put(Film.getFilmByID(m.getIdItem()), m.getRating());
        }
        likedSimple.remove(null);
        likedWeight.remove(null);
        SimpleProfile pr = new SimpleProfile(likedSimple);

        System.out.println("\n\nPROFILE SIMPLE: ");
        System.out.println(pr.toString());

        List<Recommendation> recommendations = Recommender.getRecommendations(pr, 5);
        System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION SIMPLE: ");

        for (Recommendation r : recommendations)
            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistance: " + r.getDistance());

        System.out.println("--------------------------------------------");
        VotedProfile profile = new NostraVotedProfile(likedWeight);

        System.out.println("\n\nPROFILE WEIGHT: ");
        System.out.println(profile.toString());


        recommendations = Recommender.getRecommendations(profile, 5);
        System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION WEIGHT: ");

        for (Recommendation r : recommendations)
            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistance: " + r.getDistance());


    }
}
