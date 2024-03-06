package OrdersWindows;

import DatabaseUse.ConnectDB;
import MenuWindow.MenuWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdersWindow extends JFrame{
    private JTable dataTable;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton goBackButton;

    public OrdersWindow(){
        super("EEA - Orders");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(600, 300);
        setLocationRelativeTo(null);

        ConnectDB.FillTable(dataTable, "SELECT * FROM Orders");

        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MenuWindow();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectDB.deleteRowByTwoParams(dataTable, "DELETE FROM Orders WHERE InstallationID = ? AND ProductID = ?", "InstallationID", "ProductID");
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dataTable.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(null, "Please select row to modify");
                }
                else{
                    dispose();
                    new UpdateOrdersWindow(dataTable);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddOrdersWindow();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new OrdersWindow();
    }
}
