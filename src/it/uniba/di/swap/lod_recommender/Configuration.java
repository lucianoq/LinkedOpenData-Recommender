package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.profile.Profile;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private static List<Configuration> configurations;
    private Distances.Type distance;
    private Profile.Type profile;
    private int k;

    static {
        configurations = new ArrayList<Configuration>(64);
        for (Distances.Type d : Distances.Type.values())
            for (Profile.Type p : Profile.Type.values())
                for (int i : new ArrayList<Integer>() {{
                    add(5);
                    add(10);
                    add(20);
                    add(50);
                    add(100);
                }}) {
                    configurations.add(new Configuration(d, p, i));
                }
    }


    public Configuration(Distances.Type distance, Profile.Type profile, int k) {
        this.distance = distance;
        this.profile = profile;
        this.k = k;
    }

    public static List<Configuration> getConfigurations() {
        assert configurations != null;
        return configurations;
    }

    public Distances.Type getDistance() {
        return distance;
    }

    public Profile.Type getProfile() {
        return profile;
    }

    public int getK() {
        return k;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (k != that.k) return false;
        if (distance != that.distance) return false;
        if (profile != that.profile) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = distance != null ? distance.hashCode() : 0;
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + k;
        return result;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "distance=" + distance +
                ", profile=" + profile +
                ", k=" + k +
                '}';
    }
}
