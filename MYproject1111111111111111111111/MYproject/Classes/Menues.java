package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.io.*;

public class Menues extends JFrame {

    private JPanel leftPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField textField1, textField2, searchTextField, textFieldPrice;
    private JComboBox<String> comboBox1, comboBox2;
    private JButton searchButton;
	

    private String dataFilePath = "menu.txt";
private int lastSavedSerialNumber = 1;
private int serialNumber;




    public Menues() {
        setTitle("Menues");
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(115, 167, 186));
        getContentPane().add(leftPanel);

        // Labels and Text Fields
        JLabel label1 = new JLabel("Name :");
        label1.setBounds(40, 220, 80, 25);
        leftPanel.add(label1);

        textField1 = new JTextField();
        textField1.setBounds(150, 220, 200, 25);
        leftPanel.add(textField1);

        JLabel label2 = new JLabel("Id :");
        label2.setBounds(40, 255, 80, 25);
        leftPanel.add(label2);

        textField2 = new JTextField();
        textField2.setBounds(150, 255, 200, 25);
        leftPanel.add(textField2);

        JLabel label3 = new JLabel("Menu :");
        label3.setBounds(40, 290, 80, 25);
        leftPanel.add(label3);

        String[] options1 = {"Dessert", "Drinks", "Set menu"};
        comboBox1 = new JComboBox<>(options1);
        comboBox1.setBounds(150, 290, 200, 25);
        leftPanel.add(comboBox1);

        JLabel label4 = new JLabel("Price :");
        label4.setBounds(40, 325, 80, 25);
        leftPanel.add(label4);

        textFieldPrice = new JTextField();
        textFieldPrice.setBounds(150, 325, 200, 25);
        leftPanel.add(textFieldPrice);

        JButton addButton = new JButton("Add");
        customizeButton(addButton);
        addButton.setBounds(60, 370, 150, 25);
        leftPanel.add(addButton);

        JButton editButton = new JButton("Edit");
        customizeButton(editButton);
        editButton.setBounds(240, 370, 150, 25);
        leftPanel.add(editButton);

        JButton deleteButton = new JButton("Delete");
        customizeButton(deleteButton);
        deleteButton.setBounds(240, 405, 150, 25);
        leftPanel.add(deleteButton);

        searchTextField = new JTextField();
        searchTextField.setBounds(200, 40, 500, 25);
        leftPanel.add(searchTextField);

        JButton searchButton = new JButton("Search");
        customizeButton(searchButton);
        searchButton.setBounds(730, 40, 80, 25);
        leftPanel.add(searchButton);

        JButton refreshButton = new JButton("Refresh");
        customizeButton(refreshButton);
        refreshButton.setBounds(840, 40, 80, 25);
        leftPanel.add(refreshButton);

        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        separator.setBounds(432, 100, 2, getHeight() - 100);
        leftPanel.add(separator);

        String[] columnNames = {"Serial No", "Name", "Menu", "Id", "Price"};
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(450, 150, 600, 200);
        leftPanel.add(tableScrollPane);

        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.setBounds(60, 405, 150, 25);
        leftPanel.add(backButton);
		
