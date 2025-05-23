package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserHome extends JFrame implements ActionListener {

    public UserHome() {
        setLayout(null);

        // Adding background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/front.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1600, 800);
        add(image);

        // Heading Label
        JLabel heading = new JLabel(" GROUP6 AIRLINE WELCOMES YOU");
        heading.setBounds(500, 40, 1000, 40);
        heading.setForeground(Color.BLUE);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 36));
        image.add(heading);

        // Paragraph about the Airline Reservation System
        JLabel description = new JLabel("<html><div style='width: 1000px; text-align: center;'>Welcome to Air India! "
                + "Our airline reservation system provides a seamless and efficient way for passengers to book flights, "
                + "manage their journeys, and keep track of ticketing details. You can easily add customer information, "
                + "book flights, view journey details, and even cancel tickets. Additionally, the system offers facilities "
                + "like generating boarding passes for your convenience. Enjoy a smooth experience with Air India!</div></html>");
        description.setBounds(200, 100, 1200, 60);
        description.setFont(new Font("Tahoma", Font.PLAIN, 18));
        description.setForeground(Color.BLACK);
        image.add(description);

        // Menu Bar
        JMenuBar menubar = new JMenuBar();
        menubar.setPreferredSize(new Dimension(2500, 70)); // Increased width and height
        setJMenuBar(menubar);

        // Add 'Details' Menu
        JMenu details = new JMenu("Details");
        details.setFont(new Font("Arial", Font.PLAIN, 20));
        menubar.add(details);

        // Add items in 'Details' Menu
        JMenuItem customerDetails = new JMenuItem("Add Customer Details");
        customerDetails.setFont(new Font("Arial", Font.PLAIN, 18));
        customerDetails.addActionListener(this);
        details.add(customerDetails);

        JMenuItem bookFlight = new JMenuItem("Book Flight");
        bookFlight.setFont(new Font("Arial", Font.PLAIN, 18));
        bookFlight.addActionListener(this);
        details.add(bookFlight);

        JMenuItem journeyDetails = new JMenuItem("Journey Details");
        journeyDetails.setFont(new Font("Arial", Font.PLAIN, 18));
        journeyDetails.addActionListener(this);
        details.add(journeyDetails);

        JMenuItem ticketCancellation = new JMenuItem("Cancel Ticket");
        ticketCancellation.setFont(new Font("Arial", Font.PLAIN, 18));
        ticketCancellation.addActionListener(this);
        details.add(ticketCancellation);

        // Add 'Ticket' Menu
        JMenu ticket = new JMenu("Ticket");
        ticket.setFont(new Font("Arial", Font.PLAIN, 20));
        menubar.add(ticket);

        // Create a submenu under 'Ticket'
        JMenu bookingSubMenu = new JMenu("Booking");
        bookingSubMenu.setFont(new Font("Arial", Font.PLAIN, 18));
        ticket.add(bookingSubMenu);

        // Add 'Multiple Book' menu item under the 'Booking' submenu
        JMenuItem multipleBook = new JMenuItem("Multiple Book");
        multipleBook.setFont(new Font("Arial", Font.PLAIN, 18));
        multipleBook.addActionListener(this);
        bookingSubMenu.add(multipleBook);

        // Add 'Boarding Pass' menu item directly under 'Ticket'
        JMenuItem boardingPass = new JMenuItem("Boarding Pass");
        boardingPass.setFont(new Font("Arial", Font.PLAIN, 18));
        boardingPass.addActionListener(this);
        ticket.add(boardingPass);

        // Add horizontal glue to push Logout to the right
        menubar.add(Box.createHorizontalGlue());

        // Add 'Logout' Menu
        JMenu logout = new JMenu("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 20));
        menubar.add(logout);

        // Add 'Logout' menu item
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutItem.addActionListener(this);
        logout.add(logoutItem);

        // Set the frame properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window
        setVisible(true);
    }

    // Handle menu item actions
    @Override
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();

        if (text.equals("Add Customer Details")) {
            new AddCustomer();
        } else if (text.equals("Book Flight")) {
            new BookFlight();
        } else if (text.equals("Journey Details")) {
            new JourneyDetails();
        } else if (text.equals("Cancel Ticket")) {
            new Cancel();
        } else if (text.equals("Boarding Pass")) {
            new BoardingPass();
        } else if (text.equals("Multiple Book")) {
            new MultipleBook();
        } else if (text.equals("Logout")) {
            setVisible(false); // Close current window
            Login.main(null); // Open Login screen
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        new UserHome();
    }
}
