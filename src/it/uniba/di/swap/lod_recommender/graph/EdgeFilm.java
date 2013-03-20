package it.uniba.di.swap.lod_recommender.graph;

import it.uniba.di.swap.lod_recommender.Film;

import java.io.Serializable;

public class EdgeFilm implements Serializable {

    private String label;
    private Film subject;
    private Film object;
    private double weight;

    public EdgeFilm(String label, Film subject, Film object) {
        this(label, subject, object, 1);
    }

    public EdgeFilm(String label, Film subject, Film object, double weight) {
        this.label = label;
        this.subject = subject;
        this.object = object;
        this.weight = weight;
    }

    public boolean consecutive(EdgeFilm edgeFilm) {
        if (this.getSubject().equals(edgeFilm.getSubject()))
            if (!this.getObject().equals(edgeFilm.getObject()))
                return true;
        if (this.getSubject().equals(edgeFilm.getObject()))
            if (!this.getObject().equals(edgeFilm.getSubject()))
                return true;
        if (this.getObject().equals(edgeFilm.getSubject()))
            if (!this.getSubject().equals(edgeFilm.getObject()))
                return true;
        if (this.getObject().equals(edgeFilm.getObject()))
            if (!this.getSubject().equals(edgeFilm.getSubject()))
                return true;
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeFilm edgeFilm = (EdgeFilm) o;

        if (Double.compare(edgeFilm.weight, weight) != 0) return false;
        String[] labelModified = label.split(" ");
        String labelmodified = labelModified[0] + " " + labelModified[2];
        if (labelmodified != null ? !labelmodified.equals(edgeFilm.getLabelModified()) : edgeFilm.getLabelModified() != null)
            return false;
        if (object != null ? !object.equals(edgeFilm.object) : edgeFilm.object != null) return false;
        if (subject != null ? !subject.equals(edgeFilm.subject) : edgeFilm.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = label != null ? label.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (object != null ? object.hashCode() : 0);
        temp = weight != +0.0d ? Double.doubleToLongBits(weight) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getLabel() {
        return label;
    }

    public Film getSubject() {
        return subject;
    }

    public Film getObject() {
        return object;
    }

    public double getWeight() {
        return weight;
    }

    public String getLabelModified() {
        String[] labelModified = label.split(" ");
        return labelModified[0] + " " + labelModified[2];
    }

    @Override
    public String toString() {
        return "it.uniba.di.swap.lod_recommender.graph.EdgeFilm{" +
                "label='" + label + '\'' +
                ", subject=" + subject +
                ", object=" + object +
                ", weight=" + weight +
                '}';
    }
}
