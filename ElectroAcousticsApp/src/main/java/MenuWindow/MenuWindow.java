package MenuWindow;

import CustomersWindows.CustomersWindow;
import EmployeesWindows.EmployeesWindow;
import InstallationsWindows.InstallationsWindow;
import InvoicesWindows.InvoicesWindow;
import LocationsWindows.LocationsWindow;
import LoginWindow.LoginWindow;
import OrdersWindows.OrdersWindow;
import ProductsWindows.ProductsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {

    private JPanel mainPanel;
    private JButton customersButton;
    private JButton exitButton;
    private JButton locationsButton;
    private JButton logOutButton;
    private JButton employeesButton;
    private JButton productsButton;
    private JButton ordersButton;
    private JButton installationsButton1;
    private JButton invoicesButton;
    private JLabel authorLabel;
    private JLabel menuLabel;

    public MenuWindow() {
        super("EEA - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 470);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);

        // Set component colors
        authorLabel.setForeground(Color.LIGHT_GRAY);
        menuLabel.setForeground(Color.gray);

        customersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomersWindow();
            }
        });
        employeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new EmployeesWindow();
            }
        });

        locationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LocationsWindow();
            }
        });
        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProductsWindow();
            }
        });
        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new OrdersWindow();
            }
        });
        installationsButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InstallationsWindow();
            }
        });
        invoicesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InvoicesWindow();
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginWindow();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuWindow();
    }
}
