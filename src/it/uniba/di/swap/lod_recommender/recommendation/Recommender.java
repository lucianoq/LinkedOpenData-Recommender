package it.uniba.di.swap.lod_recommender.recommendation;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens_exp.Configuration;
import it.uniba.di.swap.lod_recommender.movielens_exp.MovieLens;
import it.uniba.di.swap.lod_recommender.profile.Profile;
import it.uniba.di.swap.lod_recommender.profile.ProfileNotWeighted;
import it.uniba.di.swap.lod_recommender.profile.ProfileWeighted;

import java.util.*;

public class Recommender {

    public static final int ALL = 0;
    private static Map<Distances.Type, Map<Film, List<Recommendation>>> mapAll;

    static {
        mapAll = new HashMap<Distances.Type, Map<Film, List<Recommendation>>>(Distances.Type.values().length);
        Map<Film, List<Recommendation>> map;
        for (Distances.Type t : Distances.Type.values()) {
            map = new HashMap<Film, List<Recommendation>>(MovieLens.NUM_FILM);

            for (Film f1 : Graph.getFilms()) {
                List<Recommendation> temp = new ArrayList<Recommendation>();
                for (Film f2 : Graph.getFilms())
                    if (!f1.equals(f2)) {
                        double tmp = Distances.distances.get(t).getDistance(f1, f2).doubleValue();
                        temp.add(new Recommendation(f2, tmp));
                    }
                Collections.sort(temp);
                map.put(f1, temp);
            }
            mapAll.put(t, map);
        }
    }

    public static double getDistance(Distances.Type d, Film a, Film b) {
        for (Recommendation r : mapAll.get(d).get(a))
            if (b.equals(r.getFilm()))
                return r.getDistance();
        return Double.MAX_VALUE;
    }

    public static List<Recommendation> getRecommendations(Configuration c, Profile profile, int limit) {
        if (profile instanceof ProfileNotWeighted)
            return getRecommendations(c, (ProfileNotWeighted) profile, limit);
        else if (profile instanceof ProfileWeighted)
            return getRecommendations(c, (ProfileWeighted) profile, limit);

        return null;
    }

    public static List<Recommendation> getRecommendations(Configuration c, Profile profile) {
        return getRecommendations(c, profile, ALL);
    }

    private static List<Recommendation> getRecommendations(Configuration c, ProfileWeighted profile, int limit) {
        List<Recommendation> temp = new ArrayList<Recommendation>();

        for (Film film : Graph.getFilms())
            if (!profile.getFilmVotes().keySet().contains(film)) {
                double distance = 0d;
                for (Film liked : profile.getFilmVotes().keySet()) {
                    distance += getDistance(c.getDistance(), film, liked) * profile.weight(liked);
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

    private static List<Recommendation> getRecommendations(Configuration c, ProfileNotWeighted profile, int limit) {
        List<Recommendation> temp = new ArrayList<Recommendation>();

        for (Film film : Graph.getFilms())
            if (!profile.isIn(film)) {
                double distance = 0d;
                for (Film liked : profile.getFilms()) {
                    distance += getDistance(c.getDistance(), film, liked);
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
}
