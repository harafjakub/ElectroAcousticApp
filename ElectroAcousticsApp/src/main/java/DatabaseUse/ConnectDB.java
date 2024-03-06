package DatabaseUse;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.*;

public class ConnectDB {
    public static String databaseName = "electroacoustics_db";
    public static String url = "jdbc:mysql://localhost:3306/" + databaseName;
    public static String username = "admin";
    public static String password = "admin";

    // checks if the entered data is in the database
    public static boolean checkUser(String userUsername, String userPassword) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(ConnectDB.url, ConnectDB.username, ConnectDB.password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");
        preparedStatement.setString(1, userUsername);
        preparedStatement.setString(2, userPassword);

        ResultSet resultSet = preparedStatement.executeQuery();
        boolean result;
        if (resultSet.next()) {
            result = true;
        } else {
            result = false;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return result;
    }

    // fill table with select query from database
    public static void FillTable(JTable jTable, String selectQuery){
        DefaultTableModel tableModel = new DefaultTableModel();
        jTable.setModel(tableModel);

        // Execute SELECT query and add results to table model
        try {
            Connection connection = DriverManager.getConnection(ConnectDB.url, ConnectDB.username, ConnectDB.password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            // Get column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            // Get row data
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //jTable.getColumnModel().getColumn(0).setHeaderValue("ID");

        // Adjusting the width of the columns in the table to the data
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel columnModel = jTable.getColumnModel();
        for (int column = 0; column < jTable.getColumnCount(); column++) {
            int width = 15;
            TableColumn tableColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = tableColumn.getHeaderRenderer();
            if (renderer == null) {
                renderer = jTable.getTableHeader().getDefaultRenderer();
            }
            Component headerComp = renderer.getTableCellRendererComponent(jTable, tableColumn.getHeaderValue(), false, false, 0, 0);
            width = Math.max(headerComp.getPreferredSize().width, width);
            for (int row = 0; row < jTable.getRowCount(); row++) {
                renderer = jTable.getCellRenderer(row, column);
                Component comp = jTable.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            tableColumn.setPreferredWidth(width + 5);
            tableColumn.setMinWidth(width + 5);
            tableColumn.setMaxWidth(width + 5);
        }
    }
    public static void deleteSelectedRow(JTable jTable, String deleteQuery) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        int selectedRow = jTable.getSelectedRow();

        // Check if row is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(jTable, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get value of primary key column
        Object id = tableModel.getValueAt(selectedRow, 0);

        // Execute DELETE query
        try {
            Connection connection = DriverManager.getConnection(ConnectDB.url, ConnectDB.username, ConnectDB.password);
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setObject(1, id);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jTable, "Error deleting row.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Remove row from table model
        tableModel.removeRow(selectedRow);
    }
    public static void deleteRowByTwoParams(JTable jTable, String deleteQuery, String firstColumnName, String secondColumnName) {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        int selectedRow = jTable.getSelectedRow();

        // Check if row is selected
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(jTable, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get value of firstColumnName and secondColumnName columns from dataTable
        Object firstColumnValue = tableModel.getValueAt(selectedRow, tableModel.findColumn(firstColumnName));
        Object secondColumnValue = tableModel.getValueAt(selectedRow, tableModel.findColumn(secondColumnName));

        // Execute DELETE query
        try {
            Connection connection = DriverManager.getConnection(ConnectDB.url, ConnectDB.username, ConnectDB.password);
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setObject(1, firstColumnValue);
            statement.setObject(2, secondColumnValue);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(jTable, "Error deleting row.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Remove row from table model
        tableModel.removeRow(selectedRow);
    }


    public static void insertIntoTable(String insertQuery) {
        try {
            Connection connection = DriverManager.getConnection(ConnectDB.url, ConnectDB.username, ConnectDB.password);
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateTable(String updateQuery) {
        try {
            Connection connection = DriverManager.getConnection(ConnectDB.url, ConnectDB.username, ConnectDB.password);
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ResultSet executeSelectQuery(String selectQuery) {
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(ConnectDB.url, ConnectDB.username, ConnectDB.password);
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}