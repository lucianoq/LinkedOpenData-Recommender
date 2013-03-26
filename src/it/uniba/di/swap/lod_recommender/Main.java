package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensType;
import it.uniba.di.swap.lod_recommender.movielens.MovieLensVoting;
import it.uniba.di.swap.lod_recommender.profile.*;
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

//        type :      Nostra
//                    NostraDW
//                    NostraIIW
//                    NostraIOW
//                    PassantCW
//                    PassantDW
//                    PassantIW
//                    PassantD
//                    PassantI
//                    PassantC

        Recommender.init("Nostra");

        MovieLensVoting.init();

        ArrayList<Integer> user = MovieLensVoting.users();
        ArrayList<MovieLensType> films = MovieLensVoting.userVotes(user.get(0));

        simple(films);
        System.out.println("--------------------------------------------");

        simpleNegative(films);
        System.out.println("--------------------------------------------");

        nostraweight(films);
        System.out.println("--------------------------------------------");

        mustoweight(films);
    }

    private static void simple(ArrayList<MovieLensType> films) {
        Set<Film> likedSimple = new HashSet<Film>();
        for (MovieLensType m : films)
            likedSimple.add(Film.getFilmByID(m.getIdItem()));
        likedSimple.remove(null);

        SimpleProfile pr = new SimpleProfile(likedSimple);

//        System.out.println("\n\nPROFILE SIMPLE: ");
//        System.out.println(pr.toString());

        List<Recommendation> recommendations = Recommender.getRecommendations(pr, 5);
        //System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION SIMPLE: ");

        for (Recommendation r : recommendations)
            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistance: " + r.getDistance());
    }

    private static void simpleNegative(ArrayList<MovieLensType> films) {
        Set<Film> likedSimpleOnlyPositive = new HashSet<Film>();
        Set<Film> likedSimpleWithNegative = new HashSet<Film>();
        for (MovieLensType m : films)
            if (m.getRating() > 3)
                likedSimpleOnlyPositive.add(Film.getFilmByID(m.getIdItem()));
            else
                likedSimpleWithNegative.add(Film.getFilmByID(m.getIdItem()));

        likedSimpleOnlyPositive.remove(null);
        likedSimpleWithNegative.remove(null);

        SimpleProfile spNeg = new SimpleProfileNegative(likedSimpleOnlyPositive, likedSimpleWithNegative);

//        System.out.println("\n\nPROFILE SIMPLE NEGATIVE: ");
//        System.out.println(spNeg.toString());

        List<Recommendation> recommendations = Recommender.getRecommendations(spNeg, 5);
        //System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION SIMPLE NEGATIVE: ");

        for (Recommendation r : recommendations)
            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistance: " + r.getDistance());

    }

    private static void nostraweight(ArrayList<MovieLensType> films) {
        Map<Film, Number> likedWeight = new HashMap<Film, Number>();

        for (it.uniba.di.swap.lod_recommender.movielens.MovieLensType m : films)
            likedWeight.put(Film.getFilmByID(m.getIdItem()), m.getRating());
        likedWeight.remove(null);

//        likedWeight.put(Film.getFilmByID(444), 5);
//        likedWeight.put(Film.getFilmByID(447), 1);

        VotedProfile profile = new NostraVotedProfile(likedWeight);

//        System.out.println("\n\nPROFILE OWN WEIGHT: ");
//        System.out.println(profile.toString());


        List<Recommendation> recommendations = Recommender.getRecommendations(profile, 5);
        //System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION OWN WEIGHT: ");

        for (Recommendation r : recommendations)
            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistance: " + r.getDistance());

    }

    private static void mustoweight(ArrayList<MovieLensType> films) {
        Map<Film, Number> likedWeight = new HashMap<Film, Number>();
        for (it.uniba.di.swap.lod_recommender.movielens.MovieLensType m : films)
            likedWeight.put(Film.getFilmByID(m.getIdItem()), m.getRating());
        likedWeight.remove(null);
        VotedProfile profile = new MustoVotedProfile(likedWeight);

//        System.out.println("\n\nPROFILE MUSTO WEIGHT: ");
//        System.out.println(profile.toString());


        List<Recommendation> recommendations = Recommender.getRecommendations(profile, 5);
        //System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION MUSTO WEIGHT: ");

        for (Recommendation r : recommendations)
            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistance: " + r.getDistance());

    }
}
