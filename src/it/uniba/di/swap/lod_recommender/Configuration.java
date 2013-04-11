package it.uniba.di.swap.lod_recommender;

import it.uniba.di.swap.lod_recommender.distance.Distances;
import it.uniba.di.swap.lod_recommender.profile.Profile;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 10/04/13
 * Time: 9.45
 */
public class Configuration {
    private Distances.Type distance;
    private Profile.Type profile;

    public Configuration(Distances.Type distance, Profile.Type profile) {
        this.distance = distance;
        this.profile = profile;
    }

    public Distances.Type getDistance() {
        return distance;
    }

    public Profile.Type getProfile() {
        return profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (distance != that.distance) return false;
        if (profile != that.profile) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = distance != null ? distance.hashCode() : 0;
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        return result;
    }
}
