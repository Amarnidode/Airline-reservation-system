package airlinemanagementsystem;


import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.regex.*;

public class MultipleBook extends JFrame {

    private Choice source, destination;
    private JButton addFlightButton, bookButton;
    private JTextArea flightDetails;
    private JTextField customerNameField, contactField, emailField, pnrField;
    private JDateChooser flightDateChooser;  // JDateChooser for picking dates
    private ArrayList<String> journeyDetails = new ArrayList<>();
    private ArrayList<String> journeyDates = new ArrayList<>();
    private int totalCost = 0;
    private String generatedPNR;

    public MultipleBook() {
        setLayout(null);
        setTitle("Multi-Flight Booking");
        setSize(500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Customer Name Label and Text Field
        JLabel lblName = new JLabel("Customer Name:");
        lblName.setBounds(60, 20, 150, 25);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblName);

        customerNameField = new JTextField();
        customerNameField.setBounds(220, 20, 200, 25);
        add(customerNameField);

        // Contact Number Label and Text Field
        JLabel lblContact = new JLabel("Contact Number:");
        lblContact.setBounds(60, 60, 150, 25);
        lblContact.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblContact);

        contactField = new JTextField();
        contactField.setBounds(220, 60, 200, 25);
        add(contactField);

        // Email Label and Text Field
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(60, 100, 150, 25);
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(220, 100, 200, 25);
        add(emailField);

