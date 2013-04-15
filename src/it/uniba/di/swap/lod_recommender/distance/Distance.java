package it.uniba.di.swap.lod_recommender.distance;

import it.uniba.di.swap.lod_recommender.graph.Film;
import it.uniba.di.swap.lod_recommender.graph.Graph;
import it.uniba.di.swap.lod_recommender.recommendation.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 03/04/13
 * Time: 13.50
 */
public abstract class Distance {

    protected Map<Pair, Number> map;
    protected String name;
    protected Distance d;

    protected Distance(String name) {
        this.name = name;
    }

    protected void init() {
        try {
            FileInputStream fis;
            ObjectInputStream ois;
            fis = new FileInputStream("./serialized/" + this.name + ".bin");
            ois = new ObjectInputStream(fis);
            map = (ConcurrentHashMap<Pair, Number>) ois.readObject();
            System.out.println(name + ".bin Caricato con successo.");
            ois.close();
            fis.close();
        } catch (Exception e) {
            this.compute();
        }
    }

    protected void compute() {
        this.map = new ConcurrentHashMap<Pair, Number>(Distances.NUM_COPPIE_FILM);
        System.out.println(name + ".bin non presente. Procedo alla creazione.");
        this.fill();
        this.save();
    }

    public final Number getDistance(Film f1, Film f2) {
        return map.get(new Pair(f1, f2));
    }

    public abstract Number computeDistance(Film f1, Film f2);

    public final Map<Pair, Number> getMap() {
        return map;
    }

    public final void fill() {
        ArrayList<Film> films = Graph.getFilms();
        ArrayList<ComputeDistance> threads = new ArrayList<ComputeDistance>(8);

        for (int i = 0; i < 8; i++) {
            threads.add(new ComputeDistance(this));
        }

        for (int i = 0; i < films.size(); i++) {
            threads.get(i % 8).getSubset().add(films.get(i));
        }

        for (int i = 0; i < 8; i++) {
            threads.get(i).start();
        }

        for (int i = 0; i < 8; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public final void save() {
        FileOutputStream fos;
        ObjectOutputStream o;
        try {
            if (new File("./serialized").mkdirs()) {
            } else {
                throw new Exception("Impossibile creare la directory.");
            }
            fos = new FileOutputStream("./serialized/" + this.name + ".bin");
            o = new ObjectOutputStream(fos);
            o.writeObject(map);
            o.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
