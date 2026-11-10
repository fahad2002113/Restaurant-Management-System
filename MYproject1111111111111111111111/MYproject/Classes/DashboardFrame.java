package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DashboardFrame extends JFrame {

    private JLabel totalPriceLabel;

    public DashboardFrame() {
        setTitle("Dashboard");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        totalPriceLabel = new JLabel("Total Price: ");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));

        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton showPriceButton = new JButton("Show Price");
        JButton backButton = new JButton("Back to MainFrame");

        showPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the label with the data from the price.txt file
                updateTotalPriceLabelFromFile("price.txt");

                JOptionPane.showMessageDialog(DashboardFrame.this, "Label updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DashboardFrame.this.setVisible(false);
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                DashboardFrame.this.dispose();
            }
        });

        buttonPanel.add(showPriceButton);
        buttonPanel.add(backButton);

        add(totalPriceLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateTotalPriceLabelFromFile(String fileName) {
        StringBuilder totalPriceText = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                totalPriceText.append(line).append("<br>"); // Use HTML line break for multiple lines
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the label text with HTML to allow line breaks
        totalPriceLabel.setText("<html>" + totalPriceText.toString() + "</html>");
    }
}
