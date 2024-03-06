package EmployeesWindows;
import DatabaseUse.ConnectDB;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.regex.Pattern;

public class AddEmployeesWindow extends JFrame {
    public AddEmployeesWindow(){
        super("EEA - Add employee");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(350, 270);
        setLocationRelativeTo(null);
        setVisible(true);
        idFormattedTextField.setForeground(Color.LIGHT_GRAY);
        SetFieldsMasks();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameFormattedTextField.getText().isEmpty() && !phoneNumFormattedTextField.getText().isEmpty() && !emailFormattedTextField.getText().isEmpty()){
                    if(CheckEmial(emailFormattedTextField.getText())){
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("INSERT INTO `electroacoustics_db`.`Employees` (`Name`, `PhoneNum`, `Email`) VALUES ('"+
                                nameFormattedTextField.getText()+"', '"+
                                phoneNumFormattedTextField.getText()+"', '"+
                                emailFormattedTextField.getText()+"')"
                        );
                        ConnectDB.insertIntoTable(stringBuilder.toString());
                        emailLabel.setForeground(Color.black);
                        JOptionPane.showMessageDialog(null, "Employee added successfully");
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
                new EmployeesWindow();
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
    public static void main(String[] args) {
        new AddEmployeesWindow();
    }
    private JPanel mainPanel;
    private JButton goBackButton;
    private JButton addButton;
    private JFormattedTextField idFormattedTextField;
    private JFormattedTextField emailFormattedTextField;
    private JFormattedTextField phoneNumFormattedTextField;
    private JFormattedTextField nameFormattedTextField;
    private JLabel idLabel;
    private JLabel emailLabel;
    private JLabel phoneNumLabel;
    private JLabel nameLabel;
}
