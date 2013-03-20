package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.Film;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 14.41
 */
public class VotedProfile {

    protected Map<Film, Number> votedFilms;

    public Map<Film, Number> getFilmVotes() {
        return votedFilms;
    }

    public void addFilm(Film film, Number vote) {
        votedFilms.put(film, vote);
    }

    public void removeFilm(Film film) {
        votedFilms.remove(film);
    }

    public Number getVote(Film film) {
        return votedFilms.get(film);
    }
}
