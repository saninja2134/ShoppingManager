package org.example;

import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {

    private final HashMap<String, Product> products;

    // Constructor initializes the products HashMap
    public WestminsterShoppingManager() {
        this.products = new HashMap<>();
    }

    // Main update method for the shopping manager
    public void update() {
        try {
            System.out.println("Welcome to Westminster Shopping Manager");
            options();
            Scanner scanner = new Scanner(System.in);
            int option = getIntInput(scanner);

            // Loop for menu options until user chooses to exit (option 6)
            while (option != 6) {
                switch (option) {
                    case 1 -> addNewProduct(scanner);
                    case 2 -> {
                        System.out.println("Please enter the product ID:");
                        String id = scanner.next();
                        deleteProduct(id);
                    }
                    case 3 -> printProducts();
                    case 4 -> writeToFile();
                    case 5 -> readFromFile();
                }
                options();
                option = getIntInput(scanner);
            }
        } catch (Exception e) {
            System.err.println("An error occurred during the update: " + e.getMessage());
        }
    }

    // Helper method to get integer input from the user
    public int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a whole number.");
            scanner.next(); // consume the invalid input
        }
        return scanner.nextInt();
    }

    // Helper method to get double input from the user
    public double getDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
        }
        return scanner.nextDouble();
    }

    // Method to add a new product, prompting user for product type first
    public void addNewProduct(Scanner scanner) {
        System.out.println("Please select the type of product you want to add:\n1. Electronics\n2. Clothing");
        int productType = getIntInput(scanner);
        if (productType == 1) {
            addElectronicsProduct(scanner);
        } else if (productType == 2) {
            addClothingProduct(scanner);
        } else {
            System.out.println("Invalid product type selected");
        }
    }

    private void addElectronicsProduct(Scanner scanner) {
        System.out.println("Please enter the product ID:");
        String id = scanner.next();
        System.out.println("Please enter the product name:");
        String name = scanner.next();
        scanner.nextLine();
        System.out.println("Please enter the number of available items:");
        int availableItems = getIntInput(scanner);
        System.out.println("Please enter the price:");
        double price = getDoubleInput(scanner);
        System.out.println("Please enter the brand:");
        String brand = scanner.next();
        System.out.println("Please enter the warranty period:");
        int warrantyPeriod = getIntInput(scanner);
        this.addProduct(new Electronics(id, name, availableItems, price, brand, warrantyPeriod));
    }

    private void addClothingProduct(Scanner scanner) {
        System.out.println("Please enter the product ID:");
        String id = scanner.next();
        System.out.println("Please enter the product name:");
        String name = scanner.next();
        scanner.nextLine();
        System.out.println("Please enter the number of available items:");
        int availableItems = getIntInput(scanner);
        System.out.println("Please enter the price:");
        double price = getDoubleInput(scanner);
        System.out.println("Please enter the size:");
        String size = scanner.next();
        System.out.println("Please enter the colour:");
        String colour = scanner.next();
        this.addProduct(new Clothing(id, name, availableItems, price, size, colour));
    }

    public void deleteProduct(String id) {
        Product productToDelete = this.products.get(id);
        if (productToDelete != null) {
            this.products.remove(productToDelete.getId());
            System.out.println("Product deleted: " + productToDelete);
            System.out.println("Total number of products left in the system: " + this.products.size());
        } else {
            System.out.println("Product not found with ID: " + id);
        }
    }

    private void options() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.err.println("Error pausing: " + e.getMessage());
        }
        System.out.println("""
                Please select an option:
                1. Add a new product
                2. Delete a product
                3. Print the list of products
                4. Save the list of products to a file
                5. Read the list of products from a file
                6. Exit the application
                """);
    }

    public void addProduct(Product product) {
        if (this.products.size() < 50) {
            this.products.put(product.getId(), product);
        } else {
            System.out.println("The system can only have a maximum of 50 products");
        }
    }


    public void printProducts() {
        List<Product> productList = new ArrayList<>(this.products.values());
        productList.sort(Comparator.comparing(Product::getId));

        for (Product product : productList) {
            System.out.println(product.toString());
            if (product instanceof Electronics) {
                System.out.println("Product type: Electronics");
            } else if (product instanceof Clothing) {
                System.out.println("Product type: Clothing");
            }
        }
    }

    public void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("products.txt"))) {
            for (Product product : this.products.values()) {
                if (product instanceof Electronics electronics) {
                    bufferedWriter.write(electronics.getId() + ", " + electronics.getName() + ", " + electronics.getAvailableItems() + ", " + electronics.getPrice() + ", " + electronics.getBrand() + ", " + electronics.getWarrantyPeriod());
                    bufferedWriter.newLine();
                } else if (product instanceof Clothing clothing) {
                    bufferedWriter.write(clothing.getId() + ", " + clothing.getName() + ", " + clothing.getAvailableItems() + ", " + clothing.getPrice() + ", " + clothing.getSize() + ", " + clothing.getColour());
                    bufferedWriter.newLine();
                }
            }
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productAttributes = line.split(", ");
                try {
                    this.addProduct(new Electronics(productAttributes[0], productAttributes[1], Integer.parseInt(productAttributes[2]), Double.parseDouble(productAttributes[3]), productAttributes[4], Integer.parseInt(productAttributes[5])));
                } catch (NumberFormatException e) {
                    this.addProduct(new Clothing(productAttributes[0], productAttributes[1], Integer.parseInt(productAttributes[2]), Double.parseDouble(productAttributes[3]), productAttributes[4], productAttributes[5]));
                }
            }
            System.out.println("Data read from file successfully.");
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }
}
