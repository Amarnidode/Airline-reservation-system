//
//package airlinemanagementsystem;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.sql.*;
//import java.util.Random;
//
//public class Payment extends JFrame implements ActionListener {
//    // Labels and text fields to display details
//    JLabel lblAadhar, lblName, lblNationality, lblFlightName, lblFlightCode, lblSource, lblDestination, lblTravelDate, lblPNR, lblAmount;
//    JTextField tfAadhar, tfName, tfNationality, tfFlightName, tfFlightCode, tfSource, tfDestination, tfTravelDate, tfPNR, tfAmount;
//    JButton btnConfirm, btnBack;
//
//    // Constructor to initialize the payment page
//    public Payment(String aadhar, String name, String nationality, String flightName, String flightCode, String source, String destination, String travelDate) {
//        setTitle("Payment - Flight Booking");
//
//        // Set up the layout and window size
//        setLayout(new GridLayout(11, 2, 10, 10));
//        setSize(500, 600);
//        setLocation(400, 100);
//
//        // Initialize labels and text fields
//        lblAadhar = new JLabel("Aadhar: ");
//        lblName = new JLabel("Name: ");
//        lblNationality = new JLabel("Nationality: ");
//        lblFlightName = new JLabel("Flight Name: ");
//        lblFlightCode = new JLabel("Flight Code: ");
//        lblSource = new JLabel("Source: ");
//        lblDestination = new JLabel("Destination: ");
//        lblTravelDate = new JLabel("Travel Date: ");
//        lblPNR = new JLabel("PNR Number: ");
//        lblAmount = new JLabel("Amount (Rs.): "); // Added for amount
//
//        tfAadhar = new JTextField(aadhar);
//        tfAadhar.setEditable(false);
//        tfName = new JTextField(name);
//        tfName.setEditable(false);
//        tfNationality = new JTextField(nationality);
//        tfNationality.setEditable(false);
//        tfFlightName = new JTextField(flightName);
//        tfFlightName.setEditable(false);
//        tfFlightCode = new JTextField(flightCode);
//        tfFlightCode.setEditable(false);
//        tfSource = new JTextField(source);
//        tfSource.setEditable(false);
//        tfDestination = new JTextField(destination);
//        tfDestination.setEditable(false);
//        tfTravelDate = new JTextField(travelDate);
//        tfTravelDate.setEditable(false);
//        tfPNR = new JTextField();
//        tfPNR.setEditable(false);
//        tfAmount = new JTextField(); // For displaying the fetched amount
//        tfAmount.setEditable(false);
//
//        // Buttons for confirming the payment or going back
//        btnConfirm = new JButton("Confirm Payment");
//        btnBack = new JButton("Back");
//
//        // Add action listeners for the buttons
//        btnConfirm.addActionListener(this);
//        btnBack.addActionListener(this);
//
//        // Add the components to the frame
//        add(lblAadhar);
//        add(tfAadhar);
//        add(lblName);
//        add(tfName);
//        add(lblNationality);
//        add(tfNationality);
//        add(lblFlightName);
//        add(tfFlightName);
//        add(lblFlightCode);
//        add(tfFlightCode);
//        add(lblSource);
//        add(tfSource);
//        add(lblDestination);
//        add(tfDestination);
//        add(lblTravelDate);
//        add(tfTravelDate);
//        add(lblPNR);
//        add(tfPNR);
//        add(lblAmount);
//        add(tfAmount);
//        add(btnConfirm);
//        add(btnBack);
//
//        setVisible(true);
//
//        // Generate a random numeric PNR and display it in the text field
//        String pnr = generateRandomPNR();
//        tfPNR.setText(pnr);
//
//        // Fetch the amount for the selected flight based on source and destination
//        fetchAmount(source, destination);
//    }
//
//    // Method to fetch the amount from the flight table based on source and destination
//    private void fetchAmount(String source, String destination) {
//    if (source == null || source.trim().isEmpty() || destination == null || destination.trim().isEmpty()) {
//        JOptionPane.showMessageDialog(this, "Source or destination cannot be empty.");
//        return;
//    }
//
//    try {
//        Conn conn = new Conn();  // Assuming Conn class is established and connected
//        String query = "SELECT amount FROM flight WHERE source = ? AND destination = ?";  // Column name 'amount'
//        PreparedStatement pstmt = conn.c.prepareStatement(query);
//        pstmt.setString(1, source);  // Set the source
//        pstmt.setString(2, destination);  // Set the destination
//        ResultSet rs = pstmt.executeQuery();  // Execute the query
//
//        if (rs.next()) {
//            String amount = rs.getString("amount");  // Fetch the amount from the database
//            tfAmount.setText(amount);  // Display the amount in the text field
//        } else {
//            JOptionPane.showMessageDialog(this, "No flight found for the selected source and destination.");
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(this, "Error fetching flight amount.");
//    }
//}
//
//
//    // Method to generate a random numeric PNR (6 digits)
//    private String generateRandomPNR() {
//        Random random = new Random();
//        int pnrNumber = 100000 + random.nextInt(900000);
//        return String.valueOf(pnrNumber);
//    }
//
//    // Action performed method to handle button clicks
//    public void actionPerformed(ActionEvent ae) {
//        if (ae.getSource() == btnConfirm) {
//            String pnr = tfPNR.getText();
//
//            // Validate that amount is not empty before confirming payment
//            if (tfAmount.getText().trim().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "Please select a valid flight (source and destination).");
//                return;
//            }
//
//            try {
//                Conn conn = new Conn();
//                String query = "INSERT INTO booking_details (aadhar, name, nationality, flight_name, flight_code, source, destination, travel_date, pnr, amount) "
//                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//                PreparedStatement pstmt = conn.c.prepareStatement(query);
//                pstmt.setString(1, tfAadhar.getText());
//                pstmt.setString(2, tfName.getText());
//                pstmt.setString(3, tfNationality.getText());
//                pstmt.setString(4, tfFlightName.getText());
//                pstmt.setString(5, tfFlightCode.getText());
//                pstmt.setString(6, tfSource.getText());
//                pstmt.setString(7, tfDestination.getText());
//                pstmt.setString(8, tfTravelDate.getText());
//                pstmt.setString(9, pnr);
//                pstmt.setString(10, tfAmount.getText());
//
//                int result = pstmt.executeUpdate();
//
//                if (result > 0) {
////                    JOptionPane.showMessageDialog(this, "Payment successful. Your PNR: " + pnr);
//                    dispose();
//                    new Pay(tfSource.getText(), tfDestination.getText());
//
//                } else {
//                    JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (ae.getSource() == btnBack) {
//            dispose();
//            new BookFlight();
//        }
//    }
//
//    public static void main(String[] args) {
//        new Pay("source","destination");
//    }
//}




