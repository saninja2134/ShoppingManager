package org.example;

public interface ShoppingManager {

    // Add a new product to the system
    void addProduct(Product product);

    // Delete a product from the system
    void deleteProduct(String id);

    // Write the list of products to a file
    void writeToFile();

    // Read the list of products from a file
    void readFromFile();

    // Print the list of products
    void printProducts();

}

