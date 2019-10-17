package Vehicle;

import java.io.Serializable;

/**
 * Coach class, subclass of Vehicle
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class Coach extends Vehicle implements Serializable {

    public Coach(String theLicencePlate, int theLength, int theHeight) {
        super(theLicencePlate, theLength, theHeight);
        this.kind = "coach";
    }
}
