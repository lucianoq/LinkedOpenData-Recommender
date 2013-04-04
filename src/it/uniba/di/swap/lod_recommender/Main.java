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
        Graph.printDot();
        FilmGraph.load();
        FilmGraph.printDot();
        Distances.init();

        Recommender.init(Distances.Type.NOSTRA);
        MovieLensVoting.init();

//        ArrayList<Integer> user = MovieLensVoting.users();
//        ArrayList<MovieLensType> films = MovieLensVoting.userVotes(user.get(371));


        MovieLensUser user = new MovieLensUser(904);

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
    }

//
//
//    private static void simpleNegative(ArrayList<MovieLensType> films) {
//        Set<Film> likedSimpleOnlyPositive = new HashSet<Film>();
//        Set<Film> likedSimpleWithNegative = new HashSet<Film>();
//        for (MovieLensType m : films)
//            if (m.getRating() > 3)
//                likedSimpleOnlyPositive.add(Film.getFilmByID(m.getIdItem()));
//            else
//                likedSimpleWithNegative.add(Film.getFilmByID(m.getIdItem()));
//
//        likedSimpleOnlyPositive.remove(null);
//        likedSimpleWithNegative.remove(null);
////        likedSimpleOnlyPositive.add(Film.getFilmByID(313));
////        likedSimpleOnlyPositive.add(Film.getFilmByID(318));
////        likedSimpleOnlyPositive.add(Film.getFilmByID(475));
////        likedSimpleOnlyPositive.add(Film.getFilmByID(755));
////        likedSimpleWithNegative.add(Film.getFilmByID(457));
//
//
//        ProfileSimple spNeg = new ProfileSimpleNegative(likedSimpleOnlyPositive, likedSimpleWithNegative);
//
////        System.out.println("\n\nPROFILE SIMPLE NEGATIVE: ");
////        System.out.println(spNeg.toString());
//
//        List<Recommendation> recommendations = Recommender.getRecommendations(spNeg, 10);
//        //System.out.println("|Recommendations| = " + recommendations.size());
//        System.out.println("\n\nRECOMMENDATION SIMPLE NEGATIVE: ");
//
//        for (Recommendation r : recommendations)
//            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistances: " + r.getDistance());
//
//    }
//
//    private static void nostraweight(ArrayList<MovieLensType> films) {
//        Map<Film, Number> likedWeight = new HashMap<Film, Number>();
//
//        for (it.uniba.di.swap.lod_recommender.movielens.MovieLensType m : films)
//            likedWeight.put(Film.getFilmByID(m.getIdItem()), m.getRating());
//        likedWeight.remove(null);
////
////        likedWeight.put(Film.getFilmByID(313), 5);
////        likedWeight.put(Film.getFilmByID(318), 3);
////        likedWeight.put(Film.getFilmByID(475), 4);
////        likedWeight.put(Film.getFilmByID(755), 5);
////        likedWeight.put(Film.getFilmByID(457), 1);
////        likedWeight.put(Film.getFilmByID(447), 1);
//
//        ProfileVoted profile = new ProfileVotedNostra(likedWeight);
//
//        System.out.println("\n\nPROFILE OWN WEIGHT: ");
//        System.out.println(profile.toString());
//
//
//        List<Recommendation> recommendations = Recommender.getRecommendations(profile, 10);
//        //System.out.println("|Recommendations| = " + recommendations.size());
//        System.out.println("\n\nRECOMMENDATION OWN WEIGHT: ");
//
//        for (Recommendation r : recommendations)
//            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistances: " + r.getDistance());
//
//    }
//
//    private static void mustoweight(ArrayList<MovieLensType> films) {
//        Map<Film, Number> likedWeight = new HashMap<Film, Number>();
//        for (it.uniba.di.swap.lod_recommender.movielens.MovieLensType m : films)
//            likedWeight.put(Film.getFilmByID(m.getIdItem()), m.getRating());
//        likedWeight.remove(null);
////        likedWeight.put(Film.getFilmByID(313), 5);
////        likedWeight.put(Film.getFilmByID(318), 3);
////        likedWeight.put(Film.getFilmByID(475), 4);
////        likedWeight.put(Film.getFilmByID(755), 5);
////        likedWeight.put(Film.getFilmByID(457), 1);
//        ProfileVoted profile = new ProfileVotedMusto(likedWeight);
//
////        System.out.println("\n\nPROFILE MUSTO WEIGHT: ");
////        System.out.println(profile.toString());
//
//
//        List<Recommendation> recommendations = Recommender.getRecommendations(profile, 10);
//        //System.out.println("|Recommendations| = " + recommendations.size());
//        System.out.println("\n\nRECOMMENDATION MUSTO WEIGHT: ");
//
//        for (Recommendation r : recommendations)
//            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistances: " + r.getDistance());
//
//    }
}
