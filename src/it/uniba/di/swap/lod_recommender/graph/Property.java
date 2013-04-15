package it.uniba.di.swap.lod_recommender.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Property extends GraphResource implements Serializable {

    private int idProperty;
    private double weight;


    public Property(String uri, int idProperty, double weight) {
        super(uri);
        this.idProperty = idProperty;
        this.weight = weight;
    }

    public static ArrayList<Property> readFromFile(String path) throws IOException {
        BufferedReader inp = new BufferedReader(new FileReader(path));
        ArrayList<Property> properties = new ArrayList<Property>(15);
        String tmp;
        String[] tmp1;
        while ((tmp = inp.readLine()) != null) {
            tmp1 = tmp.split("\t");
            Property tempFilmProp = new Property(tmp1[1], Integer.parseInt(tmp1[0]), Double.parseDouble( tmp1[2] ));
            properties.add(tempFilmProp);
        }
        inp.close();
        return properties;
    }

    public double getWeight() {
        return weight;
    }

    public int getIdProperty() {
        return idProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        return this.idProperty == property.idProperty;

    }

    @Override
    public int hashCode() {
        return idProperty;
        //int result = super.hashCode();
        //result = 31 * result + idProperty;
        //return result;
    }

    @Override
    public String toString() {
        return "it.uniba.di.swap.lod_recommender.graph.Property{" +
                "idProperty=" + idProperty +
                ", uri='" + this.getUri() + "\'" +
                '}';
    }
}
