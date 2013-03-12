/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 12/03/13
 * Time: 15.47
 * To change this template use File | Settings | File Templates.
 */
public class EdgeFilm {

    private String label;
    private Film subject;
    private Film object;
    private double weight;

    public EdgeFilm(String label, Film subject, Film object) {
        this(label, subject, object, 1);
    }

    public EdgeFilm(String label, Film subject, Film object, double weight) {
        this.label = label;
        this.subject = subject;
        this.object = object;
        this.weight = weight;
    }

    public String getLabel() {
        return label;
    }

    public Film getSubject() {
        return subject;
    }

    public Film getObject() {
        return object;
    }

    public double getWeight() {
        return weight;
    }


}
