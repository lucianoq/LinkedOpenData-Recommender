package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.profile.Profile;
import it.uniba.di.swap.lod_recommender.profile.SimpleProfile;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Graph.load();
        // it.uniba.di.swap.lod_recommender.graph.Graph.updateWeight();
        Graph.printDot();

        FilmGraph.load();
        FilmGraph.printDot();

        Distance.load();

        Recommender.init();

//        it.uniba.di.swap.lod_recommender.MovieLensVoting.init();
//        Set<it.uniba.di.swap.lod_recommender.Film> liked = new HashSet<it.uniba.di.swap.lod_recommender.Film>();
//        ArrayList<Integer> user = it.uniba.di.swap.lod_recommender.MovieLensVoting.users();
//        ArrayList<it.uniba.di.swap.lod_recommender.MovieLensType> films = it.uniba.di.swap.lod_recommender.MovieLensVoting.userVotes(user.get(0));
//        for (it.uniba.di.swap.lod_recommender.MovieLensType m : films) {
//            //DA' ERRORE PERCHE NON é STATO INSERITO TRA I FILM DA ESTRARRE
//            System.out.println(m.getIdItem());
//           liked.add(it.uniba.di.swap.lod_recommender.Film.getFilmByID(m.getIdItem()));
//        }

        Collection<Film> liked = new ArrayList<Film>();
        liked.add(Film.getFilmByID(916));
        liked.add(Film.getFilmByID(229));
        SimpleProfile profile = new SimpleProfile(liked);

        System.out.println("\n\nPROFILE: ");
        System.out.println(profile.toString());

        List<Recommendation> recommendations = Recommender.getRecommendations(profile);
        System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION: ");

        for (Recommendation r : recommendations)
            System.out.println("it.uniba.di.swap.lod_recommender.Film: " + r.getFilm().getTitle() + "\t\tit.uniba.di.swap.lod_recommender.Distance: " + r.getDistance());
    }
}
