package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.Configuration;
import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.profile.Profile;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;
import it.uniba.di.swap.lod_recommender.recommendation.Recommender;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 12/04/13
 * Time: 14.27
 */
public class Metrics {
    private static Map<Configuration, Map<User, Result>> results;

    static {
        results = new HashMap<Configuration, Map<User, Result>>(Profile.Type.values().length * Distances.Type.values().length * 4);
        for (Configuration c : Configuration.getConfigurations()) {
            results.put(c, new HashMap<User, Result>(MovieLens.NUM_USER));
        }
    }

    public static void compute() {
        System.out.println("Calcolo la precisione");

        for (Configuration c : Configuration.getConfigurations()) {
            System.out.println("Inizio calcolo computazione " + c.toString());
            for (User user : User.getUsers()) {
                System.out.print(user.getId() + "");
                Result r = new Result(c, user);
                r.setPositive(getRec(c, user));
                r.setNegative(getNotRec(c, user));
                r.setPositiveInTest(getRecInTest(c, user));
                r.setNegativeInTest(getNotRecInTest(c, user));
                Map<String, Integer> tfpn = computeTFPN(user, r.getPositive(), r.getNegative());
                Map<String, Integer> tfpnInTest = computeTFPN(user, r.getPositiveInTest(), r.getNegativeInTest());
                r.setTp(tfpn.get("TP"));
                r.setTn(tfpn.get("TN"));
                r.setFp(tfpn.get("FP"));
                r.setFn(tfpn.get("FN"));
                r.setTpInTest(tfpnInTest.get("TP"));
                r.setTnInTest(tfpnInTest.get("TN"));
                r.setFpInTest(tfpnInTest.get("FP"));
                r.setFnInTest(tfpnInTest.get("FN"));
                r.setPrecisionAtK(precision(tfpn));
                r.setPrecisionAtKInTest(precision(tfpnInTest));
                r.setrPrecision(rPrecision(c, user));
                r.setMrr(doMRR(c, user));

                results.get(c).put(user, r);

            } //for User
            System.out.println();
        }
    }

    private static Map<String, Integer> computeTFPN(User user, List<Recommendation> positive, List<Recommendation> negative) {
        Map<String, Integer> tfpn = new HashMap<String, Integer>(4);
        int tp = 0;
        int tn = 0;
        int fp = 0;
        int fn = 0;

        for (Recommendation r : positive)
            if (like(user, r.getFilm()))
                tp++;
            else
                fp++;
        for (Recommendation r : negative)
            if (!like(user, r.getFilm()))
                tn++;
            else
                fn++;

        tfpn.put("TP", tp);
        tfpn.put("TN", tn);
        tfpn.put("FP", fp);
        tfpn.put("FN", fn);

        return tfpn;
    }

    private static double rPrecision(Configuration c, User user) {
        int r = MovieLens.getDbTestPositive().get(user).size();
        List<Recommendation> recs = Recommender.getRecommendations(
                c,
                user.getProfile(c.getProfile()),
                r);
        double matched = 0;
        for (Recommendation rec : recs)
            if (like(user, rec.getFilm()))
                matched++;
        return matched / r;
    }

    private static double doMRR(Configuration c, User user) {
        List<Recommendation> recs = Recommender.getRecommendations(c, user.getProfile(c.getProfile()), Recommender.ALL);
        double sommatoria = 0;
        for (Recommendation r : recs)
            if (like(user, r.getFilm())) {
                sommatoria += 1.0d / (recs.indexOf(r) + 1);
            }
        return sommatoria;
    }

    private static boolean like(User user, Film film) {
        Rating rating = new Rating(user, film, null);
        return MovieLens.getDbTestPositive().get(user).contains(rating);
    }

    private static List<Recommendation> getNotRecInTest(Configuration c, User user) {
        List<Recommendation> tutti = Recommender.getRecommendations(c, user.getProfile(c.getProfile()), Recommender.ALL);
        List<Recommendation> positive = getRecInTest(c, user);
        tutti.removeAll(positive);
        return tutti;
    }

