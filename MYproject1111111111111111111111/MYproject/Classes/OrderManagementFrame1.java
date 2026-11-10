package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter; 

class MenuItem11 {
    private int id;
    private String name;
    private double price;
    private String type;
    private int quantity; // Add this line to include quantity

    public MenuItem11(int id, String name, double price, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = 0; // Initialize quantity to 0
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
public String toString() {
    return String.format("[%d]%s-%.2f-%s X %d", id, name.charAt(0), price, type.charAt(0), quantity);
}

}

public class OrderManagementFrame1 extends JFrame {

    private DefaultListModel<MenuItem> addedItemsModel;
    private DefaultListModel<MenuItem> menuItemsModel;
    private JList<MenuItem> addedItemsList;
    private JList<MenuItem> menuItemsList;
    private JTextField quantityTextField;

    public OrderManagementFrame1() {
        setTitle("Order Management");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(102, 255, 255));

        setLayout(null);

        addedItemsModel = new DefaultListModel<>();
        menuItemsModel = new DefaultListModel<>();
        addedItemsList = new JList<>(addedItemsModel);
        menuItemsList = new JList<>(menuItemsModel);

        JScrollPane addedItemsScrollPane = new JScrollPane(addedItemsList);
        addedItemsScrollPane.setBounds(20, 20, 400, 392);
        addedItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //addedItemsList.setEnabled(false);
        add(addedItemsScrollPane);

        JScrollPane menuItemsScrollPane = new JScrollPane(menuItemsList);
        menuItemsScrollPane.setBounds(450, 20, 591, 392);
        menuItemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(menuItemsScrollPane);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(50, 430, 70, 25);
        add(quantityLabel);

        quantityTextField = new JTextField();
        quantityTextField.setBounds(120, 430, 70, 25);
        add(quantityTextField);

        JButton addButton = new JButton("Add");
        addButton.setBounds(200, 430, 80, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(300, 430, 80, 25);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
        add(deleteButton);

        JButton allButton = new JButton("All");
        allButton.setBounds(520, 430, 80, 25);
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllItems();
            }
        });
        add(allButton);

        JButton dessertButton = new JButton("Dessert");
        dessertButton.setBounds(620, 430, 100, 25);
        dessertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add action for Dessert button
                // You can implement the logic for selecting desserts here
            }
        });
        add(dessertButton);

        JButton drinksButton = new JButton("Drinks");
        drinksButton.setBounds(740, 430, 100, 25);
        drinksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add action for Drinks button
                // You can implement the logic for selecting drinks here
            }
        });
        add(drinksButton);

        JButton setMenuButton = new JButton("Set Menu");
        setMenuButton.setBounds(860, 430, 100, 25);
        setMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add action for Set Menu button
                // You can implement the logic for selecting set menu here
            }
        });
        add(setMenuButton);

        JButton calculateTotalButton = new JButton("Calculate Total");
        calculateTotalButton.setBounds(20, 500, 150, 25);
        calculateTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotal();
            }
        });
        add(calculateTotalButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(180, 500, 80, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToStaffFrame();
            }
        });
        add(backButton);

        initializeItemsFromFile();

        setVisible(true);
    }

    private void addItem() {
    // Get the selected item from the menuItemsList
    MenuItem selectedMenuItem = menuItemsList.getSelectedValue();

    if (selectedMenuItem != null) {
        // Get the quantity from the quantityTextField
        int quantity = getQuantity();

        // Calculate the total price for the added item
        double itemTotal = selectedMenuItem.getPrice() * quantity;

        // Create a new MenuItem with the updated quantity
        MenuItem addedItem = new MenuItem(selectedMenuItem.getId(), selectedMenuItem.getName(), selectedMenuItem.getPrice(), selectedMenuItem.getType());
        addedItem.setQuantity(quantity);

        // Add the item to the addedItemsModel
        addedItemsModel.addElement(addedItem);

        // Scroll to the newly added item
        addedItemsList.ensureIndexIsVisible(addedItemsModel.getSize() - 1);

        // Optional: You can also update the display in the addedItemsScrollPane
        addedItemsList.repaint(); // This will refresh the list

        // Optional: Display the item in the addItemsScrollPane
        addedItemsList.setSelectedIndex(addedItemsModel.getSize() - 1);
    }
}




    private void deleteItem() {
    // Get the selected index from addedItemsList
    int selectedIndex = addedItemsList.getSelectedIndex();

    if (selectedIndex != -1) {
        // Remove the selected item from addedItemsModel
        addedItemsModel.remove(selectedIndex);
    } else {
        JOptionPane.showMessageDialog(this, "Please select an item to delete.", "Delete Item", JOptionPane.WARNING_MESSAGE);
    }
}


    private void showAllItems() {
        int itemCount = addedItemsModel.getSize();

        if (itemCount > 0) {
            System.out.println("Order Items:");

            for (int i = 0; i < itemCount; i++) {
                MenuItem menuItem = addedItemsModel.getElementAt(i);
                int quantity = menuItem.getQuantity();

                System.out.println(String.format("[%d] %s - %.2f - %s - Quantity: %d",
                        menuItem.getId(), menuItem.getName(), menuItem.getPrice(), menuItem.getType(), quantity));
            }
        } else {
            System.out.println("No items in the order.");
        }
    }

     private void calculateTotal() {
        int itemCount = addedItemsModel.getSize();
        double totalPrice = 0;

        if (itemCount > 0) {
            System.out.println("Order Items:");

            for (int i = 0; i < itemCount; i++) {
                MenuItem menuItem = addedItemsModel.getElementAt(i);
                int quantity = menuItem.getQuantity();
                double itemTotal = menuItem.getPrice() * quantity;
                totalPrice += itemTotal;
            }

            // Show total in a popup message
            JOptionPane.showMessageDialog(this, "Total Price: " + totalPrice, "Total", JOptionPane.INFORMATION_MESSAGE);

            // Write total price to a file
            writeTotalPriceToFile(totalPrice);
        } else {
            System.out.println("No items in the order.");

            // Show a message if there are no items
            JOptionPane.showMessageDialog(this, "No items in the order.", "Total", JOptionPane.INFORMATION_MESSAGE);
        }
    }
	
	
	
	private void writeTotalPriceToFile(double totalPrice) {
        try (FileWriter writer = new FileWriter("price.txt")) {
            writer.write("Total Price: " + totalPrice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	


    private int getQuantity() {
        try {
            return Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            // If no quantity is provided, default to 1
            return 1;
        }
    }

    private void switchToStaffFrame() {
        dispose();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StaffFrame mFrame = new StaffFrame();
                mFrame.setVisible(true);
            }
        });
    }

    private void initializeItemsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            int id = 1;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 4) {
                    String name = parts[1].trim();

                    // Validate and parse the price
                    double price;
                    try {
                        // Replace commas with dots and then parse
                        price = Double.parseDouble(parts[4].trim().replace(",", "."));
                    } catch (NumberFormatException e) {
                        // Handle the case where the price is not a valid double
                        System.err.println("Invalid price format for item: " + name);
                        continue; // Skip this line and move to the next iteration
                    }

                    String type = parts[3].trim();

                    MenuItem menuItem = new MenuItem(id, name, price, type);
                    menuItemsModel.addElement(menuItem);
                    id++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
