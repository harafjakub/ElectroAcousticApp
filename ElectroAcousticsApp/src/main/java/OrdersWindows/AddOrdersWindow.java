package OrdersWindows;

import DatabaseUse.ConnectDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddOrdersWindow extends JFrame {
    public AddOrdersWindow(){
        super("EEA - Add Order");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(420, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        SetComboBoxes();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SpinnerNumberModel spinnerModel = (SpinnerNumberModel) quantitySpinner.getModel();
                Number spinnerValue = (Number) spinnerModel.getValue();
                if(spinnerValue.intValue() > 0){
                    String selectedInstallation = installationComboBox.getSelectedItem().toString();
                    String installationID = selectedInstallation.split(":")[1].trim();
                    String selectedProduct = productComboBox.getSelectedItem().toString();
                    String productID = selectedProduct.split("\\.")[0].trim();

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("INSERT INTO `electroacoustics_db`.`Orders` (`InstallationID`, `ProductID`, `Quantity`) VALUES ('"+
                            installationID+"', '"+
                            productID+"', '"+
                            spinnerValue.intValue()+"')"
                    );
                    ConnectDB.insertIntoTable(stringBuilder.toString());
                    JOptionPane.showMessageDialog(null, "Order added successfully");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Quantity must be greater than zero");
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

    public static void main(String[] args) {
        new AddOrdersWindow();
    }
    private JPanel mainPanel;
    private JButton goBackButton;
    private JButton addButton;
    private JComboBox installationComboBox;
    private JComboBox productComboBox;
    private JSpinner quantitySpinner;
}
