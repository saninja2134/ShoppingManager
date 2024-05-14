package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterShoppingManagerTest {

    @Test
    void testGetIntInput() {
        Scanner scanner = new Scanner("123");
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        int result = shoppingManager.getIntInput(scanner);
        assertEquals(123, result);
    }

    @Test
    void testGetDoubleInput() {
        Scanner scanner = new Scanner("123.45");
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        double result = shoppingManager.getDoubleInput(scanner);
        assertEquals(123.45, result, 0.01);
    }

    @Test
    void testAddProductWithinCapacity() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronics electronics = new Electronics("E001", "Laptop", 10, 1000.00, "Dell", 2);
        shoppingManager.addProduct(electronics);
        assertEquals(1, shoppingManager.getProducts().size());
    }

    @Test
    void testAddProductExceedCapacity() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        for (int i = 0; i < 60; i++) {
            Electronics electronics = new Electronics("E00" + i, "Product" + i, 10, 100.00 * i, "Brand" + i, 1);
            shoppingManager.addProduct(electronics);
        }
        assertEquals(50, shoppingManager.getProducts().size());
    }

    @Test
    void testWriteToFile() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronics electronics = new Electronics("E001", "Laptop", 10, 1000.00, "Dell", 2);
        shoppingManager.addProduct(electronics);
        shoppingManager.writeToFile();

        // Assuming file path is hardcoded in the original method
        File file = new File("products.txt");
        assertTrue(file.exists());
    }

    @Test
    void testReadFromFile() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.addProduct(new Electronics("E001", "Laptop", 10, 1000.00, "Dell", 2));
        // Assuming there is a file with product data at the specified path
        shoppingManager.readFromFile();
        assertFalse(shoppingManager.getProducts().isEmpty());
    }

    @Test
    void testAddNewClothingProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Clothing clothing = new Clothing("C001", "T-Shirt", 30, 25.00, "Medium", "Blue");
        shoppingManager.addProduct(clothing);

        assertEquals(1, shoppingManager.getProducts().size());
    }

    @Test
    void testAddNewElectronicsProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronics electronics = new Electronics("E001", "Laptop", 50, 1200.00, "Dell", 2);
        shoppingManager.addProduct(electronics);

        assertEquals(1, shoppingManager.getProducts().size());
    }

    @Test
    void testDeleteExistingProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        Electronics electronics = new Electronics("E001", "Laptop", 10, 1000.00, "Dell", 2);
        shoppingManager.addProduct(electronics);

        shoppingManager.deleteProduct("E001");

        assertEquals(0, shoppingManager.getProducts().size());
    }

    @Test
    void testDeleteNonExistentProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.deleteProduct("XYZ");

        assertEquals(0, shoppingManager.getProducts().size());
    }

    @Test
    void testPrintListOfProducts() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        // add products
        shoppingManager.addProduct(new Electronics("E001", "iPhone 12", 2, 1000, "Apple", 12));
        shoppingManager.addProduct(new Electronics("E002", "Samsung Galaxy S21", 1, 900, "Samsung", 12));
        shoppingManager.addProduct(new Electronics("E003", "Google Pixel 5", 30, 800, "Google", 12));
        shoppingManager.addProduct(new Clothing("C001", "T-Shirt", 40, 50, "M", "Black"));
        shoppingManager.addProduct(new Clothing("C002", "Jeans", 50, 100, "M", "Blue"));
        shoppingManager.addProduct(new Clothing("C003", "Jacket", 60, 150, "M", "Brown"));

        shoppingManager.printProducts();
        // No assertions as this is just to check if it prints without errors
    }

    @Test
    void testWriteAndReadFromFile() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.addProduct(new Electronics("E001", "Laptop", 10, 1000.00, "Dell", 2));
        // Assuming there are products in the system
        shoppingManager.writeToFile();
        shoppingManager.readFromFile();

        assertFalse(shoppingManager.getProducts().isEmpty());
    }
}
