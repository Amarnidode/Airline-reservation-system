package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import net.proteanit.sql.DbUtils;

public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField pnr;
    JButton show;

    public JourneyDetails() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(new FlowLayout());

        JLabel lblpnr = new JLabel("PNR Details");
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblpnr);

        pnr = new JTextField(15);
        add(pnr);

        show = new JButton("Show Details");
        show.setBackground(Color.BLACK);
        show.setForeground(Color.WHITE);
        show.addActionListener(this);
        add(show);

        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setPreferredSize(new Dimension(750, 300)); // Set preferred size for table
        add(jsp);

        setSize(800, 600);
        setLocation(400, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn conn = new Conn();

            // Check if the connection is established
            if (conn.s == null) {  // Use conn.s instead of conn.c
                JOptionPane.showMessageDialog(null, "Database connection failed!");
                return;
            }

            // Log to check if the connection is active
            System.out.println("Database connected successfully");

            // Use prepared statement to prevent SQL injection
            String query = "SELECT * From booking_details WHERE pnr = ?";
            PreparedStatement pst = conn.c.prepareStatement(query); // Use conn.s here
            pst.setString(1, pnr.getText());
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "No Information Found");
                return;
            }

            // Fill the table with the result set
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching details: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
