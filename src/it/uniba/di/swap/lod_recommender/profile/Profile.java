package it.uniba.di.swap.lod_recommender.profile;

public abstract class Profile {
    public static final int MAXVOTE = 5;
    public static final int MINVOTE = 1;
    public static final int MEDIUMVOTE = 3;
    public static final int GRANULARITYVOTE = 1;

    public static enum Type {
        SIMPLE,
        SIMPLE_NEGATIVE,
        VOTED_NOSTRA,
        VOTED_MUSTO
    }
}
