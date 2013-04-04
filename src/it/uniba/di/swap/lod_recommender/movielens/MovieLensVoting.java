package it.uniba.di.swap.lod_recommender.movielens;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieLensVoting {

    private static final String DBMOVIELENSVOTING = "./config/VotesMovielens.txt";
    public static ArrayList<MovieLensType> dbmovielens;

    public static void init() throws IOException {
        dbmovielens = readFromFile(DBMOVIELENSVOTING);
    }

    public static ArrayList<Integer> users() {
        HashSet set = new HashSet();
        for (MovieLensType m : dbmovielens)
            set.add(m.getIdUser());

        ArrayList<Integer> users = new ArrayList<Integer>();
        Iterator i = set.iterator();
        while (i.hasNext())
            users.add((Integer) i.next());
        return users;
    }

    public static ArrayList<MovieLensType> userVotes(int idUser) {
        ArrayList<MovieLensType> userVotes = new ArrayList<MovieLensType>();
        for (MovieLensType m : dbmovielens)
            if (idUser == m.getIdUser())
                userVotes.add(m);
        return userVotes;
    }

    public static Map<Film, Number> getFilmsVotedByUser(int id) {
        Map<Film, Number> userVotes = new HashMap<Film, Number>(55);
        for (MovieLensType m : dbmovielens)
            if (id == m.getIdUser())
                userVotes.put(Film.getFilmByID( m.getIdItem() ), m.getRating());
        return userVotes;
    }


    private static ArrayList<MovieLensType> readFromFile(String path) throws IOException {
        BufferedReader inp = new BufferedReader(new FileReader(path));
        ArrayList<MovieLensType> dbmovielens = new ArrayList<MovieLensType>();
        String tmpstr;
        String[] tmp;
        while ((tmpstr = inp.readLine()) != null)
            if (!tmpstr.startsWith("#")) {
                tmp = tmpstr.split(",");
                MovieLensType movieLens = new MovieLensType(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
                dbmovielens.add(movieLens);
            }
        inp.close();
        return dbmovielens;
    }
}
