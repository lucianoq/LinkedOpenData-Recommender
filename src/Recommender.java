import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 15/03/13
 * Time: 19.16
 */
public class Recommender {

    private static Map<Film, List<Recommendation>> map;

    public static void init() {
        System.out.println("Inizio costruzione distanze");

        map = new HashMap<Film, List<Recommendation>>();

        Distance d = new Distance(FilmGraph.getGraph());

        for (Film f1 : Grafo.getFilms()) {
            List<Recommendation> temp = new ArrayList<Recommendation>();
            for (Film f2 : Grafo.getFilms())
                if (!f1.equals(f2)) {
//                    temp.add(new Recommendation(f2, d.passantD(f1, f2)));
//                    temp.add(new Recommendation(f2, d.passantDW(f1, f2)));
//                    temp.add(new Recommendation(f2, d.passantI(f1, f2)));
//                    temp.add(new Recommendation(f2, d.passantIW(f1, f2)));
//                    temp.add(new Recommendation(f2, d.passantC(f1, f2)));
                    temp.add(new Recommendation(f2, d.passantCW(f1, f2)));
                    // temp.add(new Recommendation(f2, d.nostra(f1, f2)));
                }
            Collections.sort(temp);
            map.put(f1, temp);
        }

        System.out.println("Fine costruzione distanze");
    }

    public static double getDistance(Film a, Film b) {
        for (Recommendation r : map.get(a))
            if (b.equals(r.getFilm()))
                return r.getDistance();
        return Double.MAX_VALUE;
    }

    public static List<Recommendation> getRecommendations(Profile profile, int limit) {
        List<Recommendation> temp = new ArrayList<Recommendation>();

        for (Film film : Grafo.getFilms())
            if (!profile.getLikedFilms().contains(film)) {
                double distance = 0d;
                for (Film liked : profile.getLikedFilms()) {
                    distance += getDistance(film, liked);
                }
                temp.add(new Recommendation(film, distance));
            }

        Collections.sort(temp);

        if (limit == 0)
            return temp;

        List<Recommendation> toRec = new ArrayList<Recommendation>(limit);

        for (int i = 0; i < (limit <= toRec.size() ? limit : toRec.size()); i++)
            toRec.add(temp.get(i));

        return toRec;
    }

    public static List<Recommendation> getRecommendations(Profile profile) {
        return getRecommendations(profile, 0);
    }


}