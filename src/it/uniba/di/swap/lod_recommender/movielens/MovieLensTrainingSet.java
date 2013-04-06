package it.uniba.di.swap.lod_recommender.movielens;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Simone
 * Date: 07/04/13
 * Time: 1.24
 * To change this template use File | Settings | File Templates.
 */
public class MovieLensTrainingSet {

    public static final double HALFPERCENT = 0.5;
    public static final double SEVENTYPERCENT = 0.7;
    public static final double ALL = 1;

    public static void init(double perc) {
        try {
            MovieLensVoting.init();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Integer> user = MovieLensVoting.users();
        new File("./config/trainingSet").mkdirs();
        for (int i : user) {
            String s = "";
            int resize = (int) Math.round(MovieLensVoting.getNumbersFilmsVotedByUser(i) * perc);
            Map<Film, Number> userFilm = MovieLensVoting.getFilmsVotedByUserMap(i, resize);
            for (Film f : userFilm.keySet()) {
                s += i + "," + f.getIdMovieLens() + "," + userFilm.get(f).intValue() + "\n";
            }
            save("./config/trainingSet/user" + i + ".txt", s);
        }
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
}
