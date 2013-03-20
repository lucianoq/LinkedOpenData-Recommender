package it.uniba.di.swap.lod_recommender;

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

        if (Double.compare(that.distance, distance) != 0) return false;
        if (film != null ? !film.equals(that.film) : that.film != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = film != null ? film.hashCode() : 0;
        temp = distance != +0.0d ? Double.doubleToLongBits(distance) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public int compareTo(Recommendation recommendation) {
        if (this.distance < recommendation.distance)
            return -1;
        if (this.distance > recommendation.distance)
            return 1;
        if (this.distance == recommendation.distance)
            return 0;
        return 0;
    }
}
