package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.HashMap;
import java.util.Map;

public class NostraVotedProfile extends VotedProfile {

    public NostraVotedProfile() {
        this.votedFilms = new HashMap<Film, Number>();
    }

    public NostraVotedProfile(Map<Film, Number> map) {
        this.votedFilms = map;
    }

    public double weight(Film film) {
        double vote = this.votedFilms.get(film).doubleValue();
//        if (vote == 5.0)
//            return -1.0 / vote;
//        else if (vote == 4.0)
//            return -1.0 / (2.0 * vote);
//        else if (vote == 3.0)
//            return 0;
//        else if (vote == 2.0)
//            return 1.0 / (2.0 * vote);
//        else if (vote == 1.0)
//            return 1.0 / vote;
//        return 0;
        if (vote == 1.0)
            return -0.2;
        else if (vote == 2.0)
            return -0.125;
        else if (vote == 3.0)
            return 0;
        else if (vote == 4.0)
            return 0.25;
        else if (vote == 5.0)
            return 1;
        return 0;
    }
}
