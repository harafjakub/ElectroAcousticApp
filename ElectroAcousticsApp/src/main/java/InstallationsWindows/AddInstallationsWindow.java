package InstallationsWindows;

import DatabaseUse.ConnectDB;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddInstallationsWindow extends JFrame {
    public AddInstallationsWindow(){
        super("EEA - Add Installation");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(420, 380);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        SetComboBoxes();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startDate.getDate() != null){

                    String selectedLocation = locationComboBox.getSelectedItem().toString();
                    String locationID = selectedLocation.split("\\.")[0].trim();

                    String selectedCustomer = customerComboBox.getSelectedItem().toString();
                    String customerID = selectedCustomer.split("\\.")[0].trim();

                    String selectedEmployee = employeeComboBox.getSelectedItem().toString();
                    String employeeID = selectedEmployee.split("\\.")[0].trim();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("INSERT INTO `electroacoustics_db`.`Installations` (`LocationID`, `CustomerID`, `EmployeeID`, `DateStart`, `DateEnd`, `Description`) VALUES ('"+
                            locationID+"', '"+
                            customerID+"', '"+
                            employeeID+"', '"+
                            startDate.getDate()+"', "+
                            (endDate.getDate() != null ? "'" + endDate.getDate() + "'" : "NULL") + ", " +
                            (!descriptionFormattedTextField.getText().isEmpty() ? "'" + descriptionFormattedTextField.getText() + "'" : "NULL") +
                            ")");
                    ConnectDB.insertIntoTable(stringBuilder.toString());

                    JOptionPane.showMessageDialog(null, "Installation added successfully");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please fill all required fields");
                }
            }
        });
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InstallationsWindow();
            }
        });
    }
    private void SetComboBoxes(){
         try {
             ResultSet resultSet = ConnectDB.executeSelectQuery("SELECT LocationID, Name FROM electroacoustics_db.Locations");
             while (resultSet.next()) {
                int locationID = resultSet.getInt("LocationID");
                String name = resultSet.getString("Name");
                locationComboBox.addItem(locationID + ". " + name);
            }
            resultSet = ConnectDB.executeSelectQuery("SELECT CustomerID, Name FROM electroacoustics_db.Customers");
            while (resultSet.next()) {
                int customerID = resultSet.getInt("CustomerID");
                String name = resultSet.getString("Name");
                customerComboBox.addItem(customerID + ". " + name);
            }
            resultSet = ConnectDB.executeSelectQuery("SELECT EmployeeID, Name FROM electroacoustics_db.Employees");
            while (resultSet.next()) {
                int employeeID = resultSet.getInt("EmployeeID");
                String name = resultSet.getString("Name");
                employeeComboBox.addItem(employeeID + ". " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddInstallationsWindow();
    }
    private JPanel mainPanel;
    private JButton goBackButton;
    private JButton addButton;
    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField descriptionFormattedTextField;
    private JLabel idLabel;
    private JLabel addressLabel;
    private JLabel phoneNumLabel;
    private JLabel nameLabel;
    private JComboBox locationComboBox;
    private JComboBox customerComboBox;
    private JComboBox employeeComboBox;
    private DatePicker startDate;
    private DatePicker endDate;
}
