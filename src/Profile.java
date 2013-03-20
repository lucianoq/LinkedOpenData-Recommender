import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Profile {

    protected Map<Film, Integer> profiledFilms;

    protected Profile() {
        this.profiledFilms = new HashMap<Film, Integer>();
    }

    public Profile(Map<Film, Integer> liked) {
        this.profiledFilms = liked;
    }

    public Set<Film> getProfiledFilms() {
        return profiledFilms.keySet();
    }

    @Override
    public String toString() {
        String s = "";
        for (Film f : profiledFilms.keySet()) {
            s += f.getTitle() + "\t";
            s += " Vote: " + profiledFilms.get(f) + "\n";
        }
        return s;
    }
}
