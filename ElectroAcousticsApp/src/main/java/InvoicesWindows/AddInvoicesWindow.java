package InvoicesWindows;

import DatabaseUse.ConnectDB;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import static DatabaseUse.ConnectDB.executeSelectQuery;

public class AddInvoicesWindow extends JFrame {
    public AddInvoicesWindow(){
        super("EEA - Add Invoice");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(420, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        amountFormattedTextField.setForeground(Color.LIGHT_GRAY);
        SetComboBoxes();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(datePicker.getDate() != null){

                    String selectedInstallation = installationComboBox.getSelectedItem().toString();
                    String installationID = selectedInstallation.split(":")[1].trim();
                    ResultSet resultSet = executeSelectQuery("SELECT SUM(electroacoustics_db.Orders.Quantity * electroacoustics_db.Products.Price) AS SumAmount FROM electroacoustics_db.Installations INNER JOIN electroacoustics_db.Orders ON electroacoustics_db.Orders.InstallationID = electroacoustics_db.Installations.InstallationID INNER JOIN electroacoustics_db.Products ON electroacoustics_db.Products.ProductID = electroacoustics_db.Orders.ProductID WHERE electroacoustics_db.Installations.InstallationID = "+installationID);

                    StringBuilder stringBuilder = new StringBuilder();
                    try {
                        if (resultSet.next()) {
                            stringBuilder.append("INSERT INTO `electroacoustics_db`.`Invoices` (`InstallationID`, `Date`, `Amount`, `Description`, `Status`) VALUES ('"+
                                    installationID+"', '"+
                                    datePicker.getDate()+"', '"+
                                    resultSet.getFloat("SumAmount")+"',"+
                                    (!descriptionFormattedTextField.getText().isEmpty() ? "'" + descriptionFormattedTextField.getText() + "'" : "NULL") + ", '"+
                                    statusComboBox.getSelectedItem().toString()+ "')"
                            );
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    ConnectDB.insertIntoTable(stringBuilder.toString());
                    JOptionPane.showMessageDialog(null, "Invoice added successfully");
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
                new InvoicesWindow();
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
            String[] statusTypes = { "Opłacono", "Nie opłacono" };
            for (String type: statusTypes){
                statusComboBox.addItem(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AddInvoicesWindow();
    }

    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField descriptionFormattedTextField;
    private JComboBox installationComboBox;
    private DatePicker datePicker;
    private JButton addButton;
    private JButton goBackButton;
    private JPanel mainPanel;
    private JComboBox statusComboBox;
    private JFormattedTextField amountFormattedTextField;
}
