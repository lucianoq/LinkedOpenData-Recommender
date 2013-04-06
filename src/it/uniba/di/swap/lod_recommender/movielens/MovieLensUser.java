package it.uniba.di.swap.lod_recommender.movielens;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.profile.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 04/04/13
 * Time: 15.47
 */
public class MovieLensUser {

    Map<Film, Number> map;
    private int id;
    private ProfileSimple profileSimple;
    private ProfileSimple profileSimpleNegative;
    private ProfileVoted profileVotedNostra;
    private ProfileVoted profileVotedMusto;

    public MovieLensUser(int id) {
        this.id = id;
        map = MovieLensVoting.getFilmsVotedByUserMap(id);
        Map<Film, Number> mapPos = new HashMap<Film, Number>();
        Map<Film, Number> mapNeg = new HashMap<Film, Number>();
        for (Map.Entry<Film, Number> me : map.entrySet()) {
            if (me.getValue().doubleValue() < Profile.MEDIUMVOTE)
                mapNeg.put(me.getKey(), me.getValue());
            if (me.getValue().doubleValue() > Profile.MEDIUMVOTE)
                mapPos.put(me.getKey(), me.getValue());
        }
        profileSimple = new ProfileSimple(map.keySet());
        profileSimpleNegative = new ProfileSimpleNegative(mapPos.keySet(), mapNeg.keySet());
        profileVotedNostra = new ProfileVotedNostra(map);
        profileVotedMusto = new ProfileVotedMusto(map);
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
        List<Map.Entry<Film, Number>> list = MovieLensVoting.getFilmsVotedByUserSorted(getId());
        for (Map.Entry<Film, Number> me : list)
            System.out.println("Film: " + me.getKey().getTitle() + "\t\tVote: " + me.getValue().intValue());

        System.out.println("------- ------- --------");
    }
}
