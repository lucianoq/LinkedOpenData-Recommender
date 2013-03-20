package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 14.41
 */
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
            System.out.println(f);
            //s += f.getTitle() + "\t";
            //s += " Vote: " + f.getTitle() + "\n";
        }
        return s;
    }


}
