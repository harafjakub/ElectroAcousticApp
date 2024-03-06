package LocationsWindows;

import DatabaseUse.ConnectDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddLocationsWindow extends JFrame{
    String[] locationTypes = { "Kościół", "Hala sportowa" };
    public AddLocationsWindow(){
        super("EEA - Add location");
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


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameFormattedTextField.getText().isEmpty() && !addressFormattedTextField.getText().isEmpty()){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("INSERT INTO `electroacoustics_db`.`Locations` (`Name`, `Type`, `Address`) VALUES ('"+
                            nameFormattedTextField.getText()+"', '"+
                            typeComboBox.getSelectedItem().toString()+"', '"+
                            addressFormattedTextField.getText()+"')"
                    );
                    ConnectDB.insertIntoTable(stringBuilder.toString());
                    JOptionPane.showMessageDialog(null, "Location added successfully");
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

    public static void main(String[] args) {
        new AddLocationsWindow();
    }
    private JLabel idLabel;
    private JLabel addressLabel;
    private JLabel typeLabel;
    private JLabel nameLabel;
    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField addressFormattedTextField;
    private JFormattedTextField typeFormattedTextField;
    private JFormattedTextField nameFormattedTextField;
    private JButton addButton;
    private JButton goBackButton;
    private JPanel mainPanel;
    private JComboBox typeComboBox;
}
