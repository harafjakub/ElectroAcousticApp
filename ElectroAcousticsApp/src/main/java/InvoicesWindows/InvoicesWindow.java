package InvoicesWindows;

import DatabaseUse.ConnectDB;
import MenuWindow.MenuWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class InvoicesWindow extends JFrame{
    private JTable dataTable;
    private JPanel mainPanel;
    private JButton addButton;
    private JButton modifyButton;
    private JButton deleteButton;
    private JButton goBackButton;
    private JButton generatePdfButton;

    public InvoicesWindow(){
        super("EEA - Invoices");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        requestFocus();
        setSize(600, 300);
        setLocationRelativeTo(null);

        ConnectDB.FillTable(dataTable, "SELECT * FROM Invoices");

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
                ConnectDB.deleteSelectedRow(dataTable,"DELETE FROM Invoices WHERE InvoiceID = ?");
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
                    new UpdateInvoicesWindow(dataTable);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AddInvoicesWindow();
            }
        });
        generatePdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dataTable.getSelectionModel().isSelectionEmpty()){
                    JOptionPane.showMessageDialog(null, "Please select invoice to generate PDF.");
                }
                else{
                    try {
                        GeneratePdf.Generate(dataTable);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        new InvoicesWindow();
    }
}
