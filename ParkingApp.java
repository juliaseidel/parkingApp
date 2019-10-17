import Parking.*;
import Vehicle.*;
//import javafx.scene.control.Button;
import java.io.IOException;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;

/**
 * The main application class for Parking. Runs a command line menu.
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class ParkingApp implements Serializable {
    private Parking parking;
    private Scanner scan;
    private boolean isDisabled;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private LocalDateTime time = LocalDateTime.now();
    //private Button button;

    private ParkingApp() {
        scan = new Scanner(System.in);
        isDisabled = false;
        parking = new Parking();
    }

    /**
     * main menu for both customer and attendant
     * loads data from file
     * @throws IOException
     */
    private void runMenu() throws IOException{
        System.out.println("Current time: " + dtf.format(time));
        System.out.println("Loading from file");
        try {
            parking.load();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("What would you like to do:");
        System.out.println("1 - display customer menu");
        System.out.println("2 - display assistant menu");
        String answer = scan.nextLine().toUpperCase();
        switch (answer){
            case "1":
                menuCustomer();
                break;
            case "2":
                System.out.println("What would you like to do:");
                printMenuForAttendant();
                menuAttendant();
                break;
            default:
                runMenu();
        }
    }

    /**
     * the customer menu
     * @throws IOException
     */
    private void menuCustomer() throws IOException {
        String response;
        do {
            System.out.println("What would you like to do:");
            printMenuForCustomer();
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    try {
                        registerVehicle();
                    }catch(InputMismatchException e){
                        System.err.println("Wrong input format");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    collectVehicle();
                    break;
                case "3":
                    System.out.println("Are you driving a motorbike or a coach? (Y/N)");
                    String ans = scan.nextLine();
                    if (ans.equalsIgnoreCase("Y")){
                        System.out.println("You can't drop off your vehicle");
                    }
                    else {
                        dropOffVehicle();
                    }
                    break;
                case "4":
                    parking.displayParking();
                    break;
                case "Q":
                    try {
                        parking.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Try again");
            }

        } while (!(response.equals("Q")));
    }

    /**
     * the attendant menu
     * @throws IOException
     */
    private void menuAttendant() throws IOException {
        String answer;
        do {
            scan = new Scanner(System.in);
            answer = scan.nextLine().toUpperCase();
            switch (answer) {
                case "1":
                    try {
                        registerVehicle();
                    }catch(InputMismatchException e){
                        System.err.println("Wrong input format");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    collectVehicle();
                    break;
                case "3":
                    parking.displayParking();
                    break;
                case "Q":
                    try {
                        parking.save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Try again");
            }
            runMenu();
        } while (!(answer.equals("Q")));
    }

    /**
     * this function just displays the attendant menu
     * @throws IOException
     */
    private void dropOffVehicle() throws IOException {
        System.out.println("Your attendant is coming");
        System.out.println("ATTENDANT MENU");
        printMenuForAttendant();
        menuAttendant();
    }

    /**
     * prints a menu for customer
     */
    private void printMenuForCustomer() {
        System.out.println("1 -  register a vehicle");
        System.out.println("2 -  collect the vehicle");
        System.out.println("3 -  drop off the vehicle ");
        System.out.println("4 -  display the state of the parking");
        System.out.println("q -  Quit and save");
    }

    /**
     * prints a menu fro attendant
     */
    private void printMenuForAttendant() {
        System.out.println("1 - register and park a vehicle");
        System.out.println("2 - give back the vehicle to the owner");
        System.out.println("3 - display the state of the parking");
        System.out.println("q -  Quit and save");
    }

    /**
     * this method asks the user for info about the vehicle
     * and calls a method to define which vehicle is it
     * @throws IOException
     */
    private void registerVehicle() throws IOException {
        int length =0;
        int height =0;
        int swap1=0;
        int swap2=0;
        System.out.println("What is the licence plate number?");
        String licence = scan.next();
        System.out.println("What is the height of the vehicle? (in centimeters)");
        swap1 = scan.nextInt();
        if (swap1<0){
            System.err.println("Height must be a positive number");
            registerVehicle();
        }
        else {
            height =swap1;
        }
        System.out.println("What is the length of the vehicle? (in centimeters)");
        swap2 = scan.nextInt();
        if (swap2<0){
            System.err.println("Length must be a positive number");
            registerVehicle();
        }
        else {
            length = swap2;
        }
        disabled();

        Vehicle vehicle = defineVehicle(licence,length,height);
        register(vehicle);
    }

    /**
     * this method asks a user if he has a disability
     */
    private void disabled() {
        System.out.println("Is the car owned by disabled person? (Y/N)");
        String answer = scan.next();
        if (answer.equalsIgnoreCase("y")) {
            isDisabled = true;
        } else if (answer.equalsIgnoreCase("n")) {
            isDisabled = false;
        } else {
            disabled();
        }
    }

    /**
     * this method gets a defined vehicle and register
     * it in a right zone
     * @param vehicle
     * @throws IOException
     */
    private void register(Vehicle vehicle) throws IOException {
        if (isDisabled && !(vehicle instanceof Coach)) {
            System.out.println("Park in zone: Zone5, checking availability");
            parking.add(parking.getZones().get(4), vehicle);
            receiptForVehicle(vehicle);
        }
        else {
            if (vehicle instanceof Coach){
                System.out.println("Park in zone: Zone3, checking availability");
                parking.add(parking.getZones().get(2), vehicle);
                receiptForVehicle(vehicle);
            }
            else if (vehicle instanceof Car) {
                insideOutside(vehicle);
                receiptForVehicle(vehicle);
            }
            else if (vehicle instanceof TallVan) {
                System.out.println("Park in zone: Zone1, checking availability");
                parking.add(parking.getZones().get(0), vehicle);
                receiptForVehicle(vehicle);
            }
            else if (vehicle instanceof LongVan) {
                System.out.println("Park in zone: Zone2, checking availability");
                parking.add(parking.getZones().get(1), vehicle);
                receiptForVehicle(vehicle);
            }
            else if (vehicle instanceof Motorbike) {
                System.out.println("Park in zone: Zone5, checking availability");
                parking.add(parking.getZones().get(4), vehicle);
                receiptForVehicle(vehicle);
            }
            else {
                System.out.println("something's wrong");
            }
        }
    }

    /**
     * this method defines what vehicle is it
     * based on its length and height
     * @param licence
     * @param length
     * @param height
     * @return
     */
    private Vehicle defineVehicle(String licence, int length, int height) {
        if (length < 500 && height <= 200){
            Vehicle vehicle = new Car(licence, length, height);
            return vehicle;
        }
        else if (length < 500 && height <= 200){
            Vehicle vehicle = new TallVan(licence, length, height);
            return vehicle;
        }
        else if (height < 300 && length >= 510 && length <= 600){
            Vehicle vehicle = new LongVan(licence, length, height);
            return vehicle;
        }
        else if (length <= 1500 && length>600){
            Vehicle vehicle = new Coach(licence, length, height);
            return vehicle;
        }
        else {
            Vehicle vehicle = new Motorbike(licence, length, height);
            return vehicle;
        }
    }

    /**
     * this method gives a receipt to the vehicle
     * and starts counting its time on the parking
     * @param vehicle
     */
    private void receiptForVehicle(Vehicle vehicle) {
        Receipt receipt = new Receipt();
        receipt.setReceiptNumber(receipt.getRandomNumber());
        receipt.setInTime(System.currentTimeMillis());
        System.out.println("Here is your recipe number: " + receipt.getReceiptNumber());
        vehicle.setReceipt(receipt);
    }

    /**
     * this method just asks the user if
     * he wants to park outside or inside
     * @param vehicle
     * @throws IOException
     */
    private void insideOutside(Vehicle vehicle) throws IOException {
        System.out.println("Do you want to park outside or inside? (O/I)?");
        String a = scan.next();
        if (a.equalsIgnoreCase("i")) {
            System.out.println("Park in zone: Zone4, checking availability");
            parking.add(parking.getZones().get(3), vehicle);
        } else if (a.equalsIgnoreCase("o")) {
            System.out.println("Park in zone: Zone1, checking availability");
            parking.add(parking.getZones().get(0), vehicle);
        } else {
            insideOutside(vehicle);
        }
    }

    /**
     * this method asks for the receipt number
     * and then calls a method to find a vehicle
     */
    public void collectVehicle() {
        Receipt receipt = new Receipt();
        System.out.println("What is your receipt number? ");
        try {
            receipt.setReceiptNumber(Integer.parseInt(scan.next()));
        } catch (NumberFormatException e){
            collectVehicle();
        }
        parking.collectVehicle(receipt);
    }


    public static void main (String[] args) throws IOException {
        //args.launch;
        ParkingApp app = new ParkingApp();
        app.runMenu();
    }


}
