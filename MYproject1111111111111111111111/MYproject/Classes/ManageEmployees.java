package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ManageEmployees extends JFrame {

    private JTextField idTextField, usernameTextField;
    private JPasswordField passwordField, confirmPasswordField;

    public ManageEmployees() {
        setTitle("Manage Employees");
        setSize(1080, 720);
        setPreferredSize(new Dimension(1080, 720));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(102, 255, 255));
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(102, 255, 255));
        panel.setPreferredSize(new Dimension(400, 300));
        add(panel);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(40, 50, 80, 25);
        panel.add(idLabel);

        idTextField = new JTextField();
        idTextField.setBounds(150, 50, 200, 25);
        panel.add(idTextField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(40, 90, 80, 25);
        panel.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(150, 90, 200, 25);
        panel.add(usernameTextField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, 130, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 200, 25);
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(40, 170, 120, 25);
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(160, 170, 190, 25);
        panel.add(confirmPasswordField);

        idTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        usernameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isLetter(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SPACE))) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        JButton addButton = new JButton("Add Staff");
        addButton.setBounds(150, 210, 120, 30);
        panel.add(addButton);
        addButton.setBackground(new Color(0, 102, 102));

        JButton backButton = new JButton("Back");
        backButton.setBounds(40, 210, 90, 30);
        panel.add(backButton);
        backButton.setBackground(new Color(204, 102, 0));

        addButton.addActionListener(e -> addStaff());
        backButton.addActionListener(e -> goBackToMainFrame());

        pack();
        setVisible(true);
    }

    private void addStaff() {
        String id = idTextField.getText();
        String username = usernameTextField.getText();
        char[] passwordChars = passwordField.getPassword();
        char[] confirmPasswordChars = confirmPasswordField.getPassword();

        String password = new String(passwordChars);
        String confirmPassword = new String(confirmPasswordChars);

        if (password.equals(confirmPassword)) {
            writeStaffInfoToFile(id, username, password);
            JOptionPane.showMessageDialog(this, "Staff added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeStaffInfoToFile(String id, String username, String password) {
        String fileName = "staff_info.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("ID: " + id + ", Username: " + username + ", Password: " + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error writing staff information to file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToMainFrame() {
        this.setVisible(false);
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        this.dispose();
    }

}
