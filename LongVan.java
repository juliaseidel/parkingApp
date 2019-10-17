package Vehicle;

import java.io.Serializable;

/**
 * LongVan class, subclass of Vehicle
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class LongVan extends Vehicle implements Serializable {
    private String kind;

    public LongVan(String theLicencePlate, int theLength, int theHeight) {
        super(theLicencePlate, theLength, theHeight);
        this.kind = "long van";
    }

}
