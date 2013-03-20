package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.Film;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 15.27
 */
public class MustoVotedProfile extends VotedProfile {

    public MustoVotedProfile() {
        this.votedFilms = new HashMap<Film, Number>();
    }

    public MustoVotedProfile(Map<Film, Number> map) {
        this.votedFilms = map;
    }

    public double weight(Film film) {
        return (this.getMaxVote() + 1 - this.votedFilms.get(film).doubleValue());
    }
}
