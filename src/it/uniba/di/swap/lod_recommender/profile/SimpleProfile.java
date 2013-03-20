package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.Film;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 12.40
 */
public class SimpleProfile extends Profile {

    protected Collection<Film> profiledFilms;

    public SimpleProfile(Collection<Film> profiled) {
        this.profiledFilms = profiled;
    }

    public Collection<Film> getFilms() {
        return profiledFilms;
    }

    public void addFilm(Film film) {
        profiledFilms.add(film);
    }

    public void removeFilm(Film film) {
        profiledFilms.remove(film);
    }

    @Override
    public String toString() {
        String s = "";
        for (Film f : profiledFilms) {
            s += f.getTitle() + "\t";
            s += " Vote: " + f.getTitle() + "\n";
        }
        return s;
    }
}