        // PNR Label and Text Field
        JLabel lblPnr = new JLabel("PNR:");
        lblPnr.setBounds(60, 140, 150, 25);
        lblPnr.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblPnr);

        pnrField = new JTextField();
        pnrField.setBounds(220, 140, 200, 25);
        pnrField.setEditable(false); // PNR is auto-generated
        add(pnrField);

        // Source Label and Choice
        JLabel lblsource = new JLabel("Source:");
        lblsource.setBounds(60, 190, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblsource);

        source = new Choice();
        source.setBounds(220, 190, 200, 25);
        add(source);

        // Destination Label and Choice
        JLabel lbldest = new JLabel("Destination:");
        lbldest.setBounds(60, 230, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lbldest);

        destination = new Choice();
        destination.setBounds(220, 230, 200, 25);
        add(destination);

        // Flight Date Label and DateChooser
        JLabel lblDate = new JLabel("Flight Date:");
        lblDate.setBounds(60, 270, 150, 25);
        lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
        add(lblDate);

        flightDateChooser = new JDateChooser();
        flightDateChooser.setBounds(220, 270, 200, 25);
        flightDateChooser.setDateFormatString("yyyy-MM-dd");
        flightDateChooser.setMinSelectableDate(new Date()); // Set today's date as the minimum selectable date
        add(flightDateChooser);

        // Button to add flight leg
        addFlightButton = new JButton("Add Flight");
        addFlightButton.setBounds(150, 310, 150, 30);
        add(addFlightButton);

        // Button to finalize booking
        bookButton = new JButton("Book");
        bookButton.setBounds(150, 700, 150, 30);
        bookButton.setVisible(false);
        add(bookButton);

        // Text area to display added flights
        flightDetails = new JTextArea();
        flightDetails.setBounds(60, 350, 360, 300);
        flightDetails.setEditable(false);
        flightDetails.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(flightDetails);

        // Load flight data from the database
        loadFlightData();

        // Add Action Listener for the Add Flight button
        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String src = source.getSelectedItem();
                String dest = destination.getSelectedItem();
                Date flightDate = flightDateChooser.getDate();

                if (flightDate == null) {
                    JOptionPane.showMessageDialog(null, "Please select a flight date!");
                    return;
                }

                if (src.equals(dest)) {
                    JOptionPane.showMessageDialog(null, "Source and Destination cannot be the same!");
                    return;
                }

                // Fetch the cost for the selected route
                int cost = getFlightCost(src, dest);
                if (cost == -1) {
                    JOptionPane.showMessageDialog(null, "No flights available for the selected route!");
                    return;
                }

                // Add flight details to the list
                String formattedDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(flightDate);
                journeyDetails.add("Flight from " + src + " to " + dest + " on " + formattedDate + " - Cost: " + cost);
                journeyDates.add(formattedDate);
                totalCost += cost;
                updateFlightDetails();

                // Set the destination as the new source for the next leg
                source.removeAll();
                source.add(dest);

                // Make the book button visible
                bookButton.setVisible(true);
                revalidate();
                repaint();
            }
        });

        // Add Action Listener for the Book button
       bookButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String customerName = customerNameField.getText().trim();
        String contactNumber = contactField.getText().trim();
        String email = emailField.getText().trim();

        // Validate inputs
        if (!validateName(customerName)) {
            JOptionPane.showMessageDialog(null, "Customer name should contain only letters and spaces!");
            return;
        }

        if (!validateContact(contactNumber)) {
            JOptionPane.showMessageDialog(null, "Contact number should contain only numbers!");
            return;
        }

        if (!validateEmail(email)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address!");
            return;
        }

        // Generate and display PNR
        generatedPNR = generatePNR();
        pnrField.setText(generatedPNR);

        // Save booking details to the database
        saveBookingDetails(customerName, contactNumber, email, generatedPNR);

        JOptionPane.showMessageDialog(null, "Booking confirmed!\nPNR: " + generatedPNR + "\nTotal Cost: " + totalCost);

        // Open the Pay page
        new MultiPay(generatedPNR, totalCost); // Pass PNR and total cost to Pay page

        dispose(); // Close the current booking window after confirmation
    }
});

        setVisible(true);
    }
    public void saveBookingDetails(String name, String contact, String email, String pnr) {
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql:///airlinemanagementsystem", "root", "root");
        PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO bookings (pnr, customer_name, contact, email, total_cost, journey_details, journey_dates) VALUES (?, ?, ?, ?, ?, ?, ?)");
        pstmt.setString(1, pnr);
        pstmt.setString(2, name);
        pstmt.setString(3, contact);
        pstmt.setString(4, email);
        pstmt.setInt(5, totalCost);
        pstmt.setString(6, String.join("; ", journeyDetails));
        pstmt.setString(7, String.join("; ", journeyDates));

        pstmt.executeUpdate();
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving booking details!");
    }
}

    

    // Method to update the flight details display
    private void updateFlightDetails() {
        flightDetails.setText("");
        for (int i = 0; i < journeyDetails.size(); i++) {
            flightDetails.append(journeyDetails.get(i) + "\n");
        }
        flightDetails.append("\nTotal Cost: " + totalCost);
    }

    // Method to load flight data from the database
    private void loadFlightData() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql:///airlinemanagementsystem", "root", "root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT source, destination FROM flight");

            while (rs.next()) {
                String src = rs.getString("source");
                String dest = rs.getString("destination");

                if (!isChoicePresent(source, src)) {
                    source.add(src);
                }
                if (!isChoicePresent(destination, dest)) {
                    destination.add(dest);
                }
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching flight data from the database!");
        }
    }

    // Method to fetch the flight cost from the database
    private int getFlightCost(String src, String dest) {
        int cost = -1;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql:///airlinemanagementsystem", "root", "root");
            PreparedStatement pstmt = con.prepareStatement(
                    "SELECT amount FROM flight WHERE source = ? AND destination = ?");
            pstmt.setString(1, src);
            pstmt.setString(2, dest);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cost = rs.getInt("amount");
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching flight cost!");
        }
        return cost;
    }

    // Helper method to check if a Choice already contains an item
    private boolean isChoicePresent(Choice choice, String item) {
        for (int i = 0; i < choice.getItemCount(); i++) {
            if (choice.getItem(i).equals(item)) {
                return true;
            }
        }
        return false;
    }

    // Validate customer name (only letters and spaces)
    private boolean validateName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    // Validate contact number (only numbers)
    private boolean validateContact(String contact) {
        return contact.matches("\\d+");
    }

    // Validate email address
    private boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to generate a random numeric PNR
    private String generatePNR() {
        Random random = new Random();
        return String.valueOf(10000 + random.nextInt(90000)); // Generate 5-digit numeric PNR
    }

    public static void main(String[] args) {
        new MultipleBook();
    }
}
