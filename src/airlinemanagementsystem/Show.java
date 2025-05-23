package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Show extends JFrame {

    private JTextField pnrTextField;
    private JButton submitButton;

    public Show() {
        setTitle("Enter PNR to View Booking Details");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Label to prompt the user
        JLabel lblPrompt = new JLabel("Enter PNR:");

        // Text field for PNR input
        pnrTextField = new JTextField(10);

        // Submit button
        submitButton = new JButton("Submit");

        // Action listener for the Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pnr = pnrTextField.getText().trim();
                // Show booking details for the entered PNR
                String bookingDetails = getBookingDetails(pnr);
                displayBookingDetails(pnr, bookingDetails);
            }
        });

        // Add components to the frame
        add(lblPrompt);
        add(pnrTextField);
        add(submitButton);

        setVisible(true);
    }

    // Method to fetch booking details from the database
    // Method to fetch booking details from the database
// Method to fetch booking details from the database
private String getBookingDetails(String pnr) {
    String bookingDetails = "No booking details found for PNR: " + pnr;
    
    // Database URL and credentials (update with your own details)
    String url = "jdbc:mysql:///airlinemanagementsystem"; // Database URL
    String username = "root"; // Database username
    String password = "root"; // Database password
    
    // SQL query to fetch booking details for the given PNR
    String query = "SELECT customer_name, journey_details, total_cost FROM bookings WHERE pnr = ?";
    
    try (Connection conn = DriverManager.getConnection(url, username, password);
         PreparedStatement stmt = conn.prepareStatement(query)) {
         
        stmt.setString(1, pnr); // Set the PNR parameter in the query
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            // Extract the booking details from the result set
            String customerName = rs.getString("customer_name");
            String journeyDetails = rs.getString("journey_details");
            double totalCost = rs.getDouble("total_cost");
            
            bookingDetails = String.format("Customer Name: %s\nJourney Details: %s\nTotal Cost: %.2f", 
                                           customerName, journeyDetails, totalCost);
        }
    } catch (SQLException e) {
        bookingDetails = "Error fetching booking details: " + e.getMessage();
    }

    return bookingDetails;
}



    // Method to display booking details
    // Method to display booking details
private void displayBookingDetails(String pnr, String bookingDetails) {
    // Hide the current components
    getContentPane().removeAll();

    // Show booking details in a label
    JLabel lblMessage = new JLabel("Booking Details for PNR: " + pnr);
    JLabel lblDetails = new JLabel("<html>" + bookingDetails.replace("\n", "<br>") + "</html>");

    // Add new labels to the frame
    add(lblMessage);
    add(lblDetails);

    // Refresh the frame to display updated content
    revalidate();
    repaint();
}

    public static void main(String[] args) {
        // For testing purposes
        new Show();  // No need to pass PNR, user will input it
    }
}
