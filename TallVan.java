package Vehicle;

import java.io.Serializable;

/**
 * TallVan class, subclass of Vehicle
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class TallVan extends Vehicle implements Serializable {
    private String kind;

    public TallVan(String theLicencePlate, int theLength, int theHeight) {
        super(theLicencePlate, theLength, theHeight);
        this.kind = "tall van";
    }
}
