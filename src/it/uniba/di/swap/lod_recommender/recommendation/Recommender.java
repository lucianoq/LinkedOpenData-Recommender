package it.uniba.di.swap.lod_recommender.recommendation;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.profile.SimpleProfile;
import it.uniba.di.swap.lod_recommender.profile.VotedProfile;

import java.util.*;

public class Recommender {

    public static final int ALL = 0;
    private static Map<Film, List<Recommendation>> map;

    //        public static final int NOSTRA = 0;
//        public static final int NOSTRADW = 1;
//        public static final int NOSTRAIIW = 2;
//        public static final int NOSTRAIOW = 3;
//        public static final int PASSANTD = 4;
//        public static final int PASSANTDW = 5;
//        public static final int PASSANTI = 6;
//        public static final int PASSANTIW = 7;
//        public static final int PASSANTC = 8;
//        public static final int PASSANTCW = 9;
    public static void init(int type) {
        System.out.println("[INFO] Inizio creazione tabelle di raccomandazione");
        map = new HashMap<Film, List<Recommendation>>();

        for (Film f1 : Graph.getFilms()) {
            List<Recommendation> temp = new ArrayList<Recommendation>();
            for (Film f2 : Graph.getFilms())
                if (!f1.equals(f2)) {
                    //System.out.println(new java.util.Date() + " sto per fare " + f1.getTitle() + " con " + f2.getTitle());
                    double tmp = 0.0;


                    switch (type) {
                        case Distances.NOSTRA:
                            tmp = Distances.getDistanceNostra(f1, f2);
                        case Distances.NOSTRADW:
                            tmp = Distances.getDistanceNostraDW(f1, f2);
                        case Distances.NOSTRAIIW:
                            tmp = Distances.getDistanceNostraIIW(f1, f2);
                        case Distances.NOSTRAIOW:
                            tmp = Distances.getDistanceNostraIOW(f1, f2);
                        case Distances.PASSANTCW:
                            tmp = Distances.getDistancePassantCW(f1, f2);
                        case Distances.PASSANTDW:
                            tmp = Distances.getDistancePassantDW(f1, f2);
                        case Distances.PASSANTIW:
                            tmp = Distances.getDistancePassantIW(f1, f2);
                        case Distances.PASSANTD:
                            tmp = Distances.getDistancePassantD(f1, f2);
                        case Distances.PASSANTI:
                            tmp = Distances.getDistancePassantI(f1, f2);
                        case Distances.PASSANTC:
                            tmp = Distances.getDistancePassantC(f1, f2);
                    }

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
