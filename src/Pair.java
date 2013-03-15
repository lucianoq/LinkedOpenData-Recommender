/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 15/03/13
 * Time: 19.21
 * To change this template use File | Settings | File Templates.
 */
public class Pair {
    private Film a;
    private Film b;

    public Pair(Film a, Film b) {
        this.a = a;
        this.b = b;
    }

    public Film getA() {
        return a;
    }

    public Film getB() {
        return b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;


        if (this.getA().compareTo(pair.getA());
        if (a != null ? !a.equals(pair.a) : pair.a != null) return false;
        if (b != null ? !b.equals(pair.b) : pair.b != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
