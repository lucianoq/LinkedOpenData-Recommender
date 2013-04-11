package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.Configuration;
import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens_exp.mysql.DBAccess;
import it.uniba.di.swap.lod_recommender.profile.Profile;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;
import it.uniba.di.swap.lod_recommender.recommendation.Recommender;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 08/04/13
 * Time: 12.55
 */
public class MovieLens {
    public static final double TRAIN_RATE;
    public static final double TEST_RATE;
    public static final int NUM_USER;
    public static final int NUM_FILM;
    public static final String MOVIELENS_COMPLETE_FILE = "./dataset/movielensComplete";
    public static final String MOVIELENS_TRAINING_FILE = "./dataset/movielensTrain";
    public static final String MOVIELENS_TESTING_FILE = "./dataset/movielensTest";
    private static Map<User, List<Rating>> db;
    private static Map<User, List<Rating>> dbTrain;
    private static Map<User, List<Rating>> dbTest;
    private static Map<User, Set<Rating>> dbTestPositive;

    static {
        TRAIN_RATE = 0.7;
        TEST_RATE = 1 - TRAIN_RATE;
        NUM_USER = User.getUsers().size();
        NUM_FILM = Graph.getFilms().size();

        db = new HashMap<User, List<Rating>>();
        dbTrain = new HashMap<User, List<Rating>>();
        dbTest = new HashMap<User, List<Rating>>();
        dbTestPositive = new HashMap<User, Set<Rating>>();

        for (User u : User.getUsers()) {
            db.put(u, new ArrayList<Rating>());
            dbTrain.put(u, new ArrayList<Rating>());
            dbTest.put(u, new ArrayList<Rating>());
            dbTestPositive.put(u, new HashSet<Rating>());
        }

        System.out.println("Leggo Database da File");

        List<Rating> list = read(MOVIELENS_COMPLETE_FILE);
        for (Rating r : list) {
            db.get(r.getUser()).add(r);
        }

        System.out.println("Creo lo split");

//        createSplit();
        readSplit();

        System.out.println("Creo i profili degli utenti");
        User.createProfiles();

        System.err.println("DB size " + db.size());
        System.err.println("DB utente 1 size " + db.get(User.getUserByID(1)).size());
        System.err.println("DBTRAIN size " + dbTrain.size());
        System.err.println("DBTRAIN utente 1 size " + dbTrain.get(User.getUserByID(1)).size());
        System.err.println("DBTEST size " + dbTest.size());
        System.err.println("DBTEST utente 1 size " + dbTest.get(User.getUserByID(1)).size());
        System.err.println("DBTESTPOSITIVE size " + dbTestPositive.size());
        System.err.println("DBTESTPOSITIVE utente 1 size " + dbTestPositive.get(User.getUserByID(1)).size());

    }

