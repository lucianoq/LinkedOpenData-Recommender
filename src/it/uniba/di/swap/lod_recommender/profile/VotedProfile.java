package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Map;

public abstract class VotedProfile extends Profile {

    protected Map<Film, Number> votedFilms;
    private boolean hasNegativeVote;

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

    public abstract double weight(Film film);

    @Override
    public String toString() {
        String s = "";
        for (Film f : votedFilms.keySet()) {
            s += f.getTitle() + "\t";
            s += " Vote: " + votedFilms.get(f) + "\n";
        }
        return s;
    }


}
