package Vehicle;

import java.io.Serializable;

/**
 * Motorbike class, subclass of Vehicle
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class Motorbike extends Vehicle implements Serializable {
    private String kind;

    public Motorbike(String theLicencePlate, int theLength, int theHeight) {
        super(theLicencePlate, theLength, theHeight);
        this.kind = "motorbike";
    }
}
