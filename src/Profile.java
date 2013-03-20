import java.util.Map;
import java.util.Set;

public class Profile {
    private Map<Film, Integer> likedFilms;

    public Profile(Map<Film, Integer> liked) {
        this.likedFilms = liked;
    }

    public Set<Film> getLikedFilms() {
        return likedFilms.keySet();
    }


    @Override
    public String toString() {
        String s="";
        for (Film f : likedFilms.keySet()) {
            s += f.getTitle() + "\t";
            s += " Vote: " + likedFilms.get(f) + "\n";
        }
        return s;
    }
}
