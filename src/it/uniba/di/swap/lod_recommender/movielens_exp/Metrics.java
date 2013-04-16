package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.movielens_exp.mysql.DBAccess;
import it.uniba.di.swap.lod_recommender.profile.Profile;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;
import it.uniba.di.swap.lod_recommender.recommendation.Recommender;

import java.util.*;

public class Metrics {
    private static Map<Configuration, Map<User, Result>> results;
    private static Map<Configuration, ResultAgg> resultsAgg;

    static {
        results = new HashMap<Configuration, Map<User, Result>>(Profile.Type.values().length * Distances.Type.values().length * 4);
        for (Configuration c : Configuration.getConfigurations()) {
            results.put(c, new HashMap<User, Result>(MovieLens.NUM_USER));
        }
        resultsAgg = new HashMap<Configuration, ResultAgg>(Profile.Type.values().length * Distances.Type.values().length * 6);
    }

    public static void init() {

        computeResults();

        computeAgg();

        fillDatabaseResults();

        fillDatabaseResultsAgg();
    }

    private static void computeResults() {
        System.out.println(new Date() + " [INFO] Start computeResults.");
        for (Configuration c : Configuration.getConfigurations()) {
            System.out.println(new Date() + "   [INFO] Configuration: " + c.toString());
            for (User user : User.getUsers()) {
                Result r = new Result(c, user);
                fillPrecision(r);
                fillMrr(r);
                results.get(c).put(user, r);
            } //for User
        }
    }

    private static void computeAgg() {
        System.out.println(new Date() + " [INFO] Start computeAggregate.");
        for (Configuration c : Configuration.getConfigurations()) {
            double microPrec = Metrics.microPrecision(c);
            double macroPrec = Metrics.macroPrecision(c);
            double microPrec_T = Metrics.microPrecision_T(c);
            double macroPrec_T = Metrics.macroPrecision_T(c);
            double microMRR = Metrics.microMRR(c);
            double macroMRR = Metrics.macroMRR(c);
            double microMRR_T = Metrics.microMRR_T(c);
            double macroMRR_T = Metrics.macroMRR_T(c);

            ResultAgg ra = new ResultAgg(c);

            ra.setMicroPrecision(microPrec);
            ra.setMacroPrecision(macroPrec);
            ra.setMicroPrecision_T(microPrec_T);
            ra.setMacroPrecision_T(macroPrec_T);
            ra.setMicroMRR(microMRR);
            ra.setMacroMRR(macroMRR);
            ra.setMicroMRR_T(microMRR_T);
            ra.setMacroMRR_T(macroMRR_T);

            resultsAgg.put(c, ra);
        }
    }

    private static double microMRR(Configuration c) {
        double sommatoria = 0;
        double ideal = 0;
        for (User user : User.getUsers()) {
            sommatoria += results.get(c).get(user).getSumInverseRank();
            ideal += results.get(c).get(user).getIdealInverseRank();
        }
        return sommatoria / ideal;
    }

    private static double microMRR_T(Configuration c) {
        double sommatoria = 0;
        double ideal = 0;
        for (User user : User.getUsers()) {
            sommatoria += results.get(c).get(user).getSumInverseRank_T();
            ideal += results.get(c).get(user).getIdealInverseRank_T();
        }
        return sommatoria / ideal;
    }

    private static double macroMRR(Configuration c) {
        double sommatoria = 0;
        for (User user : User.getUsers()) {
            sommatoria += results.get(c).get(user).getMrr();
        }
        return sommatoria / User.getUsers().size();
    }

    private static double macroMRR_T(Configuration c) {
        double sommatoria = 0;
        for (User user : User.getUsers()) {
            sommatoria += results.get(c).get(user).getMrr_T();
        }
        return sommatoria / User.getUsers().size();
    }

    private static double macroPrecision(Configuration c) {
        double sommatoria = 0;
        for (User u : User.getUsers()) {
            sommatoria += results.get(c).get(u).getPrecision();
        }
        return sommatoria / User.getUsers().size();
    }

    private static double macroPrecision_T(Configuration c) {
        double sommatoria = 0;
        for (User u : User.getUsers()) {
            sommatoria += results.get(c).get(u).getPrecision_T();
        }
        return sommatoria / User.getUsers().size();
    }

    private static boolean like(User user, Film film) {
        Rating rating = new Rating(user, film, null);
        return MovieLens.getDbTestPositive().get(user).contains(rating);
    }

    private static boolean inTest(User u, Recommendation r) {
        Film f = r.getFilm();
        for (Rating rat : MovieLens.getDbTest().get(u))
            if (rat.getFilm().equals(f))
                return true;
        return false;
    }

    private static double microPrecision(Configuration c) {
        double tpAll = 0;
        double fpAll = 0;
        for (User u : User.getUsers()) {
            tpAll += results.get(c).get(u).getTp();
            fpAll += results.get(c).get(u).getFp();
        }
        return tpAll / (tpAll + fpAll);
    }

    private static double microPrecision_T(Configuration c) {
        double tpAll = 0;
        double fpAll = 0;
        for (User u : User.getUsers()) {
            tpAll += results.get(c).get(u).getTp_T();
            fpAll += results.get(c).get(u).getFp_T();
        }
        return tpAll / (tpAll + fpAll);
    }

