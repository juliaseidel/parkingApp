package Parking;

import Vehicle.Vehicle;
import java.io.*;
import java.util.ArrayList;

/**
 * To model a parking - a collection of zones
 * @author Julia Seidel
 * @version 1.0 (09.05.19)
 */
public class Parking implements Serializable{
    private ZoneType zoneType;
    private Space space;
    public ArrayList <Zone> zones;

    public static final int MAX_SPACES = 26;

    Zone zone1 = new Zone(ZoneType.ZONE1);
    Zone zone2 = new Zone(ZoneType.ZONE2);
    Zone zone3 = new Zone(ZoneType.ZONE3);
    Zone zone4 = new Zone(ZoneType.ZONE4);
    Zone zone5 = new Zone(ZoneType.ZONE5);

    public Parking() {
        zones = new ArrayList<>();
        zones.clear();
        zones.add(zone1);
        zones.add(zone2);
        zones.add(zone3);
        zones.add(zone4);
        zones.add(zone5);
    }

    /**
     * adds a vehicle to a zone and gives it a random space
     * @param zone
     * @param vehicle
     * @throws IOException
     */
    public void add (Zone zone, Vehicle vehicle) throws IOException {
        zone.add(vehicle);
        space = space.getRandomSpace();
        System.out.println("Your spaceID is: " + zone.getName() + ", space " + space);
    }

    /**
     * displays the state of the parking
     * free and taken spaces
     */
    public void displayParking(){
        System.out.println("State of the parking: ");
        for (Zone zone : zones){
            System.out.println(zone.getName() + ": free spaces: " + zone.getNumOfFreeSpaces() + ", taken spaces: " + (26-(zone.getNumOfFreeSpaces())));
        }
    }

    /**
     * searches through zones to find a vehicle
     * @param receipt
     */
    public void collectVehicle(Receipt receipt) {
        for (Zone zone : zones){
            zone.collectVehicle(receipt);
        }
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }


    @Override
    public String toString() {
        return "Parking.Parking{" +
                "zoneType=" + zoneType +
                ", space=" + space +
                '}';
    }

    /**public StringBuilder toStringBuilder(){
        stringBuilder.append("Data in Parking: ");
        for (Zone zone: zones){
            stringBuilder.append(zone.toString());
        }
        return stringBuilder;
    }
    */

    /**
     * loads serializable data from a text file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fileIn;
        ObjectInputStream in;
        fileIn = new FileInputStream("M:\\version1\\vehicles.txt");
        try {

        in = new ObjectInputStream(fileIn);
        if (fileIn.available()>0) {
            Object object = in.readObject();
            zones = (ArrayList<Zone>) object;
            in.close();
            fileIn.close();
        }
        }catch (EOFException e){
            System.err.println("there is nothing to read");
        }
    }

    /**
     * saves serializable data to a txt file
     * @throws IOException
     */
    public void save() throws IOException {
        FileOutputStream fileOut = new FileOutputStream("M:\\version1\\vehicles.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(zones);
        out.close();
        fileOut.close();
    }
}
