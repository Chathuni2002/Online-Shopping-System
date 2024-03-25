import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; 

public class ShoppingManagerGUI extends JFrame {
    private WestminsterShoppingManager shoppingManager;
    private JComboBox<String> productTypeComboBox;
    private JTable productTable;
    private JTextArea productDetailsArea;
    private ShoppingCart shoppingCart;

    public ShoppingManagerGUI(WestminsterShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
        shoppingCart = new ShoppingCart();

        // Set up the main frame
        setTitle("Westminster Shopping Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create components
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        productTable = new JTable();
        productDetailsArea = new JTextArea(10, 30);
        JButton addToCartButton = new JButton("Add to Cart");
        JButton viewCartButton = new JButton("Shopping Cart");

        // Add action listeners to the buttons
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewShoppingCart();
            }
        });

        // Update the productTable to respond to selection
        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRowIndex = productTable.getSelectedRow();
            if (selectedRowIndex != -1) {
                Product selectedProduct = getProductFromSelectedRow(selectedRowIndex);
                updateProductDetailsArea(selectedProduct);
            }
        });

        // Update the addToCartButton to add selected product to the shopping cart
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();
            }
        });

        // Update the viewCartButton to show the shopping cart
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewShoppingCart();
            }
        });

        // Create a panel to hold the components
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        JScrollPane detailsScrollPane = new JScrollPane(productDetailsArea);

        // Add components to the panels
        controlPanel.add(new JLabel("Select Product Type: "));
        controlPanel.add(productTypeComboBox);
        controlPanel.add(addToCartButton);
        controlPanel.add(viewCartButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(detailsScrollPane, BorderLayout.SOUTH);

        // Add the main panel to the main frame
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        updateProductTable("All");
    }

    // Method to add selected product to the shopping cart
    private void addToShoppingCart() {
        int selectedRowIndex = productTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            Product selectedProduct = getProductFromSelectedRow(selectedRowIndex);

            // Ask user for the quantity
            String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity:", "1");
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    shoppingCart.addProductsToCart(selectedProduct, quantity);
                    updateProductDetailsArea(selectedProduct);
                    JOptionPane.showMessageDialog(this, "Product added to the shopping cart.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid number.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to add to the shopping cart.");
        }
    }

    // Method to show the shopping cart
    public void viewShoppingCart() {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Info", "Quantity", "Total Price"}, 0);

        for (ShoppingCart.CartItem cartItem : shoppingCart.cartItems) {
            Product product = cartItem.getProduct();
            Object[] row = {product.getProductId(), product.getProductName(), product.getProductInfo(),
                    cartItem.getQuantity(), cartItem.getTotalPrice()};
            model.addRow(row);
        }

        double totalPriceWithDiscounts = shoppingCart.calculateTotalPriceWithDiscounts();

        // Display total price with discounts
        String totalPriceMessage = "Total Price (with Discounts): $" + totalPriceWithDiscounts;

        JTable cartTable = new JTable(model);
        cartTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JTextArea totalPriceArea = new JTextArea(totalPriceMessage);


        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(totalPriceArea, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, "Shopping Cart", JOptionPane.PLAIN_MESSAGE);
    }


    private Product getProductFromSelectedRow(int rowIndex) {
        String productId = (String) productTable.getValueAt(rowIndex, 0);
        for (Product product : shoppingManager.productList) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    //Method to update the product table.
    private void updateProductTable(String productType) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Product ID", "Name", "Category", "Info"}, 0);

        for (Product product : shoppingManager.productList) {
            if (productType.equals("All") || productType.equals(product.getProductType())) {
                Object[] row = {product.getProductId(), product.getProductName(), product.getProductType(), product.getProductInfo()};
                model.addRow(row);
            }
        }

        productTable.setModel(model);
        productTable.setAutoCreateRowSorter(true);
        updateProductDetailsArea(null);
    }

    //Method to show product detail area.
    private void updateProductDetailsArea(Product selectedProduct) {
        if (selectedProduct == null) {
            productDetailsArea.setText("");
        } else {
            productDetailsArea.setText(selectedProduct.toString());
        }
    }

}