
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Simone
 */
public class Distance<V, E> {

    private Grafo graph;

    public Distance(Grafo graph) {
        this.graph = graph;
    }

    public double ldsd(Film f1, Film f2) {

        return graph.getGraph().getEdgeCount();
    }

//    private static void distances() {
//        DijkstraShortestPath<Entita, Predicato> sp = new DijkstraShortestPath<Entita, Predicato>(graph);
//        System.out.println("DijkstraShortestPath " + new Date() + "\n");
//        System.out.println("Sto per avviare getPath");
//        Entita star_Trek_First_Contact = new Entita("http://dbpedia.org/resource/Star_Trek:_First_Contact");
//        Entita star_Trek_VI_The_Undiscovered_Country = new Entita("http://dbpedia.org/resource/Star_Trek_VI:_The_Undiscovered_Country");
//
//        System.out.println(sp.getDistance(star_Trek_First_Contact, star_Trek_VI_The_Undiscovered_Country));
//        //out.println(sp.getPath(film1, film2));
//        List<Predicato> path = sp.getPath(star_Trek_First_Contact, star_Trek_VI_The_Undiscovered_Country);
//
//        for (int i = 0; i < path.size(); i++) {
//            System.out.println(path.get(i));
//        }
//
//        Entita american_films = new Entita("http://dbpedia.org/resource/Category:American_films");
//        Entita donald = new Entita("http://dbpedia.org/resource/Donald_Peterman");
//
//        System.out.println(sp.getDistance(american_films, donald));
//        //out.println(sp.getPath(film1, film2));
//        List<Predicato> path2 = sp.getPath(american_films, donald);
//
//        for (int i = 0; i < path2.size(); i++) {
//            System.out.println(path2.get(i));
//        }
//    }
}
