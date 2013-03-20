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


    public SimpleProfile(Collection<Film> profiled) {
        this.profiledFilms = profiled;
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

    @Override
    public Collection<Film> getFilms() {
        return profiledFilms;
    }

    @Override
    public void addFilm(Film film) {
        profiledFilms.add(film);
    }

    @Override
    public void removeFilm(Film film) {
        profiledFilms.remove(film);
    }
}
