import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ViewBookings extends JFrame {
    JTable bookingsTable;

    public ViewBookings() {
        setLayout(new BorderLayout());

        // Set up the JTable to display bookings
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Booking ID");
        columnNames.add("Flights");
        columnNames.add("Name");
        columnNames.add("Contact");
        columnNames.add("Passengers");
        columnNames.add("Stay Days");
        columnNames.add("Total Cost");
        columnNames.add("Booking Date");

        Vector<Vector<Object>> data = new Vector<>();

        try {
            // Connect to the database and fetch the booking data
            Connection con = DriverManager.getConnection("jdbc:mysql:///airlinemanagementsystem", "root", "root");
            String query = "SELECT * FROM bookings";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Populate the data vector with rows
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("booking_id"));
                row.add(rs.getString("flights"));
                row.add(rs.getString("name"));
                row.add(rs.getString("contact"));
                row.add(rs.getInt("passengers"));
                row.add(rs.getInt("stay_days"));
                row.add(rs.getDouble("total_cost"));
                row.add(rs.getTimestamp("booking_date"));
                data.add(row);
            }

            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching booking data: " + e.getMessage());
        }

        bookingsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        add(scrollPane, BorderLayout.CENTER);

        setTitle("View Bookings");
        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewBookings();
    }
}
