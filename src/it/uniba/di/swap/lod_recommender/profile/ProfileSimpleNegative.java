package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.util.Collection;
import java.util.Collections;

public class ProfileSimpleNegative extends ProfileSimple {

    protected Collection<Film> profiledNegative = null;

    public ProfileSimpleNegative(Collection<Film> profiled, Collection<Film> profiledNegative) {
        super(profiled);
        this.profiledNegative = profiledNegative;
        if ( !Collections.disjoint(profiled, profiledNegative) )
            try {
                throw new Exception("Film duplicati");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    }

    @Override
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


    @Override
    public boolean isIn(Film f) {
        boolean b = profiledFilms.contains(f) || profiledNegative.contains(f);
        return b;
    }
}
