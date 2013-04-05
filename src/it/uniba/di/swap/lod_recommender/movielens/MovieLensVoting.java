package it.uniba.di.swap.lod_recommender.movielens;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MovieLensVoting {

//    private static final String DBMOVIELENSVOTING = "./config/VotesMovielens.txt";
    private static final String DBMOVIELENSVOTING = "./config/utente1_train";
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

    public static List<Map.Entry<Film, Number>> getFilmsVotedByUserSorted(int id) {
        Map<Film, Number> userVotes = new HashMap<Film, Number>(55);
        for (MovieLensType m : dbmovielens)
            if (id == m.getIdUser())
                userVotes.put(Film.getFilmByID(m.getIdItem()), m.getRating());

        List<Map.Entry<Film, Number>> sorted = new LinkedList<Map.Entry<Film, Number>>(userVotes.entrySet());
        Collections.sort(sorted, new ValueComparer(ValueComparer.DESC));
        return sorted;
    }

    public static Map<Film, Number> getFilmsVotedByUserMap(int id) {
        Map<Film, Number> userVotes = new HashMap<Film, Number>(55);
        for (MovieLensType m : dbmovielens)
            if (id == m.getIdUser())
                userVotes.put(Film.getFilmByID(m.getIdItem()), m.getRating());

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

    /**
     * inner class to do soring of the map *
     */
    private static class ValueComparer implements Comparator<Map.Entry<Film, Number>> {
        public static final int ASC = 1;
        public static final int DESC = -1;
        private int orderingType;

        public ValueComparer() {
            this.orderingType = ASC;
        }

        public ValueComparer(int orderingType) {
            this.orderingType = orderingType;
        }

        @Override
        public int compare(Map.Entry<Film, Number> filmNumberEntry, Map.Entry<Film, Number> filmNumberEntry2) {
            return orderingType*Double.compare(filmNumberEntry.getValue().doubleValue(), filmNumberEntry2.getValue().doubleValue());
        }
    }
}