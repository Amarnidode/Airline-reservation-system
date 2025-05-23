package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.*;

public class Passenger extends JFrame {

    // JDBC connection details (replace with your database credentials)
    private static final String DB_URL = "jdbc:mysql:///airlinemanagementsystem"; // Database URL
    private static final String USER = "root";  // Database username
    private static final String PASSWORD = "root";  // Database password

    public Passenger() {
        // Set the frame properties
        setTitle("Passenger Details");
        setSize(1000, 600); 
        setLocationRelativeTo(null); // Center the frame

        // Create the table model
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        // Set background color for the table
        table.setBackground(new Color(173, 216, 230)); // Light blue and yellow blend
        table.setForeground(Color.BLACK);

        // Customize table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.BLUE);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Make the table scrollable
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch column names and data from the database
        fetchColumnNamesAndData(model);

        // Set the frame visibility
        setVisible(true);
    }

    private void fetchColumnNamesAndData(DefaultTableModel model) {
        try {
            // Establish a database connection
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

            // SQL query to fetch all column names and data
            String query = "SELECT * FROM booking_Details";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Get column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Get row data
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = rs.getObject(i);
                }
                model.addRow(rowData);
            }

            // Close the connection
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Passenger();
    }
}
