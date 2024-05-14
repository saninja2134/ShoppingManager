package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInterface {
    private final HashMap<String, Product> dataMap;
    private final ShoppingCart shoppingCart = new ShoppingCart();
    private JTable table;
    private JComboBox<String> productTypeComboBox;
    private JPanel detailsPanel;

    public UserInterface(HashMap<String, Product> dataMap) {
        this.dataMap = dataMap;
    }

    public void mainUI() {
        JFrame frame = new JFrame("Westminster Shopping Center");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productTypeComboBox.addActionListener(e -> filterProducts((String) productTypeComboBox.getSelectedItem()));

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Product ID", "Product Name", "Category", "Price", "Info"});

        for (Product product : this.dataMap.values()) {
            String id;
            String name;
            String category;
            String price;
            String info;
            if (product.getAvailableItems() < 3) {
                id = product.getId();
                name = "<html><font color='red'>" + product.getName() + "</font></html>";
                category = "<html><font color='red'>" + getCategory(product) + "</font></html>";
                price = "<html><font color='red'>" + product.getPrice() + "<html><font color='red'>";
                info = "<html><font color='red'>" + getInfo(product) + "</font></html>";
            } else {
                id = product.getId();
                name = product.getName();
                category = getCategory(product);
                price = String.valueOf(product.getPrice());
                info = getInfo(product);
            }

            // Assuming you have an HTML table with a model named 'tableModel'
            tableModel.addRow(new Object[]{id, name, category, price, info});
        }

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setPreferredSize(new Dimension(300, 150));

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getSelectionModel().addListSelectionListener(e -> displayProductDetails());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        JPanel comboBoxPanel = new JPanel(new FlowLayout());
        comboBoxPanel.add(new JLabel("Select Product Category: "));
        comboBoxPanel.add(productTypeComboBox);

        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> shoppingCartUI());
        comboBoxPanel.add(shoppingCartButton);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> {
            try {
                addToShoppingCart();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding product to shopping cart!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(addToCartButton);
        southPanel.add(detailsPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(comboBoxPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(southPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void filterProducts(String selectedType) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        for (Product product : this.dataMap.values()) {
            boolean isAllType = "All".equals(selectedType);
            boolean isElectronics = product instanceof Electronics && "Electronics".equals(selectedType);
            boolean isClothing = product instanceof Clothing && "Clothing".equals(selectedType);

            if (isAllType || isElectronics || isClothing) {
                String id;
                String name;
                String category;
                String price;
                String info;
                if (product.getAvailableItems() < 3) {
                    id = product.getId();
                    name = "<html><font color='red'>" + product.getName() + "</font></html>";
                    category = "<html><font color='red'>" + getCategory(product) + "</font></html>";
                    price = "<html><font color='red'>" + product.getPrice() + "<html><font color='red'>";
                    info = "<html><font color='red'>" + getInfo(product) + "</font></html>";
                } else {
                    id = product.getId();
                    name = product.getName();
                    category = getCategory(product);
                    price = String.valueOf(product.getPrice());
                    info = getInfo(product);
                }

                // Assuming you have an HTML table with a model named 'tableModel'
                tableModel.addRow(new Object[]{id, name, category, price, info});
            }
        }
    }

    private void displayProductDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String productId = table.getValueAt(selectedRow, 0).toString();
            Product selectedProduct = this.dataMap.get(productId);

            detailsPanel.removeAll();
            JLabel selectedProductLabel = new JLabel("Selected Product - Details");
            selectedProductLabel.setFont(new Font("Arial", Font.BOLD, 14));
            detailsPanel.add(selectedProductLabel);
            detailsPanel.add(Box.createVerticalStrut(10));

            if (selectedProduct instanceof Electronics electronics) {
                addSpacedLabel("Category: Electronics");
                addSpacedLabel("Product Name: " + electronics.getName());
                addSpacedLabel("Brand: " + electronics.getBrand());
                addSpacedLabel("Warranty: " + electronics.getWarrantyPeriod());
                addSpacedLabel("Items Available: " + electronics.getAvailableItems());
            } else if (selectedProduct instanceof Clothing clothing) {
                addSpacedLabel("Category: Clothing");
                addSpacedLabel("Product Name: " + clothing.getName());
                addSpacedLabel("Size: " + clothing.getSize());
                addSpacedLabel("Colour: " + clothing.getColour());
                addSpacedLabel("Items Available: " + clothing.getAvailableItems());
            }

            detailsPanel.revalidate();
            detailsPanel.repaint();
        }
    }

    private void addSpacedLabel(String text) {
        detailsPanel.add(new JLabel(text));
        detailsPanel.add(Box.createVerticalStrut(5));
    }

    private void addToShoppingCart() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String productId = getSelectedProductId();
            Product selectedProduct = this.dataMap.get(productId);

            if (selectedProduct != null) {
                shoppingCart.addProduct(selectedProduct);
                JOptionPane.showMessageDialog(null, "Product added to shopping cart!");
            } else {
                JOptionPane.showMessageDialog(null, "Selected product not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String getCategory(Product product) {
        if (product instanceof Electronics) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        } else {
            return "Unknown";
        }
    }

    private String getInfo(Product product) {
        if (product instanceof Electronics electronics) {
            return electronics.getBrand() + ", " + electronics.getWarrantyPeriod();
        } else if (product instanceof Clothing clothing) {
            return clothing.getSize() + ", " + clothing.getColour();
        } else {
            return "Unknown";
        }
    }

    private String getSelectedProductId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Object selectedValue = table.getValueAt(selectedRow, 0);
            if (selectedValue instanceof String) {
                return (String) selectedValue;
            }
        }
        throw new RuntimeException("Invalid selection or product ID not found.");
    }

    private void shoppingCartUI() {
        JFrame cartFrame = new JFrame("Shopping Cart");
        cartFrame.setSize(400, 300);

        DefaultTableModel cartTableModel = new DefaultTableModel();
        cartTableModel.setColumnIdentifiers(new Object[]{"Product", "Quantity", "Price"});

        JTable cartTable = new JTable(cartTableModel);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setPreferredSize(new Dimension(400, 150));

        JLabel originalTotalLabel = new JLabel();
        JLabel discountLabel = new JLabel();
        JLabel totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        originalTotalLabel.setHorizontalAlignment(JLabel.RIGHT);
        discountLabel.setHorizontalAlignment(JLabel.RIGHT);
        totalLabel.setHorizontalAlignment(JLabel.RIGHT);

        JPanel cartPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());

        cartPanel.add(cartScrollPane, BorderLayout.CENTER);
        centerPanel.add(originalTotalLabel, BorderLayout.NORTH);
        centerPanel.add(discountLabel, BorderLayout.CENTER);
        centerPanel.add(totalLabel, BorderLayout.SOUTH);

        cartFrame.add(cartPanel, BorderLayout.NORTH);
        cartFrame.add(centerPanel, BorderLayout.CENTER);

        updateShoppingCart(cartTableModel, totalLabel, discountLabel, originalTotalLabel);

        cartFrame.setLocationRelativeTo(null);
        cartFrame.setVisible(true);
    }

    private void updateShoppingCart(DefaultTableModel cartTableModel, JLabel totalLabel, JLabel discountLabel, JLabel originalTotalLabel) {
        int electronicDupe = 0;
        int clothingDupe = 0;
        boolean discount = false;
        cartTableModel.setRowCount(0);

        List<Product> cartProducts = shoppingCart.getProducts();
        HashMap<String, Integer> productQuantityMap = new HashMap<>();

        for (Product product : cartProducts) {
            productQuantityMap.put(product.getId(), productQuantityMap.getOrDefault(product.getId(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : productQuantityMap.entrySet()) {
            String productID = entry.getKey();
            int quantity = entry.getValue();

            Product product = this.dataMap.get(productID);

            double price = product.getPrice() * quantity;

            Object[] rowData;
            if (product instanceof Electronics electronics) {
                rowData = new Object[]{productID + " " + electronics.getName() + ", " + electronics.getBrand() + ", " + electronics.getWarrantyPeriod(), quantity, price};
                electronicDupe += quantity;
            } else if (product instanceof Clothing clothing) {
                rowData = new Object[]{productID + " " + clothing.getName() + ", " + clothing.getSize() + ", " + clothing.getColour(), quantity, price};
                clothingDupe += quantity;
            } else {
                rowData = new Object[]{};
            }

            cartTableModel.addRow(rowData);
        }

        if (electronicDupe >= 3 || clothingDupe >= 3) {
            discount = true;
        }

        double totalPrice = shoppingCart.calculateTotalPrice();

        if (discount) {
            originalTotalLabel.setText("Total " + totalPrice);
            discountLabel.setText("Three items in same Category Discount(20%): -" + totalPrice * 0.2);
            totalLabel.setText("Final Price: " + totalPrice * 0.8);
        } else {
            totalLabel.setText("Final Price: " + totalPrice);
        }
    }
}

