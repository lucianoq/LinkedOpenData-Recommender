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
        mapTC.put(Type.PASSANTI, DistancePassantI.class);
        mapTC.put(Type.PASSANTC, DistancePassantC.class);
        mapTC.put(Type.PASSANTDW, DistancePassantDW.class);
        mapTC.put(Type.PASSANTIW, DistancePassantIW.class);
        mapTC.put(Type.PASSANTCW, DistancePassantCW.class);
        mapTC.put(Type.NOSTRA, DistanceNostra.class);

        for (Type t : Type.values())
            try {
                Method m = mapTC.get(t).getMethod("getInstance");
                Distance dist = (Distance) m.invoke(null);
                distances.put(t, dist);
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (InvocationTargetException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
    }

    public static enum Type {
        PASSANTD, PASSANTI, PASSANTC, PASSANTDW, PASSANTIW, PASSANTCW, NOSTRA
    }
}