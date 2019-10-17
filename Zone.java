package Parking;

import Vehicle.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Zone class, a collection of vehicles
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class Zone implements Serializable{
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ZoneType name;
    private int numOfFreeSpaces = 26;
    private static final int MAX_SPACES = 26;

    public Zone(ZoneType name) {
        this.name = name;
    }

    /**
     * adds a vehicle to the zone
     * @param vehicle
     * @throws IOException
     */
    public void add(Vehicle vehicle) throws IOException {
        if (numOfFreeSpaces <= MAX_SPACES && numOfFreeSpaces > 0) {
            vehicles.add(vehicle);
            numOfFreeSpaces--;
        }
        else {
            System.out.println("parking is full");
        }
    }

    /**
     * searches for a matching recepit number
     * and returns a car to the customer
     * @param receipt
     */
    public void collectVehicle(Receipt receipt){
        for (Vehicle vehicle : vehicles){
            if (vehicle.getReceipt().getReceiptNumber() == (receipt.getReceiptNumber())){
                receipt.setOutTime(System.currentTimeMillis());
                double time = TimeUnit.MILLISECONDS.toHours(receipt.calculateTheTime());
                if (time == 0){
                    time = 1;
                    System.out.println("You are " + time + " hour on the parking");
                }
                else {
                    System.out.println("You are " + time + " hours on the parking");
                }
                vehicles.remove(vehicle);
                System.out.println("Please pay for the parking");
                receipt.calculatePayment(vehicle);
                System.out.println("You can collect your car, please take the token. You have 15 minutes to leave the parking");
                numOfFreeSpaces++;
                return;
            }
            else {
                System.out.println("vehicle not found");
            }
        }
    }


    public int getNumOfFreeSpaces() {
        return numOfFreeSpaces;
    }

    public ZoneType getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "vehicles=" + vehicles +
                '}';
    }
}
