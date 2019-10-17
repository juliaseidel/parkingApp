package Vehicle;

import Parking.Receipt;

import java.io.Serializable;

/**
 * Vehicle class, super class of Car, Coach, Motorbike,
 * LongVan and TallVan
 * @author Julia Seidel
 * @version 1.0 (09.05.2019)
 */
public class Vehicle implements Serializable {
    private String licencePlate;
    public int length;
    public int height;
    public Receipt receipt;
    public String kind;

    public Vehicle(String licencePlate, int length, int height) {
        this.licencePlate = licencePlate;
        this.length = length;
        this.height = height;
    }

    public Vehicle(String licencePlate, int length, int height, Receipt receipt) {
        this.licencePlate = licencePlate;
        this.length = length;
        this.height = height;
        this.receipt = receipt;
    }

    public Vehicle(Receipt receipt){
        this.receipt = receipt;
    }

    public Vehicle() {}

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receiptNumber) {
        this.receipt = receiptNumber;
    }

}
