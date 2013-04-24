package it.uniba.di.swap.lod_recommender.distance;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Distances implements Serializable {

    public static final int NUM_COPPIE_FILM = 270000;
    public static Map<Type, Class> mapTC;
    public static Map<Type, Distance> distances;

    public static void init() {
        mapTC = new HashMap<Type, Class>();
        distances = new HashMap<Type, Distance>();

        mapTC.put(Type.PASSANTD, DistancePassantD.class);
//        mapTC.put(Type.PASSANTI, DistancePassantI.class);
        mapTC.put(Type.PASSANTC, DistancePassantC.class);
//        mapTC.put(Type.PASSANTDW, DistancePassantDW.class);
//        mapTC.put(Type.PASSANTIW, DistancePassantIW.class);
//        mapTC.put(Type.PASSANTCW, DistancePassantCW.class);
        mapTC.put(Type.PASSANTD_W, DistancePassantD_W.class);
        mapTC.put(Type.PASSANTC_W, DistancePassantC_W.class);

        for (Type t : Type.values())
            try {
                Method m = mapTC.get(t).getMethod("getInstance");
                Distance dist = (Distance) m.invoke(null);
                distances.put(t, dist);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
    }

    public static enum Type {
       PASSANTD,
        //        PASSANTI,
        PASSANTC,
        PASSANTD_W,
        PASSANTC_W
    }
}