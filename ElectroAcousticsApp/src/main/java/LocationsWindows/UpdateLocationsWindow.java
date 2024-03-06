package LocationsWindows;

import DatabaseUse.ConnectDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateLocationsWindow extends JFrame{
    String[] locationTypes = { "Kościół", "Hala sportowa" };
    public UpdateLocationsWindow(JTable dataTable){
        super("EEA - Update location");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(350, 270);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        for (String type: locationTypes){
            typeComboBox.addItem(type);
        }
        SetCurrentData(dataTable);


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameFormattedTextField.getText().isEmpty() && !addressFormattedTextField.getText().isEmpty()){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UPDATE `electroacoustics_db`.`Locations` SET " +
                            "`Name`='" + nameFormattedTextField.getText() + "', " +
                            "`Type`='" + typeComboBox.getSelectedItem().toString() + "', " +
                            "`Address`='" + addressFormattedTextField.getText() +
                            "' WHERE `LocationID`=" + idFormattedTextField.getText());
                    ConnectDB.updateTable(stringBuilder.toString());
                    JOptionPane.showMessageDialog(null, "Location updated successfully");
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
                new LocationsWindow();
            }
        });
    }
    private void SetCurrentData(JTable dataTable){
        int selectedRow = dataTable.getSelectedRow();
        idFormattedTextField.setText(dataTable.getValueAt(selectedRow, 0).toString());
        nameFormattedTextField.setText(dataTable.getValueAt(selectedRow, 1).toString());
        typeComboBox.setSelectedItem(dataTable.getValueAt(selectedRow, 2));
        addressFormattedTextField.setText(dataTable.getValueAt(selectedRow, 3).toString());
    }
    private JLabel idLabel;
    private JLabel addressLabel;
    private JLabel typeLabel;
    private JLabel nameLabel;
    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField addressFormattedTextField;
    private JFormattedTextField nameFormattedTextField;
    private JComboBox typeComboBox;
    private JButton updateButton;
    private JButton goBackButton;
    private JPanel mainPanel;
}
