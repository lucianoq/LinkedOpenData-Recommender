
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Simone
 */
public class Distance {

    private UndirectedSparseMultigraph<Film, EdgeFilm> filmGraph;

    public Distance(UndirectedSparseMultigraph<Film, EdgeFilm> filmGraph) {
        this.filmGraph = filmGraph;
    }

    // Direct Distance
    public double ldsd(Film f1, Film f2) {
        int cd_n_ra_rb = cd_n_ra_rb(f1, f2);
        int cd_n_rb_ra = cd_n_ra_rb(f2, f1);
        return 1.0 / (1.0 + cd_n_ra_rb + cd_n_rb_ra);
    }

    private int cd_n_ra_rb(Film f1, Film f2) {
        return filmGraph.findEdgeSet(f1, f2).size();
    }

    private int cd_li_ra_rb(EdgeFilm edgeFilm, Film f1, Film f2) {
        Collection<EdgeFilm> edgeFilmCollection = filmGraph.findEdgeSet(f1, f2);
        for (EdgeFilm edgeFilm1 : edgeFilmCollection) {
            if (edgeFilm1.equals(edgeFilm))
                return 1;
        }
        return 0;
    }

    private int cd_li_ra_n(EdgeFilm edgeFilm, Film f1) {
        Collection<EdgeFilm> edgeFilmCollectionDirect = filmGraph.getOutEdges(f1);
        int count = 0;
        for (EdgeFilm edgeFilm1 : edgeFilmCollectionDirect) {
            if (edgeFilm1.equals(edgeFilm))
                count++;
        }
        return count;
    }

    //     public double ldsdWeighted(Film f1, Film f2) {
//         Collection<EdgeFilm> edgeSetDirect = filmGraph.findEdgeSet(f1, f2);
//         Collection<EdgeFilm> edgeSetGlobal = filmGraph.getEdges();
//         double fattA = 0.0;
//         double fattB = 0.0;
//         for (EdgeFilm edgeFilm : edgeSetDirect) {
//             int countSrc = 0;
//             int countDest = 0;
//             for (EdgeFilm edgeFilmGlobal : edgeSetGlobal) {
//
//                 // Arco tra x e f2
//                 if (edgeFilm.getObject().equals(edgeFilmGlobal.getObject()))
//                     if (!edgeFilm.getSubject().equals(edgeFilmGlobal.getSubject()))
//                         countSrc++;
//
//
//                 // Arco tra f1 e x
//                 if (edgeFilm.getSubject().equals(edgeFilmGlobal.getSubject()))
//                     if (!edgeFilm.getObject().equals(edgeFilmGlobal.getObject()))
//                         countDest++;
//
//             }
//             fattA += 1.0 / (1.0 + Math.log(countSrc));
//             fattB += 1.0 / (1.0 + Math.log(countDest));
//         }
//
//         double ldsdWeighted = 1.0 / (1.0 + fattA + fattB);
//         return ldsdWeighted;
//     }
//
    private double ldsdWeightedFatt(Film f1, Film f2) {
        Collection<EdgeFilm> edgeSetGlobal = filmGraph.getEdges();
        double fatt = 0.0;
        for (EdgeFilm edgeFilm : edgeSetGlobal) {

            int num = cd_li_ra_rb(edgeFilm, f1, f2);
            double den = 1.0 + Math.log(cd_li_ra_n(edgeFilm, f1));

            double fattTmp = ((double) num) / den;
            fatt += fattTmp;
        }
        return fatt;
    }

    public double ldsdWeighted(Film f1, Film f2) {
        double ldsdWeighted = 1.0 / (1.0 + ldsdWeightedFatt(f1, f2) + ldsdWeightedFatt(f2, f1));
        return ldsdWeighted;
    }

    //Indirect distance
    public double ldsdIndirect(Film f1, Film f2) {
        double ldsdIndirect = 1.0 / (1.0 + cio_n_ra_rb(f1, f2) + cii_n_ra_rb(f1, f2));
        return ldsdIndirect;
    }

    // Numero di archi che partendo dalle risorse ra e rb arrivano ad una risorsa in comune
    // Numero di archi che hanno come origine n e come arrivo ra ed rb
    // in poche parole
    // numero di archi tra du film con una risorsa in mezzo
//    private static int ciocii(Film f1, Film f2) {
//
//        Collection<EdgeFilm> edgef1 = filmGraph.getIncidentEdges(f1);
//        Collection<EdgeFilm> edgef2 = filmGraph.getIncidentEdges(f2);
//
//        int numArchi = 0;
//        for (EdgeFilm ef1 : edgef1)
//            for (EdgeFilm ef2 : edgef2)
//                if (ef1.getLabelModified().equals(ef2.getLabelModified()))
//                    if (ef1.consecutive(ef2))
//                        numArchi++;
//        return numArchi;
//    }