package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class Payment extends JFrame implements ActionListener {
    // Labels and text fields to display details
    JLabel lblAadhar, lblName, lblNationality, lblFlightName, lblFlightCode, lblSource, lblDestination, lblTravelDate, lblPNR, lblAmount, lblFlightTime;
    JTextField tfAadhar, tfName, tfNationality, tfFlightName, tfFlightCode, tfSource, tfDestination, tfTravelDate, tfPNR, tfAmount, tfFlightTime;
    JButton btnConfirm, btnBack;

    // Constructor to initialize the payment page
    public Payment(String aadhar, String name, String nationality, String flightName, String flightCode, String source, String destination, String travelDate) {
        setTitle("Payment - Flight Booking");

        // Set up the layout and window size
        setLayout(new GridLayout(12, 2, 10, 10));  // Increased grid layout rows
        setSize(500, 650);  // Increased window size
        setLocation(400, 100);

        // Initialize labels and text fields
        lblAadhar = new JLabel("Aadhar: ");
        lblName = new JLabel("Name: ");
        lblNationality = new JLabel("Nationality: ");
        lblFlightName = new JLabel("Flight Name: ");
        lblFlightCode = new JLabel("Flight Code: ");
        lblSource = new JLabel("Source: ");
        lblDestination = new JLabel("Destination: ");
        lblTravelDate = new JLabel("Travel Date: ");
        lblPNR = new JLabel("PNR Number: ");
        lblAmount = new JLabel("Amount (Rs.): ");
        lblFlightTime = new JLabel("Flight Time: ");  // New label for flight time

        tfAadhar = new JTextField(aadhar);
        tfAadhar.setEditable(false);
        tfName = new JTextField(name);
        tfName.setEditable(false);
        tfNationality = new JTextField(nationality);
        tfNationality.setEditable(false);
        tfFlightName = new JTextField(flightName);
        tfFlightName.setEditable(false);
        tfFlightCode = new JTextField(flightCode);
        tfFlightCode.setEditable(false);
        tfSource = new JTextField(source);
        tfSource.setEditable(false);
        tfDestination = new JTextField(destination);
        tfDestination.setEditable(false);
        tfTravelDate = new JTextField(travelDate);
        tfTravelDate.setEditable(false);
        tfPNR = new JTextField();
        tfPNR.setEditable(false);
        tfAmount = new JTextField();
        tfAmount.setEditable(false);
        tfFlightTime = new JTextField();  // Text field for flight time
        tfFlightTime.setEditable(false);

        // Buttons for confirming the payment or going back
        btnConfirm = new JButton("Confirm Payment");
        btnBack = new JButton("Back");

        // Add action listeners for the buttons
        btnConfirm.addActionListener(this);
        btnBack.addActionListener(this);

        // Add the components to the frame
        add(lblAadhar);
        add(tfAadhar);
        add(lblName);
        add(tfName);
        add(lblNationality);
        add(tfNationality);
        add(lblFlightName);
        add(tfFlightName);
        add(lblFlightCode);
        add(tfFlightCode);
        add(lblSource);
        add(tfSource);
        add(lblDestination);
        add(tfDestination);
        add(lblTravelDate);
        add(tfTravelDate);
        add(lblPNR);
        add(tfPNR);
        add(lblAmount);
        add(tfAmount);
        add(lblFlightTime);
        add(tfFlightTime);  // Add flight time to the frame
        add(btnConfirm);
        add(btnBack);

        setVisible(true);

        // Generate a random numeric PNR and display it in the text field
        String pnr = generateRandomPNR();
        tfPNR.setText(pnr);

        // Fetch the amount and flight time for the selected flight based on source and destination
        fetchAmountAndTime(source, destination);
    }

    // Method to fetch the amount and flight time from the flight table based on source and destination
    private void fetchAmountAndTime(String source, String destination) {
        if (source == null || source.trim().isEmpty() || destination == null || destination.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Source or destination cannot be empty.");
            return;
        }

        try {
            Conn conn = new Conn();  // Assuming Conn class is established and connected
            String query = "SELECT amount, flight_time FROM flight WHERE source = ? AND destination = ?";  // Column name 'amount' and 'flight_time'
            PreparedStatement pstmt = conn.c.prepareStatement(query);
            pstmt.setString(1, source);  // Set the source
            pstmt.setString(2, destination);  // Set the destination
            ResultSet rs = pstmt.executeQuery();  // Execute the query

            if (rs.next()) {
                String amount = rs.getString("amount");  // Fetch the amount from the database
                String flightTime = rs.getString("flight_time");  // Fetch the flight time from the database
                tfAmount.setText(amount);  // Display the amount in the text field
                tfFlightTime.setText(flightTime);  // Display the flight time in the text field
            } else {
                JOptionPane.showMessageDialog(this, "No flight found for the selected source and destination.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching flight details.");
        }
    }

    // Method to generate a random numeric PNR (6 digits)
    private String generateRandomPNR() {
        Random random = new Random();
        int pnrNumber = 100000 + random.nextInt(900000);
        return String.valueOf(pnrNumber);
    }

    // Action performed method to handle button clicks
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnConfirm) {
            String pnr = tfPNR.getText();

            // Validate that amount is not empty before confirming payment
            if (tfAmount.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a valid flight (source and destination).");
                return;
            }

            try {
                Conn conn = new Conn();
                String query = "INSERT INTO booking_details (aadhar, name, nationality, flight_name, flight_code, source, destination, travel_date, pnr, amount, flight_time) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  // Added flight_time to the query
                PreparedStatement pstmt = conn.c.prepareStatement(query);
                pstmt.setString(1, tfAadhar.getText());
                pstmt.setString(2, tfName.getText());
                pstmt.setString(3, tfNationality.getText());
                pstmt.setString(4, tfFlightName.getText());
                pstmt.setString(5, tfFlightCode.getText());
                pstmt.setString(6, tfSource.getText());
                pstmt.setString(7, tfDestination.getText());
                pstmt.setString(8, tfTravelDate.getText());
                pstmt.setString(9, pnr);
                pstmt.setString(10, tfAmount.getText());
                pstmt.setString(11, tfFlightTime.getText());  // Set flight time

                int result = pstmt.executeUpdate();

                if (result > 0) {
                    dispose();
                    new Pay(tfSource.getText(), tfDestination.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == btnBack) {
            dispose();
            new BookFlight();
        }
    }

    public static void main(String[] args) {
        new Pay("source", "destination");
    }
}
