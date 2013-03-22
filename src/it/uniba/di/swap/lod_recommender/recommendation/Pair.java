package it.uniba.di.swap.lod_recommender.recommendation;

import it.uniba.di.swap.lod_recommender.graph.Film;

import java.io.Serializable;

public class Pair implements Serializable {
    private Film a;
    private Film b;

    public Pair(Film a, Film b) {
        this.a = a;
        this.b = b;
    }

    public Film getA() {
        return a;
    }

    public Film getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (a != null ? !a.equals(pair.a) : pair.a != null) return false;
        if (b != null ? !b.equals(pair.b) : pair.b != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
