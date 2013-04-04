package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.HashMap;
import java.util.Map;

public class ProfileVotedNostra extends ProfileVoted {

    public ProfileVotedNostra() {
        this.votedFilms = new HashMap<Film, Number>();
    }

    public ProfileVotedNostra(Map<Film, Number> map) {
        this.votedFilms = map;
    }

    public double weight(Film film) {
        int vote = this.votedFilms.get(film).intValue();

        if (vote == 1)
            return -1;
        else if (vote == 2)
            return -0.5;
        else if (vote == 3)
            return 0;
        else if (vote == 4)
            return 0.5;
        else if (vote == 5)
            return 1;
        return 0;
    }
}
