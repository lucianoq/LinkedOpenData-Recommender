package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.profile.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class User {

    private static Map<Integer, User> users;

    static {
        users = new HashMap<Integer, User>();
        BufferedReader inp = null;
        String path = "./config/users";
        try {
            inp = new BufferedReader(new FileReader(path));

            String tmpstr;
            while ((tmpstr = inp.readLine()) != null) {
                int id = Integer.parseInt(tmpstr);
                User u = new User(id);
                users.put(id, u);
            }
            inp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int id;
    private Map<Film, Number> seenInTrain;
    private Map<Film, Number> likedInTrain;
    private List<Map.Entry<Film, Number>> list;
    private ProfileSimple profileSimple;
    private ProfileSimple profileSimpleNegative;
    private ProfileVoted profileVotedNostra;
    private ProfileVoted profileVotedMusto;


    private User(int id) {
        this.id = id;
    }

    public static void createProfiles() {
        System.out.println(new Date() + " [INFO] Create user profiles.");
        for (User user : users.values()) {
            user.seenInTrain = new HashMap<Film, Number>(55);
            user.likedInTrain = new HashMap<Film, Number>(55);
            for (Rating r : MovieLens.getDbTrain().get(user))
                user.seenInTrain.put(r.getFilm(), r.getRating());

            user.list = new ArrayList<Map.Entry<Film, Number>>(user.seenInTrain.entrySet());
            Collections.shuffle(user.list);

            Map<Film, Number> mapPos = new HashMap<Film, Number>();
            Map<Film, Number> mapNeg = new HashMap<Film, Number>();
            for (Map.Entry<Film, Number> me : user.seenInTrain.entrySet()) {
                if (me.getValue().doubleValue() < Profile.MEDIUMVOTE)
                    mapNeg.put(me.getKey(), me.getValue());
                if (me.getValue().doubleValue() > Profile.MEDIUMVOTE)
                    mapPos.put(me.getKey(), me.getValue());
            }
            user.likedInTrain = mapPos;
            user.profileSimple = new ProfileSimple(user.seenInTrain.keySet());
            user.profileSimpleNegative = new ProfileSimpleNegative(mapPos.keySet(), mapNeg.keySet());
            user.profileVotedNostra = new ProfileVotedNostra(user.seenInTrain);
            user.profileVotedMusto = new ProfileVotedMusto(user.seenInTrain);
        }
    }

    public static User getUserByID(int id) {
        return users.get(id);
    }

    public static List<User> getUsers() {
        return new ArrayList<User>(users.values());
    }

    public int getId() {
        return id;
    }

    public Profile getProfile(Profile.Type type) {
        switch (type) {
            case SIMPLE:
                return profileSimple;
            case SIMPLE_NEGATIVE:
                return profileSimpleNegative;
            case VOTED_MUSTO:
                return profileVotedMusto;
            case VOTED_NOSTRA:
                return profileVotedNostra;
        }
        return null;
    }

    public void print() {
        System.out.println("---- Profile User " + getId() + "--------");
        List<Map.Entry<Film, Number>> list = getFilmsSorted();
        for (Map.Entry<Film, Number> me : list)
            System.out.println("Film: " + me.getKey().getTitle() + "\t\tVote: " + me.getValue().intValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Map<Film, Number> getFilmsSeenInTrain() {
        return this.seenInTrain;
    }

    public List<Map.Entry<Film, Number>> getFilmsSorted() {
        Map<Film, Number> userVotes = new HashMap<Film, Number>(55);
        for (Rating m : MovieLens.getDbTrain().get(this))
            userVotes.put(m.getFilm(), m.getRating());
        List<Map.Entry<Film, Number>> sorted = new LinkedList<Map.Entry<Film, Number>>(userVotes.entrySet());
        Collections.sort(sorted, new ValueComparer(ValueComparer.DESC));
        return sorted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }

    /**
     * inner class to do soring of the seenInTrain *
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
            return orderingType * Double.compare(filmNumberEntry.getValue().doubleValue(), filmNumberEntry2.getValue().doubleValue());
        }
    }
}
