package CustomersWindows;
import DatabaseUse.ConnectDB;
import MenuWindow.MenuWindow;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomersWindow extends JFrame{

    private JTable dataTable;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton goBackButton;

    public CustomersWindow(){
        super("EEA - Customers");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(600, 300);
        setLocationRelativeTo(null);

        ConnectDB.FillTable(dataTable, "SELECT * FROM Customers");

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
                ConnectDB.deleteSelectedRow(dataTable,"DELETE FROM Customers WHERE CustomerID = ?");
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
                    new UpdateCustomersWindow(dataTable);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddCustomersWindow();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new CustomersWindow();
    }
}
