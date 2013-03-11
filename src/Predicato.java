
import java.io.Serializable;


public class Predicato implements Comparable<Predicato>, Serializable {

    private String uri;
    private String subject;
    private String object;
    private double weight;
    //private static int i = 0;
    //private int id;

    public Predicato(String p, String s, String o) {
        uri = p;
        subject = s;
        object = o;
        weight = 1;
//        id = i;
        //  i++;
    }

    public Predicato(String p, String s, String o, double d) {
        uri = p;
        subject = s;
        object = o;
        weight = d;
//        id = i;
//        i++;
    }

    public String toString() {
        return uri + " " + subject + " " + object;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Predicato o) {
        return this.uri.compareTo(o.uri);
    }

    @Override
    public boolean equals(Object obj) {
        // if (this == obj)
        // return true;
        // if ((obj == null) || (obj.getClass() != this.getClass()))
        // return false;
        if (this.uri.equals(((Predicato) obj).uri)) {
            if (this.subject.equals(((Predicato) obj).subject)) {
                if (this.object.equals(((Predicato) obj).object)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        String all = uri + subject + object;
        int hash = 7;
        for (int i = 0; i < all.length(); i++) {
            hash = hash * 31 + all.charAt(i);
        }
        return hash;
    }
}
