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

public class AddProductsWindow extends JFrame {
    String[] productTypes = {"Mikrofon","Głośnik","Wzmacniacz","Kolumna"};
    public AddProductsWindow(){
        super("EEA - Add products");
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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameFormattedTextField.getText().isEmpty() && !manufactureFormattedTextField.getText().isEmpty() && !modelFormattedTextField.getText().isEmpty() && !quantityFormattedTextField.getText().isEmpty()){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("INSERT INTO `electroacoustics_db`.`Products` (`Name`, `Type`, `Manufacture`, `Model`, `Price`, `Quantity`) VALUES ('" +
                            nameFormattedTextField.getText()+"', '"+
                            typeComboBox.getSelectedItem().toString()+"', '"+
                            manufactureFormattedTextField.getText()+"', '"+
                            modelFormattedTextField.getText()+"', '"+
                            priceFormattedTextField.getText()+"', '"+
                            quantityFormattedTextField.getText().replace(",", "")+"')"
                    );
                    ConnectDB.insertIntoTable(stringBuilder.toString());
                    JOptionPane.showMessageDialog(null, "Product added successfully");
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

    public static void main(String[] args) {
        new AddProductsWindow();
    }
    private JButton addButton;
    private JButton goBackButton;
    private JLabel idLabel;
    private JLabel priceLabel;
    private JLabel typeLabel;
    private JLabel modelLabel;
    private JLabel nameLabel;
    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField quantityFormattedTextField;
    private JFormattedTextField modelFormattedTextField;
    private JFormattedTextField nameFormattedTextField;
    private JFormattedTextField manufactureFormattedTextField;
    private JPanel mainPanel;
    private JComboBox typeComboBox;
    private JFormattedTextField priceFormattedTextField;
    private JLabel manufactureLabel;
    private JLabel quantityLabel;
}
