package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class StaffFrame extends JFrame {
    private JLabel imageLabel;

    public StaffFrame() {
        setTitle("Staff Frame");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 204, 102));

        // Add an image (replace with the actual path to your image)
        ImageIcon icon = new ImageIcon("path/to/your/image.jpg");
        imageLabel = new JLabel(icon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adjust the size of the image label to match the size of the window
        adjustImage();

        JButton showMenuButton = new JButton("Show Menu");
        showMenuButton.addActionListener(e -> showMenu());

        JButton orderManagementButton = new JButton("Order");
        orderManagementButton.addActionListener(e -> orderManagement());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> goBack());

        // Layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(showMenuButton);
        buttonPanel.add(orderManagementButton);
        buttonPanel.add(backButton);

        add(imageLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void adjustImage() {
        // Get the size of the frame
        int frameWidth = getWidth();
        int frameHeight = getHeight();

        // Get the size of the image label
        int imageWidth = imageLabel.getIcon().getIconWidth();
        int imageHeight = imageLabel.getIcon().getIconHeight();

        // Calculate the new size for the image label
        int newWidth = Math.min(frameWidth, imageWidth);
        int newHeight = Math.min(frameHeight, imageHeight);

        // Set the preferred size for the image label
        imageLabel.setPreferredSize(new Dimension(newWidth, newHeight));

        // Revalidate and repaint the frame to apply changes
        revalidate();
        repaint();
    }

    private void showMenu() {
        // Switch to the ShowMenuFrame
        ShowMenuFrame showMenuFrame = new ShowMenuFrame();
        showMenuFrame.setVisible(true);

        // Hide the current staff frame
        this.setVisible(false);
    }

    private void orderManagement() {
         //Switch to the StaffManagementFrame
        OrderManagementFrame1 StaffManagementFrame = new OrderManagementFrame1();
        StaffManagementFrame.setVisible(true);

        // Hide the current staff frame
        this.setVisible(false);
    }

    private void goBack() {
        // Add code to go back to the previous frame
        this.dispose(); // Close the current frame
        // You may want to show the previous frame, e.g., LoginFrame
       LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}
