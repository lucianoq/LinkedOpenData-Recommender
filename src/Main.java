import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Grafo.load();
        // Grafo.updateWeight();
        Grafo.printDot();

        FilmGraph.load();
        FilmGraph.printDot();

        Distance.load();

        Recommender.init();

        MovieLensVoting.init();
        Set<Film> liked = new HashSet<Film>();
        ArrayList<Integer> user = MovieLensVoting.users();
        ArrayList<MovieLensType> films = MovieLensVoting.userVotes(user.get(0));
        for (MovieLensType m : films) {
            System.out.println(m.getIdItem());
           liked.add(Film.getFilmByID(m.getIdItem()));
        }

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
