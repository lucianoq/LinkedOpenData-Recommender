package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Collection;

public class ProfileSimple extends Profile {

    protected Collection<Film> profiledFilms;

    public ProfileSimple(Collection<Film> profiled) {
        this.profiledFilms = profiled;
    }

    public Collection<Film> getFilms() {
        return profiledFilms;
    }

    public boolean isIn(Film f) {
        return profiledFilms.contains(f);
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
            s += f.getTitle() + "\n";
        }
        return s;
    }
}
