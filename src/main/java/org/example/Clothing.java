package org.example;

public class Clothing extends Product {
    private String size;
    private String colour;

    // Constructor
    public Clothing(String id, String name, int availableItems, double price, String size, String colour) {
        super(id, name, availableItems, price);
        this.size = size;
        this.colour = colour;
    }

    // Getters and setters
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    // tostring method
    public String toString() {
        return super.toString() + "\nSize: " + size + "\nColour: " + colour;
    }
}

