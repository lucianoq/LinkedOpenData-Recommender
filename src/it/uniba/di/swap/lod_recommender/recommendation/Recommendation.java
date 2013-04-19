package it.uniba.di.swap.lod_recommender.recommendation;

import it.uniba.di.swap.lod_recommender.graph.Film;

public class Recommendation implements Comparable<Recommendation> {
    private Film film;
    private double distance;

    public Recommendation(Film film, double distance) {
        this.film = film;
        this.distance = distance;
    }

    public Film getFilm() {
        return film;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recommendation that = (Recommendation) o;

        if (!film.equals(that.film)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return film.hashCode();
    }

    @Override
    public int compareTo(Recommendation recommendation) {
        if (this.distance < recommendation.distance)
            return -1;
        if (this.distance > recommendation.distance)
            return 1;
        if (this.distance == recommendation.distance)
            return 0;

        assert false;
        return 0;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "film=" + film +
                ", distance=" + distance +
                '}';
    }
}
