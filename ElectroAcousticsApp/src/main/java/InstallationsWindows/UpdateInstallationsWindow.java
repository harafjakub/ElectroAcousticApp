package InstallationsWindows;

import DatabaseUse.ConnectDB;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateInstallationsWindow extends JFrame {
    public UpdateInstallationsWindow(JTable dataTable){
        super("EEA - Update Installation");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(420, 380);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        SetComboBoxes();
        SetCurrentData(dataTable);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startDate.getDate() != null) {
                    String selectedLocation = locationComboBox.getSelectedItem().toString();
                    String locationID = selectedLocation.split("\\.")[0].trim();

                    String selectedCustomer = customerComboBox.getSelectedItem().toString();
                    String customerID = selectedCustomer.split("\\.")[0].trim();

                    String selectedEmployee = employeeComboBox.getSelectedItem().toString();
                    String employeeID = selectedEmployee.split("\\.")[0].trim();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UPDATE `electroacoustics_db`.`Installations` SET " +
                            "`LocationID`='" + locationID + "', " +
                            "`CustomerID`='" + customerID + "', " +
                            "`EmployeeID`='" + employeeID + "', " +
                            "`DateStart`='" + startDate.getDate() + "', " +
                            "`DateEnd`=" + (endDate.getDate() != null ? "'" + endDate.getDate() + "'" : "NULL") + ", " +
                            "`Description`=" + (!descriptionFormattedTextField.getText().isEmpty() ? "'" + descriptionFormattedTextField.getText() + "'" : "NULL") +
                            " WHERE `InstallationID`=" + idFormattedTextField.getText()
                    );
                    ConnectDB.updateTable(stringBuilder.toString());

                    JOptionPane.showMessageDialog(null, "Installation updated successfully");
                } else {
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

    private void SetCurrentData(JTable dataTable) {
        try {
            int selectedRow = dataTable.getSelectedRow();
            idFormattedTextField.setText(dataTable.getValueAt(selectedRow, 0).toString());
            ResultSet resultSet = ConnectDB.executeSelectQuery("SELECT LocationID, Name FROM electroacoustics_db.Locations WHERE LocationID = "+dataTable.getValueAt(selectedRow, 1).toString());
            while (resultSet.next()) {
                int locationID = resultSet.getInt("LocationID");
                String name = resultSet.getString("Name");
                locationComboBox.setSelectedItem(locationID + ". " + name);
            }
            resultSet = ConnectDB.executeSelectQuery("SELECT CustomerID, Name FROM electroacoustics_db.Customers WHERE CustomerID = "+dataTable.getValueAt(selectedRow, 2).toString());
            while (resultSet.next()) {
                int customerID = resultSet.getInt("CustomerID");
                String name = resultSet.getString("Name");
                customerComboBox.setSelectedItem(customerID + ". " + name);
            }
            resultSet = ConnectDB.executeSelectQuery("SELECT EmployeeID, Name FROM electroacoustics_db.Employees WHERE EmployeeID = "+dataTable.getValueAt(selectedRow, 3).toString());
            while (resultSet.next()) {
                int employeeID = resultSet.getInt("EmployeeID");
                String name = resultSet.getString("Name");
                employeeComboBox.setSelectedItem(employeeID + ". " + name);
            }
            java.sql.Date startDateValue = (java.sql.Date) dataTable.getValueAt(selectedRow, 4);
            startDate.setDate(startDateValue.toLocalDate());
            java.sql.Date endDateValue = (java.sql.Date) dataTable.getValueAt(selectedRow, 5);
            if (endDateValue != null) {
                endDate.setDate(endDateValue.toLocalDate());
            } else {
                endDate.setDate(null);
            }
            descriptionFormattedTextField.setText(dataTable.getValueAt(selectedRow, 6) != null ? dataTable.getValueAt(selectedRow, 6).toString() : "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private JPanel mainPanel;
    private JButton goBackButton;
    private JButton updateButton;
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
