
package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class Cancel extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, lblflightname, lbldateoftravel, cancellationno;
    JButton fetchButton, cancelButton;

    public Cancel() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        Random random = new Random();

        JLabel heading = new JLabel("CANCELLATION");
        heading.setBounds(180, 20, 250, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        add(heading);

        JLabel lblaadhar = new JLabel("PNR Number");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 80, 150, 25);
        add(tfpnr);

        fetchButton = new JButton("Show Details");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 80, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(60, 130, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 130, 150, 25);
        add(tfname);

        JLabel lblflight = new JLabel("Flight Name");
        lblflight.setBounds(60, 180, 150, 25);
        lblflight.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblflight);

        lblflightname = new JLabel();
        lblflightname.setBounds(220, 180, 150, 25);
        add(lblflightname);

        JLabel lbldate = new JLabel("Travel Date");
        lbldate.setBounds(60, 230, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);

        lbldateoftravel = new JLabel();
        lbldateoftravel.setBounds(220, 230, 150, 25);
        add(lbldateoftravel);

        JLabel lblcancelno = new JLabel("Cancellation No");
        lblcancelno.setBounds(60, 280, 150, 25);
        lblcancelno.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblcancelno);

        cancellationno = new JLabel("" + random.nextInt(1000000));
        cancellationno.setBounds(220, 280, 150, 25);
        add(cancellationno);

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBounds(220, 330, 150, 25);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setSize(600, 450);
        setLocation(350, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == fetchButton) {
        String pnr = tfpnr.getText().trim();

        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a valid PNR");
            return;
        }

        try {
            Conn conn = new Conn();

            // Fetch details using PNR
            String query = "SELECT name, flight_name, travel_date FROM booking_details WHERE pnr = ?";
            PreparedStatement pst = conn.c.prepareStatement(query);
            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tfname.setText(rs.getString("name"));  // Set the name from the result
                lblflightname.setText(rs.getString("flight_name"));
                lbldateoftravel.setText(rs.getString("travel_date"));
            } else {
                JOptionPane.showMessageDialog(null, "PNR not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while fetching details: " + e.getMessage());
        }
    } else if (ae.getSource() == cancelButton) {
        String travelDate = lbldateoftravel.getText();

        try {
            // Check cancellation eligibility
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfTravel = sdf.parse(travelDate);
            Date currentDate = new Date();

            long diff = dateOfTravel.getTime() - currentDate.getTime();
            if (diff < (24 * 60 * 60 * 1000)) {
                JOptionPane.showMessageDialog(null, "Cancellation allowed only 1 day before travel.");
                return;
            }

            // Perform cancellation
            Conn conn = new Conn();
            String query = "UPDATE booking_details SET status = 'canceled' WHERE pnr = ?";
            PreparedStatement pst = conn.c.prepareStatement(query);
            pst.setString(1, tfpnr.getText().trim());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Ticket canceled successfully.\nCancellation No: " + cancellationno.getText());
            } else {
                JOptionPane.showMessageDialog(null, "No records found for this PNR.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while canceling ticket: " + e.getMessage());
        }
    }
}
}
