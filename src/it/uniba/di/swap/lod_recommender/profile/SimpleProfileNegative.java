package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Collection;

public class SimpleProfileNegative extends SimpleProfile {

    protected Collection<Film> profiledFilmsNegative = null;

    public SimpleProfileNegative(Collection<Film> profiled, Collection<Film> profiledFilmsNegative) {
        super(profiled);
        this.profiledFilmsNegative = profiledFilmsNegative;
    }

    @Override
    public Collection<Film> getFilms() {
        Collection<Film> tmp = profiledFilms;
        for (Film f : profiledFilmsNegative)
            if (tmp.contains(f))
                tmp.remove(f);
        return tmp;
    }

    public Collection<Film> getProfiledFilmsNegative() {
        return profiledFilmsNegative;
    }

    public void addFilmNegative(Film film) {
        profiledFilmsNegative.add(film);
    }

    public void removeFilmNegative(Film film) {
        profiledFilmsNegative.remove(film);
    }

    @Override
    public String toString() {
        String s = "Film negative: ";
        for (Film f : profiledFilmsNegative) {
            s += f.getTitle() + "\n";
        }
        s = "Film positive: ";
        for (Film f : getFilms()) {
            s += f.getTitle() + "\n";
        }
        return s;
    }
}
