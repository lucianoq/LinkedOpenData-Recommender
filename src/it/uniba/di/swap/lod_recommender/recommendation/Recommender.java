package it.uniba.di.swap.lod_recommender.recommendation;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.profile.SimpleProfile;
import it.uniba.di.swap.lod_recommender.profile.SimpleProfileNegative;
import it.uniba.di.swap.lod_recommender.profile.VotedProfile;

import java.util.*;

public class Recommender {

    public static final int ALL = 0;
    private static Map<Film, List<Recommendation>> map;

    public static void init(String type) {
        System.out.println("[INFO] Inizio creazione tabelle di raccomandazione");
        map = new HashMap<Film, List<Recommendation>>();

        for (Film f1 : Graph.getFilms()) {
            List<Recommendation> temp = new ArrayList<Recommendation>();
            for (Film f2 : Graph.getFilms())
                if (!f1.equals(f2)) {
                    //System.out.println(new java.util.Date() + " sto per fare " + f1.getTitle() + " con " + f2.getTitle());
                    double tmp = 0.0;

                    if (type.equals("Nostra"))
                        tmp = Distance.getDistanceNostra(f1, f2);
                    else if (type.equals("NostraDW"))
                        tmp = Distance.getDistanceNostraDW(f1, f2);
                    else if (type.equals("NostraIIW"))
                        tmp = Distance.getDistanceNostraIIW(f1, f2);
                    else if (type.equals("NostraIOW"))
                        tmp = Distance.getDistanceNostraIOW(f1, f2);
                    else if (type.equals("PassantCW"))
                        tmp = Distance.getDistancePassantCW(f1, f2);
                    else if (type.equals("PassantDW"))
                        tmp = Distance.getDistancePassantDW(f1, f2);
                    else if (type.equals("PassantIW"))
                        tmp = Distance.getDistancePassantIW(f1, f2);
                    else if (type.equals("PassantD"))
                        tmp = Distance.getDistancePassantD(f1, f2);
                    else if (type.equals("PassantI"))
                        tmp = Distance.getDistancePassantI(f1, f2);
                    else if (type.equals("PassantC"))
                        tmp = Distance.getDistancePassantC(f1, f2);

                    temp.add(new Recommendation(f2, tmp));
                    //System.out.println(new java.util.Date() + "it.uniba.di.swap.lod_recommender.recommendation.Recommendation: " + tmp);
                }
            Collections.sort(temp);
            map.put(f1, temp);
        }
        System.out.println("[INFO] Fine creazione tabelle di raccomandazione");
    }

    public static double getDistance(Film a, Film b) {
        for (Recommendation r : map.get(a))
            if (b.equals(r.getFilm()))
                return r.getDistance();
        return Double.MAX_VALUE;
    }

    public static List<Recommendation> getRecommendations(VotedProfile profile, int limit) {
        List<Recommendation> temp = new ArrayList<Recommendation>();

        for (Film film : Graph.getFilms())
            if (!profile.getFilmVotes().keySet().contains(film)) {
                double distance = 0d;
                for (Film liked : profile.getFilmVotes().keySet()) {
                    distance += getDistance(film, liked) * profile.weight(liked);
                }
                temp.add(new Recommendation(film, distance));
            }

        Collections.sort(temp);

        if (limit == ALL)
            return temp;

        List<Recommendation> toRec = new ArrayList<Recommendation>(limit);

        for (int i = 0; i < (limit <= temp.size() ? limit : temp.size()); i++)
            toRec.add(temp.get(i));

        return toRec;
    }

    public static List<Recommendation> getRecommendations(SimpleProfile profile, int limit) {
        List<Recommendation> temp = new ArrayList<Recommendation>();

        for (Film film : Graph.getFilms())
            if (!profile.isIn(film)) {
                double distance = 0d;
                for (Film liked : profile.getFilms()) {
                    distance += getDistance(film, liked);
                }
                temp.add(new Recommendation(film, distance));
            }

        Collections.sort(temp);

        if (limit == ALL)
            return temp;

        List<Recommendation> toRec = new ArrayList<Recommendation>(limit);

        for (int i = 0; i < (limit <= temp.size() ? limit : temp.size()); i++)
            toRec.add(temp.get(i));

        return toRec;
    }

    public static List<Recommendation> getRecommendations(SimpleProfile profile) {
        return getRecommendations(profile, ALL);
    }

    public static List<Recommendation> getRecommendations(VotedProfile profile) {
        return getRecommendations(profile, ALL);
    }


}
