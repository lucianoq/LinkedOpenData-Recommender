package it.uniba.di.swap.lod_recommender.distance;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Distances implements Serializable {

    public static final int NUM_COPPIE_FILM = 270000;
    public static Map<Type, Class> mapTC;
    public static Map<Type, Distance> distances;
//    private static Map<Pair, Double> passantD;
//    private static Map<Pair, Double> passantDW;
//    private static Map<Pair, Double> passantI;
//    private static Map<Pair, Double> passantIW;
//    private static Map<Pair, Double> passantC;
//    private static Map<Pair, Double> passantCW;
//    private static Map<Pair, Double> nostra;
//    private static Map<Pair, Double> nostraDW;
//    private static Map<Pair, Double> nostraIOW;
//    private static Map<Pair, Double> nostraIIW;
//    private static Map<Pair, Integer> cio_n_A_B;
//    private static Map<Pair, Integer> cii_n_A_B;

    public static void init() throws IllegalAccessException, InstantiationException {
        mapTC = new HashMap<Type, Class>();
        distances = new HashMap<Type, Distance>();

        mapTC.put(Type.PASSANTD, DistancePassantD.class);
        mapTC.put(Type.PASSANTI, DistancePassantI.class);
        mapTC.put(Type.PASSANTC, DistancePassantC.class);
        mapTC.put(Type.PASSANTDW, DistancePassantDW.class);
        mapTC.put(Type.PASSANTIW, DistancePassantIW.class);
        mapTC.put(Type.PASSANTCW, DistancePassantCW.class);
        mapTC.put(Type.NOSTRA, DistanceNostra.class);

        for (Type t : Type.values())
            distances.put(t, (Distance) mapTC.get(t).newInstance());
    }

    public static void saveAll() throws IOException {
        for (Type t : Type.values())
            distances.get(t).save();
    }

    public static enum Type {
        PASSANTD, PASSANTI, PASSANTC, PASSANTDW, PASSANTIW, PASSANTCW, NOSTRA
    }
}