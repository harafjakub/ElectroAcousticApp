package CustomersWindows;
import DatabaseUse.ConnectDB;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.regex.Pattern;

public class UpdateCustomersWindow extends JFrame {
    public UpdateCustomersWindow(JTable dataTable){
        super("EEA - Update customer");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        SetFieldsMasks();
        SetCurrentData(dataTable);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameFormattedTextField.getText().isEmpty() && !addressFormattedTextField.getText().isEmpty() && !phoneNumFormattedTextField.getText().isEmpty() && !emailFormattedTextField.getText().isEmpty()){
                    if(CheckEmial(emailFormattedTextField.getText())){
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("UPDATE `electroacoustics_db`.`Customers` SET " +
                                "`Name`='" + nameFormattedTextField.getText() + "', " +
                                "`Address`='" + addressFormattedTextField.getText() + "', " +
                                "`PhoneNum`='" + phoneNumFormattedTextField.getText() + "', " +
                                "`Email`='" + emailFormattedTextField.getText() +
                                "' WHERE `CustomerID`=" + idFormattedTextField.getText());
                        ConnectDB.updateTable(stringBuilder.toString());
                        emailLabel.setForeground(Color.black);
                        JOptionPane.showMessageDialog(null, "Customer updated successfully");
                    }
                    else{
                        emailLabel.setForeground(Color.RED);
                    }
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
                new CustomersWindow();
            }
        });
    }
    private void SetFieldsMasks(){
        try{
            MaskFormatter formatter = new MaskFormatter("###############");
            formatter.install(phoneNumFormattedTextField);
            phoneNumFormattedTextField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    phoneNumFormattedTextField.setSelectionStart(0);
                    phoneNumFormattedTextField.setSelectionEnd(0);
                }
            });
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
    private boolean CheckEmial(String emialAddress){
        Pattern pattern = Pattern.compile("^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        return pattern.matcher(emialAddress).matches();
    }
    private void SetCurrentData(JTable dataTable){
        int selectedRow = dataTable.getSelectedRow();
        idFormattedTextField.setText(dataTable.getValueAt(selectedRow, 0).toString());
        nameFormattedTextField.setText(dataTable.getValueAt(selectedRow, 1).toString());
        addressFormattedTextField.setText(dataTable.getValueAt(selectedRow, 2).toString());
        phoneNumFormattedTextField.setValue(dataTable.getValueAt(selectedRow, 3));
        emailFormattedTextField.setText(dataTable.getValueAt(selectedRow, 4).toString());
    }
    private JPanel mainPanel;
    private JButton goBackButton;
    private JButton updateButton;
    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField emailFormattedTextField;
    private JFormattedTextField phoneNumFormattedTextField;
    private JFormattedTextField nameFormattedTextField;
    private JFormattedTextField addressFormattedTextField;
    private JLabel idLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;
    private JLabel phoneNumLabel;
    private JLabel nameLabel;
}
