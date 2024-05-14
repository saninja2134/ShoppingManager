package org.example;

public abstract class Product {
    private String id;
    private String name;
    private int availableItems;
    private double price;

    // Constructor
    public Product(String id, String name, int availableItems, double price) {
        this.id = id;
        this.name = name;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // tostring method
    public String toString() {
        return "Product ID: " + id + "\nProduct name: " + name + "\nAvailable items: " + availableItems + "\nPrice: " + price;
    }

}

