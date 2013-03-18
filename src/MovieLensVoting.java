import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MovieLensVoting {

    private static final String DBMOVIELENSVOTING = "./VotesMovielens.txt";
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
