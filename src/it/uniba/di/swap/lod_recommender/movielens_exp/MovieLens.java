package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.movielens_exp.mysql.DBAccess;
import it.uniba.di.swap.lod_recommender.profile.Profile;
import it.uniba.di.swap.lod_recommender.recommendation.Recommendation;
import it.uniba.di.swap.lod_recommender.recommendation.Recommender;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
    private static final String MOVIELENS_COMPLETE_FILE = "./dataset/movielensComplete";
    private static final String MOVIELENS_TRAINING_FILE = "./dataset/movielensTrain";
    private static final String MOVIELENS_TESTING_FILE = "./dataset/movielensTest";
    private static List<User> users;
    private static List<Film> films;
    private static List<Rating> ratingList;

    static {
        TRAIN_RATE = 0.7;
        TEST_RATE = 1 - TRAIN_RATE;
        films = Graph.getFilms();
        users = User.getUsers();
        NUM_USER = users.size();
        NUM_FILM = films.size();
        ratingList = readFromFile(MOVIELENS_COMPLETE_FILE);
        User.createProfiles();
        splitTrainTest();
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Film> getFilms() {
        return films;
    }

    public static List<Rating> getRatingList() {
        return ratingList;
    }

    private static List<Rating> readFromFile(String path) {
        BufferedReader inp = null;
        ArrayList<Rating> list = new ArrayList<Rating>(55 * NUM_USER);
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

    public static void startExperiment() {
        for (User user : users) {
//            user.print();

            for (Distances.Type t : Distances.Type.values()) {
                Recommender.init(t);
                for (Profile.Type p : Profile.Type.values()) {
                    List<Recommendation> rec = Recommender.getRecommendations(user.getProfile(p), Recommender.ALL);
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

    private static void splitTrainTest() {
        new File("./dataset").mkdirs();
        String trainStr = "";
        String testStr = "";
        for (User u : users) {
            int numFilm = u.getFilms().size();
            int resize = (int) Math.round(numFilm * TRAIN_RATE);
            Collection<Map.Entry<Film, Number>> train = u.getFilms(0, resize);
            Collection<Map.Entry<Film, Number>> test = u.getFilms(resize, numFilm);

            for (Map.Entry<Film, Number> m : train) {
                trainStr += u.getId() + "," + m.getKey().getIdMovieLens() + "," + m.getValue() + "\n";
            }
            for (Map.Entry<Film, Number> m : test) {
                testStr += u.getId() + "," + m.getKey().getIdMovieLens() + "," + m.getValue() + "\n";
            }
        }
        assert trainStr != "";
        assert testStr != "";
        save(MOVIELENS_TRAINING_FILE, trainStr);
        save(MOVIELENS_TESTING_FILE, testStr);
    }
}
