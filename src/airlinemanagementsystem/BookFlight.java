

package airlinemanagementsystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookFlight extends JFrame implements ActionListener {
    
    JTextField tfaadhar;
    JLabel tfname, tfnationality, tfaddress, labelgender, labelfname, labelfcode,labelamount;
    JButton bookflight, fetchButton, flight;
    Choice source, destination;
    JDateChooser dcdate;

    public BookFlight() {
       String aadhar;
       String name;
       String nationality;
       String souce;
       String dest;
       String travel_date;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
        JLabel heading = new JLabel("Book Flight");
        heading.setBounds(420, 20, 500, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        heading.setForeground(Color.BLUE);
        add(heading);
        
        JLabel lblaadhar = new JLabel("Aadhar");
        lblaadhar.setBounds(60, 80, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);
        
        tfaadhar = new JTextField();
        tfaadhar.setBounds(220, 80, 150, 25);
        add(tfaadhar);
        
        fetchButton = new JButton("Fetch User");
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
        
        JLabel lblnationality = new JLabel("Nationality");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);
        
        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(60, 230, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);
        
        tfaddress = new JLabel();
        tfaddress.setBounds(220, 230, 150, 25);
        add(tfaddress);
        
        JLabel lblgender = new JLabel("Gender");
        lblgender.setBounds(60, 280, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);
        
        labelgender = new JLabel();
        labelgender.setBounds(220, 280, 150, 25);
        add(labelgender);
        
        JLabel lblsource = new JLabel("Source");
        lblsource.setBounds(60, 330, 150, 25);
        lblsource.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblsource);
        
        source = new Choice();
        source.setBounds(220, 330, 150, 25);       
        add(source);
        
        JLabel lbldest = new JLabel("Destination");
        lbldest.setBounds(60, 380, 150, 25);
        lbldest.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldest);
        
        destination = new Choice();
        destination.setBounds(220, 380, 150, 25);       
        add(destination);
        
        try {
            Conn c = new Conn();
            String query = "select * from flight";
            ResultSet rs = c.s.executeQuery(query);
            
            while(rs.next()) {
                source.add(rs.getString("source"));
                destination.add(rs.getString("destination"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        flight = new JButton("Fetch Flights");
        flight.setBackground(Color.BLACK);
        flight.setForeground(Color.WHITE);
        flight.setBounds(380, 380, 120, 25);
        flight.addActionListener(this);
        add(flight);
        
        JLabel lblfname = new JLabel("Flight Name");
        lblfname.setBounds(60, 430, 150, 25);
        lblfname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfname);
        
        labelfname = new JLabel();
        labelfname.setBounds(220, 430, 150, 25);
        add(labelfname);
        
        JLabel lblfcode = new JLabel("Flight Code");
        lblfcode.setBounds(60, 480, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcode);
        
        labelfcode = new JLabel();
        labelfcode.setBounds(220, 480, 150, 25);
        add(labelfcode);
        
        JLabel lblamount = new JLabel("Amount");
        lblamount.setBounds(60, 530, 150, 25);
        lblamount.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblamount);
        
        labelamount = new JLabel();
        labelamount.setBounds(220, 530, 150, 25);
        add(labelamount);
        
        JLabel lbldate = new JLabel("Date of Travel");
        lbldate.setBounds(60, 580, 150, 25);
        lbldate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbldate);
        
        dcdate = new JDateChooser();
        dcdate.setBounds(220, 580, 150, 25);
        add(dcdate);
        
        bookflight = new JButton("Book Flight");
        bookflight.setBackground(Color.BLACK);
        bookflight.setForeground(Color.WHITE);
        bookflight.setBounds(220, 630, 150, 25);
        bookflight.addActionListener(this);
        add(bookflight);
        
        setSize(1100, 700);
        setLocation(200, 50);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String aadhar = tfaadhar.getText();
            try {
                Conn conn = new Conn();
                String query = "select * from passenger where aadhar = '"+aadhar+"'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    tfname.setText(rs.getString("name")); 
                    tfnationality.setText(rs.getString("nationality")); 
                    tfaddress.setText(rs.getString("address"));
                    labelgender.setText(rs.getString("gender"));
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct aadhar");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == flight) {
            String src = source.getSelectedItem();
            String dest = destination.getSelectedItem();
            try {
                Conn conn = new Conn();
                String query = "select * from flight where source = '"+src+"' and destination = '"+dest+"'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    labelfname.setText(rs.getString("f_name")); 
                    labelfcode.setText(rs.getString("f_code"));
                    labelamount.setText(rs.getString("amount")); 

                } else {
                    JOptionPane.showMessageDialog(null, "No Flights Found");                
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // In the actionPerformed method when booking the flight
// In the actionPerformed method when booking the flight
else if (ae.getSource() == bookflight) {
    String aadhar = tfaadhar.getText();
    String name = tfname.getText();
    String nationality = tfnationality.getText();
    String flightName = labelfname.getText();
    String flightCode = labelfcode.getText();
    String flightamount = labelamount.getText();
    String src = source.getSelectedItem();
    String dest = destination.getSelectedItem();

    // Get the selected date from the JDateChooser
    java.util.Date selectedDate = dcdate.getDate();

    // Check if a date was selected
    if (selectedDate != null) {
        // Get today's date
        java.util.Date currentDate = new java.util.Date();

        // Convert the selected date to milliseconds for comparison
        long selectedTimeInMillis = selectedDate.getTime();

        // Check if the selected date is today or in the future
        if (selectedTimeInMillis < currentDate.getTime()) {
            JOptionPane.showMessageDialog(null, "Please select a valid date of travel. It must be today's date or a future date.");
        } else {
            // Fetch the flight time from the database
            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM flight WHERE f_name = '" + flightName + "' AND f_code = '" + flightCode + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    // Assuming the flight time is stored as a string in the database (e.g., "14:30")
                    String flightTimeStr = rs.getString("flight_time"); 
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    Date flightTime = timeFormat.parse(flightTimeStr);

                    // Get the flight's departure time in milliseconds
                    long flightTimeInMillis = flightTime.getTime();
                    
                    // Remove the 4-hour condition (just proceed without this check)

                    // Format the date to "yyyy-MM-dd"
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String travelDate = sdf.format(selectedDate);

                    // Pass the formatted travel date to the Payment window
                   new Payment(aadhar, name, nationality, flightName, flightCode, src, dest, travelDate).setVisible(true);

                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Flight details not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Please select a valid date of travel.");
    }
}
    }
}

    

