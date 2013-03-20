import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Grafo.load();
        // Grafo.updateWeight();
        Grafo.printDot();

        FilmGraph.load();
        FilmGraph.printDot();

        Distance.load();

        Recommender.init();

//        MovieLensVoting.init();
//        Set<Film> liked = new HashSet<Film>();
//        ArrayList<Integer> user = MovieLensVoting.users();
//        ArrayList<MovieLensType> films = MovieLensVoting.userVotes(user.get(0));
//        for (MovieLensType m : films) {
//            //DA' ERRORE PERCHE NON é STATO INSERITO TRA I FILM DA ESTRARRE
//            System.out.println(m.getIdItem());
//           liked.add(Film.getFilmByID(m.getIdItem()));
//        }

        Map<Film,Integer> liked = new HashMap<Film,Integer>();
        liked.put(Film.getFilmByID(916),5);
        liked.put(Film.getFilmByID(229),5);
        Profile profile = new Profile(liked);


        System.out.println("\n\nPROFILE: ");
        System.out.println(profile.toString());


        List<Recommendation> recommendations = Recommender.getRecommendations(profile);
        System.out.println("|Recommendations| = " + recommendations.size());
        System.out.println("\n\nRECOMMENDATION: ");

        for (Recommendation r : recommendations)
            System.out.println("Film: " + r.getFilm().getTitle() + "\t\tDistance: " + r.getDistance());
    }
}
