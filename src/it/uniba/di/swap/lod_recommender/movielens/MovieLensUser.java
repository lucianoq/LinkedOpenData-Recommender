package it.uniba.di.swap.lod_recommender.movielens;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.profile.*;

import java.util.HashMap;
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
        map = MovieLensVoting.getFilmsVotedByUser(id);
        Map<Film, Number> mapPos = new HashMap<Film, Number>();
        Map<Film, Number> mapNeg = new HashMap<Film, Number>();
        for (Film f : map.keySet()) {
            if (map.get(f).intValue() < Profile.MEDIUMVOTE)
                mapNeg.put(f, map.get(f));
            if (map.get(f).intValue() > Profile.MEDIUMVOTE)
                mapPos.put(f, map.get(f));
        }
        profileSimple = new ProfileSimple(map.keySet());
        profileSimpleNegative = new ProfileSimpleNegative(mapPos.keySet(), mapNeg.keySet());
        profileVotedNostra = new ProfileVotedNostra(map);
        profileVotedMusto = new ProfileVotedMusto(map);
    }

    public int getId() {
        return id;
    }

    public ProfileSimple getProfileSimple() {
        return profileSimple;
    }

    public ProfileSimple getProfileSimpleNegative() {
        return profileSimpleNegative;
    }

    public ProfileVoted getProfileVotedNostra() {
        return profileVotedNostra;
    }

    public ProfileVoted getProfileVotedMusto() {
        return profileVotedMusto;
    }

    public void print() {
        for (Film f : map.keySet()) {
            //TODO sort map by values
            System.out.println("Film: " + f.getTitle() + "\t\tVote: " + map.get(f).intValue());
        }
    }
}