    // Numero di archi che partendo dalle risorse ra e rb arrivano ad una risorsa in comune
    private int cio_n_ra_rb(Film f1, Film f2) {
        Collection<EdgeFilm> edgef1 = filmGraph.getOutEdges(f1);
        Collection<EdgeFilm> edgef2 = filmGraph.getOutEdges(f2);

        int numArchi = 0;
        for (EdgeFilm ef1 : edgef1)
            for (EdgeFilm ef2 : edgef2)
                if (ef1.getSubject().equals(f1) && ef2.getSubject().equals(f2))
                    if (ef1.getLabelModified().equals(ef2.getLabelModified()))
                        if (ef1.getObject().equals(ef2.getObject()))
                            numArchi++;
        return numArchi;
    }


    //Cio(li; ra; rb) equals 1 if there is a resource n that satisfy both <li; ra; n> and <li; rb; n> , 0 if not.
    private int cio_li_ra_rb(EdgeFilm edgeFilm, Film f1, Film f2) {
        Collection<EdgeFilm> edgef1 = filmGraph.getOutEdges(f1);
        Collection<EdgeFilm> edgef2 = filmGraph.getOutEdges(f2);

        for (EdgeFilm ef1 : edgef1)
            for (EdgeFilm ef2 : edgef2)
                if (ef1.getSubject().equals(f1) && ef2.getSubject().equals(f2))
                    if (ef1.getLabelModified().equals(edgeFilm.getLabelModified()))
                        if (ef2.getLabelModified().equals(edgeFilm.getLabelModified()))
                            if (ef1.getObject().equals(ef2.getObject()))
                                return 1;
        return 0;
    }

    // Numero di archi che hanno come origine n e come arrivo ra ed rb
    private int cii_n_ra_rb(Film f1, Film f2) {
        Collection<EdgeFilm> edgef1 = filmGraph.getInEdges(f1);
        Collection<EdgeFilm> edgef2 = filmGraph.getInEdges(f2);

        int numArchi = 0;
        for (EdgeFilm ef1 : edgef1)
            for (EdgeFilm ef2 : edgef2)
                if (ef1.getObject().equals(f1) && ef2.getObject().equals(f2))
                    if (ef1.getLabelModified().equals(ef2.getLabelModified()))
                        if (ef1.getSubject().equals(ef2.getSubject()))
                            numArchi++;
        return numArchi;
    }

    // Cii(li; ra; rb) equals 1 if there is a resource n that satisfy both <li; n; ra> and <li; n; rb>
    private int cii_li_ra_rb(EdgeFilm edgeFilm, Film f1, Film f2) {

        Collection<EdgeFilm> edgef1 = filmGraph.getInEdges(f1);
        Collection<EdgeFilm> edgef2 = filmGraph.getInEdges(f2);

        for (EdgeFilm ef1 : edgef1)
            for (EdgeFilm ef2 : edgef2)
                if (ef1.getObject().equals(f1) && ef2.getObject().equals(f2))
                    if (ef1.getLabelModified().equals(edgeFilm.getLabelModified()))
                        if (ef2.getLabelModified().equals(edgeFilm.getLabelModified()))
                            if (ef1.getSubject().equals(ef2.getSubject()))
                                return 1;
        return 0;
    }


    public double ldsdIndirectWeight(Film f1, Film f2) {
        double ldsdWeighted = 1.0 / (1.0 + ldsdIndirectWeightedFattCii(f1, f2) + ldsdIndirectWeightedFattCio(f1, f2));
        return ldsdWeighted;
    }

    private double ldsdIndirectWeightedFattCii(Film f1, Film f2) {
        Collection<EdgeFilm> edgeSetGlobal = filmGraph.getEdges();
        double fatt = 0.0;
        for (EdgeFilm edgeFilm : edgeSetGlobal) {

            int num = cii_li_ra_rb(edgeFilm, f1, f2);
            double den = 1.0 + Math.log(cii_li_ra_n(edgeFilm, f1));

            double fattTmp = ((double) num) / den;
            fatt += fattTmp;
        }
        return fatt;
    }


    //TODO
    private double cii_li_ra_n(EdgeFilm edgeFilm, Film f1) {

    }

    private double ldsdIndirectWeightedFattCio(Film f1, Film f2) {
        Collection<EdgeFilm> edgeSetGlobal = filmGraph.getEdges();
        double fatt = 0.0;
        for (EdgeFilm edgeFilm : edgeSetGlobal) {

            int num = cio_li_ra_rb(edgeFilm, f1, f2);
            double den = 1.0 + Math.log(cio_li_ra_n(edgeFilm, f1));

            double fattTmp = ((double) num) / den;
            fatt += fattTmp;
        }
        return fatt;
    }

    //TODO
    private double cio_li_ra_n(EdgeFilm edgeFilm, Film f1) {

    }

   /* private static ArrayList<EdgeFilm> ciociiDet(Film f1, Film f2) {

        Collection<EdgeFilm> edgef1 = filmGraph.getIncidentEdges(f1);
        Collection<EdgeFilm> edgef2 = filmGraph.getIncidentEdges(f2);
        ArrayList<EdgeFilm> edgeciocii = new ArrayList<EdgeFilm>();
        for (EdgeFilm ef1 : edgef1)
            for (EdgeFilm ef2 : edgef2)
                if (ef1.getLabelModified().equals(ef2.getLabelModified()))
                    if (ef1.consecutive(ef2)) {
                        edgeciocii.add(ef1);
                        edgeciocii.add(ef2);
                    }
        return edgeciocii;
    }
    */

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
