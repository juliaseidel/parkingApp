package Parking;

import Vehicle.*;
import java.io.*;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 *  Receipt class
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class Receipt implements Serializable {
    private int receiptNumber;
    private long inTime = Calendar.getInstance().getTimeInMillis();
    private long outTime;
    private double rateZone1and4;
    private double rateZone2;
    private double rateZone3;
    private double rateZone5;

    public Receipt() {
        Scanner scan = new Scanner(System.in);
    }

    /**
     * gets a random number for the receipt
     * @return
     */
    public int getRandomNumber() {
        Random random = new Random();
        int anInt = random.nextInt(9999) +1;
        return anInt;
    }

    /**
     * calculates the time spent on the parking in hours
     * @return
     */
    public long calculateTheTime(){
        long theTimeInSeconds = (outTime - inTime)/1000;
        long theTimeInHours = (outTime - inTime)/1000/60/60;
        if (theTimeInSeconds%60 > 0){
            return theTimeInHours +1;
        }
        else {
            return theTimeInHours;
        }
    }

    /**
     * loads hourly rates from a text file
     * @param filename
     * @throws IOException
     */
    public void loadHourlyRates(String filename) throws IOException {
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            rateZone1and4 = infile.nextDouble();
            rateZone2 = infile.nextDouble();
            rateZone3 = infile.nextDouble();
            rateZone5 = infile.nextDouble();
        }
    }

    /**
     * calculates the amount of money to be paid
     * @param vehicle
     * @throws InputMismatchException
     */
    public void calculatePayment(Vehicle vehicle) throws InputMismatchException {
        try {
            loadHourlyRates("prices.txt");
        } catch (FileNotFoundException e ){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        double toPay;
        if (vehicle instanceof Coach){
            toPay = calculateTheTime()*rateZone3;
            System.out.println("You need to pay " + toPay + ". How much would you like to pay?");
            Scanner scan = new Scanner(System.in);
            double givenMoney = scan.nextDouble();
            change(givenMoney, toPay);
        }
        else if (vehicle instanceof Car || vehicle instanceof TallVan) {
            toPay = calculateTheTime()*rateZone1and4;
            System.out.println("You need to pay " + toPay + ". How much would you like to pay?");
            Scanner scan = new Scanner(System.in);
            double givenMoney = scan.nextDouble();
            change(givenMoney, toPay);
        }
        else if (vehicle instanceof LongVan) {
            toPay = calculateTheTime()*rateZone2;
            System.out.println("You need to pay " + toPay + ". How much would you like to pay?");
            Scanner scan = new Scanner(System.in);
            double givenMoney = scan.nextDouble();
            change(givenMoney, toPay);
        }
        else {
            toPay = calculateTheTime()*rateZone5;
            System.out.println("You need to pay " + toPay + ". How much would you like to pay?");
            Scanner scan = new Scanner(System.in);
            double givenMoney = scan.nextDouble();
            change(givenMoney, toPay);
        }
    }

    /**
     * calculates the change
     * @param givenMoney
     * @param moneyToPay
     */
    public void change(Double givenMoney, Double moneyToPay){
        double moneyLeft = moneyToPay - givenMoney;
        if (moneyLeft>0){
            System.out.println("You need to pay " + moneyLeft + " more");
            Scanner scan = new Scanner(System.in);
            double newMoney = scan.nextDouble();
            change(newMoney, moneyLeft);
        }
        else if (moneyLeft == 0){
            System.out.println("You don't need to pay more");
        }
        else {
            System.out.println("Here is your change " + moneyLeft*-1);
        }
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public void setInTime(long inTime) {
        this.inTime = inTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }
}
