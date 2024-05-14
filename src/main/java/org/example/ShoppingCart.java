package org.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Product> products;

    // Constructor
    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    // Add a product to the cart
    public void addProduct(Product product) {
        this.products.add(product);
    }

    // Remove a product from the cart
    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    // Calculate the total cost of the products in the cart
    public double getTotalCost() {
        double totalCost = 0;
        for (Product product : this.products) {
            totalCost += product.getPrice();
        }
        return totalCost;
    }

    // Get the list of products in the cart
    public List<Product> getProducts() {
        return new ArrayList<>(this.products); // Return a copy to prevent direct modification of the internal list
    }

    public double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : this.products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }


    // tostring method
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Product product : this.products) {
            result.append(product.toString()).append("\n");
        }
        return result.toString();
    }


}

