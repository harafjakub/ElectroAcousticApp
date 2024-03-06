package LoginWindow;

import DatabaseUse.ConnectDB;
import MenuWindow.MenuWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class LoginWindow extends JFrame {
    private JPanel mainPanel;
    private JTextField textFieldUsername;
    private JPasswordField passwordField;
    private JButton logInButton;
    private JLabel logInLabel;
    private JLabel wrongDataLabel;
    public LoginWindow() {
        super("EEA - Log in");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(350, 300);
        setLocationRelativeTo(null);

        // Set component colors
        textFieldUsername.setForeground(Color.LIGHT_GRAY);
        passwordField.setForeground(Color.LIGHT_GRAY);
        logInLabel.setForeground(Color.gray);
        wrongDataLabel.setForeground(Color.red);

        // Set password field echo character to blank by default
        passwordField.setEchoChar((char)0);

        // Add focus listeners to text fields
        textFieldUsername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(textFieldUsername.getText().equals("Username")){
                    textFieldUsername.setText("");
                    textFieldUsername.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(textFieldUsername.getText().equals("")){
                    textFieldUsername.setText("Username");
                    textFieldUsername.setForeground(Color.LIGHT_GRAY);
                }
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(passwordField.getText().equals("Password")){
                    passwordField.setText("");
                    passwordField.setForeground(Color.black);
                    passwordField.setEchoChar('•');
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if(passwordField.getText().equals("")){
                    passwordField.setText("Password");
                    passwordField.setForeground(Color.LIGHT_GRAY);
                    passwordField.setEchoChar((char)0);
                }
            }
        });

        // Add action listener to login button
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword());
                String username = textFieldUsername.getText();
                try {
                    if (ConnectDB.checkUser(username, password)) {
                        JOptionPane.showMessageDialog(null, "Successful log in");
                        dispose();
                        new MenuWindow();
                    } else {
                        wrongDataLabel.setVisible(true);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Set focus on login button
        logInButton.requestFocusInWindow();

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginWindow();
    }
}
