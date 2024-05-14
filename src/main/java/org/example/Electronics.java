package org.example;

public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String id, String name, int availableItems, double price, String brand, int warrantyPeriod) {
        super(id, name, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    // tostring method
    public String toString() {
        return super.toString() + "\nBrand: " + brand + "\nWarranty period: " + warrantyPeriod;
    }

}
