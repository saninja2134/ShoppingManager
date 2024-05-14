package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Create a new WestminsterShoppingManager object
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        System.out.println("""
                Welcome to Westminster Shopping Manager
                Please select the type of interface you want to use:
                1. Westminster Shopping Manager
                2. Shopping Catalogue - User Interface""");
        Scanner scanner = new Scanner(System.in);
        // Used the getIntInput method from WestminsterShoppingManager for more robust input handling
        int option = westminsterShoppingManager.getIntInput(scanner);
        if (option == 1) {
            westminsterShoppingManager.update();
        } else if (option == 2) {
            westminsterShoppingManager.readFromFile(); // need for products in the user interface
            if (westminsterShoppingManager.getProducts().isEmpty()) {
                System.out.println("No products found. Please add products to the system.");
                return;
            }
            UserInterface userInterface = new UserInterface(westminsterShoppingManager.getProducts());
            userInterface.mainUI();
        } else {
            System.out.println("Invalid option selected");
        }

    }
}