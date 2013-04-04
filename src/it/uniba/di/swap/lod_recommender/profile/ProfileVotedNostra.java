package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Map;

public class ProfileVotedNostra extends ProfileVoted {

    public ProfileVotedNostra(Map<Film, Number> map) {
        this.votedFilms = map;
    }

    public double weight(Film film) {
        return this.votedFilms.get(film).intValue() - Profile.MEDIUMVOTE;
    }
}
