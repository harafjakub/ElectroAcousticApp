package ProductsWindows;

import DatabaseUse.ConnectDB;
import MenuWindow.MenuWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductsWindow extends JFrame{
    private JTable dataTable;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton goBackButton;

    public ProductsWindow(){
        super("EEA - Products");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(600, 300);
        setLocationRelativeTo(null);

        ConnectDB.FillTable(dataTable, "SELECT * FROM Products");

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
                ConnectDB.deleteSelectedRow(dataTable,"DELETE FROM Products WHERE ProductID = ?");
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
                    new UpdateProductsWindow(dataTable);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddProductsWindow();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ProductsWindow();
    }
}
