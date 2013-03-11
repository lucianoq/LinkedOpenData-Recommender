
import edu.uci.ics.jung.graph.Graph;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Simone
 */
public class Distance<V,E> {

    private Graph<V, E> graph;

    public Distance(Graph<V, E> graph) {
        this.graph = graph;
    }

    public double ldsd(V v1,V v2) {
        
        return graph.getEdgeCount();
    }
}
