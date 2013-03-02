public class Predicato implements Comparable<Predicato> {
	private String	uri;
	private double	weight;

	public Predicato(String s) {
		uri = s;
		weight = 1;
	}

	public Predicato(String s, double d) {
		uri = s;
		weight = d;
	}

	public String toString() {
		return uri;
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
		return this.uri.equals((Predicato) obj);
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
