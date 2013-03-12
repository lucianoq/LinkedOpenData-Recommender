import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Grafo g = new Grafo();
        g.createFromQuery();
        g.save();
        //g.load();
        g.printDot();

        //Distance d = new Distance(g);
    }
}
