package OrdersWindows;

import DatabaseUse.ConnectDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateOrdersWindow extends JFrame {
    public UpdateOrdersWindow(JTable dataTable){
        super("EEA - Update Order");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(420, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        SetComboBoxes();
        SetCurrentData(dataTable);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpinnerNumberModel spinnerModel = (SpinnerNumberModel) quantitySpinner.getModel();
                Number spinnerValue = (Number) spinnerModel.getValue();
                if(spinnerValue.intValue() > 0) {
                    String selectedInstallation = installationComboBox.getSelectedItem().toString();
                    String installationID = selectedInstallation.split(":")[1].trim();
                    String selectedProduct = productComboBox.getSelectedItem().toString();
                    String productID = selectedProduct.split("\\.")[0].trim();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UPDATE `electroacoustics_db`.`Orders` SET " +
                            "`InstallationID`='" + installationID + "', " +
                            "`ProductID`='" + productID + "', " +
                            "`Quantity`='" + spinnerValue.intValue() + "' " +
                            " WHERE `InstallationID`=" + installationID +
                            " AND `ProductID`= " + productID
                    );
                    ConnectDB.updateTable(stringBuilder.toString());

                    JOptionPane.showMessageDialog(null, "Order updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all required fields");
                }
            }
        });
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OrdersWindow();
            }
        });
    }
    private void SetComboBoxes(){
        try {
            ResultSet resultSet = ConnectDB.executeSelectQuery("SELECT InstallationID FROM electroacoustics_db.Installations");
            while (resultSet.next()) {
                int installationID = resultSet.getInt("InstallationID");
                installationComboBox.addItem("Installation ID: "+installationID);
            }
            resultSet = ConnectDB.executeSelectQuery("SELECT ProductID, Name FROM electroacoustics_db.Products");
            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String name = resultSet.getString("Name");
                productComboBox.addItem(productID + ". " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SetCurrentData(JTable dataTable) {
        try {
            int selectedRow = dataTable.getSelectedRow();
            int installationID = (int) dataTable.getValueAt(selectedRow, 0);
            installationComboBox.setSelectedItem("Installation ID: "+installationID);
            ResultSet resultSet = ConnectDB.executeSelectQuery("SELECT ProductID, Name FROM electroacoustics_db.Products WHERE ProductID = "+dataTable.getValueAt(selectedRow, 1).toString());
            while (resultSet.next()) {
                int productID = resultSet.getInt("ProductID");
                String name = resultSet.getString("Name");
                productComboBox.setSelectedItem(productID + ". " + name);
            }
            quantitySpinner.setValue(dataTable.getValueAt(selectedRow, 2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private JComboBox installationComboBox;
    private JComboBox productComboBox;
    private JSpinner quantitySpinner;
    private JButton updateButton;
    private JButton goBackButton;
    private JPanel mainPanel;
}
