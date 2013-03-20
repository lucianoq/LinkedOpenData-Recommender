import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: lusio
 * Date: 20/03/13
 * Time: 12.40
 */
public class ProfileSimple extends Profile {
    public ProfileSimple(Set<Film> liked) {
        super();
        for (Film f : liked)
            this.profiledFilms.put(f,0);
    }
}
