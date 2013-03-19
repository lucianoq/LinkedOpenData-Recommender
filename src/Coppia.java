import java.io.Serializable;

public class Coppia implements Serializable {
    private Film a;
    private Film b;

    public Coppia(Film a, Film b) {
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

        Coppia coppia = (Coppia) o;

        if (a != null ? !a.equals(coppia.a) : coppia.a != null) return false;
        if (b != null ? !b.equals(coppia.b) : coppia.b != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }
}
