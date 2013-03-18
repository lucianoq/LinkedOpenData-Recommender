import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Profile {
    private Set<Film> likedFilms;

    public Profile(Set<Film> liked) {
        this.likedFilms = liked;
    }

    public Profile(List<Film> liked) {
        this.likedFilms = new HashSet<Film>(liked);
    }

    public Set<Film> getLikedFilms() {
        return likedFilms;
    }

    @Override
    public String toString() {
        String s = "Profile{ likedFilms: \n";
        for (Film f : likedFilms)
            s += f.getTitle() + "\n";
        s += '}';
        return s;
    }
}