		loadMenuDataFromFile();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                leftPanel.setBounds(0, 0, getWidth(), getHeight());
                separator.setBounds(432, 100, 2, getHeight() - 100);
                tableScrollPane.setBounds(450, 150, getWidth() * 3 / 5 - 50, getHeight() - 200);
            }
        });

        addButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String column2Value = textField1.getText();
        String column3Value = comboBox1.getSelectedItem().toString();
        String column4Value = textField2.getText();
        String column5Value = textFieldPrice.getText();

        Object[] rowData = {serialNumber++, column2Value, column3Value, column4Value, column5Value};
        tableModel.addRow(rowData);

        saveMenuDataToFile();
    }
});


        deleteButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = table.getSelectedRows();
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int deletedSerialNumber;
            try {
                deletedSerialNumber = Integer.parseInt(tableModel.getValueAt(selectedRows[i], 0).toString());
            } catch (NumberFormatException ex) {
                // Handle the case when the value is not a valid integer
                deletedSerialNumber = -1;
            }

            tableModel.removeRow(selectedRows[i]);

            int insertIndex = findInsertIndex(deletedSerialNumber);
            Object[] rowData = {deletedSerialNumber, "", "", "", ""};
            tableModel.insertRow(insertIndex, rowData);
        }

        saveMenuDataToFile();
    }

    private int findInsertIndex(int serialNumber) {
        int rowCount = tableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            int currentSerialNumber;
            try {
                currentSerialNumber = Integer.parseInt(tableModel.getValueAt(i, 0).toString());
            } catch (NumberFormatException ex) {
                // Handle the case when the value is not a valid integer
                currentSerialNumber = -1;
            }

            if (serialNumber < currentSerialNumber || currentSerialNumber == -1) {
                return i;
            }
        }

        return rowCount;
    }
});


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchTextField.getText().toLowerCase();
                table.clearSelection();

                for (int i = 0; i < table.getRowCount(); i++) {
                    for (int j = 1; j < table.getColumnCount(); j++) {
                        String cellValue = table.getValueAt(i, j).toString().toLowerCase();

                        if (cellValue.contains(searchTerm)) {
                            table.addRowSelectionInterval(i, i);
                            break;
                        }
                    }
                }

                saveMenuDataToFile();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                    boolean allColumnsEmpty = true;

                    for (int j = 1; j < tableModel.getColumnCount(); j++) {
                        Object value = tableModel.getValueAt(i, j);

                        if (value != null && !value.toString().trim().isEmpty()) {
                            allColumnsEmpty = false;
                            break;
                        }
                    }

                    if (allColumnsEmpty) {
                        tableModel.removeRow(i);
                    }
                }

                saveMenuDataToFile();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navigateToMainFrame();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String column2Value = tableModel.getValueAt(selectedRow, 1).toString();
                    String column3Value = tableModel.getValueAt(selectedRow, 2).toString();
                    String column4Value = tableModel.getValueAt(selectedRow, 3).toString();
                    String column5Value = tableModel.getValueAt(selectedRow, 4).toString();

                    textField1.setText(column2Value);
                    comboBox1.setSelectedItem(column3Value);
                    textField2.setText(column4Value);
                    textFieldPrice.setText(column5Value);

                    JDialog editDialog = new JDialog(Menues.this, "Edit Row", true);
                    editDialog.setLayout(new GridLayout(5, 2, 10, 10));
                    editDialog.setSize(300, 200);

                    editDialog.add(new JLabel("Name:"));
                    JTextField editTextField1 = new JTextField(column2Value);
                    editDialog.add(editTextField1);

                    editDialog.add(new JLabel("Menu: "));
                    JComboBox<String> editComboBox1 = new JComboBox<>(new String[]{"Dessert", "Drink", "Set menu"});
                    editComboBox1.setSelectedItem(column3Value);
                    editDialog.add(editComboBox1);

                    editDialog.add(new JLabel("ID: "));
                    JTextField editTextField2 = new JTextField(column4Value);
                    editDialog.add(editTextField2);

                    editDialog.add(new JLabel("Price:"));
                    JTextField editTextFieldPrice = new JTextField(column5Value);
                    editDialog.add(editTextFieldPrice);

                    JButton saveButton = new JButton("Save");
                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String modifiedColumn2Value = editTextField1.getText();
                            String modifiedColumn3Value = editComboBox1.getSelectedItem().toString();
                            String modifiedColumn4Value = editTextField2.getText();
                            String modifiedColumn5Value = editTextFieldPrice.getText();

                            tableModel.setValueAt(modifiedColumn2Value, selectedRow, 1);
                            tableModel.setValueAt(modifiedColumn3Value, selectedRow, 2);
                            tableModel.setValueAt(modifiedColumn4Value, selectedRow, 3);
                            tableModel.setValueAt(modifiedColumn5Value, selectedRow, 4);

                            editDialog.dispose();
                            saveMenuDataToFile();
                        }
                    });

                    editDialog.add(saveButton);
                    editDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Menues.this, "Please select a row to edit.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private void customizeButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 152, 219));
        button.setFont(new Font("Arial", Font.PLAIN, 16));

        Border roundedBorder = new LineBorder(Color.WHITE, 2, true);
        button.setBorder(roundedBorder);
    }

    private void loadMenuDataFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] rowData = line.split(",");
            tableModel.addRow(rowData);
        }

        // Set the last saved serial number to the highest serial number in the table
        if (tableModel.getRowCount() > 0) {
            lastSavedSerialNumber = Integer.parseInt(tableModel.getValueAt(tableModel.getRowCount() - 1, 0).toString()) + 1;
        }

        // Initialize the serial number with the last saved serial number
        serialNumber = lastSavedSerialNumber;
    } catch (IOException e) {
        e.printStackTrace();
    }
}




    private void saveMenuDataToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                line.append(tableModel.getValueAt(i, j));
                if (j < tableModel.getColumnCount() - 1) {
                    line.append(",");
                }
            }
            writer.write(line.toString());
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void navigateToMainFrame() {
        dispose();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
