package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Map;

public class ProfileVotedMusto extends ProfileVoted {

    public ProfileVotedMusto(Map<Film, Number> map) {
        this.votedFilms = map;
    }

    public double weight(Film film) {
        return (Profile.MAXVOTE + 1.0 - this.votedFilms.get(film).doubleValue());
    }
}
