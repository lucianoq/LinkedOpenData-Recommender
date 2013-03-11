
import java.io.Serializable;

public class Entita implements Comparable<Entita>,Serializable {

	private String	uri;

	public Entita(String s) {
		uri = s;
	}

	public String toString() {
		return uri;
	}

	@Override
	public int compareTo(Entita o) {
		if (this == o)
			return 0;
		return this.uri.compareTo(o.uri);
	}

	@Override
	public boolean equals(Object obj) {
		// if (this == obj)
		// return true;
		// if ((obj == null) || (obj.getClass() != this.getClass()))
		// return false;
		return this.uri.equals(((Entita) obj).uri);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		for (int i = 0; i < uri.length(); i++) {
			hash = hash * 31 + uri.charAt(i);
		}
		return hash;
	}

}
