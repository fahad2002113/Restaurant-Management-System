package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ShowMenuFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public ShowMenuFrame() {
        setTitle("Another Frame");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a table with columns: Serial No, Name, Menu, Id, Price, Type
        String[] columnNames = {"Serial No", "Name", "Menu", "Id", "Price", "Type"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);

        // Add a scroll pane to the table
        JScrollPane tableScrollPane = new JScrollPane(table);
        getContentPane().add(tableScrollPane, BorderLayout.CENTER);

        // Add a back button
        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToStaffFrame();
            }
        });
        getContentPane().add(backButton, BorderLayout.SOUTH);

        // Add an Order button
        JButton orderButton = new JButton("Order");
        customizeButton(orderButton);
        //orderButton.addActionListener(e -> navigateToOrderManagementFrame());
        getContentPane().add(orderButton, BorderLayout.EAST);

        // Call the method to initially load data from menu.txt
        loadMenuData();

        setVisible(true);
    }

    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    private void loadMenuData() {
        // Clear existing data from the table
        tableModel.setRowCount(0);

        // Read data from menu.txt and add it to the table
        try (BufferedReader reader = new BufferedReader(new FileReader("menu.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into columns based on a delimiter (e.g., comma)
                String[] data = line.split(",");

                // Add the data to the table model
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately (e.g., show an error message)
        }
    }

    private void navigateToStaffFrame() {
        dispose();  // Close the current frame
        StaffFrame staffFrame = new StaffFrame();  // Create a new instance of StaffFrame
        staffFrame.setVisible(true);  // Make the StaffFrame visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShowMenuFrame());
    }
}
