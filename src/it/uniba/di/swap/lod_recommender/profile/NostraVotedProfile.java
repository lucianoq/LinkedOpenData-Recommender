package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 15.27
 */
public class NostraVotedProfile extends VotedProfile {

    public NostraVotedProfile() {
        this.votedFilms = new HashMap<Film, Number>();
    }

    public NostraVotedProfile(Map<Film, Number> map) {
        this.votedFilms = map;
    }

    public double weight(Film film) {
        double vote = this.votedFilms.get(film).doubleValue();
        if (vote == 5)
            return -1 / vote;
        else if (vote == 4)
            return -1 / (2 * vote);
        else if (vote == 3)
            return 0;
        else if (vote == 2)
            return 1 / (2 * vote);
        else if (vote == 1)
            return 1 / vote;
        return 0;
    }
}
