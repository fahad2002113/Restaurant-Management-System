package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class MainFrame extends JFrame {

    private List<JButton> buttons = new ArrayList<>();

    public MainFrame() {
        setTitle("Admin");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Load the image
        BufferedImage image = loadImage("C:\\Users\\DELL\\Documents\\The challengers\\MYproject1111111111111111111111\\MYproject\\img\\H14_Restaurant-Listicle.jpg");

        // Check if the image was loaded successfully
        if (image != null) {
            ImageIcon icon = new ImageIcon(resizeImage(image, getWidth(), getHeight()));
            JLabel imageLabel = new JLabel(icon);

            // Set layout to null to allow precise component placement
            setLayout(null);

            // Set the size and position of the image label
            imageLabel.setBounds(0, 0, getWidth(), getHeight());

            // Add the image label directly to the content pane
            getContentPane().add(imageLabel);

            // Add buttons with explicit x, y, width, and height
            addButton("Show Menu", 100, 50, 165, 82, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add action for Show Menu button
                    openMenues();
                }
            });

            addButton("Manage Employees", 100, 150, 165, 82, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add action for Manage Employees button
                    openManageEmployees();
                }
            });

            addButton("Order Management", 100, 250, 165, 82, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add action for Order Management button
                    openOrderManagementFrame();
                }
            });

            addButton("Dashboard", 100, 350, 165, 82, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add action for Dashboard button
                    openDashboardFrame();
                }
            });

            addButton("Logout", 100, 450, 165, 82, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Add action for Logout button
                    int result = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure?", "Logout", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // If 'Yes' is clicked, switch to the login frame
                        switchToLoginFrame();
                    }
                }
            });

            // Set button color to (255, 204, 204)
            setButtonColor(buttons);
        } else {
            // Display an error message if the image loading fails
            JOptionPane.showMessageDialog(this, "Failed to load the image.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setButtonColor(List<JButton> buttons) {
        for (JButton button : buttons) {
            button.setBackground(new Color(255, 204, 204));
            // Set button text size to 14
            button.setFont(new Font("Arial", Font.PLAIN, 14));
        }
    }

    private BufferedImage loadImage(String filePath) {
        try {
            return ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Image resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        return originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
    }

    private void addButton(String text, int buttonX, int buttonY, int buttonWidth, int buttonHeight, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        button.addActionListener(actionListener);
        getContentPane().add(button);
        buttons.add(button);
    }

    private void openDashboardFrame() {
        // Hide the current frame
        this.setVisible(false);

        // Create and show the Dashboard frame
        DashboardFrame dashboardFrame = new DashboardFrame();
        dashboardFrame.setVisible(true);

        // Dispose of the current frame to release resources
        this.dispose();
    }

    private void switchToLoginFrame() {
        // Hide the current frame
        this.setVisible(false);

        // Create and show the login frame
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);

        // Dispose of the current frame to release resources
        this.dispose();
    }

    private void openManageEmployees() {
        // Hide the current frame
        this.setVisible(false);

        // Create and show the ManageEmployees frame
        ManageEmployees manageEmployees = new ManageEmployees();
        manageEmployees.setVisible(true);

        // Dispose of the current frame to release resources
        this.dispose();
    }

    private void openMenues() {
        // Hide the current frame
        this.setVisible(false);

        // Create and show the Menues
        Menues Menues = new Menues();
        Menues.setVisible(true);

        // Dispose of the current frame to release resources
        this.dispose();
    }

    private void openOrderManagementFrame() {
        // Hide the current frame
        this.setVisible(false);

        // Create and show the OrderManagementFrame
        OrderManagementFrame orderManagementFrame = new OrderManagementFrame();
        orderManagementFrame.setVisible(true);

        // Dispose of the current frame to release resources
        this.dispose();
    }
}
