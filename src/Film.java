import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 12/03/13
 * Time: 0.40
 */
public class Film extends Risorsa implements Serializable, Comparable<Film> {

    private int idMovieLens;

    public Film(String uri, int idMovieLens) {
        super(uri);
        this.idMovieLens = idMovieLens;
    }

    public static ArrayList<Film> readFromFile(String path) throws IOException {
        BufferedReader inp = new BufferedReader(new FileReader(path));
        ArrayList<Film> films = new ArrayList<Film>();
        String tmp;
        String[] tmp1;
        while ((tmp = inp.readLine()) != null)
            if (!tmp.startsWith("#")) {
                tmp1 = tmp.split("\t");
                Film tempFilm = new Film(tmp1[2], Integer.parseInt(tmp1[0]));
                films.add(tempFilm);
            }
        Collections.sort(films);
        inp.close();
        return films;
    }

    public int getIdMovieLens() {
        return idMovieLens;
    }

    @Override
    public String toString() {
        return "Film{" +
                "idMovieLens=" + idMovieLens +
                ", title='" + this.title + "\'" +
                ", uri='" + this.uri + "\'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Film film = (Film) o;

        if (idMovieLens != film.idMovieLens) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + idMovieLens;
        return result;
    }

    @Override
    public int compareTo(Film o) {
        return (this.idMovieLens - o.getIdMovieLens());
    }
}
