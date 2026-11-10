package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private JTextField userIDField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox; // Added combo box for roles

    public LoginFrame() {
        setTitle("Login");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(102, 255, 255));
        initComponents();
        addComponents();
    }

    private void initComponents() {
        userIDField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"Manager", "Staff"}); // Added combo box with roles

        userIDField.setPreferredSize(new Dimension(240, 30));
        passwordField.setPreferredSize(new Dimension(240, 30));
        roleComboBox.setPreferredSize(new Dimension(240, 30)); // Set preferred size for combo box

        // Add InputVerifier to userIDField
        userIDField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField field = (JTextField) input;
                String userIDText = field.getText();

                // Verify if input is numeric
                if (userIDText.matches("\\d*")) {
                    return true; // Accepts only numeric input
                } else {
                    // Display an error message for invalid input
                    showMessage("Invalid UserID. Please enter numbers only.", "Invalid UserID");
                    return false;
                }
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userID = userIDField.getText();
                char[] password = passwordField.getPassword();
                String selectedRole = (String) roleComboBox.getSelectedItem(); // Get selected role

                // Perform login authentication based on the data in "login_information.txt"
                if (authenticateUser(userID, new String(password), selectedRole)) {
                    if (selectedRole.equals("Manager")) {
                        switchToMainFrame();
                    } else if (selectedRole.equals("Staff")) {
                        switchToStaffFrame();
                    }
                } else {
                    showMessage("Invalid UserID, Password, or Role. Please try again.", "Login Failed");
                }
            }
        });

        // Set background color for the login button
        loginButton.setBackground(new Color(0, 102, 102));
        // Set foreground color to make text readable
        loginButton.setForeground(Color.WHITE);

        JLabel titleLabel = new JLabel("Login Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel signUpLabel = new JLabel("Don't have an account?");
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the sign-up frame
                switchToSignUpFrame();
            }
        });

        // Set background color for the signup button
        signUpButton.setBackground(new Color(0, 102, 102));
        // Set foreground color to make text readable
        signUpButton.setForeground(Color.WHITE);

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 10, 10);

        add(titleLabel, gbc);
        gbc.gridy++;
        add(new JLabel("UserID:"), gbc);
        gbc.gridy++;
        add(userIDField, gbc);
        gbc.gridy++;
        add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        add(passwordField, gbc);
        gbc.gridy++;
        add(new JLabel("Role:"), gbc); // Label for the combo box
        gbc.gridy++;
        add(roleComboBox, gbc); // Combo box
        gbc.gridy++;
        add(new JLabel(), gbc); // Empty label for spacing
        gbc.gridy++;
        add(loginButton, gbc);
        gbc.gridy++;
        add(new JLabel(), gbc); // Empty label for spacing
        gbc.gridy++;
        add(signUpLabel, gbc);
        gbc.gridy++;
        add(signUpButton, gbc);
    }

    private void addComponents() {
        // Add components to the frame
    }

    private void switchToSignUpFrame() {
        // Hide the current login frame
        this.setVisible(false);

        // Create and show the sign-up frame
        SignUpFrame signUpFrame = new SignUpFrame();
        signUpFrame.setVisible(true);

        // Dispose of the login frame to release resources
        this.dispose();
    }

    private void switchToMainFrame() {
        // Hide the current login frame
        this.setVisible(false);

        // Create and show the MainFrame
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);

        // Dispose of the login frame to release resources
        this.dispose();
    }

    private void switchToStaffFrame() {
        // Hide the current login frame
        this.setVisible(false);

        // Create and show the StaffFrame
        StaffFrame staffFrame = new StaffFrame();
        staffFrame.setVisible(true);

        // Dispose of the login frame to release resources
        this.dispose();
    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private boolean authenticateUser(String userID, String password, String role) {
    String loginFilePath = "login_information.txt";
    String staffInfoFilePath = "staff_info.txt";

    try (BufferedReader loginReader = new BufferedReader(new FileReader(loginFilePath));
         BufferedReader staffInfoReader = new BufferedReader(new FileReader(staffInfoFilePath))) {

        String loginLine;
        while ((loginLine = loginReader.readLine()) != null) {
            String[] loginParts = loginLine.split(", ");
            String storedUserID = loginParts[0].split(": ")[1];
            String storedPassword = loginParts[2].split(": ")[1];

            System.out.println("Stored - UserID: " + storedUserID + ", Password: " + storedPassword);
            System.out.println("Input - UserID: " + userID + ", Password: " + password);

            if (userID.equals(storedUserID) && password.equals(storedPassword) && role.equals("Manager")) {
                return true;
            }
        }

        if ("Staff".equals(role)) {
    String staffInfoLine;
    while ((staffInfoLine = staffInfoReader.readLine()) != null) {
        String[] staffInfoParts = staffInfoLine.split(", ");
        String staffUserID = staffInfoParts[0].split(": ")[1];
        String staffPassword = staffInfoParts[2].split(": ")[1]; // Corrected index

        System.out.println("StaffInfo - UserID: " + staffUserID + ", Password: " + staffPassword);
        System.out.println("Input - UserID: " + userID + ", Password: " + password);

        if (userID.equals(staffUserID) && password.equals(staffPassword)) {
            return true;
        }
    }
}

    } catch (IOException e) {
        e.printStackTrace();
        showMessage("Error reading login information.", "Error");
    }

    return false;
}
}
