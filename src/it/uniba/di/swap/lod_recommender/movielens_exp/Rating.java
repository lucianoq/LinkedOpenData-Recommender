package it.uniba.di.swap.lod_recommender.movielens_exp;

import it.uniba.di.swap.lod_recommender.graph.Film;

public class Rating {
    private User user;
    private Film film;
    private Number rating;

    public Rating(User user, Film film, Number rating) {
        this.user = user;
        this.film = film;
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public Film getFilm() {
        return film;
    }

    public Number getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (film != null ? !film.equals(rating.film) : rating.film != null) return false;
        if (user != null ? !user.equals(rating.user) : rating.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (film != null ? film.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "user=" + user +
                ", film=" + film +
                ", rating=" + rating +
                '}';
    }
}