    private static void save(String dir, String content) {
        FileWriter outFile = null;
        try {
            outFile = new FileWriter(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = new PrintWriter(outFile);
        out.print(content);
        out.close();
    }

    private static List<Rating> read(String path) {
        List<Rating> list = new ArrayList<Rating>();
        BufferedReader inp = null;
        try {
            inp = new BufferedReader(new FileReader(path));

            String tmpstr;
            String[] tmp;
            while ((tmpstr = inp.readLine()) != null)
                if (!tmpstr.startsWith("#")) {
                    tmp = tmpstr.split(",");
                    Rating movieLens = new Rating(
                            User.getUserByID(Integer.parseInt(tmp[0])),
                            Film.getFilmByID(Integer.parseInt(tmp[1])),
                            Integer.parseInt(tmp[2]));
                    list.add(movieLens);
                }
            inp.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    public static void computePrecisions() {
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(new File("./OUT")));
            System.out.println("Calcolo la precisione");
            for (User user : User.getUsers())
                for (Distances.Type d : Distances.Type.values())
                    for (Profile.Type p : Profile.Type.values())
                        for (int k : new ArrayList<Integer>() {{
//                            add(1);
                            add(5);
//                            add(10);
//                            add(20);
//                            add(50);
                            add(100);
                            add(518);
                        }}) {
                            double prec1 = precisionAtK(user, d, p, k);
                            double prec2 = precisionAtKOnlyInTest(user, d, p, k);
                            double prec3 = rPrecision(user, d, p);
                            double prec4 = doMRR(user, d, p, k);

                            out.print("Utente: " + user.getId() + "; Distanza: " + d.name());
                            out.println("; Profilo: " + p.name() + "; k: " + k + "; ");
                            out.println("Precisione a k: " + prec1);
                            out.println("Precisione a k epurata: " + prec2);
                            out.println("R Precisione: " + prec3);
                            out.println("MRR: " + prec4);
                            out.println();
                        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fillDatabase() {
        System.out.println("Riempio il database");
        for (User user : User.getUsers()) {
            for (Distances.Type t : Distances.Type.values()) {
                for (Profile.Type p : Profile.Type.values()) {
                    Configuration c = new Configuration(t, p);
                    List<Recommendation> rec = Recommender.getRecommendations(c, user.getProfile(p), Recommender.ALL);
                    for (Recommendation r : rec) {
                        DBAccess.insert(
                                r.getFilm().getIdMovieLens(),
                                user.getId(),
                                rec.indexOf(r),
                                t.ordinal(),
                                p.ordinal(),
                                r.getFilm().getTitle(),
                                t.name(),
                                p.name());
                    }
                }
            }
            DBAccess.commit();
        }
        DBAccess.close();
    }

    private static void createSplit() {
        new File("./dataset").mkdirs();
        String trainStr = "";
        String testStr = "";
        for (User u : User.getUsers()) {
            int numFilm = db.get(u).size();
            int resize = (int) Math.round(numFilm * TRAIN_RATE);

            List<Rating> listRatingUser = new ArrayList<Rating>(db.get(u));
            Collections.shuffle(listRatingUser);

            Collection<Rating> train = listRatingUser.subList(0, resize);
            Collection<Rating> test = listRatingUser.subList(resize, numFilm);

            for (Rating r : train) {
                trainStr += u.getId() + "," + r.getFilm().getIdMovieLens() + "," + r.getRating() + "\n";
                dbTrain.get(u).add(r);
            }
            for (Rating r : test) {
                testStr += u.getId() + "," + r.getFilm().getIdMovieLens() + "," + r.getRating() + "\n";
                dbTest.get(u).add(r);
                if (r.getRating().doubleValue() > Profile.MEDIUMVOTE)
                    dbTestPositive.get(u).add(r);
            }
        }
        assert trainStr != "";
        assert testStr != "";
        save(MOVIELENS_TRAINING_FILE, trainStr);
        save(MOVIELENS_TESTING_FILE, testStr);
        MovieLens.fillDatabase();
    }

    private static void readSplit() {
        List<Rating> list = read(MOVIELENS_TRAINING_FILE);
        for (Rating r : list)
            dbTrain.get(r.getUser()).add(r);
        list = read(MOVIELENS_TESTING_FILE);
        for (Rating r : list) {
            dbTest.get(r.getUser()).add(r);
            if (r.getRating().doubleValue() > Profile.MEDIUMVOTE)
                dbTestPositive.get(r.getUser()).add(r);
        }
    }

    public static double precisionAtK(User user, Distances.Type d, Profile.Type p, int k) {
        assert k > 0;
        Configuration c = new Configuration(d, p);
        List<Recommendation> recs = Recommender.getRecommendations(c, user.getProfile(p), k);
        double matched = 0;
        for (Recommendation r : recs)
            if (like(user, r.getFilm()))
                matched++;
        return matched / k;
    }

    public static double precisionAtKOnlyInTest(User user, Distances.Type d, Profile.Type p, int k) {
        assert k > 0;
        List<Recommendation> recs = Recommender.getRecommendations(
                new Configuration(d, p),
                user.getProfile(p),
                Recommender.ALL);
        System.err.println("RECS PRIMA DEL RETAIN: " + recs.size());
        recs.retainAll(dbTest.get(user));
        System.err.println("DOPO IL RETAIN: " + recs.size());
        try {
            if (k < recs.size())
                recs = recs.subList(0, k);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("RECS SIZE: " + recs.size());
            System.err.println("K: " + k);
        }
        double matched = 0;
        for (Recommendation r : recs)
            if (like(user, r.getFilm()))
                matched++;
        return matched / k;
    }

    public static double rPrecision(User user, Distances.Type d, Profile.Type p) {
        int r = dbTestPositive.get(user).size();
        List<Recommendation> recs = Recommender.getRecommendations(
                new Configuration(d, p),
                user.getProfile(p),
                r);
        double matched = 0;
        for (Recommendation rec : recs)
            if (like(user, rec.getFilm()))
                matched++;
        return matched / r;
    }

    public static double doMRR(User user, Distances.Type d, Profile.Type p, int q) {
        Configuration c = new Configuration(d, p);
        List<Recommendation> recs = Recommender.getRecommendations(c, user.getProfile(p), q);
        double sommatoria = 0;
        for (Recommendation r : recs)
            if (like(user, r.getFilm()))
                sommatoria += 1 / (recs.indexOf(r) + 1);
        return sommatoria / q;
    }

    private static boolean like(User user, Film film) {
        Rating rating = new Rating(user, film, null);
        return dbTestPositive.get(user).contains(rating);
    }

    public static Map<User, List<Rating>> getDbTrain() {
        return dbTrain;
    }


}