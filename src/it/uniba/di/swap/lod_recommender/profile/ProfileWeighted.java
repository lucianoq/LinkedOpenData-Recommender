package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Map;

public class ProfileWeighted extends Profile {
    protected Map<Film, Number> votedFilms;

    public ProfileWeighted(Map<Film, Number> map) {
        this.votedFilms = map;
    }

    public int getSize() {
        return votedFilms.size();
    }

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

    @Override
    public String toString() {
        String s = "";
        for (Film f : votedFilms.keySet()) {
            s += f.getTitle() + "\t";
            s += " Vote: " + votedFilms.get(f) + "\n";
        }
        return s;
    }

    public double weight(Film film) {
        return this.votedFilms.get(film).intValue() - Profile.MEDIUMVOTE;
    }
}