    private static void fillPrecision(Result r) {
        r.setPositive(getRec(r.getConfiguration(), r.getUser()));
        r.setNegative(getNotRec(r.getConfiguration(), r.getUser()));
        r.setPositive_T(getRec_T(r.getConfiguration(), r.getUser()));
        r.setNegative_T(getNotRec_T(r.getConfiguration(), r.getUser()));
        Map<String, Integer> tfpn = computeTFPN(r.getUser(), r.getPositive(), r.getNegative());
        Map<String, Integer> tfpn_T = computeTFPN(r.getUser(), r.getPositive_T(), r.getNegative_T());
        r.setTp(tfpn.get("TP"));
        r.setTn(tfpn.get("TN"));
        r.setFp(tfpn.get("FP"));
        r.setFn(tfpn.get("FN"));
        r.setTp_T(tfpn_T.get("TP"));
        r.setTn_T(tfpn_T.get("TN"));
        r.setFp_T(tfpn_T.get("FP"));
        r.setFn_T(tfpn_T.get("FN"));
        r.setPrecision(precision(tfpn));
        r.setPrecision_T(precision(tfpn_T));
    }

    private static void fillMrr(Result r) {
        List<Recommendation> recs = Recommender.getRecommendations(r.getConfiguration(), r.getUser().getProfile(r.getConfiguration().getProfile()), Recommender.ALL);
        double sommatoria = 0;
        double ideal = 0;
        int i = 1;
        for (Recommendation rec : recs) {
            ideal += 1.0d / i;
            if (like(r.getUser(), rec.getFilm())) {
                sommatoria += 1.0d / i;
            }
            i++;
        }
        r.setSumInverseRank(sommatoria);
        r.setIdealInverseRank(ideal);
        r.setMrr(sommatoria / ideal);

        sommatoria = 0;
        ideal = 0;
        i = 1;
        for (Recommendation rec : recs)
            if (inTest(r.getUser(), rec)) {
                ideal += 1.0d / i;
                if (like(r.getUser(), rec.getFilm())) {
                    sommatoria += 1.0d / i;
                }
                i++;
            }
        r.setSumInverseRank_T(sommatoria);
        r.setIdealInverseRank_T(ideal);
        r.setMrr_T(sommatoria / ideal);
    }

    private static List<Recommendation> getRec_T(Configuration c, User user) {
        List<Recommendation> recs = Recommender.getRecommendations(
                c,
                user.getProfile(c.getProfile()),
                Recommender.ALL);
        List<Recommendation> only_T = new ArrayList<Recommendation>();

        Set<Film> testFilms = new HashSet<Film>();
        for (Rating r : MovieLens.getDbTest().get(user))
            testFilms.add(r.getFilm());

        for (Recommendation r : recs) {
            if (only_T.size() == c.getK())
                return only_T;
            if (testFilms.contains(r.getFilm()))
                only_T.add(r);
        }
        return only_T;
    }

    private static List<Recommendation> getNotRec_T(Configuration c, User user) {
        List<Recommendation> tutti = Recommender.getRecommendations(c, user.getProfile(c.getProfile()), Recommender.ALL);
        List<Recommendation> positive = getRec_T(c, user);
        tutti.removeAll(positive);
        return tutti;
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

    private static double precision(Map<String, Integer> tfpn) {
        return tfpn.get("TP").doubleValue() / (tfpn.get("TP").doubleValue() + tfpn.get("FP").doubleValue());
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

    private static void fillDatabaseResults() {
        DBAccess.truncate(DBAccess.RESULTS);

        DBAccess.openConnection(DBAccess.RESULTS);
        System.out.println(new Date() + " [INFO] Fill table of computeResults.");

        for (Configuration c : Configuration.getConfigurations()) {
            for (User user : User.getUsers())
                DBAccess.insertRES(
                        c.getDistance().ordinal(),
                        c.getProfile().ordinal(),
                        c.getK(),
                        user.getId(),
                        results.get(c).get(user).getTp(),
                        results.get(c).get(user).getTn(),
                        results.get(c).get(user).getFp(),
                        results.get(c).get(user).getFn(),
                        results.get(c).get(user).getTp_T(),
                        results.get(c).get(user).getTn_T(),
                        results.get(c).get(user).getFp_T(),
                        results.get(c).get(user).getFn_T(),
                        results.get(c).get(user).getSumInverseRank(),
                        results.get(c).get(user).getSumInverseRank_T(),
                        results.get(c).get(user).getIdealInverseRank(),
                        results.get(c).get(user).getIdealInverseRank_T());
            DBAccess.commit(DBAccess.RESULTS);
        }
        DBAccess.close(DBAccess.RESULTS);
    }

    private static void fillDatabaseResultsAgg() {
        DBAccess.truncate(DBAccess.RESULTS_AGG);

        DBAccess.openConnection(DBAccess.RESULTS_AGG);
        System.out.println(new Date() + " [INFO] Fill table of computeAggregate.");
        for (Configuration c : Configuration.getConfigurations())
            DBAccess.insertAGG(
                    c.getDistance().ordinal(),
                    c.getProfile().ordinal(),
                    c.getK(),
                    resultsAgg.get(c).getMicroPrecision(),
                    resultsAgg.get(c).getMacroPrecision(),
                    resultsAgg.get(c).getMicroPrecision_T(),
                    resultsAgg.get(c).getMacroPrecision_T(),
                    resultsAgg.get(c).getMicroMRR(),
                    resultsAgg.get(c).getMacroMRR(),
                    resultsAgg.get(c).getMicroMRR_T(),
                    resultsAgg.get(c).getMacroMRR_T()
            );

        DBAccess.commit(DBAccess.RESULTS_AGG);
        DBAccess.close(DBAccess.RESULTS_AGG);
    }


}
