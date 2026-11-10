package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SignUpFrame extends JFrame {
    private JTextField idField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleComboBox; // Added combo box for roles

    public SignUpFrame() {
        setTitle("Restaurant Management System - Sign Up");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(102, 255, 255));
        initComponents();
        addComponents();
    }

    private void initComponents() {
        idField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"Manager", "Staff"}); // Added combo box with roles

        // Set preferred size for the text fields and combo box
        idField.setPreferredSize(new Dimension(240, 30));
        usernameField.setPreferredSize(new Dimension(240, 30));
        passwordField.setPreferredSize(new Dimension(240, 30));
        confirmPasswordField.setPreferredSize(new Dimension(240, 30));
        roleComboBox.setPreferredSize(new Dimension(240, 30));

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if any text field is empty
                if (idField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                        new String(passwordField.getPassword()).isEmpty() ||
                        new String(confirmPasswordField.getPassword()).isEmpty()) {
                    showMessage("Please fill the form.", "Form Incomplete");
                    return;
                }

                // Validate ID (numeric input)
                String id = idField.getText();
                if (!id.matches("\\d+")) {
                    showMessage("Invalid ID. Please enter numbers only.", "Invalid ID");
                    return;
                }

                // Validate Username (text input)
                String username = usernameField.getText();
                if (!username.matches("[a-zA-Z]+")) {
                    showMessage("Invalid Username. Please enter text only.", "Invalid Username");
                    return;
                }

                // Validate Password, Confirm Password, and Role
                char[] password = passwordField.getPassword();
                char[] confirmPassword = confirmPasswordField.getPassword();

                if (!new String(password).equals(new String(confirmPassword))) {
                    showMessage("Passwords do not match. Please re-enter.", "Password Mismatch");
                    return;
                }

                String selectedRole = (String) roleComboBox.getSelectedItem(); // Get selected role

                // Save login information
                saveLoginInformation(id, username, new String(password), selectedRole);

                // Switch to the login frame
                switchToLoginFrame();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the login frame
                switchToLoginFrame();
            }
        });

        // Set background color for the buttons
        signUpButton.setBackground(new Color(0, 102, 102));
        signUpButton.setForeground(Color.WHITE);

        backButton.setBackground(new Color(0, 102, 102));
        backButton.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel("Sign Up for Restaurant Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(backButton, gbc);
        gbc.gridy++;
        add(titleLabel, gbc);
        gbc.gridy++;
        add(new JLabel("ID:"), gbc);
        gbc.gridy++;
        add(idField, gbc);
        gbc.gridy++;
        add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        add(usernameField, gbc);
        gbc.gridy++;
        add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        add(passwordField, gbc);
        gbc.gridy++;
        add(new JLabel("Confirm Password:"), gbc);
        gbc.gridy++;
        add(confirmPasswordField, gbc);
        gbc.gridy++;
        add(new JLabel("Role:"), gbc); // Label for the combo box
        gbc.gridy++;
        add(roleComboBox, gbc); // Combo box
        gbc.gridy++;
        add(new JLabel(), gbc); // Empty label for spacing
        gbc.gridy++;
        add(signUpButton, gbc);
    }

    private void addComponents() {
        // Add components to the frame
    }

    private void switchToLoginFrame() {
        // Hide the current sign-up frame
        this.setVisible(false);

        // Create and show the login frame
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);

        // Dispose of the sign-up frame to release resources
        this.dispose();
    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void saveLoginInformation(String id, String username, String password, String role) {
        // File path to save login information
        String filePath = "login_information.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            // Append login information to the file
            writer.println("ID: " + id + ", Username: " + username + ", Password: " + password + ", Role: " + role);
            showMessage("Login information saved successfully.", "Success");
        } catch (IOException e) {
            showMessage("Error saving login information.", "Error");
            e.printStackTrace();
        }
    }
}
