import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        MovieLensVoting.init();

        Grafo.load();
       // Grafo.updateWeight();
        Grafo.save();
        Grafo.printDot();

        System.out.println("Grafo generale creato o caricato");


        FilmGraph.init();
        FilmGraph.printDot();

        System.out.println("Grafo dei film creato");

        Distance.load();
        Distance.save();

        Recommender.init();

        Set<Film> liked = new HashSet<Film>();
        liked.add(Film.getFilmByID(229));
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
