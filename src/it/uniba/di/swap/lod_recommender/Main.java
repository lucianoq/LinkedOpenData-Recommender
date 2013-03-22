package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.FilmGraph;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.profile.NostraVotedProfile;
import it.uniba.di.swap.lod_recommender.profile.SimpleProfile;
import it.uniba.di.swap.lod_recommender.profile.VotedProfile;

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
        System.exit(1);

        Recommender.init();

//        it.uniba.di.swap.lod_recommender.MovieLensVoting.init();
//        Set<it.uniba.di.swap.lod_recommender.graph.Film> liked = new HashSet<it.uniba.di.swap.lod_recommender.graph.Film>();
//        ArrayList<Integer> user = it.uniba.di.swap.lod_recommender.MovieLensVoting.users();
//        ArrayList<it.uniba.di.swap.lod_recommender.MovieLensType> films = it.uniba.di.swap.lod_recommender.MovieLensVoting.userVotes(user.get(0));
//        for (it.uniba.di.swap.lod_recommender.MovieLensType m : films) {
//            //DA' ERRORE PERCHE NON é STATO INSERITO TRA I FILM DA ESTRARRE
//            System.out.println(m.getIdItem());
//           liked.add(it.uniba.di.swap.lod_recommender.graph.Film.getFilmByID(m.getIdItem()));
//        }

        Map<Film, Number> liked = new HashMap<Film, Number>();
        liked.put(Film.getFilmByID(444), 5);
        liked.put(Film.getFilmByID(447), 1);
        VotedProfile profile = new NostraVotedProfile(liked);

        System.out.println("\n\nPROFILE: ");
        System.out.println(profile.toString());

        List<Recommendation> recommendations = Recommender.getRecommendations(profile, 5);
        System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION: ");

        for (Recommendation r : recommendations)
            System.out.println("it.uniba.di.swap.lod_recommender.graph.Film: " + r.getFilm().getTitle() + "\t\tit.uniba.di.swap.lod_recommender.Distance: " + r.getDistance());

        Set<Film> likedSet = new HashSet<Film>();
        likedSet.add(Film.getFilmByID(444));
        likedSet.add(Film.getFilmByID(447));
        SimpleProfile simpleProfile = new SimpleProfile(likedSet);

        System.out.println("\n\nPROFILE: ");
        System.out.println(profile.toString());

        recommendations = Recommender.getRecommendations(simpleProfile, 5);
        System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION: ");

        for (Recommendation r : recommendations)
            System.out.println("it.uniba.di.swap.lod_recommender.graph.Film: " + r.getFilm().getTitle() + "\t\tit.uniba.di.swap.lod_recommender.Distance: " + r.getDistance());
    }
}