    private static boolean inTest(User u, Recommendation r) {
        Film f = r.getFilm();
        for (Rating rat : MovieLens.getDbTest().get(u))
            if (rat.getFilm().equals(f))
                return true;
        return false;
    }

    private static List<Recommendation> getRecInTest(Configuration c, User user) {
        List<Recommendation> recs = Recommender.getRecommendations(
                c,
                user.getProfile(c.getProfile()),
                Recommender.ALL);
        List<Recommendation> onlyInTest = new ArrayList<Recommendation>();

        Set<Film> testFilms = new HashSet<Film>();
        for (Rating r : MovieLens.getDbTest().get(user))
            testFilms.add(r.getFilm());

        for (Recommendation r : recs) {
            if (onlyInTest.size() == c.getK())
                return onlyInTest;
            if (testFilms.contains(r.getFilm()))
                onlyInTest.add(r);
        }
        return onlyInTest;
    }

    private static double precision(Map<String, Integer> tfpn) {
        return tfpn.get("TP").doubleValue() / (tfpn.get("TP").doubleValue() + tfpn.get("FP").doubleValue());
    }

    private static List<Recommendation> getNotRec(Configuration c, User user) {
        List<Recommendation> tutti = Recommender.getRecommendations(c, user.getProfile(c.getProfile()), Recommender.ALL);
        List<Recommendation> positive = Recommender.getRecommendations(c, user.getProfile(c.getProfile()), c.getK());
        tutti.removeAll(positive);
        return tutti;
    }

    private static List<Recommendation> getRec(Configuration c, User user) {
        return Recommender.getRecommendations(c, user.getProfile(c.getProfile()), c.getK());
    }

    public static double microPrecision(Configuration c) {
        double tpAll = 0;
        double fpAll = 0;
        for (User u : User.getUsers()) {
            List<Recommendation> positive = Metrics.getRec(c, u);
            List<Recommendation> negative = Metrics.getNotRec(c, u);
            Map<String, Integer> map = Metrics.computeTFPN(u, positive, negative);
            tpAll += map.get("TP");
            fpAll += map.get("FP");
        }
        return tpAll / (tpAll + fpAll);
    }

    public static double macroPrecision(Configuration c) {
        double sommatoria = 0;
        for (User u : User.getUsers()) {
            sommatoria += results.get(c).get(u).getPrecisionAtK();
        }
        return sommatoria / User.getUsers().size();
    }

    public static double microMRR(Configuration c) {
        double sommatoria = 0;
        for (User user : User.getUsers()) {
            List<Recommendation> recs = Recommender.getRecommendations(c, user.getProfile(c.getProfile()), Recommender.ALL);

            for (Recommendation r : recs)
                if (like(user, r.getFilm())) {
                    sommatoria += 1.0d / (recs.indexOf(r) + 1);
                    break;
                }
        }
        return sommatoria / User.getUsers().size();
    }

    public static double macroMRR(Configuration c) {
        double sommatoria = 0;
        for (User user : User.getUsers()) {
            sommatoria += doMRR(c, user);
        }
        return sommatoria / User.getUsers().size();
    }

    public static double microPrecisionInTest(Configuration c) {
        double tpAll = 0;
        double fpAll = 0;
        for (User u : User.getUsers()) {
            List<Recommendation> positive = Metrics.getRecInTest(c, u);
            List<Recommendation> negative = Metrics.getNotRecInTest(c, u);
            Map<String, Integer> map = Metrics.computeTFPN(u, positive, negative);
            tpAll += map.get("TP");
            fpAll += map.get("FP");
        }
        return tpAll / (tpAll + fpAll);
    }

    public static double macroPrecisionInTest(Configuration c) {
        double sommatoria = 0;
        for (User u : User.getUsers()) {
            sommatoria += results.get(c).get(u).getPrecisionAtKInTest();
        }
        return sommatoria / User.getUsers().size();
    }
}
