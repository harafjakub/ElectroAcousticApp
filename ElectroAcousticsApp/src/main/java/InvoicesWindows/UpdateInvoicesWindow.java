package InvoicesWindows;

import DatabaseUse.ConnectDB;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateInvoicesWindow extends JFrame{
    private JFormattedTextField idFormattedTextField;
    private JComboBox installationComboBox;
    private DatePicker datePicker;
    private JFormattedTextField amountFormattedTextField;
    private JComboBox statusComboBox;
    private JFormattedTextField descriptionFormattedTextField;
    private JButton updateButton;
    private JButton goBackButton;
    private JPanel mainPanel;

    public UpdateInvoicesWindow(JTable dataTable){

        super("EEA - Update Invoice");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(420, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        amountFormattedTextField.setForeground(Color.LIGHT_GRAY);
        SetComboBoxes();
        SetCurrentData(dataTable);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (datePicker.getDate() != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UPDATE `electroacoustics_db`.`Invoices` SET " +
                            "`Date`='" + datePicker.getDate() + "', " +
                            "`Description`=" + (!descriptionFormattedTextField.getText().isEmpty() ? "'" + descriptionFormattedTextField.getText() + "'" : "NULL") + ", " +
                            "`Status`='" + statusComboBox.getSelectedItem().toString() + "' " +
                            "WHERE `InvoiceID`=" + idFormattedTextField.getText()
                    );
                    ConnectDB.updateTable(stringBuilder.toString());
                    JOptionPane.showMessageDialog(null, "Invoice updated successfully");
                } else {
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
    private void SetCurrentData(JTable dataTable){
        int selectedRow = dataTable.getSelectedRow();
        idFormattedTextField.setText(dataTable.getValueAt(selectedRow, 0).toString());
        installationComboBox.setSelectedItem("Installation ID: "+dataTable.getValueAt(selectedRow, 1));
        java.sql.Date startDateValue = (java.sql.Date) dataTable.getValueAt(selectedRow, 2);
        datePicker.setDate(startDateValue.toLocalDate());
        amountFormattedTextField.setText(dataTable.getValueAt(selectedRow, 3).toString());
        descriptionFormattedTextField.setText(dataTable.getValueAt(selectedRow, 4) != null ? dataTable.getValueAt(selectedRow, 4).toString() : "");
        statusComboBox.setSelectedItem(dataTable.getValueAt(selectedRow, 5));
    }
}
