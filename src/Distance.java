
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Simone
 */
public class Distance {

    private static UndirectedSparseMultigraph<Film, EdgeFilm> filmGraph;

    public Distance(UndirectedSparseMultigraph<Film, EdgeFilm> filmGraph) {
        this.filmGraph = filmGraph;
    }

    // Direct Distance
    public double ldsd(Film f1, Film f2) {
        Collection<EdgeFilm> edgeSet = filmGraph.findEdgeSet(f1, f2);
        return 1.0 / (1.0 + 2.0 * edgeSet.size());
    }

    public double ldsdWeighted(Film f1, Film f2) {
        Collection<EdgeFilm> edgeSetDirect = filmGraph.findEdgeSet(f1, f2);
        Collection<EdgeFilm> edgeSetGlobal = filmGraph.getEdges();
        double fattA = 0.0;
        double fattB = 0.0;
        for (EdgeFilm edgeFilm : edgeSetDirect) {
            int countSrc = 0;
            int countDest = 0;
            for (EdgeFilm edgeFilmGlobal : edgeSetGlobal) {

                // Arco tra x e f2
                if (edgeFilm.getObject().equals(edgeFilmGlobal.getObject()))
                    if (!edgeFilm.getSubject().equals(edgeFilmGlobal.getSubject()))
                        countSrc++;


                // Arco tra f1 e x
                if (edgeFilm.getSubject().equals(edgeFilmGlobal.getSubject()))
                    if (!edgeFilm.getObject().equals(edgeFilmGlobal.getObject()))
                        countDest++;

            }
            fattA += 1.0 / (1.0 + Math.log(countSrc));
            fattB += 1.0 / (1.0 + Math.log(countDest));
        }

        double ldsdWeighted = 1.0 / (1.0 + fattA + fattB);
        return ldsdWeighted;
    }

    //Indirect distance
    public double ldsdIndirect(Film f1, Film f2) {
        double ldsdIndirect=1.0/(1.0+ciocii(f1,f2));

        return ldsdIndirect;
    }

    // Numero di archi che partendo dalle risorse ra e rb arrivano ad una risorsa in comune
    // Numero di archi che hanno come origine n e come arrivo ra ed rb
    // in poche parole
    // numero di archi tra du film con una risorsa in mezzo
    private static int ciocii(Film f1, Film f2) {

        Collection<EdgeFilm> edgef1 = filmGraph.getIncidentEdges(f1);
        Collection<EdgeFilm> edgef2 = filmGraph.getIncidentEdges(f2);

        int numArchi = 0;
        for (EdgeFilm ef1 : edgef1)
            for (EdgeFilm ef2 : edgef2)
                    if (ef1.getLabel().equals(ef2.getLabel()))
                        if ( ef1.consecutive(ef2) )
                            numArchi++;
        return numArchi;
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
