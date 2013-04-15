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

        db = new HashMap<User, List<Rating>>(NUM_USER);
        dbTrain = new HashMap<User, List<Rating>>(NUM_USER);
        dbTest = new HashMap<User, List<Rating>>(NUM_USER);
        dbTestPositive = new HashMap<User, Set<Rating>>(NUM_USER);


        for (User u : User.getUsers()) {
            db.put(u, new ArrayList<Rating>(NUM_FILM * 4 * 4));
            dbTrain.put(u, new ArrayList<Rating>(NUM_FILM * 4 * 4));
            dbTest.put(u, new ArrayList<Rating>(NUM_FILM * 4 * 4));
            dbTestPositive.put(u, new HashSet<Rating>(NUM_FILM * 4 * 4));
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

        System.out.println("[INFO] FILLO IL DATABASE CON LE RACCOMANDAZIONI");
//        MovieLens.fillDatabase();
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

    public static Map<User, List<Rating>> getDbTest() {
        return dbTest;
    }

    public static Map<User, Set<Rating>> getDbTestPositive() {
        return dbTestPositive;
    }

    public static void fillDatabase() {
        DBAccess.truncate(DBAccess.RECOMMENDATION);
        DBAccess.openConnection(DBAccess.RECOMMENDATION);
        System.out.println("Riempio la tabella delle raccomandazioni");
        for (User user : User.getUsers()) {
//            for (Distances.Type d : Distances.Type.values())
//                for (Profile.Type p : Profile.Type.values())
            Distances.Type d = Distances.Type.NOSTRA;
            Profile.Type p = Profile.Type.VOTED_NOSTRA;
            Configuration c = new Configuration(d, p, 0);
            List<Recommendation> rec = Recommender.getRecommendations(c, user.getProfile(p), Recommender.ALL);
            int i = 1;
            for (Recommendation r : rec) {
                DBAccess.insertREC(
                        d.ordinal(),
                        p.ordinal(),
                        user.getId(),
                        r.getFilm().getIdMovieLens(),
                        i);
                i++;
            }
            DBAccess.commit(DBAccess.RECOMMENDATION);
        }
        DBAccess.close(DBAccess.RECOMMENDATION);
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

    public static Map<User, List<Rating>> getDbTrain() {
        return dbTrain;
    }

}