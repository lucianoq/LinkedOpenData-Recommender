package it.uniba.di.swap.lod_recommender.profile;

import it.uniba.di.swap.lod_recommender.Film;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 14.41
 */
public abstract class Profile {

    protected Collection<Film> profiledFilms;

    public abstract Collection<Film> getFilms();

    public abstract void addFilm(Film film);

    public abstract void removeFilm(Film film);
}
