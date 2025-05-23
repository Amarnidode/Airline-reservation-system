//package airlinemanagementsystem;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.*;
//import net.proteanit.sql.DbUtils;
//
//public class FlightInfo extends JFrame {
//    
//    // Constructor to set up the JFrame and UI components
//    public FlightInfo() {
//        
//        getContentPane().setBackground(Color.WHITE);
//        setLayout(null);
//        
//        // JTable to display flight data
//        JTable table = new JTable();
//        
//        try {
//            Conn conn = new Conn();
//            
//            // Fetch existing flight data from the database
//            ResultSet rs = conn.s.executeQuery("SELECT * FROM flight");
//            table.setModel(DbUtils.resultSetToTableModel(rs));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        // ScrollPane for the JTable
//        JScrollPane jsp = new JScrollPane(table);
//        jsp.setBounds(0, 0, 800, 300);  // Adjusted to give space for form
//        add(jsp);
//
//        // Panel for adding new flight
//        JPanel panel = new JPanel();
//        panel.setBounds(0, 310, 800, 150);  // Positioned below the table
//        panel.setLayout(new GridLayout(5, 2, 10, 10));  // GridLayout for form inputs
//        add(panel);
//
//        // Form inputs for flight details
//        JLabel lblFlightName = new JLabel("Flight Name:");
//        JTextField tfFlightName = new JTextField();
//        JLabel lblFlightCode = new JLabel("Flight Code:");
//        JTextField tfFlightCode = new JTextField();
//        JLabel lblSource = new JLabel("Source:");
//        JTextField tfSource = new JTextField();
//        JLabel lblDestination = new JLabel("Destination:");
//        JTextField tfDestination = new JTextField();
//        JLabel lblCapacity = new JLabel("Flight_time:");
//        JTextField tfFlight_time = new JTextField();
//
//        // Add the components to the panel
//        panel.add(lblFlightName);
//        panel.add(tfFlightName);
//        panel.add(lblFlightCode);
//        panel.add(tfFlightCode);
//        panel.add(lblSource);
//        panel.add(tfSource);
//        panel.add(lblDestination);
//        panel.add(tfDestination);
//        panel.add(lblCapacity);
//        panel.add(tfFlight_time);
//
//        // Button to add new flight
//        JButton btnAddFlight = new JButton("Add Flight");
//        btnAddFlight.setBounds(350, 470, 100, 30);  // Positioned below the form
//        add(btnAddFlight);
//
//        // Action listener for the Add Flight button
//        btnAddFlight.addActionListener(e -> {
//    // Get values from the input fields
//    String flightName = tfFlightName.getText();
//    String flightCode = tfFlightCode.getText();
//    String source = tfSource.getText();
//    String destination = tfDestination.getText();
//    String Flight_time = tfFlight_time.getText();
//
//    try {
//        // Create a new connection to the database
//        Conn conn2 = new Conn();
//
//        // Prepare the SQL query to insert the new flight
//        String query = "INSERT INTO flight (f_name, f_code, source, destination, Flight_time) VALUES (?, ?, ?, ?, ?)";
//        PreparedStatement pst = conn2.c.prepareStatement(query);
//
//        // Set the values for the prepared statement
//        pst.setString(1, flightName);   // Flight Name
//        pst.setString(2, flightCode);   // Flight Code
//        pst.setString(3, source);       // Source
//        pst.setString(4, destination);  // Destination
//        pst.setString(5, Flight_time);     // Capacity
//
//        // Execute the query
//        pst.executeUpdate();  // This will add the flight to the database
//
//        // Show a success message
//        JOptionPane.showMessageDialog(null, "Flight added successfully!");
//
//        // Refresh the table data to reflect the newly added flight
//        ResultSet rs = conn2.s.executeQuery("SELECT * FROM flight");
//        table.setModel(DbUtils.resultSetToTableModel(rs));
//
//        // Clear the input fields after adding
//        tfFlightName.setText("");
//        tfFlightCode.setText("");
//        tfSource.setText("");
//        tfDestination.setText("");
//        tfFlight_time.setText("");
//    } catch (Exception ex) {
//        ex.printStackTrace();  // Handle exceptions if any
//    }
//});
//
//       
//        
//        // Set JFrame properties
//        setSize(800, 600);  // Adjusted for form space
//        setLocation(400, 200);
//        setVisible(true);
//    }
//
//    // Main method to run the application
//    public static void main(String[] args) {
//        new FlightInfo();
//    }
//}




package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class FlightInfo extends JFrame {

    public FlightInfo() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JTable table = new JTable();

        try {
            Conn conn = new Conn();

            // Fetch existing flight data from the database
            ResultSet rs = conn.s.executeQuery("SELECT * FROM flight");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 0, 800, 300); 
        add(jsp);

        JPanel panel = new JPanel();
        panel.setBounds(0, 310, 800, 200); 
        panel.setLayout(new GridLayout(6, 2, 10, 10)); 
        add(panel);

        JLabel lblFlightName = new JLabel("Flight Name:");
        JTextField tfFlightName = new JTextField();
        JLabel lblFlightCode = new JLabel("Flight Code:");
        JTextField tfFlightCode = new JTextField();
        JLabel lblSource = new JLabel("Source:");
        JTextField tfSource = new JTextField();
        JLabel lblDestination = new JLabel("Destination:");
        JTextField tfDestination = new JTextField();
        JLabel lblFlightTime = new JLabel("Flight Time:");
        JTextField tfFlightTime = new JTextField();
        JLabel lblAmount = new JLabel("Amount:");
        JTextField tfAmount = new JTextField();

        panel.add(lblFlightName);
        panel.add(tfFlightName);
        panel.add(lblFlightCode);
        panel.add(tfFlightCode);
        panel.add(lblSource);
        panel.add(tfSource);
        panel.add(lblDestination);
        panel.add(tfDestination);
        panel.add(lblFlightTime);
        panel.add(tfFlightTime);
        panel.add(lblAmount);
        panel.add(tfAmount);

        JButton btnAddFlight = new JButton("Add Flight");
        btnAddFlight.setBounds(350, 520, 100, 30); 
        add(btnAddFlight);

        btnAddFlight.addActionListener(e -> {
            String flightName = tfFlightName.getText();
            String flightCode = tfFlightCode.getText();
            String source = tfSource.getText();
            String destination = tfDestination.getText();
            String flightTime = tfFlightTime.getText();
            String amount = tfAmount.getText();

            try {
                Conn conn2 = new Conn();

                String query = "INSERT INTO flight (f_name, f_code, source, destination, Flight_time, amount) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn2.c.prepareStatement(query);

                pst.setString(1, flightName);
                pst.setString(2, flightCode);
                pst.setString(3, source);
                pst.setString(4, destination);
                pst.setString(5, flightTime);
                pst.setDouble(6, Double.parseDouble(amount)); 

                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Flight added successfully!");

                ResultSet rs = conn2.s.executeQuery("SELECT * FROM flight");
                table.setModel(DbUtils.resultSetToTableModel(rs));

                tfFlightName.setText("");
                tfFlightCode.setText("");
                tfSource.setText("");
                tfDestination.setText("");
                tfFlightTime.setText("");
                tfAmount.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        setSize(800, 600);
        setLocation(400, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
