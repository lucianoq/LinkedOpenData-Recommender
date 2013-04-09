package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.profile.Profile;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;
import it.uniba.di.swap.lod_recommender.recommendation.Recommender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 08/04/13
 * Time: 11.50
 */
public class Testing {
    private static Map<User, List<Rating>> mapList;

    static {
        mapList = new HashMap<User, List<Rating>>(User.getUsers().size());
    }

    public static Map<User, List<Rating>> getMapList() {
        return mapList;
    }

    public static void init() {
        for (User u : User.getUsers()) {
                   //TODO
        }
    }

    public static double precisionAtK(User user, Distances.Type d, Profile.Type p, int k) {
        List<Recommendation> recs = Recommender.getRecommendations(user.getProfile(p), Recommender.ALL);
        return 0;
    }
}
