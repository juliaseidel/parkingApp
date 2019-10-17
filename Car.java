package Vehicle;

import Parking.Receipt;
import java.io.Serializable;

/**
 * Car class, subclass of Vehicle
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class Car extends Vehicle implements Serializable {
    private String kind;

    public Car(String theLicencePlate, int theLength, int theHeight, Receipt receipt) {
        super(theLicencePlate, theLength, theHeight, receipt);
        this.kind = "car";
    }

    public Car(String theLicencePlate, int theLength, int theHeight) {
        super(theLicencePlate, theLength, theHeight);
        this.kind = "car";
    }

}
