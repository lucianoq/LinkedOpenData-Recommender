import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Property extends Risorsa implements Serializable {

    private int idProperty;


    public Property(String uri, int idProperty) {
        super(uri);
        this.idProperty = idProperty;
    }

    public static ArrayList<Property> readFromFile(String path) throws IOException {
        BufferedReader inp = new BufferedReader(new FileReader(path));
        ArrayList<Property> properties = new ArrayList<Property>();
        String tmp;
        String[] tmp1;
        while ((tmp = inp.readLine()) != null) {
            tmp1 = tmp.split("\t");
            Property tempFilmProp = new Property(tmp1[1], Integer.parseInt(tmp1[0]));
            properties.add(tempFilmProp);
        }
        inp.close();
        return properties;
    }

    public int getIdProperty() {
        return idProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        if (this.idProperty != property.idProperty) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return idProperty;
        //int result = super.hashCode();
        //result = 31 * result + idProperty;
        //return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "idProperty=" + idProperty +
                ", uri='" + this.uri + "\'" +
                '}';
    }
}
