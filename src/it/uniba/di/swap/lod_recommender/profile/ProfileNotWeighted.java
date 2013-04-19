package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Collection;
import java.util.Collections;

public class ProfileNotWeighted extends Profile {
    protected Collection<Film> profiledFilms;
    protected Collection<Film> profiledNegative = null;

    public ProfileNotWeighted(Collection<Film> profiled, Collection<Film> profiledNegative) {
        profiledFilms = profiled;
        this.profiledNegative = profiledNegative;
        if (!Collections.disjoint(profiled, profiledNegative))
            try {
                throw new Exception("Film duplicati");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    }

    public int getSize() {
        return profiledFilms.size();
    }

    public void addFilm(Film film) {
        profiledFilms.add(film);
    }

    public void removeFilm(Film film) {
        profiledFilms.remove(film);
    }

    public Collection<Film> getFilms() {
        return profiledFilms;
    }

    public Collection<Film> getFilmsNegative() {
        return profiledNegative;
    }

    public void addFilmNegative(Film film) {
        profiledNegative.add(film);
    }

    public void removeFilmNegative(Film film) {
        profiledNegative.remove(film);
    }

    @Override
    public String toString() {
        String s = "\n\tNEGATIVE: \n";
        for (Film f : profiledNegative) {
            s += f.getTitle() + "\n";
        }
        s += "\n\tPOSITIVE: \n";
        for (Film f : getFilms()) {
            s += f.getTitle() + "\n";
        }
        return s;
    }

    public boolean isIn(Film f) {
        boolean b = profiledFilms.contains(f) || profiledNegative.contains(f);
        return b;
    }
}
