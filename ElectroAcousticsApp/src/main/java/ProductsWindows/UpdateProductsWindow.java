package ProductsWindows;

import DatabaseUse.ConnectDB;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class UpdateProductsWindow extends JFrame{
    String[] productTypes = {"Mikrofon","Głośnik","Wzmacniacz","Kolumna"};
    public UpdateProductsWindow(JTable dataTable){
        super("EEA - Update product");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(350, 370);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        SetFieldsFormat();
        for (String type: productTypes){
            typeComboBox.addItem(type);
        }
        SetCurrentData(dataTable);


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameFormattedTextField.getText().isEmpty() && !manufactureFormattedTextField.getText().isEmpty() && !quantityFormattedTextField.getText().isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UPDATE `electroacoustics_db`.`Products` SET " +
                            "`Name`='" + nameFormattedTextField.getText() + "', " +
                            "`Type`='" + typeComboBox.getSelectedItem().toString() + "', " +
                            "`Manufacture`='" + manufactureFormattedTextField.getText() + "', " +
                            "`Model`='" + modelFormattedTextField.getText() + "', " +
                            "`Price`='" + priceFormattedTextField.getText() + "', " +
                            "`Quantity`='" + quantityFormattedTextField.getText().replace(",", "") + "' " +
                            "WHERE `ProductID`=" + idFormattedTextField.getText()
                    );
                    ConnectDB.updateTable(stringBuilder.toString());
                    JOptionPane.showMessageDialog(null, "Product updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill all required fields");
                }
            }
        });

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProductsWindow();
            }
        });
    }
    private void SetCurrentData(JTable dataTable){
        int selectedRow = dataTable.getSelectedRow();
        idFormattedTextField.setText(dataTable.getValueAt(selectedRow, 0).toString());
        nameFormattedTextField.setText(dataTable.getValueAt(selectedRow, 1).toString());
        typeComboBox.setSelectedItem(dataTable.getValueAt(selectedRow, 2));
        manufactureFormattedTextField.setText(dataTable.getValueAt(selectedRow, 3).toString());
        modelFormattedTextField.setText(dataTable.getValueAt(selectedRow, 4) != null ? dataTable.getValueAt(selectedRow, 4).toString() : "");
        priceFormattedTextField.setText(dataTable.getValueAt(selectedRow, 5).toString());
        quantityFormattedTextField.setText(dataTable.getValueAt(selectedRow, 6).toString());
    }
    private void SetFieldsFormat(){
        // Quantity - int
        NumberFormatter formatter1 = new NumberFormatter();
        formatter1.setAllowsInvalid(false);
        formatter1.install(quantityFormattedTextField);

        // Price - double
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#0.00", decimalFormatSymbols);
        decimalFormat.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        formatter.setAllowsInvalid(false);
        priceFormattedTextField.setFormatterFactory(new DefaultFormatterFactory(formatter));
    }

    private JLabel idLabel;
    private JLabel priceLabel;
    private JLabel typeLabel;
    private JLabel modelLabel;
    private JLabel nameLabel;
    private JLabel manufactureLabel;
    private JLabel quantityLabel;
    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField quantityFormattedTextField;
    private JFormattedTextField modelFormattedTextField;
    private JFormattedTextField nameFormattedTextField;
    private JFormattedTextField manufactureFormattedTextField;
    private JComboBox typeComboBox;
    private JFormattedTextField priceFormattedTextField;
    private JButton updateButton;
    private JButton goBackButton;
    private JPanel mainPanel;
}
